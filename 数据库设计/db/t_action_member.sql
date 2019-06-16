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

 Date: 13/06/2019 16:18:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_action_member
-- ----------------------------
DROP TABLE IF EXISTS `t_action_member`;
CREATE TABLE `t_action_member`  (
  `stu_num` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '同一队',
  `capital_num` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否是队长',
  `action_id` int(11) NOT NULL,
  PRIMARY KEY (`stu_num`, `action_id`) USING BTREE,
  INDEX `action_id`(`action_id`) USING BTREE,
  CONSTRAINT `t_action_member_ibfk_1` FOREIGN KEY (`action_id`) REFERENCES `t_action` (`action_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_action_member_ibfk_2` FOREIGN KEY (`stu_num`) REFERENCES `t_stu` (`stu_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_action_member
-- ----------------------------
INSERT INTO `t_action_member` VALUES ('04173011', 'edac473a-49ee-42e6-9567-d3909ae2c1e2', '04173011', 1);
INSERT INTO `t_action_member` VALUES ('04183562', 'edac473a-49ee-42e6-9567-d3909ae2c1e2', '04173011', 1);

SET FOREIGN_KEY_CHECKS = 1;
