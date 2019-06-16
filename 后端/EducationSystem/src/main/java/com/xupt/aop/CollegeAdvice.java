package com.xupt.aop;

import com.xupt.bean.College;
import com.xupt.exception.AlreadyExistsException;
import com.xupt.util.RedisCacheUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

// 给College相关业务做aop功能
@Component
@Aspect
public class CollegeAdvice {

    @Around("execution(* com.xupt.serviceImpl.CollegeServiceImpl.addCollege(..))")
    public int addCollegeAOP(ProceedingJoinPoint point) throws Throwable {

        Jedis jedis = RedisCacheUtil.getJedis();
        College college = (College) point.getArgs()[0];
        String collegeNum = jedis.get(college.getCollegeName());

        // 缓存中存在
        if(!StringUtils.isEmpty(collegeNum)){
            jedis.close();
            throw new AlreadyExistsException(college.getCollegeName()+"已存在");
        }

        int result = 0;
        if (StringUtils.isEmpty(collegeNum)){
                result = (Integer)point.proceed();
            if(result > 0){
                jedis.set(college.getCollegeName(),college.getCollegeNum());
            }
        }
        jedis.close();
        return result;
    }

    @Around("execution(* com.xupt.serviceImpl.CollegeServiceImpl.getCollegeNum(..))")
    public String getCollegeNumAOP(ProceedingJoinPoint point) throws Throwable {

        Jedis jedis = RedisCacheUtil.getJedis();
        String collegeName = (String) point.getArgs()[0];
        String collegeNum = jedis.get(collegeName);


        if(StringUtils.isEmpty(collegeNum)){
            collegeNum = (String)point.proceed();
            if(!StringUtils.isEmpty(collegeNum)){
                jedis.set(collegeName,collegeNum);
            }else{
                collegeNum = null;
            }
        }

        jedis.close();
        return collegeNum;

    }

}
