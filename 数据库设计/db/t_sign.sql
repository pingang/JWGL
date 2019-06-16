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

 Date: 13/06/2019 16:20:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_sign`;
CREATE TABLE `t_sign`  (
  `stu_num` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_num` int(11) NULL DEFAULT NULL,
  `date_time` datetime(0) NULL DEFAULT NULL,
  `state` smallint(6) NULL DEFAULT NULL,
  `weekday` smallint(255) NULL DEFAULT NULL,
  INDEX `stu_num`(`stu_num`) USING BTREE,
  CONSTRAINT `t_sign_ibfk_1` FOREIGN KEY (`stu_num`) REFERENCES `t_stu_course` (`stu_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sign
-- ----------------------------
INSERT INTO `t_sign` VALUES ('04173011', 1, '2019-06-03 00:00:00', NULL, 1);
INSERT INTO `t_sign` VALUES ('04173011', 4, '2019-06-04 00:00:00', NULL, 2);
INSERT INTO `t_sign` VALUES ('04173011', 5, '2019-06-05 00:00:00', NULL, 3);
INSERT INTO `t_sign` VALUES ('04173011', 6, '2019-06-06 00:00:00', NULL, 4);
INSERT INTO `t_sign` VALUES ('04173011', 2, '2019-06-07 00:00:00', 0, 5);
INSERT INTO `t_sign` VALUES ('04173011', 4, '2019-06-07 00:00:00', 1, 5);
INSERT INTO `t_sign` VALUES ('04173011', 3, '2019-06-07 08:00:00', 0, 5);

SET FOREIGN_KEY_CHECKS = 1;
