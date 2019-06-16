package com.xupt.controller;

import com.xupt.bean.*;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.*;
import com.xupt.util.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * 该处理器负责插入数据
 */
@RestController
public class InsertController {

    // 默认密码
    private static final String DEFAULT_PASSEORD = "21218c922c33ca7780e01511054d2ba1";

    @Autowired
    private StuService stuService;

    @Autowired
    private TeachService teachService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private SemesterService semesterService;


    /**
     *  该接口负责从xls文件中读取学生、老师、班级信息并插入到数据库中
     *
     *  学生xls文件格式： 学号 姓名 专业名称 班级名称 身份证号 民族 电话
     *
     *  老师xls文件格式： 工号 电话 姓名 学院代号 身份证号
     *
     */
    @RequestMapping(value = {"/excelStu","/excelTeach"},produces = {"application/json;charset=UTF-8"})
    public String excelStu(HttpServletRequest request, MultipartFile myFile) throws IOException {

        List<String[]> excelData = ExcelData.getExcelData(myFile);
        System.out.println(excelData.size());

        if(excelData.size() == 0)
            return "{\"errCode\":1,\"errMsg\":\"请不要输入空文件！\"}";


        if(request.getRequestURI().endsWith("excelStu")){
            List<Student> list = new ArrayList<>();
            for(String[] array: excelData){

                IsLegalInputUtil.toJudgingInput(array[0],array[1],array[2],array[3]);

                if(array[0].length()!=8){
                    return "{\"errCode\":5,\"errMsg\":\"请输入合法的学号！\"}";
                }
                int size = stuService.getStu(array[0], "").size();
                if(size == 0){
                    return "{\"errCode\":6,\"errMsg\":\"该学号已存在！\"}";
                }

                // 为学生赋值
                Student student = new Student();
                student.setStuNum(array[0]);
                student.setStuName(array[1]);

                Integer majorId = majorService.getMajorIdByMajorName(array[2]);
                if(majorId == null){
                    return "{\"errCode\":2,\"errMsg\":\"不存在名字为"+array[2]+"的专业！\"}";
                }
                student.setMajorId(majorId);
                student.setClassName(array[3]);


                if(array.length == 7) {
                    if (!StringUtils.isEmpty(array[4]) && array[4].length() != 18) {
                        return "{\"errCode\":3,\"errMsg\":\"身份证不合法:" + array[4] + "\"}";
                    }
                    student.setIdCard(array[4]);
                    student.setNation(array[5]);
                    if (!StringUtils.isEmpty(array[6])) {
                        //手机号码的正则表达式
                        String regtel = "1[345789]\\d{9}";
                        if (!array[6].matches(regtel)) {
                            return "{\"errCode\":4,\"errMsg\":\"请输入正确的电话号码:" + array[6] + "\"}";
                        }
                    }
                    student.setTelNum(array[6]);
                }
                student.setPassword(DEFAULT_PASSEORD);
                MsgFromIdCardUtil.setMsg(student);
                list.add(student);
            }
            stuService.addStudents(list);

        }else if (request.getRequestURI().endsWith("excelTeach")){
            List<Teacher> list = new ArrayList<>();
            for(String string[] : excelData){
                // 所excel文件中读取信息赋值给老师
                Teacher teacher = new Teacher();
                teacher.setTeachNum(string[0]);
                teacher.setTelNum(string[1]);
                teacher.setTeachName(string[2]);
                String collegeNum = collegeService.getCollegeNum(string[3]);
                if(StringUtils.isEmpty(collegeNum)){
                    return "{\"errCode\":2,\"errMsg\":\"不存在名字为"+string[3]+"的学院！\"}";
                }
                teacher.setCollegeNum(collegeNum);
                teacher.setIdCard(string[4]);
                teacher.setPassword(DEFAULT_PASSEORD);
                MsgFromIdCardUtil.setMsg(teacher);
                list.add(teacher);

            }
            teachService.addTeachers(list);
        }

        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }




    /**
     *  该接口负责从网页中输入信息，对学生信息进行插入
     *
     *  需输入的学生信息： 学号 姓名 专业名称 班级名称 身份证号 民族 电话
     *
     */
    @RequestMapping(value = {"/dataStu"},produces = {"application/json;charset=UTF-8"})
    public String dataStu(Student student) throws IOException {

        IsLegalInputUtil.toJudgingInput(student.getStuNum(),student.getStuName(),student.getMajorName(),student.getClassName());

        if(student.getStuNum().length() != 8){
            return "{\"errCode\":2,\"errMsg\":\"学号必须是8位！\"}";
        }

        // 判断该学号有没有被注册
        if(stuService.getStu(student.getStuNum(), "").size() != 0){
            return "{\"errCode\":3,\"errMsg\":\"学号"+student.getStuNum()+"已被注册！\"}";
        }

        if(student.getIdCard() != null && student.getIdCard().length()!=18){
            return "{\"errCode\":4,\"errMsg\":\"请输入合法的身份证号码专业！\"}";
        }

        if(student.getTelNum() != null){
            //手机号码的正则表达式
            String regtel = "1[345789]\\d{9}";
            if(!student.getTelNum().matches(regtel)){
                return "{\"errCode\":5,\"errMsg\":\"请输入正确的电话号码\"}";
            }
        }

        student.setPassword(DEFAULT_PASSEORD);


        Integer majorId = majorService.getMajorIdByMajorName(student.getMajorName());
        if(majorId == null){
            return "{\"errCode\":6,\"errMsg\":\"不存在名字为"+student.getMajorName()+"的专业！\"}";
        }
        student.setMajorId(majorId);
        MsgFromIdCardUtil.setMsg(student);

        if(stuService.addStudents(Arrays.asList(student)) > 0)
            return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
        return "{\"errCode\":1,\"errMsg\":\"添加失败！\"}";

    }


    // 已经过redis测试
    /**
     *  该接口负责从网页中输入信息，对老师信息进行插入
     *
     *  需输入的老师信息： 工号 电话 姓名 学院名称 身份证号
     *
     */
    @RequestMapping(value = {"/dataTeach"},produces = {"application/json;charset=UTF-8"})
    public String dataTeach(Teacher teacher) throws IOException {

        IsLegalInputUtil.toJudgingInput(teacher.getTeachNum(),teacher.getTeachName(),teacher.getTeachName());

        if(teacher.getTeachNum().length()!=6){
            return "{\"errCode\":2,\"errMsg\":\"工号长度必须为6！\"}";
        }

        if(teacher.getTelNum() != null){
            //手机号码的正则表达式
            String regtel = "1[345789]\\d{9}";
            if(!teacher.getTelNum().matches(regtel)){
                return "{\"errCode\":3,\"errMsg\":\"请输入正确的电话号码！\"}";
            }
        }


        teacher.setPassword(DEFAULT_PASSEORD);
        String collegeNum = collegeService.getCollegeNum(teacher.getCollegeName());
        if(StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":4,\"errMsg\":\"不存在名字为"+teacher.getCollegeName()+"的学院！\"}";
        }
        teacher.setCollegeNum(collegeNum);
        MsgFromIdCardUtil.setMsg(teacher);
        List<Teacher> lists = Arrays.asList(teacher);
        if(teachService.addTeachers(lists) > 0)
            return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
        return "{\"errCode\":1,\"errMsg\":\"添加失败！\"}";

    }

    /**
     *  该接口负责从网页中输入信息，对专业信息进行插入
     *
     *  需输入的专业信息： 专业代号 专业名称 学院名称
     */
    @RequestMapping(value = {"/dataMajor"},produces = {"application/json;charset=UTF-8"})
    public String dataMajor(Major major) throws IOException {

        IsLegalInputUtil.toJudgingInput(major.getMajorName(),major.getMajorNum(),major.getCollegeName());

        String collegeNum = collegeService.getCollegeNum(major.getCollegeName());
        if(StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":2,\"errMsg\":\"不存在名字为"+major.getCollegeName()+"的学院！\"}";
        }

        Integer r = majorService.getMajorIdByMajorName(major.getMajorName());
        if(r != null){
            return "{\"errCode\":3,\"errMsg\":\"该专业已被添加！\"}";
        }


        if(majorService.addMajor(major) > 0)
            return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
        return "{\"errCode\":1,\"errMsg\":\"添加失败！\"}";

    }


    // 已经过redis测试
    /**
     *  该接口负责从网页中输入信息，对学院信息进行插入
     *
     *  需输入的专业信息：学院代号 学院名称
     */
    @RequestMapping(value = {"/dataCollege"},produces = {"application/json;charset=UTF-8"})
    public String dataCollege(College college) throws IOException {

        IsLegalInputUtil.toJudgingInput(college.getCollegeName(),college.getCollegeNum(),college.getTeachNum());

        if(college.getTeachNum().length()!=6){
            return "{\"errCode\":2,\"errMsg\":\"工号长度必须为6！\"}";
        }

        String collegeNum = collegeService.getCollegeNum(college.getCollegeName());
        if(!StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":3,\"errMsg\":\""+college.getCollegeName()+"学院已存在！\"}";
        }

        int count = collegeService.getCollegeNumHadExist(college.getCollegeNum());
        if(count > 0){
            return "{\"errCode\":4,\"errMsg\":\""+college.getCollegeNum()+"学院代号已存在！\"}";
        }

        int result = collegeService.addCollege(college);
        if(result > 0){
            return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"添加失败！\"}";
    }




    // 已经过redis测试
    /**
     * 该接口负责教务主任通过输入插入课程
     *
     *
     * 课程名字  学分   是否选修    所属学院    课时
     */
    @RequestMapping(value = {"/addCourseByInput"},produces = {"application/json;charset=UTF-8"})
    public String addCourseByInput(Course course) throws IOException, IllegalInputException {

        IsLegalInputUtil.toJudgingInput(course.getCourseName(),course.getCredit()+"",course.getElective()+"",course.getCourseName(),course.getCourseHour()+"");

        String collegeNum = collegeService.getCollegeNum(course.getCollegeName());

        if(StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":2,\"errMsg\":\"不存在名为"+course.getCourseName()+"的学院！\"}";
        }

        course.setCollegeNum(collegeNum);
        courseService.addCourse(Arrays.asList(course));

        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }


    /**
     * 该接口负责教务主任通过文件插入课程
     *
     *
     * 课程名字  学分   是否选修    所属学院    课时
     */
    @RequestMapping(value = {"/addCourseByExcel"},produces = {"application/json;charset=UTF-8"})
    public String addCourseByExcel(MultipartFile myFile) throws IOException {

        List<String[]> excelData = ExcelData.getExcelData(myFile);
        if(excelData.size() == 0)
            return "{\"errCode\":1,\"errMsg\":\"请不要输入空文件！\"}";
        List<Course> list = new ArrayList<>();
        for(String[] strs : excelData){
            Course course = new Course();
            course.setCourseName(strs[0]);
            course.setCredit(Integer.parseInt(strs[1]));
            // 判断是否选修课
            if("是".equals(strs[2])) {
                course.setElective(0);
            }else{
                course.setElective(1);
            }
            String collegeNum = collegeService.getCollegeNum(strs[3]);
            if(StringUtils.isEmpty(collegeNum)){
                return "{\"errCode\":2,\"errMsg\":\"不存在名为"+strs[3]+"的学院！\"}";
            }
            course.setCollegeNum(collegeNum);
            course.setCourseHour(Integer.parseInt(strs[4]));
            list.add(course);
        }

       // 将课程添加到课程表
       courseService.addCourse(list);

       return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }



    /**
     * 该接口负责教务主任通过文件插入必修课表业务
     *
     * 教务主任按照班级添加课表
     *
     * 课程名字 老师编号  班级名  周几  地点  开始时间   结束时间   年份  学期
     */
    @RequestMapping(value = {"/addTimeTable"},produces = {"application/json;charset=UTF-8"})
    public String addTimeTable(MultipartFile myFile) throws Exception {
        List<String[]> excelData = ExcelData.getExcelData(myFile);
        if(excelData.size() == 0)
            return "{\"errCode\":1,\"errMsg\":\"请不要输入空文件！\"}";

        Set<String> courseNameSet = new HashSet<>();
        Set<String> teachNumSet = new HashSet<>();
        Set<String> classNameSet = new HashSet<>();
        for(String[] strs : excelData){
            courseNameSet.add(strs[0]);
            teachNumSet.add(strs[1]);
            classNameSet.add(strs[2]);
        }

        Map<String,Integer> courseMap = new HashMap<>();
        for(String courseName : courseNameSet) {
            String courseNum = courseService.getCourseNum(courseName);
            if(courseNum == null){
                return "{\"errCode\":2,\"errMsg\":\"不存在课程名为"+courseName+"的课程！\"}";
            }
            courseMap.put(courseName,Integer.parseInt(courseNum));
        }

        // 查询数据库中是否存在该老师编号
        for(String teachNum : teachNumSet){
            if(teachService.getTeach(teachNum,"").size() == 0){
                return "{\"errCode\":3,\"errMsg\":\"不存在工号为"+teachNum+"的老师！\"}";
            }
        }

        // 查询数据库中是否存在该班级名
        for(String className : classNameSet){
            if(classService.getClassName(className) == null){
                return "{\"errCode\":4,\"errMsg\":\"不存在班级名为"+className+"的班级！\"}";
            }
        }

        // 往学生课表中添加课表
        List<Course> list = new ArrayList<>();
        for(String[] strs : excelData){
            // 获得该班级所有人学号
            List<String> stuNumList = stuService.getStuNumByClassName(strs[2]);

            for(String stuNum : stuNumList) {
                // 给每个人课表添加记录
                Course course = new Course();
                course.setStuNum(stuNum);
                course.setTeachNum(strs[1]);
                course.setCourseNum(courseMap.get(strs[0]));
                course.setWeekday(Integer.parseInt(strs[3]));
                course.setAddress(strs[4]);
                course.setStartTime(strs[5].split(" ")[1]);
                course.setEndTime(strs[6].split(" ")[1]);
                course.setYear(Integer.parseInt(strs[7]));
                course.setSemester(Integer.parseInt(strs[8]));
                list.add(course);
            }
        }


        // 该学生添加课表
        courseService.addTimeTable(list);

        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }


    /**
     * 该接口负责教务主任通过输入插入必修课表业务
     *
     * 教务主任按照班级添加课表
     *
     * 课程名字 老师编号  班级名  周几  地点  开始时间   结束时间   年份  学期
     */
    @RequestMapping(value = {"/addTimeTableByInput"},produces = {"application/json;charset=UTF-8"})
    public String addTimeTableByInput(Course course) throws Exception {

        IsLegalInputUtil.toJudgingInput(course.getClassName(),course.getTeachNum(),course.getClassName(),course.getWeekday()+"",course.getStartTime(),course.getEndTime(),course.getYear()+"",course.getSemester()+"");

        String courseNum = courseService.getCourseNum(course.getCourseName());

        
        if(courseNum == null){
            return "{\"errCode\":1,\"errMsg\":\"不存在课程名为"+course.getCourseName()+"的课程！\"}";
        }
        course.setCourseNum(Integer.parseInt(courseNum));

        Teacher teacher = teachService.getTeach(course.getTeachNum(), "").get(0);
        if(teacher == null){
            return "{\"errCode\":2,\"errMsg\":\"不存在工号为"+course.getCourseNum()+"的老师！\"}";
        }

        if(classService.getClassName(course.getClassName()) == null) {
            return "{\"errCode\":3,\"errMsg\":\"不存在班级名为" + course.getClassName() + "的班级！\"}";
        }

        // 获得该班级所有人的学号
        List<String> stuNumList = stuService.getStuNumByClassName(course.getClassName());

        List<Course> list = new ArrayList<>();


        for(String stuNum : stuNumList){

            Course c = new Course();
            c.setStuNum(stuNum);
            c.setTeachNum(course.getTeachNum());
            c.setCourseNum(course.getCourseNum());
            c.setWeekday(course.getWeekday());
            c.setAddress(course.getAddress());
            c.setStartTime(course.getStartTime());
            c.setEndTime(course.getEndTime());
            c.setYear(course.getYear());
            c.setSemester(course.getSemester());

            list.add(c);
        }


        // 给学生添加课表
        courseService.addTimeTable(list);

        return "{\"errCode\":0,\"errMsg\":\"添加成功！\"}";
    }



}
