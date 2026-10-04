package com.ndt.capstone.exception.security;


import java.io.IOException;
import java.nio.charset.StandardCharsets;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import com.ndt.capstone.payload.resp.exception.AuthErrorResponse;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;


    @Override
    public void commence(
        @NonNull HttpServletRequest req,
        HttpServletResponse res,
        @NonNull AuthenticationException ex
    ) throws IOException {
        Integer code = HttpStatus.UNAUTHORIZED.value();
        res.setStatus(code);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8);

        objectMapper.writeValue(
            res.getWriter(),
            AuthErrorResponse.builder()
                .code(String.valueOf(code))
                .message("Authentication is required")
                .path(req.getRequestURI())
                .build()
        );
    }
}
