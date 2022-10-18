-- MySQL dump 10.13  Distrib 5.7.39, for Linux (x86_64)
--
-- Host: 192.168.192.154    Database: gateway_admin
-- ------------------------------------------------------
-- Server version	5.7.39-log

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
-- Current Database: `gateway_admin`
--

CREATE DATABASE /*!32312 IF NOT EXISTS */ `gateway_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `gateway_admin`;

--
-- Table structure for table `gateway_access_conf`
--

DROP TABLE IF EXISTS `gateway_access_conf`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gateway_access_conf`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `api_key`     varchar(64)          DEFAULT NULL,
    `api_secret`  varchar(64)          DEFAULT NULL,
    `system`      varchar(64)          DEFAULT NULL,
    `status`      char(1)              DEFAULT NULL,
    `remark`      longtext,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='网关访问';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_access_conf`
--

LOCK TABLES `gateway_access_conf` WRITE;
/*!40000 ALTER TABLE `gateway_access_conf`
    DISABLE KEYS */;
INSERT INTO `gateway_access_conf`
VALUES (1, 'testApiKey', 'testApiSecret', 'testApi', '0', '测试', '2022-06-04 01:27:53', '2022-06-04 01:27:27', '1',
        '1', 0, 1);
/*!40000 ALTER TABLE `gateway_access_conf`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateway_log`
--

DROP TABLE IF EXISTS `gateway_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gateway_log`
(
    `id`                     varchar(64) NOT NULL COMMENT '主键ID',
    `source_service`         varchar(64)  DEFAULT NULL,
    `api_key`                varchar(64)  DEFAULT NULL,
    `api_secret`             varchar(64)  DEFAULT NULL,
    `environment`            varchar(64)  DEFAULT NULL,
    `target_service`         varchar(64)  DEFAULT NULL,
    `schema`                 varchar(10)  DEFAULT NULL,
    `request_path`           longtext,
    `request_path_and_query` longtext,
    `request_method`         varchar(10)  DEFAULT NULL,
    `request_header`         longtext,
    `request_body`           longtext,
    `request_source_ip`      varchar(128) DEFAULT NULL,
    `response_body`          longtext,
    `http_status`            varchar(10)  DEFAULT NULL,
    `execute_time`           bigint(20)   DEFAULT NULL,
    `request_time`           datetime     DEFAULT NULL,
    `response_time`          datetime     DEFAULT NULL,
    `error_msg`              longtext,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='网关日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_log`
--

LOCK TABLES `gateway_log` WRITE;
/*!40000 ALTER TABLE `gateway_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `gateway_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateway_route_conf`
--

DROP TABLE IF EXISTS `gateway_route_conf`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gateway_route_conf`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `route_name`  varchar(30)          DEFAULT NULL,
    `route_id`    varchar(30)          DEFAULT NULL,
    `predicates`  longtext,
    `filters`     longtext,
    `uri`         longtext,
    `sort`        tinyint(4)           DEFAULT NULL,
    `metadata`    longtext,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='网关路由';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_route_conf`
--

LOCK TABLES `gateway_route_conf` WRITE;
/*!40000 ALTER TABLE `gateway_route_conf`
    DISABLE KEYS */;
INSERT INTO `gateway_route_conf`
VALUES (1, '监控管理', 'monitor-center', '[{\"args\": {\"_genkey_0\": \"/monitor-center/**\"}, \"name\": \"Path\"}]',
        '[]', 'lb://monitor-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-01-06 02:40:46', '1', '1',
        0, 1),
       (2, '百度', 'baidu', '[{\"args\": {\"_genkey_0\": \"/baidu/**\"}, \"name\": \"Path\"}]', '[]',
        'https://www.baidu.com', 0, '{\"version\": \"1\"}', '2022-06-03 08:39:53', '2022-07-03 05:55:04', '1', '1', 0,
        1);
/*!40000 ALTER TABLE `gateway_route_conf`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `name`        varchar(50)          DEFAULT NULL,
    `sort`        tinyint(4)           DEFAULT NULL,
    `parent_id`   varchar(64)          DEFAULT NULL,
    `status`      tinyint(3)           DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept`
    DISABLE KEYS */;
INSERT INTO `sys_dept`
VALUES (1, '山东', 1, -1, 1, '2018-01-22 11:00:23', '2021-12-31 06:33:51', '1', '1', 0, 1),
       (2, '沙县国际', 2, -1, 1, '2018-01-22 11:00:38', '2021-12-31 06:33:51', '1', '1', 0, 1),
       (3, '潍坊', 3, 1, 1, '2018-01-22 11:00:44', '2021-12-31 06:33:51', '1', '1', 0, 1),
       (4, '高新', 4, 3, 1, '2018-01-22 11:00:52', '2021-12-31 06:33:51', '1', '1', 0, 1),
       (5, '院校', 5, 4, 1, '2018-01-22 11:00:57', '2021-12-31 06:33:51', '1', '1', 0, 1),
       (7, '山东沙县', 7, 2, 1, '2018-01-22 11:01:57', '2021-12-31 06:33:51', '1', '1', 0, 1),
       (8, '潍坊沙县', 8, 7, 1, '2018-01-22 11:02:03', '2021-12-31 06:33:51', '1', '1', 0, 1);
/*!40000 ALTER TABLE `sys_dept`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_relation`
--

DROP TABLE IF EXISTS `sys_dept_relation`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept_relation`
(
    `ancestor`    varchar(64)          DEFAULT NULL,
    `descendant`  varchar(64)          DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门联系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_relation`
--

LOCK TABLES `sys_dept_relation` WRITE;
/*!40000 ALTER TABLE `sys_dept_relation`
    DISABLE KEYS */;
INSERT INTO `sys_dept_relation`
VALUES (1, 1, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 11),
       (1, 3, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 13),
       (1, 4, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 14),
       (1, 5, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 15),
       (1, 9, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 19),
       (2, 2, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 22),
       (2, 7, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 27),
       (2, 8, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 28),
       (3, 3, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 33),
       (3, 4, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 34),
       (3, 5, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 35),
       (4, 4, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 44),
       (4, 5, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 45),
       (5, 5, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 55),
       (7, 7, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 77),
       (7, 8, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 78),
       (8, 8, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 88),
       (9, 9, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 99),
       (1, 10, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 110),
       (2, 11, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 211),
       (2, 12, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 212),
       (7, 11, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 711),
       (7, 12, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 712),
       (10, 10, '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', 0, 1, 1010);
/*!40000 ALTER TABLE `sys_dept_relation`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_error_code`
--

DROP TABLE IF EXISTS `sys_error_code`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_error_code`
(
    `id`               varchar(64) NOT NULL COMMENT '主键ID',
    `type`             tinyint(3)           DEFAULT NULL,
    `application_name` varchar(50)          DEFAULT NULL,
    `code`             int(11)              DEFAULT NULL,
    `message`          varchar(512)         DEFAULT NULL,
    `memo`             varchar(512)         DEFAULT NULL,
    `create_time`      datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`          varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`          varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`          varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`          bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='错误码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `name`        varchar(32)          DEFAULT NULL,
    `permission`  varchar(32)          DEFAULT NULL,
    `path`        varchar(128)         DEFAULT NULL,
    `parent_id`   varchar(64)          DEFAULT NULL,
    `icon`        varchar(32)          DEFAULT NULL,
    `sort`        tinyint(4)           DEFAULT NULL,
    `keep_alive`  char(1)              DEFAULT NULL,
    `type`        char(1)              DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `component`   varchar(128)         DEFAULT NULL COMMENT '视图',
    `tag`         varchar(64)          DEFAULT NULL COMMENT '标签',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu`
    DISABLE KEYS */;
INSERT INTO `sys_menu`
VALUES (0, '首页', NULL, '/home', -1, 'el-icon-eleme-filled', 0, '0', '0', '2018-09-28 08:29:53', '2021-12-27 15:13:30',
        NULL, NULL, '1', '1', 0, 1),
       (100, '工作台', NULL, '/dashboard', 0, 'el-icon-menu', 1, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16',
        'home', NULL, '1', '1', 0, 1),
       (200, '用户中心', NULL, '/userCenter', 0, 'el-icon-user', 2, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'userCenter', NULL, '1', '1', 0, 1),
       (1000, '系统管理', NULL, '/system', -1, 'el-icon-tools', 10, '0', '0', '2017-11-02 22:24:37',
        '2017-11-02 22:24:37', NULL, NULL, '1', '1', 0, 1),
       (1100, '用户管理', NULL, '/system/user', 1000, 'el-icon-user-filled', 1, '0', '0', '2017-11-02 22:24:37',
        '2021-12-28 20:15:53', 'system/user', NULL, '1', '1', 0, 1),
       (1101, '用户新增', 'sys_user_add', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:52:09', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1102, '用户修改', 'sys_user_edit', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:52:48', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1103, '用户删除', 'sys_user_del', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1104, '导入导出', 'sys_user_export', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:54:01',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (1200, '菜单管理', NULL, '/system/menu', 1000, 'el-icon-fold', 2, '0', '0', '2017-11-08 09:57:27',
        '2021-12-28 20:14:22', 'system/menu', NULL, '1', '1', 0, 1),
       (1201, '菜单新增', 'sys_menu_add', NULL, 1200, NULL, 1, '0', '1', '2017-11-08 10:15:53', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1202, '菜单修改', 'sys_menu_edit', NULL, 1200, NULL, 1, '0', '1', '2017-11-08 10:16:23', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1203, '菜单删除', 'sys_menu_del', NULL, 1200, NULL, 1, '0', '1', '2017-11-08 10:16:43', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1300, '角色管理', NULL, '/system/role', 1000, 'el-icon-notebook', 3, '0', '0', '2017-11-08 10:13:37',
        '2021-12-28 20:14:19', 'system/role', NULL, '1', '1', 0, 1),
       (1301, '角色新增', 'sys_role_add', NULL, 1300, NULL, 1, '0', '1', '2017-11-08 10:14:18', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1302, '角色修改', 'sys_role_edit', NULL, 1300, NULL, 1, '0', '1', '2017-11-08 10:14:41', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1303, '角色删除', 'sys_role_del', NULL, 1300, NULL, 1, '0', '1', '2017-11-08 10:14:59', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1304, '分配权限', 'sys_role_perm', NULL, 1300, NULL, 1, '0', '1', '2018-04-20 07:22:55', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1400, '部门管理', NULL, '/system/dept', 1000, 'sc-icon-organization', 4, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'system/dept', NULL, '1', '1', 0, 1),
       (1401, '部门新增', 'sys_dept_add', NULL, 1400, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1402, '部门修改', 'sys_dept_edit', NULL, 1400, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1403, '部门删除', 'sys_dept_del', NULL, 1400, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55',
        NULL, NULL, '1', '1', 0, 1),
       (1500, '字典管理', NULL, '/system/dic', 1000, 'el-icon-document', 5, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'system/dic', NULL, '1', '1', 0, 1),
       (1600, '应用管理', NULL, '/system/client', 1000, 'el-icon-help-filled', 6, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'system/client', NULL, '1', '1', 0, 1),
       (1700, '表格列管理', NULL, '/system/table', 1000, 'el-icon-scale-to-original', 7, '0', '0',
        '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/table', NULL, '1', '1', 0, 1),
       (1800, '系统设置', NULL, '/system/setting', 1000, 'el-icon-tools', 10, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'system/setting', NULL, '1', '1', 0, 1),
       (1900, '系统日志', NULL, '/system/log', 1000, 'el-icon-warning', 9, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'system/log', NULL, '1', '1', 0, 1),
       (2000, '网关管理', NULL, '/gateway', -1, 'el-icon-coin', 20, '0', '0', '2018-09-04 05:58:41',
        '2022-09-23 00:21:19', 'gateway', NULL, '1', '1', 0, 1),
       (2100, '网关日志', NULL, '/gateway/log', 2000, 'el-icon-edit-pen', 1, '0', '0', '2018-09-04 05:58:41',
        '2022-09-23 00:19:37', 'gateway/log', NULL, '1', '1', 0, 1),
       (2101, '网关日志新增', 'gateway_log_add', NULL, 2100, NULL, 1, '0', '1', '2018-01-20 14:56:16',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2102, '网关日志修改', 'gateway_log_edit', NULL, 2100, NULL, 1, '0', '1', '2018-01-20 14:56:59',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2103, '网关日志删除', 'gateway_log_del', NULL, 2100, NULL, 1, '0', '1', '2018-01-20 14:57:28',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2200, '网关路由', NULL, '/gateway/route', 2000, 'el-icon-platform', 2, '0', '0', '2018-09-04 05:58:41',
        '2022-09-23 00:20:39', 'gateway/route', NULL, '1', '1', 0, 1),
       (2201, '网关路由新增', 'gateway_route_add', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:56:16',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2202, '网关路由修改', 'gateway_route_edit', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:56:59',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2203, '网关路由删除', 'gateway_route_del', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:57:28',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2204, '网关路由拷贝', 'gateway_route_copy', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:57:28',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2300, '网关访问', NULL, '/gateway/access', 2000, 'el-icon-avatar', 3, '0', '0', '2018-09-04 05:58:41',
        '2022-09-23 00:20:50', 'gateway/access', NULL, '1', '1', 0, 1),
       (2301, '网关访问新增', 'gateway_access_add', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:56:16',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2302, '网关访问修改', 'gateway_access_edit', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:56:59',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2303, '网关访问删除', 'gateway_access_del', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:57:28',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2304, '网关访问状态启用禁用', 'gateway_access_status', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:57:28',
        '2021-05-25 03:12:55', NULL, NULL, '1', '1', 0, 1),
       (2400, '元数据管理', NULL, '/gateway/metadata', 2000, 'el-icon-collection-tag', 5, '0', '0',
        '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/metadata', NULL, '1', '1', 0, 1),
       (2401, '元数据新增', 'gateway_metadata_add', NULL, 2400, NULL, 1, '0', '1', '2022-09-19 17:01:58',
        '2022-09-19 17:01:58', NULL, NULL, '1', '1', 0, 1),
       (2402, '元数据修改', 'gateway_metadata_edit', NULL, 2400, NULL, 1, '0', '1', '2022-09-19 17:01:58',
        '2022-09-19 17:01:58', NULL, NULL, '1', '1', 0, 1),
       (2403, '元数据删除', 'gateway_metadata_del', NULL, 2400, NULL, 1, '0', '1', '2022-09-19 17:01:59',
        '2022-09-19 17:01:59', NULL, NULL, '1', '1', 0, 1),
       (3000, '关于', NULL, '/other/about', -1, 'el-icon-info-filled', 30, '0', '0', '2018-09-04 05:58:41',
        '2022-09-23 00:24:30', 'other/about', NULL, '1', '1', 0, 1),
       (11100, '计划任务', NULL, '/system/task', 1000, 'el-icon-alarm-clock', 8, '0', '0', '2018-01-20 13:17:19',
        '2021-12-28 20:14:16', 'system/task', NULL, '1', '1', 0, 1),
       (11200, '错误码管理', NULL, '/system/errorcode', 1000, 'el-icon-user-filled', 5, '0', '0', '2022-09-19 17:01:55',
        '2022-09-19 17:01:55', 'system/errorcode', NULL, '1', '1', 0, 1),
       (11201, '错误码新增', 'sys_error_code_add', NULL, 11200, NULL, 1, '0', '1', '2022-09-19 17:01:55',
        '2022-09-19 17:01:55', NULL, NULL, '1', '1', 0, 1),
       (11202, '错误码修改', 'sys_error_code_edit', NULL, 11200, NULL, 1, '0', '1', '2022-09-19 17:01:56',
        '2022-09-19 17:01:56', NULL, NULL, '1', '1', 0, 1),
       (11203, '错误码删除', 'sys_error_code_del', NULL, 11200, NULL, 1, '0', '1', '2022-09-19 17:01:56',
        '2022-09-19 17:01:56', NULL, NULL, '1', '1', 0, 1);
/*!40000 ALTER TABLE `sys_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oauth_client_details`
--

DROP TABLE IF EXISTS `sys_oauth_client_details`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_oauth_client_details`
(
    `id`                      varchar(64) NOT NULL COMMENT '主键ID',
    `client_id`               varchar(32) NOT NULL COMMENT '客户端ID',
    `resource_ids`            varchar(256)         DEFAULT NULL COMMENT '资源列表',
    `client_secret`           varchar(256)         DEFAULT NULL COMMENT '客户端密钥',
    `scope`                   varchar(256)         DEFAULT NULL COMMENT '域',
    `authorized_grant_types`  varchar(256)         DEFAULT NULL COMMENT '认证类型',
    `web_server_redirect_uri` varchar(256)         DEFAULT NULL COMMENT '重定向地址',
    `authorities`             varchar(256)         DEFAULT NULL COMMENT '角色列表',
    `access_token_validity`   int(11)              DEFAULT NULL COMMENT 'token 有效期',
    `refresh_token_validity`  int(11)              DEFAULT NULL COMMENT '刷新令牌有效期',
    `additional_information`  varchar(4096)        DEFAULT NULL COMMENT '令牌扩展字段JSON',
    `autoapprove`             varchar(256)         DEFAULT NULL COMMENT '是否自动放行',
    `create_time`             datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`             datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`                 varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`                 varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`                 bigint(20)  NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`                 bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_oauth_client_details_client_id_IDX` (`client_id`, `deleted`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='终端信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oauth_client_details`
--

LOCK TABLES `sys_oauth_client_details` WRITE;
/*!40000 ALTER TABLE `sys_oauth_client_details`
    DISABLE KEYS */;
INSERT INTO `sys_oauth_client_details`
VALUES (1, 'app', NULL, 'app', 'server', 'app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, '1',
        '1', 0, 1),
       (2, 'gateway-admin', NULL, 'gateway-admin', 'server',
        'password,app,refresh_token,authorization_code,client_credentials',
        'http://localhost:4040/sso1/login,http://localhost:4041/sso1/login', NULL, NULL, NULL, NULL, 'true', NULL, NULL,
        '1', '1', 0, 1),
       (3, 'tool-center', NULL, 'tool-center', 'server',
        'password,app,refresh_token,authorization_code,client_credentials',
        NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL,
        '1', '1', 0, 1);
/*!40000 ALTER TABLE `sys_oauth_client_details`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `role_name`   varchar(64)          DEFAULT NULL,
    `role_code`   varchar(64)          DEFAULT NULL,
    `role_desc`   varchar(255)         DEFAULT NULL,
    `ds_type`     char(1)              DEFAULT NULL,
    `ds_scope`    varchar(255)         DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 'ROLE_admin', '超级管理员', '0', '2', '2017-10-29 07:45:51', '2021-12-31 06:19:46', '1', '1',
        0, 1),
       (2, '普通用户', 'ROLE_normal', '普通用户', '0', '2', '2021-12-31 06:19:20', '2021-12-31 06:20:27', '1', '1', 0,
        1);
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu`
(
    `role_id`     int(11)              DEFAULT NULL,
    `menu_id`     int(11)              DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu`
    DISABLE KEYS */;
INSERT INTO `sys_role_menu`
VALUES (1, 0, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 10),
       (3, 0, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 30),
       (1, 100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 1100),
       (1, 200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 1200),
       (3, 100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 3100),
       (3, 200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 3200),
       (1, 1000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11000),
       (1, 1100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11100),
       (1, 1101, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11101),
       (1, 1102, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11102),
       (1, 1103, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11103),
       (1, 1104, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11104),
       (1, 1200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11200),
       (1, 1201, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11201),
       (1, 1202, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11202),
       (1, 1203, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11203),
       (1, 1300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11300),
       (1, 1301, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11301),
       (1, 1302, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11302),
       (1, 1303, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11303),
       (1, 1304, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11304),
       (1, 1400, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11400),
       (1, 1401, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11401),
       (1, 1402, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11402),
       (1, 1403, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11403),
       (1, 1500, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11500),
       (1, 1600, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11600),
       (1, 1700, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11700),
       (1, 1800, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11800),
       (1, 1900, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 11900),
       (1, 2000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12000),
       (1, 2100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12100),
       (1, 2101, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12101),
       (1, 2102, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12102),
       (1, 2103, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12103),
       (1, 2200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12200),
       (1, 2201, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12201),
       (1, 2202, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12202),
       (1, 2203, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12203),
       (1, 2204, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12204),
       (1, 2300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12300),
       (1, 2301, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12301),
       (1, 2302, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12302),
       (1, 2303, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12303),
       (1, 2304, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 12304),
       (1, 2400, '2022-09-19 17:01:59', '2022-09-19 17:01:59', '1', '1', 0, 1, 12400),
       (1, 2401, '2022-09-19 17:01:59', '2022-09-19 17:01:59', '1', '1', 0, 1, 12401),
       (1, 2402, '2022-09-19 17:02:00', '2022-09-19 17:02:00', '1', '1', 0, 1, 12402),
       (1, 2403, '2022-09-19 17:02:00', '2022-09-19 17:02:00', '1', '1', 0, 1, 12403),
       (1, 3000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 13000),
       (2, 2200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 22200),
       (2, 2300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 22300),
       (2, 3000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 23000),
       (3, 3000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 33000),
       (1, 11100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', 0, 1, 111100),
       (1, 11200, '2022-09-19 17:01:56', '2022-09-19 17:01:56', '1', '1', 0, 1, 111200),
       (1, 11201, '2022-09-19 17:01:57', '2022-09-19 17:01:57', '1', '1', 0, 1, 111201),
       (1, 11202, '2022-09-19 17:01:57', '2022-09-19 17:01:57', '1', '1', 0, 1, 111202),
       (1, 11203, '2022-09-19 17:01:57', '2022-09-19 17:01:57', '1', '1', 0, 1, 111203);
/*!40000 ALTER TABLE `sys_role_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user`
(
    `id`              varchar(64) NOT NULL COMMENT '主键ID',
    `user_name`       varchar(64)          DEFAULT NULL COMMENT '用户名',
    `nick_name`       varchar(64)          DEFAULT NULL COMMENT '别名',
    `password`        varchar(255)         DEFAULT NULL COMMENT '密码',
    `salt`            varchar(255)         DEFAULT NULL COMMENT '盐',
    `gender`          tinyint(4)           DEFAULT NULL COMMENT '性别',
    `email`           varchar(255)         DEFAULT NULL COMMENT '邮箱',
    `mobile`          varchar(20)          DEFAULT NULL COMMENT '手机号',
    `avatar`          varchar(255)         DEFAULT NULL COMMENT '头像',
    `last_login_time` datetime             DEFAULT NULL COMMENT '上次登录时间',
    `last_login_ip`   varchar(64)          DEFAULT NULL COMMENT '上次登录ip',
    `dept_id`         varchar(64)          DEFAULT NULL COMMENT '部门id',
    `lock_flag`       tinyint(4)           DEFAULT NULL COMMENT '锁定标识',
    `create_time`     datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`         varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`         varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`         bigint(20)  NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`         bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user`
VALUES (1, 'admin', 'admin-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 1,
        'li7hai26@gmail.com', '17034642888', '', '2022-01-06 07:47:04', '127.0.0.1', 1, 0, '2018-04-19 23:15:18',
        '2022-07-21 14:03:01', '1', '1', 0, 1),
       (2, 'editor', 'editor-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 0,
        'li7hai26@outlook.com', '17034642888', '', '2022-01-06 07:47:08', '127.0.0.1', 5, 0, '2021-12-31 08:53:14',
        '2022-01-06 10:40:33', '1', '1', 0, 1);
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role`
(
    `user_id`     varchar(64)          DEFAULT NULL,
    `role_id`     varchar(64)          DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES (1, 1, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 11),
       (1, 2, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 12),
       (2, 2, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 22),
       (3, 2, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 32),
       (9, 1, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 91),
       (9, 2, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 92),
       (12, 2, '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', 0, 1, 122);
/*!40000 ALTER TABLE `sys_user_role`
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

-- Dump completed on 2022-10-03 14:05:59


-- ----------------------------
-- Table structure for metadata_authorize
-- ----------------------------

DROP TABLE IF EXISTS `metadata_authorize`;
CREATE TABLE `metadata_authorize`
(
    `id`          bigint(20)  NOT NULL COMMENT '主键ID',
    `object_id`   bigint(20)  NOT NULL COMMENT '目标表主键ID',
    `role_id`     bigint(20)  NOT NULL COMMENT '角色ID',
    `object_type` varchar(10) NULL DEFAULT NULL COMMENT '目标表类型',
    PRIMARY KEY (`id`, `object_id`, `role_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '元数据授权信息表'
  ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of metadata_authorize
-- ----------------------------
INSERT INTO `metadata_authorize`
VALUES ('1339728732931301378', '1336474987430793217', '1319084037507244034', 'database');
INSERT INTO `metadata_authorize`
VALUES ('1339728733359120386', '1336479261791473665', '1319084037507244034', 'table');
INSERT INTO `metadata_authorize`
VALUES ('1339728733749190658', '1336479262852632577', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728734139260930', '1336479263477583874', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728734529331201', '1336479264106729474', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728734923595777', '1336479264639406082', '1319084037507244034', 'table');
INSERT INTO `metadata_authorize`
VALUES ('1339728735317860354', '1336479265583124482', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728735707930626', '1336479266149355521', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728736093806593', '1336479266728169473', '1319084037507244034', 'table');
INSERT INTO `metadata_authorize`
VALUES ('1339728736483876865', '1336479267583807489', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728736878141442', '1336479268242313218', '1319084037507244034', 'column');
INSERT INTO `metadata_authorize`
VALUES ('1339728737268211713', '1336479268821127170', '1319084037507244034', 'column');

-- ----------------------------
-- Table structure for metadata_change_record
-- ----------------------------
DROP TABLE IF EXISTS `metadata_change_record`;
CREATE TABLE `metadata_change_record`
(
    `id`              bigint(20)   NOT NULL COMMENT '主键ID',
    `creator`         varchar(64)  NOT NULL DEFAULT '1' COMMENT '创建人',
    `create_time`     datetime              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_dept`     bigint(20)   not NULL DEFAULT '1' COMMENT '创建人所属部门',
    `updater`         varchar(64)  NOT NULL DEFAULT '1' COMMENT '更新人',
    `update_time`     datetime              DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bigint(20)   NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`         bigint(20)   NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    `object_type`     varchar(255) NULL     DEFAULT NULL COMMENT '源数据的表名或者能唯一对应的源数据表的标识（可废弃）',
    `object_id`       varchar(255) NULL     DEFAULT NULL COMMENT '源数据表主键',
    `field_name`      varchar(255) NULL     DEFAULT NULL COMMENT '修改的源数据表的字段名',
    `field_old_value` varchar(255) NULL     DEFAULT NULL COMMENT '该字段原来的值',
    `field_new_value` varchar(255) NULL     DEFAULT NULL COMMENT '该字段最新的值',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '元数据变更记录表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for metadata_column
-- ----------------------------
DROP TABLE IF EXISTS `metadata_column`;
CREATE TABLE `metadata_column`
(
    `id`              varchar(64)  NOT NULL COMMENT '主键ID',
    `source_id`       varchar(255) NULL DEFAULT NULL COMMENT '所属数据源',
    `table_id`        varchar(255) NULL DEFAULT NULL COMMENT '所属数据表',
    `column_name`     varchar(255) NULL DEFAULT NULL COMMENT '字段名称',
    `column_comment`  varchar(255) NULL DEFAULT NULL COMMENT '字段注释',
    `column_key`      varchar(255) NULL DEFAULT NULL COMMENT '字段是否主键(1是0否)',
    `column_nullable` varchar(255) NULL DEFAULT NULL COMMENT '字段是否允许为空(1是0否)',
    `column_position` int          NULL DEFAULT NULL COMMENT '字段序号',
    `data_type`       varchar(255) NULL DEFAULT NULL COMMENT '数据类型',
    `data_length`     varchar(255) NULL DEFAULT NULL COMMENT '数据长度',
    `data_precision`  varchar(255) NULL DEFAULT NULL COMMENT '数据精度',
    `data_scale`      varchar(255) NULL DEFAULT NULL COMMENT '数据小数位',
    `data_default`    varchar(255) NULL DEFAULT NULL COMMENT '数据默认值',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '元数据信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of metadata_column
-- ----------------------------
INSERT INTO `metadata_column`
VALUES ('1336479262852632577', '1336474987430793217', '1336479261791473665', 'id', '主键', '1', '0', 1, 'varchar', '50',
        NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479263477583874', '1336474987430793217', '1336479261791473665', 'patient_name', '患者姓名', '0', '1', 2,
        'varchar', '255', NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479264106729474', '1336474987430793217', '1336479261791473665', 'patient_sex', '患者性别（1男2女）', '0',
        '1', 3, 'varchar', '255', NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479265583124482', '1336474987430793217', '1336479264639406082', 'id', '主键', '1', '0', 1, 'varchar', '50',
        NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479266149355521', '1336474987430793217', '1336479264639406082', 'part_name', '部位名称', '0', '1', 2,
        'varchar', '255', NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479267583807489', '1336474987430793217', '1336479266728169473', 'id', '主键', '1', '0', 1, 'varchar', '50',
        NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479268242313218', '1336474987430793217', '1336479266728169473', 'part_id', '所属部位', '0', '1', 2,
        'varchar', '50', NULL, NULL, NULL);
INSERT INTO `metadata_column`
VALUES ('1336479268821127170', '1336474987430793217', '1336479266728169473', 'type_name', '症状名称', '0', '1', 3,
        'varchar', '255', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for metadata_source
-- ----------------------------
DROP TABLE IF EXISTS `metadata_source`;
CREATE TABLE `metadata_source`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `creator`     varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_dept` varchar(64) not NULL DEFAULT '1' COMMENT '创建人所属部门',
    `updater`     varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
    `version`     bigint(20)  NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
    `db_type`     tinyint     NULL     DEFAULT NULL COMMENT '数据源类型',
    `source_name` varchar(64) NULL     DEFAULT NULL COMMENT '数据源名称',
    `is_sync`     char(1)     NULL     DEFAULT NULL COMMENT '元数据同步（0否，1是）',
    `db_schema`   json        NULL COMMENT '数据源连接信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '数据源信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of metadata_source
-- ----------------------------
INSERT INTO `metadata_source`
VALUES ('1240185865539600385', '1214835832967581698', '2020-03-19 03:58:47', '1197789917762031617',
        '1214835832967581698', '2020-12-09 21:51:01', 0, 1, 1, 'mysql数据库', '0', '{
    \"sid\": null,
    \"host\": \"localhost\",
    \"port\": 3306,
    \"dbName\": \"foodmart2\",
    \"password\": \"1234@abcd\",
    \"username\": \"root\"
  }');
INSERT INTO `metadata_source`
VALUES ('1336474987430793217', '1214835832967581698', '2020-12-09 21:57:22', '1197789917762031617',
        '1214835832967581698', '2020-12-09 21:57:22', 0, 1, 1, 'robot数据库', '2', '{
    \"sid\": null,
    \"host\": \"localhost\",
    \"port\": 3306,
    \"dbName\": \"robot\",
    \"password\": \"1234@abcd\",
    \"username\": \"root\"
  }');

-- ----------------------------
-- Table structure for metadata_table
-- ----------------------------
DROP TABLE IF EXISTS `metadata_table`;
CREATE TABLE `metadata_table`
(
    `id`            varchar(64)  NOT NULL COMMENT '主键ID',
    `source_id`     varchar(255) NULL DEFAULT NULL COMMENT '所属数据源',
    `table_name`    varchar(255) NULL DEFAULT NULL COMMENT '表名',
    `table_comment` varchar(255) NULL DEFAULT NULL COMMENT '表注释',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '数据库表信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of metadata_table
-- ----------------------------
INSERT INTO `metadata_table`
VALUES ('1336479261791473665', '1336474987430793217', 'robot_patient', '患者表');
INSERT INTO `metadata_table`
VALUES ('1336479264639406082', '1336474987430793217', 'robot_symptom_part', '部位表');
INSERT INTO `metadata_table`
VALUES ('1336479266728169473', '1336474987430793217', 'robot_symptom_type', '症状表');
