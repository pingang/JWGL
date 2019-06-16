package com.xupt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.bean.Course;
import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.CourseService;
import com.xupt.service.StuService;
import com.xupt.service.TeachService;
import com.xupt.util.DataUtil;
import com.xupt.util.ExcelData;
import com.xupt.util.IsLegalInputUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 该处理器负责评分
 */
@RestController
public class ScoreController {

    @Autowired
    private TeachService teachService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StuService stuService;


    /*===================================   老师给学生评分   ==========================================*/

    /**
     *  老师查询自己负责的课程
     *
     *  输入：年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/getCourseName", produces = {"application/json;charset=UTF-8"})
    public String getCourseName(HttpSession session,Integer year,Integer semester){

        String teachNum = (String)session.getAttribute("teachNum");
        if(StringUtils.isEmpty(teachNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        IsLegalInputUtil.toJudgingInput(year+"",semester+"");

        List<Course> courseNameList = teachService.getCourseName(year, semester,teachNum);

        JSONArray jsonArray = new JSONArray(courseNameList);
        return jsonArray.toString();
    }


    /**
     * 老师查询自己所带的班级有哪些
     *
     * 输入：年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/getClassName", produces = {"application/json;charset=UTF-8"})
    public Object getClassName(HttpSession session,Integer year,Integer semester,Integer courseNum){

        String teachNum = (String)session.getAttribute("teachNum");
        if(StringUtils.isEmpty(teachNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }


        IsLegalInputUtil.toJudgingInput(year+"",semester+"",courseNum+"");

        List<String> className = teachService.getClassName(year, semester,courseNum,teachNum);

        return className;

    }

    /**
     * 查询某一个班级的所有人
     * <p>
     * 输入班级名称
     */
    @ResponseBody
    @RequestMapping(value = "/selStuByClassName", produces = {"application/json;charset=UTF-8"})
    public Object getStudentsByClassName(HttpSession session, String className,@RequestParam(defaultValue = "1") Integer pn) throws IllegalInputException {

        String teachNum = (String)session.getAttribute("teachNum");
        if(StringUtils.isEmpty(teachNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        IsLegalInputUtil.toJudgingInput(className);

        PageHelper.startPage(pn,9);
        List<Student> list = stuService.getStusByClassName(className);
        PageInfo page = new PageInfo(list,10);


        return page;
    }



    /**
     * 老师给学生成绩进行评分通过xls文件
     *
     * 格式：学号、课程名字、成绩
     *
     */
    @ResponseBody
    @RequestMapping(value = "/setScoreByExcel", produces = {"application/json;charset=UTF-8"})
    public Object setScoreByExcel(HttpSession session,MultipartFile myFile) throws IllegalInputException, IOException {

        String teachNum = (String)session.getAttribute("teachNum");

        if(StringUtils.isEmpty(teachNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        List<String[]> excelData = ExcelData.getExcelData(myFile);

        for(String[] array : excelData){
            String courseNum = courseService.getCourseNum(array[1]);
            if(StringUtils.isEmpty(courseNum)){
                return "{\"errCode\":2,\"errMsg\":\"抱歉，不存在该课程！\"}";
            }
            teachService.updateScore(array[0],courseNum,array[2],teachNum);
        }

        return "{\"errCode\":0,\"errMsg\":\"评分成功！\"}";
    }

    /**
     * 老师给学生成绩进行评分通过输入
     *
     * 格式：学号、课程名字、成绩
     *
     */
    @ResponseBody
    @RequestMapping(value = "/setScoreByData", produces = {"application/json;charset=UTF-8"})
    public Object setScoreByData(HttpSession session,String stuNum,String courseName,String score) throws IllegalInputException, IOException {

        String teachNum = (String)session.getAttribute("teachNum");

        if(StringUtils.isEmpty(teachNum)){
            return "{\"errCode\":2,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        IsLegalInputUtil.toJudgingInput(stuNum,courseName,score);

        String courseNum = courseService.getCourseNum(courseName);
        if(StringUtils.isEmpty(courseNum)){
            return "{\"errCode\":3,\"errMsg\":\"抱歉，不存在该课程！\"}";
        }

        int i = teachService.updateScore(stuNum, courseNum,score, teachNum);
        if(i>0) {
            return "{\"errCode\":0,\"errMsg\":\"评分成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"评分失败！\"}";
    }




    /*===================================   学生给老师评分   ==========================================*/

    @ResponseBody
    @RequestMapping(value = "/getTeachOfStu", produces = {"application/json;charset=UTF-8"})
    public Object getTeachOfStu(HttpSession session) throws IllegalInputException, IOException {

        String stuNum = (String)session.getAttribute("stuNum");
        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":1,\"errMsg\":\"请先登录！\"}";
        }

        String nowDate = DataUtil.getNowDate();

        // 获得年月日
        String[] date = nowDate.split(" ")[0].split("-");

        int month = Integer.parseInt(date[1]);
        int semester = 1;
        // 判断当前是第几学期
        if(month >=2 && month <= 8)
            semester = 2;

        List<Course> list = stuService.getTeachOfStu(stuNum,date[0],semester);
        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/giveTeachScore", produces = {"application/json;charset=UTF-8"})
    public Object giveTeachScore(HttpSession session,String teachNum,int score,String comment) throws IllegalInputException, IOException {

        IsLegalInputUtil.toJudgingInput(teachNum,score+"");

        String stuNum = (String)session.getAttribute("stuNum");
        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":2,\"errMsg\":\"请先登录！\"}";
        }

        String nowDate = DataUtil.getNowDate();

        // 获得年月日
        String[] date = nowDate.split(" ")[0].split("-");

        int month = Integer.parseInt(date[1]);
        int semester = 1;
        // 判断当前是第几学期
        if(month >=2 && month <= 8)
            semester = 2;

        int count = stuService.getHadGiveScore(stuNum,teachNum,date[0],semester);
        if(count > 0){
            return "{\"errCode\":3,\"errMsg\":\"请不要重复评分！\"}";
        }

        int r = stuService.addTeachScore(stuNum,teachNum,score,comment,date[0],semester);
        if(r > 0) {
            return "{\"errCode\":0,\"errMsg\":\"评分成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"评分失败！\"}";
    }

}
