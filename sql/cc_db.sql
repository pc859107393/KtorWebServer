/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : cc_db

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-05-02 17:43:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `uri` varchar(128) DEFAULT NULL COMMENT '接口地址',
  `icon` varchar(128) DEFAULT NULL COMMENT '接口图标',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单默认0，子菜单指定父菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员菜单';

-- ----------------------------
-- Records of admin_menu
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `login_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '-1' COMMENT '用户登录名称',
  `password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '-1' COMMENT '用户密码，建议算法：sha256(MD5(明文)+salt)',
  `duty` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户职位',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `used` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1', '超级管理员', 'pc859107393', '794582130249CA3EEC18A4CFA894022B40108F730EC9413F62A8DF1A4206424B', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('2', '管理员1', '228568859', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('3', '管理员999', '12345678', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('4', '管理员9992', 'q23rqw', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('5', 'wwrwer', '1232qewdaf', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('6', 'wrewer', '123eqwd235657i68755', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('7', 'wersf', '78uythr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('8', 'sfsf', '098uiyj', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('9', '管理员999', '345yetg', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('10', 'dvsdv', '5ter', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('11', 'yujy', '34r3qwe', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('12', 'ytj', 'eqwsa', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('13', '管理员999', '23rwefretwr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('14', 'yujt67', '234w', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('15', 'j6', '67u5yrtge45i7ut', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('16', '56u56u', '654ytefwret4y53tw4er', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('17', '管理员999', '23rw3t4wefgewt', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('19', '56u5u', '234terfwe24wefsaa', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('20', '管理员999', '12qewt2qr23rqy353wr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('22', 'gjyt', '26y3wrt23', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('24', '管理员999', '7o8865utyuirytgj', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('26', 'fghfgh', '53654wesrttrqw', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('29', '管理员999', '254wrq31423we', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('30', 'sdfsfw', '21eqwdaew2q', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('38', '管理员999', '123qad4eyr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('39', 'wefwr23sdfd', 'ikyujg6turth', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('42', '管理员999', '8i45yr', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('43', 'sdfsf', 'qeewtef', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('44', '管理员999', '5y4erdg', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('45', 'sfsf', '3terwt', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
INSERT INTO `admin_user` VALUES ('46', 'sfsfadzcx', '3terwtqewq', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');

-- ----------------------------
-- Table structure for widgets
-- ----------------------------
DROP TABLE IF EXISTS `widgets`;
CREATE TABLE `widgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `dateUpdated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='数据而已';

-- ----------------------------
-- Records of widgets
-- ----------------------------
INSERT INTO `widgets` VALUES ('3', 'asdad砸场子', '564', '2019-03-04 15:03:52');
INSERT INTO `widgets` VALUES ('4', '刚好风格', '677', '2019-04-04 15:04:03');

-- ----------------------------
-- Function structure for getChildLst
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildLst`(rootId int) RETURNS varchar(1000) CHARSET utf8
BEGIN
        DECLARE sTemp VARCHAR(1000);
        DECLARE sTempChd VARCHAR(1000);

        SET sTemp = '^';
        SET sTempChd =cast(rootId as CHAR);

        WHILE sTempChd is not null DO
            SET sTemp = concat(sTemp,',',sTempChd);
            SELECT group_concat(id) INTO sTempChd FROM `user` where FIND_IN_SET(agent_id,sTempChd)>0;
        END WHILE;
        RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getcurval
-- ----------------------------
DROP FUNCTION IF EXISTS `getcurval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getcurval`(seq_name varchar(64)) RETURNS int(11)
BEGIN
         DECLARE VALUE INTEGER;
         DECLARE _increment INTEGER;
         SET VALUE = 10000;
         SET _increment = 1;
         SELECT current_value,increment INTO VALUE, _increment
                   FROM sequence
                   WHERE NAME = seq_name;
         REPLACE INTO sequence(NAME,current_value,increment) VALUES (seq_name, VALUE + _increment,_increment);
         RETURN VALUE;
         END
;;
DELIMITER ;
