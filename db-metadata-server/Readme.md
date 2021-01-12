# db-metadata-server

> 元对象系统server主要提供基于元对象的web访问服务
> - 系统提供元对象,组件,功能,菜单,路由的CRUD动作支持
> - 系统各模块输入为MetaObject,输出一般J-dson数据
> - 系统可以通过Fatjar形态独立部署,亦可通过第三方Jar引入已有web工程

## 依赖

- jfinal
- guava
- mysql-connector-java
- fastjson
- jfinal-undertow (非必须,只有在Fatjar时用)

## 部署步骤

1. 初始化数据库db/script/wisdom-metadata.sql
1. 配置config.properties
    ```properties
    # 是否开发模式,部分动作只有在开发模式下可触发
    devMode=true 
    # 允许读取默认defaultInstance.json,defaultObject.json对初始配置进行覆盖
    config.allow.replace=true
    component.allow.replace=true
    # 默认上传功能的文件保存路径
    upload.dir=/opt/www/db-meta-serve
    #元对象Server默认的登录和鉴权开关
    user.login=false
    user.auth=false
    # 配置主数据库
    # 业务系统可与主数据库共存,也可单独配置
    # Important !! 目前还未支持跨库事务,分布式事务,有强事务操作尽可能共用一个库
    main.jdbc.url=jdbc:mysql://localhost:3309/wisdom-metadata?useSSL=false&characterEncoding=utf-8
    main.jdbc.username=root
    main.jdbc.password=gongwei911
    # 配置业务数据库 (0->N)
    biz.db=数据库1,数据库2,数据库3
    数据库1.jdbc.url=jdbc:mysql://127.0.0.1:3309/wisdom-public?useSSL=false&characterEncoding=utf-8
    数据库1.jdbc.username=root
    数据库1.jdbc.password=gongwei911
    数据库2.jdbc.url=jdbc:mysql://127.0.0.1:3309/wisdom-public?useSSL=false&characterEncoding=utf-8
    数据库2.jdbc.username=root
    数据库2.jdbc.password=gongwei911
    数据库3.jdbc.url=jdbc:mysql://127.0.0.1:3309/wisdom-public?useSSL=false&characterEncoding=utf-8
    数据库3.jdbc.username=root
    数据库3.jdbc.password=gongwei911
    ```
1. 数据库配置无误后,运行AppWebConfig,工程默认引入了jfinal-undertowserver内嵌web容器,作为第三方jar使用时参考`jfinal-spring-boot-demo`工程;
    ```java
    public class AppWebConfig extends JFinalConfig {
      public static void main(String[] args) {
        UndertowServer.start(AppWebConfig.class, 8888, true);
      }
    }
    ```

## ToDo List

- [x] 增加 MetaObject / MetaField 的mock手段;
  > ManualMetaObject,ManualMetaField 等支持
- [x] db-metadata-analysis的数据源 目前只绑定了一个
  > 多数据源支持,一个元数据主数据源,和元对象自身携带的数据源(schemaName与config数据源配置要一致)
- [x] db-metadata-web-jfinal 端的数据库操作,未做多数据源的指定;
  > 目前支持简单的多数据源并行,但没有良好的切换机制
- [ ] 在实例配置阶段删除字段问题(无法彻底删除,因系统多数遍历 使用metaobject.fields)
- [x] 推敲xxxView构建逻辑,以配置为准 还是以fields为准
    - [x] 以fields为准,则需要在metaField中对字段是否予以显示做开关
  > 以配置为准,则需要在所有渲染的地方,对"{}"做逻辑判断,防止渲染;
- [ ] https://github.com/fslev/json-compare

- [x] 复选checkbox,选中的值无法命中 数据库中1,23,4数据
- [ ] Mysql JSON 操作;
- [x] 树型逻辑支持
- [x] 主子表时 新增子表,要带主表的主键
    ```
    1. 功能(模板)纬度的设计要考虑了,携带值打开form 这个逻辑放在基础逻辑当中
  还是放在功能(模板)纬度    
    ```
- [x] 简易的数据权限(基于4个字段,可以利用sqlparse 动态追加 sql)
  > [利用druid数据源的SQL解析器构建数据权限模块](https://blog.csdn.net/weixin_30624825/article/details/95331046)
- [x] 多数据源查询,动态切换数据源问题;
- [x] meta_feature加入is_sys系统标示, 防止误删除
- [ ] defaultInstance.json中如何为test_table的TableList设置元对象级别的配置?目前只支持设置字段配置
- [ ] 系统模块的生成,走json配置文件生成功能入feature
- [x] 在UploadController 增加图片预览逻辑;
- [x] 元对象增加默认排序
- [ ] dicts字典自动导入到数据库
- [ ] 缓存的支持

##### BUG

- [x] searchPanel 无实例配时,会成功渲染
  > 允许使用smart自动计算的配置来支持默认渲染
- [x] metaField config 默认返回MetaFieldConfigParse实例,@deprecated -> ImetaFieldConfig

##### 其他

- [JMS规范](https://docs.oracle.com/cd/E19688-01/817-5020-10/overview.html#46007)

