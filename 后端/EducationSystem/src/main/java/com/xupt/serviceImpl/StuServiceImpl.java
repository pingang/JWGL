package com.xupt.serviceImpl;

import com.xupt.bean.Course;
import com.xupt.bean.SignIn;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import com.xupt.dao.StuMapper;
import com.xupt.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper mapper;


    @Override
    public Student getStuByNumAndPwd(String num, String md5pwd) {
        return mapper.loginByNumAndPwd(num, md5pwd);
    }

    @Override
    public int addStudents(List<Student> lists) {
        return mapper.insertStudents(lists);
    }

    @Override
    public int updatePwd(String tel, String md5) {
        return mapper.updatePwd(tel,md5);
    }

    @Override
    public List<Student> getStu(String stuNum,String stuName) {
        return mapper.selectStuByStuNumOrStuName(stuNum,stuName);
    }

    @Override
    public List<Student> getStusByClassName(String className) {
        return mapper.selectStuByClassName(className);
    }

    @Override
    public List<Student> getStusByMajorName(String majorName) {
        return mapper.selectStuByMajorName(majorName);
    }

    @Override
    public List<Student> getStusByCollegeName(String collegeName,String collgeNum) {
        return mapper.selectStuByCollegeName(collegeName,collgeNum);
    }

    @Override
    public List<Course> getCourse(String stuNum, String year, String semester) {
        return mapper.selectCourse(stuNum,year,semester);
    }

    @Override
    public List<Course> getScore(String stuNum, String year, String semester) {
        return mapper.selectScore(stuNum,year,semester);
    }

    @Override
    public List<String> getStuNumByClassName(String str) {
        return mapper.selStuNum(str);
    }

    @Override
    public int updateInformation(Student student) {
        return mapper.updateInformation(student);
    }

    @Override
    public int updateTelNum(String stuNum, String tel) {
        return mapper.updateTel(stuNum,tel);
    }

    @Override
    public List<SignIn> getCheckInTable(String stuNum,String startDate,String endDate) {
        return mapper.selectCheckInTable(stuNum,startDate,endDate);
    }

    @Override
    public List<Course> getTeachOfStu(String stuNum, String year, int semester) {
        return mapper.selectTeachOfStu(stuNum,year,semester);
    }

    @Override
    public int addTeachScore(String stuNum, String teachNum, int score,String comment,String year, int semester) {
        return mapper.insertTeachScore(stuNum,teachNum,score,comment,year,semester);
    }

    @Override
    public int getHadGiveScore(String stuNum, String teachNum, String year, int semester) {
        return mapper.selectHadGiveScore(stuNum,teachNum,year,semester);
    }

    @Override
    public int deleteStu(String stuNum) {
        return mapper.deleteStu(stuNum);
    }

}
