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
 -[ ] 每个需要meta的组件都需要传入objectCode, 在meta的一级属性上添加
 -[ ] 专门封装一个http请求, 对响应数据进行校验, 含有\\则reject, 或报错(axios/case.js)
 -[ ] 对$message进行封装处理
 -[x] vue watch 看上去时生效, 时而不生效(data_url延迟)
 -[x] 组件中的变量初始值统一(null, {}等): 基本类型null, 对象{}/数组[]
 -[ ] 组件中各个属性先后位置尽量统一
 -[ ] 不同组件中相同功能的属性命名尽量统一
 -[ ] const、let 分时适用
 -[x] 对象属性调用方式, 应当形成规约: obj.a OR obj['a']: 属性读取超过两级, 第二级后用['']方式读取
 -[x] this泛用优化: 箭头函数内部保留this作用域
 -[x] MetaConf界面优化-每个field单独一个JsonBox编辑框, 可单独编辑, 也可整体上传
    -[ ] 后期根据表单生产器可视化拖拽创建配置
 
 -[ ] 表格(TableList)
    -[ ] 字段值过长, 省略隐藏(...)
    -[ ] 单行CRUD: 新增、查询、更新、删除
    -[ ] 表格的单行/多行模式配置
    -[x] 表格多行模式下, 单击编辑/删除, 防止事件冒泡
    -[ ] 编辑行时, 涉及多个组件的跳转(跳转方式的配置),例如：
        - 弹框: TableList => FormTmpl
        - 开页: TableList => FormTmpl
        
        需要思考
    -[x] 下拉设置列显隐功能 由renderHeader实现 =>(替换为) 插槽实现
        
 -[ ] 表单(FormTmpl)
    -[ ] 表单布局
    -[ ] 更新/新增 模式下字段的不同属性配置, 可以设置两套配置(新增、编辑)

 -[ ] Meta-Conf
    -[ ] 表单保存格式问题, 如果元对象编码和字段名一致导致key重复 
        - 当前保存的格式为：
        ```
        {
            // 组件
            TableList: {},
            // 元对象
            test_table: {component_name: 'TableList', name: '', label: '', conf: {}},
            // 下面是字段
            id: {},
            created_by: {},
            // 其他字段 ...
        }
        ```
        - 可以改为:
        ```
        {
            component: {
                TableList: {},
            },
            object: {
                test_table: {}
            },
            field: {
                id: {},
                created_by: {},
                // 其他字段 ...
            }
        }
        ```
 
 
 
### 其他资料

- vue中动态增加method https://stackoverflow.com/questions/51797029/create-method-dynamically-in-vue-js
