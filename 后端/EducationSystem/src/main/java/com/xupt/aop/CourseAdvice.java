package com.xupt.aop;


import com.xupt.bean.College;
import com.xupt.bean.Course;
import com.xupt.exception.AlreadyExistsException;
import com.xupt.util.RedisCacheUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 给Course相关业务做aop功能
@Component
@Aspect
public class CourseAdvice {

    @Around("execution(* com.xupt.service.CourseService.addCourse(..))")
    public Integer addCourseAOP(ProceedingJoinPoint point) throws Throwable {
        Jedis jedis = RedisCacheUtil.getJedis();

        // 这一这里不可以强转ArrayList
        List<Course> list = (List)point.getArgs()[0];

        for(Course course : list){
            String courseNum = jedis.get(course.getCourseName());
            if(!StringUtils.isEmpty(courseNum)) {
                jedis.close();
                throw new AlreadyExistsException(course.getCourseName() + "已存在");
            }
        }
        int result = (Integer)point.proceed();
        for(Course course : list){
            jedis.set(course.getCourseName(),course.getCourseNum()+"");
        }
        jedis.close();
        return result;
    }

    @Around("execution(* com.xupt.service.CourseService.getCourseNum(..))")
    public String getCourseNumAOP(ProceedingJoinPoint point) throws Throwable {
        Jedis jedis = RedisCacheUtil.getJedis();
        String courseName = (String)point.getArgs()[0];
        String courseNum = jedis.get(courseName);
        if(StringUtils.isEmpty(courseNum)){
            courseNum = (String)point.proceed();
            if(!StringUtils.isEmpty(courseNum)){
                jedis.set(courseName,courseNum);
            }
        }
        return courseNum;
    }

}
