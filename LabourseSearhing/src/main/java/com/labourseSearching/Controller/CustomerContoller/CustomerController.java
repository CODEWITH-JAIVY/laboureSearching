package com.labourseSearching.Controller.CustomerContoller;

import com.labourseSearching.DTO.AddressDtos.LocationUpdateDto;
import com.labourseSearching.DTO.CustomerDto.CustomerProfileDto;
import com.labourseSearching.DTO.CustomerDto.CustomerProfileResponceDto;
import com.labourseSearching.DTO.JobFeedDtos.JobPostDto;
import com.labourseSearching.DTO.JobFeedDtos.JobPostWithNearbyLabourResponse;
import com.labourseSearching.DTO.JobFeedDtos.jobResponceDto;
import com.labourseSearching.Security.principal.UserPrincipal;
import com.labourseSearching.Service.CustomerService.CustomerService;
import com.labourseSearching.Service.JobPostService.JobService;
import com.labourseSearching.Service.JobQuery.JobQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final JobService jobService;
    private final CustomerService customerService;
    private final JobQueryService jobQueryService  ;

    public CustomerController(
            JobService jobService,
            CustomerService customerService, JobQueryService jobQueryService
    ) {
        this.jobService = jobService;
        this.customerService = customerService;
        this.jobQueryService = jobQueryService;
    }

    @PostMapping("/create-profile")
    public ResponseEntity<CustomerProfileResponceDto> createProfile(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody CustomerProfileDto dto
    ) {
        return ResponseEntity.ok(
                customerService.createProfile(user.getId(), dto)
        );
    }

    @PatchMapping("/real-time-location-save")
    public ResponseEntity<LocationUpdateDto> saveLiveLocation(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody LocationUpdateDto liveDto
    ) {
        return ResponseEntity.ok(
                customerService.updateLiveLocation(user.getId(), liveDto)
        );
    }

    @PostMapping("/job-post")
    public ResponseEntity<jobResponceDto> postJob(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody JobPostDto jobPostDto
    ) {
        return ResponseEntity.ok(
                jobService.postJobByCustomer(user.getId(), jobPostDto)
        );
    }

    // AUTO RESPONSE + PAGINATION
    @PostMapping("/job-post-auto-response")
    public ResponseEntity<JobPostWithNearbyLabourResponse> postJobAuto(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody JobPostDto jobPostDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                jobService.postJobWithNearbyLabours(
                        user.getId(),
                        jobPostDto,
                        page,
                        size
                )
        );
    }

    // JOB ACCEPTED

    @GetMapping("/job/{jobId}/accepted-labours")
    public ResponseEntity<?> getAcceptedLabours(
            @PathVariable Long jobId
    ) {
        return ResponseEntity.ok(
                jobQueryService.getAcceptedLabours(jobId)
        );
    }


}