package com.gamelist.auth_service.service.impl;

import com.gamelist.auth_service.config.JwtProperties;
import com.gamelist.auth_service.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtProperties properties;

    @Override
    public String generateToken(long userId, String email, List<String> roles) {
        long expirationTimeInMs = 1000L * 60 * 60 * 24 * 60;
        //        return Jwts.builder()
        //                .subject(String.valueOf(userId))
        //                .claim("email", email)
        //                .claim("roles", roles)
        //                .issuedAt(new Date(System.currentTimeMillis()))
        //                .expiration(new Date(System.currentTimeMillis() + expirationTimeInMs))
        //                .signWith(getSignKey())
        //                .compact();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
