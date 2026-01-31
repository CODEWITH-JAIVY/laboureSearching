package com.labourseSearching.Security.SecurityConfig;

import com.labourseSearching.Config.OAuth2Login.OAuth2LoginSuccessHandler;
import com.labourseSearching.Security.Jwtfiltering.JwtAuthFilter;
import com.labourseSearching.Security.RestAuthenticationEntryPoints.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(
            JwtAuthFilter jwtAuthFilter,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> {})   // 🔥 NOW Spring will use CorsConfig bean
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // ✅ Authorization rules
//                .authorizeHttpRequests(auth -> auth
//
//                        // 🔥 CORS preflight
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//                        // 🔓 Public APIs
//                        .requestMatchers(
//                                "/",
//                                "/login",
//                                "/signin",
//                                "/signup",
//                                "/oauth2/**",
//                                "/user/sing/email",
//                                "/user/Login"
//                        ).permitAll()
//
//                        // 🔐 Protected APIs
//                        .requestMatchers(
//                                "/customer/**",
//                                "/laboure/**"
//                        ).authenticated()
//                        .requestMatchers("/meta/**",
//                                "/labour/profile",
//                                "/customer/profile").permitAll()
//
//                        .anyRequest().authenticated()
//                )
//
//                // ✅ OAuth2 login
//                .oauth2Login(oauth2 ->
//                        oauth2.successHandler(oAuth2LoginSuccessHandler)
//                )
//
//                // ✅ JWT filter
//                .addFilterBefore(
//                        jwtAuthFilter,
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }
    RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint() ;
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 🔥 IMPORTANT PART
            .exceptionHandling(ex ->
                    ex.authenticationEntryPoint(restAuthenticationEntryPoint)
            )

            .authorizeHttpRequests(auth -> auth

                    // Preflight
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // OAuth only for login
                    .requestMatchers(
                            "/oauth2/**",
                            "/login/oauth2/**"
                    ).permitAll()

                    // Public APIs
                    .requestMatchers(
                            "/user/login",
                            "/user/sing/email",
                            "/meta/**"
                    ).permitAll()

                    // 🔐 API = JWT ONLY (NO OAUTH REDIRECT)
                    .requestMatchers(
                            "/labour/**",
                            "/customer/**",
                            "/user/**"
                    ).authenticated()

                    .anyRequest().authenticated()
            )

            // ❌ OAuth2 ONLY FOR BROWSER LOGIN
            .oauth2Login(oauth ->
                    oauth.successHandler(oAuth2LoginSuccessHandler)
            )

            .addFilterBefore(
                    jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

    return http.build();
}

}