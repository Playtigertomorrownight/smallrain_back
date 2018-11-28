/*
Navicat MySQL Data Transfer

Source Server         : localSql
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : smallrain_back

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-11-28 19:41:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `text` varchar(32) DEFAULT NULL,
  `icon` varchar(64) DEFAULT NULL,
  `clazz` varchar(64) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `parent` int(2) DEFAULT NULL,
  `order` int(2) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `media_id` varchar(32) DEFAULT NULL,
  `appid` varchar(64) DEFAULT NULL,
  `pagepath` varchar(128) DEFAULT NULL,
  `platform` varchar(32) DEFAULT NULL,
  `topref` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `id` varchar(32) NOT NULL,
  `menu_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` varchar(32) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '资源名称',
  `type` varchar(16) DEFAULT NULL COMMENT '资源类型',
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
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sex` varchar(8) DEFAULT NULL,
  `descroption` varchar(255) DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
