package com.xupt.dao;

import com.xupt.bean.Course;

import java.util.List;

public interface CourseMapper {
    String selCourseNum(String courseName);

    int insertTimeTable(List<Course> list);

    int insertOptionalCourse(List<Course> list);

    Integer selOptionalId(Course course);

    int insertOptionalCourseTable(List<Course> list);

    List<Course> selOptionalCourse(String year, String semester);

    int updateOptionCourseNumber(Integer optionalId);

    List<Course> selOptionalTable(Integer optionalId);

    int addTableToStu(List<Course> list);

    int insertCourse(List<Course> list);

    int selectStuRepeateCourse(String stuNum, Integer optionalId);
}
