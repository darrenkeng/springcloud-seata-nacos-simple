## 版本说明 

### 使用官方最新建议版本（2021-03） 

[SpringCloudAlibaba 版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E#%E7%BB%84%E4%BB%B6%E7%89%88%E6%9C%AC%E5%85%B3%E7%B3%BB) 

![b0b1682b14b0a7dc7b7f54a1fb1ce302.png](https://github.com/darrenkeng/springcloud-seata-nacos-simple/blob/master/img/b0b1682b14b0a7dc7b7f54a1fb1ce302.png) 

## 架构说明 

参考 seata 官网：[http://seata.io/zh-cn/docs/user/quickstart.html](http://seata.io/zh-cn/docs/user/quickstart.html) 

![e3a080d9b07d0a481e24daa1b458a990.png](https://github.com/darrenkeng/springcloud-seata-nacos-simple/blob/master/img/e3a080d9b07d0a481e24daa1b458a990.png) 

## 1、Nacos Server 

使用Nacos做为注册中心和配置中心，将seata-server注册到nacos，seata配置从nacos上取。 

下载地址：[https://github.com/alibaba/nacos/releases/download/1.4.1/nacos-server-1.4.1.zip](https://github.com/alibaba/nacos/releases/download/1.4.1/nacos-server-1.4.1.zip) 

快速开始：[https://nacos.io/zh-cn/docs/quick-start.html](https://nacos.io/zh-cn/docs/quick-start.html) 

## 2、Seata Server 

下载地址：[https://github.com/seata/seata/releases/download/v1.3.0/seata-server-1.3.0.zip](https://github.com/seata/seata/releases/download/v1.3.0/seata-server-1.3.0.zip) 

快速开始：[http://seata.io/zh-cn/docs/overview/what-is-seata.html](http://seata.io/zh-cn/docs/overview/what-is-seata.html) 

### 2.1、创建seata-server库和表 

下载的 seata-server.zip 中不包含 sql脚本，需要在github上单独下载后执行。 

地址：[https://github.com/seata/seata/blob/develop/script/server/db/mysql.sql](https://github.com/seata/seata/blob/develop/script/server/db/mysql.sql) 

![9efa9325af152d2c01c824b73faeb8a4.png](https://github.com/darrenkeng/springcloud-seata-nacos-simple/blob/master/img/9efa9325af152d2c01c824b73faeb8a4.png) 

### 2.2、修改 conf 目录下配置 

#### 2.2.1、删除 file.conf 

此次使用nacos做为配置中心，seata-server的配置直接从nacos上获取，不需要 file.conf。 

#### 2.2.2、修改 registry.conf 

将类型改为nacos 

``` properties
# seata-server 的注册中心类型及信息 
registry { 
    # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa 
    type = "nacos" 
    nacos { 
        # 重要！！！ 需要与 seata client 中 seata.nacos.application 保持一致 
        application = "seata-server" 
        serverAddr = "localhost:8848" 
        # 自定义 
        group = "SEATA_GROUP" 
        # 自定义 
        namespace = "seata" 
        # 重要！！！ seata-server 集群名，需要与 seata client 中 service.vgroupMapping.${seata.tx-service-group}=default 保持一致 
        cluster = "default" 
        username = "nacos" 
        password = "nacos" 
    } 
} 

# seata-server 的配置中心类型及信息 
config { 
    # file、nacos 、apollo、zk、consul、etcd3 
    type = "nacos" 
    nacos { 
        serverAddr = "localhost:8848" 
        # 自定义 
        namespace = "seata" 
        # 自定义 
        group = "SEATA_GROUP" 
        username = "nacos" 
        password = "nacos" 
    } 
} 
```

>==注意==： 
>
>重要！！！ 需要与 seata client 中 seata.nacos.application 保持一致 
>
>application = "seata-server" 
>
>重要！！！ seata-server 集群名，需要与 seata client 中 service.vgroupMapping.${seata.tx-service-group}=default 保持一致 
>
>cluster = "default" 

### 2.3、nacos-config 

下载的 seata-server.zip 中不包含 nacos-config.sh 及 config.txt ，需要在github上单独下载后使用。 

#### 2.3.1、下载 

地址： 

[https://github.com/seata/seata/blob/develop/script/config-center/config.txt](https://github.com/seata/seata/blob/develop/script/config-center/config.txt) 

放 nacos 目录下 

[https://github.com/seata/seata/blob/develop/script/config-center/nacos/nacos-config.sh](https://github.com/seata/seata/blob/develop/script/config-center/nacos/nacos-config.sh) 

放 nacos/bin 目录下 

>注意：nacos-config.sh 查找上级目录的 config.txt 

#### 2.3.2、修改 config.txt 

``` properties
service.vgroupMapping.my_test_tx_group=default 
# 可配置多个，每个应用配置一个，也可多个应用共用一个。account-tx-group 为 seata-client 端 seata.tx-service-group 名。 default 为seata-server 集群名 
service.vgroupMapping.account-tx-group=default 
service.vgroupMapping.order-tx-group=default 
service.vgroupMapping.storage-tx-group=default 
service.vgroupMapping.business-tx-group=default 
service.default.grouplist=127.0.0.1:8091 

# seata-server 的db配置 
store.mode=db 
store.db.datasource=druid 
store.db.dbType=mysql 
store.db.driverClassName=com.mysql.jdbc.Driver 
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true 
store.db.user=root 
store.db.password=123456 
```

> \#可配置多个，每个应用配置一个，也可多个应用共用一个。account-tx-group 为 seata-client 端 seata.tx-service-group 名。 default 为seata-server 集群名 
>service.vgroupMapping.account-tx-group=default 

#### 2.3.3、执行 nacos-config.sh 

``` shell

# -t seata 为 namespace，详细参数查看shell 脚本 

sh nacos-config.sh -h localhost -u nacos -w nacos -t seata 

```

将 seata 配置推送到 nacos，在nacos上可以看到配置信息。 

![4f039ef88cdabaa5679ee71bc1d67740.png](https://github.com/darrenkeng/springcloud-seata-nacos-simple/blob/master/img/4f039ef88cdabaa5679ee71bc1d67740.png) 

#### 2.3.4、启动 seata-server 

``` shell

./seata-server.sh 

```

启动日志： 

``` 

2021-03-05 15:05:00.073 INFO --- [ main] io.seata.config.FileConfiguration : The configuration file used is registry.conf 

2021-03-05 15:05:00.934 INFO --- [ main] com.alibaba.druid.pool.DruidDataSource : {dataSource-1} inited 

2021-03-05 15:05:01.111 INFO --- [ main] i.s.core.rpc.netty.NettyServerBootstrap : Server started, listen port: 8091 

```

nacos 服务列表： 

![3ee032d34249116aaf3b61b0f4ae9d41.png](https://github.com/darrenkeng/springcloud-seata-nacos-simple/blob/master/img/3ee032d34249116aaf3b61b0f4ae9d41.png) 

## 3、seata client 

### 3.1、创建数据库及表 

``` mysql
-- 创建数据库 seata_sample_storage 
DROP DATABASE IF EXISTS `seata_sample_storage`;
CREATE DATABASE `seata_sample_storage`;
USE `seata_sample_storage`;

-- 创建数据表 storage_tbl 
DROP TABLE IF EXISTS `storage_tbl`;
CREATE TABLE `storage_tbl` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`commodity_code` varchar(255) DEFAULT NULL,
	`count` int(11) DEFAULT 0,
	PRIMARY KEY (`id`),
	UNIQUE KEY (`commodity_code`)
) ENGINE = InnoDB CHARSET = utf8;
-- seata server 使用 
CREATE TABLE `undo_log` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`branch_id` bigint(20) NOT NULL,
	`xid` varchar(100) NOT NULL,
	`context` varchar(128) NOT NULL,
	`rollback_info` longblob NOT NULL,
	`log_status` int(11) NOT NULL,
	`log_created` datetime NOT NULL,
	`log_modified` datetime NOT NULL,
	`ext` varchar(100) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARSET = utf8;

INSERT INTO `storage_tbl` (`count`, `commodity_code`)
VALUES ('100', 'v1');
INSERT INTO `storage_tbl` (`count`, `commodity_code`)
VALUES ('100', 'v2');


-- 创建数据库 seata_sample_order 
DROP DATABASE IF EXISTS `seata_sample_order`;
CREATE DATABASE `seata_sample_order`;
USE `seata_sample_order`;

-- 创建数据表 order_tbl 
DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` varchar(255) DEFAULT NULL,
	`commodity_code` varchar(255) DEFAULT NULL,
	`count` int(11) DEFAULT 0,
	`money` int(11) DEFAULT 0,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;
-- seata server 使用 
CREATE TABLE `undo_log` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`branch_id` bigint(20) NOT NULL,
	`xid` varchar(100) NOT NULL,
	`context` varchar(128) NOT NULL,
	`rollback_info` longblob NOT NULL,
	`log_status` int(11) NOT NULL,
	`log_created` datetime NOT NULL,
	`log_modified` datetime NOT NULL,
	`ext` varchar(100) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARSET = utf8;


-- 创建数据库 seata_sample_account 
DROP DATABASE IF EXISTS `seata_sample_account`;
CREATE DATABASE `seata_sample_account`;
USE `seata_sample_account`;

-- 创建数据表 account_tbl 
DROP TABLE IF EXISTS `account_tbl`;
CREATE TABLE `account_tbl` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` varchar(255) DEFAULT NULL,
	`money` int(11) DEFAULT 0,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

-- seata server 使用 
CREATE TABLE `undo_log` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`branch_id` bigint(20) NOT NULL,
	`xid` varchar(100) NOT NULL,
	`context` varchar(128) NOT NULL,
	`rollback_info` longblob NOT NULL,
	`log_status` int(11) NOT NULL,
	`log_created` datetime NOT NULL,
	`log_modified` datetime NOT NULL,
	`ext` varchar(100) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARSET = utf8;

INSERT INTO `account_tbl` (`user_id`, `money`)
VALUES ('zhangsan', '80');

```

### 3.2、创建client应用 

这个要写就长了，具体看代码吧： 

[https://github.com/darrenkeng/springcloud-seata-nacos-simple](https://github.com/darrenkeng/springcloud-seata-nacos-simple) 

**核心注意点**如下： 

#### 3.2.1、父工程版本 

``` xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.darrenkeng</groupId>
    <artifactId>springcloud-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <modules>
        <module>api-commons</module>
        <module>seata-account-provider</module>
        <module>seata-storage-provider</module>
        <module>seata-order-provider</module>
        <module>seata-business-consumer</module>
    </modules>

    <!-- 统一版本管理 -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <log4j.version>1.2.17</log4j.version>
        <lombok.version>1.16.18</lombok.version>
        <mybatis.version>1.3.0</mybatis.version>
        <mysql.version>8.0.18</mysql.version>
        <druid.version>1.1.16</druid.version>
        <mybatis-plus.version>3.3.1</mybatis-plus.version>
        <mybatis.spring.boot.version>1.3.0</mybatis.spring.boot.version>
        <commons-lang3.version>3.4</commons-lang3.version>
        <hutool.version>5.2.3</hutool.version>
        <swagger2.version>2.9.2</swagger2.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!--spring boot 2.3.2-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.3.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR8-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR8</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud alibaba 2.2.5.RELEASE-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.5.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- MySql -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <!-- Druid -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!-- mybatis-plus-springboot整合 -->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>
            <!--lombok-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
            <!--junit-->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!-- log4j -->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
            <!-- commons-lang3 -->
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-lang3.version}</version>
            </dependency>
            <!-- hutools -->
            <dependency>
                <groupId>cn.hutool</groupId>
                <artifactId>hutool-all</artifactId>
                <version>${hutool.version}</version>
            </dependency>

            <!--swagger2 start-->
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger2</artifactId>
                <version>${swagger2.version}</version>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger-ui</artifactId>
                <version>${swagger2.version}</version>
            </dependency>
            <!--swagger2 end-->
        </dependencies>
    </dependencyManagement>

    <build>
        <!--<finalName>springcloud</finalName>-->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <addResources>true</addResources>
                </configuration>
            </plugin>
        </plugins>
    </build>


</project>
```

#### 3.2.2、seata-1.3版本 seata-client应用已**不需要**file.conf 及 registry.conf 

#### 3.2.3、将seata-client的application.yml 中配置放入nacos，除了spring.profiles.active 

``` yml
spring:
  profiles:
    active: dev
#  datasource:
#    type: com.alibaba.druid.pool.DruidDataSource
#    driver-class-name: com.mysql.cj.jdbc.Driver
#    url: jdbc:mysql://localhost:3306/seata_sample_account?useUnicode=true&characterEncoding=utf-8&useSSL=true
#    username: root
#    password: 123456
#    druid:
#      max-active: 100
#
#mybatis-plus:
#  mapper-locations: classpath:/mapper/*Mapper.xml
#
#
## 此处为自定义，配置中心和注册中心共同属性，下文引入即可
#nacos:
#  group: SEATA_GROUP
#  # 配置中心地址
#  server-addr: 127.0.0.1:8848
#  # 命名空间
#  namespace: seata
#  seata:
#    server-addr: 127.0.0.1:8848
#    application: seata-server
#    tx-service-group: account-tx-group
#
## 以下配置 配置在nacos服务端
#seata:
#  enabled: true
#  application-id: ${spring.application.name}
#  tx-service-group: ${nacos.seata.tx-service-group}
#  enable-auto-data-source-proxy: true
#  use-jdk-proxy: true
#  config:
#    # 指明类型
#    type: nacos
#    nacos:
#      server-addr: ${nacos.seata.server-addr}
#      namespace: ${nacos.namespace}
#      group: ${nacos.group}
#      username: ""
#      password: ""
#  registry:
#    type: nacos
#    nacos:
#      application: ${nacos.seata.application}
#      server-addr: ${nacos.seata.server-addr}
#      namespace: ${nacos.namespace}
#      group: ${nacos.group}
#      username: ""
#      password: ""


```

#### 3.2.4、最最重要的seata.registry.nacos.application:seata-server是seata-server注册进nacos的应用名，而不是client的应用名 

#### 3.2.5、做了如上那么多，就为了使用 @GlobalTransactional 

``` java
    /**
     * 采购 - 分布式事务
     */
    @Override
    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }

    /**
     * 采购 - 无事务
     */
    @Override
    public void purchaseWithoutTransactional(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }

```





## 能成功使用 @GlobalTransactional ==并不是结束，而是刚刚开始。== 

### Seata 是什么? 

Seata 是一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。Seata 将为用户提供了 ==AT、TCC、SAGA 和 XA 事务模式==，为用户打造一站式的分布式解决方案。 

### 我们使用哪种事务模式？如何使用其它几种事务模式？ 
