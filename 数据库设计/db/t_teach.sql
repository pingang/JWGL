/*
 Navicat MySQL Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 203.195.193.218:3306
 Source Schema         : es

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 13/06/2019 16:20:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_teach
-- ----------------------------
DROP TABLE IF EXISTS `t_teach`;
CREATE TABLE `t_teach`  (
  `teach_num` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teach_name` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tel_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `college_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `id_card` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birth` date NULL DEFAULT NULL,
  PRIMARY KEY (`teach_num`) USING BTREE,
  INDEX `college_num`(`college_num`) USING BTREE,
  CONSTRAINT `t_teach_ibfk_1` FOREIGN KEY (`college_num`) REFERENCES `t_college` (`college_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_teach
-- ----------------------------
INSERT INTO `t_teach` VALUES ('000001', '21218c922c33ca7780e01511054d2ba1', '王晓', '12345678901', '02', '女', '612725199905305123', '1999-05-30');
INSERT INTO `t_teach` VALUES ('000002', '21218c922c33ca7780e01511054d2ba1', '陈琳', '18191112222', '03', '女', '612725199905306666', '1999-05-30');
INSERT INTO `t_teach` VALUES ('000003', '21218c922c33ca7780e01511054d2ba1', '张庆生', '12345678901', '03', '男', '612725199905305123', '2019-06-01');
INSERT INTO `t_teach` VALUES ('000004', '21218c922c33ca7780e01511054d2ba1', '宋辉', '12345678901', '03', '男', '612725199905305123', '2019-06-01');
INSERT INTO `t_teach` VALUES ('000005', '21218c922c33ca7780e01511054d2ba1', '孟彩霞', '12345678901', '03', '女', '612725199905305123', '2019-06-01');
INSERT INTO `t_teach` VALUES ('000006', '21218c922c33ca7780e01511054d2ba1', '朱钧', '12345678901', '04', '女', '612725199905305123', '2019-06-01');
INSERT INTO `t_teach` VALUES ('000100', '21218c922c33ca7780e01511054d2ba1', '王帅', '123456789', '04', '男', '612725199905305012', '1999-05-30');
INSERT INTO `t_teach` VALUES ('100000', '21218c922c33ca7780e01511054d2ba1', '通用老师', '123456789', '04', '男', '612725199905305123', '1999-05-30');

SET FOREIGN_KEY_CHECKS = 1;
