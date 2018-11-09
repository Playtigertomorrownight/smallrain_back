/*
Navicat MySQL Data Transfer

Source Server         : localSql
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : smallrain_back

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-11-07 15:22:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sex` varchar(8) NOT NULL,
  `descroption` varchar(255) DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
