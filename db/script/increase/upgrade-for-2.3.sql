ALTER TABLE `meta_auth` CHANGE COLUMN `type` `type` VARCHAR (100) NULL DEFAULT NULL COMMENT '权限类别';

ALTER TABLE `meta_router` ADD COLUMN `props` JSON COMMENT 'props' after `components`;
