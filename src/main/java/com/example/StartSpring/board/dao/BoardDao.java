package com.example.StartSpring.board.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.StartSpring.board.bean.BoardBean;

@Mapper
public interface BoardDao {
    public int insertBoard(BoardBean bean);

    public int updateBoard(BoardBean bean);
}
