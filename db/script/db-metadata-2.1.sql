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

 Date: 07/03/2022 18:53:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meta_api_resource
-- ----------------------------
DROP TABLE IF EXISTS `meta_api_resource`;
CREATE TABLE `meta_api_resource` (
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT NULL,
  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
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
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建数据',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_union` (`type`,`uri`,`meta_code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内置资源表:接口资源';

-- ----------------------------
-- Records of meta_api_resource
-- ----------------------------
BEGIN;
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:50:32', NULL, '游客', NULL, '694223070646374400', '路由数据', '0', '/router', '', b'0', b'0', 'auth', '', 'any', '', 'any', '路由接口, 属于系统数据, 需要不登录也能正常获取', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:51:14', NULL, '游客', NULL, '694225274480496640', '菜单数据', '0', '/menu', '', b'1', b'1', 'auth', '', 'any', '', 'any', '菜单数据，登录后才能进入管理界面;登录即可访问', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:50:53', NULL, '游客', NULL, '694227116228743168', '系统属性', '0', '/app/config', '', b'0', b'0', 'auth', '', 'any', '', 'any', '系统配置数据, 无需鉴权', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:51:23', NULL, '游客', NULL, '694231657120665600', '登录接口', '0', '/user/login', '', b'0', b'0', 'auth', '', 'any', '', 'any', '用户登录接口', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:51:43', NULL, '游客', NULL, '694231832820060160', '当前登录用户信息接口', '0', '/user/info', '', b'1', b'1', 'auth', '', 'any', '', 'any', '当前用户获取其登录信息的接口', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:51:57', NULL, '游客', NULL, '694231925002473472', '登出接口', '0', '/user/logout', '', b'1', b'1', 'auth', '', 'any', '', 'any', '登出接口', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:52:09', NULL, '游客', NULL, '694232362430631936', '数据库列表接口', '0', '/db/list', '', b'1', b'0', 'role', '', 'any', 'ROOT', 'any', '获取数据库列表', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:52:27', NULL, '游客', NULL, '694232534690697216', '数据库列表接口', '0', '/db/index', '', b'1', b'0', 'role', '', 'any', 'ROOT', 'any', '获取数据库列表。等同/db/list', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:52:41', NULL, '游客', NULL, '694232700655112192', '关系表列表接口', '0', '/db/tables', '', b'1', b'0', 'role', '', 'any', 'ROOT', 'any', '获取所有表', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:52:58', NULL, '游客', NULL, '694233825454198784', '系统初始化接口', '0', '/db/init', '', b'1', b'0', 'role', '', 'any', 'ROOT', 'any', '系统初始化接口。必须具有ROOT权限', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:55:55', NULL, '游客', NULL, '694238218186526720', '字典数据接口', '0', '/dict', '', b'0', b'0', 'auth', '', 'any', '', 'any', '系统字典', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:57:35', NULL, '游客', NULL, '694238636744511488', '文件上传接口', '0', '/file/upload', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-02-24 14:58:19', NULL, '游客', NULL, '694238822514429952', '富文本文件上传', '0', '/file/upload/richText', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:42:20', NULL, 'SYSTEM', NULL, '696167536378646528', '元对象列表', '1', '/table/list', 'meta_object', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:43:51', NULL, 'SYSTEM', NULL, '696167915807969280', '新增元对象接口', '0', '/meta/doAdd', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:45:17', NULL, 'SYSTEM', NULL, '696168277629603840', '元对象更新', '1', '/form/doUpdate', 'meta_object', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:47:24', NULL, 'SYSTEM', NULL, '696168807957401600', '元对象删除', '0', '/meta/delete', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:48:36', NULL, 'SYSTEM', NULL, '696169111574679552', '元字段同步', '0', '/meta/incrementImport', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:49:30', NULL, 'SYSTEM', NULL, '696169336997548032', '元字段更新', '1', '/form/doUpdate', 'meta_field', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:51:09', NULL, 'SYSTEM', NULL, '696169751176679424', '元字段删除', '1', '/table/delete', 'meta_field', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:52:43', NULL, 'SYSTEM', NULL, '696170147102199808', '元字段列表', '1', '/table/list', 'meta_field', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:53:53', NULL, 'SYSTEM', NULL, '696170439403245568', '功能列表', '1', '/table/list', 'meta_feature', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:55:17', NULL, 'SYSTEM', NULL, '696170795436740608', '功能新增', '0', '/feature/doAdd', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:22:19', NULL, 'SYSTEM', NULL, '696171090757685248', '功能更新', '1', '/form/doUpdate', 'meta_feature', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:56:59', NULL, 'SYSTEM', NULL, '696171222936981504', '功能删除', '1', '/table/delete', 'meta_feature', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:58:21', NULL, 'SYSTEM', NULL, '696171563858399232', '组件全局配置更新', '1', '/form/doUpdate', 'meta_component', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 22:59:11', NULL, 'SYSTEM', NULL, '696171773636513792', '组件全局配置删除', '1', '/table/delete', 'meta_component', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:11:25', NULL, 'SYSTEM', NULL, '696171997700427776', '组件全局配置/实例配置保存接口', '0', '/component/doUpdate', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:00:53', NULL, 'SYSTEM', NULL, '696172202269216768', '组件实例配置数据接口', '1', '/table/list', 'meta_component_instance', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:02:22', NULL, 'SYSTEM', NULL, '696172577076416512', '组件实例配置更新接口', '1', '/form/doUpdate', 'meta_component_instance', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:02:53', NULL, 'SYSTEM', NULL, '696172706986594304', '组件实例配置删除接口', '1', '/table/delete', 'meta_component_instance', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:13:11', NULL, 'SYSTEM', NULL, '696175296763793408', '菜单数据列表', '1', '/table/tree', 'meta_menu', b'1', b'0', 'auth', 'api:query:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:14:07', NULL, 'SYSTEM', NULL, '696175533460951040', '新增菜单', '1', '/form/doAdd', 'meta_menu', b'1', b'0', 'auth', 'api:add:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:14:49', NULL, 'SYSTEM', NULL, '696175709512667136', '更新菜单', '1', '/form/doUpdate', 'meta_menu', b'1', b'0', 'auth', 'api:update:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:15:26', NULL, 'SYSTEM', NULL, '696175862860615680', '菜单删除', '1', '/table/delete', 'meta_menu', b'1', b'0', 'auth', 'api:delete:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:18:49', NULL, 'SYSTEM', NULL, '696176715805888512', '路由列表', '1', '/table/tree', 'meta_router', b'1', b'0', 'auth', 'api:query:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:19:38', NULL, 'SYSTEM', NULL, '696176920043327488', '更新路由', '1', '/form/doUpdate', 'meta_router', b'1', b'0', 'auth', 'api:update:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:19:59', NULL, 'SYSTEM', NULL, '696177008371175424', '新增路由', '1', '/form/doAdd', 'meta_router', b'1', b'0', 'auth', 'api:add:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:20:19', NULL, 'SYSTEM', NULL, '696177092982870016', '删除路由', '1', '/table/delete', 'meta_router', b'1', b'0', 'auth', 'api:delete:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:21:11', NULL, 'SYSTEM', NULL, '696177310180708352', '新增字典', '1', '/form/doAdd', 'meta_dict', b'1', b'0', 'auth', 'api:add:meta_dict', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:21:34', NULL, 'SYSTEM', NULL, '696177407417257984', '更新字典', '1', '/form/doUpdate', 'meta_dict', b'1', b'0', 'auth', 'api:update:meta_dict', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:22:01', NULL, 'SYSTEM', NULL, '696177519040270336', '删除字典', '1', '/table/delete', 'meta_dict', b'1', b'0', 'auth', 'api:delete:meta_dict', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:23:27', NULL, 'SYSTEM', NULL, '696177879901409280', '异常数据删除', '1', '/table/delete', 'meta_exception', b'1', b'0', 'auth', 'api:delete:meta_exception', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:24:16', NULL, 'SYSTEM', NULL, '696178023577292800', '权限数据新增接口', '1', '/form/doAdd', 'meta_auth', b'1', b'0', 'auth', 'api:add:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:24:41', NULL, 'SYSTEM', NULL, '696178190523174912', '权限数据查询接口', '1', '/table/list', 'meta_auth', b'1', b'0', 'auth', 'api:query:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:25:07', NULL, 'SYSTEM', NULL, '696178301361852416', '权限数据更新接口', '1', '/form/doUpdate', 'meta_auth', b'1', b'0', 'auth', 'api:update:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:25:26', NULL, 'SYSTEM', NULL, '696178379577233408', '权限数据删除接口', '1', '/table/delete', 'meta_auth', b'1', b'0', 'auth', 'api:delete:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:26:03', NULL, 'SYSTEM', NULL, '696178536532283392', '接口资源新增接口', '1', '/form/doAdd', 'meta_api_resource', b'1', b'0', 'auth', 'api:add:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:26:31', NULL, 'SYSTEM', NULL, '696178653947629568', '接口资源查询接口', '1', '/table/list', 'meta_api_resource', b'1', b'0', 'auth', 'api:query:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:27:01', NULL, 'SYSTEM', NULL, '696178779864829952', '接口资源更新接口', '1', '/form/doUpdate', 'meta_api_resource', b'1', b'0', 'auth', 'api:update:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-01 23:27:21', NULL, 'SYSTEM', NULL, '696178862790414336', '接口资源删除接口', '1', '/table/delete', 'meta_api_resource', b'1', b'0', 'auth', 'api:delete:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:06:50', NULL, 'SYSTEM', NULL, '696339795437293568', '用户数据查询', '1', '/table/list', 'meta_user', b'1', b'0', 'auth', 'api:query:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:07:37', NULL, 'SYSTEM', NULL, '696339990992523264', '用户新增接口', '1', '/form/doAdd', 'meta_user', b'1', b'0', 'auth', 'api:add:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:07:59', NULL, 'SYSTEM', NULL, '696340082122166272', '用户更新接口', '1', '/form/doUpdate', 'meta_user', b'1', b'0', 'auth', 'api:update:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:08:31', NULL, 'SYSTEM', NULL, '696340217535270912', '用户删除接口', '1', '/table/delete', 'meta_user', b'1', b'0', 'auth', 'api:delete:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:16:56', NULL, 'SYSTEM', NULL, '696342334748626944', '角色新增接口', '1', '/form/doAdd', 'meta_role', b'1', b'0', 'auth', 'api:add:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:17:20', NULL, 'SYSTEM', NULL, '696342436988981248', '角色删除接口', '1', '/table/delete', 'meta_role', b'1', b'0', 'auth', 'api:delete:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:17:47', NULL, 'SYSTEM', NULL, '696342549929005056', '角色更新接口', '1', '/form/doUpdate', 'meta_role', b'1', b'0', 'auth', 'api:update:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-02 10:18:10', NULL, 'SYSTEM', NULL, '696342647471738880', '角色列表查询接口', '1', '/table/list', 'meta_role', b'1', b'0', 'auth', 'api:query:role', 'any', '', 'any', '', b'1');
COMMIT;

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
  `type` varchar(20) NOT NULL COMMENT '权限类别(枚举)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建权限',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_code` (`code`) COMMENT '权限编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限控制表';

-- ----------------------------
-- Records of meta_auth
-- ----------------------------
BEGIN;
INSERT INTO `meta_auth` VALUES ('2022-02-24 16:04:55', NULL, 'SYSTEM', NULL, '694148810477277184', 'menu:meta:meta-data', '菜单:元数据管理', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 16:04:59', NULL, 'SYSTEM', NULL, '694149240007561216', 'menu:meta:feature', '菜单:功能维护', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:02:22', NULL, 'SYSTEM', NULL, '694149341149007872', 'menu:meta:component', '菜单:组件全局配置', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:02:09', NULL, 'SYSTEM', NULL, '694149424439496704', 'menu:meta:component-instance', '菜单:组件实例配置', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:01:27', NULL, 'SYSTEM', NULL, '694149476058796032', 'menu:meta:menu', '菜单:菜单维护', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:01:18', NULL, 'SYSTEM', NULL, '694149530551193600', 'menu:meta:router', '菜单:路由维护', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:01:41', NULL, 'SYSTEM', NULL, '694149630207856640', 'menu:meta:form-builder', '菜单:表单构建', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:01:07', NULL, 'SYSTEM', NULL, '694149720175677440', 'menu:meta:meta-conf', '菜单:MetaConf', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:00:58', NULL, 'SYSTEM', NULL, '694149774206701568', 'menu:meta:dict', '菜单:字典', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:00:48', NULL, 'SYSTEM', NULL, '694149834445295616', 'menu:meta:exception', '菜单:异常', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:00:36', NULL, 'SYSTEM', NULL, '694149907166138368', 'menu:meta:auth', '菜单:权限配置', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:00:23', NULL, 'SYSTEM', NULL, '694149960505102336', 'menu:meta:api-resource', '菜单:接口资源', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 15:00:09', '2022-03-07 10:30:35', 'SYSTEM', '0', '694150160565014528', 'menu:meta:user', '菜单:用户管理', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 14:59:57', '2022-03-07 10:30:40', 'SYSTEM', '0', '694183855120322560', 'menu:meta:role', '菜单:角色管理', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 16:07:01', NULL, 'SYSTEM', NULL, '694256110563102720', 'router:meta:feature', '路由:功能维护', '平台维护', 'router', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 17:53:28', NULL, 'SYSTEM', NULL, '694280287013703680', 'api:app-init', '系统初始化', '系统配置', 'api', '拥有此权限则可调用系统初始化接口，完成系统初始化', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:04:25', NULL, 'SYSTEM', NULL, '694330954252161024', 'menu:meta:platform-ops', '菜单:平台维护', '平台维护', 'menu', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:22:59', NULL, 'SYSTEM', NULL, '694335625574354944', 'route:meta:instance-conf-edit', '组件实例配置编辑', '平台维护', 'router', '编辑组件UI实例配置', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:24:48', NULL, 'SYSTEM', NULL, '694336083923701760', 'route:meta:manager', '路由:元数据管理', '平台维护', 'router', '维护元对象和元字段', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:55:27', NULL, 'SYSTEM', NULL, '694343798075035648', 'route:meta:feature', '路由:功能维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:55:48', NULL, 'SYSTEM', NULL, '694343885119426560', 'route:meta:global-conf-list', '路由:组件全局配置', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:56:35', NULL, 'SYSTEM', NULL, '694344082931191808', 'route:meta:global-conf-edit', '路由:组件全局配置-编辑', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:57:09', NULL, 'SYSTEM', NULL, '694344223582982144', 'route:meta:instance-conf-list', '路由:组件实例配置', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:57:36', NULL, 'SYSTEM', NULL, '694344336845967360', 'route:meta:router', '路由:路由维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:57:53', NULL, 'SYSTEM', NULL, '694344409273208832', 'route:meta:menu', '路由:菜单维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:58:13', NULL, 'SYSTEM', NULL, '694344494769901568', 'route:meta:form-builder', '路由:表单构建', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:58:34', NULL, 'SYSTEM', NULL, '694344582720262144', 'route:meta:meta-conf', '路由:meta-conf', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:58:57', NULL, 'SYSTEM', NULL, '694344675334688768', 'route:meta:dict', '路由:字典维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:59:22', NULL, 'SYSTEM', NULL, '694344780561387520', 'route:meta:exception', '路由:系统异常', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 21:59:44', NULL, 'SYSTEM', NULL, '694344875012919296', 'route:meta:auth', '路由:权限配置', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-02-24 22:00:07', NULL, 'SYSTEM', NULL, '694344969531559936', 'route:meta:api-resource', '路由:接口资源', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:46:28', NULL, 'SYSTEM', NULL, '696152559060127744', 'api:query:meta_auth', '权限列表', '权限', 'api', '查看权限编码列表数据', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:46:21', NULL, 'SYSTEM', NULL, '696153360272855040', 'api:delete:meta_auth', '删除权限', '权限', 'api', '拥有权限数据的删除权限', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:47:29', NULL, 'SYSTEM', NULL, '696153730193690624', 'api:add:meta_auth', '权限新增', '权限', 'api', '拥有新增权限的权限', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:47:58', NULL, 'SYSTEM', NULL, '696153853300707328', 'api:update:meta_auth', '权限更新', '权限', 'api', '拥有更新权限的权限', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:51:02', NULL, 'SYSTEM', NULL, '696154203961298944', 'api:add:api_meta_resource', '新增接口资源', '接口资源', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:50:58', NULL, 'SYSTEM', NULL, '696154346861236224', 'api:delete:api_meta_resource', '删除接口资源', '接口资源', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:50:54', NULL, 'SYSTEM', NULL, '696154466335985664', 'api:update:api_meta_resource', '更新接口资源', '接口资源', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:50:49', NULL, 'SYSTEM', NULL, '696154568492453888', 'api:query:api_meta_resource', '查询接口资源', '接口资源', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:51:52', NULL, 'SYSTEM', NULL, '696154835401183232', 'api:query:meta_exception', '查看异常', '异常', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:52:10', NULL, 'SYSTEM', NULL, '696154907899727872', 'api:delete:meta_exception', '删除异常', '异常', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:52:39', NULL, 'SYSTEM', NULL, '696155030516011008', 'api:add:meta_dict', '新增字典', '字典', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:53:01', NULL, 'SYSTEM', NULL, '696155122442571776', 'api:delete:meta_dict', '删除字典', '字典', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:53:38', NULL, 'SYSTEM', NULL, '696155278286131200', 'api:update:meta_dict', '更新字典', '字典', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:54:01', NULL, 'SYSTEM', NULL, '696155376374124544', 'api:query:meta_dict', '查询字典', '字典', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:54:31', NULL, 'SYSTEM', NULL, '696155502178078720', 'api:add:meta_router', '新增路由', '路由', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:54:46', NULL, 'SYSTEM', NULL, '696155564840980480', 'api:delete:meta_router', '删除路由', '路由', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:55:08', NULL, 'SYSTEM', NULL, '696155654074798080', 'api:update:meta_router', '更新路由', '路由', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:56:11', NULL, 'SYSTEM', NULL, '696155922124378112', 'api:add:meta_menu', '新增菜单', '菜单', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:56:33', NULL, 'SYSTEM', NULL, '696156011769237504', 'api:delete:meta_menu', '删除菜单', '菜单', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:56:49', NULL, 'SYSTEM', NULL, '696156079964426240', 'api:update:meta_menu', '更新菜单', '菜单', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 21:57:09', NULL, 'SYSTEM', NULL, '696156165645668352', 'api:query:meta_menu', '查询菜单', '菜单', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:11:02', NULL, 'SYSTEM', NULL, '696159659219881984', 'api:add:user', '新增用户', '用户', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:11:15', NULL, 'SYSTEM', NULL, '696159713649364992', 'api:delete:user', '删除用户', '用户', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:11:29', NULL, 'SYSTEM', NULL, '696159771941801984', 'api:update:user', '更新用户', '用户', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:11:45', NULL, 'SYSTEM', NULL, '696159836404060160', 'api:query:user', '查询所有用户', '用户', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:11:59', NULL, 'SYSTEM', NULL, '696159898110660608', 'api:add:role', '新增角色', '角色', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:12:19', NULL, 'SYSTEM', NULL, '696159978653880320', 'api:delete:role', '删除角色', '角色', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:12:37', NULL, 'SYSTEM', NULL, '696160054025523200', 'api:update:role', '更新角色', '角色', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:12:50', NULL, 'SYSTEM', NULL, '696160111751729152', 'api:query:role', '查询角色', '角色', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 22:33:58', NULL, 'SYSTEM', NULL, '696163222130135040', 'api:meta_data:maintain', '元数据维护', '元数据', 'api', '拥有此权限则可调用维护元数据相关的所有接口。元数据包括:元对象、元字段、组件配置、组件实例配置、功能。相关接口囊括对元数据的增删改查和配置。', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-01 23:17:38', NULL, 'SYSTEM', NULL, '696176274166648832', 'api:query:meta_router', '查询路由', '路由', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-03 22:19:38', NULL, '691732305378676736', NULL, '696886596984770560', 'route:user:list', '用户管理', '用户管理', 'router', '', b'0');
INSERT INTO `meta_auth` VALUES ('2022-03-03 22:20:04', NULL, '691732305378676736', NULL, '696886708112855040', 'route:role:list', '角色管理', '用户管理', 'router', '', b'0');
INSERT INTO `meta_auth` VALUES ('2022-03-04 16:28:05', '2022-03-04 16:29:34', '691732305378676736', '691732305378676736', '697160516417753088', 'api:bind:roles-to-user', '为指定用户绑定角色', '用户', 'api', '', b'0');
INSERT INTO `meta_auth` VALUES ('2022-03-04 16:28:28', '2022-03-04 16:29:47', '691732305378676736', '691732305378676736', '697160611427127296', 'api:get:auths-of-role', '获取指定角色拥有的权限', '角色', 'api', '', b'0');
INSERT INTO `meta_auth` VALUES ('2022-03-04 16:28:49', '2022-03-04 16:29:57', '691732305378676736', '691732305378676736', '697160698198888448', 'api:bind:auths-to-role', '为指定角色绑定权限', '角色', 'api', '', b'0');
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
  `cn` varchar(32) NOT NULL COMMENT '中文名称',
  `en` varchar(32) NOT NULL COMMENT '英文名称',
  `code` varchar(64) NOT NULL COMMENT '组件编码',
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
  `code` varchar(128) NOT NULL COMMENT '配置编码',
  `comp_code` varchar(32) NOT NULL COMMENT '组件编码',
  `type` varchar(32) NOT NULL COMMENT '类型',
  `dest_object` varchar(128) NOT NULL COMMENT '目标对象',
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
  `pid` varchar(32) DEFAULT NULL COMMENT '父级节点',
  `dict_type` varchar(64) DEFAULT NULL COMMENT '字典类别',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `value` varchar(128) NOT NULL COMMENT '值',
  `order_num` varchar(32) DEFAULT NULL COMMENT '排序',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
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
INSERT INTO `meta_exception` VALUES ('698284090796937216', 'null', 'com.github.md.web.user.UnLoginException: 未认证\n	at com.github.md.web.user.AuthenticationManager.permit(AuthenticationManager.java:109)\n	at com.github.md.web.user.AuthenticationConfigurer.lambda$configAuthInterceptDoer$0(AuthenticationConfigurer.java:52)\n	at com.github.md.web.user.auth.MRAuthIntercept.lambda$preHandle$0(MRAuthIntercept.java:29)\n	at java.util.stream.MatchOps$1MatchSink.accept(MatchOps.java:90)\n	at java.util.ArrayList$ArrayListSpliterator.tryAdvance(ArrayList.java:1361)\n	at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:126)\n	at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:499)\n	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:486)\n	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)\n	at java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:230)\n	at java.util.stream.MatchOps$MatchOp.evaluateSequential(MatchOps.java:196)\n	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n	at java.util.stream.ReferencePipeline.allMatch(ReferencePipeline.java:454)\n	at com.github.md.web.user.auth.MRAuthIntercept.preHandle(MRAuthIntercept.java:29)\n	at org.springframework.web.servlet.HandlerExecutionChain.applyPreHandle(HandlerExecutionChain.java:148)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1059)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at com.github.md.web.config.MetaServerWebMvcConfigurer.lambda$bodyRepeatFilter$0(MetaServerWebMvcConfigurer.java:59)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:197)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:135)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:382)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:893)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1726)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\n', 'null', '/user/info', '{}', '{}', 'db-metadata-server', '2022-03-07 18:52:46', NULL, NULL, NULL);
INSERT INTO `meta_exception` VALUES ('698284229431267328', 'null', 'java.io.IOException: Stream closed\n	at org.apache.catalina.connector.InputBuffer.throwIfClosed(InputBuffer.java:527)\n	at org.apache.catalina.connector.InputBuffer.read(InputBuffer.java:338)\n	at org.apache.catalina.connector.CoyoteInputStream.read(CoyoteInputStream.java:132)\n	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)\n	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)\n	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)\n	at java.io.InputStreamReader.read(InputStreamReader.java:184)\n	at java.io.BufferedReader.fill(BufferedReader.java:161)\n	at java.io.BufferedReader.read1(BufferedReader.java:212)\n	at java.io.BufferedReader.read(BufferedReader.java:286)\n	at java.io.Reader.read(Reader.java:140)\n	at com.github.md.web.config.json.JsonParameterToMapHandler$WritableHttpServletRequestWrapper.parseBody(JsonParameterToMapHandler.java:133)\n	at com.github.md.web.config.json.JsonParameterToMapHandler$WritableHttpServletRequestWrapper.<init>(JsonParameterToMapHandler.java:78)\n	at com.github.md.web.config.json.JsonParameterToMapHandler.preHandle(JsonParameterToMapHandler.java:39)\n	at org.springframework.web.servlet.HandlerExecutionChain.applyPreHandle(HandlerExecutionChain.java:148)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1059)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:103)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:103)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\n	at org.apache.catalina.core.ApplicationDispatcher.invoke(ApplicationDispatcher.java:711)\n	at org.apache.catalina.core.ApplicationDispatcher.processRequest(ApplicationDispatcher.java:461)\n	at org.apache.catalina.core.ApplicationDispatcher.doForward(ApplicationDispatcher.java:385)\n	at org.apache.catalina.core.ApplicationDispatcher.forward(ApplicationDispatcher.java:313)\n	at org.apache.catalina.core.StandardHostValve.custom(StandardHostValve.java:390)\n	at org.apache.catalina.core.StandardHostValve.status(StandardHostValve.java:249)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:382)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:893)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1726)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\n', 'null', '/error', '{}', '{}', 'db-metadata-server', '2022-03-07 18:53:19', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meta_feature
-- ----------------------------
DROP TABLE IF EXISTS `meta_feature`;
CREATE TABLE `meta_feature` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `type` varchar(32) NOT NULL COMMENT '功能类型',
  `name` varchar(32) DEFAULT NULL COMMENT '功能名',
  `code` varchar(32) NOT NULL COMMENT '功能编码',
  `config` json DEFAULT NULL COMMENT '配置',
  `op_config` json DEFAULT NULL COMMENT '操作配置',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建功能',
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
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建菜单',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of meta_menu
-- ----------------------------
BEGIN;
INSERT INTO `meta_menu` VALUES ('696347057069363200', '', '首页', b'0', b'0', 'dashboard', '/dashboard', 0, '', b'0', 'auth', '', 'any', '', 'any', b'1', '2022-03-02 10:35:42', 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('7bcfbe31357f48bf8c88072a18208599', '', '系统管理', b'0', b'0', 'system-manager', '/user', 3, '', b'1', 'auth', 'menu:meta:user', 'any', '', 'any', b'1', '2022-02-27 20:59:54', 'SYSTEM', '0', '2022-03-07 10:32:28');
INSERT INTO `meta_menu` VALUES ('96eab66157be4165b9916d6f8b079dd9', '7bcfbe31357f48bf8c88072a18208599', '角色管理', b'0', b'0', 'role', '/system/role', 1, '', b'1', 'auth', 'menu:meta:role', 'any', '', 'any', b'1', '2022-02-28 14:52:10', 'SYSTEM', '0', '2022-03-07 18:04:58');
INSERT INTO `meta_menu` VALUES ('f49ce02324d94fa698a0db718e380074', '7bcfbe31357f48bf8c88072a18208599', '用户管理', b'0', b'0', 'user_manage', '/system/user', 0, '', b'1', 'auth', 'menu:meta:user', 'any', '', 'any', b'1', '2022-02-28 14:51:52', 'SYSTEM', '0', '2022-03-07 18:04:29');
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
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of meta_role
-- ----------------------------
BEGIN;
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
  `pid` varchar(32) DEFAULT NULL COMMENT '父节点',
  `cn` varchar(255) DEFAULT NULL COMMENT '中文名称',
  `name` varchar(255) NOT NULL COMMENT '路由name',
  `path` varchar(255) NOT NULL COMMENT '路由path',
  `redirect` varchar(255) DEFAULT NULL COMMENT '重定向path',
  `component` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `components` json DEFAULT NULL COMMENT '多重组件',
  `meta` json DEFAULT NULL COMMENT '配置信息',
  `remark` text COMMENT '备注',
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建路由',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由信息';

-- ----------------------------
-- Records of meta_router
-- ----------------------------
BEGIN;
INSERT INTO `meta_router` VALUES ('2b17f805d48548ef89a574bd80a0dd3a', '698272484012724224', '用户列表', 'UserList', '/system/user', '', 'UserList', '{}', '{\"title\": \"用户列表\", \"disable\": false}', '', b'1', '2022-03-01 15:13:55', 'SYSTEM', '0', '2022-03-07 18:37:12');
INSERT INTO `meta_router` VALUES ('696345295902085120', '698272484012724224', '首页', 'Dashboard', '/dashboard', '', 'Dashboard', NULL, '{\"affix\": true, \"order\": -99999, \"title\": \"首页\", \"need_permit\": \"true\"}', '', b'1', '2022-03-02 10:55:23', 'SYSTEM', '0', '2022-03-07 18:06:45');
INSERT INTO `meta_router` VALUES ('698272484012724224', NULL, 'Admin布局', 'AdminLayout', '/admin', '/dashboard', 'MetaLayout', NULL, '{\"need_permit\": false}', NULL, b'0', '2022-03-07 18:06:39', '0', '0', '2022-03-07 18:36:02');
INSERT INTO `meta_router` VALUES ('698273043436408832', NULL, '登录', 'Login', '/login', NULL, 'Login', NULL, '{\"need_permit\": false}', NULL, b'0', '2022-03-07 18:08:52', '0', '0', '2022-03-07 18:09:31');
INSERT INTO `meta_router` VALUES ('698273460404752384', NULL, '401页面', 'Page401', '/401', NULL, 'Page401', NULL, '{\"need_permit\": false}', NULL, b'0', '2022-03-07 18:10:32', '0', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273579418128384', NULL, '404页面', 'Page404', '*', NULL, 'Page404', NULL, '{\"order\": 999999, \"need_permit\": false}', NULL, b'0', '2022-03-07 18:11:00', '0', '0', '2022-03-07 18:13:35');
INSERT INTO `meta_router` VALUES ('698275652171862016', NULL, 'Header布局', 'HeaderLayout', '/', NULL, 'HeaderLayout', NULL, '{}', NULL, b'0', '2022-03-07 18:19:14', '0', '0', '2022-03-07 18:34:29');
INSERT INTO `meta_router` VALUES ('e5a688069fcf43c8ab98af55c105890e', '698272484012724224', '角色列表', 'RoleList', '/system/role', '', 'RoleList', '{}', '{\"title\": \"角色列表\"}', '', b'1', '2022-02-17 12:06:33', 'SYSTEM', '0', '2022-03-07 18:37:19');
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
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `attrs` json DEFAULT NULL COMMENT '扩展属性',
  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_username` (`username`) USING HASH,
  UNIQUE KEY `uq_phone` (`phone`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of meta_user
-- ----------------------------
BEGIN;
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
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
