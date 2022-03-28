<template>
  <div class="el-card">
    <h1>可用的编程菜单</h1>
    <p>这是你自己开发的页面。你想怎么开发就怎么开发。</p>

    <p>完全自由的页面! 完全自由的菜单！完全自由的路由！</p>
    <p>虽然完全自由，但是你却可以享受dbmeta带来的快捷，比如下面这个表格, 只需要一行代码: </p>
    <code-box value='<tree-single-grid-tmpl fc="meta_dict"></single-grid-tmpl>'></code-box>

    <tree-single-grid-tmpl fc="meta_dict"></tree-single-grid-tmpl>

    <p>也就是说，你可以借助dbmeta生成的内容随意往你自己编写的页面中按需嵌套进去，当然，你也可以不这么做。完全取决于你自己。</p>

    <p>更多使用，可以参考教程(在建ing)</p>
  </div>
</template>

<script>
export default {
  name: "Route1_2",
  data() {
    return {
    }
  }

}
</script>

<style scoped>

</style>
