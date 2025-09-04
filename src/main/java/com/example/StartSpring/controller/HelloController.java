package com.example.StartSpring.controller;

import com.example.StartSpring.board.service.BoardService;
import com.example.StartSpring.member.bean.MemberBean;
import com.example.StartSpring.member.service.MemberService;
import com.example.StartSpring.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/getUsers")
    private Map<String, Object> getUsers(MemberBean bean) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "fail");
        data.put("resultMsg", "process failed");

        try {
            Integer.parseInt(bean.getPage());
            try {
                List<MemberBean> list = memberService.getUserList(bean);
                data.put("memberList", list);
                data.put("result", "ok");
                data.put("resultMsg", "process successes");
            } catch (Exception e) {
                e.printStackTrace();
                data.put("resultMsg", "process failed : " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            data.put("resultMsg", "page is not INTEGER");
            return data;
        }

        return data;
    }

    @PostMapping("/addUser")
    private Map<String, Object> addUser(MemberBean bean) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "fail");
        data.put("resultMsg", "user insert failed");

        try {
            boolean res = memberService.insertUser(bean);
            if (res) {
                data.put("result", "ok");
                data.put("resultMsg", "user insert successes");

                String authToken = JwtUtil.createToken(bean.getId(), bean.getPassword());
                data.put("authToken", authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("resultMsg", "user insert failed : " + e.getMessage());
        }

        return data;
    }

    @PostMapping("/updateUser")
    private Map<String, Object> updateUser(MemberBean bean) {
        return memberService.updateUser(bean);
    }

    @PostMapping("/deleteUser")
    private Map<String, Object> deleteUser(MemberBean bean) {
        return memberService.deleteUser(bean);
    }
}
