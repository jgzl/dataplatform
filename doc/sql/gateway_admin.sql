CREATE DATABASE /*!32312 IF NOT EXISTS */ `dataplatform` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `dataplatform`;

/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.192.2
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 192.168.192.2:3306
 Source Schema         : dataplatform

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 10/01/2023 22:36:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_access_conf
-- ----------------------------
DROP TABLE IF EXISTS `gateway_access_conf`;
CREATE TABLE `gateway_access_conf` (
                                       `id` varchar(64) NOT NULL COMMENT 'ID',
                                       `api_key` varchar(64) DEFAULT NULL,
                                       `api_secret` varchar(64) DEFAULT NULL,
                                       `system` varchar(64) DEFAULT NULL,
                                       `status` char(1) DEFAULT NULL,
                                       `remark` longtext,
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                       `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                       `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                       `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-网关访问';

-- ----------------------------
-- Records of gateway_access_conf
-- ----------------------------
BEGIN;
INSERT INTO `gateway_access_conf` (`id`, `api_key`, `api_secret`, `system`, `status`, `remark`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'testApiKey', 'testApiSecret', 'testApi', '0', '', '2022-06-04 01:27:53', '2022-06-04 01:27:27', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for gateway_application
-- ----------------------------
DROP TABLE IF EXISTS `gateway_application`;
CREATE TABLE `gateway_application` (
                                       `id` varchar(64) NOT NULL COMMENT 'ID',
                                       `application_code` varchar(64) DEFAULT NULL COMMENT '应用编码',
                                       `application_name` varchar(64) DEFAULT NULL COMMENT '应用名称',
                                       `deployment_mode` varchar(64) DEFAULT NULL COMMENT '部署模式',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                       `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                       `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                       `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-网关访问';

-- ----------------------------
-- Records of gateway_application
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gateway_log
-- ----------------------------
DROP TABLE IF EXISTS `gateway_log`;
CREATE TABLE `gateway_log` (
                               `id` varchar(64) NOT NULL COMMENT 'ID',
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
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-网关日志';

-- ----------------------------
-- Records of gateway_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gateway_metadata
-- ----------------------------
DROP TABLE IF EXISTS `gateway_metadata`;
CREATE TABLE `gateway_metadata` (
                                    `id` varchar(64) NOT NULL COMMENT 'ID',
                                    `application_code` varchar(64) DEFAULT NULL COMMENT '应用编码-提供服务方',
                                    `method` varchar(64) DEFAULT NULL COMMENT '请求类型|GET',
                                    `path` varchar(64) DEFAULT NULL COMMENT '请求路径',
                                    `description` varchar(255) DEFAULT NULL COMMENT '接口描述',
                                    `rpc_type` varchar(64) DEFAULT NULL COMMENT 'RPC接口类型(http,dubbo)',
                                    `rpc_extra` varchar(2000) DEFAULT NULL COMMENT 'RPC扩展参数',
                                    `tag` varchar(64) DEFAULT NULL COMMENT '接口标签/请求标签',
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                    `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                    `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                    `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-网关访问';

-- ----------------------------
-- Records of gateway_metadata
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gateway_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_conf`;
CREATE TABLE `gateway_route_conf` (
                                      `id` varchar(64) NOT NULL COMMENT 'ID',
                                      `route_name` varchar(30) DEFAULT NULL,
                                      `route_id` varchar(30) DEFAULT NULL,
                                      `predicates` longtext,
                                      `filters` longtext,
                                      `uri` longtext,
                                      `sort` tinyint(4) DEFAULT NULL,
                                      `metadata` longtext,
                                      `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                      `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                      `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                      `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-网关路由';

-- ----------------------------
-- Records of gateway_route_conf
-- ----------------------------
BEGIN;
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'gatewayMonitorDO-center', 'gatewayMonitorDO-center', '[{\"args\": {\"_genkey_0\": \"/monitor-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://monitor-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:14:45', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('2', 'baidu', 'baidu', '[{\"args\": {\"_genkey_0\": \"/baidu/**\"}, \"name\": \"Path\"}]', '[]', 'https://www.baidu.com', 0, '{\"version\": \"1\"}', '2022-06-03 08:39:53', '2022-12-10 22:14:53', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('3', 'auth-center', 'auth-center', '[{\"args\": {\"_genkey_0\": \"/auth-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://auth-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:00', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('4', 'system-center', 'system-center', '[{\"args\": {\"_genkey_0\": \"/system-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://system-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:08', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('5', 'gateway-admin-center', 'gateway-admin-center', '[{\"args\": {\"_genkey_0\": \"/gateway-admin/**\"}, \"name\": \"Path\"}]', '[]', 'lb://gateway-admin-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:15', '1', '1', '0', 3);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('6', 'message-center', 'message-center', '[{\"args\": {\"_genkey_0\": \"/message-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://message-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:23', '1', '1', '0', 2);
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_column`;
CREATE TABLE `infra_codegen_column` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                        `table_id` bigint(20) NOT NULL COMMENT '表编号',
                                        `column_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
                                        `data_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段类型',
                                        `column_comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段描述',
                                        `nullable` bit(1) NOT NULL COMMENT '是否允许为空',
                                        `primary_key` bit(1) NOT NULL COMMENT '是否主键',
                                        `auto_increment` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否自增',
                                        `ordinal_position` int(11) NOT NULL COMMENT '排序',
                                        `java_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性类型',
                                        `java_field` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性名',
                                        `dict_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典类型',
                                        `example` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据示例',
                                        `create_operation` bit(1) NOT NULL COMMENT '是否为 Create 创建操作的字段',
                                        `update_operation` bit(1) NOT NULL COMMENT '是否为 Update 更新操作的字段',
                                        `list_operation` bit(1) NOT NULL COMMENT '是否为 List 查询操作的字段',
                                        `list_operation_condition` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
                                        `list_operation_result` bit(1) NOT NULL COMMENT '是否为 List 查询操作的返回字段',
                                        `html_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示类型',
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `creator` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '创建人',
                                        `updater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '更新人',
                                        `deleted` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                        `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基础-代码生成表字段定义';

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_table`;
CREATE TABLE `infra_codegen_table` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                       `data_source_config_id` bigint(20) NOT NULL COMMENT '数据源配置的编号',
                                       `scene` tinyint(4) NOT NULL DEFAULT '1' COMMENT '生成场景',
                                       `table_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
                                       `table_comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
                                       `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                                       `module_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块名',
                                       `business_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务名',
                                       `class_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名称',
                                       `class_comment` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类描述',
                                       `author` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '作者',
                                       `template_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '模板类型',
                                       `parent_menu_id` bigint(20) DEFAULT NULL COMMENT '父菜单编号',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `creator` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '创建人',
                                       `updater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '更新人',
                                       `deleted` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                       `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基础-代码生成表定义';

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_data_source_config`;
CREATE TABLE `infra_data_source_config` (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
                                            `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
                                            `url` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
                                            `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                                            `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
                                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                            `creator` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '创建人',
                                            `updater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '更新人',
                                            `deleted` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基础-数据源配置表';

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for metadata_authorize
-- ----------------------------
DROP TABLE IF EXISTS `metadata_authorize`;
CREATE TABLE `metadata_authorize` (
                                      `id` bigint(20) NOT NULL COMMENT 'ID',
                                      `object_id` bigint(20) NOT NULL COMMENT 'ID',
                                      `role_id` bigint(20) NOT NULL COMMENT 'ID',
                                      `object_type` varchar(10) DEFAULT NULL,
                                      PRIMARY KEY (`id`,`object_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元数据-认证';

-- ----------------------------
-- Records of metadata_authorize
-- ----------------------------
BEGIN;
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728732931301378, 1336474987430793217, 1319084037507244034, 'database');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728733359120386, 1336479261791473665, 1319084037507244034, 'table');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728733749190658, 1336479262852632577, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728734139260930, 1336479263477583874, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728734529331201, 1336479264106729474, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728734923595777, 1336479264639406082, 1319084037507244034, 'table');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728735317860354, 1336479265583124482, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728735707930626, 1336479266149355521, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728736093806593, 1336479266728169473, 1319084037507244034, 'table');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728736483876865, 1336479267583807489, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728736878141442, 1336479268242313218, 1319084037507244034, 'column');
INSERT INTO `metadata_authorize` (`id`, `object_id`, `role_id`, `object_type`) VALUES (1339728737268211713, 1336479268821127170, 1319084037507244034, 'column');
COMMIT;

-- ----------------------------
-- Table structure for metadata_change_record
-- ----------------------------
DROP TABLE IF EXISTS `metadata_change_record`;
CREATE TABLE `metadata_change_record` (
                                          `id` bigint(20) NOT NULL COMMENT 'ID',
                                          `creator` varchar(64) NOT NULL DEFAULT '1',
                                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                          `create_dept` bigint(20) NOT NULL DEFAULT '1',
                                          `updater` varchar(64) NOT NULL DEFAULT '1',
                                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                          `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT ';0:,0',
                                          `version` bigint(20) NOT NULL DEFAULT '1',
                                          `object_type` varchar(255) DEFAULT NULL,
                                          `object_id` varchar(255) DEFAULT NULL,
                                          `field_name` varchar(255) DEFAULT NULL,
                                          `field_old_value` varchar(255) DEFAULT NULL,
                                          `field_new_value` varchar(255) DEFAULT NULL,
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元数据-变更';

-- ----------------------------
-- Records of metadata_change_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for metadata_column
-- ----------------------------
DROP TABLE IF EXISTS `metadata_column`;
CREATE TABLE `metadata_column` (
                                   `id` varchar(64) NOT NULL COMMENT 'ID',
                                   `source_id` varchar(255) DEFAULT NULL,
                                   `table_id` varchar(255) DEFAULT NULL,
                                   `column_name` varchar(255) DEFAULT NULL,
                                   `column_comment` varchar(255) DEFAULT NULL,
                                   `column_key` varchar(255) DEFAULT NULL COMMENT '(10)',
                                   `column_nullable` varchar(255) DEFAULT NULL COMMENT '(10)',
                                   `column_position` int(11) DEFAULT NULL,
                                   `data_type` varchar(255) DEFAULT NULL,
                                   `data_length` varchar(255) DEFAULT NULL,
                                   `data_precision` varchar(255) DEFAULT NULL,
                                   `data_scale` varchar(255) DEFAULT NULL,
                                   `data_default` varchar(255) DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元数据-列';

-- ----------------------------
-- Records of metadata_column
-- ----------------------------
BEGIN;
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479262852632577', '1336474987430793217', '1336479261791473665', 'id', '', '1', '0', 1, 'varchar', '50', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479263477583874', '1336474987430793217', '1336479261791473665', 'patient_name', '', '0', '1', 2, 'varchar', '255', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479264106729474', '1336474987430793217', '1336479261791473665', 'patient_sex', '12', '0', '1', 3, 'varchar', '255', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479265583124482', '1336474987430793217', '1336479264639406082', 'id', '', '1', '0', 1, 'varchar', '50', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479266149355521', '1336474987430793217', '1336479264639406082', 'part_name', '', '0', '1', 2, 'varchar', '255', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479267583807489', '1336474987430793217', '1336479266728169473', 'id', '', '1', '0', 1, 'varchar', '50', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479268242313218', '1336474987430793217', '1336479266728169473', 'part_id', '', '0', '1', 2, 'varchar', '50', NULL, NULL, NULL);
INSERT INTO `metadata_column` (`id`, `source_id`, `table_id`, `column_name`, `column_comment`, `column_key`, `column_nullable`, `column_position`, `data_type`, `data_length`, `data_precision`, `data_scale`, `data_default`) VALUES ('1336479268821127170', '1336474987430793217', '1336479266728169473', 'type_name', '', '0', '1', 3, 'varchar', '255', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for metadata_source
-- ----------------------------
DROP TABLE IF EXISTS `metadata_source`;
CREATE TABLE `metadata_source` (
                                   `id` varchar(64) NOT NULL COMMENT 'ID',
                                   `creator` varchar(64) NOT NULL DEFAULT '1',
                                   `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                   `create_dept` varchar(64) NOT NULL DEFAULT '1',
                                   `updater` varchar(64) NOT NULL DEFAULT '1',
                                   `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                   `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT ';0:,0',
                                   `version` bigint(20) NOT NULL DEFAULT '1',
                                   `db_type` tinyint(4) DEFAULT NULL,
                                   `source_name` varchar(64) DEFAULT NULL,
                                   `is_sync` char(1) DEFAULT NULL COMMENT '01',
                                   `db_schema` json DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元数据-源';

-- ----------------------------
-- Records of metadata_source
-- ----------------------------
BEGIN;
INSERT INTO `metadata_source` (`id`, `creator`, `create_time`, `create_dept`, `updater`, `update_time`, `deleted`, `version`, `db_type`, `source_name`, `is_sync`, `db_schema`) VALUES ('1240185865539600385', '1214835832967581698', '2020-03-19 03:58:47', '1197789917762031617', '1214835832967581698', '2020-12-09 21:51:01', '0', 1, 1, 'mysql', '0', '{\"sid\": null, \"host\": \"localhost\", \"port\": 3306, \"dbName\": \"foodmart2\", \"password\": \"1234@abcd\", \"username\": \"root\"}');
INSERT INTO `metadata_source` (`id`, `creator`, `create_time`, `create_dept`, `updater`, `update_time`, `deleted`, `version`, `db_type`, `source_name`, `is_sync`, `db_schema`) VALUES ('1336474987430793217', '1214835832967581698', '2020-12-09 21:57:22', '1197789917762031617', '1214835832967581698', '2020-12-09 21:57:22', '0', 1, 1, 'robot', '2', '{\"sid\": null, \"host\": \"localhost\", \"port\": 3306, \"dbName\": \"robot\", \"password\": \"1234@abcd\", \"username\": \"root\"}');
COMMIT;

-- ----------------------------
-- Table structure for metadata_table
-- ----------------------------
DROP TABLE IF EXISTS `metadata_table`;
CREATE TABLE `metadata_table` (
                                  `id` varchar(64) NOT NULL COMMENT 'ID',
                                  `source_id` varchar(255) DEFAULT NULL,
                                  `table_name` varchar(255) DEFAULT NULL,
                                  `table_comment` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元数据-表';

-- ----------------------------
-- Records of metadata_table
-- ----------------------------
BEGIN;
INSERT INTO `metadata_table` (`id`, `source_id`, `table_name`, `table_comment`) VALUES ('1336479261791473665', '1336474987430793217', 'robot_patient', '');
INSERT INTO `metadata_table` (`id`, `source_id`, `table_name`, `table_comment`) VALUES ('1336479264639406082', '1336474987430793217', 'robot_symptom_part', '');
INSERT INTO `metadata_table` (`id`, `source_id`, `table_name`, `table_comment`) VALUES ('1336479266728169473', '1336474987430793217', 'robot_symptom_type', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
                            `id` varchar(64) NOT NULL COMMENT '主键ID',
                            `name` varchar(50) DEFAULT NULL,
                            `sort` tinyint(4) DEFAULT NULL,
                            `parent_id` varchar(64) DEFAULT NULL,
                            `status` tinyint(3) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                            `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                            `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', '山东', 1, '-1', 1, '2018-01-22 11:00:23', '2021-12-31 06:33:51', '1', '1', '0', 1);
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('2', '沙县国际', 2, '-1', 1, '2018-01-22 11:00:38', '2021-12-31 06:33:51', '1', '1', '0', 1);
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('3', '潍坊', 3, '1', 1, '2018-01-22 11:00:44', '2021-12-31 06:33:51', '1', '1', '0', 1);
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('4', '高新', 4, '3', 1, '2018-01-22 11:00:52', '2021-12-31 06:33:51', '1', '1', '0', 1);
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('5', '院校', 5, '4', 1, '2018-01-22 11:00:57', '2021-12-31 06:33:51', '1', '1', '0', 1);
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('7', '山东沙县', 7, '2', 1, '2018-01-22 11:01:57', '2021-12-31 06:33:51', '1', '1', '0', 1);
INSERT INTO `sys_dept` (`id`, `name`, `sort`, `parent_id`, `status`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('8', '潍坊沙县', 8, '7', 1, '2018-01-22 11:02:03', '2021-12-31 06:33:51', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_relation`;
CREATE TABLE `sys_dept_relation` (
                                     `id` varchar(64) NOT NULL COMMENT 'ID',
                                     `ancestor` varchar(64) DEFAULT NULL,
                                     `descendant` varchar(64) DEFAULT NULL,
                                     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                     `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                     `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                     `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-部门关联';

-- ----------------------------
-- Records of sys_dept_relation
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1010', '10', '10', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11', '1', '1', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('110', '1', '10', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('13', '1', '3', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('14', '1', '4', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('15', '1', '5', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('19', '1', '9', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('211', '2', '11', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('212', '2', '12', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('22', '2', '2', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('27', '2', '7', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('28', '2', '8', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('33', '3', '3', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('34', '3', '4', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('35', '3', '5', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('44', '4', '4', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('45', '4', '5', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('55', '5', '5', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('711', '7', '11', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('712', '7', '12', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('77', '7', '7', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('78', '7', '8', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('88', '8', '8', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_dept_relation` (`id`, `ancestor`, `descendant`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('99', '9', '9', '2022-09-19 17:01:05', '2022-09-19 17:01:06', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_error_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_code`;
CREATE TABLE `sys_error_code` (
                                  `id` varchar(64) NOT NULL COMMENT 'ID',
                                  `type` tinyint(3) DEFAULT NULL,
                                  `application_name` varchar(50) DEFAULT NULL,
                                  `code` int(11) DEFAULT NULL,
                                  `message` varchar(512) DEFAULT NULL,
                                  `memo` varchar(512) DEFAULT NULL,
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                  `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                  `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                  `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-错误码';

-- ----------------------------
-- Records of sys_error_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
                            `id` varchar(64) NOT NULL COMMENT '主键ID',
                            `name` varchar(32) DEFAULT NULL,
                            `permission` varchar(32) DEFAULT NULL,
                            `path` varchar(128) DEFAULT NULL,
                            `parent_id` varchar(64) DEFAULT NULL,
                            `icon` varchar(32) DEFAULT NULL,
                            `sort` tinyint(4) DEFAULT NULL,
                            `keep_alive` char(1) DEFAULT NULL,
                            `type` char(1) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            `component` varchar(128) DEFAULT NULL COMMENT '视图',
                            `tag` varchar(64) DEFAULT NULL COMMENT '标签',
                            `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                            `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                            `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('0', '首页', NULL, '/home', '-1', 'el-icon-eleme-filled', 0, '0', '0', '2018-09-28 08:29:53', '2021-12-27 15:13:30', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('100', '工作台', NULL, '/dashboard', '0', 'el-icon-menu', 1, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'home', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1000', '系统管理', NULL, '/system', '-1', 'el-icon-tools', 10, '0', '0', '2017-11-02 22:24:37', '2022-10-19 14:02:45', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1100', '用户管理', NULL, '/system/user', '1000', 'el-icon-user-filled', 1, '0', '0', '2017-11-02 22:24:37', '2021-12-28 20:15:53', 'system/user', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1101', '用户新增', 'system:sys-user:create', NULL, '1100', NULL, 1, '0', '1', '2017-11-08 09:52:09', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1102', '用户修改', 'system:sys-user:update', NULL, '1100', NULL, 1, '0', '1', '2017-11-08 09:52:48', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1103', '用户删除', 'system:sys-user:delete', NULL, '1100', NULL, 1, '0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1104', '用户查询', 'system:sys-user:query', NULL, '1100', NULL, 1, '0', '1', '2017-11-08 09:54:01', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1105', '用户导出', 'system:sys-user:export', NULL, '1100', NULL, 1, '0', '1', '2022-10-20 11:59:55', '2022-10-20 11:59:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('11100', '计划任务', NULL, '/system/task', '1000', 'el-icon-alarm-clock', 8, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/task', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('11200', '错误码管理', NULL, '/system/errorcode', '1000', 'el-icon-user-filled', 5, '0', '0', '2022-09-19 17:01:55', '2022-09-19 17:01:55', 'system/errorcode', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('11201', '错误码新增', 'system:sys-error-code:create', NULL, '11200', NULL, 1, '0', '1', '2022-09-19 17:01:55', '2022-09-19 17:01:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('11202', '错误码修改', 'system:sys-error-code:update', NULL, '11200', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('11203', '错误码删除', 'system:sys-error-code:delete', NULL, '11200', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('11204', '错误码查询', 'system:sys-error-code:query', NULL, '11200', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1200', '菜单管理', NULL, '/system/menu', '1000', 'el-icon-fold', 2, '0', '0', '2017-11-08 09:57:27', '2021-12-28 20:14:22', 'system/menu', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1201', '菜单新增', 'system:sys-menu:create', NULL, '1200', NULL, 1, '0', '1', '2017-11-08 10:15:53', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1202', '菜单修改', 'system:sys-menu:update', NULL, '1200', NULL, 1, '0', '1', '2017-11-08 10:16:23', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1203', '菜单删除', 'system:sys-menu:delete', NULL, '1200', NULL, 1, '0', '1', '2017-11-08 10:16:43', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1204', '菜单查询', 'system:sys-menu:query', NULL, '1200', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1300', '角色管理', NULL, '/system/role', '1000', 'el-icon-notebook', 3, '0', '0', '2017-11-08 10:13:37', '2021-12-28 20:14:19', 'system/role', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1301', '角色新增', 'system:sys-role:create', NULL, '1300', NULL, 1, '0', '1', '2017-11-08 10:14:18', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1302', '角色修改', 'system:sys-role:update', NULL, '1300', NULL, 1, '0', '1', '2017-11-08 10:14:41', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1303', '角色删除', 'system:sys-role:delete', NULL, '1300', NULL, 1, '0', '1', '2017-11-08 10:14:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1304', '角色查询', 'system:sys-role:query', NULL, '1300', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1305', '分配权限', 'system:sys-role:permission', NULL, '1300', NULL, 1, '0', '1', '2018-04-20 07:22:55', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1400', '部门管理', NULL, '/system/dept', '1000', 'sc-icon-organization', 4, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/dept', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1401', '部门新增', 'system:sys-dept:create', NULL, '1400', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1402', '部门修改', 'system:sys-dept:update', NULL, '1400', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1403', '部门删除', 'system:sys-dept:delete', NULL, '1400', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1404', '部门错误码查询', 'system:sys-dept:query', NULL, '1400', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1500', '字典管理', NULL, '/system/dic', '1000', 'el-icon-document', 5, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/dic', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1600', '应用管理', NULL, '/system/gatewayClientDO', '1000', 'el-icon-help-filled', 6, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/gatewayClientDO', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1700', '表格列管理', NULL, '/system/table', '1000', 'el-icon-scale-to-original', 7, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/table', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1800', '系统设置', NULL, '/system/setting', '1000', 'el-icon-tools', 10, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/setting', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1900', '系统日志', NULL, '/system/log', '1000', 'el-icon-warning', 9, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/log', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('200', '用户中心', NULL, '/userCenter', '0', 'el-icon-user', 2, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'userCenter', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2000', '网关管理', NULL, '/gateway', '-1', 'el-icon-coin', 20, '0', '0', '2018-09-04 05:58:41', '2023-01-10 22:34:34', 'gateway', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2100', '网关日志', NULL, '/gateway/log', '2000', 'el-icon-edit-pen', 1, '0', '0', '2018-09-04 05:58:41', '2022-09-23 00:19:37', 'gateway/log', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2101', '网关日志新增', 'gateway:gateway-log:create', NULL, '2100', NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2102', '网关日志修改', 'gateway:gateway-log:update', NULL, '2100', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2103', '网关日志删除', 'gateway:gateway-log:delete', NULL, '2100', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2104', '网关日志查询', 'gateway:gateway-log:query', NULL, '2100', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2200', '网关路由', NULL, '/gateway/gatewayRouteDO', '2000', 'el-icon-platform', 2, '0', '0', '2018-09-04 05:58:41', '2022-09-23 00:20:39', 'gateway/gatewayRouteDO', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2201', '网关路由新增', 'gateway:gateway-gatewayRouteDO:create', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2202', '网关路由修改', 'gateway:gateway-gatewayRouteDO:update', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2203', '网关路由删除', 'gateway:gateway-gatewayRouteDO:delete', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2204', '网关路由查询', 'gateway:gateway-gatewayRouteDO:query', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2300', '网关访问', NULL, '/gateway/access', '2000', 'el-icon-avatar', 3, '0', '0', '2018-09-04 05:58:41', '2022-09-23 00:20:50', 'gateway/access', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2301', '网关访问新增', 'gateway:gateway-access:create', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2302', '网关访问修改', 'gateway:gateway-access:update', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2303', '网关访问删除', 'gateway:gateway-access:delete', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2304', '网关访问查询', 'gateway:gateway-access:query', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2400', '网关元数据管理', NULL, '/gateway/metadata', '2000', 'el-icon-collection-tag', 5, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/metadata', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2401', '网关元数据新增', 'gateway:gateway-metadata:create', NULL, '2400', NULL, 1, '0', '1', '2022-09-19 17:01:58', '2022-09-19 17:01:58', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2402', '网关元数据修改', 'gateway:gateway-metadata:update', NULL, '2400', NULL, 1, '0', '1', '2022-09-19 17:01:58', '2022-09-19 17:01:58', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2403', '网关元数据删除', 'gateway:gateway-metadata:delete', NULL, '2400', NULL, 1, '0', '1', '2022-09-19 17:01:59', '2022-09-19 17:01:59', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2404', '网关元数据查询', 'gateway:gateway-metadata:query', NULL, '2400', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2500', '网关应用', NULL, '/gateway/application', '2000', 'el-icon-collection-tag', 5, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/application', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2501', '网关应用新增', 'gateway:gateway-application:crea', NULL, '2500', NULL, 1, '0', '1', '2022-09-19 17:01:58', '2022-09-19 17:01:58', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2502', '网关应用修改', 'gateway:gateway-application:upda', NULL, '2500', NULL, 1, '0', '1', '2022-09-19 17:01:58', '2022-09-19 17:01:58', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2503', '网关应用删除', 'gateway:gateway-application:dele', NULL, '2500', NULL, 1, '0', '1', '2022-09-19 17:01:59', '2022-09-19 17:01:59', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2504', '网关应用查询', 'gateway:gateway-application:quer', NULL, '2500', NULL, 1, '0', '1', '2022-09-19 17:01:56', '2022-09-19 17:01:56', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('3000', '关于', NULL, '/other/about', '-1', 'el-icon-info-filled', 30, '0', '0', '2018-09-04 05:58:41', '2022-09-23 00:24:30', 'other/about', NULL, '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details` (
                                            `id` varchar(64) NOT NULL COMMENT 'ID',
                                            `client_id` varchar(32) NOT NULL COMMENT 'ID',
                                            `resource_ids` varchar(256) DEFAULT NULL,
                                            `client_secret` varchar(256) DEFAULT NULL,
                                            `scope` varchar(256) DEFAULT NULL,
                                            `authorized_grant_types` varchar(256) DEFAULT NULL,
                                            `web_server_redirect_uri` varchar(256) DEFAULT NULL,
                                            `authorities` varchar(256) DEFAULT NULL,
                                            `access_token_validity` int(11) DEFAULT NULL COMMENT 'token ',
                                            `refresh_token_validity` int(11) DEFAULT NULL,
                                            `additional_information` varchar(4096) DEFAULT NULL COMMENT 'JSON',
                                            `autoapprove` varchar(256) DEFAULT NULL,
                                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                            `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                            `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                            `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE KEY `sys_oauth_client_details_client_id_IDX` (`client_id`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-应用';

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `sys_oauth_client_details` (`id`, `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'app', NULL, 'app', 'server', 'app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_oauth_client_details` (`id`, `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('2', 'system-center', NULL, 'system-center', 'server', 'password,app,refresh_token,authorization_code,client_credentials', 'https://www.baidu.com', NULL, NULL, NULL, NULL, 'true', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_oauth_client_details` (`id`, `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('3', 'gateway-admin-center', NULL, 'gateway-admin-center', 'server', 'password,app,refresh_token,authorization_code,client_credentials', 'https://www.baidu.com', NULL, NULL, NULL, NULL, 'true', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_oauth_client_details` (`id`, `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('4', 'message-center', NULL, 'message-center', 'server', 'password,app,refresh_token,authorization_code,client_credentials', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_oauth_client_details` (`id`, `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('5', 'tool-center', NULL, 'tool-center', 'server', 'password,app,refresh_token,authorization_code,client_credentials', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` varchar(64) NOT NULL COMMENT '主键ID',
                            `role_name` varchar(64) DEFAULT NULL,
                            `role_code` varchar(64) DEFAULT NULL,
                            `role_desc` varchar(255) DEFAULT NULL,
                            `ds_type` char(1) DEFAULT NULL,
                            `ds_scope` varchar(255) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                            `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                            `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `role_desc`, `ds_type`, `ds_scope`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', '超级管理员', 'ROLE_admin', '超级管理员', '0', '2', '2017-10-29 07:45:51', '2023-01-10 22:34:15', '1', '1', '0', 2);
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `role_desc`, `ds_type`, `ds_scope`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('2', '普通用户', 'ROLE_normal', '普通用户', '0', '2', '2021-12-31 06:19:20', '2021-12-31 06:20:27', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
                                 `id` varchar(64) NOT NULL COMMENT 'ID',
                                 `role_id` int(11) DEFAULT NULL,
                                 `menu_id` int(11) DEFAULT NULL,
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                 `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                 `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                 `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-角色菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('10', 1, 0, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1100', 1, 100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11000', 1, 1000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11100', 1, 1100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11101', 1, 1101, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11102', 1, 1102, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11103', 1, 1103, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11104', 1, 1104, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('111100', 1, 11100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('111200', 1, 11200, '2022-09-19 17:01:56', '2022-09-19 17:01:56', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('111201', 1, 11201, '2022-09-19 17:01:57', '2022-09-19 17:01:57', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('111202', 1, 11202, '2022-09-19 17:01:57', '2022-09-19 17:01:57', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('111203', 1, 11203, '2022-09-19 17:01:57', '2022-09-19 17:01:57', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('111204', 1, 11204, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11200', 1, 1200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11201', 1, 1201, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11202', 1, 1202, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11203', 1, 1203, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11204', 1, 1204, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11300', 1, 1300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11301', 1, 1301, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11302', 1, 1302, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11303', 1, 1303, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11304', 1, 1304, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11305', 1, 1305, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11400', 1, 1400, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11401', 1, 1401, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11402', 1, 1402, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11403', 1, 1403, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11404', 1, 1404, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11500', 1, 1500, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11600', 1, 1600, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11700', 1, 1700, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11800', 1, 1800, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11900', 1, 1900, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1200', 1, 200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12000', 1, 2000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12100', 1, 2100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12101', 1, 2101, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12102', 1, 2102, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12103', 1, 2103, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12104', 1, 2104, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12200', 1, 2200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12201', 1, 2201, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12202', 1, 2202, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12203', 1, 2203, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12204', 1, 2204, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12300', 1, 2300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12301', 1, 2301, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12302', 1, 2302, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12303', 1, 2303, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12304', 1, 2304, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12400', 1, 2400, '2022-09-19 17:01:59', '2022-09-19 17:01:59', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12401', 1, 2401, '2022-09-19 17:01:59', '2022-09-19 17:01:59', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12402', 1, 2402, '2022-09-19 17:02:00', '2022-09-19 17:02:00', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12403', 1, 2403, '2022-09-19 17:02:00', '2022-09-19 17:02:00', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12404', 1, 2404, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12500', 1, 2500, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12501', 1, 2501, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12502', 1, 2502, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12503', 1, 2503, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12504', 1, 2504, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('13000', 1, 3000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('22200', 2, 2200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('22300', 2, 2300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('23000', 2, 3000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('30', 3, 0, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('3100', 3, 100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('3200', 3, 200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('33000', 3, 3000, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` varchar(64) NOT NULL COMMENT 'ID',
                            `user_name` varchar(64) DEFAULT NULL,
                            `nick_name` varchar(64) DEFAULT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `salt` varchar(255) DEFAULT NULL,
                            `gender` tinyint(4) DEFAULT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `mobile` varchar(20) DEFAULT NULL,
                            `avatar` varchar(255) DEFAULT NULL,
                            `last_login_time` datetime DEFAULT NULL,
                            `last_login_ip` varchar(64) DEFAULT NULL COMMENT 'ip',
                            `dept_id` varchar(64) DEFAULT NULL COMMENT 'id',
                            `lock_flag` tinyint(4) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                            `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                            `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                            `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `nick_name`, `password`, `salt`, `gender`, `email`, `mobile`, `avatar`, `last_login_time`, `last_login_ip`, `dept_id`, `lock_flag`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'admin', 'admin-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 1, 'li7hai26@gmail.com', '17034642888', '', '2022-01-06 07:47:04', '127.0.0.1', '1', 0, '2018-04-19 23:15:18', '2022-07-21 14:03:01', '1', '1', '0', 1);
INSERT INTO `sys_user` (`id`, `user_name`, `nick_name`, `password`, `salt`, `gender`, `email`, `mobile`, `avatar`, `last_login_time`, `last_login_ip`, `dept_id`, `lock_flag`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('2', 'editor', 'editor-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 0, 'li7hai26@outlook.com', '17034642888', '', '2022-01-06 07:47:08', '127.0.0.1', '5', 0, '2021-12-31 08:53:14', '2022-01-06 10:40:33', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` varchar(64) NOT NULL COMMENT 'ID',
                                 `user_id` varchar(64) DEFAULT NULL,
                                 `role_id` varchar(64) DEFAULT NULL,
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `creator` varchar(64) NOT NULL DEFAULT '1' COMMENT '创建人',
                                 `updater` varchar(64) NOT NULL DEFAULT '1' COMMENT '更新人',
                                 `deleted` varchar(64) NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                 `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统-用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('11', '1', '1', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('12', '1', '2', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('122', '12', '2', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('22', '2', '2', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('32', '3', '2', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('91', '9', '1', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('92', '9', '2', '2022-09-19 17:01:06', '2022-09-19 17:01:06', '1', '1', '0', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
