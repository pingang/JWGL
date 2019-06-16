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

 Date: 13/06/2019 16:19:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_college
-- ----------------------------
DROP TABLE IF EXISTS `t_college`;
CREATE TABLE `t_college`  (
  `college_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college_name` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teach_num` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`college_num`) USING BTREE,
  INDEX `teach_num`(`teach_num`) USING BTREE,
  CONSTRAINT `t_college_ibfk_1` FOREIGN KEY (`teach_num`) REFERENCES `t_teach` (`teach_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_college
-- ----------------------------
INSERT INTO `t_college` VALUES ('01', '电子工程院', NULL);
INSERT INTO `t_college` VALUES ('02', '外国语学院', '000001');
INSERT INTO `t_college` VALUES ('03', '通信工程学院', NULL);
INSERT INTO `t_college` VALUES ('04', '计算机学院', '000003');
INSERT INTO `t_college` VALUES ('09', '理学院', NULL);

SET FOREIGN_KEY_CHECKS = 1;
