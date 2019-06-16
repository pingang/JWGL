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

 Date: 13/06/2019 16:20:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_stu_course
-- ----------------------------
DROP TABLE IF EXISTS `t_stu_course`;
CREATE TABLE `t_stu_course`  (
  `stu_num` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `course_num` int(11) NOT NULL,
  `teach_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `weekday` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_time` time(6) NULL DEFAULT NULL,
  `end_time` time(6) NULL DEFAULT NULL,
  `year` int(11) NULL DEFAULT NULL,
  `semester` int(11) NULL DEFAULT NULL,
  `score` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`stu_num`, `course_num`, `weekday`) USING BTREE,
  INDEX `course_num`(`course_num`) USING BTREE,
  INDEX `teach_num`(`teach_num`) USING BTREE,
  INDEX `stu_num`(`stu_num`) USING BTREE,
  CONSTRAINT `t_stu_course_ibfk_1` FOREIGN KEY (`stu_num`) REFERENCES `t_stu` (`stu_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_stu_course_ibfk_3` FOREIGN KEY (`teach_num`) REFERENCES `t_teach` (`teach_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_stu_course_ibfk_4` FOREIGN KEY (`course_num`) REFERENCES `t_course` (`course_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_stu_course
-- ----------------------------
INSERT INTO `t_stu_course` VALUES ('04173001', 2, '000003', '1', '长安校区东区FZ403', '02:30:00.000000', '04:00:00.000000', 2019, 2, 100);
INSERT INTO `t_stu_course` VALUES ('04173001', 2, '000003', '3', '长安校区东区FZ403', '02:30:00.000000', '04:00:00.000000', 2019, 2, 100);
INSERT INTO `t_stu_course` VALUES ('04173011', 1, '000001', '1', '长安校区东区FF302', '08:00:00.000000', '09:45:00.000000', 2019, 2, 88);
INSERT INTO `t_stu_course` VALUES ('04173011', 2, '000003', '5', '长安校区东区FF123', '10:30:00.000000', '12:00:00.000000', 2019, 2, 90);
INSERT INTO `t_stu_course` VALUES ('04173011', 3, '000002', '2', '长安校区东区FF111', '08:00:00.000000', '09:45:00.000000', 2019, 2, 50);
INSERT INTO `t_stu_course` VALUES ('04173011', 3, '000002', '5', '长安校区东区FF403', '08:00:00.000000', '09:45:00.000000', 2019, 2, 50);
INSERT INTO `t_stu_course` VALUES ('04173011', 4, '000004', '2', '长安校区东区FF403', '10:30:00.000000', '12:00:00.000000', 2019, 2, 60);
INSERT INTO `t_stu_course` VALUES ('04173011', 4, '000004', '5', '长安校区东区FF403', '14:30:00.000000', '16:15:00.000000', 2019, 2, 60);
INSERT INTO `t_stu_course` VALUES ('04173011', 5, '000006', '3', '长安校区东区FF403', '08:00:00.000000', '09:45:00.000000', 2019, 2, 70);
INSERT INTO `t_stu_course` VALUES ('04173011', 6, '000005', '4', '长安校区东区FF403', '14:30:00.000000', '16:15:00.000000', 2019, 2, 60);
INSERT INTO `t_stu_course` VALUES ('04173011', 6, '000005', '5', '长安校区东区FF403', '16:30:42.000000', '18:15:00.000000', 2019, 2, 60);
INSERT INTO `t_stu_course` VALUES ('04173011', 9, '100000', '1', '长安校区东区FF503', '10:15:00.000000', '12:00:00.000000', 2019, 2, 61);
INSERT INTO `t_stu_course` VALUES ('04173011', 10, '100000', '1', '长安校区东区FF403', '16:30:42.000000', '18:15:00.000000', 2019, 2, 98);
INSERT INTO `t_stu_course` VALUES ('04173011', 11, '100000', '1', '长安校区东区FF403', '10:15:00.000000', '10:15:00.000000', 2019, 2, 97);
INSERT INTO `t_stu_course` VALUES ('04173011', 11, '100000', '2', '长安校区东区FF403', '16:30:42.000000', '18:15:00.000000', 2019, 2, 97);
INSERT INTO `t_stu_course` VALUES ('04173011', 12, '100000', '3', '长安校区东区FF403', '16:30:42.000000', '18:15:00.000000', 2019, 2, 89);
INSERT INTO `t_stu_course` VALUES ('04173011', 13, '100000', '4', '长安校区东区FF403', '16:30:42.000000', '18:15:00.000000', 2019, 2, 56);
INSERT INTO `t_stu_course` VALUES ('04173011', 14, '100000', '5', '长安校区东区FF403', '16:30:42.000000', '18:15:00.000000', 2019, NULL, 79);
INSERT INTO `t_stu_course` VALUES ('04183562', 2, '000003', '1', '东区FZ403', '02:30:00.000000', '04:00:00.000000', 2019, 2, NULL);
INSERT INTO `t_stu_course` VALUES ('04183562', 2, '000003', '3', '东区FZ403', '02:30:00.000000', '04:00:00.000000', 2019, 2, NULL);

SET FOREIGN_KEY_CHECKS = 1;
