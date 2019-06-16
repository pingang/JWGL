package com.xupt.service;


import com.xupt.bean.Course;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import org.json.JSONObject;

import java.util.List;

public interface TeachService {

    Teacher getTeachByNumAndPwd(String num, String md5pwd);

    int updatePwd(String tel, String md5);

    int addTeachers(List<Teacher> excelData);

    List<Teacher> getTeach(String teachNum,String teachName);

    List<Teacher> getTeachByCollegeName(String collegeName,String collegeNum);

    int updateScore(String stuNum, String courseNum,String score, String teachNum);

    List<Student> getStuOfTeach(String year, String semester, String teachNum);

    int updateTelNum(String teachNum,String tel);

    List<String> getClassName(Integer year, Integer semester,Integer courseNum,String teachNum);

    List<Course> getCourseName(Integer year, Integer semester,String teachNum);

    int updateTeachCollege(String teachNum, String collegeNum);
}
