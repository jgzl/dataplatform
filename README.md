# 数据中台
## 项目结构
admin-ui为前端,参考前端项目 [vue-admin-work-x](https://gitee.com/qingqingxuan/vue-admin-work-x)

## 平台简介
数据中台-拥有数据治理,数据质量,数据集市功能
数据中台-网关基于Spring Cloud Gateway开发的网关管理平台，毫无保留给个人及企业免费使用。

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码(待开发)。
* 提供了技术栈（[Vue3](https://v3.cn.vuejs.org) [Element Plus](https://element-plus.org/zh-CN) [Vite](https://cn.vitejs.dev)）
* 特别鸣谢：[vue-admin-work-x](https://gitee.com/qingqingxuan/vue-admin-work-x)。

## docker-compose方式快速打包
export IMAGE_REPO_URL=registry.cn-shanghai.aliyuncs.com/gatewayx && export IMAGE_VERSION=dev && sh build-docker.sh prod
## docker-compose方式快速启动
export IMAGE_REPO_URL=registry.cn-shanghai.aliyuncs.com/gatewayx && export IMAGE_VERSION=dev && sh run-docker.sh

## 项目启动
* 1.配置nacos信息，分为注册中心和配置中心
* 2.配置redis信息
* 3.配置mysql信息
* 4.配置sentinel-dashboard信息

## 访问信息
>账号 admin 
> 
>密码 123456

## 部署步骤
1.安装mysql,nacos,redis,elasticsearch步骤如下所示

[安装mysql](https://www.yuque.com/yuque_lihaifeng/cloudnative/docker-mysql)

[安装nacos](https://www.yuque.com/yuque_lihaifeng/cloudnative/docker-nacos)

[安装redis](https://www.yuque.com/yuque_lihaifeng/cloudnative/docker-redis)

[安装elasticsearch](https://www.yuque.com/yuque_lihaifeng/cloudnative/docker-elasticsearch7)

2.build.sh 打包脚本(用法如下)
```shell
# sh build-docker.sh 需要打包的profile
sh build-docker.sh prod
```
3.run.sh 运行脚本(用法如下)
```shell
sh run-docker.sh
```

## 计划完成功能
- [ ] 需要给接入方提供一个apiKey和apiSecret,管控非微服务接口及微服务层级接口
- [ ] 限流配置(漏桶,令牌桶,固定时间窗口,滑动时间窗口)
- [ ] 权限认证RBAC
- [ ] token(jwt,oauth2)
- [ ] 非微服接口文档生成
- [x] 网关管理平台(web)-日志记录查看,路由配置,用户管理,菜单管理,部门管理

## 生产发布(内网)
内网地址 

http://192.168.192.151

http://192.168.192.152

外网地址

http://150.230.202.112

http://150.230.219.246

使用**Zerotier**进行内网发布,公网使用Oracle Cloud LB进行负载均衡发布

使用如下方式加入内网（linux）
```shell
curl -s https://install.zerotier.com | sudo bash
zerotier-cli join 83048a0632ecb930
```

## 接口文档(内网)
使用smart-doc + torna来管理接口文档

~~torna地址 http://192.168.192.3:7700,账号密码自行注册~~

## APM(Skywalking)
JVM参数中需要加入如下
```
-javaagent:D:\\soft\\skywalking-agent\\skywalking-agent.jar
-Dskywalking.agent.service_name=gateway-admin-server
-Dskywalking.collector.backend_service=192.168.192.2:11800
-Dplugin.toolkit.log.grpc.reporter.server_host=192.168.192.2
-Dplugin.toolkit.log.grpc.reporter.server_port=11800
-Dskywalking.trace.ignore_path=HikariCP/**,Kafka/**,Redisson/**,Lettuce/**,/actuator/**
```
1. javaagent以实际目录为准
2. service_name以应用名为准
3. backend_service以实际skywalking-oap-serive地址为准
4. log收集以实际skywalking-oap-serive地址为准