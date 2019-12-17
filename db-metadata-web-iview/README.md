# db-metadata-web-iview

### 表单设计器

- [form-create](https://github.com/xaboy/form-create)
- [vue-form-making](http://tools.xiaoyaoji.cn/)

- [form-design](https://github.com/vincentzyc/form-design)

#### 表单组件元对象设计
```json
{
        "name": "FormTmpl",
        "label": "表单模板",
        "component_name": "FormTmpl",
        "action": "/form/doAdd/{objectCode}",
        "conf": {
            "label-width": "100px",
            "size": "medium",
            "disabled": false,
            "rules": {
                "id": [{"required": true, "message": "必填字段", "trigger": "blur"}]
            }
        },
        "columns": [
            {
                "name": "id",
                "label": "ID",
                "component_name": "TextBox",
                "inline": false,
                "showable": true,
                "index": 1,
                "default_value": 1,
    
                "conf": {
                    "clearable": true,
                    "placeholder": "请输入..",
                    "readonly": true,
                    "disabled": false
                }
            }
        ],
        "btns": {
            "submit": {
                "label": "提交",
                "conf": {
                    "type": "primary"
                }
            },
            "cancel": {
                "label": "取消",
                "conf": {
                }
            }
        }
    }
```

### 表格设计

#### 表格组件元对象设计
```json
{
        "name": "TableList",
        "label": "表格模板",
        "component_name": "TableList",
        "data_url": "/table/list/{objectCode}",
        "delete_url": "/table/delete?objectCode={objectCode}&ids={ids}",
        "multi_select": true,
        "editable": false,
        "conf": {
            "default-sort": {"prop": "id", "order": "descending"},
            "highlight-current-row": true,
            "size": "medium"
        },
        "columns": [
            {
                "name": "name",
                "label": "label",
                "component_name": "TextBox",
                "editable": false,
                "searchable": true,
                "index": 1,
                
                "conf": {
                    "width": "50",
                    "sortable": true
                }
            }
        ],
        "pagination": {
            "page-size": 10,
            "page-sizes": [10, 20, 50, 100, 200],
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    }
```

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
- [x] 异步加载数据的组件,指定默认值
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
 -[x] 每个需要meta的组件都需要传入objectCode, 在meta的一级属性上添加
 -[x] 组件meta数据准备和组件渲染顺序, 是否应该支持异步渲染组件？
      - meta就绪前,是否应当渲染组件,亦或是阻塞(类似于v-if的手段)。目前的设计是, meta非必选;
      - 异步渲染的目标, 是希望meta数据就续前, 不影响组件的渲染, 等meta数据就绪后, 支持流入组件, 组件内部再根据meta进行重新构建组件并渲染
      > [解决] prop本身为单向流入, 介于组件内部会将prop传入的meta与全局配置meta执行merge操作, 导致有些属性未被vue跟踪. 因此在merge操作中
      支持Vue.$set进行k-v合并, 即可解决此问题.  
        
 -[x] 专门封装一个http请求, 对响应数据进行校验, 含有\\则reject, 或报错(axios/case.js)
 -[x] 对$message进行封装处理: element支持, 但需要对resp和err进行"msg"属性保障 处理(responseExchange.js)
 -[x] vue watch 看上去时生效, 时而不生效(data_url延迟)
 -[x] 组件中的变量初始值统一(null, {}等): 基本类型null, 对象{}/数组[]
 -[ ] 创建一个Meta class, 对所有组件的prop: meta 进行强类型校验, 同时初始化meta(和全局默认meta进行merge)
 -[ ] 重复路由点击报错(NavigationDuplicated), element bug. 等待element(2.13). https://github.com/ElemeFE/element/pull/17269
 -[ ] 思考组件强干预的策略:
    - 广泛应用插槽(视图干预)
    - ref引用(逻辑干预)
    - 模板(DOM)替换(模板干预: 视图、逻辑全替换, 支持可脱离vue单页应用?)
    
 -[x] DropDownBox, RadioBox, CheckBox支持三种传入选项数据的方式:
    - 硬编码传入options属性
    - 组件元对象中定义options属性和值
    - data_url组件内自动获取选项数据(GET), 响应格式需为[{key:value}, ...],若不满足,需要给组件元数据定义behavior选项,并自定义'format'函数,将响应数据转换为key-value格式
    
    优先级: 逐渐降低. 数据格式均需满足: [{key:value}, ...]
 -[x] CheckBox支持(a,b,c 多值形式)
 
 -[ ] 组件中各个属性先后位置尽量统一
 
        name
        mixins
        component
        props
        data
        methods
        watch
        beforeCreated
        created
        beforeMounted
        mounted
        computed
        
 -[x] 不同组件中相同功能的属性命名尽量统一
 -[x] const、let 分时适用
 -[x] 对象属性调用方式, 应当形成规约: obj.a OR obj['a']: 属性读取超过两级, 第二级后用['']方式读取
 -[x] this泛用优化: 箭头函数内部保留this作用域, 因此可以直接调用this;
 -[x] MetaConf界面优化-每个field单独一个JsonBox编辑框, 可单独编辑, 也可整体上传
    -[x] 后期根据表单生产器可视化拖拽创建配置

 -[ ] 表格(TableList)
    -[x] 字段值过长, 省略隐藏(...): 支持配置(element ui查询)
    -[x] 单行CRUD: 新增、查询、更新、删除
    -[x] 表格的单行/多行模式配置
    -[x] 表格多行模式下, 单击编辑/删除, 防止事件冒泡
    -[ ] 编辑行时, 涉及多个组件的跳转(跳转方式的配置),例如：
        - 弹框: TableList => FormTmpl
        - 开页: TableList => FormTmpl
        
        需要思考
    -[x] 下拉设置列显隐功能 由renderHeader实现 =>(替换为) 插槽实
    -[x] 表格的操作条支持插槽扩展
    -[ ] 目前SearchPanel没有嵌入在TableList, 存在一个问题: 当SearchPanel中有输入值, 
    再去改选TableList字段显隐时, SearchPanel中的参数如何带入到触发的搜索中？
    -[x] 表格排序支持: 
        ```
        设置字段的conf['sortable']为:
        1. false: 不允许排序(默认)
        2. true:  单页排序
        3. custom: 服务端排序
        ```
    -[ ] 主键名由后端提供(meta.objectPrimaryKey), 需要支持联合主键问题。当组件为联合主键时,编辑/新增判断需要调整支持, 批量删除需要调整支持(接口需要调整)
        
 -[ ] 表单Box(FormTmpl)
    -[x] 表单布局: 针对每个控件配置自定义(非element原生支持)inline属性
    -[x] 区分表单编辑/新增模式
    -[ ] 更新/新增 模式下字段的不同属性配置, 可以设置两套配置(新增、编辑)- 暂定用一套配置。 需要支持：
        - [x] 字段新增状态下： 正常|隐藏|只读|禁用
        - [x] 字段更新状态下： 正常|隐藏|只读|禁用
        - [x] 字段查看状态下： 正常|隐藏|只读|禁用
        根据接口(toUpdate/toAdd/toView)可以做处理, 前端透明; 对应配置为: 
        - 正常(能看,能生成sql):    (showable: true, readonly:false, disabled: false)
        - 隐藏(不能看,能生成sql:    (showable: false, readonly:true, disabled: false)
        - 只读(能看,不生成sql):    (showable: true, readonly: true, disabled: true)
        - 禁用(不能看,不生成SQL:    (showable: false, readonly: true, disabled: true)
    
 -[ ] dialog
    -[x] ~~dialog 全局配置~~
  
 -[ ] JsonBox
    -[x] 内部做兼容,支持Object, String(可转换为Object)
    
 -[ ] BoolBox
    -[x] 添加对[false, true, "false", "true", 0, 1, "0", "1", "f", "t"]的支持, 保存时保留原值;
    
 -[ ] SearchPanel 搜索面板
    -[x] 参见jsonTemplate.json或者default.js
    -[x] SearchPanel脱离FormTmpl独立, 内部根据SearchPanel的meta另行实现,需要考虑范围搜索(如日期范围,需要两个日期控件), 大于等于  等；
        ```
        操作符：
        - 等于(eq)
        - 不等于(ne)
        - 大于(gt)
        - 小于(lt)
        - 大于等于(ge)
        - 小于等于(le)
        - 范围(range)
        - in(in)
        - 模糊(like: 左匹配、右匹配，全匹配)
        ```
    
 -[ ] FindBox 搜索框
    -[x] 搜索框，参见jsonTemplate.json或者default.js
    -[ ] 支持多选
    -[ ] 支持sql配置FindPanel可选内容
    
 -[ ] FileBox 文件上传
    
    ```js
        // 上传后响应的数据
        [
           {"name": "readme.txt", "suffix": "txt", "url": "http://xxx", "token": "xxx"},
           // ...    
        ]
    ``` 
   
 -[ ] ImgBox 图片上传
    -[ ] 编辑表单时, ImgBox字段对应的value中键url的值应当为图片资源路径, 否则无法呈现并预览图片
 
 -[ ] FindPanel 查找面板(复杂)
    -[x] 参见jsonTemplate.json或者default.js
    -[x] 支持数组(多选)回选

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
    -[ ] ~~支持element原生属性的配置改为表单输入, 表单的构建建立在对element原生控件props的准确获取.~~
    -[x] 支持iframe可跳转至业务组件, url传参(?componentCode=&objectCode=), 组件接受的路由参数可从commonRoute.js中参考
 -[x] FormBuilder 表单项/表单 属性编辑转换为表单(element ui 配置简化采用JsonBox)
 -[ ] 组件复用方式, 如何方便、可靠的提供给其他应用复用组件: `npm install`
 -[ ] 表单渲染时需要整齐划一,控件的宽度统一指定;
 -[ ] meta_feature中的config字段需要根据featureType加载不同的mini表单
 
 
### 目录结构NEW
    - document
    - public
    - packages
        - dropdownbox
        - ...
    - src
        - mixins
        - utils
        - axios
        - constant
        - router
        - config
        - meta
        - layout
        - ...
    
 
### 其他资料

- vue中动态增加method https://stackoverflow.com/questions/51797029/create-method-dynamically-in-vue-js
- vue cli3使用vue.config.js，不再使用传统的webpack, 导致别名路径@设置后, IDE无法识别(@/constant).
  
  ```
    解决办法：将别名设置的部分抽取出来放到单独的配置文件`alias.config.js`，再配置IDE, WebPack->webpack configuration file 设置为alias.config.js即可。
  ```