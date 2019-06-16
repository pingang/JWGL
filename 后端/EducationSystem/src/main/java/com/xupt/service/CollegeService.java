package com.xupt.service;

import com.xupt.bean.College;

public interface CollegeService {
    int addCollege(College college);

    String getCollegeNum(String s);

    String getRegistrar(String teachNum);

    int getCollegeNumHadExist(String collegeNum);
}
