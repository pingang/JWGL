package com.xupt.bean;

/**
 * 学生
 */
public class Student {

    //学号
    private String stuNum;

    //学生姓名
    private String stuName;

    //性别
    private String sex;

    //班级姓名
    private String className;

    //身份证号码
    private String idCard;

    //生日
    private String birth;

    //民族
    private String nation;

    //电话号码
    private String telNum;

    //专业id
    private Integer majorId;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    //专业名称
    private String majorName;

    //学院名称
    private String collegeName;

    public Student() {
    }

    public Student(String stuNum, String stuName, String sex, String className, String idCard, String birth, String nation, String telNum, String majorName, String collegeName) {
        this.stuNum = stuNum;
        this.stuName = stuName;
        this.sex = sex;
        this.className = className;
        this.idCard = idCard;
        this.birth = birth;
        this.nation = nation;
        this.telNum = telNum;
        this.majorName = majorName;
        this.collegeName = collegeName;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuNum='" + stuNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", sex='" + sex + '\'' +
                ", className='" + className + '\'' +
                ", idCard='" + idCard + '\'' +
                ", birth='" + birth + '\'' +
                ", nation='" + nation + '\'' +
                ", telNum='" + telNum + '\'' +
                ", majorName='" + majorName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }
}
