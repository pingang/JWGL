package com.xupt.controller;

import com.baidu.aip.face.AipFace;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xupt.bean.Course;
import com.xupt.bean.face.Face;
import com.xupt.service.FaceService;
import com.xupt.service.SignInService;
import com.xupt.service.StuService;
import com.xupt.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * 该处理器负责传来人脸进行考勤信息录入
 */
@Controller
public class SignInController {

    @Autowired
    private AipFace aipFace;

    @Autowired
    private FaceService service;

    @Autowired
    private SignInService signInService;

    private ObjectMapper objectMapepr = new ObjectMapper();

    @ResponseBody
    @RequestMapping(value = "/signIn",produces = {"application/json;charset=UTF-8"})
    public String faceSign(String img) throws IOException, IOException, ParseException {

        // 获取当前时间
        // String time = DataUtil.getNowDate();
        String time = "2019-06-14 14:30:00";
        String nowDate = time.split(" ")[0];
        String nowTime = time.split(" ")[1];
        System.out.println(System.currentTimeMillis());
        // 过滤非签到时间
        if("07:00:00".compareTo(nowTime) > 0 || "18:15:00".compareTo(nowTime) < 0 || ("12:00:00".compareTo(nowTime) < 0 && "14:00:00".compareTo(nowTime)>0)){
            return "A";
        }

        //在人脸库中进行搜索
        String str = service.faceSearch(aipFace, img);

        Face face = objectMapepr.readValue(str,Face.class);

        //如果人脸库中没有搜索到的话
        if(!"0".equals(face.getError_code())){
            return "B";
        }

        String stuNum = face.getResult().getUser_list().get(0).getUser_id();

        String courseStartTime = null;
        int state = 1;
        // 第一节课签到时间
        if("07:00:00".compareTo(nowTime) <= 0 && "09:45:00".compareTo(nowTime)>0){
            if("07:00:00".compareTo(nowTime) <= 0 && "08:00:00".compareTo(nowTime)>=0) {
                state = 0;
            }
            courseStartTime = "08:00:00";
        // 第二节课签到时间
        }else if("09:45:00".compareTo(nowTime) <=0 && "12:00:00".compareTo(nowTime)>0){
            if("09:45:00".compareTo(nowTime) <= 0 && "10:15:00".compareTo(nowTime)>=0) {
                state = 0;
            }
            courseStartTime = "10:15:00";
        // 第三节课签到时间
        }else if("14:00:00".compareTo(nowTime) <=0 && "16:15:00".compareTo(nowTime)>=0){
            if("14:00:00".compareTo(nowTime) <= 0 && "14:30:00".compareTo(nowTime)>=0) {
                state = 0;
            }
            courseStartTime = "14:30:00";
        // 第四节课签到时间
        }else if("16:15:00".compareTo(nowTime) <= 0 && "18:15:00".compareTo(nowTime)>=0){
            if("16:15:00".compareTo(nowTime) <= 0 && "16:45:00".compareTo(nowTime)>=0) {
                state = 0;
            }
            courseStartTime = "16:30:00";
        }


        int weekday = DataUtil.getWeekDay(nowDate);
        String year = nowDate.split("-")[0];
        int month = Integer.parseInt(nowDate.split("-")[1]);
        int semester = 1;
        // 判断当前是第几学期
        if(month >=2 && month <= 8)
            semester = 2;

        // 查询当前时间是否存在课程
        Integer courseNum = signInService.getCourseNumNow(stuNum, courseStartTime, weekday, year, semester);
        if(courseNum == null){
            // 当前时刻不存在课
            return "C";
        }

        // 查询有没有签到过
        Integer i = signInService.getHadSignIn(stuNum,courseNum,nowDate);
        if(i == 0 || i == 1){
            // 已签到过
            return "D";
        }

        // 开始进行签到
        int r = signInService.addSignInTime(stuNum,state,time,courseNum);
        if(r > 0){
            //签到成功
            return "E";
        }
        return "F";
    }

}
