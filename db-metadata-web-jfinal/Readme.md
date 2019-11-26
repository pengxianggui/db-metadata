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

- [ ] 增加 MetaObject / MetaField 的mock手段;
- [ ] db-metadata-analysis的数据源 目前只绑定了一个
- [ ] db-metadata-web-jfinal 端的数据库操作,未做多数据源的指定;
- [ ] 在实例配置阶段删除字段问题(无法彻底删除,因系统多数遍历 使用metaobject.fields) 