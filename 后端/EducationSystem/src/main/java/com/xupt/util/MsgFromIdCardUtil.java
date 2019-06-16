package com.xupt.util;

import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import org.springframework.util.StringUtils;

public class MsgFromIdCardUtil {

    public static void setMsg(Object obj){
        String idCard = null;

        if(obj instanceof Student) {
            idCard = ((Student) obj).getIdCard();
            if(StringUtils.isEmpty(idCard))
                return;
            if(Integer.parseInt(idCard.charAt(16)+"")%2 != 0)
                ((Student) obj).setSex("男");
            else
                ((Student) obj).setSex("女");
            String birth = idCard.substring(6,14);
            ((Student) obj).setBirth(birth.substring(0,4)+"-"+birth.substring(4,6)+"-"+birth.substring(6));
        }else{
            idCard = ((Teacher) obj).getIdCard();
            if(StringUtils.isEmpty(idCard))
                return;
            if(Integer.parseInt(idCard.charAt(16)+"")%2 != 0)
                ((Teacher) obj).setSex("男");
            else
                ((Teacher) obj).setSex("女");
            String birth = idCard.substring(6,14);
            ((Teacher) obj).setBirth(birth.substring(0,4)+"-"+birth.substring(4,6)+"-"+birth.substring(6));
            System.out.println(obj);
        }

    }

    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(student);
    }
}
