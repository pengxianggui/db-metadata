# 简介

见名知意，快速将db-metadata集成到SpringBoot工程中

## 声明

借助jfinal-spring-boot-starter实现。

## 使用说明

- 如果需要替换db-meta用户、鉴权等相关配置。请覆盖UserConfig
- 如果需要替换db-meta事件相关配置。请覆盖EventConfig
- 如果需要替换db-meta字典配置。请覆盖DictConfig
- 如果需要替换db-meta组件注册配置。请覆盖ComponentConfig
- 如果需要扩展db-meta的配置。请覆盖ExtConfig

## 关于配置

支持Spring原生配置文件。使用方法如下:

```yaml

jfinal:
  dialect: com.jfinal.plugin.activerecord.dialect.MysqlDialect # 数据库方言
  #  kit-classes:
  #    - model._MappingKit
  #  sql-templates:
  #    - classpath:template/*.sql
  show-sql: true
  #  transaction-level: 4 # 数据库事务级别: 可选值详见java.sql.Connection类
  dev-mode: true
  #  error-view-mapping:
  #    404: '404' # 错误的视图页面
  inject-dependency: false # 设置对 Controller、Interceptor、Validator 进行依赖注入， 默认为false
  date-pattern: 'yyyy-MM-dd HH:mm:ss' # 设置json转换日期格式
  max-post-size: 10485760 # 设置http post最大值, 默认为10M, 文件上传也收此配置限制
#  filter-prefix: /jf/* # 由JFinal接管的Servlet API通配符。必须是一个匹配路径
  #  task-info-map: # 配置定时任务，依赖cron4j
  #    task1:
  #      enable:
  #      cron:
  #      task: it.sauronsoftware.cron4j或Runnable的继承类全路径
  #      daemon:


  db-meta: # db-metadata相关配置
    config-allow-replace: true # 是否允许从defaultObject.json文件中读取数据并覆盖数据库实例配置。db-meta默认的defaultObject.json中都是关于dbmeta配置表自身的元数据。所以这项配不配关系不大。至于业务系统如果需要覆盖此文件，后面还需要单独对配置格式进行介绍。
    upload-dir: /opt/www/db-meta-serve # 上传接口的文件保存路径。
    user-login: false # 是否开启登录认证。默认的登录采用的json文件中的静态用户数据
    component-allow-replace: false # dbmeta类路径中有一个jsonTemplate.json文件，记录了各个前端组件的默认配置，开启此项，会将这个配置自动初始化到库中(如果库中没有这些数据的话)，如果同时还处于开发模式则每次启动都会从jsonTemplate.json同步到库中，并且增加版本号。
    user-auth: false # 是否开启用户鉴权。默认的鉴权采用的json文件中的静态数据。实际应用时，请覆盖userConfig类配置自定义的鉴权逻辑
#    api-prefix: /jf # 指定db-meta内置接口前缀。必须是jfinal.filter-prefix子集。必须是一个具体的路径
    # 主数据库配置信息。为db-meta配置库
    main-jdbc-url: jdbc:mysql://localhost:3306/comprehensive_evaluation_dbmeta?useSSL=false&characterEncoding=utf-8
    main-jdbc-username: root
    main-jdbc-password: 123456
    # 业务数据库配置信息。支持多个业务数据库。规则如下。业务数据库也可以和主数据库是同一个库。那么下面配置可以省略，但是还是建议拆分。
    biz-db: comprehensive_evaluation,db-metadata-business-demo
    biz-db-config:
      comprehensive_evaluation:
        jdbc-url: jdbc:mysql://localhost:3306/comprehensive_evaluation?useSSL=false&characterEncoding=utf-8
        jdbc-username: root
        jdbc-password: 123456
      db-metadata-business-demo:
        jdbc-url: jdbc:mysql://localhost:3306/db-metadata-business-demo?useSSL=false&characterEncoding=utf-8
        jdbc-username: root
        jdbc-password: 123456
```

注意:

1. 更多配置参见JFinalProperties类。如需更多JFinal配置需要集成到Spring配置文件中，请联系开发人员支持。
2. 若Spring配置文件中无jfinal.db-meta配置项，则会采用db-meta默认的配置文件加载方式。否则会采用Spring配置文件中的。所以一旦配置了`jfinal.db-meta`，就务必配置完整。
3. 既然用了db-metadata-spring-boot-starter，就推荐只用spring配置文件。dbmeta的config.properties就别用了。
