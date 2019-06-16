package com.xupt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.bean.*;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.CourseService;
import com.xupt.service.MajorService;
import com.xupt.service.StuService;
import com.xupt.service.TeachService;
import com.xupt.util.IsLegalInputUtil;
import com.xupt.util.RedisCacheUtil;
import org.json.JSONArray;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 该处理器负责信息查询
 */
@Controller
public class SelectController {

    @Autowired
    private StuService stuService;

    @Autowired
    private TeachService teachService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private CourseService courseService;


    /**
     * 根据学号或姓名查询学生
     * <p>
     * 输入学号或姓名
     */
    @ResponseBody
    @RequestMapping(value = "/selStu", produces = {"application/json;charset=UTF-8"})
    public Object getStuMsg(String stuNum, String stuName) throws IllegalInputException {

        if (StringUtils.isEmpty(stuNum) && StringUtils.isEmpty(stuName)) {
            return "{\"errCode\":1,\"errMsg\":\"至少输入一个值！\"}";
        }

        List<Student> lists = stuService.getStu(stuNum, stuName);

        return lists;
    }

    /**
     * 根据工号或姓名查询老师
     * <p>
     * 输入工号或姓名
     */
    @ResponseBody
    @RequestMapping(value = "/selTeach", produces = {"application/json;charset=UTF-8"})
    public Object getTeachMsg(String teachNum, String teachName) throws IllegalInputException {

        if (StringUtils.isEmpty(teachNum) && StringUtils.isEmpty(teachName)) {
            return "{\"errCode\":1,\"errMsg\":\"至少输入一个值！\"}";
        }

        List<Teacher> teacher = teachService.getTeach(teachNum, teachName);

        return teacher;
    }

    /**
     * 查询某一个专业的所有人
     * <p>
     * 输入专业名称
     */
    @ResponseBody
    @RequestMapping(value = "/selStuByMajorName", produces = {"application/json;charset=UTF-8"})
    public PageInfo getStudentsByMajorName(String majorName, Integer pn) throws IllegalInputException {

        IsLegalInputUtil.toJudgingInput(majorName);

        PageHelper.startPage(pn,9);
        List<Student> list = stuService.getStusByMajorName(majorName);
        PageInfo page = new PageInfo(list,10);

        return page;

    }

    /**
     * 查询某一个学院的所有人
     * <p>
     * 输入学院名称
     */
    @ResponseBody
    @RequestMapping(value = "/selStuByCollegeNameOrCollegeNum", produces = {"application/json;charset=UTF-8"})
    public Object getStudentsByCollegeName(String collegeName,String collegeNum, Integer pn) throws IllegalInputException {

        if(StringUtils.isEmpty(collegeName) && StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":1,\"errMsg\":\"至少输入一个值！\"}";
        }

        PageHelper.startPage(pn,9);
        List<Student> list = stuService.getStusByCollegeName(collegeName,collegeNum);
        PageInfo page = new PageInfo(list,10);

        return page;
    }

    /**
     * 查询某一学院的所有老师
     * <p>
     * 输入学院名称
     */
    @ResponseBody
    @RequestMapping(value = "/selTeachByCollegeNameOrCollegeNum", produces = {"application/json;charset=UTF-8"})
    public Object getTeachsByCollegeName(String collegeName,String collegeNum) throws IllegalInputException {

        if(StringUtils.isEmpty(collegeName) && StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":1,\"errMsg\":\"至少输入一个值！\"}";
        }

        List<Teacher> list = teachService.getTeachByCollegeName(collegeName,collegeNum);

        return list;
    }

    /**
     * 查询某一学院的所有专业
     * <p>
     * 输入学院名称
     */
    @ResponseBody
    @RequestMapping(value = "/selMajorByCollegeNameOrCollegeNum", produces = {"application/json;charset=UTF-8"})
    public Object getMajorsByCollegeName(String collegeName,String collegeNum) throws IllegalInputException {

        if(StringUtils.isEmpty(collegeName) && StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":1,\"errMsg\":\"至少输入一个值！\"}";
        }

        List<Major> list = majorService.getMajorsByCollegeName(collegeName,collegeNum);

        return list;
    }


    /**
     * 查询学生的课表
     * <p>
     * 输入学号、年份、第几学期
     */
    @ResponseBody
    @RequestMapping(value = "/selCourse", produces = {"application/json;charset=UTF-8"})
    public Object getCourse(HttpSession session,String stuNum, String year, String semester) throws IllegalInputException {

        IsLegalInputUtil.toJudgingInput(stuNum, year, semester);


        //String stuNum = (String)session.getAttribute("stuNum");
        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        List<Course> list = stuService.getCourse(stuNum, year, semester);

        return list;
    }

    /**
     * 学生查询成绩
     * <p>
     * 输入学号、年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/selScore", produces = {"application/json;charset=UTF-8"})
    public String getScore(HttpSession session, String year, String semester) throws IllegalInputException {

        IsLegalInputUtil.toJudgingInput(year, semester);


        String stuNum = (String)session.getAttribute("stuNum");
        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        List<Course> list = stuService.getScore(stuNum, year, semester);

        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();
    }


    /**
     * 老师查询选了自己所教的课的学生
     * <p>
     * 输入年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/teachSelStu", produces = {"application/json;charset=UTF-8"})
    public Object teachSelStu(HttpSession session,String teachNum,String year, String semester) throws IllegalInputException {

        //String teachNum = (String)session.getAttribute("teachNum");
        if(StringUtils.isEmpty(teachNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        IsLegalInputUtil.toJudgingInput(year,semester);

        List<Student> list = teachService.getStuOfTeach(year,semester,teachNum);

        return list;
    }

    @Test
    public void test(){
        Jedis jedis = RedisCacheUtil.getJedis();
        jedis.flushDB();
    }

}
