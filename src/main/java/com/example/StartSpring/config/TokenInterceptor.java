package com.example.StartSpring.config;

import java.io.PrintWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.StartSpring.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 1. request에서 token 값 가져오기
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("authToken: " + authToken);

        if (authToken == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");

            PrintWriter writer = response.getWriter();
            String jsonMsg = "{\n" +
                    "  \"result\": \"fail\",\n" +
                    "  \"resultMsg\": \"유효한 토큰값이 아닙니다.\"\n" +
                    "}";
            writer.write(jsonMsg);
            writer.flush();
            writer.close();
            return false;
        }

        try {
            authToken = authToken.replace("Bearer ", "");
            Jws<Claims> claimsJws = JwtUtil.verifyToken(authToken);
            String userId = (String) claimsJws.getBody().get("userId");
            String password = (String) claimsJws.getBody().get("password");
            System.out.println("userId: " + userId);
            System.out.println("password: " + password);
        } catch (ExpiredJwtException eje) {
            eje.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
