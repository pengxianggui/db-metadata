<template>
  <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
</template>

<script>
export default {
  name: "DictList",
  meta: {
    type: 'page',
    cn: '字典列表',
    buildIn: true
  },
  data() {
    return {
      fc: "meta_dict"
    }
  }
}
</script>

<style scoped>
</style>
