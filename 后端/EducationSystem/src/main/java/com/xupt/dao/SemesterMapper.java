package com.xupt.dao;

import com.xupt.bean.Course;
import org.apache.ibatis.annotations.Param;

public interface SemesterMapper {
    int insertSemester(String year, String semester, String startDate, String endDate);

    int updateSemester(String year, String semseter, String startDate, String endDate);

    Course selSemesterDate(Integer year, Integer semester);
}
