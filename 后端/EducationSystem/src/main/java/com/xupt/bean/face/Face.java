package com.xupt.bean.face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Face {
    private Result result;
    private String log_id;
    private String error_msg;
    private String error_code;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "Face{" +
                "result=" + result +
                ", log_id='" + log_id + '\'' +
                ", error_msg='" + error_msg + '\'' +
                ", error_code='" + error_code + '\'' +
                '}';
    }
}
