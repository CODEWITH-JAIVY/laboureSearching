package com.labourseSearching.Controller.UserConttoler;

import com.labourseSearching.DTO.UserDto.*;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import com.labourseSearching.Security.principal.UserPrincipal;
import com.labourseSearching.Service.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserContoller {


    private final UserService userService;
    private final UserRepository userRepository;

    public UserContoller(UserService userService,
                         UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    // SIGNUP OR AUTO LOGIN
    @PostMapping("/sing/email")
    public ResponseEntity<UserEmailLoninResponseDto> authenticateSingIn(
            @Valid @RequestBody EmailAuthSingInRequestDto request
    ) {

        System.out.println("Sing in Comtrollre hit ");
        return ResponseEntity.ok(userService.emailAuthSingIn(request));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<UserEmailLoninResponseDto> authenticateLogin(
            @Valid @RequestBody EmailAuthLonginRequestDto request
    ) {
        System.out.println("Login controlller hit ");
        return ResponseEntity.ok(userService.emailAuthLogin(request));
    }


    // when the user will open the application than this api will hit by the frontedn
    // this api will be set  in the client side that when the user will open the applicatin that time this  api will hit
    @GetMapping("/profile/me")
    public ResponseEntity<UserAuthStateDto> showMyProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        System.out.println(" Profile controller hit ");
        Long id = userPrincipal.getId();
        return ResponseEntity.ok(userService.showMyProfile(id));
    }

    // when the user will login their profile that time they can see the onter laboure
    // base on  on the  near location





    ///   Permanent address in witch the user can update the village , post , dist  , postal etnc
    ///  implement this api



    ///  update profile  image only

    @PostMapping("me/profile/photo")
    public ResponseEntity<String> uploadProfilePhoto(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam("file") MultipartFile file
    ) {
        System.out.println("Profile image contoller hit ");
        String imageUrl = userService.uploadProfilePhoto(user.getId(), file);
        System.out.println("Image url save in the db " + imageUrl );
        return ResponseEntity.ok(imageUrl);
    }


    @PostMapping("/select-role")
    public ResponseEntity<UserTypeSelectResponceDto> selectUserRole(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid UserTypeSelectRequestDto request
    ) {
        System.out.println("user select-role controller hit ");
        System.out.println("user typr " +  request.getUserType());
        UserTypeSelectResponceDto  userTypeSelectResponceDto  =  userService.setUserType(
                principal.getId() ,
                request.getUserType()
        );
        return ResponseEntity.ok(userTypeSelectResponceDto) ;
    }


}