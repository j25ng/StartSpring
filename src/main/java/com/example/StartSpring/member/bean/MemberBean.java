package com.example.StartSpring.member.bean;

import lombok.Data;

@Data // lombok 자동으로 setter, getter 만들어줌
public class MemberBean {
    private String id;
    private String username;
    private String password;
    private String email;
}
