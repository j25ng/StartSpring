package com.example.StartSpring.member.bean;

import lombok.Data;

@Data // lombok 자동으로 setter, getter 만들어줌
public class MemberBean {
    private String id;
    private String username;
    private String password;
    private String email;

    private String page = "1";
    private int offset; // 건너뛸 갯수
    private int limit = 10; // 가져올 데이터 갯수

    public int getOffset() {
        if (page == null)
            page = "1";

        return (Integer.parseInt(page) - 1) * limit;
    }
}
