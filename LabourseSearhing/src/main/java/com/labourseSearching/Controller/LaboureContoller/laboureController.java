package com.labourseSearching.Controller.LaboureContoller;

import com.labourseSearching.DTO.AddressDtos.LocationUpdateDto;
import com.labourseSearching.DTO.JobFeedDtos.JobFeedDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileCreateDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileResponce;
import com.labourseSearching.DTO.UserDto.LaboureUpdateProfileDto;
import com.labourseSearching.Security.principal.UserPrincipal;
import com.labourseSearching.Service.JobQuery.JobAcceptanceService;
import com.labourseSearching.Service.LaboureService.LabourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboure")
public class laboureController {

    private final LabourseService labourseService;
    private  final  JobAcceptanceService jobAcceptanceService  ;

    public laboureController(LabourseService labourseService, JobAcceptanceService jobAcceptanceService) {
        this.labourseService = labourseService;
        this.jobAcceptanceService = jobAcceptanceService;
    }

    //========================================================
    //           LABOURE
    //======================================================

    @PatchMapping("/createProfile")
    private ResponseEntity<LabourseProfileResponce> createLaboureProfile(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody LabourseProfileCreateDto dto
    ) {
        if (user == null) {
            throw new RuntimeException("Unauthorized: User not authenticated");
        }
        long userid = user.getId();
        System.out.println("CREATE PROFILE API HIT");

        System.out.println(" user id for the testing perpose in controller layer " + userid);
        return ResponseEntity.ok(
                labourseService.createProfile(userid, dto)
        );
    }
//


    // UPDATE THERIE PROFILE
    @PatchMapping("/updateProfile/{laboureId}")
    private ResponseEntity<LaboureUpdateProfileDto> updateProfile(
            @PathVariable long laboureId,
            @RequestBody LaboureUpdateProfileDto laboureUpdateProfileDto
    ) {
//        System.out.println("==laboure want to update profile ");
        return ResponseEntity.ok(labourseService.updateProfile(laboureId, laboureUpdateProfileDto));
    }


    ///  UPDATE THE LABOURES LIVE LOCATION

    @PatchMapping("/real-time-location-save")
    private ResponseEntity<LocationUpdateDto> livelocationSave(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody LocationUpdateDto liveDto
    ) {
        long userid = userPrincipal.getId();
        return ResponseEntity.ok(labourseService.updateLiveLocation(userid, liveDto));
    }


    ///  FOR THIS API THERE IS NO NEED TO HIT THE API
    /// WHEN THE LABOURE UPDATE THE LIVE LOCATION THAT TIME THIS API WILL SUGGEST THE NEAREST JOB POST
    @GetMapping("/jobs/nearby")
    public List<JobFeedDto> nearbyJobs(
            @RequestParam int page,
            @RequestParam int size,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return labourseService.getNearbyJobsForLabourer(
                user.getId(),
                page,
                size
        );
    }


  /// ACEPT THE POSTED JOB
    @PostMapping("/accept-job")
    public ResponseEntity<String> acceptJob(
            @AuthenticationPrincipal UserPrincipal labour,
            @RequestParam Long jobId,
            @RequestParam String labourType,
            @RequestParam Long customerId
    ) {
        boolean ok = jobAcceptanceService.acceptJob(
                jobId,
                labourType,
                labour.getId(),
                customerId
        );

        return ok
                ? ResponseEntity.ok("Job accepted")
                : ResponseEntity.badRequest().body("Job already full");
    }

}