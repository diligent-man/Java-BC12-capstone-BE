package com.ndt.capstone.utils.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import tools.jackson.databind.ObjectMapper;


import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import com.ndt.capstone.payload.response.exception.AuthErrorResponse;


@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;


    @Override
    public void handle(
        @NonNull HttpServletRequest req,
        HttpServletResponse res,
        @NonNull AccessDeniedException ex
    ) throws IOException {
        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8);

        objectMapper.writeValue(
            res.getWriter(),
            AuthErrorResponse.builder()
                .code(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .message("Access is denied")
                .path(req.getRequestURI())
                .build()
        );
    }
}
