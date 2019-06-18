package com.xupt.service.serviceImpl;

import com.baidu.aip.face.AipFace;
import com.xupt.service.FaceService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FaceServiceImpl implements FaceService {

    @Override
    public String faceCheck(AipFace client, String img) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age,beauty");
        options.put("max_face_num", "2");
        //options.put("face_type", "LIVE");

        String imageType = "BASE64";

        // 人脸检测
        JSONObject res = client.detect(img, imageType, options);
        return res.toString(2);
    }

    @Override
    public String faceSearch(AipFace client, String img) {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("quality_control", "NORMAL");
            options.put("liveness_control", "LOW");
            options.put("max_user_num", "3");

            String imageType = "BASE64";
            //在login_group组中进行人脸搜索
            String groupIdList = "es";

            // 人脸搜索
            JSONObject res = client.search(img, imageType, groupIdList, options);
            return res.toString(2);
    }

    @Override
    public String faceAdd(AipFace client, String img, String userNum) {

            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("quality_control", "NORMAL");
            options.put("liveness_control", "LOW");
            options.put("action_type","REPLACE");

            String imageType = "BASE64";
            String groupId = "es";
            String userId = userNum;

            // 人脸注册
            JSONObject res = client.addUser(img, imageType, groupId, userId, options);
            return res.toString(2);
    }


}
