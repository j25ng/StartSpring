package com.example.StartSpring.config;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.StartSpring.member.bean.MemberBean;
import com.example.StartSpring.member.service.MemberService;
import com.example.StartSpring.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 1. request에서 token 값 가져오기
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authToken == null) {
            sendMsg(response, "유효한 토큰값이 아닙니다.");
            return false;
        }

        try {
            authToken = authToken.replace("Bearer ", "");
            Jws<Claims> claimsJws = JwtUtil.verifyToken(authToken);
            String email = (String) claimsJws.getBody().get("email");
            String password = (String) claimsJws.getBody().get("password");
            System.out.println("email: " + email + ", password: " + password);

            MemberBean pMemBean = new MemberBean();
            pMemBean.setEmail(email);
            pMemBean.setPassword(password);
            MemberBean rtnBean = memberService.selectLoginMember(pMemBean);

            if (rtnBean != null) {
                // 세션객체에 저장해서 controller에서 사용할 수 있도록 처리
                request.getSession().setAttribute("userId", rtnBean.getId());
                return true;
            }

            sendMsg(response, "일치하는 회원정보가 없습니다.");

        } catch (ExpiredJwtException eje) {
            eje.printStackTrace();
            sendMsg(response, "토큰이 만료되었습니다.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            sendMsg(response, "올바른 토큰값이 아닙니다.");
            return false;
        }

        return false;
    }

    private void sendMsg(HttpServletResponse response, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            PrintWriter writer = response.getWriter();
            String jsonMsg = "{\n" +
                    "  \"result\": \"fail\",\n" +
                    "  \"resultMsg\": \"" + msg + "\"\n" +
                    "}";
            writer.write(jsonMsg);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
