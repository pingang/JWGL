package com.xupt.dao;

import com.xupt.bean.Course;
import com.xupt.bean.SignIn;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuMapper {

    Student loginByNumAndPwd(String num, String md5pwd);

    int updatePwd(String tel, String md5);

    int insertStudents(List<Student> list);

    List<Student> selectStuByStuNumOrStuName(@Param("stuNum") String stuNum,@Param("stuName") String stuName);

    List<Student> selectStuByClassName(String className);

    List<Student> selectStuByMajorName(String majorName);

    List<Student> selectStuByCollegeName(@Param("collegeName") String collegeName,@Param("collegeNum") String collegeNum);

    List<Course> selectCourse(String stuNum, String year, String semester);

    List<Course> selectScore(String stuNum,String year,String semester);

    List<String> selStuNum(String str);

    int updateInformation(Student student);

    int updateTel(String stuNum, String tel);

    List<SignIn> selectCheckInTable(String stuNum, String startDate,String endDate);

    Course selCourseByTime(String num, String year, int semester, int weekday, String nowTime, String suffixDate);

    int insertStudentsToSignTable(List<Course> list);

    List<Course> selectTeachOfStu(String stuNum, String year, int semester);

    int insertTeachScore(String stuNum, String teachNum,int score,String comment, String year, int semester);

    int selectHadGiveScore(String stuNum, String teachNum, String year, int semester);

    int deleteStu(String stuNum);
}
