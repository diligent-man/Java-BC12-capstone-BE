package com.ndt.capstone.filter;

import java.util.List;

import java.io.IOException;


import com.ndt.capstone.service.LoginAttemptServiceImpl;
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


import com.ndt.capstone.service.JwtServiceImpl;


@Service
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtService;

    private final LoginAttemptServiceImpl loginAttemptService;


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
        // 1. Kiểm tra JWT hợp lệ và còn hạn không
        if (!jwtService.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"code\":\"401\",\"status\":\"Phiên đăng nhập đã kết thúc, vui lòng đăng nhập lại\"}");
            return;
        }
        // 2. JWT hợp lệ -> Kiểm tra session trong Redis
        Claims claims = jwtService.extractClaims(token);
        String email = claims.get("email", String.class);
        String activeToken = loginAttemptService.getActiveSession(email);
        if (activeToken == null || !token.equals(activeToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"code\":\"401\",\"status\":\"Phiên đăng nhập đã kết thúc, vui lòng đăng nhập lại\"}");
            return;
        }
        // 3. Hợp lệ cả 2 -> Cấp quyền đi tiếp
        Long userId = Long.parseLong(claims.getSubject());
        String role = claims.get("role", String.class);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
