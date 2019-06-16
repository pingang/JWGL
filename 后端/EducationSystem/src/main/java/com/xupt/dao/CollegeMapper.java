package com.xupt.dao;

import com.xupt.bean.College;

public interface CollegeMapper {
    int insertCollege(College college);

    String selectCollegeNum(String s);

    String selectRegistrar(String teachNum);

    int selectCollegeNumHadExist(String collegeNum);
}
