/*
Navicat MySQL Data Transfer

Source Server         : 120.25.226.197
Source Server Version : 50537
Source Host           : 120.25.226.197:3306
Source Database       : wlp

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2016-03-12 11:04:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wlp_activecode
-- ----------------------------
DROP TABLE IF EXISTS `wlp_activecode`;
CREATE TABLE `wlp_activecode` (
  `ID` varchar(64) NOT NULL,
  `EMAIL` varchar(255) NOT NULL COMMENT '用户',
  `CODE` varchar(255) NOT NULL COMMENT '激活码',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '状态：0--未使用，1--已使用',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '生成时间',
  `SHARE_EMAIL` varchar(255) DEFAULT NULL,
  `SHARE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wlp_activecode
-- ----------------------------
INSERT INTO `wlp_activecode` VALUES ('1', 'hukangwei@qq.com', '1001', '0', '2016-02-24 22:28:55', 'admin@qq.com', '2016-02-21 10:54:54');
INSERT INTO `wlp_activecode` VALUES ('2', 'hukangwei@qq.com', '1002', '1', '2016-02-24 22:28:57', 'admin@qq.com', '2016-02-21 10:56:02');
INSERT INTO `wlp_activecode` VALUES ('3', 'hukangwei@qq.com', '1003', '0', '2016-02-24 22:28:58', 'admin@qq.com', '2016-02-21 11:00:45');
INSERT INTO `wlp_activecode` VALUES ('4', 'hukangwei@qq.com', '1004', '0', '2016-02-24 22:29:00', 'admin@qq.com', '2016-02-21 11:03:22');
INSERT INTO `wlp_activecode` VALUES ('5', 'hukangwei@qq.com', '1005', '0', '2016-02-24 22:29:02', 'admin@qq.com', '2016-02-21 11:04:03');
INSERT INTO `wlp_activecode` VALUES ('6', 'gmh1', '1006', '1', '2016-02-24 22:30:19', 'admin@qq.com', '2016-03-12 10:38:48');
INSERT INTO `wlp_activecode` VALUES ('7', 'admin@qq.com', '1007', '0', '2016-02-24 22:30:20', null, null);
INSERT INTO `wlp_activecode` VALUES ('8', 'admin@qq.com', '1008', '0', '2016-02-24 22:30:22', null, null);
INSERT INTO `wlp_activecode` VALUES ('9', 'admin@qq.com', '1009', '0', '2016-02-24 22:30:18', null, null);

-- ----------------------------
-- Table structure for wlp_pair_log
-- ----------------------------
DROP TABLE IF EXISTS `wlp_pair_log`;
CREATE TABLE `wlp_pair_log` (
  `ID` varchar(64) NOT NULL,
  `PAIR_MONEY` decimal(10,0) NOT NULL,
  `FROM_USER` varchar(255) DEFAULT NULL,
  `TO_USER` varchar(255) DEFAULT NULL,
  `ORDER_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `PAIR_TIME` timestamp NULL DEFAULT NULL COMMENT '配对时间',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '状态：0--未完成，1--已完成',
  `PAY_TYPE` varchar(255) NOT NULL DEFAULT '' COMMENT '支付方式bank银行alipay支付宝wechat--微信',
  `EXTRAK_TYPE` varchar(255) DEFAULT NULL COMMENT '提现类型：dynamic--动态体现,help--互助提现',
  `ORDER_PIC` varchar(255) DEFAULT NULL COMMENT '交易图片地址，一般为一张转帐截图',
  `REMARK` varchar(2000) DEFAULT NULL,
  `TO_OLD_BALANCE` decimal(10,0) DEFAULT NULL COMMENT '接受方原余额',
  `TO_BALANCE` decimal(10,0) DEFAULT NULL COMMENT '接受方余额',
  `FROM_OLD_BALANCE` decimal(10,0) DEFAULT NULL COMMENT '提供方原余额',
  `FROM_BALANCE` decimal(10,0) DEFAULT NULL COMMENT '提供方余额',
  `TYPE` decimal(10,0) DEFAULT NULL COMMENT '余额类型：1-本金，2-分红',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wlp_pair_log
-- ----------------------------

-- ----------------------------
-- Table structure for wlp_pair_pipeline
-- ----------------------------
DROP TABLE IF EXISTS `wlp_pair_pipeline`;
CREATE TABLE `wlp_pair_pipeline` (
  `ID` varchar(64) NOT NULL COMMENT '交易配对管道表',
  `LOOP` int(11) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wlp_pair_pipeline
-- ----------------------------

-- ----------------------------
-- Table structure for wlp_user
-- ----------------------------
DROP TABLE IF EXISTS `wlp_user`;
CREATE TABLE `wlp_user` (
  `ID` varchar(64) NOT NULL,
  `USER_NAME` varchar(255) NOT NULL COMMENT '昵称',
  `MOBILE_PHONE` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `EMAIL` varchar(255) NOT NULL COMMENT '邮箱(登录账号)',
  `LOGIN_PASSWORD` varchar(50) NOT NULL COMMENT '登录密码',
  `TRANS_PASSWORD` varchar(50) NOT NULL COMMENT '交易密码',
  `REC_EMAIL` varchar(255) DEFAULT NULL COMMENT '推荐人邮箱',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `WECHAT` varchar(255) DEFAULT NULL COMMENT '微信号',
  `ALIPAY` varchar(255) DEFAULT NULL COMMENT '支付宝账号',
  `BANK_NAME` varchar(255) DEFAULT NULL COMMENT '银行名称',
  `BANK_USERNAME` varchar(255) DEFAULT NULL COMMENT '银行账户名称',
  `BANK_ACCT` varchar(255) DEFAULT NULL COMMENT '银行账号',
  `STATUS` varchar(10) DEFAULT '0' COMMENT '状态1-激活互助，0-未激活互助',
  `ACTIVE_TIME` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `EMAIL_Unique` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wlp_user
-- ----------------------------
INSERT INTO `wlp_user` VALUES ('09a4c540-66cd-43b4-9fd2-30fd461d18c1', 'xxxx', 'xxxxxx', 'xxxxxxxxx', 'xxxxxx', 'xxxxxx', 'admin@qq.com', null, null, null, null, null, null, '0', null);
INSERT INTO `wlp_user` VALUES ('1001', '管理员', '1377777777', 'admin@qq.com', '777777', '888888', 'admin@qq.com', '39', '999', '999', '4', '999', '999', '1', null);
INSERT INTO `wlp_user` VALUES ('1002', '王大锤', '138888888', 'hukangwei@qq.com', '888888', '888888', 'admin@qq.com', '5', null, null, null, null, null, '1', null);
INSERT INTO `wlp_user` VALUES ('1003', '易中天', null, 'lisi@qq.com', '888888', '888888', 'admin@qq.com', '5', null, null, null, null, null, '0', null);
INSERT INTO `wlp_user` VALUES ('46990be9-8a16-4a2d-83b0-cdce72601c1c', 'gmh1', '18977666677', 'gmh1', '666666', '888888', 'admin@qq.com', '35', null, null, null, null, null, '0', null);
INSERT INTO `wlp_user` VALUES ('6d355266-636c-4f4c-a27a-643947bc66df', 'saf', '13444444444', 'sdaf@@qq.com', '666666', '777777', 'lisi@qq.com', null, null, null, null, null, null, '0', null);
INSERT INTO `wlp_user` VALUES ('d460f52f-87da-4ab2-8dfe-b9bb8c5ad650', 'xxx', 'xxxxxx', 'xxxxx', 'xxxxxx', 'xxxxxx', 'admin@qq.com', null, null, null, null, null, null, '0', null);
INSERT INTO `wlp_user` VALUES ('edf9bafc-e6e9-4561-acbd-4f07bde4730a', 'gmh', '18974870733', 'gmh', '666666', '777777', 'admin@qq.com', '26', null, null, null, null, null, '0', null);
INSERT INTO `wlp_user` VALUES ('eefe79c0-eccb-4ebc-aaa8-02220d5dac86', 'yyy', '13455555555', 'adaf@qq.com', '888888', '888888', '', null, null, null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for wlp_wallet
-- ----------------------------
DROP TABLE IF EXISTS `wlp_wallet`;
CREATE TABLE `wlp_wallet` (
  `ID` varchar(64) NOT NULL,
  `EMAIL` varchar(255) NOT NULL COMMENT '钱包帐号',
  `CAPITAL` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '本金',
  `BONUS` decimal(10,0) DEFAULT NULL COMMENT '分红',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wlp_wallet
-- ----------------------------
