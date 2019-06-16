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

 Date: 01/06/2019 20:31:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_schedule
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule`;
CREATE TABLE `t_schedule`  (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `teach_num` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_num` int(11) NULL DEFAULT NULL,
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `weekday` smallint(11) NULL DEFAULT NULL,
  `start_time` time(0) NULL DEFAULT NULL,
  `end_time` time(0) NULL DEFAULT NULL,
  `major_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`schedule_id`) USING BTREE,
  UNIQUE INDEX `teach_num`(`teach_num`, `course_num`, `weekday`, `start_time`, `end_time`, `major_id`) USING BTREE,
  INDEX `course_num`(`course_num`) USING BTREE,
  INDEX `major_id`(`major_id`) USING BTREE,
  CONSTRAINT `t_schedule_ibfk_1` FOREIGN KEY (`teach_num`) REFERENCES `t_teach` (`teach_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_schedule_ibfk_2` FOREIGN KEY (`course_num`) REFERENCES `t_course` (`course_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_schedule_ibfk_3` FOREIGN KEY (`major_id`) REFERENCES `t_major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
