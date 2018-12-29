/*
Navicat MySQL Data Transfer

Source Server         : localSql
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : smallrain_back

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-12-29 18:39:21
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
INSERT INTO `menu` VALUES ('b61bc4a319c94086b47e81c9224a48c5', 'manager-back-resource-list', '资源列表', 'ios-list-box-outline', 'resource-list', 'manager/resource?current=manager-back-resource-list', '-1', '0', '', '', '', '', 'BACKLEFT', 'resource-manager');
INSERT INTO `menu` VALUES ('b9fd6b19c6fb4a1b905b65d493c2c283', 'resource-manager', '资源管理', 'ios-folder', 'resource-manager', '/manager/resource', '-1', '2', '', '', '', '', 'BACKTOP', '');
INSERT INTO `menu` VALUES ('d34c2efc1bff4db897139f102dfb29ad', 'manager-back-resource-add', '资源上传', 'md-add', 'resource-add', 'manager/resource?current=manager-back-resource-add', '-1', '2', '', '', '', '', 'BACKLEFT', 'resource-manager');

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
INSERT INTO `resource` VALUES ('03b66df5eabd4412a42f97b3cbfc8304', 'ArrayList.md', 'MARKDOWN', null, '30466', '2018-11-21 16:37:54', 'markdown/md-6c7696ae0d494c6dbf218dc5dab16b3a.md', null, null, null, '.md');
INSERT INTO `resource` VALUES ('07d06564133e4a06b5a47621c31007f4', '头像', 'IMAGE', '', '29076', '2018-12-25 15:42:36', 'image/img-cf4350309b6e4a7089b739e2c280579f.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('0de8d3f2e21640f586da7f0d21cadf0c', 'vsftpd', 'DEFAULT', '', '5030', '2018-12-26 09:34:22', 'default/def-4c4567e467714f2db6571a7262293698.conf', '', null, null, '.conf');
INSERT INTO `resource` VALUES ('0f09d29ba91c4ad882d935504ab8c5cc', '加班二维码.jpg', 'IMAGE', null, '20344', '2018-11-21 16:32:35', 'image/img-c7b973464f434d26a974bf5216c80b2f.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('14fe86e01df84e1d86b5c4e42ca5618f', '微信图片_20180920084431', 'IMAGE', null, '29076', '2018-12-25 13:52:25', 'image/img-6142bb4ceec14afa91eaa6edd818edc4.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('15ad5126894940dd85989e4cbc395a66', '加班二维码', 'IMAGE', '', '20344', '2018-12-29 13:46:24', 'image/img-5f95f4ff11194794b70349f3111e95f4.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('2098a766fcc2445e8d46e8c507750a4b', '微信截图_20181224141016', 'IMAGE', null, '48838', '2018-12-25 13:51:58', 'image/img-6c8e32d6272f4ecea5d6410dc18c0e74.png', null, null, null, '.png');
INSERT INTO `resource` VALUES ('26820057509b4df28238268cdf963fb2', '加班二维码', 'IMAGE', null, '20344', '2018-12-25 13:52:20', 'image/img-ce3721d231a14b248dbb92ac3e60b46d.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('2b404fe0f09e4cba9c7fcc9330d24fdb', 'test', 'TXT', '', '3111', '2018-12-29 14:30:14', 'text/txt-f4388f741b2043aa8c2aa8f7255a9eba.txt', '', null, null, '.txt');
INSERT INTO `resource` VALUES ('2ffd83585bc145c589a0e2ba9bc157bd', 'new name', 'IMAGE', 'image', '48838', '2018-12-25 15:44:51', 'image/img-b037b7cb41b74616b6e9942d5c8eff06.png', 'test image', null, null, '.png');
INSERT INTO `resource` VALUES ('3630218401144365ba97e7f43a7bb08e', '头像', 'IMAGE', null, '29076', '2018-12-25 10:11:30', 'image/img-7f99bae2326b4c6eb0270c5a6a544a6b.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('3df2b15642254da291e2d71e29d3cd92', '头像', 'IMAGE', null, '29076', '2018-12-25 10:28:02', 'image/img-8a9ac8f31459431f8beafea944e8344d.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('41b854b97c204bd8b0096d22b6a063a7', '微信截图_20181224141016', 'IMAGE', '', '48838', '2018-12-25 16:24:49', 'image/img-df16864960a44fa18b420909324d256e.png', '', null, null, '.png');
INSERT INTO `resource` VALUES ('427f036588104cc2890159c234966727', '头像', 'IMAGE', null, '29076', '2018-12-25 10:14:09', 'image/img-b8a053c18c194a709bc1f83c75b397df.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('4b692ac29b59438886fe59ed642d8cf7', 'mysql007.bat', 'TXT', '', '39', '2018-12-29 11:26:55', 'text/txt-dad91a45d7b94f3b8896544e73934d7a.txt', '', null, null, '.txt');
INSERT INTO `resource` VALUES ('523377138fea408587c6444ba407a10f', '微信截图_20181224141016', 'IMAGE', null, '48838', '2018-12-25 15:22:00', 'image/img-74c86c8a183840229d83ab32e4b39569.png', null, null, null, '.png');
INSERT INTO `resource` VALUES ('698aee911fe24a7a97c0607248067d15', '微信截图_20181224141016', 'IMAGE', '', '48838', '2018-12-26 09:39:37', 'image/img-760464cd48e148588a694186cb735bd1.png', '', null, null, '.png');
INSERT INTO `resource` VALUES ('6c35af4dc0ec4e4ab0eb34a7e8cc2507', 'new name2', 'IMAGE', 'image', '29076', '2018-12-25 15:45:32', 'image/img-3290849a33594f39b3717599ad14f4b9.jpg', 'testimage', null, null, '.jpg');
INSERT INTO `resource` VALUES ('7b87d4f84c644e7eba8493772b2b6495', '微信截图_20181224141016', 'IMAGE', '', '48838', '2018-12-25 15:49:55', 'image/img-f43e549e635d4af3ad1c782a6bd9adc7.png', '', null, null, '.png');
INSERT INTO `resource` VALUES ('8c96b21dec73427b98bf4b0d238b493a', '头像', 'IMAGE', '', '29076', '2018-12-29 11:19:27', 'image/img-5620f7e8f9134de898b6f8d59678bcf2.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('96ad7f33a6ea4888a399570b99cb0479', 'vsftpd', 'DEFAULT', '', '5030', '2018-12-29 10:56:53', 'default/def-65553f4c110148f69c89ef8f4cb722a2.conf', '', null, null, '.conf');
INSERT INTO `resource` VALUES ('96f65ff010c449b68dfa9bd66af64885', 'ArrayList-Grow', 'MARKDOWN', null, '13768', '2018-12-24 17:39:38', 'markdown/md-7c1ac052f4294fa1adda3c172859bae1.md', null, null, null, '.md');
INSERT INTO `resource` VALUES ('9ded85f4d94f4ff48cc671f7670198fa', '加班二维码.jpg', 'IMAGE', null, '20344', null, '加班二维码.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('9f65ad5147fb4d548901055d2502ea02', '微信截图_20181224141016', 'IMAGE', '', '48838', '2018-12-25 17:03:10', 'image/img-b208822e94ba4aa187272d53b882fb13.png', '', null, null, '.png');
INSERT INTO `resource` VALUES ('a748b88a3f96485daf45408691a0b8fd', '微信截图_20181224141016', 'IMAGE', null, '48838', '2018-12-24 17:34:12', 'image/img-6aaaf23ecb874ad5b8be60fb30cce07d.png', null, null, null, '.png');
INSERT INTO `resource` VALUES ('b4b5e84a49e947918036dad9e9d47059', 'ArrayList.md', 'DEFAULT', null, '30466', '2018-11-21 16:36:50', 'default/def-3b98f2093dbf4684b6123b6b2874ca02.md', null, null, null, '.md');
INSERT INTO `resource` VALUES ('ba9c5ab0c1f24f8ba8a00dc94b721e47', '加班二维码', 'IMAGE', '', '20344', '2018-12-29 11:05:42', 'image/img-238e5de2009b4473980aee44ec8d81cb.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('bef49312fd5f4b1fac151aeb93f38a51', '微信截图_20181224141016', 'IMAGE', '', '48838', '2018-12-25 16:29:48', 'image/img-0e39cdb82c754f02adc73e8734beaf26.png', '', null, null, '.png');
INSERT INTO `resource` VALUES ('c090f3993c834d7dbc80cb4177339e0b', '头像', 'IMAGE', '', '29076', '2018-12-25 18:04:55', 'image/img-028340e14c8c4429b31cfeca3594dd28.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('c175b2b89bb3431cb893d75a4b866240', '微信图片_20180920084431', 'IMAGE', '', '29076', '2018-12-29 12:27:17', 'image/img-d1101b66f82646759b748c7edad9817a.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('c3bd3c56ef1041f8953b5f323af04c8d', '加班二维码', 'IMAGE', '', '20344', '2018-12-29 18:23:54', 'image/img-a17e9a7ba80540748e4493c828924bc4.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('c714c392fc4b48029c2a9a1d80cfcaf6', 'timg (2)', 'IMAGE', '', '495485', '2018-12-29 11:19:16', 'image/img-8c40eae760634d7eab73bff84f3123ad.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('c8fd118491694e25bd72af0190b5bc95', '微信截图_20181224141016', 'IMAGE', '', '48838', '2018-12-25 18:04:56', 'image/img-dedcfe493dd047d986d5c018aecc035b.png', '', null, null, '.png');
INSERT INTO `resource` VALUES ('d4dc582503b64ed498e639383e4d7fec', '头像', 'IMAGE', null, '29076', '2018-12-24 17:38:57', 'image/img-35d9e2f17f0f4041b17cf8593641275e.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('d7f351a616c042f488ee0fb61c3a25fc', '加班二维码', 'IMAGE', null, '20344', '2018-12-25 13:52:23', 'image/img-e77b3aeddfb6442fbe114dabc4c9d0bf.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('da75c1fad11e4d51880e1a826dd2a6e0', 'vsftpd', 'DEFAULT', '', '5030', '2018-12-26 09:39:47', 'default/def-61fcae1a1a0f41de8e059791a284f13c.conf', '', null, null, '.conf');
INSERT INTO `resource` VALUES ('db08afe9905e476389a2f38a40f7f3e0', '微信图片_20180920084431', 'IMAGE', '', '29076', '2018-12-25 17:03:12', 'image/img-e33e7b10eb2e43829ad66d474a3d2ba2.jpg', '', null, null, '.jpg');
INSERT INTO `resource` VALUES ('db9765f5895e4c6da53767659f1f9117', '加班二维码', 'IMAGE', null, '20344', '2018-12-25 13:52:19', 'image/img-1b92357188b94501bd39f468c39649bb.jpg', null, null, null, '.jpg');
INSERT INTO `resource` VALUES ('eb3cfcbc013e417d8a692bce7bb8eb22', 'test', 'IMAGE', 'image', '29076', '2018-12-25 17:49:32', 'image/img-9a491d99fef1493f9f3e4ec9e6dc1e2c.jpg', 'testtest', null, null, '.jpg');
INSERT INTO `resource` VALUES ('ffeb7b841fd54999973d4d055e7875df', '头像', 'IMAGE', '', '29076', '2018-12-26 10:40:26', 'image/img-eeaf92da0ea444f9ae756855527cb51b.jpg', '', null, null, '.jpg');

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
