package com.xupt.controller;

import com.xupt.exception.IllegalInputException;
import com.xupt.service.SemesterService;
import com.xupt.util.DataUtil;
import com.xupt.util.IsLegalInputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;

/**
 * 该处理器负责学期日期的管理
 */
@Controller
public class SemesterController {


    @Autowired
    private SemesterService semsterService;

    /**
     *  该接口负责录入本学期的开始时间和结束时间
     */
    @ResponseBody
    @RequestMapping(value = {"/enterSemester"},produces = {"application/json;charset=UTF-8"})
    public String enterSemster(String year,String semster,String startDate,String endDate) throws IOException, IllegalInputException, ParseException {

        IsLegalInputUtil.toJudgingInput(year,semster,startDate,endDate);

        if (DataUtil.getWeekDay(startDate) != 1){
            return "{\"errCode\":2,\"errMsg\":\"学期开始日期必须为周一！\"}";
        }
        int r = semsterService.addSemester(year,semster,startDate,endDate);
        if(r > 0){
            return "{\"errCode\":0,\"errMsg\":\"录入成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"录入失败！\"}";
    }

    /**
     *  该接口负责更改本学期的开始时间和结束时间
     */
    @ResponseBody
    @RequestMapping(value = {"/updateSemester"},produces = {"application/json;charset=UTF-8"})
    public String updateSemster(String year,String semster,String startDate,String endDate) throws IOException, IllegalInputException, ParseException {

        IsLegalInputUtil.toJudgingInput(year,semster,startDate,endDate);

        if (DataUtil.getWeekDay(startDate) != 1){
            return "{\"errCode\":2,\"errMsg\":\"学期开始日期必须为周一！\"}";
        }
        int r = semsterService.updateSemester(year,semster,startDate,endDate);
        if(r > 0){
            return "{\"errCode\":0,\"errMsg\":\"更改成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"更改失败！\"}";
    }



}
