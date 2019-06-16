package com.xupt.exception;

public class IllegalInputException extends RuntimeException{

    public IllegalInputException() {
        super("请不要输入空值！！！");
    }

}
