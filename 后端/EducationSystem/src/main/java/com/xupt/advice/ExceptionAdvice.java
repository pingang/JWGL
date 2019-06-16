package com.xupt.advice;

import com.xupt.controller.PerfectInformationController;
import com.xupt.util.ErrorCode;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionAdvice {

    /*Logger logger = Logger.getLogger(ExceptionAdvice.class);*/

    @ResponseBody
    @ExceptionHandler
    public String returnErrorMsg(Exception e, HttpServletResponse response){
        e.printStackTrace();
        //logger.error(e.getMessage());
        return "{\"errCode\":"+ ErrorCode.getErrorCode(e) +",\"errMsg\":\"" +e.getMessage() +"\"}";
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public String insertMsgError(Exception e, HttpServletResponse response){
        e.printStackTrace();
        return "{\"errCode\":"+ ErrorCode.getErrorCode(e) +",\"errMsg\":\"数据不合理\"}";
    }

}
