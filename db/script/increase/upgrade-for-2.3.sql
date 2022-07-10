ALTER TABLE `magpie_bridge`.`meta_auth`
    CHANGE COLUMN `type` `type` VARCHAR (100) NULL DEFAULT NULL COMMENT '权限类别'
