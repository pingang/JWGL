package com.xupt.serviceImpl;

import com.xupt.bean.Course;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import com.xupt.dao.TeachMapper;
import com.xupt.service.TeachService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachServiceImpl implements TeachService {

    @Autowired
    private TeachMapper mapper;


    @Override
    public Teacher getTeachByNumAndPwd(String num, String md5pwd) {
        return mapper.loginByNumPwd(num, md5pwd);
    }


    @Override
    public int updatePwd(String tel, String md5) {
        return mapper.updatePwd(tel,md5);
    }

    @Override
    public int addTeachers(List<Teacher> list) {
       return mapper.insertTeachers(list);
    }

    @Override
    public List<Teacher> getTeach(String teachNum,String teachName) {
        return mapper.selectTeachByTeachNumOrTeachName(teachNum,teachName);
    }

    @Override
    public List<Teacher> getTeachByCollegeName(String collegeName,String collegeNum) {
        return mapper.selectTeachByCollegeNum(collegeName,collegeNum);
    }

    @Override
    public int updateScore(String stuNum, String courseNum,String score, String teachNum) {
        return mapper.updateScore(stuNum,courseNum,score,teachNum);
    }

    @Override
    public List<Student> getStuOfTeach(String year, String semester, String teachNum) {
        return mapper.selectStuByTeach(year,semester,teachNum);
    }

    @Override
    public int updateTelNum(String teachNum, String tel) {
        return mapper.updateTel(teachNum,tel);
    }

    @Override
    public List<String> getClassName(Integer year, Integer semester,Integer courseNum,String teachNum) {
        return mapper.selectClassName(year,semester,courseNum,teachNum);
    }

    @Override
    public List<Course> getCourseName(Integer year, Integer semester, String teachNum) {
        return mapper.selectCourseName(year,semester,teachNum);
    }

    @Override
    public int updateTeachCollege(String teachNum, String collegeNum) {
        return mapper.updateTeachCollege(teachNum,collegeNum);
    }
}
