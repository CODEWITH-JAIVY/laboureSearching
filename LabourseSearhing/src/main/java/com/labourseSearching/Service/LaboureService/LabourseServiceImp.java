package com.labourseSearching.Service.LaboureService;

import com.labourseSearching.DTO.AddressDtos.LocationUpdateDto;
import com.labourseSearching.DTO.JobFeedDtos.JobFeedDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileCreateDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileResponce;
import com.labourseSearching.DTO.UserDto.LaboureUpdateProfileDto;
import com.labourseSearching.Entity.ENUM.UserType;
import com.labourseSearching.Entity.JobPost.JobPost;
import com.labourseSearching.Entity.Labour.LabourProfile;
import com.labourseSearching.Entity.LocationLiveTracing.LiveLocation;
import com.labourseSearching.Entity.Users.User;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.UserNotFoudnException;
import com.labourseSearching.ModerlerMapper.LaboureUpdateProfileModelMapper;
import com.labourseSearching.ModerlerMapper.LabourseProfileCreateProfileMapper;
import com.labourseSearching.Repository.JobPostRespository.JobPostRepository;
import com.labourseSearching.Repository.Laboure.LabourProfileRepository;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import com.labourseSearching.Service.Geo.NearbyResult;
import com.labourseSearching.Service.Geo.RedisGeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LabourseServiceImp implements LabourseService {

    private final UserRepository userRepository;
    private final LaboureUpdateProfileModelMapper updateProfileMapper;
    private final LabourseProfileCreateProfileMapper createProfileMapper;
    private final LabourProfileRepository labourProfileRepository;
    private final RedisGeoService redisGeoService ;
    private final JobPostRepository jobPostRepository;


    public LabourseServiceImp(
            UserRepository userRepository,
            LaboureUpdateProfileModelMapper updateProfileMapper,
            LabourseProfileCreateProfileMapper createProfileMapper,
            LabourProfileRepository labourProfileRepository, RedisGeoService redisGeoService, JobPostRepository jobPostRepository
    ) {
        this.userRepository = userRepository;
        this.updateProfileMapper = updateProfileMapper;
        this.createProfileMapper = createProfileMapper;
        this.labourProfileRepository = labourProfileRepository;
        this.redisGeoService = redisGeoService;
        this.jobPostRepository = jobPostRepository;
    }

    // ================= UPDATE PROFILE =================
    @Override
    public LaboureUpdateProfileDto updateProfile(
            long laboureId,
            LaboureUpdateProfileDto dto
    ) {

        User dbUser = userRepository.findById(laboureId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        updateProfileMapper.mapToEntity(dto, dbUser);
        userRepository.save(dbUser);

        return updateProfileMapper.toDto(dbUser);
    }


    @Transactional
    @Override
    public LabourseProfileResponce createProfile(
            long userId,
            LabourseProfileCreateDto dto
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        // 🚫 USER TYPE CHECK
        if (user.getUserType() != UserType.LABOUR) {
            throw new IllegalStateException("User is not LABOUR");
        }

        // 🚫 DUPLICATE PROFILE CHECK
        if (user.getLabourProfile() != null) {
            throw new IllegalStateException("Labour profile already exists");
        }

        createProfileMapper.mapToEntity(dto, user);
        userRepository.save(user);

        return createProfileMapper.toResponse(user);
    }



    @Transactional
    public LocationUpdateDto updateLiveLocation(long userId, LocationUpdateDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        LabourProfile profile = user.getLabourProfile();
        if (profile == null) {
            throw new RuntimeException("Create labour profile first");
        }

        LiveLocation liveLocation = profile.getLiveLocation();
        if (liveLocation == null) {
            liveLocation = new LiveLocation();
        }

        liveLocation.setLatitude(dto.getLatitude());
        liveLocation.setLongitude(dto.getLongitude());
        liveLocation.setUpdatedAt(LocalDateTime.now());

        profile.setLiveLocation(liveLocation);

        labourProfileRepository.save(profile);

        // Redis GEO
        redisGeoService.save(
                "labourer:geo",
                profile.getId(),
                dto.getLatitude(),
                dto.getLongitude()
        );

        return dto;
    }
    @Override
    public List<JobFeedDto> getNearbyJobsForLabourer(
            Long userId,
            int page,
            int size
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        LabourProfile profile = user.getLabourProfile();
        if (profile == null || profile.getLiveLocation() == null) {
            return List.of();
        }

        double lat = profile.getLiveLocation().getLatitude();
        double lng = profile.getLiveLocation().getLongitude();

        String redisKey = "job:geo:" + profile.getLabourType();
        double radiusKm = 5.0;

        List<NearbyResult> nearbyJobs =
                redisGeoService.findNearby(
                        redisKey,
                        lat,
                        lng,
                        radiusKm,
                        page,   // ✅ CORRECT
                        size
                );

        if (nearbyJobs.isEmpty()) {
            return List.of();
        }

        List<Long> jobIds = nearbyJobs.stream()
                .map(NearbyResult::id)
                .toList();

        Map<Long, JobPost> jobMap =
                jobPostRepository.findByIdInAndActiveTrue(jobIds)
                        .stream()
                        .collect(Collectors.toMap(
                                JobPost::getId,
                                j -> j
                        ));

        List<JobFeedDto> response = new ArrayList<>();

        for (NearbyResult r : nearbyJobs) {
            JobPost job = jobMap.get(r.id());
            if (job != null) {
                response.add(JobFeedDto.from(job, r.distanceKm()));
            }
        }

        return response;
    }






}