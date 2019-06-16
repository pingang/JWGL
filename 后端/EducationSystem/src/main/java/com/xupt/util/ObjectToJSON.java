package com.xupt.util;

import com.xupt.bean.Student;
import com.xupt.bean.Teacher;
import org.json.JSONObject;

public class ObjectToJSON {

    // 登录成功后对信息在进行一次封装
    public static String toJson(Object object){
        JSONObject jsonObject = null;
        if(object instanceof Student){
            Student student = (Student)object;
            student.setMajorId(null);
            student.setPassword(null);
            jsonObject = new JSONObject(student);
        }else if(object instanceof Teacher){
            Teacher teacher = (Teacher)object;
            teacher.setPassword(null);
            jsonObject = new JSONObject(teacher);
        }
        jsonObject.put("errCode",0);
        return jsonObject.toString();
    }



}
