/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : permission

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 07/04/2019 17:51:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `pid` int(11) DEFAULT '0' COMMENT '父ID',
                                  `type` tinyint(4) NOT NULL COMMENT '资源类型（1：菜单，2：按钮，3：操作）',
                                  `name` varchar(64) CHARACTER SET latin1 NOT NULL COMMENT '资源名称',
                                  `code` varchar(64) CHARACTER SET latin1 NOT NULL COMMENT '资源标识（或者叫权限字符串）',
                                  `uri` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '资源URI',
                                  `seq` int(11) DEFAULT '1' COMMENT '序号',
                                  `create_user` varchar(64) CHARACTER SET latin1 DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL,
                                  `update_user` varchar(64) CHARACTER SET latin1 DEFAULT NULL,
                                  `update_time` datetime DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `code` (`code`),
                                  KEY `idx_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO sys_permission (id, pid, type, name, code, uri, seq, create_user, create_time, update_user, update_time) VALUES (1, 0, 3, 'query', 'system:user:query', '/user/query', 1, 'system', '2019-03-03 18:50:17', 'system', '2019-03-03 18:50:20');
INSERT INTO sys_permission (id, pid, type, name, code, uri, seq, create_user, create_time, update_user, update_time) VALUES (2, 0, 3, 'delete', 'system:user:delete', '/user/delete', 1, 'system', '2026-02-03 17:06:41', null, null);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(32) NOT NULL COMMENT '角色名称',
                            `role_code` varchar(32) NOT NULL,
                            `role_description` varchar(64) DEFAULT NULL COMMENT '角色描述',
                            `create_user` varchar(64) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                            `update_user` varchar(64) DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO sys_role (id, role_name, role_code, role_description, create_user, create_time, update_user, update_time) VALUES (1, 'ADMIN', 'ADMIN', '管理员', 'system', '2019-02-12 11:14:41', null, null);
INSERT INTO sys_role (id, role_name, role_code, role_description, create_user, create_time, update_user, update_time) VALUES (2, 'USER', 'USER', '用户', 'system', '2019-02-12 11:15:37', null, null);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `role_id` int(11) NOT NULL COMMENT '角色ID',
                                       `permission_id` int(11) NOT NULL COMMENT '权限ID',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 1, 1);
INSERT INTO `sys_role_permission` VALUES (2, 1, 2);
INSERT INTO `sys_role_permission` VALUES (3, 2, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `username` varchar(64) NOT NULL COMMENT '账号',
                            `password` varchar(256) NOT NULL COMMENT '密码',
                            `nickname` varchar(64) NOT NULL COMMENT '昵称',
                            `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                            `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0：锁定，1：解锁）',
                            `create_user` varchar(64) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                            `update_user` varchar(64) DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO security_permission.sys_user (id, username, password, nickname, email, status, create_user, create_time, update_user, update_time) VALUES (1, 'admin', '$2a$10$KLGNptkyLLfeQmy6c.nO4.Pz.cRlHNDxXss1JZPyjVTwhD0tt88BS', '管理员', 'abc@123.com', 1, 'system', '2019-02-12 11:12:19', null, null);
INSERT INTO security_permission.sys_user (id, username, password, nickname, email, status, create_user, create_time, update_user, update_time) VALUES (2, 'zhangsan', '$2a$10$KLGNptkyLLfeQmy6c.nO4.Pz.cRlHNDxXss1JZPyjVTwhD0tt88BS', '张三', 'zhangsan@126.com', 1, 'system', '2019-02-12 11:13:27', null, null);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `user_id` int(11) NOT NULL COMMENT '用户ID',
                                 `role_id` int(11) NOT NULL COMMENT '角色ID',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 1, 2);
INSERT INTO `sys_user_role` VALUES (3, 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
