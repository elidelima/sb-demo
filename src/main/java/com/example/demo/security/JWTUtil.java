package com.example.demo.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTUtil {

    private static final String SECRET = "9A7D3F1B8E2C4D5A6F7B3C1E5D8A4B2F9C6E7D1A3F5B8E2C4D9A6F1B7C3E5D8A4B2F7C1E9A3D5B6F8E2C4D1A9F3B7E5D6A2C8E7B4F1D9C3A5F2B6E8D4A7C9E1F5B3D6A2";
    private static final long EXP_TIME_4_DAYS = 3_600_000;

    public static String generateToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXP_TIME_4_DAYS )) // 10 days
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    //later will have another method for extract username
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //method for validating token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Signature exception" + ex);
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired token signature");
        }
        return false;

    }
}
