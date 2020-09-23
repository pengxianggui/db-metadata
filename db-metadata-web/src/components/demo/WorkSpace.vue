<template>
  <div style="padding: 20px; overflow: auto; height: 100%;">
    <h1>组件开发调试页面</h1>
<!--    <tree-table-tmpl fc="news-tree-table"></tree-table-tmpl>-->

<!--    <sql-box v-model="value"></sql-box>-->
    <rich-text-box v-model="value"></rich-text-box>
  </div>
</template>

<script>

export default {
  name: "WorkSpace",
  data() {
    return {
      value: "select * from value",
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
