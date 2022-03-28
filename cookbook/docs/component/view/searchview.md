# 搜索组件
搜索组件内部本质是个内联表单。

## 组件名
`SearchView`

## 配置项
```json
{
  "name": "SearchView",
  "expand": true,
  "label-position": "top-center",
  "conf": {
    "label-width": "80px",
    "size": "mini"
  },
  "columns": [],
  "directly_trigger": []
}
```

### expand
为true, 则搜索面板刷新页面时会默认展开

### label-position
面板展开/收缩的按钮位置

### conf
内部`el-form`配置

### columns
内部控件配置

### directly_trigger
直接触发配置。比如有一个搜索项是type，是一个下拉控件，那么将tyle配置到directly_trigger
数组中，每次下拉切换值时，会自动触发检索，而不必再次点击"搜索"按钮。

:::warning
你最好只设置下拉这样的搜索控件 为 directly_trigger，如果设置一个普通的文本框为直接触发，那么
可能引起每次输入都会触发检索。
:::
