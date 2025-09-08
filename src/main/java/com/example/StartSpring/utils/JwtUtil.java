package com.example.StartSpring.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // HS256용 시크릿 키 생성 (서버 비밀 키)
    // private static final Key secretKey =
    // Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final String SECRET = "6ChpV3JhN5qmxWKgTTRpT9Dwfvt23K0t";
    private static final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 토큰 유효 시간 (예: 1시간)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // ✅ JWT 토큰 생성
    public static String createToken(String email, String password) {
        return Jwts.builder()
                .setSubject("accessToken")
                .setIssuer("sjw-app") // 발급자
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .claim("email", email) // 커스텀 클레임
                .claim("password", password) // 커스텀 클레임
                .signWith(secretKey) // 서명
                .compact(); // 최종 문자열 생성
    }

    // ✅ JWT 토큰 검증
    public static Jws<Claims> verifyToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 검증할 키
                .build()
                .parseClaimsJws(token); // 여기서 예외 발생 시 유효하지 않은 토큰
    }

    // ✅ 사용 예시
    public static void main(String[] args) {
        String email = "user123@example.com";

        // 1. 토큰 생성
        String jwt = createToken(email, "password123");
        System.out.println("생성된 JWT: " + jwt);

        // 2. 토큰 검증
        try {
            Jws<Claims> claims = verifyToken(jwt);
            System.out.println("토큰 유효 ✔");
            System.out.println("email: " + claims.getBody().get("email"));
            System.out.println("발급자: " + claims.getBody().getIssuer());
        } catch (JwtException e) {
            System.out.println("토큰 유효하지 않음 ❌");
            e.printStackTrace();
        }
    }
}