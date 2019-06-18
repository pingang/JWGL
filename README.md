# JWGL
教务管理系统



## 简述

该项目为教务管理系统，花费两周的时间和小组成员共同完成的课程设计，该项目功能有正常教务系统所具备的相关功能，及通过人脸识别所完成的考勤系统。

有很多的不足，希望各位可以多多海涵，提出意见，计划后期不断完善。



## 说明

接口相关的说明文档位于  后端代码/EducationSystem/src/main/webapp/word 目录下





## 你需要修改的地方

百度人脸识别相关配置，该配置位于applicationContext.xml文件中，你需要自己申请相关账号：

    <!-- 百度AI人脸识别API -->
    <bean class="com.baidu.aip.face.AipFace">
        <constructor-arg name="apiKey" value=""/>
        <constructor-arg name="appId" value=""/>
        <constructor-arg name="secretKey" value=""/>
    </bean>


腾讯云短信相关配置，位于com.xupt.util.SendVaildCodeUtil类下。



需要连接Redis，你需要一台安装Redis的机器，更改IP地址即可：

com.xupt.util.RedisCacheUtil.java



## 不足

1、接口设计不够符合规范，如接口的命名、响应信息反馈等

2、数据库设计不完全符合范式，数据冗余

3、后端有很多已完成的接口，前端并未来的及实现

