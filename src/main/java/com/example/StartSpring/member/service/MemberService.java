package com.example.StartSpring.member.service;

import com.example.StartSpring.member.bean.MemberBean;
import com.example.StartSpring.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    public List<MemberBean> getUserList() {
        return memberDao.selectMemberList(null);
    }

    // @return true: 회원가입 성공, false: 회원가입 실패
    public boolean insertUser(MemberBean bean) {
        int row = memberDao.insertUser(bean);

        if (row > 0) {
            return true;
        } else {
            return false;
        }
    }
}
