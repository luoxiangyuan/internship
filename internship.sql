/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : internship

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-11-12 15:24:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(255) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `class_id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`class_id`),
  KEY `class_id` (`class_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `enterprise_id` int(255) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `contact_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `contact_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `intro` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `images` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `check_file` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`enterprise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for internship_detail
-- ----------------------------
DROP TABLE IF EXISTS `internship_detail`;
CREATE TABLE `internship_detail` (
  `internship_id` int(255) NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enterprise_id` int(255) DEFAULT NULL,
  `description` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `need_num` int(10) DEFAULT NULL,
  `exp_end_time` datetime DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  `apply_end_time` datetime DEFAULT NULL,
  `exp_modify_time` datetime DEFAULT NULL,
  `exp_begin_time` datetime DEFAULT NULL,
  PRIMARY KEY (`internship_id`),
  KEY `fk` (`enterprise_id`) USING BTREE,
  CONSTRAINT `internship_detail_ibfk_1` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL COMMENT '0 表示男 1 表示女',
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `introduction` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `exps` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `works` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stu_class` int(255) NOT NULL,
  PRIMARY KEY (`stu_id`),
  KEY `fk12` (`stu_class`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`stu_class`) REFERENCES `class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for stu_achievement
-- ----------------------------
DROP TABLE IF EXISTS `stu_achievement`;
CREATE TABLE `stu_achievement` (
  `apply_id` int(255) NOT NULL,
  `certificate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mark` double(255,0) DEFAULT NULL,
  `perception` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `work_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `fk_ach_apply` (`apply_id`) USING BTREE,
  CONSTRAINT `stu_achievement_ibfk_1` FOREIGN KEY (`apply_id`) REFERENCES `stu_apply` (`apply_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for stu_apply
-- ----------------------------
DROP TABLE IF EXISTS `stu_apply`;
CREATE TABLE `stu_apply` (
  `apply_id` int(255) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `exp_id` int(255) DEFAULT NULL,
  `apply_status` varchar(255) DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `fk_apply_student` (`stu_id`) USING BTREE,
  KEY `fk_exp_apply` (`exp_id`) USING BTREE,
  CONSTRAINT `stu_apply_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stu_apply_ibfk_2` FOREIGN KEY (`exp_id`) REFERENCES `internship_detail` (`internship_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
