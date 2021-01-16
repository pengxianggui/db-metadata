/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : db-metadata-demo

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 16/01/2021 17:43:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for change_log
-- ----------------------------
DROP TABLE IF EXISTS `change_log`;
CREATE TABLE `change_log` (
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能配置 ';

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
-- Table structure for meta_menu
-- ----------------------------
DROP TABLE IF EXISTS `meta_menu`;
CREATE TABLE `meta_menu` (
  `id` varchar(32) NOT NULL,
  `pid` varchar(32) DEFAULT NULL COMMENT '父ID',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单名',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '隐藏',
  `disable` tinyint(1) DEFAULT '1' COMMENT '生效',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `meta` json DEFAULT NULL COMMENT '配置信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

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
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由信息';

SET FOREIGN_KEY_CHECKS = 1;
