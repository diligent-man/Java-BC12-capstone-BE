package com.ndt.capstone.service;

import java.util.Date;

import javax.crypto.SecretKey;


import io.jsonwebtoken.*;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

import io.jsonwebtoken.security.Keys;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import com.ndt.capstone.dto.UserDto;


@Service
public class JwtService {
    @Value(value = "${jwt.secret:}")
    private String secretKey;

    @Value(value = "${jwt.expiration:600000}")
    private long accessExpiration;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }


    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }


    public String genAccessToken(UserDto user) {
        try {
            return Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRoleName())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getSigningKey())
                .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }


    public static String genSecretKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        return Encoders.BASE64.encode(key.getEncoded());
    }


}
