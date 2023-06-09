# 改造思路

顺序:
以Controller为中心,逐步迁移到SpringBoot中

## TODO

- [ ] Controller API 显式转换 -> SpringBoot
  - [x] 编程式注册url (初步做到集中对某一类Controller加前缀)
    - https://my.oschina.net/u/3101282/blog/3022154
  - [x] QueryHelper 优雅的替代方案
  - [x] getPara系列方法的迁移
- [ ] JFinal interceptor 拦截器 -> Spring Interceptor
  - [ ] 全局异常的改写
  - [ ] ExceptionIntercept
    - 非Controller中的异常捕获,如(HandlerExceptionResolver,BasicErrorController)
  - [x] JsonParamIntercept
- [ ] Upload逻辑改造;
- [ ] @Service 临时使用,后期核心Service需要通过显式注册到容器中,方便外部控制
- [ ] 事务的兼容
  - @Before(Tx.class) 替代方案
  - @Before(HttpRequestHolder.class)
- [ ] sql动态编译的改造 JFinal.Enjoy -> Spring.El
- [ ] PointCut中的queryHelper替换
- [ ] JsonParameterToMapHandler.preHandle
  - TODO 由于ControllerAdapter中使用RequestContextHolder 来获取的Request,利用此处的时机改写request;
- [ ] Mysql 8.0 的支持
  - [ ] autoreimport 时，默认路由的添加
    ```
    INSERT INTO `meta_router` VALUES ('0', '', '默认根路由', 'Default', '/default', '', 'AdminLayout', '{}', '{}', '2021-02-02 01:40:45',
    'db-meta-web-devUser', NULL, NULL, '默认根路由, 指向db-meta默认布局组件AdminLayout');
      ```
- 用户模块
- 权限模块
- [ ] [Bug] 下拉框显示（）
## 构想

- 每一次Controller的链路跟踪 都能跟踪到的功能
- 

## Tips

### 兼容

- JFinal中getPara() 默认取 `http://xxx/avb/b/b/c` 出的是`c`,为了兼容Springboot,每个Mapping后都加了`/{object_code}`
  - [ ] 这种方式不够优雅,后期考虑通过`RequestMappingHandlerMapping`,`RequestMappingInfoHandlerMapping`集中改写看看

##### 参考

- https://gitee.com/blingking/jfinal-orm-spring-boot-starter
- https://stackoverflow.com/questions/27405713/running-code-after-spring-boot-starts
- https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-Configuration-Binding
- 
About advice
- https://stackoverflow.com/questions/49072611/spring-register-advice-programmatically-at-runtime
- http://www.java2s.com/Code/Java/Spring/DynamicPointcutExample.htm

RequestMappingHandlerMapping
- https://blog.csdn.net/philip502/article/details/98502246
- https://www.jianshu.com/p/a501bea7535f
- https://blog.csdn.net/wangwei19871103/article/details/105527962/

Dynamically registering controller
- https://stackoverflow.com/questions/33603950/registering-spring-mvc-controllers-dynamically
- https://my.oschina.net/u/3101282/blog/3022154