# Meta-Element ChangeLog

> 一些相对小的改动可能不做记录

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

1.
修复InstanceConfEdit编辑时会会反复发起组件列表请求（原因：任意修改配置内容,会影响meta的生成——因为meta的生成依赖于该配置项的value值,从而触发迷你表单重新构建）。解决办法：将配置加载后一次性构建整个meta
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

调整FormView item插槽的上抛的绑定数据, 将整个model引用数据抛出, 以便于外部修改其中 的值, 可以实现回传(双向绑定), 若抛出的是基本类型value， 则无法在外部被绑定（v-model）

### 0.2.48

FileBox, ImgBox支持差异化多文件

### 0.2.52

修复SearchPanel配置expand为true无法生效的bug;

### 0.2.53

- 新增SvgIcon组件(待完善)
- 新增菜单组件(暂时只支持内置dataUrl加载菜单数据,待完善);
- 将MetaEasyEdit中快捷UI编辑的路由拎到导出的MetaRoute中，可以避免客户端从MetaEasyEdit入口编辑UI conf时，打开的UI conf组件不是全视图的，而是位于子route-view中;

### 0.2.60

组件命名规范统一 修复一些缺陷

### 0.3.0

支持动态路由 支持动态菜单 提供默认的AdminLayout 简化自定义配置(Vue.use(MetaElement))

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

更新FormTmpl, 支持type区分新增表单还是编辑表单; SvgIcon支持三种模式: 外部链接、elementUI字体图标、svg图标(需要客户端处理svg图)

### 0.3.15~0.3.18

TableTreeView删除记录 IconBox支持点选扩展的svg图标 修复ImgBox, FileBox删除后vModel值为[{}], 应当为[]
JsonBox 将传入的值转为合法值

### 0.3.19

新增TableView和TableTreeView的查看支持(formType:view); 移除TableView和TableTreeView无用的插槽绑定元素; 修复一处显示缺陷;

### 0.3.20

修复一些小问题 Meta菜单支持根据ROOT角色控制显隐; 元数据管理修复两处缺陷：批量删除源对象异常 和 创建好源对象后提示跳转UI配置;

### 0.3.21~0.3.25

SearchView组件支持UIConf配置初始搜索值，并支持定义directlyTrigger的筛选项; 修复一些问题

### 0.3.30~0.3.33

支持tagView

### 0.3.34~0.3.35

优化部分代码 tagView固定

### 0.3.36 ~ 0.3.37

支持菜单排序 支持更便捷的装配动态路由 创建功能时集成路由和菜单的快捷创建;

### 0.3.38

提供地区选择器

### 0.3.39

修复BoolBox缺陷: 当传入值为空串, 会选中

### 0.3.46~0.3.48

TableView和TableTreeView meta配置增强, 行内操作配置的show支持函数回调配置控制行的显影 修复一些缺陷

### 0.3.53

TableCell中将行记录传入render函数，以便更丰富的扩展; 修复SearchView一处缺陷: NumBox比较逻辑下拉没有出来

### 0.3.55

axios对象不再由MetaElement负责创建, 由外部创建并作为必配置项传入

### 0.3.56

axios对象由外部实例化后, 响应拦截时的数据装配也有外部负责, 为防止this.$message.info(err)时, err不是字符串会发生报错，并影响中断 逻辑, 用解构对进行处理。

### 0.3.57~0.3.60

修复一些缺陷;

### 0.3.61

RichTextBox改为使用vue-tinymce-text

### 0.3.62

修复切换tagview时, RichTextBox会被隐藏的问题：此缺陷原因未知，当router-view加上transition时，此缺陷修复。若未加，此RichTextBox中的 编辑框会在切换回来后，奇怪的被加上display:
none的样式.

### 0.3.65

TagView由path作为唯一键, 改为fullPath。对于相同的路由path，当路由参数不同时 也认为是两个不同的路由。这很适用, 当存在某个路由指向编辑页面，而需要开多个编辑页面时，可以满足这种情况。

### 0.3.66

新增导出TagViewUtil, 可以使用TagViewUtil.deleteView对当前路由tag进行关闭。也可以使用 $router.push(TagViewUtil.pop())
回到TagView中最后一个路由，从而替代$router.go(-1)

### 0.3.68

1. TreeView, TableView, TableTreeView添加meta 配置:

```json
"oper_logic": {
"chose_type": "default"
}
```

表示是否可取消选中的记录;

2. TreeTableTmpl添加SearchView面板支持

### 0.3.70

1. 优化组件实例配置编辑界面, 新增/编辑二合一;优化该部分的代码, 增加部分组件的复用性; 支持从列表页进入编辑界面编辑指定的MetaField;
2. TagView支持自定义关闭, 配合路由可实现关闭回到上一个视图;

### 0.3.71

1. FormBuilder支持栅格布局配置( TODO 配置保存待后端接口支持), FormView支持栅格布局展示;
2. 动态路由的TagView支持快捷编辑路由数据

### 0.3.72

1. FormView支持编码指定model值, 优先级:model的value > default_value = record。 但是key以meta中的columns配置为主;
2. FormView支持form_type为VIEW时的显示;
3. 修复RegionBox传入可转换为Array的字符串时未执行转换;

### 0.3.73

优化FormView VIEW下的展示; FormView增加一个width配置;

### 0.3.74 ~ 0.3.75

修复部分缺陷 FormView在VIEW模式下inline无效, 否则容易出现错位.

### 0.3.76

1. FormView的宽度默认为60%;s
2. NavMenu菜单支持展开/搜索(支持配置化);
3. 调整动态路由装配函数的参数顺序(必填项排前面);
4. 优化部分代码;

### 0.3.77
1. 优化表单查看状态下的显示效果
2. 优化FormView宽度效果(在弹窗中, 宽度转换为对弹窗生效, FormView宽度百分百; 在单独的页面中, 宽度对FormView生效)
3. 支持菜单无权限隐藏

## 0.3.78
1. ImgBox 预览图片遮罩优化
2. 支持ImgBox上传后隐藏上传按钮;考虑显示效果,FileBox是否需要支持待定

## 0.3.79
1. 修复一处缺陷,现在可以指定ROOT角色的角色编码了
2. user新增一个api： getRoles

## 0.3.80
1. 修复SqlBox异常

## 0.3.81
修复部分缺陷

## 0.3.82
1. 优化功能创建页面
2. DropDownBox和RadioBox change事件参数添加选中的option对象

## 0.3.83
1. CheckBox支持change事件时将命中的选项也emit

## 0.3.84
修复部分问题

## 0.3.85
TableView控制字段显隐时，优先使用label展示字段;

## 0.3.86
修复表单view模式下前端转义功能不完整导致未正确显示的问题

## 0.3.87
RichTextBox 支持 tinymce完整配置

## 0.3.88
RichTextBox 自动计算时自动配置好图片上传相关内容

## 0.3.90
1. FormView支持栅格布局
2. 优化FormBuilder布局

## 0.3.91
1. 支持元对象创建后的一键创建UI配置
2. 支持一些render snippet

## 0.3.96
1. 增加一些组件的导出

## 0.3.97
1. 优化菜单：调整平台维护和动态菜单、编程菜单的位置，并支持单个子节点上移。

## 0.4.00
1. 优化菜单、路由的装配，简化其配置。编程菜单需要单独提供，而不是从编程路由数据推导出，这样会显得更清晰可控。

## 0.4.70
1. 提供内置登录鉴权实现
2. 使系统更整体
