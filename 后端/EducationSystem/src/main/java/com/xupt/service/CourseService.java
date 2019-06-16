package com.xupt.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.xupt.bean.Course;

import java.text.ParseException;
import java.util.List;

public interface CourseService {

    String getCourseNum(String courseName);

    void addTimeTable(List<Course> list) throws Exception;

    int addOptionalCourse(List<Course> list);

    Integer getOptionalId(Course course);

    int addOptionalCourseTable(List<Course> list) throws ParseException;

    List<Course> getOptionalCourse(String year, String semester);

    int addOptionTableToStu(String stuNum, Integer optionalId) throws Exception;

    int addCourse(List<Course> list);
}
