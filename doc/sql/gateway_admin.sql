create database `gateway_admin` default character set utf8mb4 collate utf8mb4_general_ci;
USE `gateway_admin`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_access_conf
-- ----------------------------
DROP TABLE IF EXISTS `gateway_access_conf`;
CREATE TABLE `gateway_access_conf`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `api_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api访问key',
  `api_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api访问密钥',
  `system` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态(是否启用) 0:启用,1:禁用',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_idx1_username`(`api_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网关访问配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_access_conf
-- ----------------------------
INSERT INTO `gateway_access_conf` VALUES (1, 'metersphere-key', 'metersphere-secret', 'metersphere', '0', 'metersphere备注', '2022-01-14 17:01:09', '2022-01-14 17:09:55', '0');
INSERT INTO `gateway_access_conf` VALUES (2, 'test', 'test', 'test', '1', 'test', '2022-01-14 17:09:21', '2022-01-14 17:09:37', '0');

-- ----------------------------
-- Table structure for gateway_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_conf`;
CREATE TABLE `gateway_route_conf`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `route_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `route_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由id',
  `predicates` json NULL COMMENT '断言',
  `filters` json NULL COMMENT '过滤器',
  `uri` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'url',
  `order` int(2) NULL DEFAULT 0 COMMENT '排序',
  `metadata` json NULL COMMENT '路由元信息',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网关路由配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_route_conf
-- ----------------------------
INSERT INTO `gateway_route_conf` VALUES (1, '监控管理', 'monitor', '[{\"args\": {\"_genkey_0\": \"/monitor/**\"}, \"name\": \"Path\"}]', '[]', 'lb://monitor', 0, '{\"version\": \"1\"}', '2021-12-27 10:43:25', '2022-01-06 10:40:46', '0');

-- ----------------------------
-- Table structure for magic_api_backup
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_backup`;
CREATE TABLE `magic_api_backup`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原对象ID',
  `create_date` bigint(13) NOT NULL COMMENT '备份时间',
  `tag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原名称',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备份内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`, `create_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of magic_api_backup
-- ----------------------------
INSERT INTO `magic_api_backup` VALUES ('0ff52bca18bd48c98502ece1329eeac0', 1651505339211, NULL, 'api', '获取全部网关路由信息', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"0ff52bca18bd48c98502ece1329eeac0\",\r\n  \"script\" : \"return \'Hello magic-api\'\",\r\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"获取全部网关路由信息\",\r\n  \"createTime\" : 1651505339190,\r\n  \"updateTime\" : null,\r\n  \"lock\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"path\" : \"/routes\",\r\n  \"method\" : \"GET\",\r\n  \"parameters\" : [ ],\r\n  \"options\" : [ ],\r\n  \"requestBody\" : null,\r\n  \"headers\" : [ ],\r\n  \"paths\" : [ ],\r\n  \"responseBody\" : null,\r\n  \"description\" : null,\r\n  \"requestBodyDefinition\" : null,\r\n  \"responseBodyDefinition\" : null\r\n}', NULL);
INSERT INTO `magic_api_backup` VALUES ('0ff52bca18bd48c98502ece1329eeac0', 1651505411818, NULL, 'api', '获取全部网关路由信息', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"0ff52bca18bd48c98502ece1329eeac0\",\r\n  \"script\" : \"db.table(\\\"gateway_route_conf\\\").count()\",\r\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"获取全部网关路由信息\",\r\n  \"createTime\" : null,\r\n  \"updateTime\" : 1651505411797,\r\n  \"lock\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"path\" : \"/routes\",\r\n  \"method\" : \"GET\",\r\n  \"parameters\" : [ ],\r\n  \"options\" : [ ],\r\n  \"requestBody\" : null,\r\n  \"headers\" : [ ],\r\n  \"paths\" : [ ],\r\n  \"responseBody\" : null,\r\n  \"description\" : null,\r\n  \"requestBodyDefinition\" : null,\r\n  \"responseBodyDefinition\" : null\r\n}', NULL);
INSERT INTO `magic_api_backup` VALUES ('0ff52bca18bd48c98502ece1329eeac0', 1651505432267, NULL, 'api', '获取全部网关路由信息', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"0ff52bca18bd48c98502ece1329eeac0\",\r\n  \"script\" : \"db.table(\\\"gateway_route_conf\\\").page()\",\r\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"获取全部网关路由信息\",\r\n  \"createTime\" : null,\r\n  \"updateTime\" : 1651505432248,\r\n  \"lock\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"path\" : \"/routes\",\r\n  \"method\" : \"GET\",\r\n  \"parameters\" : [ ],\r\n  \"options\" : [ ],\r\n  \"requestBody\" : null,\r\n  \"headers\" : [ ],\r\n  \"paths\" : [ ],\r\n  \"responseBody\" : \"{\\n    \\\"code\\\": 1,\\n    \\\"message\\\": \\\"success\\\",\\n    \\\"data\\\": 1,\\n    \\\"timestamp\\\": 1651505412773,\\n    \\\"executeTime\\\": 26\\n}\",\r\n  \"description\" : null,\r\n  \"requestBodyDefinition\" : null,\r\n  \"responseBodyDefinition\" : {\r\n    \"name\" : \"\",\r\n    \"value\" : \"\",\r\n    \"description\" : \"\",\r\n    \"required\" : false,\r\n    \"dataType\" : \"Object\",\r\n    \"type\" : null,\r\n    \"defaultValue\" : null,\r\n    \"validateType\" : \"\",\r\n    \"error\" : \"\",\r\n    \"expression\" : \"\",\r\n    \"children\" : [ {\r\n      \"name\" : \"code\",\r\n      \"value\" : \"1\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Integer\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"message\",\r\n      \"value\" : \"success\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"String\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"data\",\r\n      \"value\" : \"1\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Integer\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"timestamp\",\r\n      \"value\" : \"1651505412773\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Long\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"executeTime\",\r\n      \"value\" : \"26\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Integer\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    } ]\r\n  }\r\n}', NULL);
INSERT INTO `magic_api_backup` VALUES ('62ee0d323ae946b8a9fcd08a11389fc1', 1651505239995, NULL, 'api', '测试', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"62ee0d323ae946b8a9fcd08a11389fc1\",\r\n  \"script\" : \"return \'Hello magic-api\'\",\r\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"测试\",\r\n  \"createTime\" : 1651505239976,\r\n  \"updateTime\" : null,\r\n  \"lock\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"path\" : \"/test\",\r\n  \"method\" : \"GET\",\r\n  \"parameters\" : [ ],\r\n  \"options\" : [ ],\r\n  \"requestBody\" : null,\r\n  \"headers\" : [ ],\r\n  \"paths\" : [ ],\r\n  \"responseBody\" : null,\r\n  \"description\" : null,\r\n  \"requestBodyDefinition\" : null,\r\n  \"responseBodyDefinition\" : null\r\n}', NULL);
INSERT INTO `magic_api_backup` VALUES ('968a9a7ee3c04483a1c6a6c23f8c445d', 1651505223813, NULL, 'api-group', '网管管理服务', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"网管管理服务\",\r\n  \"type\" : \"api\",\r\n  \"parentId\" : \"0\",\r\n  \"path\" : \"/magic/api\",\r\n  \"createTime\" : 1651505223795,\r\n  \"updateTime\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"paths\" : [ ],\r\n  \"options\" : [ ]\r\n}', NULL);
INSERT INTO `magic_api_backup` VALUES ('968a9a7ee3c04483a1c6a6c23f8c445d', 1651505331365, NULL, 'api-group', '网管管理服务', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"网管管理服务\",\r\n  \"type\" : \"api\",\r\n  \"parentId\" : \"0\",\r\n  \"path\" : \"/magic/api/gateway/admin\",\r\n  \"createTime\" : null,\r\n  \"updateTime\" : 1651505331355,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"paths\" : [ ],\r\n  \"options\" : [ ]\r\n}', NULL);

-- ----------------------------
-- Table structure for magic_api_file
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_file`;
CREATE TABLE `magic_api_file`  (
  `file_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`file_path`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of magic_api_file
-- ----------------------------
INSERT INTO `magic_api_file` VALUES ('magic-api/api/网管管理服务/', 'this is directory');
INSERT INTO `magic_api_file` VALUES ('magic-api/api/网管管理服务/group.json', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"网管管理服务\",\r\n  \"type\" : \"api\",\r\n  \"parentId\" : \"0\",\r\n  \"path\" : \"/magic/api/gateway/admin\",\r\n  \"createTime\" : null,\r\n  \"updateTime\" : 1651505331355,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"paths\" : [ ],\r\n  \"options\" : [ ]\r\n}');
INSERT INTO `magic_api_file` VALUES ('magic-api/api/网管管理服务/测试.ms', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"62ee0d323ae946b8a9fcd08a11389fc1\",\r\n  \"script\" : null,\r\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"测试\",\r\n  \"createTime\" : 1651505239976,\r\n  \"updateTime\" : null,\r\n  \"lock\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"path\" : \"/test\",\r\n  \"method\" : \"GET\",\r\n  \"parameters\" : [ ],\r\n  \"options\" : [ ],\r\n  \"requestBody\" : null,\r\n  \"headers\" : [ ],\r\n  \"paths\" : [ ],\r\n  \"responseBody\" : null,\r\n  \"description\" : null,\r\n  \"requestBodyDefinition\" : null,\r\n  \"responseBodyDefinition\" : null\r\n}\r\n================================\r\nreturn \'Hello magic-api\'');
INSERT INTO `magic_api_file` VALUES ('magic-api/api/网管管理服务/获取全部网关路由信息.ms', '{\r\n  \"properties\" : { },\r\n  \"id\" : \"0ff52bca18bd48c98502ece1329eeac0\",\r\n  \"script\" : null,\r\n  \"groupId\" : \"968a9a7ee3c04483a1c6a6c23f8c445d\",\r\n  \"name\" : \"获取全部网关路由信息\",\r\n  \"createTime\" : null,\r\n  \"updateTime\" : 1651505432248,\r\n  \"lock\" : null,\r\n  \"createBy\" : null,\r\n  \"updateBy\" : null,\r\n  \"path\" : \"/routes\",\r\n  \"method\" : \"GET\",\r\n  \"parameters\" : [ ],\r\n  \"options\" : [ ],\r\n  \"requestBody\" : null,\r\n  \"headers\" : [ ],\r\n  \"paths\" : [ ],\r\n  \"responseBody\" : \"{\\n    \\\"code\\\": 1,\\n    \\\"message\\\": \\\"success\\\",\\n    \\\"data\\\": 1,\\n    \\\"timestamp\\\": 1651505412773,\\n    \\\"executeTime\\\": 26\\n}\",\r\n  \"description\" : null,\r\n  \"requestBodyDefinition\" : null,\r\n  \"responseBodyDefinition\" : {\r\n    \"name\" : \"\",\r\n    \"value\" : \"\",\r\n    \"description\" : \"\",\r\n    \"required\" : false,\r\n    \"dataType\" : \"Object\",\r\n    \"type\" : null,\r\n    \"defaultValue\" : null,\r\n    \"validateType\" : \"\",\r\n    \"error\" : \"\",\r\n    \"expression\" : \"\",\r\n    \"children\" : [ {\r\n      \"name\" : \"code\",\r\n      \"value\" : \"1\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Integer\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"message\",\r\n      \"value\" : \"success\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"String\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"data\",\r\n      \"value\" : \"1\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Integer\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"timestamp\",\r\n      \"value\" : \"1651505412773\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Long\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    }, {\r\n      \"name\" : \"executeTime\",\r\n      \"value\" : \"26\",\r\n      \"description\" : \"\",\r\n      \"required\" : false,\r\n      \"dataType\" : \"Integer\",\r\n      \"type\" : null,\r\n      \"defaultValue\" : null,\r\n      \"validateType\" : \"\",\r\n      \"error\" : \"\",\r\n      \"expression\" : \"\",\r\n      \"children\" : [ ]\r\n    } ]\r\n  }\r\n}\r\n================================\r\ndb.table(\"gateway_route_conf\").page()');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `parent_id` int(20) NULL DEFAULT NULL COMMENT '父部门id',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '山东', 1, -1, 1, '2018-01-22 19:00:23', '2021-12-31 14:33:51', '0');
INSERT INTO `sys_dept` VALUES (2, '沙县国际', 2, -1, 1, '2018-01-22 19:00:38', '2021-12-31 14:33:51', '0');
INSERT INTO `sys_dept` VALUES (3, '潍坊', 3, 1, 1, '2018-01-22 19:00:44', '2021-12-31 14:33:51', '0');
INSERT INTO `sys_dept` VALUES (4, '高新', 4, 3, 1, '2018-01-22 19:00:52', '2021-12-31 14:33:51', '0');
INSERT INTO `sys_dept` VALUES (5, '院校', 5, 4, 1, '2018-01-22 19:00:57', '2021-12-31 14:33:51', '0');
INSERT INTO `sys_dept` VALUES (6, '潍院', 6, 5, 1, '2018-01-22 19:01:06', '2021-12-31 14:33:51', '1');
INSERT INTO `sys_dept` VALUES (7, '山东沙县', 7, 2, 1, '2018-01-22 19:01:57', '2021-12-31 14:33:51', '0');
INSERT INTO `sys_dept` VALUES (8, '潍坊沙县', 8, 7, 1, '2018-01-22 19:02:03', '2021-12-31 14:33:51', '0');

-- ----------------------------
-- Table structure for sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_relation`;
CREATE TABLE `sys_dept_relation`  (
  `ancestor` int(11) NOT NULL COMMENT '祖先节点',
  `descendant` int(11) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`ancestor`, `descendant`) USING BTREE,
  INDEX `idx1`(`ancestor`) USING BTREE,
  INDEX `idx2`(`descendant`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept_relation
-- ----------------------------
INSERT INTO `sys_dept_relation` VALUES (1, 1);
INSERT INTO `sys_dept_relation` VALUES (1, 3);
INSERT INTO `sys_dept_relation` VALUES (1, 4);
INSERT INTO `sys_dept_relation` VALUES (1, 5);
INSERT INTO `sys_dept_relation` VALUES (2, 2);
INSERT INTO `sys_dept_relation` VALUES (2, 7);
INSERT INTO `sys_dept_relation` VALUES (2, 8);
INSERT INTO `sys_dept_relation` VALUES (2, 11);
INSERT INTO `sys_dept_relation` VALUES (2, 12);
INSERT INTO `sys_dept_relation` VALUES (3, 3);
INSERT INTO `sys_dept_relation` VALUES (3, 4);
INSERT INTO `sys_dept_relation` VALUES (3, 5);
INSERT INTO `sys_dept_relation` VALUES (4, 4);
INSERT INTO `sys_dept_relation` VALUES (4, 5);
INSERT INTO `sys_dept_relation` VALUES (5, 5);
INSERT INTO `sys_dept_relation` VALUES (7, 7);
INSERT INTO `sys_dept_relation` VALUES (7, 8);
INSERT INTO `sys_dept_relation` VALUES (7, 11);
INSERT INTO `sys_dept_relation` VALUES (7, 12);
INSERT INTO `sys_dept_relation` VALUES (8, 8);

-- ----------------------------
-- Table structure for sys_error_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_code`;
CREATE TABLE `sys_error_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '错误码编号',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '错误码类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `code` int(11) NOT NULL DEFAULT 0 COMMENT '错误码编码',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '错误码错误提示',
  `memo` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1512820178886918148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '错误码表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_error_code
-- ----------------------------
INSERT INTO `sys_error_code` VALUES (1512820177905451010, 1, 'gateway-admin-server', 1002000000, '登录失败，账号密码不正确', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177913839618, 1, 'gateway-admin-server', 1002000001, '登录失败，账号被禁用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177913839619, 1, 'gateway-admin-server', 1002000002, '登录失败', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177913839620, 1, 'gateway-admin-server', 1002000003, '验证码不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177980948481, 1, 'gateway-admin-server', 1002000004, '验证码不正确', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177980948482, 1, 'gateway-admin-server', 1002000005, '未绑定账号，需要进行绑定', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177980948483, 1, 'gateway-admin-server', 1002000006, 'Token 已经过期', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177980948484, 1, 'gateway-admin-server', 1002001000, '已经存在该名字的菜单', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820177980948485, 1, 'gateway-admin-server', 1002001001, '父菜单不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178043863042, 1, 'gateway-admin-server', 1002001002, '不能设置自己为父菜单', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178043863043, 1, 'gateway-admin-server', 1002001003, '菜单不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178043863044, 1, 'gateway-admin-server', 1002001004, '存在子菜单，无法删除', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178043863045, 1, 'gateway-admin-server', 1002001005, '父菜单的类型必须是目录或者菜单', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178043863046, 1, 'gateway-admin-server', 1002002000, '角色不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178115166209, 1, 'gateway-admin-server', 1002002001, '已经存在名为【{}】的角色', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178115166210, 1, 'gateway-admin-server', 1002002002, '已经存在编码为【{}】的角色', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178115166211, 1, 'gateway-admin-server', 1002002003, '不能操作类型为系统内置的角色', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178115166212, 1, 'gateway-admin-server', 1002002004, '名字为【{}】的角色已被禁用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178173886465, 1, 'gateway-admin-server', 1002002005, '编码【{}】不能使用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178173886466, 1, 'gateway-admin-server', 1002003000, '用户账号已经存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178173886467, 1, 'gateway-admin-server', 1002003001, '手机号已经存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178173886468, 1, 'gateway-admin-server', 1002003002, '邮箱已经存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178173886469, 1, 'gateway-admin-server', 1002003003, '用户不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178240995330, 1, 'gateway-admin-server', 1002003004, '导入用户数据不能为空！', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178240995331, 1, 'gateway-admin-server', 1002003005, '用户密码校验失败', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178240995332, 1, 'gateway-admin-server', 1002003006, '名字为【{}】的用户已被禁用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178240995333, 1, 'gateway-admin-server', 1002003008, '创建用户失败，原因：超过租户最大租户配额({})！', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178240995334, 1, 'gateway-admin-server', 1002004000, '已经存在该名字的部门', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178303909890, 1, 'gateway-admin-server', 1002004001, '父级部门不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178303909891, 1, 'gateway-admin-server', 1002004002, '当前部门不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178303909892, 1, 'gateway-admin-server', 1002004003, '存在子部门，无法删除', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178303909893, 1, 'gateway-admin-server', 1002004004, '不能设置自己为父部门', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178303909894, 1, 'gateway-admin-server', 1002004005, '部门中存在员工，无法删除', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178371018754, 1, 'gateway-admin-server', 1002004006, '部门不处于开启状态，不允许选择', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178371018755, 1, 'gateway-admin-server', 1002004007, '不能设置自己的子部门为父部门', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178371018756, 1, 'gateway-admin-server', 1002005000, '当前岗位不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178371018757, 1, 'gateway-admin-server', 1002005001, '岗位({}) 不处于开启状态，不允许选择', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178371018758, 1, 'gateway-admin-server', 1002005002, '已经存在该名字的岗位', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178433933313, 1, 'gateway-admin-server', 1002005003, '已经存在该标识的岗位', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178433933314, 1, 'gateway-admin-server', 1002006001, '当前字典类型不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178433933315, 1, 'gateway-admin-server', 1002006002, '字典类型不处于开启状态，不允许选择', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178433933316, 1, 'gateway-admin-server', 1002006003, '已经存在该名字的字典类型', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178501042178, 1, 'gateway-admin-server', 1002006004, '已经存在该类型的字典类型', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178501042179, 1, 'gateway-admin-server', 1002006005, '无法删除，该字典类型还有字典数据', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178501042180, 1, 'gateway-admin-server', 1002007001, '当前字典数据不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178501042181, 1, 'gateway-admin-server', 1002007002, '字典数据({})不处于开启状态，不允许选择', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178501042182, 1, 'gateway-admin-server', 1002007003, '已经存在该值的字典数据', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178568151041, 1, 'gateway-admin-server', 1002008001, '当前通知公告不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178568151042, 1, 'gateway-admin-server', 1002011000, '短信渠道不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178568151043, 1, 'gateway-admin-server', 1002011001, '短信渠道不处于开启状态，不允许选择', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178568151044, 1, 'gateway-admin-server', 1002011002, '无法删除，该短信渠道还有短信模板', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178568151045, 1, 'gateway-admin-server', 1002012000, '短信模板不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178643648513, 1, 'gateway-admin-server', 1002012001, '已经存在编码为【{}】的短信模板', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178647842817, 1, 'gateway-admin-server', 1002013000, '手机号不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178647842818, 1, 'gateway-admin-server', 1002013001, '模板参数({})缺失', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178647842819, 1, 'gateway-admin-server', 1002013002, '短信模板不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178647842820, 1, 'gateway-admin-server', 1002014000, '验证码不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178693980161, 1, 'gateway-admin-server', 1002014001, '验证码已过期', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178693980162, 1, 'gateway-admin-server', 1002014002, '验证码已使用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178693980163, 1, 'gateway-admin-server', 1002014003, '验证码不正确', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178693980164, 1, 'gateway-admin-server', 1002014004, '超过每日短信发送数量', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178693980165, 1, 'gateway-admin-server', 1002014005, '短信发送过于频率', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178756894721, 1, 'gateway-admin-server', 1002014006, '手机号已被使用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178756894722, 1, 'gateway-admin-server', 1002014007, '验证码未被使用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178756894723, 1, 'gateway-admin-server', 1002015000, '租户不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178756894724, 1, 'gateway-admin-server', 1002015001, '名字为【{}】的租户已被禁用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178756894725, 1, 'gateway-admin-server', 1002015002, '名字为【{}】的租户已过期', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003585, 1, 'gateway-admin-server', 1002015003, '系统租户不能进行修改、删除等操作！', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003586, 1, 'gateway-admin-server', 1002016000, '租户套餐不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003587, 1, 'gateway-admin-server', 1002016001, '租户正在使用该套餐，请给租户重新设置套餐后再尝试删除', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003588, 1, 'gateway-admin-server', 1002016002, '名字为【{}】的租户套餐已被禁用', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003589, 1, 'gateway-admin-server', 1002017000, '错误码不存在', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003590, 1, 'gateway-admin-server', 1002017001, '已经存在编码为【{}】的错误码', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178824003591, 1, 'gateway-admin-server', 1002018000, '社交授权失败，原因是：{}', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178886918146, 1, 'gateway-admin-server', 1002018001, '社交解绑失败，非当前用户绑定', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');
INSERT INTO `sys_error_code` VALUES (1512820178886918147, 1, 'gateway-admin-server', 1002018002, '社交授权失败，找不到对应的用户', '', '-1', '2022-04-09 23:50:12', '-1', '2022-04-09 23:50:12', b'0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名字',
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标志',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由标识路径',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `sort` int(11) NULL DEFAULT 1 COMMENT '排序值',
  `keep_alive` bit(1) NULL DEFAULT b'0' COMMENT '是否缓存',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单类型 （0菜单 1按钮）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1000, '系统管理', NULL, '/system', -1, 'SettingIcon', 0, b'0', '0', '2018-09-28 08:29:53', '2021-12-27 15:13:30', '0');
INSERT INTO `sys_menu` VALUES (1100, '用户管理', NULL, '/system/user', 1000, 'MenuIcon', 1, b'0', '0', '2017-11-02 22:24:37', '2021-12-28 20:15:53', '0');
INSERT INTO `sys_menu` VALUES (1101, '用户新增', 'sys_user_add', NULL, 1100, NULL, 1, b'0', '1', '2017-11-08 09:52:09', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1102, '用户修改', 'sys_user_edit', NULL, 1100, NULL, 1, b'0', '1', '2017-11-08 09:52:48', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1103, '用户删除', 'sys_user_del', NULL, 1100, NULL, 1, b'0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1104, '导入导出', 'sys_user_export', NULL, 1100, NULL, 1, b'0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1200, '菜单管理', NULL, '/system/menu', 1000, 'MenuIcon', 2, b'0', '0', '2017-11-08 09:57:27', '2021-12-28 20:14:22', '0');
INSERT INTO `sys_menu` VALUES (1201, '菜单新增', 'sys_menu_add', NULL, 1200, NULL, 1, b'0', '1', '2017-11-08 10:15:53', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1202, '菜单修改', 'sys_menu_edit', NULL, 1200, NULL, 1, b'0', '1', '2017-11-08 10:16:23', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1203, '菜单删除', 'sys_menu_del', NULL, 1200, NULL, 1, b'0', '1', '2017-11-08 10:16:43', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1300, '角色管理', NULL, '/system/role', 1000, 'MenuIcon', 3, b'0', '0', '2017-11-08 10:13:37', '2021-12-28 20:14:19', '0');
INSERT INTO `sys_menu` VALUES (1301, '角色新增', 'sys_role_add', NULL, 1300, NULL, 1, b'0', '1', '2017-11-08 10:14:18', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1302, '角色修改', 'sys_role_edit', NULL, 1300, NULL, 1, b'0', '1', '2017-11-08 10:14:41', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1303, '角色删除', 'sys_role_del', NULL, 1300, NULL, 1, b'0', '1', '2017-11-08 10:14:59', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1304, '分配权限', 'sys_role_perm', NULL, 1300, NULL, 1, b'0', '1', '2018-04-20 07:22:55', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1400, '部门管理', NULL, '/system/department', 1000, 'MenuIcon', 4, b'0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', '0');
INSERT INTO `sys_menu` VALUES (1401, '部门新增', 'sys_dept_add', NULL, 1400, NULL, 1, b'0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1402, '部门修改', 'sys_dept_edit', NULL, 1400, NULL, 1, b'0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (1403, '部门删除', 'sys_dept_del', NULL, 1400, NULL, 1, b'0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2000, '网关管理', NULL, '/gateway', -1, 'OperationIcon', 1, b'0', '0', '2018-09-04 05:58:41', '2021-12-31 16:17:42', '0');
INSERT INTO `sys_menu` VALUES (2100, '网关日志', NULL, '/gateway/log', 2000, 'MenuIcon', 1, b'0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:25', '0');
INSERT INTO `sys_menu` VALUES (2101, '网关日志新增', 'gateway_log_add', NULL, 2100, NULL, 1, b'0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2102, '网关日志修改', 'gateway_log_edit', NULL, 2100, NULL, 1, b'0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2103, '网关日志删除', 'gateway_log_del', NULL, 2100, NULL, 1, b'0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2200, '网关路由', NULL, '/gateway/route', 2000, 'MenuIcon', 2, b'0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:28', '0');
INSERT INTO `sys_menu` VALUES (2201, '网关路由新增', 'gateway_route_add', NULL, 2200, NULL, 1, b'0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2202, '网关路由修改', 'gateway_route_edit', NULL, 2200, NULL, 1, b'0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2203, '网关路由删除', 'gateway_route_del', NULL, 2200, NULL, 1, b'0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2204, '网关路由拷贝', 'gateway_route_copy', NULL, 2200, NULL, 1, b'0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2300, '网关访问', NULL, '/gateway/access', 2000, 'MenuIcon', 3, b'0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:28', '0');
INSERT INTO `sys_menu` VALUES (2301, '网关访问新增', 'gateway_access_add', NULL, 2300, NULL, 1, b'0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2302, '网关访问修改', 'gateway_access_edit', NULL, 2300, NULL, 1, b'0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2303, '网关访问删除', 'gateway_access_del', NULL, 2300, NULL, 1, b'0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (2304, '网关访问状态启用禁用', 'gateway_access_status', NULL, 2300, NULL, 1, b'0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', '0');
INSERT INTO `sys_menu` VALUES (3000, '项目信息', NULL, '/project', -1, 'MapLocationIcon', 2, b'0', '0', '2018-09-04 05:58:41', '2021-12-31 16:17:42', '0');
INSERT INTO `sys_menu` VALUES (3100, '项目依赖', NULL, '/project/information', 3000, 'MenuIcon', 1, b'0', '0', '2018-09-04 05:58:41', '2021-12-28 20:14:25', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色code',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `ds_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '2' COMMENT '数据权限类型',
  `ds_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限范围',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`role_id`) USING BTREE,
  INDEX `role_idx1_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROLE_admin', '超级管理员', '0', '2', '2017-10-29 15:45:51', '2021-12-31 14:19:46', '0');
INSERT INTO `sys_role` VALUES (2, '普通用户', 'ROLE_normal', '普通用户', '0', '2', '2021-12-31 14:19:20', '2021-12-31 14:20:27', '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1000);
INSERT INTO `sys_role_menu` VALUES (1, 1100);
INSERT INTO `sys_role_menu` VALUES (1, 1101);
INSERT INTO `sys_role_menu` VALUES (1, 1102);
INSERT INTO `sys_role_menu` VALUES (1, 1103);
INSERT INTO `sys_role_menu` VALUES (1, 1104);
INSERT INTO `sys_role_menu` VALUES (1, 1200);
INSERT INTO `sys_role_menu` VALUES (1, 1201);
INSERT INTO `sys_role_menu` VALUES (1, 1202);
INSERT INTO `sys_role_menu` VALUES (1, 1203);
INSERT INTO `sys_role_menu` VALUES (1, 1300);
INSERT INTO `sys_role_menu` VALUES (1, 1301);
INSERT INTO `sys_role_menu` VALUES (1, 1302);
INSERT INTO `sys_role_menu` VALUES (1, 1303);
INSERT INTO `sys_role_menu` VALUES (1, 1304);
INSERT INTO `sys_role_menu` VALUES (1, 1400);
INSERT INTO `sys_role_menu` VALUES (1, 1401);
INSERT INTO `sys_role_menu` VALUES (1, 1402);
INSERT INTO `sys_role_menu` VALUES (1, 1403);
INSERT INTO `sys_role_menu` VALUES (1, 2000);
INSERT INTO `sys_role_menu` VALUES (1, 2100);
INSERT INTO `sys_role_menu` VALUES (1, 2101);
INSERT INTO `sys_role_menu` VALUES (1, 2102);
INSERT INTO `sys_role_menu` VALUES (1, 2103);
INSERT INTO `sys_role_menu` VALUES (1, 2200);
INSERT INTO `sys_role_menu` VALUES (1, 2201);
INSERT INTO `sys_role_menu` VALUES (1, 2202);
INSERT INTO `sys_role_menu` VALUES (1, 2203);
INSERT INTO `sys_role_menu` VALUES (1, 2204);
INSERT INTO `sys_role_menu` VALUES (1, 2300);
INSERT INTO `sys_role_menu` VALUES (1, 2301);
INSERT INTO `sys_role_menu` VALUES (1, 2302);
INSERT INTO `sys_role_menu` VALUES (1, 2303);
INSERT INTO `sys_role_menu` VALUES (1, 2304);
INSERT INTO `sys_role_menu` VALUES (1, 3000);
INSERT INTO `sys_role_menu` VALUES (1, 3100);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2100);
INSERT INTO `sys_role_menu` VALUES (2, 2101);
INSERT INTO `sys_role_menu` VALUES (2, 2102);
INSERT INTO `sys_role_menu` VALUES (2, 2103);
INSERT INTO `sys_role_menu` VALUES (2, 2200);
INSERT INTO `sys_role_menu` VALUES (2, 2201);
INSERT INTO `sys_role_menu` VALUES (2, 2202);
INSERT INTO `sys_role_menu` VALUES (2, 2203);
INSERT INTO `sys_role_menu` VALUES (2, 2300);
INSERT INTO `sys_role_menu` VALUES (2, 2301);
INSERT INTO `sys_role_menu` VALUES (2, 2302);
INSERT INTO `sys_role_menu` VALUES (2, 2303);
INSERT INTO `sys_role_menu` VALUES (2, 3000);
INSERT INTO `sys_role_menu` VALUES (2, 3100);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录IP',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `lock_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否锁定',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `user_idx1_username`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 1, 'li7hai26@gmail.com', '17034642888', NULL, '2022-01-06 15:47:04', '127.0.0.1', 1, 0, '2018-04-20 07:15:18', '2022-01-06 18:13:43', '0');
INSERT INTO `sys_user` VALUES (2, 'editor', 'editor-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 0, 'li7hai26@outlook.com', '17034642888', NULL, '2022-01-06 15:47:08', '127.0.0.1', 5, 0, '2021-12-31 16:53:14', '2022-01-06 18:40:33', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
