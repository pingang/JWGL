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

 Date: 08/06/2019 22:46:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_optional
-- ----------------------------
DROP TABLE IF EXISTS `t_optional`;
CREATE TABLE `t_optional`  (
  `optional_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_num` int(11) NOT NULL,
  `teach_num` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `number` int(11) NULL DEFAULT NULL COMMENT '人数',
  `year` int(11) NULL DEFAULT NULL,
  `semester` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`optional_id`) USING BTREE,
  UNIQUE INDEX `course_num`(`course_num`, `teach_num`, `year`, `semester`) USING BTREE,
  INDEX `teach_num`(`teach_num`) USING BTREE,
  CONSTRAINT `t_optional_ibfk_1` FOREIGN KEY (`course_num`) REFERENCES `t_course` (`course_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_optional_ibfk_2` FOREIGN KEY (`teach_num`) REFERENCES `t_teach` (`teach_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_optional
-- ----------------------------
INSERT INTO `t_optional` VALUES (3, 1, '000001', 60, 2019, 2);
INSERT INTO `t_optional` VALUES (4, 2, '000003', 80, 2019, 2);

SET FOREIGN_KEY_CHECKS = 1;
