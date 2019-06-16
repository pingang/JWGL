package com.xupt.controller;

import com.xupt.bean.Action;
import com.xupt.exception.IllegalInputException;
import com.xupt.service.ActionService;
import com.xupt.service.StuService;
import com.xupt.util.DataUtil;
import com.xupt.util.IsLegalInputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * 负责一些活动的添加、报名
 */
@RestController
public class ActionController {


    @Autowired
    private ActionService actionService;

    @Autowired
    private StuService stuService;

    @RequestMapping(value = {"/addAction"},produces = {"text/json;charset=UTF-8"} )
    public String addAction(Action action){
        IsLegalInputUtil.toJudgingInput(action.getActionName(),action.getStartTime(),action.getEndTIme(),action.getNumber()+"");

        if(actionService.addAction(action)>0){
            return "{\"errCode\":0,\"errMsg\":\"报名成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"报名失败！\"}";
    }

    @RequestMapping(value = {"/getAction"})
    public List<Action> getAction(){

        String nowDate = DataUtil.getNowDate();
        List<Action> list = actionService.getAction(nowDate);
        return list;
    }

    @RequestMapping(value = {"/register"},produces = {"text/json;charset=UTF-8"} )
    public String register(Integer actionId, String[] nums){

        IsLegalInputUtil.toJudgingInput(actionId+"");

        if(nums.length == 0){
            throw new IllegalInputException();
        }

        for(String num : nums){
            int size = stuService.getStu(num, "").size();
            if(size == 0){
                return "{\"errCode\":2,\"errMsg\":\""+num+"不存在！\"}";
            }
        }

        UUID.randomUUID();
        if(actionService.addRegisterMember(actionId,nums)>0){
            return "{\"errCode\":0,\"errMsg\":\"报名成功！\"}";
        }
        return "{\"errCode\":1,\"errMsg\":\"报名失败！\"}";
    }

}
