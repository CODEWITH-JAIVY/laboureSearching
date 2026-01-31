package com.labourseSearching.Service.JobPostService;

import com.labourseSearching.DTO.JobFeedDtos.*;
import com.labourseSearching.Entity.Customer.CustomerProfile;
import com.labourseSearching.Entity.JobPost.JobPost;
import com.labourseSearching.Entity.Labour.LabourProfile;
import com.labourseSearching.Entity.LocationLiveTracing.LiveLocation;
import com.labourseSearching.Entity.Users.User;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.UserNotFoudnException;
import com.labourseSearching.ModerlerMapper.JobPostModelMapper;
import com.labourseSearching.Repository.Customer.CustomerProfileRepository;
import com.labourseSearching.Repository.JobPostRespository.JobPostRepository;
import com.labourseSearching.Repository.Laboure.LabourProfileRepository;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import com.labourseSearching.Service.Geo.NearbyResult;
import com.labourseSearching.Service.Geo.RedisGeoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    private static final double RADIUS_KM = 5.0;

    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;
    private final JobPostModelMapper mapper;
    private final RedisGeoService redisGeoService;
    private final CustomerProfileRepository customerProfileRepository;
    private final LabourProfileRepository labourProfileRepository;

    public JobServiceImp(
            UserRepository userRepository,
            JobPostRepository jobPostRepository,
            JobPostModelMapper mapper,
            RedisGeoService redisGeoService,
            CustomerProfileRepository customerProfileRepository,
            LabourProfileRepository labourProfileRepository
    ) {
        this.userRepository = userRepository;
        this.jobPostRepository = jobPostRepository;
        this.mapper = mapper;
        this.redisGeoService = redisGeoService;
        this.customerProfileRepository = customerProfileRepository;
        this.labourProfileRepository = labourProfileRepository;
    }

    // ---------- SIMPLE JOB ----------
    @Override
    @Transactional
    public jobResponceDto postJobByCustomer(long userId, JobPostDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        JobPost jobPost = mapper.toEntity(dto, user);
        jobPostRepository.save(jobPost);

        return mapper.toResponse(jobPost);
    }

    // ---------- JOB + PAGINATED ONLINE LABOURS ----------
    @Override
    @Transactional
    public JobPostWithNearbyLabourResponse postJobWithNearbyLabours(
            long userId,
            JobPostDto dto,
            int page,
            int size
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        CustomerProfile customerProfile =
                customerProfileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException("Create customer profile first"));

        LiveLocation location = customerProfile.getLiveLocation();
        if (location == null) {
            throw new RuntimeException("Customer live location not available");
        }

        JobPost jobPost = mapper.toEntity(dto, user);
        jobPostRepository.save(jobPost);

        String labourType = jobPost.getLabourType().name();

        // 🔥 ONLY ONLINE LABOURS + JOB TYPE + PAGINATION
        List<NearbyResult> nearbyResults =
                redisGeoService.findNearby(
                        "labour:geo:" + labourType,
                        location.getLatitude(),
                        location.getLongitude(),
                        RADIUS_KM,
                        page,
                        size
                );

        List<Long> labourIds = nearbyResults.stream()
                .map(NearbyResult::id)
                .toList();

        Map<Long, LabourProfile> labourMap =
                labourIds.isEmpty()
                        ? Map.of()
                        : labourProfileRepository.findByIdIn(labourIds)
                        .stream()
                        .collect(Collectors.toMap(
                                LabourProfile::getId,
                                l -> l
                        ));

        List<NearbyLabourDto> feed = new ArrayList<>();

        for (NearbyResult r : nearbyResults) {
            LabourProfile lp = labourMap.get(r.id());
            if (lp != null) {
                NearbyLabourDto d = new NearbyLabourDto();
                d.setLabourProfileId(lp.getId());
                d.setName(lp.getUser().getName());
                d.setLabourType(lp.getLabourType().name());
                d.setAvailableToday(lp.isAvailableToday());
                d.setDistanceKm(r.distanceKm());
                feed.add(d);
            }
        }

        JobPostWithNearbyLabourResponse response =
                new JobPostWithNearbyLabourResponse();
        response.setJob(mapper.toResponse(jobPost));
        response.setNearbyLabours(feed);

        return response;
    }
}