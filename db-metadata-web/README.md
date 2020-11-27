# meta-element

> 数据驱动的前端组件库(基于`Vue + elementUI`).需要配合后端(db-metadata-service)使用, 形成快速开发平台.以表驱动一套完整的业务模块.　
需要做的仅仅是做一些配置.或开发自己的模板. 后端地址: [db-metadata-service]()

## 使用方式
1. iframe嵌入: 基于fatjar(前段整合进后端中), 具体使用参见后端说明
2. 代码集成(npm install): 前端组件已经发布至npm,　可以下载使用,　基于组件库(配合后端接口)快速开发业务模块;

### iframe引入
在fatjar项目中创建对应的元对象和菜单功能后, 访问对应的url,　即可渲染既定的模板实例.　如:

```html
<iframe style="width: 100%; height: 100%;" src="http://localhost:8080/#/table?featureCode=iot_dict" frameborder="0"></iframe>
```
其中src的值需要参考系统支持的[内置模板]().

### 代码集成
1.`cdn`
```javascript

```

2.`npm`

(1). 下载
```bash
npm i meta-element -S
# 或
yarn add meta-element
```

(2). 安装
```javascript
import Vue from 'vue'
import metaElement from 'meta-element'
const config = {
   axios: {
       bashURL: ''
   },
   authorities: ['ADMIN'],
   // ...
};
Vue.use(metaElement, config)
```
> 由于`meta-element`内部使用`element`, `axios`等,　因此有关配置用户可通过`config`自定义.
更多关于自定义配置的说明参见[配置说明]()

(3). 使用

使用参见[组件库]()

## 配置说明
- axios : axios配置对象,　由于meta-element内部会进行axios请求, api接口的根路径需要通过此进行配置,　以及一些其他关于axios的配置。
- authorities: meta-element内置了Vue全局方法$hasAuth,　并且内部部分组件需要进行权限控制, 此属性是内部鉴权的角色依据.
- 其他...

## 组件库
查看[《组件库说明》](./document/组件库说明.md)

## 资料
### 表单设计器

- [form-create](https://github.com/xaboy/form-create)
- [vue-form-making](http://tools.xiaoyaoji.cn/)

- [form-design](https://github.com/vincentzyc/form-design)

## 说明

### 响应数据格式为
```
{
    state: String,
    code: Number,
    msg: String,
    data: [Object, Array]
}
```

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

## TODO
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
    -[x] 主键名由后端提供(meta.objectPrimaryKey), 需要支持联合主键问题。当组件为联合主键时,编辑/新增判断需要调整支持, 批量删除需要调整支持(接口需要调整)
    -[x] 像activeData, choseData目前并不支持从父组件流入其中，只是单向从TableList流向父组件，因此改为emit触发，还可以取消父组件对该属性的watch
    -[ ] 行数据查看(form只读)目前两种方案: 1. 双击行(或添加“查看”按钮); 2. 行下拉展开详细信息
    -[ ] 更多定制化的行操作按钮可以提供一个hover的“更多”下拉菜单
    -[ ] 除了弹窗, 也需要支持右侧抽屉拉出模式——不同按钮操作下,抽屉和弹窗两种模式如何通过配置指定？
    -[ ] 单元格render(转义)
    
 -[ ] Tree
    -[x] 多选(check)
    -[ ] 搜索过滤
    -[x] 展开/收缩所有
    -[x] 全部勾选/取消全选/反选
    -[ ] 添加/删除(批量)/编辑 节点
        
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
    
    -[ ] 表单渲染时需要整齐划一,控件的宽度统一指定;
    
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
    -[x] 支持sql配置FindPanel可选内容
    
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
    -[x] 支持iframe可跳转至业务组件, url传参(?componentCode=&objectCode=), 组件接受的路由参数可从splitRoute.js中参考
 -[x] FormBuilder 表单项/表单 属性编辑转换为表单(element ui 配置简化采用JsonBox)
 -[ ] 组件复用方式, 如何方便、可靠的提供给其他应用复用组件: `npm install`
 -[ ] meta_feature中的config字段需要根据featureType加载不同的mini表单——等featureType类型较多时再采用此方案
 -[x] 新增功能弹窗，主键的输入改成下拉选择
 
        类似这样的输入都限定下拉选择；数据驱动一切，因此项目中数据的正确性至关重要，所有用户输入的入口，都必须用尽办法保证输入的正确性
        
 -[x] created中初始化一些meta数据, mounted初始化业务数据 
  

## 功能模板结构(config)
 - SingleGridTableTmpl —— 单表模板：普通表
 ```json
    {
      "icon": "el-icon-s-grid", 
      "singleGrid": {
        "objectCode": "test_table"
      }
    }
 ```
 - MasterSlaveTableTmpl —— 主子表模板
 ```json
    {
      "master": {
        "objectCode": "meta_object",
        "primaryKey": "code"
      },
      "slaves": [
        {
          "objectCode": "meta_field",
          "foreignFieldCode": "object_code",
          "order": 1
        },
        {
          "objectCode": "change_log",
          "foreignFieldCode": "object_code",
          "order": 2
        }
      ],
      "icon": ""
    }
 ```
 - DataListTableTmpl —— 左侧列表-右侧单表模板
 ```json
    {
      "list": {
        "objectCode": "XXX",
        "primaryKey": "x",
        "label": ""
      },
      "table": {
        "objectCode": "YYY",
        "foreignFieldCode": "y"
      },
      "icon": ""
    }
 ```
 - TreeSingleGridTmpl —— 单表模板：可展开的树表
 ```json
    {
        "icon": "el-icon-s-management",
        "table": {
            "objectCode": "YYY",
            "primaryKey": "id",
            "foreignKey": "pid"
        }
    }
 ```
 - TreeTableTmpl —— 左侧树-右侧表模板
 ```json
    {
      "tree": {
        "objectCode": "",
        "primaryKey": "",
        "idKey": "",
        "pidKey": "",
        "rootIdentify": "",
        "label": "",
        "isSync": false
      },
      "table": {
        "objectCode": "",
        "foreignFieldCode": "y"
      },
      "icon": ""
    }
 ```
 - TableFromTmpl —— 左侧表-右侧表单模板
 ```json
    {
      "table": {
        "objectCode": ""
      },
      "icon": ""
    }
 ```
 - TreeFormTmpl —— 左侧树-右侧表单模板
 ```json
    {
      "tree": {
        "objectCode": ""
      },
      "form": {
        "objectCode": ""
      },
      "icon": ""
    }
 ```

#### 系统固定模板
1. MetaDataManager
 -[ ] MetaDataManager为系统模板, 模板类型为主子表(MasterSlaveTableTmpl)), 但内部并基于MasterSlaveTableTmpl扩展.需要重构, 重构思路:
    
    1). 基于MasterSlaveTableTmpl扩展, 需要升级MasterSlaveTableTmpl使之具备这般可扩展性;
    2). 依旧将MetaDataManager作为系统固化模板, 取消对feature的请求, 直接请求主子元对象(meta_object, meta_field), 从而取消歧义;
  
 
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
  
  
## ChangeLog
### 0.1.2
1. 添加`IconBox`
2. 取消FindPanel的导出, 仅在FindBox中使用
3. 更新merge.js中的打印日志级别
4. 增加内置模板的扩展插槽, 使内置模板具备基本的扩展能力
5. 修复其他小缺陷

### 0.1.3
1. `TableList` 单元格自定义渲染扩展, 可在meta中设置render属性, 属性值为函数字符串, 详细参见组件描述;
2. 无渲染行为插槽应用于`FormView`, 可通过无渲染行为插槽扩展`FormView`默认的提交和取消行为;
3. 组件实例配置引入`instanceCode`概念, 原先`comp_code`和`dest_object`不再表示唯一键, 慢慢过渡到使用`instanceCode`作为唯一键;
    > 一个元对象在FormView上可能不止一套配置
4. 添加`MiniFormBox`, 详见组件描述;
5. 添加`CodeBox`, 详见组件描述;
6. `MiniFormBox`支持以`JsonBox`形态展示并编辑数据

### 0.1.4
1. 导出meta管理组件

### 0.1.5
1. 纠正FormView的注册名(FormBox => FormView)
2. 修复FormBuilder预览错误

### 0.2.0
修复部分问题

### 0.2.1
1. 新增表格类别模板的刷新API;

### 0.2.2
1. 将组件的默认UI配置导出;
2. 将全局函数和全局变量放到Vue.prototype.$metaElement变量下,避免和自定义函数/变量 或 第三方冲突;

### 0.2.3
1. 导出的全局函数纠正-挂载到Vue.prototype实例上， 全局变量挂载到Vue.prototype.$metaElement上

### 0.2.4
1. TableList 支持 直接通过属性配置 多选框显隐、操作列显隐

|方式|key|类型|优先级|
|---|---|---|---|
|标签属性配置|multi-select|Boolean|最高|
|meta属性配置|multi_select|Boolean|其次|

### 0.2.5
1. 新增 tags 组件

### 0.2.8
1. 修复InstanceConfEdit编辑时会会反复发起组件列表请求（原因：任意修改配置内容,会影响meta的生成——因为meta的生成依赖于该配置项的value值,从而触发迷你表单重新构建）。解决办法：将配置加载后一次性构建整个meta
2. UIConfig 配置界面布局实现优化(by gird布局);MiniFormBox切换展示状态时,保持数据双向同步正常，同时基于value刷新MiniFormBox的meta时,避免频繁刷新组件列表接口;
3. 为UIConfig添加帮助提示内容容器. 提示内容需要再专门统一撰写;

### 0.2.9
1. 修复Tree组件中缺陷
2. 调整label-width宽度

### 0.2.10
1. DataList 提供默认渲染插槽

### 0.2.11
1. 元对象逻辑Conf中新增bizInterceptor配置项

### 0.2.13
将URL常量提供自定义覆盖的口子, 允许鹊桥覆盖一些url, 包括restUrl和routeUrl; 移除无效文件


### 0.2.25
FindBox修复双击回选无效的bug;

### 0.2.28
规范部分组件name, 和文件名相同

### 0.2.34
将meta路由, 以及菜单封装打包提供, meta菜单(平台维护下)和meta路由都由meta-element提供, 可以确保两者的统一

### 0.2.35
将一些自定义样式打包带入, 防止每次el-card无内边距

### 0.2.36 、0.2.37
简化客户端路由覆盖配置

### 0.2.37 ~ 0.2.44
MetaEasyEdit权限功能配置

### 0.2.45
添加富文本组件(基于[Tinymce](https://www.tiny.cloud/docs/integrations/vue/#forminputbindingsv-model)),

暂未提供更多可配置化内容, 后期完善。

### 0.2.46
导出直接获取某个元对象的form meta(Add、Update)

### 0.2.47
调整FormView item插槽的上抛的绑定数据, 将整个model引用数据抛出, 以便于外部修改其中
的值, 可以实现回传(双向绑定), 若抛出的是基本类型value， 则无法在外部被绑定（v-model）

### 0.2.48
FileBox, ImgBox支持差异化多文件

### 0.2.52
修复SearchPanel配置expand为true无法生效的bug;

### 0.2.53
- 新增SvgIcon组件(待完善)
- 新增菜单组件(暂时只支持内置dataUrl加载菜单数据,待完善);
- 将MetaEasyEdit中快捷UI编辑的路由拎到导出的MetaRoute中，可以避免客户端从MetaEasyEdit入口编辑UI conf时，打开的UI conf组件不是全视图的，而是位于子route-view中;

### 0.2.60
组件命名规范统一
修复一些缺陷

### 0.3.0
支持动态路由
支持动态菜单
提供默认的AdminLayout
简化自定义配置(Vue.use(MetaElement))

### 0.3.1
优化AdminLayout插槽

### 0.3.2~0.3.10
修复一些问题

### 0.3.11
- 为Tinymce配置apiKey, 解决富文本框提示未注册问题, 并配置富文本的表格/图片功能(上传暂未支持, 目前是链接外部图片);
- 调整meta-element默认的AdminLayout布局中插槽位置, 提升自定义程度;
- 修复路由管理页面无法滚动;
- 兼容路由数据中meta为对象字符串;
- 修复meta-element菜单高度无法100%;

### 0.3.12~0.3.13
修复一些问题

### 0.3.14
更新FormTmpl, 支持type区分新增表单还是编辑表单;
SvgIcon支持三种模式: 外部链接、elementUI字体图标、svg图标(需要客户端处理svg图)

### 0.3.15~0.3.18
TableTreeView删除记录
IconBox支持点选扩展的svg图标
修复ImgBox, FileBox删除后vModel值为[{}], 应当为[]
JsonBox 将传入的值转为合法值

### 0.3.19
新增TableView和TableTreeView的查看支持(formType:view);
移除TableView和TableTreeView无用的插槽绑定元素;
修复一处显示缺陷;

### 0.3.20
修复一些小问题
Meta菜单支持根据ROOT角色控制显隐;
元数据管理修复两处缺陷：批量删除源对象异常 和 创建好源对象后提示跳转UI配置;

### 0.3.21~0.3.25
SearchView组件支持UIConf配置初始搜索值，并支持定义directlyTrigger的筛选项;
修复一些问题

### 0.3.30~0.3.33
支持tagView

### 0.3.34~0.3.35
优化部分代码
tagView固定

### 0.3.36 ~ 0.3.37
支持菜单排序
支持更便捷的装配动态路由
创建功能时集成路由和菜单的快捷创建;

### 0.3.38
提供地区选择器
### 0.3.39
修复BoolBox缺陷: 当传入值为空串, 会选中

### 0.3.46~0.3.48
TableView和TableTreeView meta配置增强, 行内操作配置的show支持函数回调配置控制行的显影
修复一些缺陷

### 0.5.53
TableCell中将行记录传入render函数，以便更丰富的扩展;
修复SearchView一处缺陷: NumBox比较逻辑下拉没有出来

### 0.5.55
axios对象不再由MetaElement负责创建, 由外部创建并作为必配置项传入

### 0.5.56
axios对象由外部实例化后, 响应拦截时的数据装配也有外部负责, 为防止this.$message.info(err)时, err不是字符串会发生报错，并影响中断
逻辑, 用解构对进行处理。

### 0.5.57~0.5.60
修复一些缺陷;

### 0.5.61
RichTextBox改为使用vue-tinymce-text

### 0.5.62
修复切换tagview时, RichTextBox会被隐藏的问题：此缺陷原因未知，当router-view加上transition时，此缺陷修复。若未加，此RichTextBox中的
编辑框会在切换回来后，奇怪的被加上display:none的样式.

### 0.5.65
TagView由path作为唯一键, 改为fullPath。对于相同的路由path，当路由参数不同时
也认为是两个不同的路由。这很适用, 当存在某个路由指向编辑页面，而需要开多个编辑页面时，可以满足这种情况。

### 0.5.66
新增导出TagViewUtil, 可以使用TagViewUtil.deleteView对当前路由tag进行关闭。也可以使用
$router.push(TagViewUtil.pop())回到TagView中最后一个路由，从而替代$router.go(-1)

### 0.5.68
1. TreeView, TableView, TableTreeView添加meta 配置:
```json
"oper_logic": {
    "chose_type": "default"
}
```
表示是否可取消选中的记录;

2. TreeTableTmpl添加SearchView面板支持

### 0.5.70
1. 优化组件实例配置编辑界面, 新增/编辑二合一;优化该部分的代码, 增加部分组件的复用性; 支持从列表页进入编辑界面编辑指定的MetaField;
2. TagView支持自定义关闭, 配合路由可实现关闭回到上一个视图;