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

 Date: 13/06/2019 16:20:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_semester
-- ----------------------------
DROP TABLE IF EXISTS `t_semester`;
CREATE TABLE `t_semester`  (
  `year` int(255) NOT NULL,
  `semester` smallint(255) NOT NULL,
  `start_date` date NULL DEFAULT NULL,
  `end_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`year`, `semester`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_semester
-- ----------------------------
INSERT INTO `t_semester` VALUES (2019, 2, '2019-03-04', '2019-07-26');

SET FOREIGN_KEY_CHECKS = 1;
