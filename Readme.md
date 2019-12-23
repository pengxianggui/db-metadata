# db-metadata

## 元对象 于 组件的关联设计

- Eova中的页面搜索条件是与页面控件进行绑定的,页面控件的变化,会导致最后生成的sql 变化 
    > ? 搜索条件可以反过来与DBtype进行关联,做兼容处理 -> 各种类型可以支持常规检索(值检索,范围检索,区间检索)
- 元对象,元子段和前端控件的关系
    > 
    
## 元对象多了以后 需要提供有效的管理手段

## 抽象概念

- 组件(Component)
    > 每个组件需要有一套默认的全局的配置;
    - 数据展示(ViewComponent)
        - 表格组件(依赖元对象)(TableView)
        - 表单组件(依赖元对象)
        - 树型组件(依赖元对象)
        - 搜索组件(依赖元对象)(简单的可以使用穿梭框)
    - 表单(FormView)
        - 单选组件(元子段)FormField
        - 多选组件(元子段)
        - 输入组件(元子段)
        - 业务查询组件
        - 开关组件(元子段)
        - 日期组件(元子段)
        - 范围组件(元子段)
        - 上传
    
    ```
    初始化过程分开:
    手动初始化
    根据元对象或元子段初始化
    
        new  -> 手动静态组件
             -> load全局配置
             -> [组装器](组件+元对象)
    ```
    
- 功能
    > 功能可以是一个按钮+背后的逻辑
    > 功能可以是一个纯背后的逻辑
    > 功能可以是一个页面
    ```
    功能 = (SearchBar(Component) + UIConfig + 元对象) * n
    功能 = Table(Component) + UIConfig + 元对象
    功能 = 功能 * n
    ```
- 模板
```
[模板 = 功能 * n]  与 [功能 = 功能 * n] 的区别?
可以固化?
可以打包?
有后台接口?
嵌入方式?
```
    > 模板不能直接使用
    > 模板硬编码编辑
    - 单表格
    - 树表格
    - 主子表(1:1)(1:N)
- 元对象
    > 新建元对象的时候,需要对元对象提供一套默认的ui配置信息;
    - 元对象和数据组件的展示是有机结合,结合后就是功能的配置
    - 元对象的config 应保存与UI无关的配置(是否使用uuid生成主键,是否有前置后置逻辑,默认排序,记录筛选条件等等?)
        ```
        主要存放针对服务端的一些配置
        ```
- 关于配置的继承

```
- 组件的全局UI配置<元对象的UI配置<功能的配置
- 功能配置

功能之间的组合???
```

```
Extjs 的文档, 可以参考
https://docs.sencha.com/extjs/6.2.0
```


## 元对象管理功能

- 元对象的导入;
- 元对象的确认+修改;
- 元对象在不同组件中的配置(不同组件的行为);
- 集中保存元对象信息,和其他数据;

###  FQ

- 组件之间的路由? 按钮触发component? 事件触发component?


`
## 可以有的功能和思考

- [ ] 异常管理
- [ ] 配置比对
- [ ] 4个基础字段的传递
- [ ] 注释生成https://blog.csdn.net/10km/article/details/78252586
- [ ] 打包部署
    ```
    1. vue -> run dist
    2. copy to javaProject/resources/static/
    3. package -> fatjar
    ```
- [ ] analysis 工程的打包(基于undertow的)
- [ ] 围绕元对象的权限模块
- [ ] Json 比较
    ```
    https://github.com/5SSS/vue-json-compare
    ```
- [ ] Json Editor
    ```
    https://github.com/yourtion/vue-json-ui-editor
    ```
- [ ] 集成方式 
    - frame
    - 生成.vue?
- [ ] 元信息的缓存,component数据的缓存,减少数据拼装动作,提高查询效率 
- [ ] 围绕元数据产生的复杂业务场景思考
- [ ] 表单重复提交
- [Undertow 2.0 文档](http://undertow.io/undertow-docs/undertow-docs-2.0.0/index.html#bootstrapping-undertow)
- [x] Json 序列化入库时 boolean 类型的值带""导致vue报错
    > 解决:SerializerFeature.WriteNonStringValueAsString 配置引起
- [ ] 表单 tabIndex
- [ ] mysql 数据库版本8.0 不兼容
- [ ] 脏数据,错误数据处理与提示;


[Maven：Maven GPG Plugin](https://blog.csdn.net/en_joker/article/details/84140033)

## RoadMap
- server 源代码方式集成,剥离db-metadata-server业务逻辑和容器有关的逻辑,目的为了上层使用其他mvc框架做支持;
- 



## Spring 相关
- [借助ImportBeanDefinitionRegistrar接口实现bean的动态注入](https://www.jianshu.com/p/2b993ced6a4c)
- [jfinal-spring-boot-starter](https://github.com/ArtIsLong/jfinal-spring-boot-starter)
- [SpringBoot完美整合Jfinal](https://www.jianshu.com/p/e7c1d069a78f)