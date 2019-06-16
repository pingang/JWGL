package com.xupt.service;

import com.baidu.aip.face.AipFace;

/**
 *  人脸识别的接口
 *
 */
public interface FaceService {

    //人脸检测
    public String faceCheck(AipFace client, String img);

    //人脸搜索
    public String faceSearch(AipFace client, String img);

    //人脸添加
    public String faceAdd(AipFace client, String img, String userNum);
}
