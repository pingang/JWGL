package com.xupt.bean.face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    private Integer face_num;
    private String face_token;
    private List<User> user_list;

    public List<User> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<User> user_list) {
        this.user_list = user_list;
    }

    public Integer getFace_num() {
        return face_num;
    }

    public void setFace_num(Integer face_num) {
        this.face_num = face_num;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    @Override
    public String toString() {
        return "Result{" +
                "face_num=" + face_num +
                ", face_token='" + face_token + '\'' +
                ", user_list=" + user_list +
                '}';
    }
}
