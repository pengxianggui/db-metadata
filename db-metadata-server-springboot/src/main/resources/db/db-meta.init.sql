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
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694223070646374400', '路由数据', '0', '/router', '', b'0', b'0', 'auth', '', 'any', '', 'any', '路由接口, 属于系统数据, 需要不登录也能正常获取', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694225274480496640', '菜单数据', '0', '/menu', '', b'1', b'1', 'auth', '', 'any', '', 'any', '菜单数据，登录后才能进入管理界面;登录即可访问', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694227116228743168', '系统属性', '0', '/app/config', '', b'0', b'0', 'auth', '', 'any', '', 'any', '系统配置数据, 无需鉴权', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694231657120665600', '登录接口', '0', '/user/login', '', b'0', b'0', 'auth', '', 'any', '', 'any', '用户登录接口', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694231832820060160', '当前登录用户信息接口', '0', '/user/info', '', b'1', b'1', 'auth', '', 'any', '', 'any', '当前用户获取其登录信息的接口', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694231925002473472', '登出接口', '0', '/user/logout', '', b'0', b'0', 'auth', '', 'any', '', 'any', '登出接口', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694232534690697216', '数据库列表接口', '0', '/db/index', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', 'ROOT', 'any', '获取数据库列表。等同/db/list', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694232700655112192', '关系表列表接口', '0', '/db/tables', '', b'1', b'0', 'role', 'api:meta_data:maintain', 'any', 'ROOT', 'any', '获取所有表', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694233825454198784', '系统初始化接口', '0', '/db/init', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', 'ROOT', 'any', '系统初始化接口。必须具有ROOT权限', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694238218186526720', '字典数据接口', '0', '/dict', '', b'0', b'0', 'auth', '', 'any', '', 'any', '系统字典', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694238636744511488', '文件上传接口', '0', '/file/upload', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694238822514429952', '富文本文件上传', '0', '/file/upload/richText', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696167536378646528', '元对象列表', '1', '/table/list', 'meta_object', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696167915807969280', '新增元对象接口', '0', '/meta/doAdd', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696168277629603840', '元对象更新', '1', '/form/doUpdate', 'meta_object', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696168807957401600', '元对象删除', '0', '/meta/delete', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696169111574679552', '元字段同步', '0', '/meta/incrementImport', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696169336997548032', '元字段更新', '1', '/form/doUpdate', 'meta_field', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696169751176679424', '元字段删除', '1', '/table/delete', 'meta_field', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696170147102199808', '元字段列表', '1', '/table/list', 'meta_field', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696170439403245568', '功能配置列表', '1', '/table/list', 'meta_feature', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696170795436740608', '功能新增', '0', '/feature/doAdd', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696171090757685248', '功能更新', '1', '/form/doUpdate', 'meta_feature', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696171222936981504', '功能删除', '1', '/table/delete', 'meta_feature', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696171563858399232', '组件全局配置更新', '1', '/form/doUpdate', 'meta_component', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696171773636513792', '组件全局配置删除', '1', '/table/delete', 'meta_component', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696171997700427776', '组件全局配置/实例配置保存接口', '0', '/component/doUpdate', '', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696172202269216768', '组件实例配置列表', '1', '/table/list', 'meta_component_instance', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696172577076416512', '组件实例配置更新接口', '1', '/form/doUpdate', 'meta_component_instance', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696172706986594304', '组件实例配置删除接口', '1', '/table/delete', 'meta_component_instance', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696175296763793408', '菜单数据列表', '1', '/table/tree', 'meta_menu', b'1', b'0', 'auth', 'api:query:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696175533460951040', '新增菜单', '1', '/form/doAdd', 'meta_menu', b'1', b'0', 'auth', 'api:add:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696175709512667136', '更新菜单', '1', '/form/doUpdate', 'meta_menu', b'1', b'0', 'auth', 'api:update:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696175862860615680', '菜单删除', '1', '/table/delete', 'meta_menu', b'1', b'0', 'auth', 'api:delete:meta_menu', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696176715805888512', '路由列表', '1', '/table/tree', 'meta_router', b'1', b'0', 'auth', 'api:query:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696176920043327488', '更新路由', '1', '/form/doUpdate', 'meta_router', b'1', b'0', 'auth', 'api:update:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696177008371175424', '新增路由', '1', '/form/doAdd', 'meta_router', b'1', b'0', 'auth', 'api:add:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696177092982870016', '删除路由', '1', '/table/delete', 'meta_router', b'1', b'0', 'auth', 'api:delete:meta_router', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696177310180708352', '新增字典', '1', '/form/doAdd', 'meta_dict', b'1', b'0', 'auth', 'api:add:meta_dict', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696177407417257984', '更新字典', '1', '/form/doUpdate', 'meta_dict', b'1', b'0', 'auth', 'api:update:meta_dict', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696177519040270336', '删除字典', '1', '/table/delete', 'meta_dict', b'1', b'0', 'auth', 'api:delete:meta_dict', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696177879901409280', '异常数据删除', '1', '/table/delete', 'meta_exception', b'1', b'0', 'auth', 'api:delete:meta_exception', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178023577292800', '权限数据新增接口', '1', '/form/doAdd', 'meta_auth', b'1', b'0', 'auth', 'api:add:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178190523174912', '权限数据列表', '1', '/table/list', 'meta_auth', b'1', b'0', 'auth', 'api:query:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178301361852416', '权限数据更新接口', '1', '/form/doUpdate', 'meta_auth', b'1', b'0', 'auth', 'api:update:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178379577233408', '权限数据删除接口', '1', '/table/delete', 'meta_auth', b'1', b'0', 'auth', 'api:delete:meta_auth', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178536532283392', '接口资源新增接口', '1', '/form/doAdd', 'meta_api_resource', b'1', b'0', 'auth', 'api:add:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178653947629568', '接口资源列表', '1', '/table/list', 'meta_api_resource', b'1', b'0', 'auth', 'api:query:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178779864829952', '接口资源更新接口', '1', '/form/doUpdate', 'meta_api_resource', b'1', b'0', 'auth', 'api:update:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696178862790414336', '接口资源删除接口', '1', '/table/delete', 'meta_api_resource', b'1', b'0', 'auth', 'api:delete:api_meta_resource', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696339795437293568', '用户列表', '1', '/table/list', 'meta_user', b'1', b'0', 'auth', 'api:query:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696339990992523264', '用户新增接口', '1', '/form/doAdd', 'meta_user', b'1', b'0', 'auth', 'api:add:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696340082122166272', '用户更新接口', '1', '/form/doUpdate', 'meta_user', b'1', b'0', 'auth', 'api:update:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696340217535270912', '用户删除接口', '1', '/table/delete', 'meta_user', b'1', b'0', 'auth', 'api:delete:user', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696342334748626944', '角色新增接口', '1', '/form/doAdd', 'meta_role', b'1', b'0', 'auth', 'api:add:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696342436988981248', '角色删除接口', '1', '/table/delete', 'meta_role', b'1', b'0', 'auth', 'api:delete:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696342549929005056', '角色更新接口', '1', '/form/doUpdate', 'meta_role', b'1', b'0', 'auth', 'api:update:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696342647471738880', '角色列表', '1', '/table/list', 'meta_role', b'1', b'0', 'auth', 'api:query:role', 'any', '', 'any', '', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704005535661428736', '组件实例配置一键生成接口', '0', '/component/import-auto-computed', NULL, b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', '一键生成组件的实例配置', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704009277622325248', '元字段配置sql校验', '0', '/check/sql', NULL, b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', NULL, b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704011526276780032', '组件实例配置删除接口', '0', '/component/delete', NULL, b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', NULL, b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704013975712567296', '元对象更新', '0', '/meta/editObject', NULL, b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', 'ROOT用户快捷编辑元对象', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704014393469440000', '元字段编辑', '0', '/meta/editField', NULL, b'1', b'0', 'auth', NULL, 'any', NULL, 'any', 'ROOT用户快捷编辑元字段', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704017169255632896', '异常信息列表', '1', '/table/list', 'meta_exception', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', NULL, b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704017720508813312', '字典列表', '1', '/table/tree', 'meta_dict', b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', NULL, b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704019308350672896', '清空元数据表接口', '0', '/db/truncate', NULL, b'1', b'0', 'auth', 'api:meta_data:maintain', 'any', NULL, 'any', '只删除内建元数据', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704020269446074368', '查询拥有的角色', '0', '/user/roles', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '获取当前登录用户拥有的角色', b'1');
INSERT INTO `meta_api_resource` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '704020454637178880', '获取权限接口', '0', '/user/auths', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '获取当前登录用户拥有的权限列表', b'1');
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
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694148810477277184', 'menu:meta:meta-data', '菜单:元数据管理', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149240007561216', 'menu:meta:feature', '菜单:功能维护', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149341149007872', 'menu:meta:component', '菜单:组件全局配置', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149424439496704', 'menu:meta:component-instance', '菜单:组件实例配置', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149476058796032', 'menu:meta:menu', '菜单:菜单维护', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149530551193600', 'menu:meta:router', '菜单:路由维护', '平台维护', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149774206701568', 'menu:sys:dict', '菜单:字典', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149834445295616', 'menu:sys:exception', '菜单:异常', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149907166138368', 'menu:sys:auth', '菜单:权限配置', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694149960505102336', 'menu:sys:api-resource', '菜单:接口资源', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694150160565014528', 'menu:sys:user', '菜单:用户管理', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694183855120322560', 'menu:sys:role', '菜单:角色管理', '系统管理', 'menu', NULL, b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694280287013703680', 'api:app-init', '系统重置', '平台维护', 'api', '拥有此权限则可调用系统重置接口，完成系统重置', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694330954252161024', 'menu:meta:platform-ops', '菜单:平台维护', '平台维护', 'menu', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694335625574354944', 'route:meta:instance-conf-edit', '组件实例配置编辑', '平台维护', 'router', '编辑组件UI实例配置', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694336083923701760', 'route:meta:manager', '路由:元数据管理', '平台维护', 'router', '维护元对象和元字段', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694343798075035648', 'route:meta:feature', '路由:功能维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694343885119426560', 'route:meta:global-conf-list', '路由:组件全局配置', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344082931191808', 'route:meta:global-conf-edit', '路由:组件全局配置-编辑', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344223582982144', 'route:meta:instance-conf-list', '路由:组件实例配置', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344336845967360', 'route:meta:router', '路由:路由维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344409273208832', 'route:meta:menu', '路由:菜单维护', '平台维护', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344675334688768', 'route:meta:dict', '路由:字典维护', '系统管理', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344780561387520', 'route:meta:exception', '路由:系统异常', '系统管理', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344875012919296', 'route:meta:auth', '路由:权限配置', '系统管理', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '694344969531559936', 'route:meta:api-resource', '路由:接口资源', '系统管理', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696152559060127744', 'api:query:meta_auth', '权限列表', '系统管理', 'api', '查看权限编码列表数据', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696153360272855040', 'api:delete:meta_auth', '删除权限', '系统管理', 'api', '拥有权限数据的删除权限', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696153730193690624', 'api:add:meta_auth', '权限新增', '系统管理', 'api', '拥有新增权限的权限', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696153853300707328', 'api:update:meta_auth', '权限更新', '系统管理', 'api', '拥有更新权限的权限', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696154203961298944', 'api:add:api_meta_resource', '新增接口资源', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696154346861236224', 'api:delete:api_meta_resource', '删除接口资源', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696154466335985664', 'api:update:api_meta_resource', '更新接口资源', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696154568492453888', 'api:query:api_meta_resource', '查询接口资源', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696154835401183232', 'api:query:meta_exception', '查看异常', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696154907899727872', 'api:delete:meta_exception', '删除异常', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155030516011008', 'api:add:meta_dict', '新增字典', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155122442571776', 'api:delete:meta_dict', '删除字典', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155278286131200', 'api:update:meta_dict', '更新字典', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155376374124544', 'api:query:meta_dict', '查询字典', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155502178078720', 'api:add:meta_router', '新增路由', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155564840980480', 'api:delete:meta_router', '删除路由', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155654074798080', 'api:update:meta_router', '更新路由', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696155922124378112', 'api:add:meta_menu', '新增菜单', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696156011769237504', 'api:delete:meta_menu', '删除菜单', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696156079964426240', 'api:update:meta_menu', '更新菜单', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696156165645668352', 'api:query:meta_menu', '查询菜单', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696159659219881984', 'api:add:user', '新增用户', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696159713649364992', 'api:delete:user', '删除用户', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696159771941801984', 'api:update:user', '更新用户', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696159836404060160', 'api:query:user', '查询所有用户', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696159898110660608', 'api:add:role', '新增角色', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696159978653880320', 'api:delete:role', '删除角色', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696160054025523200', 'api:update:role', '更新角色', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696160111751729152', 'api:query:role', '查询角色', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696163222130135040', 'api:meta_data:maintain', '元数据维护', '平台维护', 'api', '拥有此权限则可调用维护元数据相关的所有接口。元数据包括:元对象、元字段、组件配置、组件实例配置、功能。相关接口囊括对元数据的增删改查和配置。', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696176274166648832', 'api:query:meta_router', '查询路由', '平台维护', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696886596984770560', 'route:user:list', '路由:用户管理', '系统管理', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '696886708112855040', 'route:role:list', '路由:角色管理', '系统管理', 'router', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '697160516417753088', 'api:bind:roles-to-user', '为指定用户绑定角色', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '697160611427127296', 'api:get:auths-of-role', '获取指定角色拥有的权限', '系统管理', 'api', '', b'1');
INSERT INTO `meta_auth` VALUES ('2022-03-24 03:01:36', NULL, 'SYSTEM', 'SYSTEM', '697160698198888448', 'api:bind:auths-to-role', '为指定角色绑定权限', '系统管理', 'api', '', b'1');
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
                                  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建数据',
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
                                           `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建数据',
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
                             `p_value` varchar(128) DEFAULT NULL COMMENT '父节点字典值',
                             `type` varchar(64) DEFAULT NULL COMMENT '字典类别',
                             `key` varchar(128) NOT NULL COMMENT '键',
                             `value` varchar(128) NOT NULL COMMENT '值',
                             `order_num` varchar(32) DEFAULT NULL COMMENT '排序',
                             `created_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                             `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                             `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `uq_key` (`p_value`,`value`) USING BTREE COMMENT '同一个字典项下的的值保持唯一'
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
                                  `exp_title` varchar(255) DEFAULT NULL COMMENT '异常名',
                                  `exp_chain` text COMMENT '异常堆栈',
                                  `exp_msg` text COMMENT '异常消息',
                                  `ext_url` varchar(1024) DEFAULT NULL COMMENT '请求url',
                                  `req_data` text COMMENT '请求内容',
                                  `res_data` longtext COMMENT '响应内容',
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
                                `type` varchar(32) NOT NULL COMMENT '功能类型',
                                `name` varchar(32) NOT NULL COMMENT '功能名',
                                `code` varchar(32) NOT NULL COMMENT '功能编码',
                                `config` json NOT NULL COMMENT '配置',
                                `op_config` json DEFAULT NULL COMMENT '操作配置',
                                `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                                `created_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                                `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建功能',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `uq_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能配置 ';

-- ----------------------------
-- Records of meta_feature
-- ----------------------------
BEGIN;
INSERT INTO `meta_feature` VALUES ('704326178714030080', 'MasterSlaveGrid', '元数据管理', 'meta_data', '{\"master\": {\"config\": {\"objectCode\": \"meta_object\", \"primaryKey\": \"code\"}, \"instanceCodes\": {\"FormView\": \"meta_object.FormView\", \"TableView\": \"meta_object.TableView\", \"SearchView\": \"meta_object.SearchView\"}}, \"slaves\": [{\"config\": {\"order\": 0, \"objectCode\": \"meta_field\", \"foreignPrimaryKey\": \"object_code\"}, \"instanceCodes\": {\"FormView\": \"meta_field.FormView\", \"TableView\": \"meta_field.TableView\", \"SearchView\": \"meta_field.SearchView\"}}]}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326178768556032', 'SingleGrid', '功能维护', 'meta_feature', '{\"config\": {\"objectCode\": \"meta_feature\"}, \"instanceCodes\": {\"FormView\": \"meta_feature.FormView\", \"TableView\": \"meta_feature.TableView\", \"SearchView\": \"meta_feature.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326178827276288', 'SingleGrid', '组件默认配置管理', 'meta_component', '{\"config\": {\"objectCode\": \"meta_component\"}, \"instanceCodes\": {\"FormView\": \"meta_component.FormView\", \"TableView\": \"meta_component.TableView\", \"SearchView\": \"meta_component.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326178873413632', 'SingleGrid', '组件实例配置管理', 'meta_component_instance', '{\"config\": {\"objectCode\": \"meta_component_instance\"}, \"instanceCodes\": {\"FormView\": \"meta_component_instance.FormView\", \"TableView\": \"meta_component_instance.TableView\", \"SearchView\": \"meta_component_instance.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326178915356672', 'TreeSingleGrid', '菜单维护', 'meta_menu', '{\"config\": {\"idKey\": \"id\", \"label\": \"title\", \"isSync\": false, \"pidKey\": \"pid\", \"objectCode\": \"meta_menu\", \"rootIdentify\": \"\"}, \"instanceCodes\": {\"FormView\": \"meta_menu.FormView\", \"SearchView\": \"meta_menu.SearchView\", \"TableTreeView\": \"meta_menu.TableTreeView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326178965688320', 'TreeSingleGrid', '路由维护', 'meta_router', '{\"config\": {\"idKey\": \"id\", \"label\": \"cn\", \"isSync\": false, \"pidKey\": \"pid\", \"objectCode\": \"meta_router\", \"rootIdentify\": \"\"}, \"instanceCodes\": {\"FormView\": \"meta_router.FormView\", \"SearchView\": \"meta_router.SearchView\", \"TableTreeView\": \"meta_router.TableTreeView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326179011825664', 'TreeSingleGrid', '字典管理', 'meta_dict', '{\"config\": {\"idKey\": \"value\", \"label\": \"key\", \"isSync\": false, \"pidKey\": \"p_value\", \"objectCode\": \"meta_dict\", \"rootIdentify\": \"\"}, \"instanceCodes\": {\"FormView\": \"meta_dict.FormView\", \"SearchView\": \"meta_dict.SearchView\", \"TableTreeView\": \"meta_dict.TableTreeView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326179070545920', 'SingleGrid', '系统异常管理', 'meta_exception', '{\"config\": {\"objectCode\": \"meta_exception\"}, \"instanceCodes\": {\"FormView\": \"meta_exception.FormView\", \"TableView\": \"meta_exception.TableView\", \"SearchView\": \"meta_exception.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326179137654784', 'SingleGrid', '权限管理', 'meta_auth', '{\"config\": {\"objectCode\": \"meta_auth\"}, \"instanceCodes\": {\"FormView\": \"meta_auth.FormView\", \"TableView\": \"meta_auth.TableView\", \"SearchView\": \"meta_auth.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326179200569344', 'SingleGrid', '接口资源管理', 'meta_api_resource', '{\"config\": {\"objectCode\": \"meta_api_resource\"}, \"instanceCodes\": {\"FormView\": \"meta_api_resource.FormView\", \"TableView\": \"meta_api_resource.TableView\", \"SearchView\": \"meta_api_resource.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326179267678208', 'SingleGrid', '用户管理', 'meta_user', '{\"config\": {\"objectCode\": \"meta_user\"}, \"instanceCodes\": {\"FormView\": \"meta_user.FormView\", \"TableView\": \"meta_user.TableView\", \"SearchView\": \"meta_user.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
INSERT INTO `meta_feature` VALUES ('704326179330592768', 'SingleGrid', '角色管理', 'meta_role', '{\"config\": {\"objectCode\": \"meta_role\"}, \"instanceCodes\": {\"FormView\": \"meta_role.FormView\", \"TableView\": \"meta_role.TableView\", \"SearchView\": \"meta_role.SearchView\"}}', NULL, NULL, NULL, NULL, NULL, NULL, b'1');
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
                              `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建数据',
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
INSERT INTO `meta_menu` VALUES ('696347057069363200', '', '首页', b'0', b'0', 'dashboard', '/dashboard', 0, '', b'0', 'auth', '', 'any', '', 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_menu` VALUES ('702190979259699200', '7bcfbe31357f48bf8c88072a18208599', '字典管理', b'0', b'0', 'dict', '/dict', 3, NULL, b'1', 'auth', 'menu:meta:dict', 'any', NULL, 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_menu` VALUES ('702191170834534400', '7bcfbe31357f48bf8c88072a18208599', '异常信息', b'0', b'0', 'el-icon-warning', '/exception', 99, NULL, b'1', 'auth', 'menu:meta:exception', 'any', NULL, 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_menu` VALUES ('702191337100939264', '7bcfbe31357f48bf8c88072a18208599', '权限配置', b'0', b'0', 'auth', '/auth', 4, NULL, b'1', 'auth', 'menu:meta:auth', 'any', NULL, 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_menu` VALUES ('7bcfbe31357f48bf8c88072a18208599', '', '系统管理', b'0', b'0', 'system-manager', '/user', 3, '', b'1', 'auth', 'menu:meta:user', 'any', '', 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_menu` VALUES ('96eab66157be4165b9916d6f8b079dd9', '7bcfbe31357f48bf8c88072a18208599', '角色管理', b'0', b'0', 'role', '/role', 1, '', b'1', 'auth', 'menu:meta:role', 'any', '', 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_menu` VALUES ('f49ce02324d94fa698a0db718e380074', '7bcfbe31357f48bf8c88072a18208599', '用户管理', b'0', b'0', 'user_manage', '/user', 0, '', b'1', 'auth', 'menu:meta:user', 'any', '', 'any', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
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
                               `config` json DEFAULT NULL COMMENT '配置',
                               `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                               `created_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                               `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                               `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '内建数据',
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
INSERT INTO `meta_router` VALUES ('2b17f805d48548ef89a574bd80a0dd3a', '698272484012724224', 'UserList', '用户列表', '/user', '', 'UserList', '{}', b'0', b'0', 0, '{}', b'1', 'auth', NULL, 'any', NULL, 'any', '', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('696345295902085120', '698272484012724224', 'Dashboard', '首页', '/dashboard', '', 'Dashboard', NULL, b'0', b'0', -99999, '{}', b'1', 'auth', NULL, 'any', NULL, 'any', '', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('698272484012724224', NULL, 'AdminLayout', '布局组件', '/', '/dashboard', 'MetaLayout', NULL, b'1', b'0', 0, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('698273043436408832', NULL, 'Login', '登录', '/login', NULL, 'Login', NULL, b'0', b'0', 1, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('698273460404752384', NULL, 'Page401', '401', '/401', NULL, 'Page401', NULL, b'0', b'0', 100, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('698273579418128384', NULL, 'Page404', '404', '*', NULL, 'Page404', NULL, b'0', b'0', 999999, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('702189989265543168', '698272484012724224', 'DictList', '字典维护', '/dict', NULL, 'DictList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta:dict', 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('702190236326825984', '698272484012724224', 'ExceptionList', '系统异常', '/exception', NULL, 'ExceptionList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta:exception', 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('702190403968962560', '698272484012724224', 'AuthList', '权限配置', '/auth', NULL, 'AuthList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta:auth', 'any', NULL, 'any', NULL, b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
INSERT INTO `meta_router` VALUES ('e5a688069fcf43c8ab98af55c105890e', '698272484012724224', 'RoleList', '角色列表', '/role', '', 'RoleList', '{}', b'0', b'0', 0, '{}', b'1', 'auth', NULL, 'any', NULL, 'any', '', b'1', '2022-03-24 03:01:37', 'SYSTEM', 'SYSTEM', NULL);
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
