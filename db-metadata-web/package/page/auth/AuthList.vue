<template>
  <tree-table-tmpl :fc="fc"></tree-table-tmpl>
</template>

<script>
export default {
  name: "AuthList",
  meta: {
    type: 'page',
    cn: '权限列表',
    buildIn: true // 内建：DbMeta提供
  },
  data() {
    return {
      fc: 'meta_auth'
    }
  }
}
</script>

<style scoped>

</style>
