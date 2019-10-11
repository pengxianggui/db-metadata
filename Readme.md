# db-metadata

## 元对象 于 组件的关联设计

- Eova中的页面搜索条件是与页面控件进行绑定的,页面控件的变化,会导致最后生成的sql 变化 
    > ? 搜索条件可以反过来与DBtype进行关联,做兼容处理 -> 各种类型可以支持常规检索(值检索,范围检索,区间检索)
- 元对象,元子段和前端控件的关系
    > 
    
## 元对象多了以后 需要提供有效的管理手段

## 抽象概念

- 组件
    > 每个组件需要有一套默认的全局的配置;
    - 数据展示
        - 表格组件(依赖元对象)
        - 表单组件(依赖元对象)
        - 树型组件(依赖元对象)
        - 搜索组件(依赖元对象)(简单的可以使用穿梭框)
    - 表单
        - 单选组件(元子段)
        - 多选组件(元子段)
        - 输入组件(元子段)
        - 业务查询组件
        - 开关组件(元子段)
        - 日期组件(元子段)
        - 范围组件(元子段)
        - 上传
    
    ```
    初始化过程:
        new  -> load全局配置
             -> 
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
- 关于配置的继承
```
- 组件的UI配置<元对象的UI配置<功能的配置
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
## 功能

- [ ] 异常管理
- [ ] 配置比对
- [ ] 4个基础字段的传递