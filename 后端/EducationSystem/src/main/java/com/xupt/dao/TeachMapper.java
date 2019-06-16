package com.xupt.dao;

import com.xupt.bean.Course;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeachMapper {
    Teacher loginByNumPwd(String num, String md5pwd);

    int insertTeachers(List<Teacher> list);

    int updatePwd(String tel, String md5);

    List<Teacher> selectTeachByTeachNumOrTeachName(@Param("teachNum") String teachNum,@Param("teachName") String teachName);

    List<Teacher> selectTeachByCollegeNum(@Param("collegeName") String collegeName,@Param("collegeNum") String collegeNum);

    int updateScore(String stuNum,String courseNum, String score, String teachNum);

    List<Student> selectStuByTeach(String year, String semester, String teachNum);

    int updateTel(String teachNum, String tel);

    List<String> selectClassName(Integer year, Integer semester,Integer courseNum,String teachNum);

    List<Course> selectCourseName(Integer year, Integer semester, String teachNum);

    int updateTeachCollege(String teachNum, String collegeNum);
}
