package com.example.StartSpring.member.service;

import com.example.StartSpring.member.bean.MemberBean;
import com.example.StartSpring.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    public List<MemberBean> getUserList(MemberBean bean) {
        return memberDao.selectMemberList(bean);
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

    public Map<String, Object> updateUser(MemberBean bean) {
        Map<String, Object> data = new HashMap<>();
        int row;

        if (StringUtils.isEmpty(bean.getId())) {
            data.put("result", "fail");
            data.put("resultMsg", "id is null");
            return data;
        }

        try {
            row = memberDao.updateUser(bean);

            if (row > 0) {
                data.put("result", "ok");
                data.put("resultMsg", "user update successes");
            } else {
                data.put("result", "fail");
                data.put("resultMsg", "user update failed");
            }
        } catch (DuplicateKeyException dke) {
            dke.printStackTrace();
            data.put("result", "fail");
            data.put("resultMsg", "duplicate error occurred");
        } catch (Exception e) {
            e.printStackTrace();
            data.put("result", "fail");
            data.put("resultMsg", "user update failed : " + e.getMessage());
        }

        return data;
    }

    public Map<String, Object> deleteUser(MemberBean bean) {
        Map<String, Object> data = new HashMap<>();
        int row;
        System.out.println(bean);

        if (StringUtils.isEmpty(bean.getId())) {
            data.put("result", "fail");
            data.put("resultMsg", "id is null");
            return data;
        }

        try {
            row = memberDao.deleteUser(bean);

            if (row > 0) {
                data.put("result", "ok");
                data.put("resultMsg", "user delete successes");
            } else {
                data.put("result", "fail");
                data.put("resultMsg", "user not found or already deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("result", "fail");
            data.put("resultMsg", "user delete failed : " + e.getMessage());
        }

        return data;
    }
}
