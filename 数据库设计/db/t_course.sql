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

 Date: 13/06/2019 16:19:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `course_num` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `credit` int(11) NULL DEFAULT NULL,
  `elective` int(11) NULL DEFAULT NULL,
  `college_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_hour` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`course_num`) USING BTREE,
  INDEX `college_num`(`college_num`) USING BTREE,
  CONSTRAINT `t_course_ibfk_1` FOREIGN KEY (`college_num`) REFERENCES `t_college` (`college_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES (1, '大学英语IV', 2, 1, '02', 16);
INSERT INTO `t_course` VALUES (2, '软件工程', 4, 0, '03', 64);
INSERT INTO `t_course` VALUES (3, '算法设计与分析', 4, 0, '03', 48);
INSERT INTO `t_course` VALUES (4, '人机界面设计', 2, 1, '03', 32);
INSERT INTO `t_course` VALUES (5, '数字电路与逻辑电路', 2, 0, '04', 32);
INSERT INTO `t_course` VALUES (6, '数据库原理与设计', 4, 1, '03', 48);
INSERT INTO `t_course` VALUES (7, '大学英语III', 2, 0, '02', 32);
INSERT INTO `t_course` VALUES (8, '数据结构和算法', 4, 1, '04', 48);
INSERT INTO `t_course` VALUES (9, '编译原理', 4, 0, '04', 64);
INSERT INTO `t_course` VALUES (10, '计算机网络', 4, 1, '04', 64);
INSERT INTO `t_course` VALUES (11, '面向对象程序设计', 5, 1, '04', 64);
INSERT INTO `t_course` VALUES (12, '面向过程程序设计', 5, 1, '04', 64);
INSERT INTO `t_course` VALUES (13, '项目管理', 2, 1, '04', 32);
INSERT INTO `t_course` VALUES (14, 'UML统一建模语言', 2, 1, '04', 32);

SET FOREIGN_KEY_CHECKS = 1;
