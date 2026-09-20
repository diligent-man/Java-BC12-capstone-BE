package com.ndt.capstone.service.contract;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Encoders;

import com.ndt.capstone.dto.UserDto;


public interface JwtService {
    Claims extractClaims(String token);


    boolean isTokenValid(String token);


    String genAccessToken(UserDto user);


    String genAccessToken(UserDto user, long expiration);


    static String genSecretKey() {
        return Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded());
    }
}
