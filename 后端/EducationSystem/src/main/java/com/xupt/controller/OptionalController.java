package com.xupt.controller;

import com.xupt.bean.Course;
import com.xupt.bean.Teacher;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.CourseService;
import com.xupt.service.TeachService;
import com.xupt.util.ExcelData;
import com.xupt.util.IsLegalInputUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  该处理器负责选修课相关业务
 */
@Controller
public class OptionalController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeachService teachService;

    /**
     * 通过输入插入选修课
     *
     * 课程名、老师工号、人数、年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/insertOptionalCourseByInput", produces = {"application/json;charset=UTF-8"})
    public String insertOptionalCourseByInput(Course course) throws IllegalInputException {

        IsLegalInputUtil.toJudgingInput(course.getCourseName(),course.getTeachNum(),course.getNumber()+"",course.getYear()+"",course.getSemester()+"");

        // 判断课程是否在课程表中
        String courseNum = courseService.getCourseNum(course.getCourseName());
        if(courseNum == null){
            return "{\"errCode\":1,\"errMsg\":\"抱歉，课程表中不存在名为"+course.getCourseName()+"的课程！\"}";
        }
        course.setCourseNum(Integer.parseInt(courseNum));
        // 判断老师是否存在于老师表
        List<Teacher> list = teachService.getTeach(course.getTeachNum(), "");
        if(list.size() == 0){
            return "{\"errCode\":2,\"errMsg\":\"抱歉，不存在工号为"+course.getTeachNum()+"的老师！\"}";
        }

        // 往选课表插入课程记录
        int r = courseService.addOptionalCourse(Arrays.asList(course));
        if(r > 0){
            return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
        }
        return "{\"errCode\":3,\"errMsg\":\"添加失败！\"}";
    }

    /**
     * 通过文件插入选修课
     *
     * 课程名、老师工号、人数、年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/insertOptionalCourseByExcel", produces = {"application/json;charset=UTF-8"})
    public String insertOptionalCourseByExcel(MultipartFile myFile) throws IllegalInputException, IOException {
        List<String[]> excelData = ExcelData.getExcelData(myFile);
        if(excelData.size() == 0)
            return "{\"errCode\":1,\"errMsg\":\"请不要输入空文件！\"}";

        List<Course> list = new ArrayList<>();
        for(String[] strings : excelData){
            Course course = new Course();
            // 判断课程是否在课程表中
            String courseNum = courseService.getCourseNum(strings[0]);
            if(courseNum == null){
                return "{\"errCode\":2,\"errMsg\":\"抱歉，课程表中不存在名为"+strings[0]+"的课程！\"}";
            }
            course.setCourseNum(Integer.parseInt(courseNum));
            // 判断老师是否存在于老师表
            List<Teacher> teach = teachService.getTeach(strings[1], "");
            if(teach.size() == 0){
                return "{\"errCode\":3,\"errMsg\":\"抱歉，不存在工号为"+strings[1]+"的老师！\"}";
            }
            course.setTeachNum(strings[1]);
            course.setNumber(Integer.parseInt(strings[2]));
            course.setYear(Integer.parseInt(strings[3]));
            course.setSemester(Integer.parseInt(strings[4]));
            list.add(course);
        }

        courseService.addOptionalCourse(list);

        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }


    /**
     * 通过输入插入选修课的课表
     *
     * 课程名、老师工号、年份、学期、周几、上课地址、开始时间、结束时间
     */
    @ResponseBody
    @RequestMapping(value = "/insertOptionalCourseTableByInput", produces = {"application/json;charset=UTF-8"})
    public String insertOptionalCourseTableByInput(Course course) throws IllegalInputException, ParseException {


        IsLegalInputUtil.toJudgingInput(course.getCourseName(),course.getTeachNum(),course.getYear()+"",course.getSemester()+"",course.getWeekday()+"",course.getAddress(),course.getStartTime(),course.getEndTime());

        String courseNum = courseService.getCourseNum(course.getCourseName());
        if(courseNum == null){
            return "{\"errCode\":1,\"errMsg\":\"不存在名为"+course.getCourseName()+"的选课！\"}";
        }
        course.setCourseNum(Integer.parseInt(courseNum));
        // 判断选修课是否被添加到选课表
        Integer optionalId = courseService.getOptionalId(course);
        if(optionalId == null){
            return "{\"errCode\":2,\"errMsg\":\"不存在课程名为"+course.getCourseName()+"老师工号为"+course.getTeachNum()+"的选课！\"}";
        }
        course.setOptionalId(optionalId);
        // 插入选课表
        courseService.addOptionalCourseTable(Arrays.asList(course));
        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }

    /**
     * 通过文件插入选修课的课表
     *
     * 课程名、老师工号、年份、学期、周几、上课地址、开始时间、结束时间
     */
    @ResponseBody
    @RequestMapping(value = "/insertOptionalCourseTableByExcel", produces = {"application/json;charset=UTF-8"})
    public String insertOptionalCourseTableByExcel(MultipartFile myFile) throws IllegalInputException, IOException, ParseException {

        List<String[]> excelData = ExcelData.getExcelData(myFile);
        if(excelData.size() == 0)
            return "{\"errCode\":1,\"errMsg\":\"请不要输入空文件！\"}";

        List<Course> list = new ArrayList<>();
        for(String[] strings : excelData){
            Course course = new Course();
            course.setCourseName(strings[0]);
            course.setTeachNum(strings[1]);
            course.setYear(Integer.parseInt(strings[2]));
            course.setSemester(Integer.parseInt(strings[3]));
            course.setWeekday(Integer.parseInt(strings[4]));
            course.setAddress(strings[5]);
            course.setStartTime(strings[6]);
            course.setEndTime(strings[7]);

            String courseNum = courseService.getCourseNum(course.getCourseName());
            if(courseNum == null){
                return "{\"errCode\":2,\"errMsg\":\"不存在名为"+course.getCourseName()+"的选课！\"}";
            }
            course.setCourseNum(Integer.parseInt(courseNum));
            // 判断选修课是否被添加到选课表
            Integer optionalId = courseService.getOptionalId(course);
            if(optionalId == null){
                return "{\"errCode\":3,\"errMsg\":\"不存在课程名为"+course.getCourseName()+"老师工号为"+course.getTeachNum()+"的选课！\"}";
            }
            course.setOptionalId(optionalId);
            list.add(course);
        }


        courseService.addOptionalCourseTable(list);

        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }


    /**
     * 查询可选修的课
     *
     * 年份、学期
     */
    @ResponseBody
    @RequestMapping(value = "/getOptionalCourse", produces = {"application/json;charset=UTF-8"})
    public String getOptionalCourse(String year,String semester) throws IllegalInputException, IOException {

        IsLegalInputUtil.toJudgingInput(year,semester);

        // 查询所有可以选修的课
        List<Course> list = courseService.getOptionalCourse(year,semester);

        JSONArray json = new JSONArray(list);
        return json.toString();
    }


    /**
     * 学生开始进行选课
     */
    @ResponseBody
    @RequestMapping(value = "/startToOption", produces = {"application/json;charset=UTF-8"})
    public String startToOption(HttpSession session,String stuNum, Integer optionalId) throws Exception {

        IsLegalInputUtil.toJudgingInput(optionalId+"");

        /*String stuNum = (String)session.getAttribute("stuNum");
        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的身份已过期！\"}";
        }*/

        int r = courseService.addOptionTableToStu(stuNum,optionalId);
        if(r > 0){
            return "{\"errCode\":0,\"errMsg\":\"选课成功！\"}";
        }
        return "{\"errCode\":2,\"errMsg\":\"选课失败！\"}";
    }


}
