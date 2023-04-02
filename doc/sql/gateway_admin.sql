CREATE DATABASE /*!32312 IF NOT EXISTS */ `dataplatform` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `dataplatform`;

/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.192.1
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 192.168.192.1:3306
 Source Schema         : dataplatform

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 02/04/2023 20:31:01
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
INSERT INTO `gateway_access_conf` (`id`, `api_key`, `api_secret`, `system`, `status`, `remark`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'testApiKey', 'testApiSecret', 'testApi', '0', 'test', '2022-06-04 01:27:53', '2023-01-10 22:58:56', '1', '1', '0', 2);
COMMIT;

-- ----------------------------
-- Table structure for gateway_apidoc
-- ----------------------------
DROP TABLE IF EXISTS `gateway_apidoc`;
CREATE TABLE `gateway_apidoc` (
                                  `id` varchar(40) NOT NULL COMMENT '主键，同route_id',
                                  `content` text COMMENT '内容',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-接口文档';

-- ----------------------------
-- Records of gateway_apidoc
-- ----------------------------
BEGIN;
INSERT INTO `gateway_apidoc` (`id`, `content`) VALUES ('auth-center', '百度首页');
INSERT INTO `gateway_apidoc` (`id`, `content`) VALUES ('baidu', '百度首页');
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
-- Table structure for gateway_balanced
-- ----------------------------
DROP TABLE IF EXISTS `gateway_balanced`;
CREATE TABLE `gateway_balanced` (
                                    `id` varchar(40) NOT NULL COMMENT '主键',
                                    `name` varchar(40) NOT NULL COMMENT '负载名称',
                                    `groupCode` varchar(40) NOT NULL COMMENT '分组编码',
                                    `loadUri` varchar(200) DEFAULT NULL COMMENT '负载地址',
                                    `status` varchar(2) DEFAULT NULL COMMENT '状态，0启用，1禁用',
                                    `createTime` datetime NOT NULL COMMENT '创建时间',
                                    `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                    `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-负载';

-- ----------------------------
-- Records of gateway_balanced
-- ----------------------------
BEGIN;
INSERT INTO `gateway_balanced` (`id`, `name`, `groupCode`, `loadUri`, `status`, `createTime`, `updateTime`, `remarks`) VALUES ('7e7c2c9e8b4841a08b1904224ad6d842', '测试', 'interior_api', 'test/**', '0', '2023-01-15 02:25:57', '2023-01-15 14:51:19', '测试');
INSERT INTO `gateway_balanced` (`id`, `name`, `groupCode`, `loadUri`, `status`, `createTime`, `updateTime`, `remarks`) VALUES ('a5da6f66c3b8432f83d959493bb0b792', '百度', 'public_api', 'baidu', '0', '2023-02-05 19:28:43', '2023-02-05 23:04:26', NULL);
COMMIT;

-- ----------------------------
-- Table structure for gateway_client
-- ----------------------------
DROP TABLE IF EXISTS `gateway_client`;
CREATE TABLE `gateway_client` (
                                  `id` varchar(40) NOT NULL COMMENT '主键,注册key',
                                  `systemCode` varchar(40) NOT NULL COMMENT '系统代号',
                                  `name` varchar(40) NOT NULL COMMENT '客户端名称',
                                  `groupCode` varchar(40) NOT NULL COMMENT '分组编码',
                                  `ip` varchar(16) DEFAULT NULL COMMENT '客户端IP',
                                  `status` varchar(2) DEFAULT NULL COMMENT '状态，0启用，1禁用',
                                  `createTime` datetime NOT NULL COMMENT '创建时间',
                                  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-客户端';

-- ----------------------------
-- Records of gateway_client
-- ----------------------------
BEGIN;
INSERT INTO `gateway_client` (`id`, `systemCode`, `name`, `groupCode`, `ip`, `status`, `createTime`, `updateTime`, `remarks`) VALUES ('72eabbb2bb784ae9a887c0f7fadc0109', 'DS', '数据服务', 'interior_api', '10.*.*.*', '0', '2023-01-15 00:45:53', NULL, '测试');
COMMIT;

-- ----------------------------
-- Table structure for gateway_groovyscript
-- ----------------------------
DROP TABLE IF EXISTS `gateway_groovyscript`;
CREATE TABLE `gateway_groovyscript` (
                                        `id` bigint(8) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
                                        `routeId` varchar(40) DEFAULT NULL COMMENT '网关ID',
                                        `name` varchar(40) DEFAULT NULL COMMENT '脚本名称',
                                        `content` text COMMENT '脚本内容',
                                        `extendInfo` varchar(1000) DEFAULT NULL COMMENT '扩展内容,参数json',
                                        `event` char(8) DEFAULT NULL COMMENT '执行事件',
                                        `orderNum` int(4) DEFAULT NULL COMMENT '顺序',
                                        `status` varchar(2) DEFAULT NULL COMMENT '状态',
                                        `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
                                        `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                                        `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-groovy脚本';

-- ----------------------------
-- Records of gateway_groovyscript
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gateway_loadserver
-- ----------------------------
DROP TABLE IF EXISTS `gateway_loadserver`;
CREATE TABLE `gateway_loadserver` (
                                      `id` bigint(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `routeId` varchar(40) NOT NULL COMMENT '路由ID',
                                      `balancedId` varchar(40) NOT NULL COMMENT '负载ID',
                                      `weight` int(3) NOT NULL COMMENT '权重',
                                      `createTime` datetime NOT NULL COMMENT '创建时间',
                                      `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                      `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='网关-负载已绑定服务';

-- ----------------------------
-- Records of gateway_loadserver
-- ----------------------------
BEGIN;
INSERT INTO `gateway_loadserver` (`id`, `routeId`, `balancedId`, `weight`, `createTime`, `updateTime`, `remarks`) VALUES (6, 'gateway-admin', '7e7c2c9e8b4841a08b1904224ad6d842', 100, '2023-01-15 12:23:46', NULL, NULL);
INSERT INTO `gateway_loadserver` (`id`, `routeId`, `balancedId`, `weight`, `createTime`, `updateTime`, `remarks`) VALUES (8, 'baidu', 'a5da6f66c3b8432f83d959493bb0b792', 100, '2023-02-05 19:28:43', NULL, NULL);
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
-- Table structure for gateway_monitor
-- ----------------------------
DROP TABLE IF EXISTS `gateway_monitor`;
CREATE TABLE `gateway_monitor` (
                                   `id` varchar(40) NOT NULL COMMENT '主键，同routeId',
                                   `status` char(2) DEFAULT NULL COMMENT '0启用，1禁用，2告警',
                                   `emails` varchar(200) DEFAULT NULL COMMENT '通知接收邮箱',
                                   `topic` varchar(200) DEFAULT NULL COMMENT '告警邮件主题',
                                   `recover` char(2) DEFAULT NULL COMMENT '告警重试，0开启，1禁用',
                                   `frequency` char(4) DEFAULT NULL COMMENT '告警频率：30m,1h,5h,12h,24h',
                                   `alarmTime` datetime DEFAULT NULL COMMENT '告警时间',
                                   `sendTime` datetime DEFAULT NULL COMMENT '发送告警时间',
                                   `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-监控';

-- ----------------------------
-- Records of gateway_monitor
-- ----------------------------
BEGIN;
INSERT INTO `gateway_monitor` (`id`, `status`, `emails`, `topic`, `recover`, `frequency`, `alarmTime`, `sendTime`, `updateTime`) VALUES ('auth-center', '0', 'li7hai26@outlook.com', '授权服务异常', '1', '30m', NULL, NULL, '2023-02-05 23:36:15');
COMMIT;

-- ----------------------------
-- Table structure for gateway_regserver
-- ----------------------------
DROP TABLE IF EXISTS `gateway_regserver`;
CREATE TABLE `gateway_regserver` (
                                     `id` bigint(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `clientId` varchar(40) NOT NULL COMMENT '客户端ID',
                                     `routeId` varchar(40) NOT NULL COMMENT '路由ID',
                                     `token` varchar(600) DEFAULT NULL COMMENT 'token加密内容',
                                     `secretKey` varchar(200) DEFAULT NULL COMMENT 'token加密密钥',
                                     `tokenEffectiveTime` datetime DEFAULT NULL COMMENT 'token令牌有效截止时间',
                                     `status` varchar(2) NOT NULL COMMENT '创建时间',
                                     `createTime` datetime NOT NULL COMMENT '更新时间',
                                     `updateTime` datetime DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='网关-服务已注册客户端';

-- ----------------------------
-- Records of gateway_regserver
-- ----------------------------
BEGIN;
INSERT INTO `gateway_regserver` (`id`, `clientId`, `routeId`, `token`, `secretKey`, `tokenEffectiveTime`, `status`, `createTime`, `updateTime`) VALUES (2, '72eabbb2bb784ae9a887c0f7fadc0109', 'baidu', NULL, NULL, NULL, '0', '2023-01-16 01:10:29', NULL);
COMMIT;

-- ----------------------------
-- Table structure for gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route` (
                                 `id` varchar(40) NOT NULL COMMENT '主键',
                                 `systemCode` varchar(40) NOT NULL COMMENT '系统代号',
                                 `name` varchar(40) NOT NULL COMMENT '名称',
                                 `groupCode` varchar(40) NOT NULL COMMENT '分组',
                                 `uri` varchar(200) DEFAULT NULL COMMENT '服务地址',
                                 `path` varchar(100) DEFAULT NULL COMMENT '断言地址',
                                 `method` varchar(6) DEFAULT NULL COMMENT '请求类型',
                                 `host` varchar(100) DEFAULT NULL COMMENT '断言主机',
                                 `remoteAddr` varchar(20) DEFAULT NULL COMMENT '断言远程地址',
                                 `header` varchar(200) DEFAULT NULL COMMENT '断言Headers',
                                 `filterGatewaName` varchar(50) DEFAULT NULL COMMENT '过滤器',
                                 `filterHystrixName` varchar(50) DEFAULT NULL COMMENT '熔断器',
                                 `filterRateLimiterName` varchar(50) DEFAULT NULL COMMENT '限流器',
                                 `filterAuthorizeName` varchar(60) DEFAULT NULL COMMENT '鉴权器',
                                 `fallbackMsg` varchar(200) DEFAULT NULL COMMENT '熔断返回提示',
                                 `fallbackTimeout` bigint(8) DEFAULT NULL COMMENT '熔断超时设置',
                                 `replenishRate` int(6) DEFAULT NULL COMMENT '每秒流量',
                                 `burstCapacity` int(6) DEFAULT NULL COMMENT '令牌总量',
                                 `weight` int(6) DEFAULT NULL COMMENT '权重值',
                                 `status` varchar(2) DEFAULT NULL COMMENT '状态，0启用，1禁用',
                                 `stripPrefix` tinyint(1) DEFAULT NULL COMMENT '断言截取',
                                 `requestParameter` varchar(200) DEFAULT NULL COMMENT '请求参数',
                                 `rewritePath` varchar(200) DEFAULT NULL COMMENT '重写Path路径',
                                 `accessHeader` varchar(200) DEFAULT NULL COMMENT 'header验证',
                                 `accessIp` varchar(200) DEFAULT NULL COMMENT 'ip验证',
                                 `accessParameter` varchar(200) DEFAULT NULL COMMENT '参数验证',
                                 `accessTime` varchar(40) DEFAULT NULL COMMENT '限行时间段验证',
                                 `accessCookie` varchar(200) DEFAULT NULL COMMENT 'cookie键值验证',
                                 `createTime` datetime NOT NULL COMMENT '创建时间',
                                 `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-路由';

-- ----------------------------
-- Records of gateway_route
-- ----------------------------
BEGIN;
INSERT INTO `gateway_route` (`id`, `systemCode`, `name`, `groupCode`, `uri`, `path`, `method`, `host`, `remoteAddr`, `header`, `filterGatewaName`, `filterHystrixName`, `filterRateLimiterName`, `filterAuthorizeName`, `fallbackMsg`, `fallbackTimeout`, `replenishRate`, `burstCapacity`, `weight`, `status`, `stripPrefix`, `requestParameter`, `rewritePath`, `accessHeader`, `accessIp`, `accessParameter`, `accessTime`, `accessCookie`, `createTime`, `updateTime`) VALUES ('auth-center', 'auth-center', '鉴权中心', 'external_api', 'lb://auth-center', '/api/auth-center/**', '', '', '', '', '', NULL, NULL, '', '', 0, 20, 100, NULL, '0', 2, '', '', '', '', '', '', '', '2022-12-30 00:36:09', '2023-02-05 23:36:15');
INSERT INTO `gateway_route` (`id`, `systemCode`, `name`, `groupCode`, `uri`, `path`, `method`, `host`, `remoteAddr`, `header`, `filterGatewaName`, `filterHystrixName`, `filterRateLimiterName`, `filterAuthorizeName`, `fallbackMsg`, `fallbackTimeout`, `replenishRate`, `burstCapacity`, `weight`, `status`, `stripPrefix`, `requestParameter`, `rewritePath`, `accessHeader`, `accessIp`, `accessParameter`, `accessTime`, `accessCookie`, `createTime`, `updateTime`) VALUES ('baidu', 'baidu', '百度-主页', 'external_api', 'https://www.baidu.com', '/route/baidu/**', '', '', '', '', '', NULL, NULL, '', '', 0, 20, 100, NULL, '0', 2, '', '', '', '', '', '', '', '2022-12-30 00:36:09', '2022-12-30 00:36:09');
INSERT INTO `gateway_route` (`id`, `systemCode`, `name`, `groupCode`, `uri`, `path`, `method`, `host`, `remoteAddr`, `header`, `filterGatewaName`, `filterHystrixName`, `filterRateLimiterName`, `filterAuthorizeName`, `fallbackMsg`, `fallbackTimeout`, `replenishRate`, `burstCapacity`, `weight`, `status`, `stripPrefix`, `requestParameter`, `rewritePath`, `accessHeader`, `accessIp`, `accessParameter`, `accessTime`, `accessCookie`, `createTime`, `updateTime`) VALUES ('gateway-admin', 'gateway-admin', '网关管理', 'external_api', 'lb://gateway-admin-center', '/api/gateway-admin/**', '', '', '', '', '', NULL, NULL, '', '', 0, 20, 100, NULL, '0', 2, '', '', '', '', '', '', '', '2022-12-30 00:36:09', '2022-12-30 00:36:09');
INSERT INTO `gateway_route` (`id`, `systemCode`, `name`, `groupCode`, `uri`, `path`, `method`, `host`, `remoteAddr`, `header`, `filterGatewaName`, `filterHystrixName`, `filterRateLimiterName`, `filterAuthorizeName`, `fallbackMsg`, `fallbackTimeout`, `replenishRate`, `burstCapacity`, `weight`, `status`, `stripPrefix`, `requestParameter`, `rewritePath`, `accessHeader`, `accessIp`, `accessParameter`, `accessTime`, `accessCookie`, `createTime`, `updateTime`) VALUES ('system-center', 'system-center', '系统中心', 'external_api', 'lb://system-center', '/api/system-center/**', '', '', '', '', '', NULL, NULL, '', '', 0, 20, 100, NULL, '0', 2, '', '', '', '', '', '', '', '2022-12-30 00:36:09', '2022-12-30 00:36:09');
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
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'monitor-center', 'monitor-center', '[{\"args\": {\"_genkey_0\": \"/monitor-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://monitor-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:14:45', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('2', 'baidu', 'baidu', '[{\"args\": {\"_genkey_0\": \"/baidu/**\"}, \"name\": \"Path\"}]', '[]', 'https://www.baidu.com', 0, '{\"version\": \"1\"}', '2022-06-03 08:39:53', '2022-12-10 22:14:53', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('3', 'auth-center', 'auth-center', '[{\"args\": {\"_genkey_0\": \"/auth-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://auth-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:00', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('4', 'system-center', 'system-center', '[{\"args\": {\"_genkey_0\": \"/system-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://system-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:08', '1', '1', '0', 2);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('5', 'gateway-admin-center', 'gateway-admin-center', '[{\"args\": {\"_genkey_0\": \"/gateway-admin/**\"}, \"name\": \"Path\"}]', '[]', 'lb://gateway-admin-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:15', '1', '1', '0', 3);
INSERT INTO `gateway_route_conf` (`id`, `route_name`, `route_id`, `predicates`, `filters`, `uri`, `sort`, `metadata`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('6', 'message-center', 'message-center', '[{\"args\": {\"_genkey_0\": \"/message-center/**\"}, \"name\": \"Path\"}]', '[]', 'lb://message-center', 0, '{\"version\": \"1\"}', '2021-12-27 02:43:25', '2022-12-10 22:15:23', '1', '1', '0', 2);
COMMIT;

-- ----------------------------
-- Table structure for gateway_secureip
-- ----------------------------
DROP TABLE IF EXISTS `gateway_secureip`;
CREATE TABLE `gateway_secureip` (
                                    `ip` varchar(16) NOT NULL COMMENT 'IP主键',
                                    `status` varchar(2) DEFAULT NULL COMMENT '状态：0正常，1无效',
                                    `createTime` datetime NOT NULL COMMENT '创建时间',
                                    `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                    `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
                                    PRIMARY KEY (`ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网关-IP过滤';

-- ----------------------------
-- Records of gateway_secureip
-- ----------------------------
BEGIN;
INSERT INTO `gateway_secureip` (`ip`, `status`, `createTime`, `updateTime`, `remarks`) VALUES ('10.0.0.0', '0', '2023-01-15 12:48:49', NULL, '');
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
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS `infra_file`;
CREATE TABLE `infra_file` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件编号',
                              `config_id` bigint(20) DEFAULT NULL COMMENT '配置编号',
                              `name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名',
                              `path` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
                              `url` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件 URL',
                              `type` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
                              `size` int(11) NOT NULL COMMENT '文件大小',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                              `creator` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '创建人',
                              `updater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '更新人',
                              `deleted` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                              `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=910 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';

-- ----------------------------
-- Records of infra_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_config`;
CREATE TABLE `infra_file_config` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                     `name` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名',
                                     `storage` tinyint(4) NOT NULL COMMENT '存储器',
                                     `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                                     `master` bit(1) NOT NULL COMMENT '是否为主配置',
                                     `config` varchar(4096) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
                                     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `creator` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '创建人',
                                     `updater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '更新人',
                                     `deleted` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                     `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件配置表';

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
BEGIN;
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES (4, '数据库', 1, '我是数据库', b'0', '{\"@class\":\"cn.cleanarch.dp.common.file.core.client.db.DBFileClientConfig\",\"domain\":\"http://127.0.0.1:48080\"}', '2022-03-15 23:56:24', '2022-03-26 21:39:26', '1', '1', '0', 1);
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES (5, '本地磁盘', 10, '测试下本地存储', b'0', '{\"@class\":\"cn.cleanarch.dp.common.file.core.client.local.LocalFileClientConfig\",\"basePath\":\"/Users/yunai/file_test\",\"domain\":\"http://127.0.0.1:48080\"}', '2022-03-15 23:57:00', '2022-03-26 21:39:26', '1', '1', '0', 1);
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES (11, 'S3 - 七牛云', 20, NULL, b'1', '{\"@class\":\"cn.cleanarch.dp.common.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3-cn-south-1.qiniucs.com\",\"domain\":\"http://test.yudao.iocoder.cn\",\"bucket\":\"ruoyi-vue-pro\",\"accessKey\":\"b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8\",\"accessSecret\":\"kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP\"}', '2022-03-19 18:00:03', '2022-03-26 21:39:26', '1', '1', '0', 1);
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES (15, 'S3 - 七牛云', 20, '', b'0', '{\"@class\":\"cn.cleanarch.dp.common.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3-cn-south-1.qiniucs.com\",\"domain\":\"http://test.yudao.iocoder.cn\",\"bucket\":\"ruoyi-vue-pro\",\"accessKey\":\"b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8\",\"accessSecret\":\"kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP\"}', '2022-06-10 20:50:41', '2022-06-10 20:50:41', '1', '1', '0', 1);
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES (16, 'S3 - 七牛云', 20, '', b'0', '{\"@class\":\"cn.cleanarch.dp.common.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3-cn-south-1.qiniucs.com\",\"domain\":\"http://test.yudao.iocoder.cn\",\"bucket\":\"ruoyi-vue-pro\",\"accessKey\":\"b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8\",\"accessSecret\":\"kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP\"}', '2022-06-11 20:32:08', '2022-06-11 20:32:08', '1', '1', '0', 1);
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES (17, 'S3 - 七牛云', 20, '', b'0', '{\"@class\":\"cn.cleanarch.dp.common.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3-cn-south-1.qiniucs.com\",\"domain\":\"http://test.yudao.iocoder.cn\",\"bucket\":\"ruoyi-vue-pro\",\"accessKey\":\"b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8\",\"accessSecret\":\"kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP\"}', '2022-06-11 20:32:47', '2022-06-21 08:14:54', '1', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_content`;
CREATE TABLE `infra_file_content` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                      `config_id` bigint(20) NOT NULL COMMENT '配置编号',
                                      `path` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
                                      `content` mediumblob NOT NULL COMMENT '文件内容',
                                      `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `creator` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '创建人',
                                      `updater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '更新人',
                                      `deleted` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标记;0:正常,非0已删除',
                                      `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for magic_api_backup
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_backup`;
CREATE TABLE `magic_api_backup` (
                                    `id` varchar(32) NOT NULL COMMENT '原对象ID',
                                    `create_date` bigint(13) NOT NULL COMMENT '备份时间',
                                    `tag` varchar(32) DEFAULT NULL COMMENT '标签',
                                    `type` varchar(32) DEFAULT NULL COMMENT '类型',
                                    `name` varchar(64) DEFAULT NULL COMMENT '原名称',
                                    `content` blob COMMENT '备份内容',
                                    `create_by` varchar(64) DEFAULT NULL COMMENT '操作人',
                                    PRIMARY KEY (`id`,`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='magic-备份';

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
                                  `file_path` varchar(512) NOT NULL,
                                  `file_content` mediumtext,
                                  PRIMARY KEY (`file_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='magic-核心';

-- ----------------------------
-- Records of magic_api_file
-- ----------------------------
BEGIN;
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/api/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/datasource/', 'this is directory');
INSERT INTO `magic_api_file` (`file_path`, `file_content`) VALUES ('/magic-api/function/', 'this is directory');
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
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826113459138561', 1, 'system-center', 1002000000, '登录失败，账号密码不正确', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826113849208833', 1, 'system-center', 1002000001, '登录失败，账号被禁用', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826113924706305', 1, 'system-center', 1002000002, '登录失败', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826113991815170', 1, 'system-center', 1002000003, '验证码不存在', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114046341122', 1, 'system-center', 1002000004, '验证码不正确', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114109255681', 1, 'system-center', 1002000005, '未绑定账号，需要进行绑定', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114201530370', 1, 'system-center', 1002000006, 'Token 已经过期', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114268639233', 1, 'system-center', 1002001000, '已经存在该名字的菜单', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114352525314', 1, 'system-center', 1002001001, '父菜单不存在', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114428022785', 1, 'system-center', 1002001002, '不能设置自己为父菜单', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114516103169', 1, 'system-center', 1002001003, '菜单不存在', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114583212034', 1, 'system-center', 1002001004, '存在子菜单，无法删除', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114662903810', 1, 'system-center', 1002001005, '父菜单的类型必须是目录或者菜单', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114721624065', 1, 'system-center', 1002002000, '角色不存在', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114788732929', 1, 'system-center', 1002002001, '已经存在名为【{}】的角色', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114860036097', 1, 'system-center', 1002002002, '已经存在编码为【{}】的角色', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826114960699393', 1, 'system-center', 1002002003, '不能操作类型为系统内置的角色', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826115027808258', 1, 'system-center', 1002002004, '名字为【{}】的角色已被禁用', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826115220746241', 1, 'system-center', 1002002005, '编码【{}】不能使用', NULL, '2023-01-10 22:58:05', '2023-01-10 22:58:05', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826115761811457', 1, 'system-center', 1002003000, '用户账号已经存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826115858280450', 1, 'system-center', 1002003001, '手机号已经存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826115937972226', 1, 'system-center', 1002003002, '邮箱已经存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116013469698', 1, 'system-center', 1002003003, '用户不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116097355777', 1, 'system-center', 1002003004, '导入用户数据不能为空！', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116164464642', 1, 'system-center', 1002003005, '用户密码校验失败', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116248350722', 1, 'system-center', 1002003006, '名字为【{}】的用户已被禁用', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116323848194', 1, 'system-center', 1002003008, '创建用户失败，原因：超过租户最大租户配额({})！', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116395151361', 1, 'system-center', 1002004000, '已经存在该名字的部门', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116466454530', 1, 'system-center', 1002004001, '父级部门不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116525174786', 1, 'system-center', 1002004002, '当前部门不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116583895042', 1, 'system-center', 1002004003, '存在子部门，无法删除', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116869107714', 1, 'system-center', 1002004004, '不能设置自己为父部门', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826116961382402', 1, 'system-center', 1002004005, '部门中存在员工，无法删除', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117036879873', 1, 'system-center', 1002004006, '部门不处于开启状态，不允许选择', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117116571650', 1, 'system-center', 1002004007, '不能设置自己的子部门为父部门', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117179486209', 1, 'system-center', 1002005000, '当前岗位不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117254983682', 1, 'system-center', 1002005001, '岗位({}) 不处于开启状态，不允许选择', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117322092546', 1, 'system-center', 1002005002, '已经存在该名字的岗位', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117380812801', 1, 'system-center', 1002005003, '已经存在该标识的岗位', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117435338753', 1, 'system-center', 1002006001, '当前字典类型不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117536002049', 1, 'system-center', 1002006002, '字典类型不处于开启状态，不允许选择', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117590528002', 1, 'system-center', 1002006003, '已经存在该名字的字典类型', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117661831169', 1, 'system-center', 1002006004, '已经存在该类型的字典类型', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117712162818', 1, 'system-center', 1002006005, '无法删除，该字典类型还有字典数据', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117775077377', 1, 'system-center', 1002007001, '当前字典数据不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117821214722', 1, 'system-center', 1002007002, '字典数据({})不处于开启状态，不允许选择', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117875740674', 1, 'system-center', 1002007003, '已经存在该值的字典数据', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826117972209666', 1, 'system-center', 1002008001, '当前通知公告不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826118043512833', 1, 'system-center', 1002011000, '短信渠道不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826118152564737', 1, 'system-center', 1002011001, '短信渠道不处于开启状态，不允许选择', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826118387445761', 1, 'system-center', 1002011002, '无法删除，该短信渠道还有短信模板', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826118441971713', 1, 'system-center', 1002012000, '短信模板不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826118521663489', 1, 'system-center', 1002012001, '已经存在编码为【{}】的短信模板', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119226306562', 1, 'system-center', 1002013000, '手机号不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119289221121', 1, 'system-center', 1002013001, '模板参数({})缺失', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119385690113', 1, 'system-center', 1002013002, '短信模板不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119436021761', 1, 'system-center', 1002014000, '验证码不存在', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119511519234', 1, 'system-center', 1002014001, '验证码已过期', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119561850881', 1, 'system-center', 1002014002, '验证码已使用', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119607988225', 1, 'system-center', 1002014003, '验证码不正确', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119683485697', 1, 'system-center', 1002014004, '超过每日短信发送数量', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826119758983169', 1, 'system-center', 1002014005, '短信发送过于频率', NULL, '2023-01-10 22:58:06', '2023-01-10 22:58:06', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120014835713', 1, 'system-center', 1002014006, '手机号已被使用', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120094527490', 1, 'system-center', 1002014007, '验证码未被使用', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120144859137', 1, 'system-center', 1002015000, '租户不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120228745217', 1, 'system-center', 1002015001, '名字为【{}】的租户已被禁用', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120274882562', 1, 'system-center', 1002015002, '名字为【{}】的租户已过期', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120316825602', 1, 'system-center', 1002015003, '系统租户不能进行修改、删除等操作！', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120358768642', 1, 'system-center', 1002016000, '租户套餐不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120404905985', 1, 'system-center', 1002016001, '租户正在使用该套餐，请给租户重新设置套餐后再尝试删除', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120442654722', 1, 'system-center', 1002016002, '名字为【{}】的租户套餐已被禁用', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120522346498', 1, 'system-center', 1002017000, '错误码不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120572678146', 1, 'system-center', 1002017001, '已经存在编码为【{}】的错误码', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120639787009', 1, 'system-center', 1002018000, '社交授权失败，原因是：{}', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120685924354', 1, 'system-center', 1002018001, '社交解绑失败，非当前用户绑定', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120820142081', 1, 'system-center', 1002018002, '社交授权失败，找不到对应的用户', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120887250946', 1, 'system-center', 1003001000, '数据源配置不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826120966942721', 1, 'system-center', 1003001001, '数据源配置不正确，无法进行连接', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121025662978', 1, 'system-center', 1003002000, '表定义已经存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121092771842', 1, 'system-center', 1003002001, '导入的表不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121138909185', 1, 'system-center', 1003002002, '导入的字段不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121189240833', 1, 'system-center', 1003002004, '表定义不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121243766785', 1, 'system-center', 1003002005, '字段义不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121336041473', 1, 'system-center', 1003002006, '同步的字段不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121512202241', 1, 'system-center', 1003002007, '同步失败，不存在改变', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121558339586', 1, 'system-center', 1003002008, '数据库的表注释未填写', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121604476930', 1, 'system-center', 1003002009, '数据库的表字段({})注释未填写', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121663197186', 1, 'system-center', 1004002001, '网关请求来源apiKey不合法', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121709334530', 1, 'system-center', 1004002002, '网关请求来源apiSecret不合法', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121755471874', 1, 'system-center', 1004002003, '网关请求来源system不合法', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121826775041', 1, 'system-center', 1004002004, '网关请求来源apiKey/apiSecret已禁用', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121877106689', 1, 'system-center', 1004002005, '网关请求来源apiKey/apiSecret/system不允许为空', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
INSERT INTO `sys_error_code` (`id`, `type`, `application_name`, `code`, `message`, `memo`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1612826121952604161', 1, 'system-center', 1004002006, '网关请求认证不存在', NULL, '2023-01-10 22:58:07', '2023-01-10 22:58:07', '1', '1', '0', 1);
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
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('1600', '应用管理', NULL, '/system/client', '1000', 'el-icon-help-filled', 6, '0', '0', '2018-01-20 13:17:19', '2021-12-28 20:14:16', 'system/client', NULL, '1', '1', '0', 1);
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
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21100', '网关负载管理', NULL, '/gateway/dashboard/loadBalanced', '2000', 'el-icon-collection-tag', 11, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/LoadBalanced', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21101', '网关负载管理-创建负载均衡', NULL, '/gateway/dashboard/createBalanced', '2000', 'el-icon-collection-tag', 11, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/CreateBalanced', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21200', '网关服务管理', NULL, '/gateway/dashboard/gatewayList', '2000', 'el-icon-collection-tag', 12, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/GatewayList', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21201', '网关服务管理-创建网关服务', NULL, '/gateway/dashboard/createGateway', '2000', 'el-icon-collection-tag', 12, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/CreateGateway', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21202', '网关服务管理-网关路由拓扑结构', NULL, '/gateway/dashboard/gatewayTopology', '2000', 'el-icon-collection-tag', 12, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/GatewayTopology', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21203', '网关服务管理-添加网关客户端', NULL, '/gateway/dashboard/addGatewayClient', '2000', 'el-icon-collection-tag', 12, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/AddGatewayClient', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21204', '网关服务管理-添加规则组件', NULL, '/gateway/dashboard/addGroovyScript', '2000', 'el-icon-collection-tag', 12, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/AddGroovyScript', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21300', '网关客户端管理', NULL, '/gateway/dashboard/clientList', '2000', 'el-icon-collection-tag', 13, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/ClientList', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21301', '网关客户端管理-创建网关客户端', NULL, '/gateway/dashboard/createClient', '2000', 'el-icon-collection-tag', 13, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/CreateClient', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21302', '网关客户端管理-添加客户端网关', NULL, '/gateway/dashboard/addClientGateway', '2000', 'el-icon-collection-tag', 12, '0', '4', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/AddClientGateway', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21400', '网关IP名单管理', NULL, '/gateway/dashboard/ipList', '2000', 'el-icon-collection-tag', 14, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/IpList', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21500', '网关接口统计', NULL, '/gateway/dashboard/apiCount', '2000', 'el-icon-collection-tag', 15, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/ApiCount', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21600', '网关接口监控', NULL, '/gateway/dashboard/apiMonitor', '2000', 'el-icon-collection-tag', 16, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/ApiMonitor', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('21700', '网关接口文档', NULL, '/gateway/dashboard/apiDoc', '2000', 'el-icon-collection-tag', 17, '0', '0', '2022-09-19 17:01:58', '2022-09-23 00:21:03', 'gateway/dashboard/ApiDoc', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2200', '网关路由', NULL, '/gateway/route', '2000', 'el-icon-platform', 2, '0', '0', '2018-09-04 05:58:41', '2022-09-23 00:20:39', 'gateway/route', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2201', '网关路由新增', 'gateway:gateway-route:create', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2202', '网关路由修改', 'gateway:gateway-route:update', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2203', '网关路由删除', 'gateway:gateway-route:delete', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2204', '网关路由查询', 'gateway:gateway-route:query', NULL, '2200', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2300', '网关访问', NULL, '/gateway/access', '2000', 'el-icon-avatar', 3, '0', '0', '2018-09-04 05:58:41', '2022-09-23 00:20:50', 'gateway/access', NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2301', '网关访问新增', 'gateway:gateway-access:create', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:56:16', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2302', '网关访问修改', 'gateway:gateway-access:update', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:56:59', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2303', '网关访问删除', 'gateway:gateway-access:delete', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2304', '网关访问查询', 'gateway:gateway-access:query', NULL, '2300', NULL, 1, '0', '1', '2018-01-20 14:57:28', '2021-05-25 03:12:55', NULL, NULL, '1', '1', '0', 1);
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `component`, `tag`, `creator`, `updater`, `deleted`, `version`) VALUES ('2400', '网关元数据', NULL, '/gateway/metadata', '2000', 'el-icon-collection-tag', 5, '0', '0', '2022-09-19 17:01:58', '2023-01-10 22:39:02', 'gateway/metadata', NULL, '1', '1', '0', 1);
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
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121100', 1, 21100, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121101', 1, 21101, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121200', 1, 21200, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121201', 1, 21201, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121202', 1, 21202, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121203', 1, 21203, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121204', 1, 21204, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121300', 1, 21300, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121301', 1, 21301, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121302', 1, 21302, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121400', 1, 21400, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121500', 1, 21500, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121600', 1, 21600, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('121700', 1, 21700, '2022-09-19 17:01:07', '2022-09-19 17:01:07', '1', '1', '0', 1);
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
INSERT INTO `sys_user` (`id`, `user_name`, `nick_name`, `password`, `salt`, `gender`, `email`, `mobile`, `avatar`, `last_login_time`, `last_login_ip`, `dept_id`, `lock_flag`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `version`) VALUES ('1', 'admin', 'admin-nickname', '$2a$10$IVzj1Wd.ZQdOIWdb1htQjexU94uoNeuk1crlQ9ExVupPi0Iy1uv.C', '', 1, 'li7hai26@gmail.com', '17034642888', '', '2022-01-06 07:47:04', '127.0.0.1', '1', 0, '2023-01-10 14:39:24', '2023-01-10 22:39:25', '1', '1', '0', 1);
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