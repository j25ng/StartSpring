package com.example.StartSpring.member.dao;

import com.example.StartSpring.member.bean.MemberBean;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Member;
import java.util.List;

// MemberMapper.xml 에서 매핑, 여기서는 interface로 껍데기만 생성
@Mapper
public interface MemberDao {
    public List<MemberBean> selectMemberList(MemberBean memberBean);

    // insert는 성공(1), 실패(0) 값이 리턴되기 떄문에 return type int
    public int insertUser(MemberBean memberBean);

    public int updateUser(MemberBean memberBean);

    public int deleteUser(MemberBean memberBean);
}
