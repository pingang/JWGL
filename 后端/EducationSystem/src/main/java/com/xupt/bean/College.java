package com.xupt.bean;

/**
 * 学院
 */
public class College {

    private String collegeNum;

    private String collegeName;

    private String teachNum;

    public String getTeachNum() {
        return teachNum;
    }

    public void setTeachNum(String teachNum) {
        this.teachNum = teachNum;
    }

    public College() {
    }


    public String getCollegeNum() {
        return collegeNum;
    }

    public void setCollegeNum(String collegeNum) {
        this.collegeNum = collegeNum;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "College{" +
                "collegeNum='" + collegeNum + '\'' +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }
}
