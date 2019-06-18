package com.xupt.service.serviceImpl;

import com.xupt.bean.Course;
import com.xupt.dao.CourseMapper;
import com.xupt.dao.SemesterMapper;
import com.xupt.dao.StuMapper;
import com.xupt.exception.NeedNecessaryMsgException;
import com.xupt.service.CourseService;
import com.xupt.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private SemesterMapper semesterMapper;

    @Autowired
    private StuMapper stuMapper;

    @Override
    public String getCourseNum(String courseName) {
        return courseMapper.selCourseNum(courseName);
    }

    @Override
    @Transactional
    public void addTimeTable(List<Course> list) throws Exception {

        // 将课程信息添加到学生课表
        courseMapper.insertTimeTable(list);

        // 查询开学时间
        Course course = semesterMapper.selSemesterDate(list.get(0).getYear(), list.get(0).getSemester());
        if(course == null){
            throw new NeedNecessaryMsgException("抱歉，你未录入本学期的开始时间！");
        }
        String startTime = course.getStartTime();
        String endTime = course.getEndTime();

        while (startTime.compareTo(endTime) < 0){

            for(Course course1 : list){
                course1.setDateTime(DataUtil.getTime(startTime,course1.getWeekday()-1));
            }
            startTime = DataUtil.getTime(startTime,7);
            // 根据课程时间安排签到表
            stuMapper.insertStudentsToSignTable(list);
        }

    }

    @Override
    public int addOptionalCourse(List<Course> list) {
        return courseMapper.insertOptionalCourse(list);
    }

    @Override
    public Integer getOptionalId(Course course) {
        return courseMapper.selOptionalId(course);
    }

    @Override
    public int addOptionalCourseTable(List<Course> list) throws ParseException {
        return courseMapper.insertOptionalCourseTable(list);
    }

    @Override
    public List<Course> getOptionalCourse(String year, String semester) {
        return courseMapper.selOptionalCourse(year,semester);
    }

    @Override
    @Transactional
    public int addOptionTableToStu(String stuNum, Integer optionalId) throws Exception {

        // 判断该学生有没有重复选择
        int count = courseMapper.selectStuRepeateCourse(stuNum,optionalId);
        if(count > 0){
            throw  new Exception("请不要重复选择！");
        }

        int r = courseMapper.updateOptionCourseNumber(optionalId);
        // 如果该课程没有被选完
        if(r > 0){

            // 将该课程的课表添加到学生课表
            // 查询该课程的课表
            List<Course> list = courseMapper.selOptionalTable(optionalId);

            // 判断教务主任有没有安排选课信息
            if (list.size() == 0){
                throw new NeedNecessaryMsgException("选课信息并未安排");
            }

            for(Course course : list)
                course.setStuNum(stuNum);
            courseMapper.addTableToStu(list);
            // 添加记录到签到表

            // 查询开学时间
            Course course = semesterMapper.selSemesterDate(list.get(0).getYear(), list.get(0).getSemester());
            if(course == null){
                throw new NeedNecessaryMsgException("抱歉，你未录入本学期的开始时间！");
            }
            String startTime = course.getStartTime();
            String endTime = course.getEndTime();

            while (startTime.compareTo(endTime) < 0){

                for(Course course1 : list){
                    course1.setDateTime(DataUtil.getTime(startTime,course1.getWeekday()-1));
                }
                startTime = DataUtil.getTime(startTime,7);
                // 根据课程时间安排签到表
                stuMapper.insertStudentsToSignTable(list);
            }
        }
        return r;
    }

    @Override
    public int addCourse(List<Course> list) {
        return courseMapper.insertCourse(list);
    }


}
