package com.xupt.dao;

import com.xupt.bean.Major;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MajorMapper {
    int insertMajor(Major major);

    List<Major> selectMajorByCollegeName(@Param("collegeName") String collegeName,@Param("collegeNum") String collegeNum);

    Integer selectMajorIdByName(String s);
}
