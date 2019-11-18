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
"scopeRange": [1,3,4,5],
```