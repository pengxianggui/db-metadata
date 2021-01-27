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
  #  task-info-map: # 配置定时任务，依赖cron4j
  #    task1:
  #      enable:
  #      cron:
  #      task: it.sauronsoftware.cron4j或Runnable的继承类全路径
  #      daemon:

  db-meta: # db-metadata相关配置
    config-allow-replace: true
    upload-dir: /opt/www/db-meta-serve
    user-login: false
    component-allow-replace: false
    user-auth: false
    main-jdbc-url: jdbc:mysql://localhost:3306/comprehensive_evaluation_dbmeta?useSSL=false&characterEncoding=utf-8
    main-jdbc-username: root
    main-jdbc-password: 123456
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
