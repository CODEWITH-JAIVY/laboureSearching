package com.labourseSearching.Security.Jwtfiltering;

import com.labourseSearching.Entity.Users.User;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import com.labourseSearching.Security.JwtServices.JwtService;
import com.labourseSearching.Security.principal.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // BYPASS PUBLIC ENDPOINTS
        String path = request.getServletPath();

        if (path.startsWith("/auth/")
                || path.startsWith("/oauth2/")
                || path.startsWith("/login")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        //  READ AUTH HEADER
        String header = request.getHeader("Authorization");
        System.out.println("Header  of the user " +  header );
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //  EXTRACT TOKEN
        String token = header.substring(7);
        System.out.println("Token of the user " + token );
        // VALIDATE TOKEN
        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //  EXTRACT USER
        String email = jwtService.extractEmail(token);
        User dbUser = (User) userRepository.findByEmail(email).orElse(null);

        //  SET SECURITY CONTEXT
        if (dbUser != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserPrincipal principal =
                    new UserPrincipal(
                            dbUser.getId(),
                            dbUser.getEmail(),
                            dbUser.getPassword()
                    );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            principal.getAuthorities()
                    );
            //===================================
            //********************************
            //       TESTING PERPOSE
            System.out.println("Id of the user is  "+ principal.getId());
//
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        //    SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
            System.out.println("JWT authenticated user id = " + principal.getId()) ;

        }

        filterChain.doFilter(request, response);
    }
}