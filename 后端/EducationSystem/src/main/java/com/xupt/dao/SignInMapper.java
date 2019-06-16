package com.xupt.dao;

public interface SignInMapper {
    int updateSignTime(String num, int state, String dateTime,int course_num);

    Integer selectHadSignIn(String stuNum, Integer courseNum,String nowDate);

    Integer selectCourseNumNow(String stuNum, String courseStartTime, int weekday, String year, int semester);
}
