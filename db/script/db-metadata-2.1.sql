/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : metadata

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 21/02/2022 11:23:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meta_auth
-- ----------------------------
DROP TABLE IF EXISTS `meta_auth`;
CREATE TABLE `meta_auth` (
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT NULL,
  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `code` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '权限编码',
  `name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '权限名',
  `group` varchar(255) DEFAULT NULL COMMENT '分组',
  `type` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '-1' COMMENT '鉴权类型',
  `uri` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '接口uri',
  `meta_code` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `build_in` bit(1) DEFAULT b'0' COMMENT '内建权限',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_code` (`code`) COMMENT '权限编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限控制表';

-- ----------------------------
-- Records of meta_auth
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_change_log
-- ----------------------------
DROP TABLE IF EXISTS `meta_change_log`;
CREATE TABLE `meta_change_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `object_code` varchar(64) DEFAULT NULL COMMENT '元对象',
  `table_name` varchar(64) DEFAULT NULL COMMENT '表名',
  `pkey` varchar(32) DEFAULT NULL COMMENT '主键字段',
  `pvalue` varchar(32) DEFAULT NULL COMMENT '主键值',
  `action` varchar(32) DEFAULT NULL COMMENT '动作',
  `olddata` json DEFAULT NULL COMMENT '旧数据',
  `newdata` json DEFAULT NULL COMMENT '新数据',
  `diff` text COMMENT '差异',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据变化日志 ';

-- ----------------------------
-- Records of meta_change_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_component
-- ----------------------------
DROP TABLE IF EXISTS `meta_component`;
CREATE TABLE `meta_component` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `cn` varchar(32) DEFAULT NULL COMMENT '中文名称',
  `en` varchar(32) DEFAULT NULL COMMENT '英文名称',
  `code` varchar(64) DEFAULT NULL COMMENT '组件编码',
  `config` json DEFAULT NULL COMMENT '配置信息',
  `version` int(11) DEFAULT NULL COMMENT '版本信息',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件注册表 ';

-- ----------------------------
-- Records of meta_component
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_component_instance
-- ----------------------------
DROP TABLE IF EXISTS `meta_component_instance`;
CREATE TABLE `meta_component_instance` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `code` varchar(128) DEFAULT NULL COMMENT '配置编码',
  `comp_code` varchar(32) DEFAULT NULL COMMENT '组件编码',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `dest_object` varchar(128) DEFAULT NULL COMMENT '目标对象',
  `name` varchar(128) DEFAULT NULL COMMENT '描述',
  `config` json DEFAULT NULL COMMENT '配置',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件配置关系表 ';

-- ----------------------------
-- Records of meta_component_instance
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_config
-- ----------------------------
DROP TABLE IF EXISTS `meta_config`;
CREATE TABLE `meta_config` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `module` varchar(1024) DEFAULT NULL COMMENT '模块',
  `module_code` varchar(1024) DEFAULT NULL COMMENT '模块名',
  `config` text COMMENT '配置',
  `version` varchar(32) DEFAULT NULL COMMENT '版本',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置 ';

-- ----------------------------
-- Records of meta_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_dict
-- ----------------------------
DROP TABLE IF EXISTS `meta_dict`;
CREATE TABLE `meta_dict` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `pid` varchar(32) DEFAULT NULL COMMENT '父层标志',
  `dict_type` varchar(64) DEFAULT NULL COMMENT '字典类别',
  `is_sys` varchar(1) DEFAULT '0' COMMENT '是否系统默认',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `value` varchar(128) DEFAULT NULL COMMENT '值',
  `live_flag` varchar(1) DEFAULT '1' COMMENT '有效',
  `order_num` varchar(32) DEFAULT NULL COMMENT '排序',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表 ';

-- ----------------------------
-- Records of meta_dict
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_exception
-- ----------------------------
DROP TABLE IF EXISTS `meta_exception`;
CREATE TABLE `meta_exception` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `exp_title` varchar(128) DEFAULT NULL COMMENT '异常名',
  `exp_chain` text COMMENT '异常堆栈',
  `exp_msg` text COMMENT '异常消息',
  `ext_url` varchar(1024) DEFAULT NULL COMMENT '请求url',
  `req_data` varchar(3072) DEFAULT NULL COMMENT '请求内容',
  `res_data` varchar(3072) DEFAULT NULL COMMENT '响应内容',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Meta异常 ';

-- ----------------------------
-- Records of meta_exception
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_feature
-- ----------------------------
DROP TABLE IF EXISTS `meta_feature`;
CREATE TABLE `meta_feature` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `type` varchar(32) DEFAULT NULL COMMENT '功能类型',
  `name` varchar(32) DEFAULT NULL COMMENT '功能名',
  `code` varchar(32) DEFAULT NULL COMMENT '功能编码',
  `config` json DEFAULT NULL COMMENT '配置',
  `op_config` json DEFAULT NULL COMMENT '操作配置',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `build_in` bit(1) DEFAULT b'0' COMMENT '内建功能',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能配置 ';

-- ----------------------------
-- Records of meta_feature
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_field
-- ----------------------------
DROP TABLE IF EXISTS `meta_field`;
CREATE TABLE `meta_field` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `object_code` varchar(64) DEFAULT NULL COMMENT '对象编码',
  `field_code` varchar(64) DEFAULT NULL COMMENT '字段编码',
  `is_primary` varchar(1) DEFAULT NULL COMMENT '是否主键',
  `en` varchar(128) DEFAULT NULL COMMENT '英文',
  `cn` varchar(128) DEFAULT NULL COMMENT '中文',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `db_type` varchar(128) DEFAULT NULL COMMENT '数据类型',
  `db_type_length` varchar(32) DEFAULT NULL COMMENT '数据长度',
  `java_type` varchar(128) DEFAULT NULL COMMENT 'JAVA类型',
  `config` json DEFAULT NULL COMMENT '配置',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元字段 ';

-- ----------------------------
-- Records of meta_field
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_menu
-- ----------------------------
DROP TABLE IF EXISTS `meta_menu`;
CREATE TABLE `meta_menu` (
  `id` varchar(32) NOT NULL,
  `pid` varchar(32) DEFAULT NULL COMMENT '父ID',
  `title` varchar(255) NOT NULL COMMENT '菜单名',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `disable` bit(1) DEFAULT b'1' COMMENT '生效',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `meta` json DEFAULT NULL COMMENT '配置信息',
  `build_in` bit(1) DEFAULT b'0' COMMENT '内建菜单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of meta_menu
-- ----------------------------
BEGIN;
INSERT INTO `meta_menu` VALUES ('7bcfbe31357f48bf8c88072a18208599', '', '用户管理', b'0', b'1', 'el-icon-s-custom', '', 2, '2022-02-16 19:06:01', 'SYSTEM', NULL, NULL, '', '{}', b'1');
INSERT INTO `meta_menu` VALUES ('96eab66157be4165b9916d6f8b079dd9', '7bcfbe31357f48bf8c88072a18208599', '角色列表', b'0', b'0', 'el-icon-s-custom', '/role', 1, '2022-02-16 19:02:23', 'SYSTEM', NULL, NULL, '', '{}', b'1');
INSERT INTO `meta_menu` VALUES ('f49ce02324d94fa698a0db718e380074', '7bcfbe31357f48bf8c88072a18208599', '用户列表', b'0', b'1', 'el-icon-user-solid', '/user', 0, '2022-02-16 19:01:55', 'SYSTEM', NULL, NULL, '', '{}', b'1');
COMMIT;

-- ----------------------------
-- Table structure for meta_object
-- ----------------------------
DROP TABLE IF EXISTS `meta_object`;
CREATE TABLE `meta_object` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `code` varchar(64) NOT NULL COMMENT '对象编码',
  `name` varchar(128) DEFAULT NULL COMMENT '对象名',
  `table_name` varchar(64) NOT NULL COMMENT '表名',
  `schema_name` varchar(64) NOT NULL COMMENT '库名',
  `primarys` varchar(128) DEFAULT NULL COMMENT '主键组',
  `is_sys` varchar(1) DEFAULT '0' COMMENT '系统模块',
  `config` json DEFAULT NULL COMMENT '配置',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_metaobject_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元对象 ';

-- ----------------------------
-- Records of meta_object
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_role
-- ----------------------------
DROP TABLE IF EXISTS `meta_role`;
CREATE TABLE `meta_role` (
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT NULL,
  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名',
  `remark` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '角色说明',
  `group` varchar(255) DEFAULT NULL COMMENT '分组',
  `build_in` bit(1) DEFAULT NULL COMMENT '内建角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of meta_role
-- ----------------------------
BEGIN;
INSERT INTO `meta_role` VALUES ('2022-02-17 18:29:46', NULL, 'SYSTEM', NULL, '691755319587639296', 'ROOT', 'ROOT', '拥有平台维护权限', '管理员', b'1');
COMMIT;

-- ----------------------------
-- Table structure for meta_role_auth_rela
-- ----------------------------
DROP TABLE IF EXISTS `meta_role_auth_rela`;
CREATE TABLE `meta_role_auth_rela` (
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `auth_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of meta_role_auth_rela
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for meta_router
-- ----------------------------
DROP TABLE IF EXISTS `meta_router`;
CREATE TABLE `meta_router` (
  `id` varchar(32) NOT NULL,
  `pid` varchar(32) DEFAULT NULL COMMENT '父ID',
  `cn` varchar(255) DEFAULT NULL COMMENT '中文名称',
  `name` varchar(255) DEFAULT NULL COMMENT '路由name',
  `path` varchar(255) DEFAULT NULL COMMENT '路由path',
  `redirect` varchar(255) DEFAULT NULL COMMENT '重定向path',
  `component` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `components` json DEFAULT NULL COMMENT '多重组件',
  `meta` json DEFAULT NULL COMMENT '配置信息',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `build_in` bit(1) DEFAULT b'0' COMMENT '内建路由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由信息';

-- ----------------------------
-- Records of meta_router
-- ----------------------------
BEGIN;
INSERT INTO `meta_router` VALUES ('2b17f805d48548ef89a574bd80a0dd3a', '', '用户列表', 'UserList', '/user', '', 'UserList', '{}', '{\"title\": \"用户列表\"}', '2022-02-17 12:06:29', 'SYSTEM', NULL, NULL, '', b'1');
INSERT INTO `meta_router` VALUES ('e5a688069fcf43c8ab98af55c105890e', '', '角色列表', 'RoleList', '/role', '', 'RoleList', '{}', '{\"title\": \"角色列表\"}', '2022-02-17 12:06:33', 'SYSTEM', NULL, NULL, '', b'1');
COMMIT;

-- ----------------------------
-- Table structure for meta_user
-- ----------------------------
DROP TABLE IF EXISTS `meta_user`;
CREATE TABLE `meta_user` (
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT NULL,
  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `attrs` json DEFAULT NULL COMMENT '其他属性',
  `build_in` bit(1) DEFAULT b'0' COMMENT '内建用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_username` (`username`) USING HASH,
  UNIQUE KEY `uq_phone` (`phone`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of meta_user
-- ----------------------------
BEGIN;
INSERT INTO `meta_user` VALUES ('2022-02-20 10:24:46', NULL, 'SYSTEM', NULL, '691732305378676736', 'ROOT', '727227682e82393805614e2a7774d03d', '13524136383', '{}', b'1');
COMMIT;

-- ----------------------------
-- Table structure for meta_user_role_rela
-- ----------------------------
DROP TABLE IF EXISTS `meta_user_role_rela`;
CREATE TABLE `meta_user_role_rela` (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of meta_user_role_rela
-- ----------------------------
BEGIN;
INSERT INTO `meta_user_role_rela` VALUES ('691732305378676736', '691755319587639296');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
