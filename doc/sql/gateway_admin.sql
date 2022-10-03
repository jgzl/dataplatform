-- MySQL dump 10.13  Distrib 5.7.39, for Linux (x86_64)
--
-- Host: 192.168.192.154    Database: gateway_admin
-- ------------------------------------------------------
-- Server version	5.7.39-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `gateway_admin`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `gateway_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `gateway_admin`;

--
-- Table structure for table `gateway_access_conf`
--

DROP TABLE IF EXISTS `gateway_access_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gateway_access_conf` (
                                       `id` bigint(20) NOT NULL,
                                       `api_key` varchar(64) DEFAULT NULL,
                                       `api_secret` varchar(64) DEFAULT NULL,
                                       `system` varchar(64) DEFAULT NULL,
                                       `status` char(1) DEFAULT NULL,
                                       `remark` longtext,
                                       `create_time` datetime DEFAULT NULL,
                                       `update_time` datetime DEFAULT NULL,
                                       `creator` varchar(64) NOT NULL DEFAULT '1',
                                       `updater` varchar(64) NOT NULL DEFAULT '1',
                                       `deleted` bigint(20) NOT NULL DEFAULT '0',
                                       `version` bigint(20) NOT NULL DEFAULT '1',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关访问';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_access_conf`
--

LOCK TABLES `gateway_access_conf` WRITE;
/*!40000 ALTER TABLE `gateway_access_conf` DISABLE KEYS */;
INSERT INTO `gateway_access_conf` VALUES (1,'testApiKey','testApiSecret','testApi','0','测试','2022-06-04 01:27:53','2022-06-04 01:27:27','1','1',0,1);
/*!40000 ALTER TABLE `gateway_access_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateway_log`
--

DROP TABLE IF EXISTS `gateway_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gateway_log` (
                               `id` varchar(64) NOT NULL,
                               `source_service` varchar(64) DEFAULT NULL,
                               `api_key` varchar(64) DEFAULT NULL,
                               `api_secret` varchar(64) DEFAULT NULL,
                               `environment` varchar(64) DEFAULT NULL,
                               `target_service` varchar(64) DEFAULT NULL,
                               `schema` varchar(10) DEFAULT NULL,
                               `request_path` longtext,
                               `request_path_and_query` longtext,
                               `request_method` varchar(10) DEFAULT NULL,
                               `request_header` longtext,
                               `request_body` longtext,
                               `request_source_ip` varchar(128) DEFAULT NULL,
                               `response_body` longtext,
                               `http_status` varchar(10) DEFAULT NULL,
                               `execute_time` bigint(20) DEFAULT NULL,
                               `request_time` datetime DEFAULT NULL,
                               `response_time` datetime DEFAULT NULL,
                               `error_msg` longtext,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_log`
--

LOCK TABLES `gateway_log` WRITE;
/*!40000 ALTER TABLE `gateway_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `gateway_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateway_route_conf`
--

DROP TABLE IF EXISTS `gateway_route_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gateway_route_conf` (
                                      `id` bigint(20) NOT NULL,
                                      `route_name` varchar(30) DEFAULT NULL,
                                      `route_id` varchar(30) DEFAULT NULL,
                                      `predicates` longtext,
                                      `filters` longtext,
                                      `uri` longtext,
                                      `sort` tinyint(4) DEFAULT NULL,
                                      `metadata` longtext,
                                      `create_time` datetime DEFAULT NULL,
                                      `update_time` datetime DEFAULT NULL,
                                      `creator` varchar(64) NOT NULL DEFAULT '1',
                                      `updater` varchar(64) NOT NULL DEFAULT '1',
                                      `deleted` bigint(20) NOT NULL DEFAULT '0',
                                      `version` bigint(20) NOT NULL DEFAULT '1',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关路由';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_route_conf`
--

LOCK TABLES `gateway_route_conf` WRITE;
/*!40000 ALTER TABLE `gateway_route_conf` DISABLE KEYS */;
INSERT INTO `gateway_route_conf` VALUES (1,'监控管理','monitor-center','[{\"args\": {\"_genkey_0\": \"/monitor-center/**\"}, \"name\": \"Path\"}]','[]','lb://monitor-center',0,'{\"version\": \"1\"}','2021-12-27 02:43:25','2022-01-06 02:40:46','1','1',0,1),(2,'百度','baidu','[{\"args\": {\"_genkey_0\": \"/baidu/**\"}, \"name\": \"Path\"}]','[]','https://www.baidu.com',0,'{\"version\": \"1\"}','2022-06-03 08:39:53','2022-07-03 05:55:04','1','1',0,1);
/*!40000 ALTER TABLE `gateway_route_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept` (
                            `id` bigint(20) NOT NULL DEFAULT '0',
                            `name` varchar(50) DEFAULT NULL,
                            `sort` tinyint(4) DEFAULT NULL,
                            `parent_id` bigint(20) DEFAULT NULL,
                            `status` tinyint(3) DEFAULT NULL,
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `creator` varchar(64) NOT NULL DEFAULT '1',
                            `updater` varchar(64) NOT NULL DEFAULT '1',
                            `deleted` bigint(20) NOT NULL DEFAULT '0',
                            `version` bigint(20) NOT NULL DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (1,'山东',1,-1,1,'2018-01-22 11:00:23','2021-12-31 06:33:51','1','1',0,1),(2,'沙县国际',2,-1,1,'2018-01-22 11:00:38','2021-12-31 06:33:51','1','1',0,1),(3,'潍坊',3,1,1,'2018-01-22 11:00:44','2021-12-31 06:33:51','1','1',0,1),(4,'高新',4,3,1,'2018-01-22 11:00:52','2021-12-31 06:33:51','1','1',0,1),(5,'院校',5,4,1,'2018-01-22 11:00:57','2021-12-31 06:33:51','1','1',0,1),(7,'山东沙县',7,2,1,'2018-01-22 11:01:57','2021-12-31 06:33:51','1','1',0,1),(8,'潍坊沙县',8,7,1,'2018-01-22 11:02:03','2021-12-31 06:33:51','1','1',0,1);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_relation`
--

DROP TABLE IF EXISTS `sys_dept_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept_relation` (
                                     `ancestor` bigint(20) DEFAULT NULL,
                                     `descendant` bigint(20) DEFAULT NULL,
                                     `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                     `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                     `creator` varchar(64) NOT NULL DEFAULT '1',
                                     `updater` varchar(64) NOT NULL DEFAULT '1',
                                     `deleted` bigint(20) NOT NULL DEFAULT '0',
                                     `version` bigint(20) NOT NULL DEFAULT '1',
                                     `id` bigint(20) NOT NULL COMMENT 'id',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门联系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_relation`
--

LOCK TABLES `sys_dept_relation` WRITE;
/*!40000 ALTER TABLE `sys_dept_relation` DISABLE KEYS */;
INSERT INTO `sys_dept_relation` VALUES (1,1,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,11),(1,3,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,13),(1,4,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,14),(1,5,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,15),(1,9,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,19),(2,2,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,22),(2,7,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,27),(2,8,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,28),(3,3,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,33),(3,4,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,34),(3,5,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,35),(4,4,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,44),(4,5,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,45),(5,5,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,55),(7,7,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,77),(7,8,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,78),(8,8,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,88),(9,9,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,99),(1,10,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,110),(2,11,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,211),(2,12,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,212),(7,11,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,711),(7,12,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,712),(10,10,'2022-09-19 17:01:05','2022-09-19 17:01:06','1','1',0,1,1010);
/*!40000 ALTER TABLE `sys_dept_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_error_code`
--

DROP TABLE IF EXISTS `sys_error_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_error_code` (
                                  `id` bigint(20) NOT NULL,
                                  `type` tinyint(3) DEFAULT NULL,
                                  `application_name` varchar(50) DEFAULT NULL,
                                  `code` int(11) DEFAULT NULL,
                                  `message` varchar(512) DEFAULT NULL,
                                  `memo` varchar(512) DEFAULT NULL,
                                  `creator` varchar(64) DEFAULT '1',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                  `updater` varchar(64) DEFAULT '1',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                  `deleted` char(1) DEFAULT '0',
                                  `version` bigint(20) NOT NULL DEFAULT '1',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错误码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_error_code`
--

LOCK TABLES `sys_error_code` WRITE;
/*!40000 ALTER TABLE `sys_error_code` DISABLE KEYS */;
INSERT INTO `sys_error_code` VALUES (1576564617892253697,1,'gateway-admin-server',1002000000,'登录失败，账号密码不正确',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564618320072705,1,'gateway-admin-server',1002000001,'登录失败，账号被禁用',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564618768863233,1,'gateway-admin-server',1002000002,'登录失败',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564619217653762,1,'gateway-admin-server',1002000003,'验证码不存在',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564619595141121,1,'gateway-admin-server',1002000004,'验证码不正确',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564620102651906,1,'gateway-admin-server',1002000005,'未绑定账号，需要进行绑定',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564620555636737,1,'gateway-admin-server',1002000006,'Token 已经过期',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564620979261442,1,'gateway-admin-server',1002001000,'已经存在该名字的菜单',NULL,'1','2022-10-02 21:27:51','1','2022-10-02 21:27:51','0',1),(1576564621390303234,1,'gateway-admin-server',1002001001,'父菜单不存在',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564621839093762,1,'gateway-admin-server',1002001002,'不能设置自己为父菜单',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564622241746945,1,'gateway-admin-server',1002001003,'菜单不存在',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564622610845697,1,'gateway-admin-server',1002001004,'存在子菜单，无法删除',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564623055441922,1,'gateway-admin-server',1002001005,'父菜单的类型必须是目录或者菜单',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564623504232449,1,'gateway-admin-server',1002002000,'角色不存在',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564623948828673,1,'gateway-admin-server',1002002001,'已经存在名为【{}】的角色',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564624393424897,1,'gateway-admin-server',1002002002,'已经存在编码为【{}】的角色',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564624775106561,1,'gateway-admin-server',1002002003,'不能操作类型为系统内置的角色',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564625223897089,1,'gateway-admin-server',1002002004,'名字为【{}】的角色已被禁用',NULL,'1','2022-10-02 21:27:52','1','2022-10-02 21:27:52','0',1),(1576564625601384449,1,'gateway-admin-server',1002002005,'编码【{}】不能使用',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564626176004097,1,'gateway-admin-server',1002003000,'用户账号已经存在',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564626628988929,1,'gateway-admin-server',1002003001,'手机号已经存在',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564627069390850,1,'gateway-admin-server',1002003002,'邮箱已经存在',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564627576901633,1,'gateway-admin-server',1002003003,'用户不存在',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564627954388993,1,'gateway-admin-server',1002003004,'导入用户数据不能为空！',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564628398985218,1,'gateway-admin-server',1002003005,'用户密码校验失败',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564628784861185,1,'gateway-admin-server',1002003006,'名字为【{}】的用户已被禁用',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564629250428929,1,'gateway-admin-server',1002003008,'创建用户失败，原因：超过租户最大租户配额({})！',NULL,'1','2022-10-02 21:27:53','1','2022-10-02 21:27:53','0',1),(1576564629690830850,1,'gateway-admin-server',1002004000,'已经存在该名字的部门',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564630072512513,1,'gateway-admin-server',1002004001,'父级部门不存在',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564630521303042,1,'gateway-admin-server',1002004002,'当前部门不存在',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564630932344833,1,'gateway-admin-server',1002004003,'存在子部门，无法删除',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564631301443585,1,'gateway-admin-server',1002004004,'不能设置自己为父部门',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564631683125249,1,'gateway-admin-server',1002004005,'部门中存在员工，无法删除',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564632131915778,1,'gateway-admin-server',1002004006,'部门不处于开启状态，不允许选择',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564632513597442,1,'gateway-admin-server',1002004007,'不能设置自己的子部门为父部门',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564632958193665,1,'gateway-admin-server',1002005000,'当前岗位不存在',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564633339875329,1,'gateway-admin-server',1002005001,'岗位({}) 不处于开启状态，不允许选择',NULL,'1','2022-10-02 21:27:54','1','2022-10-02 21:27:54','0',1),(1576564633788665857,1,'gateway-admin-server',1002005002,'已经存在该名字的岗位',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564634237456386,1,'gateway-admin-server',1002005003,'已经存在该标识的岗位',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564634623332353,1,'gateway-admin-server',1002006001,'当前字典类型不存在',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564635067928577,1,'gateway-admin-server',1002006002,'字典类型不处于开启状态，不允许选择',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564635516719106,1,'gateway-admin-server',1002006003,'已经存在该名字的字典类型',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564635898400769,1,'gateway-admin-server',1002006004,'已经存在该类型的字典类型',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564636347191297,1,'gateway-admin-server',1002006005,'无法删除，该字典类型还有字典数据',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564636787593218,1,'gateway-admin-server',1002007001,'当前字典数据不存在',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564637236383745,1,'gateway-admin-server',1002007002,'字典数据({})不处于开启状态，不允许选择',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564637643231234,1,'gateway-admin-server',1002007003,'已经存在该值的字典数据',NULL,'1','2022-10-02 21:27:55','1','2022-10-02 21:27:55','0',1),(1576564638133964801,1,'gateway-admin-server',1002008001,'当前通知公告不存在',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564638515646465,1,'gateway-admin-server',1002011000,'短信渠道不存在',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564638968631298,1,'gateway-admin-server',1002011001,'短信渠道不处于开启状态，不允许选择',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564639417421826,1,'gateway-admin-server',1002011002,'无法删除，该短信渠道还有短信模板',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564639803297793,1,'gateway-admin-server',1002012000,'短信模板不存在',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564640243699713,1,'gateway-admin-server',1002012001,'已经存在编码为【{}】的短信模板',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564640625381378,1,'gateway-admin-server',1002013000,'手机号不存在',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564641078366210,1,'gateway-admin-server',1002013001,'模板参数({})缺失',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564641464242178,1,'gateway-admin-server',1002013002,'短信模板不存在',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564641854312449,1,'gateway-admin-server',1002014000,'验证码不存在',NULL,'1','2022-10-02 21:27:56','1','2022-10-02 21:27:56','0',1),(1576564642307297281,1,'gateway-admin-server',1002014001,'验证码已过期',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564642697367554,1,'gateway-admin-server',1002014002,'验证码已使用',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564643154546690,1,'gateway-admin-server',1002014003,'验证码不正确',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564643536228353,1,'gateway-admin-server',1002014004,'超过每日短信发送数量',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564643980824577,1,'gateway-admin-server',1002014005,'短信发送过于频率',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564644366700546,1,'gateway-admin-server',1002014006,'手机号已被使用',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564644811296770,1,'gateway-admin-server',1002014007,'验证码未被使用',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564645197172738,1,'gateway-admin-server',1002015000,'租户不存在',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564645645963266,1,'gateway-admin-server',1002015001,'名字为【{}】的租户已被禁用',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564646023450625,1,'gateway-admin-server',1002015002,'名字为【{}】的租户已过期',NULL,'1','2022-10-02 21:27:57','1','2022-10-02 21:27:57','0',1),(1576564646526767106,1,'gateway-admin-server',1002015003,'系统租户不能进行修改、删除等操作！',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564646912643073,1,'gateway-admin-server',1002016000,'租户套餐不存在',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564647361433601,1,'gateway-admin-server',1002016001,'租户正在使用该套餐，请给租户重新设置套餐后再尝试删除',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564647747309570,1,'gateway-admin-server',1002016002,'名字为【{}】的租户套餐已被禁用',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564648196100097,1,'gateway-admin-server',1002017000,'错误码不存在',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564648661667841,1,'gateway-admin-server',1002017001,'已经存在编码为【{}】的错误码',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564649093681153,1,'gateway-admin-server',1002018000,'社交授权失败，原因是：{}',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564649479557121,1,'gateway-admin-server',1002018001,'社交解绑失败，非当前用户绑定',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564649928347649,1,'gateway-admin-server',1002018002,'社交授权失败，找不到对应的用户',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564650305835010,1,'gateway-admin-server',1002020001,'网关请求来源apiKey不合法',NULL,'1','2022-10-02 21:27:58','1','2022-10-02 21:27:58','0',1),(1576564650750431234,1,'gateway-admin-server',1002020002,'网关请求来源apiSecret不合法',NULL,'1','2022-10-02 21:27:59','1','2022-10-02 21:27:59','0',1),(1576564651132112897,1,'gateway-admin-server',1002020003,'网关请求来源system不合法',NULL,'1','2022-10-02 21:27:59','1','2022-10-02 21:27:59','0',1),(1576564651580903426,1,'gateway-admin-server',1002020004,'网关请求来源apiKey/apiSecret已禁用',NULL,'1','2022-10-02 21:27:59','1','2022-10-02 21:27:59','0',1),(1576564651966779394,1,'gateway-admin-server',1002020005,'网关请求来源apiKey/apiSecret/system不允许为空',NULL,'1','2022-10-02 21:27:59','1','2022-10-02 21:27:59','0',1);
/*!40000 ALTER TABLE `sys_error_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
                            `id` bigint(20) NOT NULL DEFAULT '0',
                            `name` varchar(32) DEFAULT NULL,
                            `permission` varchar(32) DEFAULT NULL,
                            `path` varchar(128) DEFAULT NULL,
                            `parent_id` bigint(20) DEFAULT NULL,
                            `icon` varchar(32) DEFAULT NULL,
                            `sort` tinyint(4) DEFAULT NULL,
                            `keep_alive` char(1) DEFAULT NULL,
                            `type` char(1) DEFAULT NULL,
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `component` varchar(128) DEFAULT NULL COMMENT '视图',
                            `tag` varchar(64) DEFAULT NULL COMMENT '标签',
                            `creator` varchar(64) NOT NULL DEFAULT '1',
                            `updater` varchar(64) NOT NULL DEFAULT '1',
                            `deleted` bigint(20) NOT NULL DEFAULT '0',
                            `version` bigint(20) NOT NULL DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (0,'首页',NULL,'/home',-1,'el-icon-eleme-filled',0,'0','0','2018-09-28 08:29:53','2021-12-27 15:13:30',NULL,NULL,'1','1',0,1),(100,'工作台',NULL,'/dashboard',0,'el-icon-menu',1,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','home',NULL,'1','1',0,1),(200,'用户中心',NULL,'/userCenter',0,'el-icon-user',2,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','userCenter',NULL,'1','1',0,1),(1000,'系统管理',NULL,'/system',-1,'el-icon-tools',10,'0','0','2017-11-02 22:24:37','2017-11-02 22:24:37',NULL,NULL,'1','1',0,1),(1100,'用户管理',NULL,'/system/user',1000,'el-icon-user-filled',1,'0','0','2017-11-02 22:24:37','2021-12-28 20:15:53','system/user',NULL,'1','1',0,1),(1101,'用户新增','sys_user_add',NULL,1100,NULL,1,'0','1','2017-11-08 09:52:09','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1102,'用户修改','sys_user_edit',NULL,1100,NULL,1,'0','1','2017-11-08 09:52:48','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1103,'用户删除','sys_user_del',NULL,1100,NULL,1,'0','1','2017-11-08 09:54:01','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1104,'导入导出','sys_user_export',NULL,1100,NULL,1,'0','1','2017-11-08 09:54:01','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1200,'菜单管理',NULL,'/system/menu',1000,'el-icon-fold',2,'0','0','2017-11-08 09:57:27','2021-12-28 20:14:22','system/menu',NULL,'1','1',0,1),(1201,'菜单新增','sys_menu_add',NULL,1200,NULL,1,'0','1','2017-11-08 10:15:53','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1202,'菜单修改','sys_menu_edit',NULL,1200,NULL,1,'0','1','2017-11-08 10:16:23','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1203,'菜单删除','sys_menu_del',NULL,1200,NULL,1,'0','1','2017-11-08 10:16:43','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1300,'角色管理',NULL,'/system/role',1000,'el-icon-notebook',3,'0','0','2017-11-08 10:13:37','2021-12-28 20:14:19','system/role',NULL,'1','1',0,1),(1301,'角色新增','sys_role_add',NULL,1300,NULL,1,'0','1','2017-11-08 10:14:18','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1302,'角色修改','sys_role_edit',NULL,1300,NULL,1,'0','1','2017-11-08 10:14:41','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1303,'角色删除','sys_role_del',NULL,1300,NULL,1,'0','1','2017-11-08 10:14:59','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1304,'分配权限','sys_role_perm',NULL,1300,NULL,1,'0','1','2018-04-20 07:22:55','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1400,'部门管理',NULL,'/system/dept',1000,'sc-icon-organization',4,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/dept',NULL,'1','1',0,1),(1401,'部门新增','sys_dept_add',NULL,1400,NULL,1,'0','1','2018-01-20 14:56:59','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1402,'部门修改','sys_dept_edit',NULL,1400,NULL,1,'0','1','2018-01-20 14:56:59','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1403,'部门删除','sys_dept_del',NULL,1400,NULL,1,'0','1','2018-01-20 14:57:28','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(1500,'字典管理',NULL,'/system/dic',1000,'el-icon-document',5,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/dic',NULL,'1','1',0,1),(1600,'应用管理',NULL,'/system/client',1000,'el-icon-help-filled',6,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/client',NULL,'1','1',0,1),(1700,'表格列管理',NULL,'/system/table',1000,'el-icon-scale-to-original',7,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/table',NULL,'1','1',0,1),(1800,'系统设置',NULL,'/system/setting',1000,'el-icon-tools',10,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/setting',NULL,'1','1',0,1),(1900,'系统日志',NULL,'/system/log',1000,'el-icon-warning',9,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/log',NULL,'1','1',0,1),(2000,'网关管理',NULL,'/gateway',-1,'el-icon-coin',20,'0','0','2018-09-04 05:58:41','2022-09-23 00:21:19','gateway',NULL,'1','1',0,1),(2100,'网关日志',NULL,'/gateway/log',2000,'el-icon-edit-pen',1,'0','0','2018-09-04 05:58:41','2022-09-23 00:19:37','gateway/log',NULL,'1','1',0,1),(2101,'网关日志新增','gateway_log_add',NULL,2100,NULL,1,'0','1','2018-01-20 14:56:16','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2102,'网关日志修改','gateway_log_edit',NULL,2100,NULL,1,'0','1','2018-01-20 14:56:59','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2103,'网关日志删除','gateway_log_del',NULL,2100,NULL,1,'0','1','2018-01-20 14:57:28','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2200,'网关路由',NULL,'/gateway/route',2000,'el-icon-platform',2,'0','0','2018-09-04 05:58:41','2022-09-23 00:20:39','gateway/route',NULL,'1','1',0,1),(2201,'网关路由新增','gateway_route_add',NULL,2200,NULL,1,'0','1','2018-01-20 14:56:16','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2202,'网关路由修改','gateway_route_edit',NULL,2200,NULL,1,'0','1','2018-01-20 14:56:59','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2203,'网关路由删除','gateway_route_del',NULL,2200,NULL,1,'0','1','2018-01-20 14:57:28','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2204,'网关路由拷贝','gateway_route_copy',NULL,2200,NULL,1,'0','1','2018-01-20 14:57:28','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2300,'网关访问',NULL,'/gateway/access',2000,'el-icon-avatar',3,'0','0','2018-09-04 05:58:41','2022-09-23 00:20:50','gateway/access',NULL,'1','1',0,1),(2301,'网关访问新增','gateway_access_add',NULL,2300,NULL,1,'0','1','2018-01-20 14:56:16','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2302,'网关访问修改','gateway_access_edit',NULL,2300,NULL,1,'0','1','2018-01-20 14:56:59','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2303,'网关访问删除','gateway_access_del',NULL,2300,NULL,1,'0','1','2018-01-20 14:57:28','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2304,'网关访问状态启用禁用','gateway_access_status',NULL,2300,NULL,1,'0','1','2018-01-20 14:57:28','2021-05-25 03:12:55',NULL,NULL,'1','1',0,1),(2400,'元数据管理',NULL,'/gateway/metadata',2000,'el-icon-collection-tag',5,'0','0','2022-09-19 17:01:58','2022-09-23 00:21:03','gateway/metadata',NULL,'1','1',0,1),(2401,'元数据新增','gateway_metadata_add',NULL,2400,NULL,1,'0','1','2022-09-19 17:01:58','2022-09-19 17:01:58',NULL,NULL,'1','1',0,1),(2402,'元数据修改','gateway_metadata_edit',NULL,2400,NULL,1,'0','1','2022-09-19 17:01:58','2022-09-19 17:01:58',NULL,NULL,'1','1',0,1),(2403,'元数据删除','gateway_metadata_del',NULL,2400,NULL,1,'0','1','2022-09-19 17:01:59','2022-09-19 17:01:59',NULL,NULL,'1','1',0,1),(3000,'关于',NULL,'/other/about',-1,'el-icon-info-filled',30,'0','0','2018-09-04 05:58:41','2022-09-23 00:24:30','other/about',NULL,'1','1',0,1),(11100,'计划任务',NULL,'/system/task',1000,'el-icon-alarm-clock',8,'0','0','2018-01-20 13:17:19','2021-12-28 20:14:16','system/task',NULL,'1','1',0,1),(11200,'错误码管理',NULL,'/system/errorcode',1000,'el-icon-user-filled',5,'0','0','2022-09-19 17:01:55','2022-09-19 17:01:55','system/errorcode',NULL,'1','1',0,1),(11201,'错误码新增','sys_error_code_add',NULL,11200,NULL,1,'0','1','2022-09-19 17:01:55','2022-09-19 17:01:55',NULL,NULL,'1','1',0,1),(11202,'错误码修改','sys_error_code_edit',NULL,11200,NULL,1,'0','1','2022-09-19 17:01:56','2022-09-19 17:01:56',NULL,NULL,'1','1',0,1),(11203,'错误码删除','sys_error_code_del',NULL,11200,NULL,1,'0','1','2022-09-19 17:01:56','2022-09-19 17:01:56',NULL,NULL,'1','1',0,1);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oauth_client_details`
--

DROP TABLE IF EXISTS `sys_oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_oauth_client_details` (
                                            `id` bigint(20) NOT NULL COMMENT '主键',
                                            `client_id` varchar(32) NOT NULL COMMENT '客户端ID',
                                            `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源列表',
                                            `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密钥',
                                            `scope` varchar(256) DEFAULT NULL COMMENT '域',
                                            `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '认证类型',
                                            `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '重定向地址',
                                            `authorities` varchar(256) DEFAULT NULL COMMENT '角色列表',
                                            `access_token_validity` int(11) DEFAULT NULL COMMENT 'token 有效期',
                                            `refresh_token_validity` int(11) DEFAULT NULL COMMENT '刷新令牌有效期',
                                            `additional_information` varchar(4096) DEFAULT NULL COMMENT '令牌扩展字段JSON',
                                            `autoapprove` varchar(256) DEFAULT NULL COMMENT '是否自动放行',
                                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                            `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                            `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                            `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                            PRIMARY KEY (`id`),
                                            UNIQUE KEY `sys_oauth_client_details_client_id_IDX` (`client_id`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='终端信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oauth_client_details`
--

LOCK TABLES `sys_oauth_client_details` WRITE;
/*!40000 ALTER TABLE `sys_oauth_client_details` DISABLE KEYS */;
INSERT INTO `sys_oauth_client_details` VALUES (1,'app',NULL,'app','server','app,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',NULL,NULL,'1','1',0,1),(2,'daemon',NULL,'daemon','server','password,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',NULL,NULL,'1','1',0,1),(3,'gen',NULL,'gen','server','password,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',NULL,NULL,'1','1',0,1),(4,'gateway-admin',NULL,'gateway-admin','server','password,app,refresh_token,authorization_code,client_credentials','http://localhost:4040/sso1/login,http://localhost:4041/sso1/login',NULL,NULL,NULL,NULL,'true',NULL,NULL,'1','1',0,1),(5,'test',NULL,'test','server','password,app,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',NULL,NULL,'1','1',0,1);
/*!40000 ALTER TABLE `sys_oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
                            `id` bigint(20) NOT NULL DEFAULT '0',
                            `role_name` varchar(64) DEFAULT NULL,
                            `role_code` varchar(64) DEFAULT NULL,
                            `role_desc` varchar(255) DEFAULT NULL,
                            `ds_type` char(1) DEFAULT NULL,
                            `ds_scope` varchar(255) DEFAULT NULL,
                            `create_time` datetime NOT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `creator` varchar(64) NOT NULL DEFAULT '1',
                            `updater` varchar(64) NOT NULL DEFAULT '1',
                            `deleted` bigint(20) NOT NULL DEFAULT '0',
                            `version` bigint(20) NOT NULL DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','ROLE_admin','超级管理员','0','2','2017-10-29 07:45:51','2021-12-31 06:19:46','1','1',0,1),(2,'普通用户','ROLE_normal','普通用户','0','2','2021-12-31 06:19:20','2021-12-31 06:20:27','1','1',0,1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
                                 `role_id` int(11) DEFAULT NULL,
                                 `menu_id` int(11) DEFAULT NULL,
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 `creator` varchar(64) NOT NULL DEFAULT '1',
                                 `updater` varchar(64) NOT NULL DEFAULT '1',
                                 `deleted` bigint(20) NOT NULL DEFAULT '0',
                                 `version` bigint(20) NOT NULL DEFAULT '1',
                                 `id` bigint(20) NOT NULL COMMENT 'id',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,0,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,10),(3,0,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,30),(1,100,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,1100),(1,200,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,1200),(3,100,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,3100),(3,200,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,3200),(1,1000,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11000),(1,1100,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11100),(1,1101,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11101),(1,1102,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11102),(1,1103,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11103),(1,1104,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11104),(1,1200,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11200),(1,1201,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11201),(1,1202,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11202),(1,1203,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11203),(1,1300,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11300),(1,1301,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11301),(1,1302,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11302),(1,1303,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11303),(1,1304,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11304),(1,1400,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11400),(1,1401,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11401),(1,1402,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11402),(1,1403,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11403),(1,1500,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11500),(1,1600,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11600),(1,1700,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11700),(1,1800,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11800),(1,1900,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,11900),(1,2000,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12000),(1,2100,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12100),(1,2101,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12101),(1,2102,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12102),(1,2103,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12103),(1,2200,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12200),(1,2201,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12201),(1,2202,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12202),(1,2203,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12203),(1,2204,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12204),(1,2300,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12300),(1,2301,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12301),(1,2302,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12302),(1,2303,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12303),(1,2304,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,12304),(1,2400,'2022-09-19 17:01:59','2022-09-19 17:01:59','1','1',0,1,12400),(1,2401,'2022-09-19 17:01:59','2022-09-19 17:01:59','1','1',0,1,12401),(1,2402,'2022-09-19 17:02:00','2022-09-19 17:02:00','1','1',0,1,12402),(1,2403,'2022-09-19 17:02:00','2022-09-19 17:02:00','1','1',0,1,12403),(1,3000,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,13000),(2,2200,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,22200),(2,2300,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,22300),(2,3000,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,23000),(3,3000,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,33000),(1,11100,'2022-09-19 17:01:07','2022-09-19 17:01:07','1','1',0,1,111100),(1,11200,'2022-09-19 17:01:56','2022-09-19 17:01:56','1','1',0,1,111200),(1,11201,'2022-09-19 17:01:57','2022-09-19 17:01:57','1','1',0,1,111201),(1,11202,'2022-09-19 17:01:57','2022-09-19 17:01:57','1','1',0,1,111202),(1,11203,'2022-09-19 17:01:57','2022-09-19 17:01:57','1','1',0,1,111203);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
                            `id` bigint(20) NOT NULL DEFAULT '0',
                            `user_name` varchar(64) DEFAULT NULL,
                            `nick_name` varchar(64) DEFAULT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `salt` varchar(255) DEFAULT NULL,
                            `gender` tinyint(4) DEFAULT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `mobile` varchar(20) DEFAULT NULL,
                            `avatar` varchar(255) DEFAULT NULL,
                            `last_login_time` datetime DEFAULT NULL,
                            `last_login_ip` varchar(64) DEFAULT NULL,
                            `dept_id` bigint(20) DEFAULT NULL,
                            `lock_flag` tinyint(4) DEFAULT NULL,
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `creator` varchar(64) NOT NULL DEFAULT '1',
                            `updater` varchar(64) NOT NULL DEFAULT '1',
                            `deleted` bigint(20) NOT NULL DEFAULT '0',
                            `version` bigint(20) NOT NULL DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','admin-nickname','$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C','',1,'li7hai26@gmail.com','17034642888','','2022-01-06 07:47:04','127.0.0.1',1,0,'2018-04-19 23:15:18','2022-07-21 14:03:01','1','1',0,1),(2,'editor','editor-nickname','$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C','',0,'li7hai26@outlook.com','17034642888','','2022-01-06 07:47:08','127.0.0.1',5,0,'2021-12-31 08:53:14','2022-01-06 10:40:33','1','1',0,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
                                 `user_id` bigint(20) DEFAULT NULL,
                                 `role_id` bigint(20) DEFAULT NULL,
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 `creator` varchar(64) NOT NULL DEFAULT '1',
                                 `updater` varchar(64) NOT NULL DEFAULT '1',
                                 `deleted` bigint(20) NOT NULL DEFAULT '0',
                                 `version` bigint(20) NOT NULL DEFAULT '1',
                                 `id` bigint(20) NOT NULL COMMENT 'id',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,11),(1,2,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,12),(2,2,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,22),(3,2,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,32),(9,1,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,91),(9,2,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,92),(12,2,'2022-09-19 17:01:06','2022-09-19 17:01:06','1','1',0,1,122);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-03 14:05:59
