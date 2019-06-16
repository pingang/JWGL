package com.xupt.exception;

import com.xupt.util.ExcelData;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String str){
        super(str);
    }

}
