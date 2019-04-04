/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : cc_db

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-04-04 20:21:49
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('16', '超级管理员', 'pc859107393', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');

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
