package com.xupt.service;

import com.xupt.bean.Major;

import java.util.List;

public interface MajorService {


    int addMajor(Major major);

    List<Major> getMajorsByCollegeName(String collegeName,String collegeNum);

    Integer getMajorIdByMajorName(String s);
}
