package com.example.StartSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StartSpring.board.bean.BoardBean;
import com.example.StartSpring.board.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/board/writeBoard")
    public Map<String, Object> writeBoard(BoardBean bean) {
        return boardService.insertBoard(bean);
    }

    @PostMapping("/board/updateBoard")
    public Map<String, Object> updateBoard(HttpServletRequest request, BoardBean bean) {
        String userId = (String) request.getSession().getAttribute("userId");
        System.out.println("userId: " + userId);

        bean.setWriter(userId);
        return boardService.updateBoard(bean);
    }
}
