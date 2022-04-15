/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 24/03/2022 11:14:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meta_api_resource
-- ----------------------------
DROP TABLE IF EXISTS `meta_api_resource`;
CREATE TABLE `meta_api_resource` (
                                     `id` varchar(32) CHARACTER SET utf8 NOT NULL,
                                     `name` varchar(255) NOT NULL COMMENT '资源名称',
                                     `type` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '资源类型(0-接口;1-接口加元对象;2-接口加功能)',
                                     `uri` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '接口uri',
                                     `meta_code` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '元编码(元对象编码或功能编码)',
                                     `need_permit` bit(1) NOT NULL DEFAULT b'1' COMMENT '需要鉴权(关闭则后面配置无效)',
                                     `just_sign` bit(1) NOT NULL DEFAULT b'0' COMMENT '登录即可访问',
                                     `permit_by` varchar(10) NOT NULL DEFAULT 'auth' COMMENT '判定依据(auth-权限;role-角色)',
                                     `auths` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的权限编码(逗号分隔)',
                                     `auth_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '权限匹配模式(any-任一匹配;all-所有匹配)',
                                     `roles` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的角色编码(逗号分隔)',
                                     `role_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '角色匹配模式(any-任一匹配;all-所有匹配)',
                                     `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                     `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                     `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                     `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                     `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_union` (`type`,`uri`,`meta_code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内置资源表:接口资源';

-- ----------------------------
-- Table structure for meta_auth
-- ----------------------------
DROP TABLE IF EXISTS `meta_auth`;
CREATE TABLE `meta_auth` (
                             `id` varchar(32) CHARACTER SET utf8 NOT NULL,
                             `code` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '权限编码',
                             `name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '权限名',
                             `module_id` varchar(32) DEFAULT NULL COMMENT '所属模块',
                             `type` varchar(20) DEFAULT NULL COMMENT '权限类别',
                             `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                             `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `uq_code` (`code`) COMMENT '权限编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限控制表';

-- ----------------------------
-- Table structure for meta_auth_module
-- ----------------------------
DROP TABLE IF EXISTS `meta_auth_module`;
CREATE TABLE `meta_auth_module` (
                                    `id` varchar(32) COLLATE utf8_bin NOT NULL,
                                    `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '模块',
                                    `pid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父模块',
                                    `order` int(11) DEFAULT NULL COMMENT '排序',
                                    `remark` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
                                    `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                    `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                    `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                    `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限模块';

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
                                   `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                   `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                   `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                   `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据变化日志 ';

-- ----------------------------
-- Table structure for meta_component
-- ----------------------------
DROP TABLE IF EXISTS `meta_component`;
CREATE TABLE `meta_component` (
                                  `id` varchar(32) NOT NULL COMMENT '主键',
                                  `cn` varchar(32) NOT NULL COMMENT '中文名称',
                                  `en` varchar(32) NOT NULL COMMENT '英文名称',
                                  `code` varchar(64) NOT NULL COMMENT '组件编码',
                                  `config` json DEFAULT NULL COMMENT '配置信息',
                                  `version` int(11) DEFAULT NULL COMMENT '版本信息',
                                  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                  `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件注册表 ';


-- ----------------------------
-- Table structure for meta_component_instance
-- ----------------------------
DROP TABLE IF EXISTS `meta_component_instance`;
CREATE TABLE `meta_component_instance` (
                                           `id` varchar(32) NOT NULL COMMENT '主键',
                                           `code` varchar(128) NOT NULL COMMENT '配置编码',
                                           `comp_code` varchar(32) NOT NULL COMMENT '组件编码',
                                           `type` varchar(32) NOT NULL COMMENT '类型',
                                           `dest_object` varchar(128) NOT NULL COMMENT '目标对象',
                                           `name` varchar(128) DEFAULT NULL COMMENT '描述',
                                           `config` json DEFAULT NULL COMMENT '配置',
                                           `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                           `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                           `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                           `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                           `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件配置关系表 ';


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
                               `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                               `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                               `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置 ';


-- ----------------------------
-- Table structure for meta_dict
-- ----------------------------
DROP TABLE IF EXISTS `meta_dict`;
CREATE TABLE `meta_dict` (
                             `id` varchar(32) NOT NULL COMMENT '主键',
                             `p_value` varchar(128) DEFAULT NULL COMMENT '父节点字典值',
                             `type` varchar(64) DEFAULT NULL COMMENT '字典类别',
                             `key` varchar(128) NOT NULL COMMENT '键',
                             `value` varchar(128) NOT NULL COMMENT '值',
                             `value_db_type` varchar(128) DEFAULT 'VARCHAR' COMMENT '值DB类型',
                             `order` varchar(32) DEFAULT NULL COMMENT '排序',
                             `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                             `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `uq_key` (`p_value`,`value`) USING BTREE COMMENT '同一个字典项下的的值保持唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表 ';

-- ----------------------------
-- Table structure for meta_exception
-- ----------------------------
DROP TABLE IF EXISTS `meta_exception`;
CREATE TABLE `meta_exception` (
                                  `id` varchar(32) NOT NULL COMMENT '主键',
                                  `exp_title` varchar(255) DEFAULT NULL COMMENT '异常名',
                                  `exp_chain` text COMMENT '异常堆栈',
                                  `exp_msg` text COMMENT '异常消息',
                                  `ext_url` varchar(1024) DEFAULT NULL COMMENT '请求url',
                                  `req_data` text COMMENT '请求内容',
                                  `res_data` longtext COMMENT '响应内容',
                                  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                  `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Meta异常 ';


-- ----------------------------
-- Table structure for meta_feature
-- ----------------------------
DROP TABLE IF EXISTS `meta_feature`;
CREATE TABLE `meta_feature` (
                                `id` varchar(32) NOT NULL COMMENT '主键',
                                `type` varchar(32) NOT NULL COMMENT '功能类型',
                                `name` varchar(32) NOT NULL COMMENT '功能名',
                                `code` varchar(32) NOT NULL COMMENT '功能编码',
                                `config` json NOT NULL COMMENT '配置',
                                `op_config` json DEFAULT NULL COMMENT '操作配置',
                                `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `uq_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能配置 ';

-- ----------------------------
-- Table structure for meta_field
-- ----------------------------
DROP TABLE IF EXISTS `meta_field`;
CREATE TABLE `meta_field` (
                              `id` varchar(32) NOT NULL COMMENT '主键',
                              `object_code` varchar(64) NOT NULL COMMENT '对象编码',
                              `field_code` varchar(64) NOT NULL COMMENT '字段编码',
                              `is_primary` varchar(1) NOT NULL COMMENT '是否主键',
                              `en` varchar(128) DEFAULT NULL COMMENT '英文',
                              `cn` varchar(128) DEFAULT NULL COMMENT '中文',
                              `order_num` int(11) DEFAULT NULL COMMENT '排序',
                              `db_type` varchar(128) DEFAULT NULL COMMENT '数据类型',
                              `db_type_length` varchar(32) DEFAULT NULL COMMENT '数据长度',
                              `java_type` varchar(128) DEFAULT NULL COMMENT 'JAVA类型',
                              `config` json DEFAULT NULL COMMENT '配置',
                              `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                              `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                              `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                              `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                              `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元字段 ';


-- ----------------------------
-- Table structure for meta_menu
-- ----------------------------
DROP TABLE IF EXISTS `meta_menu`;
CREATE TABLE `meta_menu` (
                             `id` varchar(32) NOT NULL,
                             `pid` varchar(32) DEFAULT NULL COMMENT '父ID',
                             `title` varchar(255) NOT NULL COMMENT '菜单名',
                             `hidden` bit(1) NOT NULL DEFAULT b'0' COMMENT '隐藏',
                             `disable` bit(1) NOT NULL DEFAULT b'0' COMMENT '禁用',
                             `icon` text COMMENT '图标',
                             `path` varchar(255) DEFAULT NULL COMMENT '路径',
                             `order` int(11) DEFAULT '0' COMMENT '排序',
                             `remark` text COMMENT '备注',
                             `need_permit` bit(1) NOT NULL DEFAULT b'1' COMMENT '需要鉴权(关闭则后面配置无效)',
                             `permit_by` varchar(10) NOT NULL DEFAULT 'auth' COMMENT '判定依据(auth-权限;role-角色)',
                             `auths` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的权限编码(逗号分隔)',
                             `auth_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '权限匹配模式(any-任一匹配;all-所有匹配)',
                             `roles` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的角色编码(逗号分隔)',
                             `role_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '角色匹配模式(any-任一匹配;all-所有匹配)',
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                             `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Table structure for meta_profile_menu
-- ----------------------------
DROP TABLE IF EXISTS `meta_profile_menu`;
CREATE TABLE `meta_profile_menu` (
                                     `id` varchar(32) NOT NULL,
                                     `title` varchar(255) NOT NULL COMMENT '菜单名',
                                     `hidden` bit(1) NOT NULL DEFAULT b'0' COMMENT '隐藏',
                                     `disable` bit(1) NOT NULL DEFAULT b'0' COMMENT '禁用',
                                     `icon` text COMMENT '图标',
                                     `path` varchar(255) DEFAULT NULL COMMENT '路径',
                                     `order` int(11) DEFAULT '0' COMMENT '排序',
                                     `remark` text COMMENT '备注',
                                     `need_permit` bit(1) NOT NULL DEFAULT b'1' COMMENT '需要鉴权(关闭则后面配置无效)',
                                     `permit_by` varchar(10) NOT NULL DEFAULT 'auth' COMMENT '判定依据(auth-权限;role-角色)',
                                     `auths` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的权限编码(逗号分隔)',
                                     `auth_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '权限匹配模式(any-任一匹配;all-所有匹配)',
                                     `roles` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的角色编码(逗号分隔)',
                                     `role_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '角色匹配模式(any-任一匹配;all-所有匹配)',
                                     `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                     `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                     `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                     `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Profile菜单';

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
                               `config` json DEFAULT NULL COMMENT '配置',
                               `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                               `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                               `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE KEY `unique_metaobject_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元对象 ';

-- ----------------------------
-- Table structure for meta_role
-- ----------------------------
DROP TABLE IF EXISTS `meta_role`;
CREATE TABLE `meta_role` (
                             `id` varchar(32) CHARACTER SET utf8 NOT NULL,
                             `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色编码',
                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名',
                             `remark` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '角色说明',
                             `role_type` varchar(128) DEFAULT NULL COMMENT '角色类别',
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                             `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uq_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Table structure for meta_role_auth_rela
-- ----------------------------
DROP TABLE IF EXISTS `meta_role_auth_rela`;
CREATE TABLE `meta_role_auth_rela` (
                                       `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
                                       `auth_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限id',
                                       `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                       `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                       `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                       `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                       PRIMARY KEY (`role_id`,`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

-- ----------------------------
-- Table structure for meta_router
-- ----------------------------
DROP TABLE IF EXISTS `meta_router`;
CREATE TABLE `meta_router` (
                               `id` varchar(32) NOT NULL,
                               `pid` varchar(32) DEFAULT NULL COMMENT '父节点',
                               `name` varchar(255) NOT NULL COMMENT '路由名',
                               `cn` varchar(255) DEFAULT NULL COMMENT '中文名',
                               `path` varchar(255) NOT NULL COMMENT '路由path',
                               `redirect` varchar(255) DEFAULT NULL COMMENT '重定向path',
                               `component` varchar(255) DEFAULT NULL COMMENT '组件名称',
                               `components` json DEFAULT NULL COMMENT '多重组件',
                               `layout` bit(1) NOT NULL DEFAULT b'0' COMMENT '布局路由',
                               `disable` bit(1) NOT NULL DEFAULT b'0' COMMENT '禁用',
                               `order` int(11) DEFAULT '0' COMMENT '排序',
                               `meta` json DEFAULT NULL COMMENT '配置信息',
                               `need_permit` bit(1) NOT NULL DEFAULT b'1' COMMENT '需要鉴权(关闭则后面配置无效)',
                               `permit_by` varchar(10) NOT NULL DEFAULT 'auth' COMMENT '判定依据(auth-权限;role-角色)',
                               `auths` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的权限编码(逗号分隔)',
                               `auth_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '权限匹配模式(any-任一匹配;all-所有匹配)',
                               `roles` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '需要的角色编码(逗号分隔)',
                               `role_match_mode` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'any' COMMENT '角色匹配模式(any-任一匹配;all-所有匹配)',
                               `remark` text COMMENT '备注',
                               `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                               `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由信息';

-- ----------------------------
-- Table structure for meta_user
-- ----------------------------
DROP TABLE IF EXISTS `meta_user`;
CREATE TABLE `meta_user` (
                             `id` varchar(32) CHARACTER SET utf8 NOT NULL,
                             `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
                             `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
                             `avatar` json DEFAULT NULL COMMENT '头像',
                             `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
                             `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
                             `sex` varchar(1) DEFAULT NULL COMMENT '性别',
                             `age` int(3) DEFAULT NULL COMMENT '年龄',
                             `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
                             `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                             `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                             `attrs` json DEFAULT NULL COMMENT '扩展属性',
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                             `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uq_username` (`username`) USING HASH,
                             UNIQUE KEY `uq_phone` (`phone`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for meta_user_role_rela
-- ----------------------------
DROP TABLE IF EXISTS `meta_user_role_rela`;
CREATE TABLE `meta_user_role_rela` (
                                       `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
                                       `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
                                       `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                       `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                       `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                       `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                       PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

SET FOREIGN_KEY_CHECKS = 1;
