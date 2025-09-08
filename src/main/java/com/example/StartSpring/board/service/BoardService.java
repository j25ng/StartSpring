package com.example.StartSpring.board.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.StartSpring.board.bean.BoardBean;
import com.example.StartSpring.board.dao.BoardDao;

@Component
public class BoardService {
    @Autowired
    private BoardDao boardDao;

    public Map<String, Object> insertBoard(BoardBean bean) {
        Map<String, Object> data = new HashMap<>();

        if (!StringUtils.hasText(bean.getTitle()) ||
                !StringUtils.hasText(bean.getContent()) ||
                !StringUtils.hasText(bean.getWriter())) {
            data.put("result", "false");
            data.put("resultMsg", "필수 항목이 누락되었습니다.");
            return data;
        }

        try {
            int row = boardDao.insertBoard(bean);

            if (row > 0) {
                data.put("result", "ok");
                data.put("resultMsg", "게시글이 등록되었습니다.");
            } else {
                data.put("result", "false");
                data.put("resultMsg", "게시글 등록에 실패했습니다.");
            }
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                data.put("result", "false");
                data.put("resultMsg", "존재하지 않는 사용자 ID입니다.");
            } else {
                data.put("result", "false");
                data.put("resultMsg", "데이터베이스 오류가 발생했습니다: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("result", "false");
            data.put("resultMsg", "게시글 등록 중 오류가 발생했습니다." + e.getMessage());
        }

        return data;
    }

    public Map<String, Object> updateBoard(BoardBean bean) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", "fail");
        data.put("resultMsg", "update failed");

        try {
            int row = boardDao.updateBoard(bean);

            if (row > 0) {
                data.put("result", "ok");
                data.put("resultMsg", "update success");
            } else {
                data.put("result", "fail");
                data.put("resultMsg", "your not authorized");
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("result", "fail");
            data.put("resultMsg", "update failed: " + e.getMessage());
        }

        return data;

    }
}
