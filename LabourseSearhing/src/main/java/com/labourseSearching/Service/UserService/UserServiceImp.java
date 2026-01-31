package com.labourseSearching.Service.UserService;

import com.labourseSearching.DTO.UserDto.*;
import com.labourseSearching.Entity.ENUM.AuthProvider;
import com.labourseSearching.Entity.ENUM.UserType;
import com.labourseSearching.Entity.Users.User;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.InvalidPasswordException;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.UserNotFoudnException;
import com.labourseSearching.ModerlerMapper.UsermodelMapper;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import com.labourseSearching.Security.JwtServices.JwtService;
import com.labourseSearching.Service.S3ServiceImage.S3Service;
import jakarta.validation.Valid;
import org.springframework.boot.security.autoconfigure.SecurityProperties;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsermodelMapper userModelMapper;
    private final JwtService jwtService;
    private final S3Service s3Service;

    public UserServiceImp(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UsermodelMapper userModelMapper,
            JwtService jwtService,
            S3Service s3Service
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userModelMapper = userModelMapper;
        this.jwtService = jwtService;

        this.s3Service = s3Service;
    }

    // =========================================================
    // SIGNUP (AUTO LOGIN)
    // =========================================================
    @Override
    public UserEmailLoninResponseDto emailAuthSingIn(
            @Valid EmailAuthSingInRequestDto request
    ) {

        System.out.println("Email Sing in service hit start  ");

        Optional<User> existingOpt =
                userRepository.findByEmail(request.getEmail());

        // 🔁 USER EXISTS → LOGIN FLOW
        if (existingOpt.isPresent()) {
            System.out.println("Sing in time user mil gya db be ab sidhe login kr ");
            EmailAuthLonginRequestDto loginRequest =
                    new EmailAuthLonginRequestDto();

            loginRequest.setEmail(request.getEmail());
            loginRequest.setPassword(request.getPassword());

            return emailAuthLogin(loginRequest);
        }

        // 🆕 FRESH SIGNUP
        User newUser = userModelMapper.toSingInUser(request);
        User savedUser = userRepository.save(newUser);

        String token = jwtService.generateToken(
                savedUser.getId(),
                savedUser.getEmail()
        );

        System.out.println("sing in end ");
        // ❗ Fresh signup → profile never exists
        return userModelMapper.toResponce(
                savedUser,
                token,
                false
        );
    }

    // =========================================================
    // LOGIN
    // =========================================================
    @Override
    public UserEmailLoninResponseDto emailAuthLogin(
            EmailAuthLonginRequestDto request
    ) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found, please signup"
                        )
                );

        // 🚫 Google users cannot login with password
        if (user.getAuthProvider() == AuthProvider.GOOGLE) {
            throw new InvalidPasswordException(
                    "This account uses Google login"
            );
        }

        // 🔐 Password validation
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail()
        );

        // ✅ PROFILE EXISTENCE CHECK (ONLY HERE)
        boolean profileExists =
                user.getLabourProfile() != null ||
                        user.getCustomerProfile() != null;

        return userModelMapper.toResponce(
                user,
                token,
                profileExists
        );
    }

    // =========================================================
    // MY PROFILE
    // =========================================================
    @Override
    public UserAuthStateDto showMyProfile(long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoudnException("User not found")
                );
        UserAuthStateDto  userAuthStateDto = new UserAuthStateDto() ;
        userAuthStateDto.setUserType(user.getUserType());
        userAuthStateDto.setProfileExists(false);
        userAuthStateDto.setUserId(id);
        return userAuthStateDto ;
    }

    // UPLOAD PROFILE IMAGE
    public String uploadProfilePhoto(Long userId, MultipartFile file) {

        User user = userRepository.findById(userId).orElseThrow();
        if (user.getProfileImageUrl() != null) {
            s3Service.delete(user.getProfileImageUrl());
        }
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        // optional validation
        if (!file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only images allowed");
        }
//        if(file.size () > 5 MB  ) {
//            throw  new MaxUploadSizeExceededException ( )
//        }


        String imageUrl = s3Service.upload(file);


        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }

    @Override
    public UserTypeSelectResponceDto setUserType(long userId, UserType userType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        if (user.getUserType() != null) {
            throw new IllegalStateException("User type already selected");
        }

        // ✅ SET FIRST
        user.setUserType(userType);
        userRepository.save(user);

        // ✅ MAP AFTER SAVE
        return userModelMapper.userRoleSetAtLoginOrSingInFirstTime(user);
    }

}