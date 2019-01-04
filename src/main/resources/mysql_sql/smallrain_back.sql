/*
Navicat MySQL Data Transfer

Source Server         : localSql
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : smallrain_back

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2019-01-04 22:18:31
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
  `parent` varchar(32) DEFAULT NULL,
  `orderNum` int(2) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `media_id` varchar(32) DEFAULT NULL,
  `appid` varchar(64) DEFAULT NULL,
  `pagepath` varchar(128) DEFAULT NULL,
  `platform` varchar(32) DEFAULT NULL,
  `topref` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('655e7cddb4ce458a8b4c36598651ff6f', 'manager-wechat-menu', '管理公众号菜单', '', 'manager-wechat-menu', '/manager/menu?platform=WECHAT&current=manager-wechat-menu', '-1', '3', '', '', '', '', 'BACKLEFT', 'menu-manager');
INSERT INTO `menu` VALUES ('a8775523b78f4a74a3c85d1552a899ee', 'wechat_operate', '一般操作', '', '', '', '-1', '3', '', '', '', '', 'WECHAT', '');
INSERT INTO `menu` VALUES ('b22cba03082f453e88a11d9efafb4ae8', 'samllrain_home', '小雨淅沥', '', '', 'wx/web/index', '-1', '0', 'VIEW', '', '', '/', 'WECHAT', '');
INSERT INTO `menu` VALUES ('b61bc4a319c94086b47e81c9224a48c5', 'manager-back-resource-list', '资源列表', 'ios-list-box-outline', 'resource-list', 'manager/resource?current=manager-back-resource-list', '-1', '0', '', '', '', '', 'BACKLEFT', 'resource-manager');
INSERT INTO `menu` VALUES ('b9fd6b19c6fb4a1b905b65d493c2c283', 'resource-manager', '资源管理', 'ios-folder', 'resource-manager', '/manager/resource', '-1', '2', '', '', '', '', 'BACKTOP', '');
INSERT INTO `menu` VALUES ('c36942c18642417d8a9a016a64e47909', 'java_study', 'JAVA 学习', '', '', '/wx/web/articles?label=java', 'd050b561c12c47f9b2797d5664042540', '0', 'VIEW', '', '', '', 'WECHAT', '');
INSERT INTO `menu` VALUES ('d050b561c12c47f9b2797d5664042540', 'wx_web_articles', '我的文章', '', '', '', '-1', '2', 'CLICK', '', '', '', 'WECHAT', '');
INSERT INTO `menu` VALUES ('d34c2efc1bff4db897139f102dfb29ad', 'manager-back-resource-add', '资源上传', 'md-add', 'resource-add', 'manager/resource?current=manager-back-resource-add', '-1', '2', '', '', '', '', 'BACKLEFT', 'resource-manager');
INSERT INTO `menu` VALUES ('e42758aa61a841249ccc91c5e65aab31', 'manager-wx-web-menus', '管理微网站菜单', '', 'manager-wx-web-menus', '/manager/menu?platform=WECHATWEB&current=manager-wx-web-menus', '-1', '4', '', '', '', '', 'BACKLEFT', 'menu-manager');

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
-- Records of menu_role
-- ----------------------------

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
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('f458442555794bb3a592394b2d291d16', 'ArrayList', 'MARKDOWN', '', '30589', '2019-01-03 16:17:15', 'markdown/md-585c75ed2b7f461dafd6d56430abb36e.md', '', null, null, '.md');

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
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sex` varchar(8) DEFAULT NULL,
  `descroption` varchar(255) DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('', 'admin', '0551872238f43bec237cc16c39beff79', null, null, null);

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

-- ----------------------------
-- Records of user_role
-- ----------------------------
