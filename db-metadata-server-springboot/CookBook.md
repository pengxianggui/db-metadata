# Cook Book

## 如何快速集成SpringBoot 版本的dbmeta
### 数据库初始化
初始化脚本: [初始化脚本](../db/script/db-metadata-2.0.sql)
> ps: 库名不限。dbmeta支持多库连接，可以将dbmeta相关内置表和业务表同库，也可以分开。但是dbmeta内置相关表所在库必须为
> 默认主库。即，springboot数据库连接配置的，就是默认主库。

### maven pom依赖
```xml
<dependency>
    <groupId>com.github.md</groupId>
    <artifactId>db-metadata-server-springboot</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>
```

### 配置文件
```yaml
md:
  dev-mode: true # 开发模式
  
  server:
    login:
      enable: false # 开启登录控制
      includes: # 配置影响的path
      excludes: # 配置排除的path
    auth:
      enable: false # 开启权限控制，以下同理
      includes:
      excludes:
    meta-object:
      replace-from-json-file: true
    component:
      replace-from-json-file: true
    upload: # 上传配置
      mode: local # 默认上传服务器本地。当为其他值时需要自行扩展
      base-upload-path: /opt/www/db-meta-serve # 当mode为本地时，配置的本地上传根路径

  analysis:
    # 多数据源配置，当只有一个主数据源时，下面可以不要
    source-config:
      biz_db1:
        url: 
        username:
        password:
        driver-class-name:
      biz_db2:
        url:
        username:
        password:
        driver-class-name:

```

### SpringBoot启动类添加注解
```java
@SpringBootApplication(scanBasePackages = {"your self package path", "com.github.md.*"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(AlarmPlatformApplication.class, args);
    }
}
```
> 目前springboot版本的dbmeta还只是将代码从jfinal迁移为springboot，并不是一个starter，因此需要配置扫描路径: `com.github.md.*`
