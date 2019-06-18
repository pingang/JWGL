package com.xupt.service.serviceImpl;

import com.xupt.dao.SignInMapper;
import com.xupt.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public int addSignInTime(String num, int state, String dateTime,int courseNum) {
        return signInMapper.updateSignTime(num,state,dateTime,courseNum);
    }

    @Override
    public Integer getHadSignIn(String stuNum, Integer courseNum,String nowDate) {
        return signInMapper.selectHadSignIn(stuNum,courseNum,nowDate);
    }

    @Override
    public Integer getCourseNumNow(String stuNum, String courseStartTime, int weekday, String year, int semester) {
        return signInMapper.selectCourseNumNow(stuNum,courseStartTime,weekday,year,semester);
    }
}
