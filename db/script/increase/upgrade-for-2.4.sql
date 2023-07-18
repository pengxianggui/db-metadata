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
                                    `version` int(9) NOT NULL AUTO_INCREMENT COMMENT '当前app配置的版本号',
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
INSERT INTO `meta_app_config` (`id`, `name`, `logo`, `registerable`, `default_pass`, `pass_encrypt_key`, `login_bg`, `reset_pass`, `show_greeting`, `show_theme_setting`, `allow_custom_theme`, `version`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('0', 'DB-Metadata低代码开发工具', '/img/meta.svg', b'0', '888888', 'DB-Metadata is delicious', '/img/default_bg.jpg', 'dbmeta', b'1', b'1', b'0', 1, NOW(), NULL, NULL, NULL);
COMMIT;
