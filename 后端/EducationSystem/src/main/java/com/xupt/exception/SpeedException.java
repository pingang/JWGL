package com.xupt.exception;

public class SpeedException extends RuntimeException {
    public SpeedException(){
        super("MD5加密出错,请输入其他密码");
    }
}