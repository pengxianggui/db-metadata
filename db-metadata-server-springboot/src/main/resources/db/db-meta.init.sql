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
-- Records of meta_api_resource
-- ----------------------------
BEGIN;
INSERT INTO `meta_api_resource` VALUES ('694223070646374400', '路由数据', '0', '/router', '', b'0', b'0', 'auth', '', 'any', '', 'any', '路由接口, 属于系统数据, 需要不登录也能正常获取', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694225274480496640', '菜单数据', '0', '/menu', '', b'1', b'1', 'auth', '', 'any', '', 'any', '菜单数据，登录后才能进入管理界面;登录即可访问', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694227116228743168', '系统属性', '0', '/app/config', '', b'0', b'0', 'auth', '', 'any', '', 'any', '系统配置数据, 无需鉴权', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694231657120665600', '登录接口', '0', '/user/login', '', b'0', b'0', 'auth', '', 'any', '', 'any', '用户登录接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694231832820060160', '当前登录用户信息接口', '0', '/user/info', '', b'1', b'1', 'auth', '', 'any', '', 'any', '当前用户获取其登录信息的接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694231925002473472', '登出接口', '0', '/user/logout', '', b'0', b'0', 'auth', '', 'any', '', 'any', '登出接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694232534690697216', '数据库列表接口', '0', '/db/index', NULL, b'1', b'0', 'auth', 'add:meta_object', 'any', NULL, 'any', '获取数据库列表。等同/db/list', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694232700655112192', '关系表列表接口', '0', '/db/tables', NULL, b'1', b'0', 'role', 'add:meta_object', 'any', NULL, 'any', '获取所有表', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694233825454198784', '系统初始化接口', '0', '/db/init', NULL, b'1', b'0', 'auth', 'api:app-init', 'any', NULL, 'any', '系统初始化接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694238218186526720', '字典数据接口', '0', '/dict', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '系统字典', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694238636744511488', '文件上传接口', '0', '/file/upload', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694238822514429952', '富文本文件上传', '0', '/file/upload/richText', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696167536378646528', '元对象列表', '1', '/table/list', 'meta_object', b'1', b'0', 'auth', 'query:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696167915807969280', '新增元对象接口', '0', '/meta/doAdd', NULL, b'1', b'0', 'auth', 'add:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696168277629603840', '元对象更新', '1', '/form/doUpdate', 'meta_object', b'1', b'0', 'auth', 'update:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696168807957401600', '元对象删除', '0', '/meta/delete', NULL, b'1', b'0', 'auth', 'delete:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696169111574679552', '元字段同步', '0', '/meta/incrementImport', NULL, b'1', b'0', 'auth', 'sync:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696169336997548032', '元字段更新', '1', '/form/doUpdate', 'meta_field', b'1', b'0', 'auth', 'update:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696169751176679424', '元字段删除', '1', '/table/delete', 'meta_field', b'1', b'0', 'auth', 'delete:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696170147102199808', '元字段列表', '1', '/table/list', 'meta_field', b'1', b'0', 'auth', 'query:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696170439403245568', '功能配置列表', '1', '/table/list', 'meta_feature', b'1', b'0', 'auth', 'query:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696170795436740608', '功能新增', '0', '/feature/doAdd', NULL, b'1', b'0', 'auth', 'add:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171090757685248', '功能更新', '1', '/form/doUpdate', 'meta_feature', b'1', b'0', 'auth', 'update:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171222936981504', '功能删除', '1', '/table/delete', 'meta_feature', b'1', b'0', 'auth', 'delete:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171563858399232', '组件默认配置更新', '1', '/form/doUpdate', 'meta_component', b'1', b'0', 'auth', 'update:meta_component', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171773636513792', '组件默认配置删除', '1', '/table/delete', 'meta_component', b'1', b'0', 'auth', 'delete:meta_component', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171997700427776', '组件默认配置/组件实例配置更新接口', '0', '/component/doUpdate', NULL, b'1', b'0', 'auth', 'update:meta_component,update:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696172202269216768', '组件实例配置列表', '1', '/table/list', 'meta_component_instance', b'1', b'0', 'auth', 'query:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696172577076416512', '组件实例配置更新接口', '1', '/form/doUpdate', 'meta_component_instance', b'1', b'0', 'auth', 'update:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696172706986594304', '组件实例配置删除接口', '1', '/table/delete', 'meta_component_instance', b'1', b'0', 'auth', 'delete:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175296763793408', '菜单数据列表', '1', '/table/tree', 'meta_menu', b'1', b'0', 'auth', 'query:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175533460951040', '新增菜单', '1', '/form/doAdd', 'meta_menu', b'1', b'0', 'auth', 'add:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175709512667136', '更新菜单', '1', '/form/doUpdate', 'meta_menu', b'1', b'0', 'auth', 'update:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175862860615680', '菜单删除', '1', '/table/delete', 'meta_menu', b'1', b'0', 'auth', 'delete:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696176715805888512', '路由列表', '1', '/table/tree', 'meta_router', b'1', b'0', 'auth', 'query_meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696176920043327488', '更新路由', '1', '/form/doUpdate', 'meta_router', b'1', b'0', 'auth', 'update:meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177008371175424', '新增路由', '1', '/form/doAdd', 'meta_router', b'1', b'0', 'auth', 'add:meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177092982870016', '删除路由', '1', '/table/delete', 'meta_router', b'1', b'0', 'auth', 'delete:meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177310180708352', '新增字典', '1', '/form/doAdd', 'meta_dict', b'1', b'0', 'auth', 'add:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177407417257984', '更新字典', '1', '/form/doUpdate', 'meta_dict', b'1', b'0', 'auth', 'update:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177519040270336', '删除字典', '1', '/table/delete', 'meta_dict', b'1', b'0', 'auth', 'delete:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177879901409280', '异常数据删除', '1', '/table/delete', 'meta_exception', b'1', b'0', 'auth', 'delete:meta_exception', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178023577292800', '权限数据新增接口', '1', '/form/doAdd', 'meta_auth', b'1', b'0', 'auth', 'add:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178190523174912', '权限数据列表', '1', '/table/list', 'meta_auth', b'1', b'0', 'auth', 'query:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178301361852416', '权限数据更新接口', '1', '/form/doUpdate', 'meta_auth', b'1', b'0', 'auth', 'update:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178379577233408', '权限数据删除接口', '1', '/table/delete', 'meta_auth', b'1', b'0', 'auth', 'delete:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178536532283392', '接口资源新增接口', '1', '/form/doAdd', 'meta_api_resource', b'1', b'0', 'auth', 'add:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178653947629568', '接口资源列表', '1', '/table/list', 'meta_api_resource', b'1', b'0', 'auth', 'query:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178779864829952', '接口资源更新接口', '1', '/form/doUpdate', 'meta_api_resource', b'1', b'0', 'auth', 'update:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178862790414336', '接口资源删除接口', '1', '/table/delete', 'meta_api_resource', b'1', b'0', 'auth', 'delete:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696339795437293568', '用户列表', '1', '/table/list', 'meta_user', b'1', b'0', 'auth', 'query:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696339990992523264', '用户新增接口', '1', '/form/doAdd', 'meta_user', b'1', b'0', 'auth', 'add:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696340082122166272', '用户更新接口', '1', '/form/doUpdate', 'meta_user', b'1', b'0', 'auth', 'update:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696340217535270912', '用户删除接口', '1', '/table/delete', 'meta_user', b'1', b'0', 'auth', 'delete:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342334748626944', '角色新增接口', '1', '/form/doAdd', 'meta_role', b'1', b'0', 'auth', 'add:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342436988981248', '角色删除接口', '1', '/table/delete', 'meta_role', b'1', b'0', 'auth', 'delete:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342549929005056', '角色更新接口', '1', '/form/doUpdate', 'meta_role', b'1', b'0', 'auth', 'update:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342647471738880', '角色列表', '1', '/table/list', 'meta_role', b'1', b'0', 'auth', 'query:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704005535661428736', '组件实例配置一键生成接口', '0', '/component/import-auto-computed', NULL, b'1', b'0', 'auth', 'add:meta_component_instance', 'any', NULL, 'any', '一键生成组件的实例配置', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704009277622325248', '元字段配置sql校验', '0', '/check/sql', NULL, b'1', b'0', 'auth', 'update:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704011526276780032', '组件实例配置删除接口', '0', '/component/delete', NULL, b'1', b'0', 'auth', 'delete:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704013975712567296', '元对象更新', '0', '/meta/editObject', NULL, b'1', b'0', 'auth', 'update:meta_object', 'any', NULL, 'any', 'ROOT用户快捷编辑元对象', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704014393469440000', '元字段更新', '0', '/meta/editField', NULL, b'1', b'0', 'auth', 'update:meta_field', 'any', NULL, 'any', 'ROOT用户快捷编辑元字段', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704017169255632896', '异常信息列表', '1', '/table/list', 'meta_exception', b'1', b'0', 'auth', 'query:meta_exception', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704017720508813312', '字典列表', '1', '/table/tree', 'meta_dict', b'1', b'0', 'auth', 'query:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704020269446074368', '查询当前用户拥有的角色', '0', '/user/roles', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '获取当前登录用户拥有的角色', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704020454637178880', '获取当前用户拥有的权限接口', '0', '/user/auths', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '获取当前登录用户拥有的权限列表', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('711632186226380800', '选项数据', '0', '/component/options', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;

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
-- Records of meta_auth
-- ----------------------------
BEGIN;
INSERT INTO `meta_auth` VALUES ('694149240007561216', 'menu:meta_feature', '菜单:功能维护', '711604182171389952', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149341149007872', 'menu:meta_component', '菜单:组件全局配置', '711604231555125248', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149424439496704', 'menu:meta_component_instance', '菜单:组件实例配置', '711604268704075776', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149476058796032', 'menu:meta_menu', '菜单:菜单维护', '711604305995632640', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149530551193600', 'menu:meta_router', '菜单:路由维护', '711604336572108800', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149774206701568', 'menu:meta_dict', '菜单:字典管理', '711603985097822208', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149834445295616', 'menu:meta_exception', '菜单:异常信息', '711604059425083392', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149907166138368', 'menu:meta_auth', '菜单:权限配置', '711604024738189312', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149960505102336', 'menu:meta_api_resource', '菜单:接口资源', '711604368734031872', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694150160565014528', 'menu:meta_user', '菜单:用户管理', '711603857981050880', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694183855120322560', 'menu:meta_role', '菜单:角色管理', '711603907276705792', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694280287013703680', 'api:app-init', '系统重置', NULL, 'api,button', '拥有此权限则可调用系统重置接口，完成系统重置', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694330954252161024', 'menu:platform-ops', '菜单:平台维护', '711603760899690496', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694335625574354944', 'route:meta_component_instance:edit', '页面:组件实例配置编辑', '711604268704075776', 'router', '编辑组件UI实例配置', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694343798075035648', 'route:meta_feature', '页面:功能维护', '711604182171389952', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694343885119426560', 'route:meta_component', '页面:组件全局配置', '711604231555125248', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344082931191808', 'route:meta_component:edit', '页面:组件全局配置-编辑', '711604231555125248', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344336845967360', 'route:meta_router', '页面:路由维护', '711604336572108800', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344409273208832', 'route:meta_menu', '页面:菜单维护', '711604305995632640', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344675334688768', 'route:meta_dict', '页面:字典管理', '711603985097822208', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344780561387520', 'route:meta_exception', '页面:异常信息', '711604059425083392', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344875012919296', 'route:meta_auth', '页面:权限配置', '711604024738189312', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344969531559936', 'route:meta_api_resource', '页面:接口资源', '711604368734031872', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696152559060127744', 'query:meta_auth', '权限列表', '711604024738189312', 'api', '查看权限编码列表数据', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696153360272855040', 'delete:meta_auth', '删除权限', '711604024738189312', 'api,button', '拥有权限数据的删除权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696153730193690624', 'add:meta_auth', '权限新增', '711604024738189312', 'api,button', '拥有新增权限的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696153853300707328', 'update:meta_auth', '权限更新', '711604024738189312', 'api,button', '拥有更新权限的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154203961298944', 'add:meta_api_resource', '新增接口资源', '711604368734031872', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154346861236224', 'delete:meta_api_resource', '删除接口资源', '711604368734031872', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154466335985664', 'update:meta_api_resource', '更新接口资源', '711604368734031872', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154568492453888', 'query:meta_api_resource', '查询接口资源', '711604368734031872', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154835401183232', 'query:meta_exception', '异常数据列表', '711604059425083392', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154907899727872', 'delete:meta_exception', '异常数据删除', '711604059425083392', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155030516011008', 'add:meta_dict', '新增字典', '711603985097822208', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155122442571776', 'delete:meta_dict', '删除字典', '711603985097822208', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155278286131200', 'update:meta_dict', '更新字典', '711603985097822208', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155376374124544', 'query:meta_dict', '查询字典', '711603985097822208', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155502178078720', 'add:meta_router', '新增路由', '711604336572108800', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155564840980480', 'delete:meta_router', '删除路由', '711604336572108800', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155654074798080', 'update:meta_router', '更新路由', '711604336572108800', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155922124378112', 'add:meta_menu', '新增菜单', '711604305995632640', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696156011769237504', 'delete:meta_menu', '删除菜单', '711604305995632640', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696156079964426240', 'update:meta_menu', '更新菜单', '711604305995632640', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696156165645668352', 'query:meta_menu', '查询菜单', '711604305995632640', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159659219881984', 'add:meta_user', '新增用户', '711603857981050880', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159713649364992', 'delete:meta_user', '删除用户', '711603857981050880', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159771941801984', 'update:meta_user', '更新用户', '711603857981050880', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159836404060160', 'query:meta_user', '查询用户', '711603857981050880', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159898110660608', 'add:meta_role', '新增角色', '711603907276705792', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159978653880320', 'delete:meta_role', '删除角色', '711603907276705792', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696160054025523200', 'update:meta_role', '更新角色', '711603907276705792', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696160111751729152', 'query:meta_role', '查询角色', '711603907276705792', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696176274166648832', 'query_meta_router', '查询路由', '711604336572108800', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696886596984770560', 'route:meta_user', '页面:用户管理', '711603857981050880', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696886708112855040', 'route:meta_role', '页面:角色管理', '711603907276705792', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('697160516417753088', 'bind:roles:to-user', '为指定用户绑定角色', '711603857981050880', 'api,button', '为用户绑定角色', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('697160698198888448', 'bind:auths-to-role', '为指定角色绑定权限', '711603907276705792', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711618459557040128', 'menu:sys', '菜单:系统管理', '711603723775905792', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711621009249275904', 'add:meta_object', '创建元对象', '711604134998052864', 'api,button', '拥有元对象创建权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711621133711052800', 'route:meta_data', '页面:元数据管理', '711604134998052864', 'router', '拥有进入元数据管理页面的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711621340548960256', 'menu:meta_data', '菜单:元数据管理', '711604134998052864', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711623619599536128', 'update:meta_object', '更新元对象', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711623737232986112', 'delete:meta_object', '删除元对象', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624070503993344', 'sync:meta_field', '同步增量字段', '711604134998052864', 'button,api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624225265422336', 'delete:meta_field', '删除元字段', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624303988314112', 'update:meta_field', '更新元字段', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624635795509248', 'add:meta_feature', '创建功能', '711604182171389952', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624738526597120', 'update:meta_feature', '更新功能', '711604182171389952', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624886224818176', 'delete:meta_feature', '删除功能', '711604182171389952', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711625652272500736', 'update:meta_component', '更新默认组件配置', '711604231555125248', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711625820128546816', 'delete:meta_component', '删除默认组件配置', '711604231555125248', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626372585492480', 'route:meta_component_instance', '页面:组件实例配置', '711604268704075776', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626548469436416', 'add:meta_component_instance', '新增组件实例配置', '711604268704075776', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626638537920512', 'update:meta_component_instance', '更新组件实例配置', '711604268704075776', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626755575779328', 'delete:meta_component_instance', '删除组件实例配置', '711604268704075776', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627077702520832', 'query:meta_component_instance', '查询组件实例配置', '711604268704075776', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627199916150784', 'query:meta_component', '查询组件默认配置', '711604231555125248', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627319839690752', 'query:meta_feature', '查询功能', '711604182171389952', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627452220313600', 'query:meta_object', '查询元对象', '711604134998052864', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627513272602624', 'query:meta_field', '查询元字段', '711604134998052864', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;


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
-- Records of meta_auth_module
-- ----------------------------
BEGIN;
INSERT INTO `meta_auth_module` VALUES ('711603723775905792', '系统管理', NULL, 99, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603760899690496', '平台维护', NULL, 100, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603857981050880', '用户管理', '711603723775905792', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603907276705792', '角色管理', '711603723775905792', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603985097822208', '字典管理', '711603723775905792', 2, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604024738189312', '权限配置', '711603723775905792', 3, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604059425083392', '异常信息', '711603723775905792', 4, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604134998052864', '元数据管理', '711603760899690496', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604182171389952', '功能维护', '711603760899690496', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604231555125248', '组件全局配置', '711603760899690496', 2, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604268704075776', '组件实例配置', '711603760899690496', 3, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604305995632640', '菜单维护', '711603760899690496', 4, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604336572108800', '路由维护', '711603760899690496', 5, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604368734031872', '接口资源', '711603760899690496', 6, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604523105390592', '首页', NULL, 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
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
                                   `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                   `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                   `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                   `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
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
                                  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                  `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                  `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
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
                                           `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                           `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                                           `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                           `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                           `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
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
                               `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                               `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                               `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
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
-- Records of meta_dict
-- ----------------------------
BEGIN;
INSERT INTO `meta_dict` VALUES ('711536798504980480', NULL, '0', '权限类型', 'authType', '0', '权限类型字典项', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711537043011932160', 'authType', '0', '菜单权限', 'menu', '0', '声明权限应用于菜单', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711541084135755776', 'authType', '0', '页面权限', 'router', '1', '表明权限应用于页面，描述访问页面需要的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711541340051214336', 'authType', '0', '接口权限', 'api', '2', '表示权限类别为接口，应用于接口访问控制', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711541699972829184', 'authType', NULL, '按钮权限', 'button', '3', '表示权限类型为按钮类型，应用于页面类似于按钮等元素的显示/隐藏', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711739800452993024', NULL, '0', '角色类别', 'roleType', '1', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711740014207307776', 'roleType', '0', '内建角色', 'buildIn', '0', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
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
                                  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
                                  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                  `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                  `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
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
                             `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                             `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                             `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of meta_menu
-- ----------------------------
BEGIN;
INSERT INTO `meta_menu` VALUES ('696347057069363200', '', '首页', b'0', b'0', 'dashboard', '/dashboard', 0, '', b'0', 'auth', '', 'any', '', 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('702190979259699200', '7bcfbe31357f48bf8c88072a18208599', '字典管理', b'0', b'0', 'dict', '/dict', 3, NULL, b'1', 'auth', 'menu:meta_dict', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('702191170834534400', '7bcfbe31357f48bf8c88072a18208599', '异常信息', b'0', b'0', 'el-icon-warning', '/exception', 99, NULL, b'1', 'auth', 'menu:meta_exception', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('702191337100939264', '7bcfbe31357f48bf8c88072a18208599', '权限配置', b'0', b'0', 'auth', '/auth', 4, NULL, b'1', 'auth', 'menu:meta_auth', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('7bcfbe31357f48bf8c88072a18208599', NULL, '系统管理', b'0', b'0', 'system-manager', '/user', 3, NULL, b'1', 'auth', 'menu:sys', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('96eab66157be4165b9916d6f8b079dd9', '7bcfbe31357f48bf8c88072a18208599', '角色管理', b'0', b'0', 'role', '/role', 1, NULL, b'1', 'auth', 'menu:meta_role', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('f49ce02324d94fa698a0db718e380074', '7bcfbe31357f48bf8c88072a18208599', '用户管理', b'0', b'0', 'user_manage', '/user', 0, NULL, b'1', 'auth', 'menu:meta_user', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
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
-- Records of meta_object
-- ----------------------------
BEGIN;
COMMIT;

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
-- Records of meta_role
-- ----------------------------
BEGIN;
INSERT INTO `meta_role` VALUES ('711640534434844672', 'ADMIN', '超级管理员', '拥有除【平台维护】外的所有权限', 'buildIn', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role` VALUES ('711660585720352768', 'DEVELOPER', '开发者', '开发者角色拥有平台维护权限', 'buildIn', b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meta_role_auth_rela
-- ----------------------------
DROP TABLE IF EXISTS `meta_role_auth_rela`;
CREATE TABLE `meta_role_auth_rela` (
                                       `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
                                       `auth_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限id',
                                       `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                       `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                       `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                       PRIMARY KEY (`role_id`,`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of meta_role_auth_rela
-- ----------------------------
BEGIN;
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694149774206701568', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694149834445295616', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694149907166138368', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694150160565014528', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694183855120322560', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694344675334688768', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694344780561387520', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694344875012919296', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696152559060127744', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696153360272855040', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696153730193690624', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696153853300707328', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696154835401183232', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696154907899727872', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155030516011008', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155122442571776', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155278286131200', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155376374124544', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159659219881984', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159713649364992', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159771941801984', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159836404060160', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159898110660608', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159978653880320', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696160054025523200', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696160111751729152', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696886596984770560', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696886708112855040', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '697160516417753088', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '697160698198888448', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '711618459557040128', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149240007561216', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149341149007872', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149424439496704', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149476058796032', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149530551193600', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149960505102336', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694330954252161024', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694335625574354944', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694343798075035648', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694343885119426560', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344082931191808', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344336845967360', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344409273208832', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344969531559936', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154203961298944', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154346861236224', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154466335985664', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154568492453888', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155502178078720', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155564840980480', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155654074798080', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155922124378112', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696156011769237504', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696156079964426240', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696156165645668352', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696176274166648832', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711621009249275904', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711621133711052800', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711621340548960256', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711623619599536128', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711623737232986112', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624070503993344', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624225265422336', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624303988314112', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624635795509248', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624738526597120', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624886224818176', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711625652272500736', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711625820128546816', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626372585492480', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626548469436416', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626638537920512', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626755575779328', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627077702520832', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627199916150784', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627319839690752', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627452220313600', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627513272602624', NOW(), 'SYSTEM', NULL, NULL);
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
                               `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                               `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由信息';

-- ----------------------------
-- Records of meta_router
-- ----------------------------
BEGIN;
INSERT INTO `meta_router` VALUES ('2b17f805d48548ef89a574bd80a0dd3a', '698272484012724224', 'UserList', '用户列表', '/user', NULL, 'UserList', '{}', b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('696345295902085120', '698272484012724224', 'Dashboard', '首页', '/dashboard', '', 'Dashboard', NULL, b'0', b'0', -99999, '{}', b'1', 'auth', NULL, 'any', NULL, 'any', '', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698272484012724224', NULL, 'MetaLayout', '布局组件', '/', '/dashboard', 'MetaLayout', NULL, b'1', b'0', 0, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273043436408832', NULL, 'Login', '登录', '/login', NULL, 'Login', NULL, b'0', b'0', 1, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273460404752384', NULL, 'Page401', '401', '/401', NULL, 'Page401', NULL, b'0', b'0', 100, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273579418128384', NULL, 'Page404', '404', '*', NULL, 'Page404', NULL, b'0', b'0', 999999, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('702189989265543168', '698272484012724224', 'DictList', '字典维护', '/dict', NULL, 'DictList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('702190236326825984', '698272484012724224', 'ExceptionList', '系统异常', '/exception', NULL, 'ExceptionList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_exception', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('702190403968962560', '698272484012724224', 'AuthList', '权限配置', '/auth', NULL, 'AuthList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('e5a688069fcf43c8ab98af55c105890e', '698272484012724224', 'RoleList', '角色列表', '/role', NULL, 'RoleList', '{}', b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for meta_user
-- ----------------------------
DROP TABLE IF EXISTS `meta_user`;
CREATE TABLE `meta_user` (
                             `id` varchar(32) CHARACTER SET utf8 NOT NULL,
                             `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
                             `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
                             `avatar` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '头像',
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
                                       `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
                                       `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                       `updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
                                       PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of meta_user_role_rela
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
