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

 Date: 13/06/2019 16:19:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_optional_table
-- ----------------------------
DROP TABLE IF EXISTS `t_optional_table`;
CREATE TABLE `t_optional_table`  (
  `optional_id` int(11) NULL DEFAULT NULL,
  `weekday` int(11) NULL DEFAULT NULL,
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_time` time(0) NULL DEFAULT NULL,
  `end_time` time(0) NULL DEFAULT NULL,
  INDEX `optional_id`(`optional_id`) USING BTREE,
  CONSTRAINT `t_optional_table_ibfk_1` FOREIGN KEY (`optional_id`) REFERENCES `t_optional` (`optional_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_optional_table
-- ----------------------------
INSERT INTO `t_optional_table` VALUES (4, 1, '东区FZ403', '02:30:00', '04:00:00');
INSERT INTO `t_optional_table` VALUES (4, 3, '东区FZ403', '02:30:00', '04:00:00');

SET FOREIGN_KEY_CHECKS = 1;
