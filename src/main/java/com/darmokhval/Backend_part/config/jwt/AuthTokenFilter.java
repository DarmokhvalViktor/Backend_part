package com.darmokhval.Backend_part.config.jwt;


import com.darmokhval.Backend_part.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Let’s define a filter that executes once per request.
 * So we create AuthTokenFilter class that extends OncePerRequestFilter and override doFilterInternal() method.
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private CustomUserDetailsService customUserDetailsService;
    private final static Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * What we do inside doFilterInternal():
     * get JWT from the Authorization header (by removing Bearer prefix)
     * if the request has JWT, validate it, parse username from it
     * from username, get UserDetails to create an Authentication object
     * set the current UserDetails in SecurityContext using setAuthentication(authentication) method
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try {
            String token = parseJwt(request);
//            Should add to validateJwtToken cause if token is refresh?
            if(token != null && !jwtUtils.isRefreshToken(token) && jwtUtils.validateJwtToken(token)) {
                String username = jwtUtils.getUserNameFromJwtToken(token);
//                TODO invoking when signup? looking for a user in database, why? Wanted to only register. Not sure if the error is here
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);

//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
    /**
     * After this, everytime you want to get UserDetails, just use SecurityContext like this:
     *      * UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     */

    /**
     * Extracting token from request
     * @param request
     * @return token or null;
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
