package com.xupt.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String uri = httpServletRequest.getRequestURI();
        if(uri.contains("excel") || uri.contains("data") || uri.contains("add") || uri.contains("del")){
            httpServletResponse.setContentType("application/json,charset=UTF-8");
            if (StringUtils.isEmpty(httpServletRequest.getSession().getAttribute("registrarNum"))){
                httpServletResponse.getWriter().println("{\"errCode\":100,\"errMsg\":\"请登录教务主任账号！\"}");
                return false;
            }
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
