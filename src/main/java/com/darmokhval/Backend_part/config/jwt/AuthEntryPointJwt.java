package com.darmokhval.Backend_part.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


    /**
     *  HttpServletResponse.SC_UNAUTHORIZED is the 401 Status code. It indicates that the request requires HTTP authentication.
     *     If you want to customize the response data, just use an ObjectMapper like following code:
     *     @Override
     *     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
     *             throws IOException, ServletException {
     *         logger.error("Unauthorized error: {}", authException.getMessage());
     *         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
     *     }
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        if(authException.getCause() instanceof SignatureException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT signature");
        }
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("path", request.getServletPath());
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
