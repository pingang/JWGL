package com.xupt.service.serviceImpl;

import com.xupt.bean.Course;
import com.xupt.dao.SemesterMapper;
import com.xupt.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterMapper semesterMapper;

    @Override
    public int addSemester(String year, String semster, String startDate, String endDate) {
        return semesterMapper.insertSemester(year,semster,startDate,endDate);
    }

    @Override
    public int updateSemester(String year, String semster, String startDate, String endDate) {
        return semesterMapper.updateSemester(year,semster,startDate,endDate);
    }

    @Override
    public Course getSemesterDate(Integer year, Integer semester) {
        return semesterMapper.selSemesterDate(year,semester);
    }


}
