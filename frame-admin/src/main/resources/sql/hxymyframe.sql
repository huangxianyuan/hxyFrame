/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : hxymyframe

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-25 17:58:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父菜单id',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(1000) DEFAULT NULL COMMENT '菜单链接',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  ` sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` varchar(6) DEFAULT NULL COMMENT '状态（0显示，1隐藏)',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `update_id` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '角色id',
  `office_id` varchar(32) NOT NULL COMMENT '机构id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(64) DEFAULT NULL COMMENT '角色代码',
  `status` varchar(6) DEFAULT NULL COMMENT '角色状态(0正常 1禁用）',
  `role_type` varchar(6) DEFAULT NULL COMMENT '角色类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_id` varchar(32) DEFAULT NULL COMMENT '新增人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限角色表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` varchar(32) NOT NULL COMMENT 'id主键',
  `office_id` varchar(32) NOT NULL COMMENT '机构id',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `login_name` varchar(64) NOT NULL COMMENT '登陆帐户',
  `pass_word` varchar(128) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '新建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(6) DEFAULT NULL COMMENT '状态(0正常 -1禁用)',
  `phone` varchar(64) DEFAULT NULL COMMENT '电话',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `update_id` varchar(32) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES ('56911adfd61f41e7a99135e87ff016f3', '', 'hxy', 'hxy', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_users` VALUES ('740ae43fd7434a7683a6c2db43c8b2e9', '11', 'huangxianyuan', 'hxy', null, '2017-04-25 17:32:48', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
