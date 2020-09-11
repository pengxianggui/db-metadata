<template>
  <div style="padding: 20px; overflow: auto; height: 100%;">
    <h1>组件开发调试页面</h1>
    <tree-table-tmpl fc="news-tree-table"></tree-table-tmpl>
  </div>
</template>

<script>

export default {
  name: "WorkSpace",
  data() {
    return {
      value: "",
      treeMeta: {}
    }
  },
  methods: {},
  created() {
  }
}
</script>

<style scoped>
.ddd {
  font-size: 40px;
}
</style>
