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

 Date: 13/06/2019 16:18:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_action
-- ----------------------------
DROP TABLE IF EXISTS `t_action`;
CREATE TABLE `t_action`  (
  `action_id` int(11) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `number` smallint(6) NULL DEFAULT NULL COMMENT '最多报名人数',
  PRIMARY KEY (`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_action
-- ----------------------------
INSERT INTO `t_action` VALUES (1, '英语四级', '2019-06-01 17:35:58', '2019-06-30 17:36:05', 1);
INSERT INTO `t_action` VALUES (2, '英语六级', '2019-06-02 17:36:29', '2019-06-29 17:36:37', 1);
INSERT INTO `t_action` VALUES (3, '互联网+', '2019-06-13 15:47:23', '2019-06-27 15:47:30', 5);

SET FOREIGN_KEY_CHECKS = 1;
