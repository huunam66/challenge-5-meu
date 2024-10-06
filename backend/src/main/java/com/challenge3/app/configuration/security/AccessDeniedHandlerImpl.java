package com.challenge3.app.configuration.security;

import com.challenge3.app.common.response.ResponseAPI;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
        Json.pretty(
                new ResponseAPI(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage())
        ));

    }
}
