package com.xupt.bean;

import java.io.Serializable;

public class Teacher implements Serializable {

   private String teachNum;

   private String password;

   private String teachName;

   private String telNum;

   private String collegeNum;

   private String sex;

   private String idCard;

   private String birth;

   private String collegeName;

   public Teacher(String teachNum, String password, String teachName, String telNum, String collegeNum, String sex, String idCard, String birth, String collegeName) {
        this.teachNum = teachNum;
        this.password = password;
        this.teachName = teachName;
        this.telNum = telNum;
        this.collegeNum = collegeNum;
        this.sex = sex;
        this.idCard = idCard;
        this.birth = birth;
        this.collegeName = collegeName;
   }

   public Teacher() {
   }

    public String getTeachNum() {
        return teachNum;
    }

    public void setTeachNum(String teachNum) {
        this.teachNum = teachNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeachName() {
        return teachName;
    }

    public void setTeachName(String teachName) {
        this.teachName = teachName;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getCollegeNum() {
        return collegeNum;
    }

    public void setCollegeNum(String collegeNum) {
        this.collegeNum = collegeNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teachNum='" + teachNum + '\'' +
                ", password='" + password + '\'' +
                ", teachName='" + teachName + '\'' +
                ", telNum='" + telNum + '\'' +
                ", collegeNum='" + collegeNum + '\'' +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", birth='" + birth + '\'' +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }
}
