package com.xupt.service;


import com.xupt.bean.Course;
import com.xupt.bean.SignIn;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;

import java.util.List;

public interface StuService {

    Student getStuByNumAndPwd(String num, String md5pwd);

    int addStudents(List<Student> lists);

    int updatePwd(String tel, String md5);

    List<Student> getStu(String stuNum,String stuName);

    List<Student> getStusByClassName(String className);

    List<Student> getStusByMajorName(String majorName);

    List<Student> getStusByCollegeName(String collegeName,String collegeNum);

    List<Course> getCourse(String stuNum, String year, String semester);

    List<Course> getScore(String stuNum, String year, String semester);

    List<String> getStuNumByClassName(String str);

    int updateInformation(Student student);

    int updateTelNum(String stuNum, String tel);

    List<SignIn> getCheckInTable(String stuNum, String startDate,String endDate);

    List<Course> getTeachOfStu(String stuNum, String s, int semester);

    int addTeachScore(String stuNum, String teachNum, int score,String comment,String year, int semester);

    int getHadGiveScore(String stuNum, String teachNum, String s, int semester);

    int deleteStu(String stuNum);
}
