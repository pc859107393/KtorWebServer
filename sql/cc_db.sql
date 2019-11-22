/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : cc_db

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 23/11/2019 01:39:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_menu`;
CREATE TABLE `sys_admin_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `uri` varchar(128) DEFAULT NULL COMMENT '接口地址',
  `icon` varchar(128) DEFAULT NULL COMMENT '接口图标',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单默认0，子菜单指定父菜单ID',
  `sort` int(11) NOT NULL COMMENT '菜单排序',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='管理员菜单';

-- ----------------------------
-- Records of sys_admin_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin_menu` VALUES (3, '测试父权限', NULL, 'setting', 0, 1, '2019-05-21 06:55:51', '2019-05-21 06:56:02');
INSERT INTO `sys_admin_menu` VALUES (4, '测试子权限', 'www.baidu.com', 'url', 3, 1, '2019-05-21 06:56:05', '2019-05-21 06:56:07');
INSERT INTO `sys_admin_menu` VALUES (5, '测试子权限01', 'www.baidu.com', 'url', 3, 2, '2019-05-21 06:56:09', '2019-05-21 06:56:11');
INSERT INTO `sys_admin_menu` VALUES (6, '管理员-菜单权限', NULL, 'menu', 0, 2, '2019-06-24 04:46:12', '2019-06-24 04:46:14');
INSERT INTO `sys_admin_menu` VALUES (7, '刷新菜单', 'aaa', 'url', 6, 1, '2019-06-24 04:46:49', '2019-06-24 04:46:51');
INSERT INTO `sys_admin_menu` VALUES (8, '管理员-管理后台用户', NULL, 'manager', 0, 2, '2019-06-24 04:47:33', '2019-06-24 04:47:35');
INSERT INTO `sys_admin_menu` VALUES (9, '获取所有用户', 'getAllFromCache', 'url', 0, 1, '2019-06-24 04:49:19', '2019-06-24 04:49:23');
COMMIT;

-- ----------------------------
-- Table structure for sys_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_user`;
CREATE TABLE `sys_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `login_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '-1' COMMENT '用户登录名称',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '-1' COMMENT '用户密码，建议算法：sha256(MD5(明文)+salt)',
  `duty` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户职位',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `used` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_admin_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin_user` VALUES (1, '超级管理员', 'pc859107393', '92BCA834278DC708BD0AA463DABC37A0F377C475A4159F37F4A8F7DF71EDB87F', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (2, '管理员1', '228568859', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 0);
INSERT INTO `sys_admin_user` VALUES (3, '管理员999', '12345678', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (4, '管理员9992', 'q23rqw', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (5, 'wwrwer', '1232qewdaf', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (6, 'wrewer', '123eqwd235657i68755', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (7, 'wersf', '78uythr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (8, 'sfsf', '098uiyj', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (9, '管理员999', '345yetg', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (10, 'dvsdv', '5ter', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (11, 'yujy', '34r3qwe', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (12, 'ytj', 'eqwsa', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (13, '管理员999', '23rwefretwr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (14, 'yujt67', '234w', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (15, 'j6', '67u5yrtge45i7ut', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (16, '56u56u', '654ytefwret4y53tw4er', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (17, '管理员999', '23rw3t4wefgewt', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (19, '56u5u', '234terfwe24wefsaa', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (20, '管理员999', '12qewt2qr23rqy353wr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (22, 'gjyt', '26y3wrt23', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (24, '管理员999', '7o8865utyuirytgj', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (26, 'fghfgh', '53654wesrttrqw', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (29, '管理员999', '254wrq31423we', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (30, 'sdfsfw', '21eqwdaew2q', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (38, '管理员999', '123qad4eyr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (39, 'wefwr23sdfd', 'ikyujg6turth', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (42, '管理员999', '8i45yr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (43, 'sdfsf', 'qeewtef', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (44, '管理员999', '5y4erdg', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (45, 'sfsf', '3terwt', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
INSERT INTO `sys_admin_user` VALUES (46, 'sfsfadzcx', '3terwtqewq', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_site_con
-- ----------------------------
DROP TABLE IF EXISTS `sys_site_con`;
CREATE TABLE `sys_site_con` (
  `name` varchar(512) NOT NULL,
  `value` varchar(4096) DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `site_con_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_site_con
-- ----------------------------
BEGIN;
INSERT INTO `sys_site_con` VALUES ('admin', '[3,4,5,6,7,8,9]');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(64) DEFAULT NULL COMMENT '账号',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `salt` varchar(255) DEFAULT NULL COMMENT '密码盐',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `id_card` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `delete_status` tinyint(1) DEFAULT '1' COMMENT '账号状态(0：禁用，1：正常)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `index_account` (`account`),
  KEY `index_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
