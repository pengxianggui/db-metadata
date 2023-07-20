-- ----------------------------
-- Table structure for meta_app_config
-- ----------------------------
DROP TABLE IF EXISTS `meta_app_config`;
CREATE TABLE `meta_app_config`  (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `name` varchar(45) NOT NULL DEFAULT 'DB-Metadata低代码开发工具' COMMENT '系统名',
    `logo` varchar(255) NULL COMMENT '系统logo',
    `registerable` bit(1) NULL DEFAULT b'0' COMMENT '是否开放注册',
    `default_pass` varchar(20) NOT NULL DEFAULT '888888' COMMENT '默认(初始)密码',
    `pass_encrypt_key` varchar(500) NOT NULL DEFAULT 'DB-Metadata is delicious' COMMENT '用户密码加密密钥',
    `login_bg` varchar(255) NULL COMMENT '登录页背景图',
    `reset_pass` varchar(45) NOT NULL DEFAULT 'dbmeta' COMMENT '系统元数据重置口令',
    `show_greeting` bit(1) NULL DEFAULT b'1' COMMENT '显示问候语',
    `show_theme_setting` bit(1) NULL DEFAULT b'1' COMMENT '显示主题设置入口',
    `allow_custom_theme` bit(1) NULL DEFAULT b'0' COMMENT '是否允许自定义主题',
    `version` int(9) NOT NULL AUTO_INCREMENT COMMENT '版本号',
    `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
    `created_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `updated_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
    `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_version` (`version`) USING HASH
) COMMENT = '系统配置';

-- ----------------------------
-- Records of meta_router
-- ----------------------------
BEGIN;
INSERT INTO `meta_app_config` (`id`, `name`, `logo`, `registerable`, `default_pass`, `pass_encrypt_key`, `login_bg`, `reset_pass`, `show_greeting`, `show_theme_setting`, `allow_custom_theme`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('0', 'DB-Metadata低代码开发工具', '/img/meta.svg', b'0', '888888', 'DB-Metadata is delicious', '/img/default_bg.jpg', 'dbmeta', b'1', b'1', b'0', 1, NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
COMMIT;


-- ----------------------------
-- Table structure for meta_var
-- ----------------------------
DROP TABLE IF EXISTS `meta_var`;
CREATE TABLE `meta_var`  (
     `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
     `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变量名(唯一)',
     `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变量值',
     `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
     `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
     `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
     `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
     `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '变量(应用于代码片段等场景)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table record for meta_var
-- ----------------------------
INSERT INTO `meta_var` (`id`, `name`, `value`, `description`, `build_in`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('0', 'baseURL', '/api', 'API代理前缀。有时数据库会存储一些相对路径的资源地址，比如文件上传/下载为本地模式时，上传的图片保存的地址都是后端接口的相对路径，我们希望在表格单元显示出图片，就需要在这个路径前加上这个前缀，以便前端代理正确转发到后端。', b'1', NOW(), 'SYSTEM', NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();

-- ----------------------------
-- Table structure for meta_snippet
-- ----------------------------
DROP TABLE IF EXISTS `meta_snippet`;
CREATE TABLE `meta_snippet`  (
    `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '编码(唯一)',
    `title` varchar(45) NOT NULL COMMENT '标题',
    `description` text NULL COMMENT '描述',
    `content` text NOT NULL COMMENT '内容(js代码)',
    `build_in` bit(1) NOT NULL DEFAULT b'0' COMMENT '系统内置',
    `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `updated_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
    `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_code`(`code`)
) COMMENT = '代码片段';

-- ----------------------------
-- Table record for meta_snippet
-- ----------------------------
INSERT INTO `meta_snippet` (`id`, `code`, `title`, `description`, `content`, `build_in`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('0', 'STR_IMG_RENDER', '字符串值的图片渲染', '值为字符串url时, 用于渲染成图片展示，借助h函数动态渲染出el-image.需要注意的是：值必须是字符串，且为有效的图片路径，路径若非绝对地址，则视为相对路径，会拼接全局变量中的baseURL前缀。', '/**\n * @param h 		vue中的渲染函数:createElement\n * @param value 	原始值。例如表格场景下，此值为单元格原始值\n * @param record	整行记录值。例如表格场景下，此值为value所在行的记录\n * @param meta		字段的实例配置。例如表格场景下，此值为value所在列的实例配置。\n */\nfunction (h, value, record, meta) {\n    if (!this.isEmpty(value)) {\n        let url = this.isExternal(value) ? value : \'${baseURL}\' + value;\n        return h(\'el-image\', {\n            attrs: {\n                src: url,\n                \"preview-src-list\": [url]\n            },\n            style: {\n                width: \'30px\',\n                height: \'30px\'\n            }\n        }, null)\n    }\n    return h(\'span\', value)\n}', b'1', NOW(), 'SYSTEM', NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_snippet` (`id`, `code`, `title`, `description`, `content`, `build_in`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('1', 'JSONARRAY_IMG_RENDER', 'JSON数组图片渲染', '值为JSON数组时的图片渲染，借助h函数动态渲染el-image。需要注意的是，json数组的值，必须满足格式： [{\"url\":\"\"}, ...]。若url值非绝对地址，则视为后端接口地址，会拼接全局变量中的baseURL作为前缀。', '/**\n * @param h 		vue中的渲染函数:createElement\n * @param value 	原始值。例如表格场景下，此值为单元格原始值\n * @param record	整行记录值。例如表格场景下，此值为value所在行的记录\n * @param meta		字段的实例配置。例如表格场景下，此值为value所在列的实例配置。\n */\nfunction (h, value, record, meta) {\n    if (this.isEmpty(value)) {\n        return h(\'span\', value)\n    }\n    let arrVal;\n    try {\n        arrVal = JSON.parse(value)\n    } catch (err) {\n        return h(\'span\', value)\n    }\n\n    let images;\n    let hArr = [];\n    const urls = []\n    for (let i = 0; i < arrVal.length; i++) {\n        const {name, url} = arrVal[i]\n        if (this.isEmpty(url)) {\n            continue;\n        }\n\n        const finalUrl = this.isExternal(url) ? url : \'${baseURL}\' + url;\n        urls.push(finalUrl);\n\n        const dom = h(\'el-image\', {\n            attrs: {\n                src: finalUrl,\n                \"preview-src-list\": urls\n            },\n            style: {\n                width: \'30px\',\n                height: \'30px\',\n                \'margin-right\': \'5px\'\n            }\n        }, null)\n        hArr.push(dom)\n    }\n    \n    return h(\'div\', null, hArr)\n}', b'1', NOW(), 'SYSTEM', NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_snippet` (`id`, `code`, `title`, `description`, `content`, `build_in`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('2', 'STR_FILE_RENDER', '字符串文件渲染', '值为字符串形式的文件地址时，渲染为链接，点击可下载此文件。', '/**\n * @param h 		vue中的渲染函数:createElement\n * @param value 	原始值。例如表格场景下，此值为单元格原始值\n * @param record	整行记录值。例如表格场景下，此值为value所在行的记录\n * @param meta		字段的实例配置。例如表格场景下，此值为value所在列的实例配置。\n */\nfunction (h, value, record, meta) {\n    if (!this.isEmpty(value)) {\n        let url = this.isExternal(value) ? value : \'${baseURL}\' + value;\n        return h(\'el-link\', {\n            attrs: {\n                href: url,\n			  	type: \'primary\',\n			  	icon: \'el-icon-link\',\n			  	target: \'_blank\'\n            }\n        }, \'点击下载\')\n    }\n    return h(\'span\', value)\n}', b'1', NOW(), 'SYSTEM', NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_snippet` (`id`, `code`, `title`, `description`, `content`, `build_in`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('3', 'JSONARR_FILE_RENDER', 'JSON数组文件渲染', '值为JSON数组时的文件渲染，借助h函数动态渲染el-link。需要注意的是，json数组的值，必须满足格式： [{\"url\":\"\"}, ...]。若url值非绝对地址，则视为后端接口地址，会拼接全局变量中的baseURL作为前缀。', '/**\n * @param h 		vue中的渲染函数:createElement\n * @param value 	原始值。例如表格场景下，此值为单元格原始值\n * @param record	整行记录值。例如表格场景下，此值为value所在行的记录\n * @param meta		字段的实例配置。例如表格场景下，此值为value所在列的实例配置。\n */\nfunction (h, value, record, meta) {\n    if (this.isEmpty(value)) {\n        return h(\'span\', value)\n    }\n    let arrVal;\n    try {\n        arrVal = JSON.parse(value)\n    } catch (err) {\n        return h(\'span\', value)\n    }\n\n    let images;\n    let hArr = [];\n    const urls = []\n    for (let i = 0; i < arrVal.length; i++) {\n        const {name, url} = arrVal[i]\n        if (this.isEmpty(url)) {\n            continue;\n        }\n\n        const finalUrl = this.isExternal(url) ? url : \'${baseURL}\' + url;\n        urls.push(finalUrl);\n\n        const dom = h(\'el-link\', {\n            attrs: {\n                href: finalUrl,\n			  	type: \'primary\',\n			  	icon: \'el-icon-link\',\n			  	target: \'_blank\'\n            },\n			style: {\n			  display: \'block\',\n			  \'font-style\': \'italic\',\n			}\n        }, name)\n        hArr.push(dom)\n    }\n    \n    return h(\'div\', {\n	  style: {\n		\'text-align\': \'left\'\n	  }\n	}, hArr)\n}', b'1', NOW(), 'SYSTEM', NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_snippet` (`id`, `code`, `title`, `description`, `content`, `build_in`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('4', 'ICON_RENDER', '图标渲染', '原始值为SvgIcon组件支持的图标值时，可以使用此渲染函数渲染以显示正确的图标值。', '/**\n * @param h 		vue中的渲染函数:createElement\n * @param value 	原始值。例如表格场景下，此值为单元格原始值\n * @param record	整行记录值。例如表格场景下，此值为value所在行的记录\n * @param meta		字段的实例配置。例如表格场景下，此值为value所在列的实例配置。\n */\nfunction (h, value, record, meta) {\n	if (this.isEmpty(value)) {\n	  return h(\'span\', null, value)\n	}\n  	return h(\'svg-icon\', {\n	  attrs: {\n		value: value\n	  }\n	})\n}', b'1', NOW(), 'SYSTEM', NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
