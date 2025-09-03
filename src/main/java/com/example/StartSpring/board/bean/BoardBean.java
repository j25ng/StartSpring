package com.example.StartSpring.board.bean;

import lombok.Data;

@Data
public class BoardBean {
    private String no;
    private String title;
    private String writer;
    private String content;
    private String regDt;
    private String udtDt;
}
