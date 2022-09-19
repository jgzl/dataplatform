use gateway_admin;
/*
 Navicat Premium Data Transfer

 Source Server         : methodot
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : e538e60da76b.c.methodot.com:31810
 Source Schema         : gateway_admin

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 31/08/2022 10:14:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_access_conf
-- ----------------------------
DROP TABLE IF EXISTS `gateway_access_conf`;
CREATE TABLE `gateway_access_conf` (
  `id` bigint(20) DEFAULT NULL,
  `api_key` varchar(64) DEFAULT NULL,
  `api_secret` varchar(64) DEFAULT NULL,
  `system` varchar(64) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `remark` longtext,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gateway_access_conf
-- ----------------------------
BEGIN;
INSERT INTO `gateway_access_conf` (`id`, `api_key`, `api_secret`, `system`, `status`, `remark`, `create_time`, `update_time`, `del_flag`) VALUES (1, 'testApiKey', 'testApiSecret', 'testApi', '0', '测试', '2022-06-04 01:27:53', '2022-06-04 01:27:27', '0');
COMMIT;

-- ----------------------------
-- Table structure for gateway_log
-- ----------------------------
DROP TABLE IF EXISTS `gateway_log`;
CREATE TABLE `gateway_log` (
  `id` varchar(64) DEFAULT NULL,
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
  `error_msg` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gateway_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gateway_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_conf`;
CREATE TABLE `gateway_route_conf` (
  `id` bigint(20) DEFAULT NULL,
  `route_name` varchar(30) DEFAULT NULL,
  `route_id` varchar(30) DEFAULT NULL,
  `predicates` longtext,
  `filters` longtext,
  `uri` longtext,
  `sort` tinyint(4) DEFAULT NULL,
  `metadata` longtext,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gateway_route_conf
-- ----------------------------
BEGIN;
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `del_flag`) VALUES (1, '监控管理', 'monitor-center', '[{\"args\": {\"_genkey_0\": \"/monitor-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://monitor-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-01-06 02:40:46', '0');
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `del_flag`) VALUES (2, '百度', 'baidu', '[{\"args\": {\"_genkey_0\": \"/baidu/**\"}, \"name\": \"Path\"}]', '[]', 'https://www.baidu.com', 0, '{\"version\": \"1\"}', '2022-06-03 08:39:53', '2022-07-03 05:55:04', '0');
COMMIT;

-- ----------------------------
-- Table structure for magic_api_backup
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_backup`;
CREATE TABLE `magic_api_backup` (
  `id` varchar(32) NOT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `tag` varchar(32) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `content` mediumtext,
  `create_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of magic_api_backup
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for magic_api_file
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_file`;
CREATE TABLE `magic_api_file` (
  `file_path` varchar(512) DEFAULT NULL,
  `file_content` mediumtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of magic_api_file
-- ----------------------------
BEGIN;
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/网管管理服务/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/网管管理服务/group.json', '{\n  \"properties\" : { },\n  \"id\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\n  \"name\" : \"网管管理服务\",\n  \"type\" : \"api\",\n  \"parentId\" : \"0\",\n  \"path\" : \"/magic/api/gateway/admin\",\n  \"createTime\" : null,\n  \"updateTime\" : 1651505331355,\n  \"createBy\" : null,\n  \"updateBy\" : null,\n  \"paths\" : [ ],\n  \"options\" : [ ]\n}');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/网管管理服务/测试.ms', '{\n  \"properties\" : { },\n  \"id\" : \"62ee0d323ae946b8a9fcd08a11389fc1\",\n  \"script\" : null,\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\n  \"name\" : \"测试\",\n  \"createTime\" : null,\n  \"updateTime\" : 1657764977019,\n  \"lock\" : null,\n  \"createBy\" : null,\n  \"updateBy\" : null,\n  \"path\" : \"/test\",\n  \"method\" : \"GET\",\n  \"parameters\" : [ ],\n  \"options\" : [ ],\n  \"requestBody\" : \"\",\n  \"headers\" : [ ],\n  \"paths\" : [ ],\n  \"responseBody\" : null,\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : null\n}\n================================\nreturn \'Hello magic-api\'');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/api/网管管理服务/获取全部网关路由信息.ms', 'executeTime\"\": 26n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : {\n    \"name\" : \"\",\n    \"value\" : \"\",\n    \"description\" : \"\",\n    \"required\" : false,\n    \"dataType\" : \"Object\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : \"\",\n    \"error\" : \"\",\n    \"expression\" : \"\",\n    \"children\" : [ {\n      \"name\" : \"code\",\n      \"value\" : \"1\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"message\",\n      \"value\" : \"success\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"data\",\n      \"value\" : \"1\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"timestamp\",\n      \"value\" : \"1651505412773\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Long\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"executeTime\",\n      \"value\" : \"26\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    } ]\n  }\n}\n================================\ndb.table(\"gateway_route_conf\").page()');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/datasource/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('magic-api/function/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/datasource/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/function/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/api/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/api/测试/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/api/测试/group.json', '{\n  \"properties\" : { },\n  \"id\" : \"40ca8dd16bd141a0b927cfae323e9528\",\n  \"name\" : \"测试\",\n  \"type\" : \"api\",\n  \"parentId\" : \"0\",\n  \"path\" : \"/test\",\n  \"createTime\" : 1657765532990,\n  \"updateTime\" : null,\n  \"createBy\" : \"admin\",\n  \"updateBy\" : null,\n  \"paths\" : [ ],\n  \"options\" : [ ]\n}');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/api/测试/demo.ms', 'executeTime\"\": 54n}\",\n  \"description\" : null,\n  \"requestBodyDefinition\" : null,\n  \"responseBodyDefinition\" : {\n    \"name\" : \"\",\n    \"value\" : \"\",\n    \"description\" : \"\",\n    \"required\" : false,\n    \"dataType\" : \"Object\",\n    \"type\" : null,\n    \"defaultValue\" : null,\n    \"validateType\" : \"\",\n    \"error\" : \"\",\n    \"expression\" : \"\",\n    \"children\" : [ {\n      \"name\" : \"code\",\n      \"value\" : \"200\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"msg\",\n      \"value\" : \"success\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"data\",\n      \"value\" : \"Hello magic-api\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"String\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"timestamp\",\n      \"value\" : \"1657765557453\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Long\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"requestTime\",\n      \"value\" : \"1657765557399\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Long\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    }, {\n      \"name\" : \"executeTime\",\n      \"value\" : \"54\",\n      \"description\" : \"\",\n      \"required\" : false,\n      \"dataType\" : \"Integer\",\n      \"type\" : null,\n      \"defaultValue\" : null,\n      \"validateType\" : \"\",\n      \"error\" : \"\",\n      \"expression\" : \"\",\n      \"children\" : [ ]\n    } ]\n  }\n}\n================================\nreturn \'Hello magic-api\'');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `sort` tinyint(4) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (1, '山东', 1, -1, 1, '2018-01-22 11:00:23', '2021-12-31 06:33:51', '0');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (2, '沙县国际', 2, -1, 1, '2018-01-22 11:00:38', '2021-12-31 06:33:51', '0');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (3, '潍坊', 3, 1, 1, '2018-01-22 11:00:44', '2021-12-31 06:33:51', '0');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (4, '高新', 4, 3, 1, '2018-01-22 11:00:52', '2021-12-31 06:33:51', '0');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (5, '院校', 5, 4, 1, '2018-01-22 11:00:57', '2021-12-31 06:33:51', '0');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (6, '潍院', 6, 5, 1, '2018-01-22 11:01:06', '2021-12-31 06:33:51', '1');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (7, '山东沙县', 7, 2, 1, '2018-01-22 11:01:57', '2021-12-31 06:33:51', '0');
INSERT INTO `sys_dept` (`dept_id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `del_flag`) VALUES (8, '潍坊沙县', 8, 7, 1, '2018-01-22 11:02:03', '2021-12-31 06:33:51', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_relation`;
CREATE TABLE `sys_dept_relation` (
  `ancestor` bigint(20) DEFAULT NULL,
  `descendant` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dept_relation
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (1, 1);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (1, 3);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (1, 4);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (1, 5);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (2, 2);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (2, 7);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (2, 8);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (2, 11);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (2, 12);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (3, 3);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (3, 4);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (3, 5);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (4, 4);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (4, 5);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (5, 5);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (7, 7);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (7, 8);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (7, 11);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (7, 12);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (8, 8);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (1, 9);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (9, 9);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (1, 10);
INSERT INTO `sys_dept_relation` (`ancestor`, `descendant`) VALUES (10, 10);
COMMIT;

-- ----------------------------
-- Table structure for sys_error_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_code`;
CREATE TABLE `sys_error_code` (
  `id` bigint(20) DEFAULT NULL,
  `type` tinyint(3) DEFAULT NULL,
  `application_name` varchar(50) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `message` varchar(512) DEFAULT NULL,
  `memo` varchar(512) DEFAULT NULL,
  `creator` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `updater` varchar(64) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `deleted` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_error_code
-- ----------------------------
BEGIN;
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885482316619778, 1, 'gateway-admin-server', 1002000000, '登录失败，账号密码不正确', NULL, '-1', '2022-08-06 19:56:21', '-1', '2022-08-06 19:56:21', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885482887045121, 1, 'gateway-admin-server', 1002000001, '登录失败，账号被禁用', NULL, '-1', '2022-08-06 19:56:21', '-1', '2022-08-06 19:56:21', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885483591688194, 1, 'gateway-admin-server', 1002000002, '登录失败', NULL, '-1', '2022-08-06 19:56:21', '-1', '2022-08-06 19:56:21', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885484229222401, 1, 'gateway-admin-server', 1002000003, '验证码不存在', NULL, '-1', '2022-08-06 19:56:21', '-1', '2022-08-06 19:56:21', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885484929671170, 1, 'gateway-admin-server', 1002000004, '验证码不正确', NULL, '-1', '2022-08-06 19:56:21', '-1', '2022-08-06 19:56:21', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885485504290817, 1, 'gateway-admin-server', 1002000005, '未绑定账号，需要进行绑定', NULL, '-1', '2022-08-06 19:56:21', '-1', '2022-08-06 19:56:21', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885488515801089, 1, 'gateway-admin-server', 1002000006, 'Token 已经过期', NULL, '-1', '2022-08-06 19:56:22', '-1', '2022-08-06 19:56:22', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885489086226433, 1, 'gateway-admin-server', 1002001000, '已经存在该名字的菜单', NULL, '-1', '2022-08-06 19:56:22', '-1', '2022-08-06 19:56:22', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885489719566337, 1, 'gateway-admin-server', 1002001001, '父菜单不存在', NULL, '-1', '2022-08-06 19:56:22', '-1', '2022-08-06 19:56:22', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885490357100545, 1, 'gateway-admin-server', 1002001002, '不能设置自己为父菜单', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885490998829058, 1, 'gateway-admin-server', 1002001003, '菜单不存在', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885491640557570, 1, 'gateway-admin-server', 1002001004, '存在子菜单，无法删除', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885492341006337, 1, 'gateway-admin-server', 1002001005, '父菜单的类型必须是目录或者菜单', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885492986929154, 1, 'gateway-admin-server', 1002002000, '角色不存在', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885493628657665, 1, 'gateway-admin-server', 1002002001, '已经存在名为【{}】的角色', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885494211665921, 1, 'gateway-admin-server', 1002002002, '已经存在编码为【{}】的角色', NULL, '-1', '2022-08-06 19:56:23', '-1', '2022-08-06 19:56:23', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885494907920386, 1, 'gateway-admin-server', 1002002003, '不能操作类型为系统内置的角色', NULL, '-1', '2022-08-06 19:56:24', '-1', '2022-08-06 19:56:24', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885495553843202, 1, 'gateway-admin-server', 1002002004, '名字为【{}】的角色已被禁用', NULL, '-1', '2022-08-06 19:56:24', '-1', '2022-08-06 19:56:24', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885496124268545, 1, 'gateway-admin-server', 1002002005, '编码【{}】不能使用', NULL, '-1', '2022-08-06 19:56:24', '-1', '2022-08-06 19:56:24', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885496774385665, 1, 'gateway-admin-server', 1002003000, '用户账号已经存在', NULL, '-1', '2022-08-06 19:56:24', '-1', '2022-08-06 19:56:24', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885497344811010, 1, 'gateway-admin-server', 1002003001, '手机号已经存在', NULL, '-1', '2022-08-06 19:56:24', '-1', '2022-08-06 19:56:24', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885497986539521, 1, 'gateway-admin-server', 1002003002, '邮箱已经存在', NULL, '-1', '2022-08-06 19:56:24', '-1', '2022-08-06 19:56:24', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885499773313026, 1, 'gateway-admin-server', 1002003003, '用户不存在', NULL, '-1', '2022-08-06 19:56:25', '-1', '2022-08-06 19:56:25', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885500410847234, 1, 'gateway-admin-server', 1002003004, '导入用户数据不能为空！', NULL, '-1', '2022-08-06 19:56:25', '-1', '2022-08-06 19:56:25', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885501102907394, 1, 'gateway-admin-server', 1002003005, '用户密码校验失败', NULL, '-1', '2022-08-06 19:56:25', '-1', '2022-08-06 19:56:25', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885501681721345, 1, 'gateway-admin-server', 1002003006, '名字为【{}】的用户已被禁用', NULL, '-1', '2022-08-06 19:56:25', '-1', '2022-08-06 19:56:25', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885502260535297, 1, 'gateway-admin-server', 1002003008, '创建用户失败，原因：超过租户最大租户配额({})！', NULL, '-1', '2022-08-06 19:56:25', '-1', '2022-08-06 19:56:25', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885502843543554, 1, 'gateway-admin-server', 1002004000, '已经存在该名字的部门', NULL, '-1', '2022-08-06 19:56:25', '-1', '2022-08-06 19:56:25', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885503502049282, 1, 'gateway-admin-server', 1002004001, '父级部门不存在', NULL, '-1', '2022-08-06 19:56:26', '-1', '2022-08-06 19:56:26', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885504185720834, 1, 'gateway-admin-server', 1002004002, '当前部门不存在', NULL, '-1', '2022-08-06 19:56:26', '-1', '2022-08-06 19:56:26', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885504768729089, 1, 'gateway-admin-server', 1002004003, '存在子部门，无法删除', NULL, '-1', '2022-08-06 19:56:26', '-1', '2022-08-06 19:56:26', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885505469177858, 1, 'gateway-admin-server', 1002004004, '不能设置自己为父部门', NULL, '-1', '2022-08-06 19:56:26', '-1', '2022-08-06 19:56:26', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885506106712066, 1, 'gateway-admin-server', 1002004005, '部门中存在员工，无法删除', NULL, '-1', '2022-08-06 19:56:26', '-1', '2022-08-06 19:56:26', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885506807160834, 1, 'gateway-admin-server', 1002004006, '部门不处于开启状态，不允许选择', NULL, '-1', '2022-08-06 19:56:26', '-1', '2022-08-06 19:56:26', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885507507609601, 1, 'gateway-admin-server', 1002004007, '不能设置自己的子部门为父部门', NULL, '-1', '2022-08-06 19:56:27', '-1', '2022-08-06 19:56:27', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885508149338113, 1, 'gateway-admin-server', 1002005000, '当前岗位不存在', NULL, '-1', '2022-08-06 19:56:27', '-1', '2022-08-06 19:56:27', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885510003220482, 1, 'gateway-admin-server', 1002005001, '岗位({}) 不处于开启状态，不允许选择', NULL, '-1', '2022-08-06 19:56:27', '-1', '2022-08-06 19:56:27', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885510653337601, 1, 'gateway-admin-server', 1002005002, '已经存在该名字的岗位', NULL, '-1', '2022-08-06 19:56:27', '-1', '2022-08-06 19:56:27', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885511290871810, 1, 'gateway-admin-server', 1002005003, '已经存在该标识的岗位', NULL, '-1', '2022-08-06 19:56:28', '-1', '2022-08-06 19:56:28', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885511869685761, 1, 'gateway-admin-server', 1002006001, '当前字典类型不存在', NULL, '-1', '2022-08-06 19:56:28', '-1', '2022-08-06 19:56:28', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885512503025666, 1, 'gateway-admin-server', 1002006002, '字典类型不处于开启状态，不允许选择', NULL, '-1', '2022-08-06 19:56:28', '-1', '2022-08-06 19:56:28', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885513199280129, 1, 'gateway-admin-server', 1002006003, '已经存在该名字的字典类型', NULL, '-1', '2022-08-06 19:56:28', '-1', '2022-08-06 19:56:28', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885518874173441, 1, 'gateway-admin-server', 1002006004, '已经存在该类型的字典类型', NULL, '-1', '2022-08-06 19:56:29', '-1', '2022-08-06 19:56:29', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885520874856449, 1, 'gateway-admin-server', 1002006005, '无法删除，该字典类型还有字典数据', NULL, '-1', '2022-08-06 19:56:30', '-1', '2022-08-06 19:56:30', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885521508196353, 1, 'gateway-admin-server', 1002007001, '当前字典数据不存在', NULL, '-1', '2022-08-06 19:56:30', '-1', '2022-08-06 19:56:30', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885522275753985, 1, 'gateway-admin-server', 1002007002, '字典数据({})不处于开启状态，不允许选择', NULL, '-1', '2022-08-06 19:56:30', '-1', '2022-08-06 19:56:30', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885522988785665, 1, 'gateway-admin-server', 1002007003, '已经存在该值的字典数据', NULL, '-1', '2022-08-06 19:56:30', '-1', '2022-08-06 19:56:30', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885523634708482, 1, 'gateway-admin-server', 1002008001, '当前通知公告不存在', NULL, '-1', '2022-08-06 19:56:30', '-1', '2022-08-06 19:56:30', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885524326768641, 1, 'gateway-admin-server', 1002011000, '短信渠道不存在', NULL, '-1', '2022-08-06 19:56:31', '-1', '2022-08-06 19:56:31', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885525098520577, 1, 'gateway-admin-server', 1002011001, '短信渠道不处于开启状态，不允许选择', NULL, '-1', '2022-08-06 19:56:31', '-1', '2022-08-06 19:56:31', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885525731860481, 1, 'gateway-admin-server', 1002011002, '无法删除，该短信渠道还有短信模板', NULL, '-1', '2022-08-06 19:56:31', '-1', '2022-08-06 19:56:31', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885526373588994, 1, 'gateway-admin-server', 1002012000, '短信模板不存在', NULL, '-1', '2022-08-06 19:56:31', '-1', '2022-08-06 19:56:31', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885528248442882, 1, 'gateway-admin-server', 1002012001, '已经存在编码为【{}】的短信模板', NULL, '-1', '2022-08-06 19:56:32', '-1', '2022-08-06 19:56:32', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885528881782786, 1, 'gateway-admin-server', 1002013000, '手机号不存在', NULL, '-1', '2022-08-06 19:56:32', '-1', '2022-08-06 19:56:32', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885529586425857, 1, 'gateway-admin-server', 1002013001, '模板参数({})缺失', NULL, '-1', '2022-08-06 19:56:32', '-1', '2022-08-06 19:56:32', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885530228154369, 1, 'gateway-admin-server', 1002013002, '短信模板不存在', NULL, '-1', '2022-08-06 19:56:32', '-1', '2022-08-06 19:56:32', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885530936991745, 1, 'gateway-admin-server', 1002014000, '验证码不存在', NULL, '-1', '2022-08-06 19:56:32', '-1', '2022-08-06 19:56:32', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885531582914561, 1, 'gateway-admin-server', 1002014001, '验证码已过期', NULL, '-1', '2022-08-06 19:56:32', '-1', '2022-08-06 19:56:32', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885532228837378, 1, 'gateway-admin-server', 1002014002, '验证码已使用', NULL, '-1', '2022-08-06 19:56:33', '-1', '2022-08-06 19:56:33', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885534149828610, 1, 'gateway-admin-server', 1002014003, '验证码不正确', NULL, '-1', '2022-08-06 19:56:33', '-1', '2022-08-06 19:56:33', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885534854471681, 1, 'gateway-admin-server', 1002014004, '超过每日短信发送数量', NULL, '-1', '2022-08-06 19:56:33', '-1', '2022-08-06 19:56:33', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885535500394498, 1, 'gateway-admin-server', 1002014005, '短信发送过于频率', NULL, '-1', '2022-08-06 19:56:33', '-1', '2022-08-06 19:56:33', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885536209231874, 1, 'gateway-admin-server', 1002014006, '手机号已被使用', NULL, '-1', '2022-08-06 19:56:33', '-1', '2022-08-06 19:56:33', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885536850960386, 1, 'gateway-admin-server', 1002014007, '验证码未被使用', NULL, '-1', '2022-08-06 19:56:34', '-1', '2022-08-06 19:56:34', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885537438162946, 1, 'gateway-admin-server', 1002015000, '租户不存在', NULL, '-1', '2022-08-06 19:56:34', '-1', '2022-08-06 19:56:34', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885538138611713, 1, 'gateway-admin-server', 1002015001, '名字为【{}】的租户已被禁用', NULL, '-1', '2022-08-06 19:56:34', '-1', '2022-08-06 19:56:34', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885538843254785, 1, 'gateway-admin-server', 1002015002, '名字为【{}】的租户已过期', NULL, '-1', '2022-08-06 19:56:34', '-1', '2022-08-06 19:56:34', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885540701331457, 1, 'gateway-admin-server', 1002015003, '系统租户不能进行修改、删除等操作！', NULL, '-1', '2022-08-06 19:56:35', '-1', '2022-08-06 19:56:35', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885542676848642, 1, 'gateway-admin-server', 1002016000, '租户套餐不存在', NULL, '-1', '2022-08-06 19:56:35', '-1', '2022-08-06 19:56:35', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885543243079681, 1, 'gateway-admin-server', 1002016001, '租户正在使用该套餐，请给租户重新设置套餐后再尝试删除', NULL, '-1', '2022-08-06 19:56:35', '-1', '2022-08-06 19:56:35', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885543880613890, 1, 'gateway-admin-server', 1002016002, '名字为【{}】的租户套餐已被禁用', NULL, '-1', '2022-08-06 19:56:35', '-1', '2022-08-06 19:56:35', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885545797410818, 1, 'gateway-admin-server', 1002017000, '错误码不存在', NULL, '-1', '2022-08-06 19:56:36', '-1', '2022-08-06 19:56:36', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885548871835650, 1, 'gateway-admin-server', 1002017001, '已经存在编码为【{}】的错误码', NULL, '-1', '2022-08-06 19:56:36', '-1', '2022-08-06 19:56:36', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885549580673025, 1, 'gateway-admin-server', 1002018000, '社交授权失败，原因是：{}', NULL, '-1', '2022-08-06 19:56:37', '-1', '2022-08-06 19:56:37', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885550268538882, 1, 'gateway-admin-server', 1002018001, '社交解绑失败，非当前用户绑定', NULL, '-1', '2022-08-06 19:56:37', '-1', '2022-08-06 19:56:37', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885550973181954, 1, 'gateway-admin-server', 1002018002, '社交授权失败，找不到对应的用户', NULL, '-1', '2022-08-06 19:56:37', '-1', '2022-08-06 19:56:37', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885552894173185, 1, 'gateway-admin-server', 1002020001, '网关请求来源apiKey不合法', NULL, '-1', '2022-08-06 19:56:37', '-1', '2022-08-06 19:56:37', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885553472987137, 1, 'gateway-admin-server', 1002020002, '网关请求来源apiSecret不合法', NULL, '-1', '2022-08-06 19:56:38', '-1', '2022-08-06 19:56:38', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885554051801089, 1, 'gateway-admin-server', 1002020003, '网关请求来源system不合法', NULL, '-1', '2022-08-06 19:56:38', '-1', '2022-08-06 19:56:38', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885554748055553, 1, 'gateway-admin-server', 1002020004, '网关请求来源apiKey/apiSecret已禁用', NULL, '-1', '2022-08-06 19:56:38', '-1', '2022-08-06 19:56:38', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1555885555389784066, 1, 'gateway-admin-server', 1002020005, '网关请求来源apiKey/apiSecret/system不允许为空', NULL, '-1', '2022-08-06 19:56:38', '-1', '2022-08-06 19:56:38', NULL);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177905451010, 1, 'gateway-admin-server', 1002000000, '登录失败，账号密码不正确', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177913839618, 1, 'gateway-admin-server', 1002000001, '登录失败，账号被禁用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177913839619, 1, 'gateway-admin-server', 1002000002, '登录失败', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177913839620, 1, 'gateway-admin-server', 1002000003, '验证码不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177980948481, 1, 'gateway-admin-server', 1002000004, '验证码不正确', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177980948482, 1, 'gateway-admin-server', 1002000005, '未绑定账号，需要进行绑定', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177980948483, 1, 'gateway-admin-server', 1002000006, 'Token 已经过期', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177980948484, 1, 'gateway-admin-server', 1002001000, '已经存在该名字的菜单', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820177980948485, 1, 'gateway-admin-server', 1002001001, '父菜单不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178043863042, 1, 'gateway-admin-server', 1002001002, '不能设置自己为父菜单', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178043863043, 1, 'gateway-admin-server', 1002001003, '菜单不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178043863044, 1, 'gateway-admin-server', 1002001004, '存在子菜单，无法删除', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178043863045, 1, 'gateway-admin-server', 1002001005, '父菜单的类型必须是目录或者菜单', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178043863046, 1, 'gateway-admin-server', 1002002000, '角色不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178115166209, 1, 'gateway-admin-server', 1002002001, '已经存在名为【{}】的角色', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178115166210, 1, 'gateway-admin-server', 1002002002, '已经存在编码为【{}】的角色', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178115166211, 1, 'gateway-admin-server', 1002002003, '不能操作类型为系统内置的角色', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178115166212, 1, 'gateway-admin-server', 1002002004, '名字为【{}】的角色已被禁用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178173886465, 1, 'gateway-admin-server', 1002002005, '编码【{}】不能使用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178173886466, 1, 'gateway-admin-server', 1002003000, '用户账号已经存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178173886467, 1, 'gateway-admin-server', 1002003001, '手机号已经存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178173886468, 1, 'gateway-admin-server', 1002003002, '邮箱已经存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178173886469, 1, 'gateway-admin-server', 1002003003, '用户不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178240995330, 1, 'gateway-admin-server', 1002003004, '导入用户数据不能为空！', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178240995331, 1, 'gateway-admin-server', 1002003005, '用户密码校验失败', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178240995332, 1, 'gateway-admin-server', 1002003006, '名字为【{}】的用户已被禁用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178240995333, 1, 'gateway-admin-server', 1002003008, '创建用户失败，原因：超过租户最大租户配额({})！', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178240995334, 1, 'gateway-admin-server', 1002004000, '已经存在该名字的部门', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178303909890, 1, 'gateway-admin-server', 1002004001, '父级部门不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178303909891, 1, 'gateway-admin-server', 1002004002, '当前部门不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178303909892, 1, 'gateway-admin-server', 1002004003, '存在子部门，无法删除', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178303909893, 1, 'gateway-admin-server', 1002004004, '不能设置自己为父部门', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178303909894, 1, 'gateway-admin-server', 1002004005, '部门中存在员工，无法删除', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178371018754, 1, 'gateway-admin-server', 1002004006, '部门不处于开启状态，不允许选择', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178371018755, 1, 'gateway-admin-server', 1002004007, '不能设置自己的子部门为父部门', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178371018756, 1, 'gateway-admin-server', 1002005000, '当前岗位不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178371018757, 1, 'gateway-admin-server', 1002005001, '岗位({}) 不处于开启状态，不允许选择', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178371018758, 1, 'gateway-admin-server', 1002005002, '已经存在该名字的岗位', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178433933313, 1, 'gateway-admin-server', 1002005003, '已经存在该标识的岗位', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178433933314, 1, 'gateway-admin-server', 1002006001, '当前字典类型不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178433933315, 1, 'gateway-admin-server', 1002006002, '字典类型不处于开启状态，不允许选择', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178433933316, 1, 'gateway-admin-server', 1002006003, '已经存在该名字的字典类型', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178501042178, 1, 'gateway-admin-server', 1002006004, '已经存在该类型的字典类型', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178501042179, 1, 'gateway-admin-server', 1002006005, '无法删除，该字典类型还有字典数据', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178501042180, 1, 'gateway-admin-server', 1002007001, '当前字典数据不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178501042181, 1, 'gateway-admin-server', 1002007002, '字典数据({})不处于开启状态，不允许选择', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178501042182, 1, 'gateway-admin-server', 1002007003, '已经存在该值的字典数据', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178568151041, 1, 'gateway-admin-server', 1002008001, '当前通知公告不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178568151042, 1, 'gateway-admin-server', 1002011000, '短信渠道不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178568151043, 1, 'gateway-admin-server', 1002011001, '短信渠道不处于开启状态，不允许选择', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178568151044, 1, 'gateway-admin-server', 1002011002, '无法删除，该短信渠道还有短信模板', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178568151045, 1, 'gateway-admin-server', 1002012000, '短信模板不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178643648513, 1, 'gateway-admin-server', 1002012001, '已经存在编码为【{}】的短信模板', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178647842817, 1, 'gateway-admin-server', 1002013000, '手机号不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178647842818, 1, 'gateway-admin-server', 1002013001, '模板参数({})缺失', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178647842819, 1, 'gateway-admin-server', 1002013002, '短信模板不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178647842820, 1, 'gateway-admin-server', 1002014000, '验证码不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178693980161, 1, 'gateway-admin-server', 1002014001, '验证码已过期', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178693980162, 1, 'gateway-admin-server', 1002014002, '验证码已使用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178693980163, 1, 'gateway-admin-server', 1002014003, '验证码不正确', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178693980164, 1, 'gateway-admin-server', 1002014004, '超过每日短信发送数量', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178693980165, 1, 'gateway-admin-server', 1002014005, '短信发送过于频率', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178756894721, 1, 'gateway-admin-server', 1002014006, '手机号已被使用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178756894722, 1, 'gateway-admin-server', 1002014007, '验证码未被使用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178756894723, 1, 'gateway-admin-server', 1002015000, '租户不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178756894724, 1, 'gateway-admin-server', 1002015001, '名字为【{}】的租户已被禁用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178756894725, 1, 'gateway-admin-server', 1002015002, '名字为【{}】的租户已过期', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003585, 1, 'gateway-admin-server', 1002015003, '系统租户不能进行修改、删除等操作！', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003586, 1, 'gateway-admin-server', 1002016000, '租户套餐不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003587, 1, 'gateway-admin-server', 1002016001, '租户正在使用该套餐，请给租户重新设置套餐后再尝试删除', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003588, 1, 'gateway-admin-server', 1002016002, '名字为【{}】的租户套餐已被禁用', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003589, 1, 'gateway-admin-server', 1002017000, '错误码不存在', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003590, 1, 'gateway-admin-server', 1002017001, '已经存在编码为【{}】的错误码', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178824003591, 1, 'gateway-admin-server', 1002018000, '社交授权失败，原因是：{}', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178886918146, 1, 'gateway-admin-server', 1002018001, '社交解绑失败，非当前用户绑定', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1512820178886918147, 1, 'gateway-admin-server', 1002018002, '社交授权失败，找不到对应的用户', '', '-1', '2022-04-09 15:50:12', '-1', '2022-04-09 15:50:12', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1523331144308879362, 1, 'gateway-admin-server', 1002020001, '网关请求来源apiKey不合法', '', '-1', '2022-05-08 15:57:02', '-1', '2022-06-11 07:31:45', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1523331144694755329, 1, 'gateway-admin-server', 1002020002, '网关请求来源apiSecret不合法', '', '-1', '2022-05-08 15:57:02', '-1', '2022-06-11 07:31:45', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1523331145026105345, 1, 'gateway-admin-server', 1002020003, '网关请求来源system不合法', '', '-1', '2022-05-08 15:57:02', '-1', '2022-06-11 07:31:45', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1535525176282669058, 1, 'gateway-admin-server', 1002020004, '网关请求来源apiKey/apiSecret已禁用', '', '-1', '2022-06-11 07:31:45', '-1', '2022-06-11 07:31:45', b'0');
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1535525176886648833, 1, 'gateway-admin-server', 1002020005, '网关请求来源apiKey/apiSecret/system不允许为空', '', '-1', '2022-06-11 07:31:46', '-1', '2022-06-11 07:31:46', b'0');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL,
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
  `del_flag` char(1) DEFAULT NULL,
  `component` varchar(128) DEFAULT NULL COMMENT '视图',
  `tag` varchar(64) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (0, '首页', NULL, '/home', -1, 'el-icon-eleme-filled', 0, '0', '0', '2018-09-28 08:29:53', '2021-12-27 15:13:30', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (100, '工作台', NULL, '/dashboard', 0, 'el-icon-menu', 1, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'home', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (200, '用户中心', NULL, '/userCenter', 0, 'el-icon-user', 2, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'userCenter', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1000, '系统管理', NULL, '/system', -1, 'el-icon-tools', 10, '0', '0', '2018-09-28 08:29:53', '2021-12-27 15:13:30', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1100, '用户管理', NULL, '/system/user', 1000, 'el-icon-user-filled', 1, '0', '0', '2017-11-02 22:24:37', '2021-12-28 20:15:53', '0', 'system/user', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1101, '用户新增', 'sys_user_add', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:52:09', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1102, '用户修改', 'sys_user_edit', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:52:48', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1103, '用户删除', 'sys_user_del', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1104, '导入导出', 'sys_user_export', NULL, 1100, NULL, 1, '0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1200, '菜单管理', NULL, '/system/menu', 1000, 'el-icon-fold', 2, '0', '0', '2017-11-08 09:57:27', '2021-12-28 20:14:22', '0', 'system/menu', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1201, '菜单新增', 'sys_menu_add', NULL, 1200, NULL, 1, '0', '1', '2017-11-08 10:15:53', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1202, '菜单修改', 'sys_menu_edit', NULL, 1200, NULL, 1, '0', '1', '2017-11-08 10:16:23', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1203, '菜单删除', 'sys_menu_del', NULL, 1200, NULL, 1, '0', '1', '2017-11-08 10:16:43', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1300, '角色管理', NULL, '/system/role', 1000, 'el-icon-notebook', 3, '0', '0', '2017-11-08 10:13:37', '2021-12-28 20:14:19', '0', 'system/role', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1301, '角色新增', 'sys_role_add', NULL, 1300, NULL, 1, '0', '1', '2017-11-08 10:14:18', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1302, '角色修改', 'sys_role_edit', NULL, 1300, NULL, 1, '0', '1', '2017-11-08 10:14:41', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1303, '角色删除', 'sys_role_del', NULL, 1300, NULL, 1, '0', '1', '2017-11-08 10:14:59', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1304, '分配权限', 'sys_role_perm', NULL, 1300, NULL, 1, '0', '1', '2018-04-20 07:22:55', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1400, '部门管理', NULL, '/system/dept', 1000, 'sc-icon-organization', 4, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/dept', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1401, '部门新增', 'sys_dept_add', NULL, 1400, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1402, '部门修改', 'sys_dept_edit', NULL, 1400, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1403, '部门删除', 'sys_dept_del', NULL, 1400, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1500, '字典管理', NULL, '/system/dic', 1000, 'el-icon-document', 5, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/dic', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1600, '应用管理', NULL, '/system/client', 1000, 'el-icon-help-filled', 6, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/client', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1700, '表格列管理', NULL, '/system/table', 1000, 'el-icon-scale-to-original', 7, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/table', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1800, '系统设置', NULL, '/system/setting', 1000, 'el-icon-tools', 10, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/setting', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (1900, '系统日志', NULL, '/system/log', 1000, 'el-icon-warning', 9, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/log', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2000, '网关管理', NULL, '/gateway', -1, 'el-icon-help-filled', 20, '0', '0', '2018-09-04 05:58:41', '2021-12-31 16:17:42', '0', 'gateway', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2100, '网关日志', NULL, '/gateway/log', 2000, 'el-icon-help-filled', 1, '0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:25', '0', 'gateway/log', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2101, '网关日志新增', 'gateway_log_add', NULL, 2100, NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2102, '网关日志修改', 'gateway_log_edit', NULL, 2100, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2103, '网关日志删除', 'gateway_log_del', NULL, 2100, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2200, '网关路由', NULL, '/gateway/route', 2000, 'el-icon-help-filled', 2, '0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:28', '0', 'gateway/route', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2201, '网关路由新增', 'gateway_route_add', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2202, '网关路由修改', 'gateway_route_edit', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2203, '网关路由删除', 'gateway_route_del', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2204, '网关路由拷贝', 'gateway_route_copy', NULL, 2200, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2300, '网关访问', NULL, '/gateway/access', 2000, 'el-icon-help-filled', 3, '0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:28', '0', 'gateway/access', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2301, '网关访问新增', 'gateway_access_add', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2302, '网关访问修改', 'gateway_access_edit', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2303, '网关访问删除', 'gateway_access_del', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (2304, '网关访问状态启用禁用', 'gateway_access_status', NULL, 2300, NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (3000, '关于', NULL, '/other/about', -1, 'el-icon-info-filled', 30, '0', '0', '2018-09-04 05:58:41', '2021-12-31 16:17:42', '0', 'other/about', NULL);
INSERT INTO `sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`, `component`, `tag`) VALUES (11100, '计划任务', NULL, '/system/task', 1000, 'el-icon-alarm-clock', 8, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0', 'system/task', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) DEFAULT NULL,
  `role_code` varchar(64) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `ds_type` char(1) DEFAULT NULL,
  `ds_scope` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `ds_type`, `ds_scope`, `create_time`, `update_time`, `del_flag`) VALUES (1, '超级管理员', 'ROLE_admin', '超级管理员', '0', '2', '2017-10-29 07:45:51', '2021-12-31 06:19:46', '0');
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `ds_type`, `ds_scope`, `create_time`, `update_time`, `del_flag`) VALUES (2, '普通用户', 'ROLE_normal', '普通用户', '0', '2', '2021-12-31 06:19:20', '2021-12-31 06:20:27', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (2, 2200);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (2, 2300);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (2, 3000);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (3, 0);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (3, 100);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (3, 200);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (3, 3000);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 0);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 100);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 200);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1000);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1100);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1104);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1103);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1102);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1101);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1200);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1203);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1202);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1201);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1300);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1304);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1303);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1302);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1301);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1400);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1403);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1402);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1401);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1500);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1600);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1700);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 11100);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1900);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1800);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2000);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2100);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2103);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2102);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2101);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2200);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2204);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2203);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2202);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2201);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2300);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2304);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2303);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2302);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2301);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 3000);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`user_id`, `user_name`, `nick_name`, `password`, `salt`, `gender`, `email`, `mobile`, `avatar`, `last_login_time`, `last_login_ip`, `dept_id`, `lock_flag`, `create_time`, `update_time`, `del_flag`) VALUES (1, 'admin', 'admin-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 1, 'li7hai26@gmail.com', '17034642888', '', '2022-01-06 07:47:04', '127.0.0.1', 1, 0, '2018-04-19 23:15:18', '2022-07-21 14:03:01', '0');
INSERT INTO `sys_user` (`user_id`, `user_name`, `nick_name`, `password`, `salt`, `gender`, `email`, `mobile`, `avatar`, `last_login_time`, `last_login_ip`, `dept_id`, `lock_flag`, `create_time`, `update_time`, `del_flag`) VALUES (2, 'editor', 'editor-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 0, 'li7hai26@outlook.com', '17034642888', '', '2022-01-06 07:47:08', '127.0.0.1', 5, 0, '2021-12-31 08:53:14', '2022-01-06 10:40:33', '0');
INSERT INTO `sys_user` (`user_id`, `user_name`, `nick_name`, `password`, `salt`, `gender`, `email`, `mobile`, `avatar`, `last_login_time`, `last_login_ip`, `dept_id`, `lock_flag`, `create_time`, `update_time`, `del_flag`) VALUES (3, 'demo', 'demo', '$2a$10$7pNLq6i9nQUl9s3PrWN08eMLo/PugSkJVIWf2UKuO8yHgY08kFDAu', NULL, NULL, 'demo@saicmotor.com', '17621006318', NULL, NULL, NULL, 3, 0, '2022-08-28 12:35:40', '2022-08-28 20:35:43', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (2, 2);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 2);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (12, 2);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (9, 1);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (9, 2);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (3, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
