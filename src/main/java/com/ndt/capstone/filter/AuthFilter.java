package com.ndt.capstone.filter;

import java.util.List;

import java.io.IOException;

import com.ndt.capstone.service.LoginAttemptService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;


import io.jsonwebtoken.Claims;


import org.jspecify.annotations.NonNull;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import com.ndt.capstone.service.JwtService;


@Service
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (jwtService.isTokenValid(token)) {
            Claims claims = jwtService.extractClaims(token);

            String email = claims.get("email", String.class);

            // ===== KIỂM TRA SINGLE SESSION =====
            String activeToken = loginAttemptService.getActiveSession(email);

            if (activeToken == null || !token.equals(activeToken)) {
                // Session không tồn tại hoặc token không khớp → phiên không hợp lệ
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(
                        "{\"code\":\"401\",\"status\":\"Session invalid, please login again\"}"
                );
                return; // KHÔNG cho đi tiếp
            }
            // ===== KẾT THÚC KIỂM TRA =====

            Integer userId = Integer.parseInt(claims.getSubject());
            String role = claims.get("role", String.class);

            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
