package com.xupt.service.serviceImpl;

import com.xupt.dao.ClassMapper;
import com.xupt.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public String getClassName(String className) {
        return classMapper.selClass(className);
    }
}
