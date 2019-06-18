package com.xupt.service.serviceImpl;

import com.xupt.bean.College;
import com.xupt.dao.CollegeMapper;
import com.xupt.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public int addCollege(College college) {
        return collegeMapper.insertCollege(college);
    }

    @Override
    public String getCollegeNum(String collegeName) {
        return collegeMapper.selectCollegeNum(collegeName);
    }

    @Override
    public String getRegistrar(String teachNum) {
        return collegeMapper.selectRegistrar(teachNum);
    }

    @Override
    public int getCollegeNumHadExist(String collegeNum) {
        return collegeMapper.selectCollegeNumHadExist(collegeNum);
    }
}
