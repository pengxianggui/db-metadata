/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3309
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3309
 Source Schema         : metadata

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 09/12/2019 20:23:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for change_log
-- ----------------------------
DROP TABLE IF EXISTS `change_log`;
CREATE TABLE `change_log`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `object_code`  varchar(64) DEFAULT NULL COMMENT '元对象',
    `table_name`   varchar(64) DEFAULT NULL COMMENT '表名',
    `pkey`         varchar(32) DEFAULT NULL COMMENT '主键字段',
    `pvalue`       varchar(32) DEFAULT NULL COMMENT '主键值',
    `action`       varchar(32) DEFAULT NULL COMMENT '动作',
    `olddata`      text COMMENT '旧数据',
    `newdata`      text COMMENT '新数据',
    `diff`         text COMMENT '差异',
    `created_by`   varchar(64) DEFAULT NULL COMMENT '创建人',
    `created_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `remark`       varchar(32) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据变化日志 ';

-- ----------------------------
-- Table structure for meta_component
-- ----------------------------
DROP TABLE IF EXISTS `meta_component`;
CREATE TABLE `meta_component`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `cn`           varchar(32) DEFAULT NULL COMMENT '中文名称',
    `en`           varchar(32) DEFAULT NULL COMMENT '英文名称',
    `code`         varchar(64) DEFAULT NULL COMMENT '组件编码',
    `config`       json COMMENT '配置信息',
    `version`      int(11)     DEFAULT NULL COMMENT '版本信息',
    `created_by`   varchar(64) DEFAULT NULL COMMENT '创建人',
    `created_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64) DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime    DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组件注册表 ';

-- ----------------------------
-- Table structure for meta_component_instance
-- ----------------------------
DROP TABLE IF EXISTS `meta_component_instance`;
CREATE TABLE `meta_component_instance`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `comp_code`    varchar(32)  DEFAULT NULL COMMENT '组件code',
    `type`         varchar(32)  DEFAULT NULL COMMENT '类型',
    `dest_object`  varchar(128) DEFAULT NULL COMMENT '目标对象',
    `label`        varchar(32)  DEFAULT NULL COMMENT '描述',
    `config`       json COMMENT '配置',
    `created_by`   varchar(64)  DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组件配置关系表 ';

-- ----------------------------
-- Table structure for meta_config
-- ----------------------------
DROP TABLE IF EXISTS `meta_config`;
CREATE TABLE `meta_config`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `module`       varchar(1024) DEFAULT NULL COMMENT '模块',
    `module_code`  varchar(1024) DEFAULT NULL COMMENT '模块名',
    `config`       json COMMENT '配置',
    `version`      varchar(32)   DEFAULT NULL COMMENT '版本',
    `created_by`   varchar(64)   DEFAULT NULL COMMENT '创建人',
    `created_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64)   DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime      DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)   DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='配置 ';

-- ----------------------------
-- Table structure for meta_dict
-- ----------------------------
DROP TABLE IF EXISTS `meta_dict`;
CREATE TABLE `meta_dict`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `pid`          varchar(64)   DEFAULT NULL COMMENT '父层标志',
    `paths`        varchar(1024) DEFAULT NULL COMMENT '路径',
    `path_names`   varchar(1024) DEFAULT NULL COMMENT '路径名称',
    `name`         varchar(32)   DEFAULT NULL COMMENT '名称',
    `value`        varchar(32)   DEFAULT NULL COMMENT '值',
    `created_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `created_by`   varchar(64)   DEFAULT NULL COMMENT '创建人',
    `updated_by`   varchar(64)   DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime      DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)   DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典表 ';

-- ----------------------------
-- Table structure for meta_feature
-- ----------------------------
DROP TABLE IF EXISTS `meta_feature`;
CREATE TABLE `meta_feature`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `type`         varchar(32) DEFAULT NULL COMMENT '功能类型',
    `name`         varchar(32) DEFAULT NULL COMMENT '功能名',
    `code`         varchar(32) DEFAULT NULL COMMENT '功能编码',
    `config`       json COMMENT '配置',
    `created_by`   varchar(64) DEFAULT NULL COMMENT '创建人',
    `created_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64) DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime    DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='功能配置 ';

-- ----------------------------
-- Table structure for meta_field
-- ----------------------------
DROP TABLE IF EXISTS `meta_field`;
CREATE TABLE `meta_field`
(
    `id`             varchar(32) NOT NULL COMMENT '主键',
    `object_code`    varchar(64)  DEFAULT NULL COMMENT '对象编码',
    `field_code`     varchar(64)  DEFAULT NULL COMMENT '字段编码',
    `is_primary`     varchar(1)   DEFAULT NULL COMMENT '是否主键',
    `en`             varchar(128) DEFAULT NULL COMMENT '英文',
    `cn`             varchar(128) DEFAULT NULL COMMENT '中文',
    `order_num`      int(11)      DEFAULT NULL COMMENT '排序',
    `db_type`        varchar(128) DEFAULT NULL COMMENT '数据类型',
    `db_type_length` varchar(32)  DEFAULT NULL COMMENT '数据长度',
    `java_type`      varchar(128) DEFAULT NULL COMMENT 'JAVA类型',
    `config`         json COMMENT '配置',
    `created_by`     varchar(64)  DEFAULT NULL COMMENT '创建人',
    `created_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `updated_by`     varchar(64)  DEFAULT NULL COMMENT '更新人',
    `updated_time`   datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`         varchar(32)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='元字段 ';

-- ----------------------------
-- Table structure for meta_object
-- ----------------------------
DROP TABLE IF EXISTS `meta_object`;
CREATE TABLE `meta_object`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `code`         varchar(64) NOT NULL COMMENT '对象编码',
    `name`         varchar(128) DEFAULT NULL COMMENT '对象名',
    `table_name`   varchar(64) NOT NULL COMMENT '表名',
    `schema_name`  varchar(64) NOT NULL COMMENT '库名',
    `primarys`     varchar(128) DEFAULT NULL COMMENT '主键组',
    `is_sys`       varchar(1)   DEFAULT '0' COMMENT '系统模块',
    `config`       json COMMENT '配置',
    `created_by`   varchar(64)  DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_metaobject_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='元对象 ';

-- ----------------------------
-- Table structure for test_table
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `col_int`      int(11)        DEFAULT NULL COMMENT '普通整数',
    `col_bigint`   bigint(20)     DEFAULT NULL COMMENT '大整数',
    `col_bool`     varchar(1)     DEFAULT NULL COMMENT '布尔',
    `col_date`     date           DEFAULT NULL COMMENT '日期',
    `col_time`     datetime       DEFAULT NULL COMMENT '时间',
    `col_decimal`  decimal(32, 8) DEFAULT NULL COMMENT '金额',
    `col_checkbox` varchar(32)    DEFAULT NULL COMMENT '复选',
    `col_nor_str`  varchar(1024)  DEFAULT NULL COMMENT '普通字符',
    `col_file`     varchar(1024)  DEFAULT NULL COMMENT '文件',
    `created_by`   varchar(64)    DEFAULT NULL COMMENT '创建人',
    `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64)    DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime       DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='测试表 ';

SET FOREIGN_KEY_CHECKS = 1;
