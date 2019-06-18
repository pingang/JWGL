package com.xupt.service.serviceImpl;

import com.xupt.bean.Major;
import com.xupt.dao.MajorMapper;
import com.xupt.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired()
    private MajorMapper majorMapper;

    @Override
    public int addMajor(Major major) {
        return majorMapper.insertMajor(major);
    }

    @Override
    public List<Major> getMajorsByCollegeName(String collegeName,String collegeNum) {
        return majorMapper.selectMajorByCollegeName(collegeName,collegeNum);
    }

    @Override
    public Integer getMajorIdByMajorName(String s) {
        return majorMapper.selectMajorIdByName(s);
    }
}
