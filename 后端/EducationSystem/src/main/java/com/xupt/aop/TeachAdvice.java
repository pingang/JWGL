package com.xupt.aop;

import com.xupt.bean.College;
import com.xupt.bean.Teacher;
import com.xupt.exception.AlreadyExistsException;
import com.xupt.util.RedisCacheUtil;
import com.xupt.util.SerializeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

// 给Teach相关业务做aop功能
@Component
@Aspect
public class TeachAdvice {


    @Around("execution(* com.xupt.service.serviceImpl.TeachServiceImpl.addTeachers(..))")
    public int addTeachAOP(ProceedingJoinPoint point) throws Throwable {

        Jedis jedis = RedisCacheUtil.getJedis();
        List<Teacher> list = (List)point.getArgs()[0];
        for(Teacher teacher : list){
            byte[] buf = jedis.get(teacher.getTeachNum().getBytes());
            if(buf != null){
                jedis.close();
                throw new AlreadyExistsException(teacher.getCollegeNum()+"已存在！");
            }
        }
        int result = (int)point.proceed();
        for(Teacher teacher : list) {
            jedis.set(teacher.getTeachNum().getBytes(),SerializeUtil.serialize(teacher));
        }
        jedis.close();
        return result;
    }

   /* @Around("execution(* com.xupt.serviceImpl.TeachServiceImpl.getTeach(..))")
    public List<Teacher> getTeachAOP(ProceedingJoinPoint point) throws Throwable {
        String teachNum = (String)point.getArgs()[0];

        if(teachNum != null) {
            List<Teacher> list = new ArrayList<>();
            Jedis jedis = RedisCacheUtil.getJedis();
            teachNum = (String) point.getArgs()[0];
            byte[] buf = jedis.get(teachNum.getBytes());
            if (buf != null) {
                list.add((Teacher) SerializeUtil.deserialize(buf));
            } else {
                list = (List) point.proceed();
                for (Teacher teacher : list) {
                    jedis.set(teacher.getTeachNum().getBytes(), SerializeUtil.serialize(teacher));
                }
            }
            jedis.close();
            return list;
        }
        return (List) point.proceed();
    }

    @Around("execution(* com.xupt.serviceImpl.TeachServiceImpl.updateTeachCollege(..))")
    public int updateTeachCollegeAOP(ProceedingJoinPoint point) throws Throwable {

        Integer r = (Integer)point.proceed();
        if(r > 0 ){

        }

    }*/



}
