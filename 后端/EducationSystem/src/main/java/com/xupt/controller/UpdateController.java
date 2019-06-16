package com.xupt.controller;

import com.xupt.exception.IllegalInputException;
import com.xupt.service.CollegeService;
import com.xupt.service.StuService;
import com.xupt.service.TeachService;
import com.xupt.util.IsLegalInputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UpdateController {


    @Autowired
    private StuService stuService;


    @Autowired
    private TeachService teachService;

    @Autowired
    private CollegeService collegeService;
    /**
     * 删除学生
     */
    @ResponseBody
    @RequestMapping(value = "/delStu", produces = {"application/json;charset=UTF-8"})
    public Object delStu(String stuNum) throws IllegalInputException {

        IsLegalInputUtil.toJudgingInput(stuNum);
        int size = stuService.getStu(stuNum, "").size();
        if(size == 0){
            return "{\"errCode\":2,\"errMsg\":\"不存在该学号！\"}";
        }

        int r = stuService.deleteStu(stuNum);
        if(r > 0){
            return "{\"errCode\":0,\"errMsg\":\"删除成功！\"}";
        }

        return "{\"errCode\":1,\"errMsg\":\"删除失败！\"}";
    }

    /**
     * 更改老师所属学院
     */
    @ResponseBody
    @RequestMapping(value = "/updateTeachCollege", produces = {"application/json;charset=UTF-8"})
    public Object updateTeachCollege(HttpSession session,String teachNum, String collegeName) throws IllegalInputException {

        String registrarNum = (String)session.getAttribute("registrarNum");
        if(StringUtils.isEmpty(registrarNum)){
            return "{\"errCode\":100,\"errMsg\":\"请登录教务主任账号！\"}";
        }

        IsLegalInputUtil.toJudgingInput(teachNum,collegeName);

        int size = teachService.getTeach(teachNum, "").size();
        if(size == 0){
            return "{\"errCode\":2,\"errMsg\":\"不存在该工号！\"}";
        }

        String collegeNum = collegeService.getCollegeNum(collegeName);
        if(StringUtils.isEmpty(collegeNum)){
            return "{\"errCode\":3,\"errMsg\":\"不存在该学院！\"}";
        }

        int r = teachService.updateTeachCollege(teachNum,collegeNum);
        if(r > 0){
            return "{\"errCode\":0,\"errMsg\":\"更改成功！\"}";
        }

        return "{\"errCode\":1,\"errMsg\":\"更改失败！\"}";
    }

}
