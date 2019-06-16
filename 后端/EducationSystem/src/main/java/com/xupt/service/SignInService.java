package com.xupt.service;

public interface SignInService {
    int addSignInTime(String num, int state, String dateTime,int courseNum);

    Integer getHadSignIn(String stuNum, Integer courseNum,String nowDate);

    Integer getCourseNumNow(String stuNum, String courseStartTime, int weekday, String year, int semester);
}
