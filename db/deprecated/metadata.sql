/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.93
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 192.168.1.93:3310
 Source Schema         : metadata

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/11/2019 17:00:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for PDMAN_DB_VERSION
-- ----------------------------
DROP TABLE IF EXISTS `PDMAN_DB_VERSION`;
CREATE TABLE `PDMAN_DB_VERSION`
(
    `DB_VERSION`   varchar(256)  DEFAULT NULL,
    `VERSION_DESC` varchar(1024) DEFAULT NULL,
    `CREATED_TIME` varchar(32)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of PDMAN_DB_VERSION
-- ----------------------------
BEGIN;
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('v0.0.0', '默认版本，新增的版本不能低于此版本', '2019-09-24 17:04:45');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.10', '0.10', '2019-09-24 17:04:51');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.11', '0.11', '2019-10-10 16:56:23');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.12', 'meta_component_instance', '2019-10-12 12:59:10');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.13', '组件实例中增加类别', '2019-10-18 11:18:32');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.14', '是否系统模块', '2019-10-25 10:40:56');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.15', 'issys 默认值', '2019-10-25 10:41:54');
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('0.16', 'changelog', '2019-11-07 09:25:48');
COMMIT;

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
    `olddata`      json        DEFAULT NULL COMMENT '旧数据',
    `newdata`      json        DEFAULT NULL COMMENT '新数据',
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
    `config`       json        DEFAULT NULL COMMENT '配置信息',
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
-- Records of meta_component
-- ----------------------------
BEGIN;
INSERT INTO `meta_component`
VALUES ('385064764017086464', '下拉框组件', 'DropDownBox', 'DropDownBox', '{
  \"conf\": {
    \"clearable\": true
  },
  \"name\": \"DropDownBox\",
  \"group\": false,
  \"label\": \"下拉框\",
  \"component_name\": \"DropDownBox\"
}', 24, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('385064764197441536', '按钮组件', 'Button', 'Button', 'null', 25, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('385064764335853568', '输入框组件', 'TextBox', 'TextBox', '{
  \"conf\": {
    \"clearable\": true,
    \"placeholder\": \"请输入内容..\"
  },
  \"name\": \"TextBox\",
  \"label\": \"文本框\",
  \"component_name\": \"TextBox\"
}', 24, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('385064764470071296', '表格组件', 'TableList', 'TableList', '{
  \"conf\": {
    \"size\": \"medium\",
    \"default-sort\": {
      \"prop\": \"id\",
      \"order\": \"descending\"
    },
    \"highlight-current-row\": true
  },
  \"name\": \"TableList\",
  \"label\": \"表格模板\",
  \"columns\": [],
  \"methods\": \"GET\",
  \"data_url\": \"/table/list\",
  \"pagination\": {
    \"layout\": \"total, sizes, prev, pager, next, jumper\",
    \"page-size\": 10,
    \"page-sizes\": 20,
    \"current-page\": 1
  },
  \"component_name\": \"TableList\"
}', 25, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('385064764616871936', '表单组件', 'FormTmpl', 'FormTmpl', '{
  \"btns\": {
    \"cancel\": {
      \"conf\": {},
      \"label\": \"取消\"
    },
    \"submit\": {
      \"conf\": {
        \"type\": \"primary\"
      },
      \"label\": \"提交\"
    }
  },
  \"conf\": {
    \"size\": \"medium\",
    \"rules\": {},
    \"label-width\": \"100px\"
  },
  \"name\": \"FormTmpl\",
  \"label\": \"表单模板\",
  \"action\": \"/save\",
  \"columns\": [],
  \"component_name\": \"FormTmpl\"
}', 25, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749163719528448', '数值框', 'NumBox', 'NumBox', '{
  \"conf\": {
    \"controls\": false,
    \"placeholder\": \"请输入数值..\"
  },
  \"name\": \"NumBox\",
  \"label\": \"数字框\",
  \"component_name\": \"NumBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749164105404416', '日期时间框', 'DateTimeBox', 'DateTimeBox', '{
  \"conf\": {
    \"clearable\": true,
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"DateTimeBox\",
  \"label\": \"日期时间框\",
  \"component_name\": \"DateTimeBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749164206067712', '日期框', 'DateBox', 'DateBox', '{
  \"conf\": {
    \"clearable\": true,
    \"value-format\": \"yyyy-MM-dd\"
  },
  \"name\": \"DateBox\",
  \"label\": \"日期框\",
  \"component_name\": \"DateBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749164289953792', '时间框', 'TimeBox', 'TimeBox', '{
  \"conf\": {
    \"clearable\": true,
    \"value-format\": \"HH:mm:ss\"
  },
  \"name\": \"TimeBox\",
  \"label\": \"时间框\",
  \"component_name\": \"TimeBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749164738744320', 'Json框', 'JsonBox', 'JsonBox', '{
  \"conf\": {},
  \"mode\": \"text\",
  \"name\": \"JsonBox\",
  \"label\": \"Json框\",
  \"modes\": [
    \"code\",
    \"tree\",
    \"text\",
    \"view\",
    \"form\"
  ],
  \"component_name\": \"JsonBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749164826824704', '文本域', 'TextAreaBox', 'TextAreaBox', '{
  \"conf\": {
    \"clearable\": true,
    \"placeholder\": \"请输入文本内容..\"
  },
  \"name\": \"TextAreaBox\",
  \"label\": \"文本域\",
  \"component_name\": \"TextAreaBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749164923293696', '布尔框', 'BoolBox', 'BoolBox', '{
  \"conf\": {},
  \"name\": \"BoolBox\",
  \"label\": \"布尔框\",
  \"component_name\": \"BoolBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_component`
VALUES ('389749165179146240', '单选框', 'RadioBox', 'RadioBox', '{
  \"conf\": {},
  \"name\": \"RadioBox\",
  \"group\": false,
  \"label\": \"单选框\",
  \"data_url\": \"\",
  \"component_name\": \"RadioBox\"
}', 23, NULL, NULL, NULL, NULL, NULL);
COMMIT;

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
    `config`       json         DEFAULT NULL COMMENT '配置',
    `created_by`   varchar(64)  DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组件配置关系表 ';

-- ----------------------------
-- Records of meta_component_instance
-- ----------------------------
BEGIN;
INSERT INTO `meta_component_instance`
VALUES ('389750589262794752', 'TableList', 'META_OBJECT', 'meta_object', NULL, '{
  \"conf\": {
    \"size\": \"medium\",
    \"default-sort\": {
      \"prop\": \"id\",
      \"order\": \"descending\"
    },
    \"highlight-current-row\": \"true\"
  },
  \"name\": \"meta_object\",
  \"label\": \"meta_object\",
  \"columns\": [],
  \"methods\": \"GET\",
  \"data_url\": \"/table/list\",
  \"pagination\": {
    \"layout\": \"total, sizes, prev, pager, next, jumper\",
    \"page-size\": \"10\",
    \"page-sizes\": \"20\",
    \"current-page\": \"1\"
  },
  \"component_name\": \"TableList\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737792', 'TableList', 'META_FIELD', 'meta_object.id', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"id\",
  \"label\": \"主键\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737793', 'TableList', 'META_FIELD', 'meta_object.code', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"code\",
  \"label\": \"对象编码\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737794', 'TableList', 'META_FIELD', 'meta_object.name', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"name\",
  \"label\": \"对象名\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737795', 'TableList', 'META_FIELD', 'meta_object.table_name', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"table_name\",
  \"label\": \"表名\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737796', 'TableList', 'META_FIELD', 'meta_object.schema_name', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"schema_name\",
  \"label\": \"库名\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737797', 'TableList', 'META_FIELD', 'meta_object.primarys', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"primarys\",
  \"label\": \"主键组\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737798', 'TableList', 'META_FIELD', 'meta_object.is_sys', NULL, '{
  \"conf\": {
    \"maxlength\": \"1\"
  },
  \"name\": \"is_sys\",
  \"label\": \"系统模块\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737799', 'TableList', 'META_FIELD', 'meta_object.config', NULL, '{
  \"conf\": {},
  \"mode\": \"text\",
  \"name\": \"config\",
  \"label\": \"配置\",
  \"modes\": [
    \"code\",
    \"tree\",
    \"text\",
    \"view\",
    \"form\"
  ],
  \"component_name\": \"JsonBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737800', 'TableList', 'META_FIELD', 'meta_object.created_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"created_by\",
  \"label\": \"创建人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737801', 'TableList', 'META_FIELD', 'meta_object.created_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"created_time\",
  \"label\": \"创建时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737802', 'TableList', 'META_FIELD', 'meta_object.updated_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"updated_by\",
  \"label\": \"更新人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737803', 'TableList', 'META_FIELD', 'meta_object.updated_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"updated_time\",
  \"label\": \"更新时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750589304737804', 'TableList', 'META_FIELD', 'meta_object.remark', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"remark\",
  \"label\": \"备注\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:47', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615129067520', 'TableList', 'META_OBJECT', 'meta_field', NULL, '{
  \"conf\": {
    \"size\": \"medium\",
    \"default-sort\": {
      \"prop\": \"id\",
      \"order\": \"descending\"
    },
    \"highlight-current-row\": \"true\"
  },
  \"name\": \"meta_field\",
  \"label\": \"meta_field\",
  \"columns\": [],
  \"methods\": \"GET\",
  \"data_url\": \"/table/list\",
  \"pagination\": {
    \"layout\": \"total, sizes, prev, pager, next, jumper\",
    \"page-size\": \"10\",
    \"page-sizes\": \"20\",
    \"current-page\": \"1\"
  },
  \"component_name\": \"TableList\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039040', 'TableList', 'META_FIELD', 'meta_field.id', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"id\",
  \"label\": \"主键\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039041', 'TableList', 'META_FIELD', 'meta_field.object_code', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"object_code\",
  \"label\": \"对象编码\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039042', 'TableList', 'META_FIELD', 'meta_field.field_code', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"field_code\",
  \"label\": \"字段编码\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039043', 'TableList', 'META_FIELD', 'meta_field.is_primary', NULL, '{
  \"conf\": {
    \"maxlength\": \"1\"
  },
  \"name\": \"is_primary\",
  \"label\": \"主键\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039044', 'TableList', 'META_FIELD', 'meta_field.en', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"en\",
  \"label\": \"英文\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039045', 'TableList', 'META_FIELD', 'meta_field.cn', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"cn\",
  \"label\": \"中文\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039046', 'TableList', 'META_FIELD', 'meta_field.order_num', NULL, '{
  \"conf\": {
    \"controls\": \"false\",
    \"placeholder\": \"请输入数值..\"
  },
  \"name\": \"order_num\",
  \"label\": \"排序\",
  \"component_name\": \"NumBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039047', 'TableList', 'META_FIELD', 'meta_field.db_type', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"db_type\",
  \"label\": \"数据类型\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039048', 'TableList', 'META_FIELD', 'meta_field.db_type_length', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"db_type_length\",
  \"label\": \"数据长度\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039049', 'TableList', 'META_FIELD', 'meta_field.java_type', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"java_type\",
  \"label\": \"JAVA类型\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039050', 'TableList', 'META_FIELD', 'meta_field.config', NULL, '{
  \"conf\": {},
  \"mode\": \"text\",
  \"name\": \"config\",
  \"label\": \"配置\",
  \"modes\": [
    \"code\",
    \"tree\",
    \"text\",
    \"view\",
    \"form\"
  ],
  \"component_name\": \"JsonBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039051', 'TableList', 'META_FIELD', 'meta_field.created_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"created_by\",
  \"label\": \"创建人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039052', 'TableList', 'META_FIELD', 'meta_field.created_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"created_time\",
  \"label\": \"创建时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039053', 'TableList', 'META_FIELD', 'meta_field.updated_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"updated_by\",
  \"label\": \"更新人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039054', 'TableList', 'META_FIELD', 'meta_field.updated_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"updated_time\",
  \"label\": \"更新时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389750615150039055', 'TableList', 'META_FIELD', 'meta_field.remark', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"remark\",
  \"label\": \"备注\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 09:30:53', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669335314432', 'TableList', 'META_OBJECT', 'meta_component', NULL, '{
  \"conf\": {
    \"size\": \"medium\",
    \"default-sort\": {
      \"prop\": \"id\",
      \"order\": \"descending\"
    },
    \"highlight-current-row\": \"true\"
  },
  \"name\": \"meta_component\",
  \"label\": \"meta_component\",
  \"columns\": [],
  \"methods\": \"GET\",
  \"data_url\": \"/table/list\",
  \"pagination\": {
    \"layout\": \"total, sizes, prev, pager, next, jumper\",
    \"page-size\": \"10\",
    \"page-sizes\": \"20\",
    \"current-page\": \"1\"
  },
  \"component_name\": \"TableList\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646080', 'TableList', 'META_FIELD', 'meta_component.id', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"id\",
  \"label\": \"主键\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646081', 'TableList', 'META_FIELD', 'meta_component.cn', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"cn\",
  \"label\": \"中文名称\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646082', 'TableList', 'META_FIELD', 'meta_component.en', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"en\",
  \"label\": \"英文名称\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646083', 'TableList', 'META_FIELD', 'meta_component.code', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"code\",
  \"label\": \"组件编码\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646084', 'TableList', 'META_FIELD', 'meta_component.config', NULL, '{
  \"conf\": {},
  \"mode\": \"text\",
  \"name\": \"config\",
  \"label\": \"配置信息\",
  \"modes\": [
    \"code\",
    \"tree\",
    \"text\",
    \"view\",
    \"form\"
  ],
  \"component_name\": \"JsonBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646085', 'TableList', 'META_FIELD', 'meta_component.version', NULL, '{
  \"conf\": {
    \"controls\": \"false\",
    \"placeholder\": \"请输入数值..\"
  },
  \"name\": \"version\",
  \"label\": \"版本信息\",
  \"component_name\": \"NumBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646086', 'TableList', 'META_FIELD', 'meta_component.created_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"created_by\",
  \"label\": \"创建人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646087', 'TableList', 'META_FIELD', 'meta_component.created_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"created_time\",
  \"label\": \"创建时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646088', 'TableList', 'META_FIELD', 'meta_component.updated_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"updated_by\",
  \"label\": \"更新人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646089', 'TableList', 'META_FIELD', 'meta_component.updated_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"updated_time\",
  \"label\": \"更新时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389835669385646090', 'TableList', 'META_FIELD', 'meta_component.remark', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"remark\",
  \"label\": \"备注\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:08:52', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841721955061760', 'FormTmpl', 'META_OBJECT', 'meta_object', NULL, '{
  \"btns\": {
    \"cancel\": {
      \"conf\": {},
      \"label\": \"取消\"
    },
    \"submit\": {
      \"conf\": {
        \"type\": \"primary\"
      },
      \"label\": \"提交\"
    }
  },
  \"conf\": {
    \"size\": \"medium\",
    \"rules\": {},
    \"label-width\": \"100px\"
  },
  \"name\": \"meta_object\",
  \"label\": \"meta_object\",
  \"action\": \"/save\",
  \"columns\": [],
  \"component_name\": \"FormTmpl\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782016', 'FormTmpl', 'META_FIELD', 'meta_object.id', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"id\",
  \"label\": \"主键\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782017', 'FormTmpl', 'META_FIELD', 'meta_object.code', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"code\",
  \"label\": \"对象编码\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782018', 'FormTmpl', 'META_FIELD', 'meta_object.name', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"name\",
  \"label\": \"对象名\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782019', 'FormTmpl', 'META_FIELD', 'meta_object.table_name', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"table_name\",
  \"label\": \"表名\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782020', 'FormTmpl', 'META_FIELD', 'meta_object.schema_name', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"schema_name\",
  \"label\": \"库名\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782021', 'FormTmpl', 'META_FIELD', 'meta_object.primarys', NULL, '{
  \"conf\": {
    \"maxlength\": \"128\"
  },
  \"name\": \"primarys\",
  \"label\": \"主键组\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782022', 'FormTmpl', 'META_FIELD', 'meta_object.is_sys', NULL, '{
  \"conf\": {
    \"maxlength\": \"1\"
  },
  \"name\": \"is_sys\",
  \"label\": \"系统模块\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782023', 'FormTmpl', 'META_FIELD', 'meta_object.config', NULL, '{
  \"conf\": {},
  \"mode\": \"text\",
  \"name\": \"config\",
  \"label\": \"配置\",
  \"modes\": [
    \"code\",
    \"tree\",
    \"text\",
    \"view\",
    \"form\"
  ],
  \"component_name\": \"JsonBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782024', 'FormTmpl', 'META_FIELD', 'meta_object.created_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"created_by\",
  \"label\": \"创建人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782025', 'FormTmpl', 'META_FIELD', 'meta_object.created_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"created_time\",
  \"label\": \"创建时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782026', 'FormTmpl', 'META_FIELD', 'meta_object.updated_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"updated_by\",
  \"label\": \"更新人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782027', 'FormTmpl', 'META_FIELD', 'meta_object.updated_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"updated_time\",
  \"label\": \"更新时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389841722013782028', 'FormTmpl', 'META_FIELD', 'meta_object.remark', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"remark\",
  \"label\": \"备注\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 15:32:55', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816892682240', 'FormTmpl', 'META_OBJECT', 'meta_component', NULL, '{
  \"btns\": {
    \"cancel\": {
      \"conf\": {},
      \"label\": \"取消\"
    },
    \"submit\": {
      \"conf\": {
        \"type\": \"primary\"
      },
      \"label\": \"提交\"
    }
  },
  \"conf\": {
    \"size\": \"medium\",
    \"rules\": {},
    \"label-width\": \"100px\"
  },
  \"name\": \"meta_component\",
  \"label\": \"meta_component\",
  \"action\": \"/save\",
  \"columns\": [],
  \"component_name\": \"FormTmpl\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625280', 'FormTmpl', 'META_FIELD', 'meta_component.id', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"id\",
  \"label\": \"主键\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625281', 'FormTmpl', 'META_FIELD', 'meta_component.cn', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"cn\",
  \"label\": \"中文名称\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625282', 'FormTmpl', 'META_FIELD', 'meta_component.en', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"en\",
  \"label\": \"英文名称\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625283', 'FormTmpl', 'META_FIELD', 'meta_component.code', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"code\",
  \"label\": \"组件编码\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625284', 'FormTmpl', 'META_FIELD', 'meta_component.config', NULL, '{
  \"conf\": {},
  \"mode\": \"text\",
  \"name\": \"config\",
  \"label\": \"配置信息\",
  \"modes\": [
    \"code\",
    \"tree\",
    \"text\",
    \"view\",
    \"form\"
  ],
  \"component_name\": \"JsonBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625285', 'FormTmpl', 'META_FIELD', 'meta_component.version', NULL, '{
  \"conf\": {
    \"controls\": \"false\",
    \"placeholder\": \"请输入数值..\"
  },
  \"name\": \"version\",
  \"label\": \"版本信息\",
  \"component_name\": \"NumBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625286', 'FormTmpl', 'META_FIELD', 'meta_component.created_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"created_by\",
  \"label\": \"创建人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625287', 'FormTmpl', 'META_FIELD', 'meta_component.created_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"created_time\",
  \"label\": \"创建时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625288', 'FormTmpl', 'META_FIELD', 'meta_component.updated_by', NULL, '{
  \"conf\": {
    \"maxlength\": \"64\"
  },
  \"name\": \"updated_by\",
  \"label\": \"更新人\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625289', 'FormTmpl', 'META_FIELD', 'meta_component.updated_time', NULL, '{
  \"conf\": {
    \"clearable\": \"true\",
    \"value-format\": \"yyyy-MM-dd HH:mm:ss\"
  },
  \"name\": \"updated_time\",
  \"label\": \"更新时间\",
  \"component_name\": \"DateTimeBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
INSERT INTO `meta_component_instance`
VALUES ('389855816934625290', 'FormTmpl', 'META_FIELD', 'meta_component.remark', NULL, '{
  \"conf\": {
    \"maxlength\": \"32\"
  },
  \"name\": \"remark\",
  \"label\": \"备注\",
  \"component_name\": \"TextBox\"
}', 'db-meta-web-jfinal', '2019-11-07 16:28:56', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meta_config
-- ----------------------------
DROP TABLE IF EXISTS `meta_config`;
CREATE TABLE `meta_config`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `module`       varchar(1024) DEFAULT NULL COMMENT '模块',
    `module_code`  varchar(1024) DEFAULT NULL COMMENT '模块名',
    `config`       json          DEFAULT NULL COMMENT '配置',
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
-- Table structure for meta_field
-- ----------------------------
DROP TABLE IF EXISTS `meta_field`;
CREATE TABLE `meta_field`
(
    `id`             varchar(32) NOT NULL COMMENT '主键',
    `object_code`    varchar(64)  DEFAULT NULL COMMENT '对象编码',
    `field_code`     varchar(64)  DEFAULT NULL COMMENT '字段编码',
    `is_primary`     varchar(1)   DEFAULT NULL COMMENT '主键',
    `en`             varchar(128) DEFAULT NULL COMMENT '英文',
    `cn`             varchar(128) DEFAULT NULL COMMENT '中文',
    `order_num`      int(11)      DEFAULT NULL COMMENT '排序',
    `db_type`        varchar(128) DEFAULT NULL COMMENT '数据类型',
    `db_type_length` varchar(32)  DEFAULT NULL COMMENT '数据长度',
    `java_type`      varchar(128) DEFAULT NULL COMMENT 'JAVA类型',
    `config`         json         DEFAULT NULL COMMENT '配置',
    `created_by`     varchar(64)  DEFAULT NULL COMMENT '创建人',
    `created_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `updated_by`     varchar(64)  DEFAULT NULL COMMENT '更新人',
    `updated_time`   datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`         varchar(32)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='元字段 ';

-- ----------------------------
-- Records of meta_field
-- ----------------------------
BEGIN;
INSERT INTO `meta_field`
VALUES ('389750324547686400', 'meta_object', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"id\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"false\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880704', 'meta_object', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"code\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880705', 'meta_object', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"name\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880706', 'meta_object', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"table_name\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880707', 'meta_object', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"schema_name\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880708', 'meta_object', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"primarys\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880709', 'meta_object', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"is_sys\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"0\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880710', 'meta_object', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"config\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880711', 'meta_object', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880712', 'meta_object', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880713', 'meta_object', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880714', 'meta_object', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_object\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750324551880715', 'meta_object', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"remark\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268096', 'meta_field', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"id\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"false\",
  \"objectCode\": \"meta_field\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268097', 'meta_field', 'object_code', '0', 'object_code', '对象编码', 1, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"object_code\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268098', 'meta_field', 'field_code', '0', 'field_code', '字段编码', 2, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"field_code\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268099', 'meta_field', 'is_primary', '0', 'is_primary', '主键', 3, 'varchar', '1',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"is_primary\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268100', 'meta_field', 'en', '0', 'en', '英文', 4, 'varchar', '128', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"en\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_field\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268101', 'meta_field', 'cn', '0', 'cn', '中文', 5, 'varchar', '128', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"cn\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_field\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268102', 'meta_field', 'order_num', '0', 'order_num', '排序', 6, 'int', NULL, 'java.lang.Integer', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"order_num\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_field\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268103', 'meta_field', 'db_type', '0', 'db_type', '数据类型', 7, 'varchar', '128', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"db_type\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_field\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268104', 'meta_field', 'db_type_length', '0', 'db_type_length', '数据长度', 8, 'varchar', '32',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"db_type_length\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268105', 'meta_field', 'java_type', '0', 'java_type', 'JAVA类型', 9, 'varchar', '128',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"java_type\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268106', 'meta_field', 'config', '0', 'config', '配置', 10, 'json', NULL, 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"config\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_field\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268107', 'meta_field', 'created_by', '0', 'created_by', '创建人', 11, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268108', 'meta_field', 'created_time', '0', 'created_time', '创建时间', 12, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268109', 'meta_field', 'updated_by', '0', 'updated_by', '更新人', 13, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268110', 'meta_field', 'updated_time', '0', 'updated_time', '更新时间', 14, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_field\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389750404235268111', 'meta_field', 'remark', '0', 'remark', '备注', 15, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"remark\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_field\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851904', 'change_log', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"id\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"false\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851905', 'change_log', 'object_code', '0', 'object_code', '元对象', 1, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"object_code\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"change_log\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851906', 'change_log', 'table_name', '0', 'table_name', '表名', 2, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"table_name\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"change_log\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851907', 'change_log', 'pkey', '0', 'pkey', '主键字段', 3, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"pkey\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851908', 'change_log', 'pvalue', '0', 'pvalue', '主键值', 4, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"pvalue\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851909', 'change_log', 'action', '0', 'action', '动作', 5, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"action\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851910', 'change_log', 'olddata', '0', 'olddata', '旧数据', 6, 'json', NULL, 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"olddata\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851911', 'change_log', 'newdata', '0', 'newdata', '新数据', 7, 'json', NULL, 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"newdata\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851912', 'change_log', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"change_log\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851913', 'change_log', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"change_log\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389834924921851914', 'change_log', 'remark', '0', 'remark', '备注', 10, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"remark\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"change_log\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787648', 'meta_component', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"id\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"false\",
  \"objectCode\": \"meta_component\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787649', 'meta_component', 'cn', '0', 'cn', '中文名称', 1, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"cn\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_component\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787650', 'meta_component', 'en', '0', 'en', '英文名称', 2, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"en\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_component\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787651', 'meta_component', 'code', '0', 'code', '组件编码', 3, 'varchar', '64', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"code\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_component\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787652', 'meta_component', 'config', '0', 'config', '配置信息', 4, 'json', NULL, 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"config\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"meta_component\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787653', 'meta_component', 'version', '0', 'version', '版本信息', 5, 'int', NULL, 'java.lang.Integer',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"version\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_component\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787654', 'meta_component', 'created_by', '0', 'created_by', '创建人', 6, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_component\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787655', 'meta_component', 'created_time', '0', 'created_time', '创建时间', 7, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_component\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787656', 'meta_component', 'updated_by', '0', 'updated_by', '更新人', 8, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_component\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787657', 'meta_component', 'updated_time', '0', 'updated_time', '更新时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"meta_component\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389835599621787658', 'meta_component', 'remark', '0', 'remark', '备注', 10, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_component\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714112', 'test_table', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"id\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"false\",
  \"objectCode\": \"test_table\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714113', 'test_table', 'col_int', '0', 'col_int', '普通整数', 1, 'int', NULL, 'java.lang.Integer', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"col_int\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"test_table\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714114', 'test_table', 'col_bigint', '0', 'col_bigint', '大整数', 2, 'bigint', NULL, 'java.lang.Long',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"col_bigint\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"test_table\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714115', 'test_table', 'col_bool', '0', 'col_bool', '布尔', 3, 'varchar', '1', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"col_bool\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"test_table\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714116', 'test_table', 'col_date', '0', 'col_date', '日期', 4, 'date', NULL, 'java.util.Date', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"col_date\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"test_table\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714117', 'test_table', 'col_time', '0', 'col_time', '时间', 5, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"col_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714118', 'test_table', 'col_decimal', '0', 'col_decimal', '金额', 6, 'decimal', NULL,
        'java.math.BigDecimal', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"col_decimal\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714119', 'test_table', 'col_nor_str', '0', 'col_nor_str', '普通字符', 7, 'varchar', '1024',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"col_nor_str\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714120', 'test_table', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714121', 'test_table', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"created_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714122', 'test_table', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_by\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714123', 'test_table', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
    \"version\": \"0.1\",
    \"fieldCode\": \"updated_time\",
    \"isMultiple\": \"false\",
    \"isNullable\": \"true\",
    \"objectCode\": \"test_table\",
    \"defaultValue\": \"\"
  }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_field`
VALUES ('389856267226714124', 'test_table', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"remark\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"true\",
  \"objectCode\": \"test_table\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meta_object
-- ----------------------------
DROP TABLE IF EXISTS `meta_object`;
CREATE TABLE `meta_object`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `code`         varchar(64)  DEFAULT NULL COMMENT '对象编码',
    `name`         varchar(128) DEFAULT NULL COMMENT '对象名',
    `table_name`   varchar(64)  DEFAULT NULL COMMENT '表名',
    `schema_name`  varchar(64)  DEFAULT NULL COMMENT '库名',
    `primarys`     varchar(128) DEFAULT NULL COMMENT '主键组',
    `is_sys`       varchar(1)   DEFAULT '0' COMMENT '系统模块',
    `config`       json         DEFAULT NULL COMMENT '配置',
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
-- Records of meta_object
-- ----------------------------
BEGIN;
INSERT INTO `meta_object`
VALUES ('389750324505743360', 'meta_object', 'meta_object', 'meta_object', 'metadata', 'id', '1', '{
  \"isMultiple\": \"false\",
  \"objectCode\": \"meta_object\",
  \"isUUIDPrimary\": \"true\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_object`
VALUES ('389750404201713664', 'meta_field', 'meta_field', 'meta_field', 'metadata', 'id', '1', '{
  \"isMultiple\": \"false\",
  \"objectCode\": \"meta_field\",
  \"isUUIDPrimary\": \"true\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_object`
VALUES ('389834924875714560', 'change_log', 'change_log', 'change_log', 'metadata', 'id', '0', '{
  \"isMultiple\": \"false\",
  \"objectCode\": \"change_log\",
  \"isUUIDPrimary\": \"true\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_object`
VALUES ('389835599600816128', 'meta_component', 'meta_component', 'meta_component', 'metadata', 'id', '0', '{
  \"isMultiple\": \"false\",
  \"objectCode\": \"meta_component\",
  \"isUUIDPrimary\": \"true\"
}', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `meta_object`
VALUES ('389856267205742592', 'test_table', 'test_table', 'test_table', 'metadata', 'id', '0', '{
  \"isMultiple\": \"false\",
  \"objectCode\": \"test_table\",
  \"isUUIDPrimary\": \"true\"
}', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for pdman_db_version
-- ----------------------------
DROP TABLE IF EXISTS `pdman_db_version`;
CREATE TABLE `pdman_db_version`
(
    `DB_VERSION`   varchar(256)  DEFAULT NULL,
    `VERSION_DESC` varchar(1024) DEFAULT NULL,
    `CREATED_TIME` varchar(32)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of pdman_db_version
-- ----------------------------
BEGIN;
INSERT INTO `pdman_db_version`
VALUES ('v0.0.0', '默认版本，新增的版本不能低于此版本', '2019-08-23 15:50:17');
INSERT INTO `pdman_db_version`
VALUES ('0.6', '0.6', '2019-08-23 15:50:25');
INSERT INTO `pdman_db_version`
VALUES ('0.7', '增加配置', '2019-08-23 16:56:55');
INSERT INTO `pdman_db_version`
VALUES ('0.8', '是否主键', '2019-08-23 19:02:35');
INSERT INTO `pdman_db_version`
VALUES ('0.9', '0.9', '2019-09-12 11:00:35');
COMMIT;

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
    `col_nor_str`  varchar(1024)  DEFAULT NULL COMMENT '普通字符',
    `created_by`   varchar(64)    DEFAULT NULL COMMENT '创建人',
    `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(64)    DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime       DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(32)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='测试表 ';

SET FOREIGN_KEY_CHECKS = 1;
