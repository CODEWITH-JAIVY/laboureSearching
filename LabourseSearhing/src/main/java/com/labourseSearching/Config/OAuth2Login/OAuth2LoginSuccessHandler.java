package com.labourseSearching.Config.OAuth2Login;

import com.labourseSearching.Entity.ENUM.AuthProvider;
import com.labourseSearching.Entity.Users.User;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import com.labourseSearching.Security.JwtServices.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public OAuth2LoginSuccessHandler(
            UserRepository userRepository,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        User user = userRepository.findByEmail(email)
                .orElseGet(User::new);

        user.setEmail(email);
        user.setName(name);
        user.setProfileImageUrl(picture);
        user.setAuthProvider(AuthProvider.GOOGLE);

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail()
        );

        String redirectUrl =
                frontendUrl + "/oauth-success?token=" + token;

        System.out.println("OAuth Redirect → " + redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}