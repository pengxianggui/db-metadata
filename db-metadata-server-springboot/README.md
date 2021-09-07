# 改造思路

顺序:
以Controller为中心,逐步迁移到SpringBoot中

## TODO

- [ ] Controller API 显式转换 -> SpringBoot
    - [ ] 编程式注册url
        - https://my.oschina.net/u/3101282/blog/3022154
    - [ ] QueryHelper 优雅的替代方案
    - [ ] getPara系列方法的迁移
- [ ] JFinal interceptor 拦截器 -> Spring Interceptor
- [ ] @Service 临时使用,后期核心Service需要通过显式注册到容器中,方便外部控制
- [ ] 事务的兼容
  - @Before(Tx.class) 替代方案
  - @Before(HttpRequestHolder.class)


## Tips
### 兼容

- JFinal中getPara() 默认取 `http://xxx/avb/b/b/c` 出的是`c`,为了兼容Springboot,每个Mapping后都加了`/{object_code}`
  - [ ] 这种方式不够优雅,后期考虑通过`RequestMappingInfoHandlerMapping`集中改写看看
  
##### 参考

- https://gitee.com/blingking/jfinal-orm-spring-boot-starter