package com.xupt.service;

import com.xupt.bean.Course;

public interface SemesterService {
    int addSemester(String year, String semester, String startDate, String endDate);

    int updateSemester(String year, String semester, String startDate, String endDate);

    Course getSemesterDate(Integer year, Integer semester);
}
