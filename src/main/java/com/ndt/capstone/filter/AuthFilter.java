package com.ndt.capstone.filter;

import java.util.List;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;


import org.springframework.security.authentication.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;


import com.ndt.capstone.service.JwtServiceImpl;
import com.ndt.capstone.enums.exception.AuthErrMsg;
import com.ndt.capstone.exception.auth.AuthException;
import com.ndt.capstone.service.contract.LoginAttemptService;


@Service
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtService;

    private final LoginAttemptService loginAttemptService;


    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest req,
        @NonNull HttpServletResponse resp,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(req, resp);
            return;
        }

        String token = authHeader.substring(7);
        try {
            // can throw on bad signature / expired / malformed
            Claims claims = jwtService.extractClaims(token);

            // check Redis session
            String email = claims.get("email", String.class);
            String activeToken = loginAttemptService.getActiveSession(email);

            if (!token.equals(activeToken))
                throw new AuthException(AuthErrMsg.SESSION_INVALID);

            // valid auth -> set authentication
            Long userId = Long.parseLong(claims.getSubject());
            String role = claims.get("role", String.class);
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (JwtException | IllegalArgumentException | AuthenticationException e) {
            SecurityContextHolder.clearContext();
            resolver.resolveException(req, resp, null, e);
            return;
        }
        filterChain.doFilter(req, resp);
    }
}
