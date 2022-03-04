<template>
  <div class="el-card">
    <h1>可用的编程菜单</h1>
    <p>这是你自己开发的页面。你想怎么开发就怎么开发。</p>

    <p>完全自由的页面! 完全自由的菜单！完全自由的路由！</p>
    <p>虽然完全自由，但是你却可以享受dbmeta带来的快捷，比如下面这个表格, 只需要一行代码: </p>
    <sql-box value='<single-grid-tmpl oc="meta_dict"></single-grid-tmpl>'></sql-box>

    <single-grid-tmpl oc="meta_dict"></single-grid-tmpl>

    <p>也就是说，你可以借助dbmeta生成的内容随意往你自己编写的页面中按需嵌套进去，当然，你也可以不这么做。完全取决于你自己。</p>

    <p>再比如下面这棵树, 代码也很简单</p>
    <sql-box :value="tree"></sql-box>
    <tree-view :data="treeData" style="width: 200px"></tree-view>

    <p>更多使用，可以参考教程(在建ing)</p>
  </div>
</template>

<script>
const treeData = [
  {
    id: '1',
    label: '节点1',
    children: [
      {
        id: '1-1',
        label: '节点1-1'
      },
      {
        id: '1-2',
        label: '节点1-2'
      },
      {
        id: '1-3',
        label: '节点1-3'
      }
    ]
  },
  {
    id: '2',
    label: '节点2',
    children: [
      {
        id: '1-1',
        label: '节点2-1'
      },
      {
        id: '2-2',
        label: '节点2-2',
        children: [
          {
            id: '2-2-1',
            label: '节点2-2-1'
          }
        ]
      }
    ]
  }
]
export default {
  name: "Route1_2",
  data() {
    return {
      treeData: treeData,
      tree: '<tree-view :data="treeData" style="width: 200px"></tree-view> \n'
        + "treeData: " + JSON.stringify(treeData)
    }
  }

}
</script>

<style scoped>

</style>
