package com.labourseSearching.Service.JobQuery;

import com.labourseSearching.DTO.Labourse.jobAcceptedLabourDto;
import com.labourseSearching.Entity.Labour.LabourProfile;
import com.labourseSearching.Repository.JobPostRespository.JobAcceptedLabourRepository;
import com.labourseSearching.Repository.Laboure.LabourProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobQueryService {

    private final JobAcceptedLabourRepository acceptedRepo;
    private final LabourProfileRepository labourRepo;

    public JobQueryService(
            JobAcceptedLabourRepository acceptedRepo,
            LabourProfileRepository labourRepo
    ) {
        this.acceptedRepo = acceptedRepo;
        this.labourRepo = labourRepo;
    }

    public List<jobAcceptedLabourDto> getAcceptedLabours(Long jobId) {
        return acceptedRepo.findByJobId(jobId)
                .stream()
                .map(a -> {
                    LabourProfile lp =
                            labourRepo.findById(a.getLabourId()).orElseThrow();

                    jobAcceptedLabourDto dto = new jobAcceptedLabourDto();
                    dto.setLabourId(lp.getId());
                    dto.setName(lp.getUser().getName());
                    dto.setLabourType(lp.getLabourType().name());
                    dto.setMobile(lp.getUser().getMobile());

                    return dto;
                })
                .collect(Collectors.toList());
    }
}