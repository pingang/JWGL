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

 Date: 01/06/2019 15:05:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_score
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score`  (
  `stu_num` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teach_num` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_num` int(11) NOT NULL,
  `score` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`stu_num`, `course_num`) USING BTREE,
  INDEX `course_num`(`course_num`) USING BTREE,
  INDEX `teach_num`(`teach_num`) USING BTREE,
  CONSTRAINT `t_score_ibfk_1` FOREIGN KEY (`stu_num`) REFERENCES `t_stu_course` (`stu_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_score_ibfk_2` FOREIGN KEY (`teach_num`) REFERENCES `t_stu_course` (`teach_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_score_ibfk_3` FOREIGN KEY (`course_num`) REFERENCES `t_stu_course` (`course_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_score
-- ----------------------------
INSERT INTO `t_score` VALUES ('04173011', '000001', 1, 66);
INSERT INTO `t_score` VALUES ('04173011', '000003', 2, 100);
INSERT INTO `t_score` VALUES ('04173011', '000002', 3, 88);
INSERT INTO `t_score` VALUES ('04173011', '000004', 4, 99);
INSERT INTO `t_score` VALUES ('04173011', '000006', 5, 77);
INSERT INTO `t_score` VALUES ('04173011', '000005', 6, 99);

SET FOREIGN_KEY_CHECKS = 1;
