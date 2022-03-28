# 域组件

DbMeta一共内置的域组件有18个：

1. `TextBox`    单行文本域
1. `TextAreaBox`    多行文本域
1. `NumBox` 数值框
1. `PassBox`    密码框
1. `BoolBox`    布尔框
1. `RadioBox`   单选框
1. `CheckBox`   多选框
1. `DropDownBox`    下拉框
1. `TimeBox`    时间框
1. `DateBox`    日期框
1. `DateTimeBox`    日期时间框
1. `FileBox`    文件上传框
1. `ImgBox` 图片上传框
1. `JsonBox`    Json框
1. `MiniFormBox`    迷你表单。Json数据的表单(依据值推导控件)
1. `FindBox`:   查找框。配合弹窗面板，关联数据
1. `RegionBox`: 省市框
1. `RichTextBox`: 富文本框

:::tip
自定义组件扩展机制，请期待。
:::


域组件 + 元字段 可以成为一套实例配置。公式如下:

`字段实例配置(域实例配置) = 元字段 + 域组件`

字段实例配置也有唯一编码: `instanceCode`(`ic`)

> 但是字段实例配置脱离了 容器实例配置 几乎没有什么意义。因此DbMeta中，一般将实例配置，认为就是容器实例配置。元数据装配
> 也是从容器实例配置切入的。
