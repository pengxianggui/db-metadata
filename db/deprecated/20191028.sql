-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: metadata
-- ------------------------------------------------------
-- Server version	5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `PDMAN_DB_VERSION`
--

DROP TABLE IF EXISTS `PDMAN_DB_VERSION`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PDMAN_DB_VERSION`
(
    `DB_VERSION`   varchar(256)  DEFAULT NULL,
    `VERSION_DESC` varchar(1024) DEFAULT NULL,
    `CREATED_TIME` varchar(32)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PDMAN_DB_VERSION`
--

LOCK TABLES `PDMAN_DB_VERSION` WRITE;
/*!40000 ALTER TABLE `PDMAN_DB_VERSION`
    DISABLE KEYS */;
INSERT INTO `PDMAN_DB_VERSION`
VALUES ('v0.0.0', '默认版本，新增的版本不能低于此版本', '2019-09-24 17:04:45'),
       ('0.10', '0.10', '2019-09-24 17:04:51'),
       ('0.11', '0.11', '2019-10-10 16:56:23'),
       ('0.12', 'meta_component_instance', '2019-10-12 12:59:10'),
       ('0.13', '组件实例中增加类别', '2019-10-18 11:18:32'),
       ('0.14', '是否系统模块', '2019-10-25 10:40:56'),
       ('0.15', 'issys 默认值', '2019-10-25 10:41:54');
/*!40000 ALTER TABLE `PDMAN_DB_VERSION`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meta_component`
--

DROP TABLE IF EXISTS `meta_component`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meta_component`
--

LOCK TABLES `meta_component` WRITE;
/*!40000 ALTER TABLE `meta_component`
    DISABLE KEYS */;
INSERT INTO `meta_component`
VALUES ('385064764017086464', '下拉框组件', 'DropDownBox', 'DropDownBox', 'null', 1, NULL, NULL, NULL, NULL, NULL),
       ('385064764197441536', '按钮组件', 'Button', 'Button', '{}', 1, NULL, NULL, NULL, NULL, NULL),
       ('385064764335853568', '输入框组件', 'TextBox', 'TextBox', '{}', 1, NULL, NULL, NULL, NULL, NULL),
       ('385064764470071296', '表格组件', 'TableList', 'TableList', '{}', 1, NULL, NULL, NULL, NULL, NULL),
       ('385064764616871936', '表单组件', 'FormTmpl', 'FormTmpl', '{}', 1, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `meta_component`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meta_component_instance`
--

DROP TABLE IF EXISTS `meta_component_instance`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meta_component_instance`
--

LOCK TABLES `meta_component_instance` WRITE;
/*!40000 ALTER TABLE `meta_component_instance`
    DISABLE KEYS */;
INSERT INTO `meta_component_instance`
VALUES ('714e22155d1f42afa2b87e149e7eb0b2', 'TableList', 'META_FIELD', 'object_code_admin111.one2', NULL, '{
  \"name\": \"元对象\",
  \"label\": \"meta_object\",
  \"columns\": [
    {
      \"name\": \"id\",
      \"label\": \"主键\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"code\",
      \"label\": \"对象编码\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"name\",
      \"label\": \"对象名\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"table_name\",
      \"label\": \"表名\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"schema_name\",
      \"label\": \"库名\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"primarys\",
      \"label\": \"主键组\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"config\",
      \"label\": \"配置\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"created_by\",
      \"label\": \"创建人\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"created_time\",
      \"label\": \"创建时间\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"updated_by\",
      \"label\": \"更新人\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"updated_time\",
      \"label\": \"更新时间\",
      \"component_name\": \"TextBox\"
    },
    {
      \"name\": \"remark\",
      \"label\": \"备注\",
      \"component_name\": \"TextBox\"
    }
  ],
  \"component_name\": \"TableList\"
}', NULL, NULL, NULL, NULL, NULL),
       ('833744e74e17453d9f48e76523f8d042', 'TableList', 'META_FIELD', 'object_code_admin111.one3', NULL, '{
         \"name\": \"元对象\",
         \"label\": \"meta_object\",
         \"columns\": [
           {
             \"name\": \"id\",
             \"label\": \"主键\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"code\",
             \"label\": \"对象编码\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"name\",
             \"label\": \"对象名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"table_name\",
             \"label\": \"表名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"schema_name\",
             \"label\": \"库名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"primarys\",
             \"label\": \"主键组\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"config\",
             \"label\": \"配置\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"created_by\",
             \"label\": \"创建人\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"created_time\",
             \"label\": \"创建时间\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"updated_by\",
             \"label\": \"更新人\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"updated_time\",
             \"label\": \"更新时间\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"remark\",
             \"label\": \"备注\",
             \"component_name\": \"TextBox\"
           }
         ],
         \"component_name\": \"TableList\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('8cc7d4bfd17d44939b93b95d595ae545', 'TableList', 'META_FIELD', 'object_code_admin111.one4', NULL, '{
         \"name\": \"元对象\",
         \"label\": \"meta_object\",
         \"columns\": [
           {
             \"name\": \"id\",
             \"label\": \"主键\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"code\",
             \"label\": \"对象编码\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"name\",
             \"label\": \"对象名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"table_name\",
             \"label\": \"表名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"schema_name\",
             \"label\": \"库名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"primarys\",
             \"label\": \"主键组\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"config\",
             \"label\": \"配置\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"created_by\",
             \"label\": \"创建人\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"created_time\",
             \"label\": \"创建时间\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"updated_by\",
             \"label\": \"更新人\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"updated_time\",
             \"label\": \"更新时间\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"remark\",
             \"label\": \"备注\",
             \"component_name\": \"TextBox\"
           }
         ],
         \"component_name\": \"TableList\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('db1547714ef943e68274feaacdf83e8f', 'TableList', 'META_FIELD', 'object_code_admin111.one1', NULL, '{
         \"name\": \"元对象\",
         \"label\": \"meta_object\",
         \"columns\": [
           {
             \"name\": \"id\",
             \"label\": \"主键\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"code\",
             \"label\": \"对象编码\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"name\",
             \"label\": \"对象名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"table_name\",
             \"label\": \"表名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"schema_name\",
             \"label\": \"库名\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"primarys\",
             \"label\": \"主键组\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"config\",
             \"label\": \"配置\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"created_by\",
             \"label\": \"创建人\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"created_time\",
             \"label\": \"创建时间\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"updated_by\",
             \"label\": \"更新人\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"updated_time\",
             \"label\": \"更新时间\",
             \"component_name\": \"TextBox\"
           },
           {
             \"name\": \"remark\",
             \"label\": \"备注\",
             \"component_name\": \"TextBox\"
           }
         ],
         \"component_name\": \"TableList\"
       }', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `meta_component_instance`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meta_config`
--

DROP TABLE IF EXISTS `meta_config`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meta_config`
--

LOCK TABLES `meta_config` WRITE;
/*!40000 ALTER TABLE `meta_config`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `meta_config`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meta_field`
--

DROP TABLE IF EXISTS `meta_field`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meta_field`
--

LOCK TABLES `meta_field` WRITE;
/*!40000 ALTER TABLE `meta_field`
    DISABLE KEYS */;
INSERT INTO `meta_field`
VALUES ('384056265149648896', 'meta_object', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
  \"version\": \"0.1\",
  \"fieldCode\": \"id\",
  \"isMultiple\": \"false\",
  \"isNullable\": \"false\",
  \"objectCode\": \"meta_object\",
  \"defaultValue\": \"\"
}', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648897', 'meta_object', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648898', 'meta_object', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648899', 'meta_object', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648900', 'meta_object', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648901', 'meta_object', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648902', 'meta_object', 'config', '0', 'config', '配置', 6, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648903', 'meta_object', 'created_by', '0', 'created_by', '创建人', 7, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648904', 'meta_object', 'created_time', '0', 'created_time', '创建时间', 8, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648905', 'meta_object', 'updated_by', '0', 'updated_by', '更新人', 9, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648906', 'meta_object', 'updated_time', '0', 'updated_time', '更新时间', 10, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384056265149648907', 'meta_object', 'remark', '0', 'remark', '备注', 11, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229504', 'meta_field', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229505', 'meta_field', 'object_code', '0', 'object_code', '对象编码', 1, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"object_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229506', 'meta_field', 'field_code', '0', 'field_code', '字段编码', 2, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"field_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229507', 'meta_field', 'is_primary', '0', 'is_primary', '主键', 3, 'varchar', '1',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_primary\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229508', 'meta_field', 'en', '0', 'en', '英文', 4, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"en\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229509', 'meta_field', 'cn', '0', 'cn', '中文', 5, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"cn\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229510', 'meta_field', 'order_num', '0', 'order_num', '排序', 6, 'int', NULL, 'java.lang.Integer', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"order_num\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229511', 'meta_field', 'db_type', '0', 'db_type', '数据类型', 7, 'varchar', '128', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"db_type\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_field\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229512', 'meta_field', 'db_type_length', '0', 'db_type_length', '数据长度', 8, 'varchar', '32',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"db_type_length\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229513', 'meta_field', 'java_type', '0', 'java_type', 'JAVA类型', 9, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"java_type\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229514', 'meta_field', 'config', '0', 'config', '配置', 10, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229515', 'meta_field', 'created_by', '0', 'created_by', '创建人', 11, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229516', 'meta_field', 'created_time', '0', 'created_time', '创建时间', 12, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229517', 'meta_field', 'updated_by', '0', 'updated_by', '更新人', 13, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229518', 'meta_field', 'updated_time', '0', 'updated_time', '更新时间', 14, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384057226702229519', 'meta_field', 'remark', '0', 'remark', '备注', 15, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395712', 's', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395713', 's', 'object_code', '0', 'object_code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"object_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395714', 's', 'field_code', '0', 'field_code', '字段编码', 2, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"field_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395715', 's', 'is_primary', '0', 'is_primary', '主键', 3, 'varchar', '1', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_primary\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395716', 's', 'en', '0', 'en', '英文', 4, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"en\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395717', 's', 'cn', '0', 'cn', '中文', 5, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"cn\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395718', 's', 'order_num', '0', 'order_num', '排序', 6, 'int', NULL, 'java.lang.Integer', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"order_num\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395719', 's', 'db_type', '0', 'db_type', '数据类型', 7, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"db_type\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395720', 's', 'db_type_length', '0', 'db_type_length', '数据长度', 8, 'varchar', '32',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"db_type_length\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395721', 's', 'java_type', '0', 'java_type', 'JAVA类型', 9, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"java_type\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395722', 's', 'config', '0', 'config', '配置', 10, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395723', 's', 'created_by', '0', 'created_by', '创建人', 11, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395724', 's', 'created_time', '0', 'created_time', '创建时间', 12, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395725', 's', 'updated_by', '0', 'updated_by', '更新人', 13, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395726', 's', 'updated_time', '0', 'updated_time', '更新时间', 14, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616337395727', 's', 'remark', '0', 'remark', '备注', 15, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716992', '', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716993', '', 'module', '0', 'module', '模块', 1, 'varchar', '1024', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"module\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716994', '', 'module_code', '0', 'module_code', '模块名', 2, 'varchar', '1024', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"module_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716995', '', 'config', '0', 'config', '配置', 3, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716996', '', 'version', '0', 'version', '版本', 4, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"version\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716997', '', 'created_by', '0', 'created_by', '创建人', 5, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716998', '', 'created_time', '0', 'created_time', '创建时间', 6, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517716999', '', 'updated_by', '0', 'updated_by', '更新人', 7, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517717000', '', 'updated_time', '0', 'updated_time', '更新时间', 8, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384368866517717001', '', 'remark', '0', 'remark', '备注', 9, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930304', 'object_code', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930305', 'object_code', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930306', 'object_code', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930307', 'object_code', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930308', 'object_code', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930309', 'object_code', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930310', 'object_code', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930311', 'object_code', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930312', 'object_code', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930313', 'object_code', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930314', 'object_code', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930315', 'object_code', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626552930316', 'object_code', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682624', 'object_code_field', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682625', 'object_code_field', 'object_code', '0', 'object_code', '对象编码', 1, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"object_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682626', 'object_code_field', 'field_code', '0', 'field_code', '字段编码', 2, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"field_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682627', 'object_code_field', 'is_primary', '0', 'is_primary', '主键', 3, 'varchar', '1',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_primary\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682628', 'object_code_field', 'en', '0', 'en', '英文', 4, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"en\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682629', 'object_code_field', 'cn', '0', 'cn', '中文', 5, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"cn\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682630', 'object_code_field', 'order_num', '0', 'order_num', '排序', 6, 'int', NULL,
        'java.lang.Integer', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"order_num\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682631', 'object_code_field', 'db_type', '0', 'db_type', '数据类型', 7, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"db_type\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682632', 'object_code_field', 'db_type_length', '0', 'db_type_length', '数据长度', 8, 'varchar', '32',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"db_type_length\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682633', 'object_code_field', 'java_type', '0', 'java_type', 'JAVA类型', 9, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"java_type\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682634', 'object_code_field', 'config', '0', 'config', '配置', 10, 'json', NULL, 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"config\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_field\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682635', 'object_code_field', 'created_by', '0', 'created_by', '创建人', 11, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682636', 'object_code_field', 'created_time', '0', 'created_time', '创建时间', 12, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682637', 'object_code_field', 'updated_by', '0', 'updated_by', '更新人', 13, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682638', 'object_code_field', 'updated_time', '0', 'updated_time', '更新时间', 14, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264422682639', 'object_code_field', 'remark', '0', 'remark', '备注', 15, 'varchar', '32',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_field\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350336', 'meta_conf', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350337', 'meta_conf', 'module', '0', 'module', '模块', 1, 'varchar', '1024', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"module\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350338', 'meta_conf', 'module_code', '0', 'module_code', '模块名', 2, 'varchar', '1024',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"module_code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350339', 'meta_conf', 'config', '0', 'config', '配置', 3, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350340', 'meta_conf', 'version', '0', 'version', '版本', 4, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"version\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350341', 'meta_conf', 'created_by', '0', 'created_by', '创建人', 5, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350342', 'meta_conf', 'created_time', '0', 'created_time', '创建时间', 6, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350343', 'meta_conf', 'updated_by', '0', 'updated_by', '更新人', 7, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350344', 'meta_conf', 'updated_time', '0', 'updated_time', '更新时间', 8, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840942350345', 'meta_conf', 'remark', '0', 'remark', '备注', 9, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_config\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705984', 'object_code_table_list', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"id\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"false\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705985', 'object_code_table_list', 'code', '0', 'code', '对象编码', 1, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705986', 'object_code_table_list', 'name', '0', 'name', '对象名', 2, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705987', 'object_code_table_list', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705988', 'object_code_table_list', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705989', 'object_code_table_list', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705990', 'object_code_table_list', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705991', 'object_code_table_list', 'config', '0', 'config', '配置', 7, 'json', NULL,
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705992', 'object_code_table_list', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705993', 'object_code_table_list', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime',
        NULL, 'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705994', 'object_code_table_list', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705995', 'object_code_table_list', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime',
        NULL, 'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187681705996', 'object_code_table_list', 'remark', '0', 'remark', '备注', 12, 'varchar', '32',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075136', 'object_code_temp', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075137', 'object_code_temp', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"code\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075138', 'object_code_temp', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"name\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075139', 'object_code_temp', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075140', 'object_code_temp', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075141', 'object_code_temp', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075142', 'object_code_temp', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075143', 'object_code_temp', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075144', 'object_code_temp', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075145', 'object_code_temp', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075146', 'object_code_temp', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075147', 'object_code_temp', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035354075148', 'object_code_temp', 'remark', '0', 'remark', '备注', 12, 'varchar', '32',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700416', 'object_code_2', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700417', 'object_code_2', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700418', 'object_code_2', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700419', 'object_code_2', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700420', 'object_code_2', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700421', 'object_code_2', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700422', 'object_code_2', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700423', 'object_code_2', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700424', 'object_code_2', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700425', 'object_code_2', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700426', 'object_code_2', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700427', 'object_code_2', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933632700428', 'object_code_2', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424832', 'object_code_21', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424833', 'object_code_21', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424834', 'object_code_21', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424835', 'object_code_21', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424836', 'object_code_21', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424837', 'object_code_21', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424838', 'object_code_21', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424839', 'object_code_21', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424840', 'object_code_21', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424841', 'object_code_21', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424842', 'object_code_21', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424843', 'object_code_21', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148265026424844', 'object_code_21', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093888', 'object_code_3', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093889', 'object_code_3', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093890', 'object_code_3', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093891', 'object_code_3', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093892', 'object_code_3', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093893', 'object_code_3', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093894', 'object_code_3', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093895', 'object_code_3', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093896', 'object_code_3', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093897', 'object_code_3', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093898', 'object_code_3', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093899', 'object_code_3', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236381093900', 'object_code_3', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581312', 'object_code_4', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581313', 'object_code_4', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581314', 'object_code_4', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581315', 'object_code_4', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581316', 'object_code_4', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581317', 'object_code_4', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581318', 'object_code_4', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581319', 'object_code_4', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581320', 'object_code_4', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581321', 'object_code_4', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581322', 'object_code_4', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581323', 'object_code_4', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736157581324', 'object_code_4', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579328', 'object_code4', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579329', 'object_code4', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579330', 'object_code4', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579331', 'object_code4', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579332', 'object_code4', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579333', 'object_code4', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579334', 'object_code4', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579335', 'object_code4', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579336', 'object_code4', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579337', 'object_code4', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579338', 'object_code4', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579339', 'object_code4', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507716579340', 'object_code4', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201920', 'object_code41', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201921', 'object_code41', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201922', 'object_code41', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201923', 'object_code41', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201924', 'object_code41', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201925', 'object_code41', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201926', 'object_code41', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201927', 'object_code41', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201928', 'object_code41', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201929', 'object_code41', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201930', 'object_code41', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201931', 'object_code41', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543045201932', 'object_code41', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615680', 'sss', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615681', 'sss', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615682', 'sss', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615683', 'sss', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615684', 'sss', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615685', 'sss', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615686', 'sss', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615687', 'sss', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615688', 'sss', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615689', 'sss', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615690', 'sss', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615691', 'sss', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854220615692', 'sss', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984768', 'xxxxx', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984769', 'xxxxx', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984770', 'xxxxx', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984771', 'xxxxx', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984772', 'xxxxx', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"schema_name\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984773', 'xxxxx', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984774', 'xxxxx', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984775', 'xxxxx', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984776', 'xxxxx', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"created_by\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984777', 'xxxxx', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984778', 'xxxxx', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"updated_by\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984779', 'xxxxx', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629629984780', 'xxxxx', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358400', 'object_codeyyii', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358401', 'object_codeyyii', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358402', 'object_codeyyii', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358403', 'object_codeyyii', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358404', 'object_codeyyii', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358405', 'object_codeyyii', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358406', 'object_codeyyii', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358407', 'object_codeyyii', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358408', 'object_codeyyii', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358409', 'object_codeyyii', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358410', 'object_codeyyii', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358411', 'object_codeyyii', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635616358412', 'object_codeyyii', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340608', 'asdfasdgfsagfs', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340609', 'asdfasdgfsagfs', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340610', 'asdfasdgfsagfs', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340611', 'asdfasdgfsagfs', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340612', 'asdfasdgfsagfs', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340613', 'asdfasdgfsagfs', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"primarys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340614', 'asdfasdgfsagfs', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"is_sys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"0\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340615', 'asdfasdgfsagfs', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340616', 'asdfasdgfsagfs', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340617', 'asdfasdgfsagfs', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340618', 'asdfasdgfsagfs', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340619', 'asdfasdgfsagfs', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870086340620', 'asdfasdgfsagfs', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"remark\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707712', 'dfsdfsdfs', 'id', '1', 'id', '主键', 0, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"id\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"false\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707713', 'dfsdfsdfs', 'code', '0', 'code', '对象编码', 1, 'varchar', '64', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"code\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707714', 'dfsdfsdfs', 'name', '0', 'name', '对象名', 2, 'varchar', '128', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707715', 'dfsdfsdfs', 'table_name', '0', 'table_name', '表名', 3, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"table_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707716', 'dfsdfsdfs', 'schema_name', '0', 'schema_name', '库名', 4, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"schema_name\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707717', 'dfsdfsdfs', 'primarys', '0', 'primarys', '主键组', 5, 'varchar', '128', 'java.lang.String',
        '{
          \"version\": \"0.1\",
          \"fieldCode\": \"primarys\",
          \"isMultiple\": \"false\",
          \"isNullable\": \"true\",
          \"objectCode\": \"meta_object\",
          \"defaultValue\": \"\"
        }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707718', 'dfsdfsdfs', 'is_sys', '0', 'is_sys', '系统模块', 6, 'varchar', '1', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"is_sys\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"0\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707719', 'dfsdfsdfs', 'config', '0', 'config', '配置', 7, 'json', NULL, 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"config\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707720', 'dfsdfsdfs', 'created_by', '0', 'created_by', '创建人', 8, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707721', 'dfsdfsdfs', 'created_time', '0', 'created_time', '创建时间', 9, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"created_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707722', 'dfsdfsdfs', 'updated_by', '0', 'updated_by', '更新人', 10, 'varchar', '64',
        'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_by\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707723', 'dfsdfsdfs', 'updated_time', '0', 'updated_time', '更新时间', 11, 'datetime', NULL,
        'com.hthjsj.analysis.db.DateTime', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"updated_time\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155434014707724', 'dfsdfsdfs', 'remark', '0', 'remark', '备注', 12, 'varchar', '32', 'java.lang.String', '{
         \"version\": \"0.1\",
         \"fieldCode\": \"remark\",
         \"isMultiple\": \"false\",
         \"isNullable\": \"true\",
         \"objectCode\": \"meta_object\",
         \"defaultValue\": \"\"
       }', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `meta_field`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meta_object`
--

DROP TABLE IF EXISTS `meta_object`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meta_object`
--

LOCK TABLES `meta_object` WRITE;
/*!40000 ALTER TABLE `meta_object`
    DISABLE KEYS */;
INSERT INTO `meta_object`
VALUES ('384056265120288768', 'meta_object', '基础原对象', 'meta_object', 'metadata', 'id', '1', '{
  \"isMultiple\": \"false\",
  \"objectCode\": \"meta_object\",
  \"isUUIDPrimary\": \"true\"
}', NULL, NULL, NULL, NULL, NULL),
       ('384057226672869376', 'meta_field', 'meta_field', 'meta_field', 'metadata', 'id', '1', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_field\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('384060616295452672', 's', 's', 'meta_field', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_field\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385121626506792960', 'object_code', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122264393322496', 'object_code_field', '元字段', 'meta_field', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_field\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385122840912990208', 'meta_conf', '元对象配置', 'meta_config', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_config\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385128187643957248', 'object_code_table_list', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385131035320520704', 'object_code_temp', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385146933565591552', 'object_code_2', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385148264971898880', 'object_code_21', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149236347539456', 'object_code_3', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385149736128221184', 'object_code_4', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150507683024896', 'object_code4', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150543015841792', 'object_code41', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385150854191255552', 'sss', 's', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385151629600624640', 'xxxxx', 'sdfsf', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154635586998272', 'object_codeyyii', '元对象', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385154870056980480', 'asdfasdgfsagfs', 'sdfs', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL),
       ('385155433985347584', 'dfsdfsdfs', 'sdfsdf', 'meta_object', 'metadata', 'id', '0', '{
         \"isMultiple\": \"false\",
         \"objectCode\": \"meta_object\",
         \"isUUIDPrimary\": \"true\"
       }', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `meta_object`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pdman_db_version`
--

DROP TABLE IF EXISTS `pdman_db_version`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pdman_db_version`
(
    `DB_VERSION`   varchar(256)  DEFAULT NULL,
    `VERSION_DESC` varchar(1024) DEFAULT NULL,
    `CREATED_TIME` varchar(32)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pdman_db_version`
--

LOCK TABLES `pdman_db_version` WRITE;
/*!40000 ALTER TABLE `pdman_db_version`
    DISABLE KEYS */;
INSERT INTO `pdman_db_version`
VALUES ('v0.0.0', '默认版本，新增的版本不能低于此版本', '2019-08-23 15:50:17'),
       ('0.6', '0.6', '2019-08-23 15:50:25'),
       ('0.7', '增加配置', '2019-08-23 16:56:55'),
       ('0.8', '是否主键', '2019-08-23 19:02:35'),
       ('0.9', '0.9', '2019-09-12 11:00:35');
/*!40000 ALTER TABLE `pdman_db_version`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_table`
--

DROP TABLE IF EXISTS `test_table`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_table`
--

LOCK TABLES `test_table` WRITE;
/*!40000 ALTER TABLE `test_table`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `test_table`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2019-10-28  7:07:38
