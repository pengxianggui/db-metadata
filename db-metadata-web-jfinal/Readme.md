#### 代码段说明

```java

/**
* 释义: 
* 循环kv元素添加至mate中,覆盖策略:使用旧值
*/
kv.forEach((k,v)->meta.merge(k,v,(oldVal,newVal)-> {
            return oldVal;
}));
```
MetaField 数据源配置

```
"scopeSql": "select value id,name cn from meta_dict where pid=1",
//如value 为 1,2 int类型,则下拉框反赋值时会直接显示value,而非key
"scopeOptions": [{ "key": "男", "value": "1" }, { "key": "女", "value": "2" }],
"scopeRange": ["options1","options2","option3"],
```

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
- [ ] 树型逻辑支持
- [ ] 主子表时 新增子表,要带主表的主键
    ```
    1. 功能(模板)纬度的设计要考虑了,携带值打开form 这个逻辑放在基础逻辑当中
  还是放在功能(模板)纬度    
    ```
- [ ] 简易的数据权限(基于4个字段,可以利用sqlparse 动态追加 sql)
    > [利用druid数据源的SQL解析器构建数据权限模块](https://blog.csdn.net/weixin_30624825/article/details/95331046)
- [x] 多数据源查询,动态切换数据源问题;
- [x] meta_feature加入is_sys系统标示, 防止误删除
- [ ] defaultInstance.json中如何为test_table的TableList设置元对象级别的配置?目前只支持设置字段配置
- [ ] 系统模块的生成,走json配置文件生成功能入feature
- [ ] 在UploadController 增加图片预览逻辑;
- [x] 元对象增加默认排序
- [ ] dicts字典自动导入到数据库
##### BUG
- [x] searchPanel 无实例配时,会成功渲染
    > 允许使用smart自动计算的配置来支持默认渲染
- [x] metaField config 默认返回MetaFieldConfigParse实例,@deprecated -> ImetaFieldConfig
