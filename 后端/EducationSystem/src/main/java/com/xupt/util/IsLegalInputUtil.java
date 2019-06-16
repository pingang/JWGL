package com.xupt.util;

import com.xupt.exception.IllegalInputException;
import org.springframework.util.StringUtils;

public class IsLegalInputUtil {

    public static void toJudgingInput(String ... strings) throws IllegalInputException {
        for(String str : strings){
            if(StringUtils.isEmpty(str))
                //如果输入数据不合法，直接抛出异常
                throw new IllegalInputException();
        }
    }

}
