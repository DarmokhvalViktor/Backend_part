package com.darmokhval.Backend_part.config;

import com.darmokhval.Backend_part.config.jwt.AuthEntryPointJwt;
import com.darmokhval.Backend_part.config.jwt.AuthTokenFilter;
import com.darmokhval.Backend_part.config.jwt.JwtUtils;
import com.darmokhval.Backend_part.repository.UserRepository;
import com.darmokhval.Backend_part.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableMethodSecurity - this annotation to provide security to method-level security in application, websecurity-all application
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private CustomUserDetailsService customUserDetailsService;
    private AuthEntryPointJwt unauthorizedHandler;

//    added recently to resolve problem with null in AuthTokenFilter authenticationJWTTokenFilter
    private JwtUtils jwtUtils;


    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, AuthEntryPointJwt authEntryPointJwt
                          ,JwtUtils jwtUtils
    ) {
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = authEntryPointJwt;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public AuthTokenFilter authenticationJWTTokenFilter(
            JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService
    ) {
        return new AuthTokenFilter(
                jwtUtils, customUserDetailsService
        );
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    This method tells Spring Security how to configure CORS and CSRF, when we want
//    to require all users to be authenticated or not, which filter (AuthTokenFilter)
//    and when we want it to work (filter before UsernamePasswordAuthenticationFilter),
//    which Exception Handler is chosen (AuthEntryPointJwt).
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**", "/swagger-config", "/docs", "/test/**").permitAll()
//                        auth.requestMatchers("/auth/**").permitAll()
//                                .requestMatchers("/swagger-config").permitAll()
//                                .requestMatchers("/docs").permitAll()
                                .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJWTTokenFilter(
                jwtUtils, customUserDetailsService
        ), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



//    @Bean
//    public static PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(UserRepository userRepository, PasswordEncoder passwordEncoder){
//        return new CustomAuthenticationManager(userRepository, passwordEncoder);
//    }
//
////    TODO Authorization server. 2) Resource server needs tokens from auth.server
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf((csrf) -> csrf.disable())
//                .authorizeHttpRequests((authorize) ->
//                    authorize
////                            .requestMatchers(HttpMethod.GET, "/**").permitAll()
////                            .requestMatchers("/auth/admin").hasRole("ADMIN")
//                            .requestMatchers("/auth/login", "auth/signup", "auth/swagger-config").permitAll()
//                            .anyRequest().hasAnyRole("ADMIN", "USER"));
//
////                .authorizeHttpRequests((authorize) ->
////                        authorize
////                            .anyRequest().authenticated());
//        return http.build();
//
//    }
}
