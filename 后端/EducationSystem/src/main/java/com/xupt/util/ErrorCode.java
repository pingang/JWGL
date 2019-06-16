package com.xupt.util;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.xupt.exception.AlreadyExistsException;
import com.xupt.exception.IllegalInputException;
import com.xupt.exception.SpeedException;
import org.springframework.dao.DuplicateKeyException;

public class ErrorCode {

    public static int getErrorCode(Exception e){
        if(e instanceof IllegalInputException)
            return 101;
        if(e instanceof SpeedException)
            return 102;
        if(e instanceof AlreadyExistsException)
            return 103;
        if(e instanceof DuplicateKeyException){
            return 104;
        }
        return 500;
    }

}
