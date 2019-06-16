package com.xupt.controller;

import com.baidu.aip.face.AipFace;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xupt.bean.Student;
import com.xupt.bean.face.Face;
import com.xupt.bean.face.User;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.FaceService;
import com.xupt.service.StuService;
import com.xupt.service.TeachService;
import com.xupt.util.IsLegalInputUtil;
import com.xupt.util.MsgFromIdCardUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *  该处理器负责完善个人信息
 */
@Controller
public class PerfectInformationController {

    @Autowired
    private StuService stuService;

    @Autowired
    private TeachService teachService;

    @Autowired
    private FaceService faceService;

    @Autowired
    private AipFace aipFace;

    private ObjectMapper objectMapepr = new ObjectMapper();

    /**
     *  学生完善个人信息
     */
    @ResponseBody
    @RequestMapping(value = {"/information"},produces = {"text/json;charset=UTF-8"} )
    public Object perfectInformation(String idCard, String nation, String tel, String img, HttpSession session) throws IllegalInputException, IOException {

        IsLegalInputUtil.toJudgingInput(idCard,nation,tel,img);

        if(idCard.length()<18){
            return "{\"errCode\":6,\"errMsg\":\"身份证号码不合理！\"}";
        }

        //手机号码的正则表达式
        String regtel = "1[345789]\\d{9}";
        if(!tel.matches(regtel)){
            return "{\"errCode\":7,\"errMsg\":\"请输入正确的电话号码\"}";
        }

        String str = faceService.faceCheck(aipFace,img);
        System.out.println("人脸检查");
        System.out.println(str);
        Face face = objectMapepr.readValue(str,Face.class);
        if(!"0".equals(face.getError_code())){
            return "{\"errCode\":5,\"errMsg\":\""+face.getError_msg()+"\"}";
        }

        str = faceService.faceSearch(aipFace, img);
        System.out.println("人脸搜索");
        System.out.println(str);
        face = objectMapepr.readValue(str,Face.class);

        //如果人脸库中搜索到的话
        if("0".equals(face.getError_code())){

            List<User> list = face.getResult().getUser_list();
            for(User user : list){
                if(Double.parseDouble(user.getScore()) >= 85){
                    //说明已经进行注册了
                    System.out.println("抱歉,该人脸已存在");
                    return "{\"errCode\":2,\"errMsg\":\"抱歉,该人脸已存在\"}";
                }
            }

        }

        String stuNum = (String)session.getAttribute("stuNum");

        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":4,\"errMsg\":\"抱歉,你的信息已过期\"}";
        }

        Student student = new Student();
        student.setStuNum(stuNum);
        student.setIdCard(idCard);
        student.setNation(nation);
        student.setTelNum(tel);
        MsgFromIdCardUtil.setMsg(student);

        // 往人脸库中进行数据添加
        System.out.println("人脸添加");
        str = faceService.faceAdd(aipFace, img, stuNum);
        face = objectMapepr.readValue(str,Face.class);
        System.out.println(str);
        //人脸库添加人脸失败
        if(!"0".equals(face.getError_code())){
            return "{\"errCode\":3,\"errMsg\":\"抱歉,添加人脸失败，请稍后重试\"}";
        }


        //往数据库中保存个人信息
        int i = stuService.updateInformation(student);
        System.out.println(i);
        if(i > 0){
            return "{\"errCode\":0,\"errMsg\":\"完善成功\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"完善失败\"}";



    }

    /**
     *  更改电话号码
     */
    @ResponseBody
    @RequestMapping(value = {"/updateTel"},produces = {"text/json;charset=UTF-8"} )
    public Object perfectInformation(String tel, HttpSession session) throws IllegalInputException, IOException {

        IsLegalInputUtil.toJudgingInput(tel);

        //手机号码的正则表达式
        String regtel = "1[345789]\\d{9}";
        if(!tel.matches(regtel)){
            return "{\"errCode\":2,\"errMsg\":\"请输入正确的电话号码\"}";
        }

        String teachNum = (String)session.getAttribute("teachNum");
        System.out.println(teachNum);
        if(!StringUtils.isEmpty(teachNum)){
            int r = teachService.updateTelNum(teachNum,tel);
            if(r > 0){
                return "{\"errCode\":0,\"errMsg\":\"更改成功\"}";
            }
        }

        String stuNum = (String)session.getAttribute("stuNum");
        if(!StringUtils.isEmpty(stuNum)){
            int r = stuService.updateTelNum(stuNum,tel);
            if(r > 0){
                return "{\"errCode\":0,\"errMsg\":\"更改成功\"}";
            }
        }
        return "{\"errCode\":1,\"errMsg\":\"更改失败\"}";
    }

}
