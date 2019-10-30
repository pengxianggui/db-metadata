# db-metadata-web-iview

### Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn run serve
```

### Compiles and minifies for production
```
yarn run build
```

### Run your tests
```
yarn run test
```

### Lints and fixes files
```
yarn run lint
```


### 表单设计器

- [form-create](https://github.com/xaboy/form-create)
- [vue-form-making](http://tools.xiaoyaoji.cn/)

- [form-design](https://github.com/vincentzyc/form-design)

### 所有组件默认处理的响应数据格式为
```
{
    state: String,
    code: Number,
    msg: String,
    data: [Object, Array]
}
```


- [ ] 全局局部方法 约定;
    - data() 中变量的格式统一约定
       ```
       如组件公共属性:name,label 
       用来绑定form的数据对象使用model命名?
       data() {
            return {
                model: {},
                tableOptions: [],
                schemaOptions: []
            }
        }
       ```
- [ ] 异步加载数据的组件,指定默认值
- [ ] 表格中的批量编辑

### 概念
- 元组件： 支持传入组件元数据, 构建组件内部业务逻辑, 传入不同组件元数据, 组件表现不同；
    
    ```
    核心概念： 组件元数据(meta), 该属性从父组件单向流入, 用于初始化组件。若未传入, 
    内部会采用该组件默认的全局元数据。如果元组件是肉体, meta就是灵魂, 只有给元组件
    注入特定的meta,才会产生有实际含义的组件。
    
    一个meta一般是这样的(以TableList的为例):
    {
        name: "book",
        label: "书籍",
        data_url: "/book/list",
        component_name: "TableList",
        conf: {
            // 一般是UI配置, 支持element ELTable的所有配置
            "default-sort": {prop: "id", order: "descending"},
            "max-height": 500,
        },
        columns: [
            {
                name: "id",
                label: "ID",
                component_name: "TextBox",
                conf: {
                    // 支持element ELInput的UI配置
                }
            },
            ...
        ],
        behavior: { // 行为配置因具体元组件支持不同而不同
            xxx: function(data) {
            }
        }
    }
    ```
    
- 业务组件: 特定功能下的业务组件；

### TODO
 -[x] meta-Manager 默认不加载
 -[x] vue watch 看上去时生效, 时而不生效(data_url延迟)
 -[ ] 组件中的变量初始值统一(null, {}等)
 -[ ] 组件中各个属性先后位置尽量统一
 -[ ] 不同组件中相同功能的属性命名尽量统一
 -[ ] const、let 分时适用
 -[ ] 对象属性调用方式, 应当形成规约: obj.a OR obj['a']
 -[ ] this泛用优化
 -[ ] MetaConf界面优化-每个field单独一个JsonBox编辑框, 可单独编辑, 也可整体上传
 
 
 
### 其他资料

- vue中动态增加method https://stackoverflow.com/questions/51797029/create-method-dynamically-in-vue-js
