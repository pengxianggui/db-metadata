# MD

## 重构需要注意的地方

1. 任何情况下保证能够编译通过
2. 函数,小功能重构必须通过单元测试来验证
3. 为保证第一点,必要时可以使用过度类和空方法

  ```java
    package com.jfinal.core;

import com.github.md.analysis.kit.Kv;

/**
 * 整理JFinal Controller中用到的方法,剥离
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Controller {

    default Kv getKv() {
        throw new RuntimeException("该接口用来保证迁移时编译通过,未实现");
    }
}
  ```

  ```java
    /**
     * TODO 编译用
     */
    class Plugins {
    
      List<IPlugin> getPluginList() {
        throw new RuntimeException("该接口用来保证迁移时编译通过,未实现");
      }
    
      Plugins add(IPlugin plugin) {
        throw new RuntimeException("该接口用来保证迁移时编译通过,未实现");
      }
    }
  ```

## 要求

- 一些项目的DataSource的获取方式不同,需要弹性设计
    - 直接通过spring.datasource声明的
    - 第三方插件定义的 Mybatis-plus等
- 使用JdbcTemplate,而不是JFinal的Activerecord的原因
    - 考虑到MD是可能被集成到各种项目中,希望依赖尽可能一栈式
    - 多数据源的支持(JdbcTemplate与sql是强耦合),如果要避免跨数据库方言带来的问题,sql尽可能使用SQL ansi99 标准,少使用一些内置函数

## TODO

### 高优先级

- [x] 完成DataSource初始化和业务中频繁调用的方法
    - [x] 支持动态切换数据源
- [ ] 使用JdbcTemplate 重写DbMetaService,BusinessService

### 中优先级

- [ ] JFinal包中的依赖逐步去除,部分工具类通过Copy Class方式移植到项目中
- [ ] 一些bean的载入顺序问题
  - @ConditionalOnBean(JFinalActiveRecordPluginManager.class)
### 低优先级

- [ ] Druid中ast部分 考虑用JSqlParse替换
- [ ] hthjsj包名待server重构完毕后统一替换;
-

##### 参考

- [Spring Boot DataSource Configuration](https://howtodoinjava.com/spring-boot2/datasource-configuration/)
- [jfinal-orm-spring-boot-starter](https://gitee.com/blingking/jfinal-orm-spring-boot-starter)