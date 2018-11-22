/*
Navicat MySQL Data Transfer

Source Server         : localSql
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : smallrain_back

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-11-21 15:55:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` varchar(32) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '资源名称',
  `type` int(2) DEFAULT NULL COMMENT '资源类型',
  `label` varchar(16) DEFAULT NULL COMMENT '资源标签',
  `size` bigint(11) DEFAULT NULL COMMENT '资源大小',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `path` varchar(128) DEFAULT NULL COMMENT '上传后的路径',
  `description` varchar(255) DEFAULT NULL COMMENT '资源描述',
  `user_id` varchar(32) DEFAULT NULL COMMENT '上传用户id',
  `image` varchar(128) DEFAULT NULL COMMENT '封面图片路径',
  `suffix` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
