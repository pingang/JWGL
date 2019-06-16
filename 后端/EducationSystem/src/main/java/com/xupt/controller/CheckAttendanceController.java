package com.xupt.controller;

import com.xupt.bean.Course;
import com.xupt.bean.SignIn;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.SemesterService;
import com.xupt.service.StuService;
import com.xupt.util.DataUtil;
import com.xupt.util.IsLegalInputUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *  该接口负责考勤信息的查询
 */
@Controller
public class CheckAttendanceController {

    @Autowired
    private StuService stuService;

    @Autowired
    private SemesterService semesterService;



    /**
     *  查询指定时间段的签到信息
     *
     */
    @ResponseBody
    @RequestMapping(value = "/getCheckInTableByTime", produces = {"application/json;charset=UTF-8"})
    public Object getCheckInTableByTime(HttpSession session,String startDate,String endDate) throws ParseException, IllegalInputException {

        IsLegalInputUtil.toJudgingInput(startDate,endDate);


        String stuNum = (String) session.getAttribute("stuNum");
        if(StringUtils.isEmpty(stuNum)){
            return "{\"errCode\":1,\"errMsg\":\"你的信息已过期，请重新操作！\"}";
        }

        IsLegalInputUtil.toJudgingInput(startDate,endDate);

        List<SignIn> list = stuService.getCheckInTable(stuNum,startDate,endDate);
        return list;

    }

}
