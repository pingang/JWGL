package com.xupt.controller;

import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import com.xupt.exception.IllegalInputException;
import com.xupt.exception.SpeedException;
import com.xupt.service.CollegeService;
import com.xupt.service.StuService;
import com.xupt.service.TeachService;
import com.xupt.util.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;

/**
 *  该处理器负责登录相关业务
 */
@RestController
public class LoginController {



    @Autowired
    private StuService stuService;

    @Autowired
    private TeachService teachService;

    @Autowired
    private CollegeService collegeService;

    //该接口作用是进行登录
    @RequestMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public Object login(HttpSession session, String num, String pwd) throws SpeedException, IllegalInputException {

        //输入不合法
        IsLegalInputUtil.toJudgingInput(num,pwd);

        String MD5pwd = MD5Util.getMD5(pwd);

        JSONObject jsonObject;

        //学生登录
        if(num.length() == 8) {
            Student student = stuService.getStuByNumAndPwd(num, MD5pwd);
            if (student != null) {

                session.setAttribute("stuNum",num);
                System.out.println(num);
                return ObjectToJSON.toJson(student);
            }
        }
        //老师登录
        else if(num.length() == 6){
            Teacher teacher = teachService.getTeachByNumAndPwd(num,MD5pwd);
            if(teacher != null) {

                // 判断是不是教务主任
                if(!StringUtils.isEmpty(collegeService.getRegistrar(teacher.getTeachNum()))){
                    session.setAttribute("registrarNum",teacher.getTeachNum());
                }
                session.setAttribute("teachNum",num);

                return ObjectToJSON.toJson(teacher);
            }
        }

        return "{\"errCode\":2,\"errMsg\":\"用户名或密码错误！\"}";

    }


    @RequestMapping(value = "/exit",produces = {"application/json;charset=UTF-8"})
    public Object exit(HttpSession session){
        // 清除会话信息
        session.invalidate();
        return "{\"errCode\":0,\"errMsg\":\"退出成功！\"}";
    }


    /* ===============================   以下代码负责修改密码   ==================================*/

    /*
            流程：
                1、发送验证码
                2、检验验证码
                3、确认密码
     */

    //该接口作用是进行验证码发送
    @RequestMapping(value = {"/getValidCode"},produces = {"text/json;charset=UTF-8"} )
    public String sendVaildCode(HttpSession session, String tel) throws IllegalInputException {

        System.out.println(tel);

        IsLegalInputUtil.toJudgingInput(tel);

        //手机号码的正则表达式
        String regtel = "1[345789]\\d{9}";
        if(!tel.matches(regtel)){
            return "{\"errCode\":1,\"errMsg\":\"请输入正确的电话号码\"}";
        }

        // 防止有人多次发送验证码
        Jedis jedis = RedisCacheUtil.getJedis();
        if(!jedis.exists(tel)){
            jedis.setex(tel, 60, "");
        }else{
            return "{\"errCode\":2,\"errMsg\":\"你操作的太频繁了！\"}";
        }


        String s = SendVaildCodeUtil.sendVaildCode(tel).substring(1, 7);
        session.setAttribute("validCode",s);
        session.setAttribute("tel",tel);

        session.setMaxInactiveInterval(5*60);
        return "{\"errCode\":0,\"errMsg\":\"发送成功\"}";
    }

    //该接口作用是进行检验验证码
    @RequestMapping(value = "/checkValidCode",produces = {"application/json;charset=UTF-8"})
    public Object checkValidCode(HttpSession session, String validCode) throws SpeedException, IllegalInputException {

        IsLegalInputUtil.toJudgingInput(validCode);

        String code = (String)session.getAttribute("validCode");
        if(!validCode.equals(code)){
            return "{\"errCode\":1,\"errMsg\":\"验证码不正确！\"}";
        }

        session.removeAttribute("validCode");
        return "{\"errCode\":0,\"errMsg\":\"验证码正确！\"}";

    }

    //该接口作用是进行密码确认并修改
    @RequestMapping(value = "/enSurePwd",produces = {"application/json;charset=UTF-8"})
    public String changePwd(HttpSession session, String pwd1, String pwd2) throws SpeedException, IllegalInputException {

        IsLegalInputUtil.toJudgingInput(pwd1,pwd2);

        if(!pwd1.equals(pwd2)){
            return "{\"errCode\":2,\"errMsg\":\"密码不一致！\"}";
        }
        String tel = (String)session.getAttribute("tel");
        if (StringUtils.isEmpty(tel)){
            return "{\"errCode\":3,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        // 在老师表根据电话号码修改
        int result = teachService.updatePwd(tel, MD5Util.getMD5(pwd1));
        if(result > 0){
            return "{\"errCode\":0,\"errMsg\":\"修改成功！\"}";
        }
        result = stuService.updatePwd(tel, MD5Util.getMD5(pwd1));

        // 在学生表根据电话号码修改
        if(result > 0){
            return "{\"errCode\":0,\"errMsg\":\"修改成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"修改失败！\"}";

    }


    /* ==================================================================================*/

}
