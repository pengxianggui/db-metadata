<template>
  <single-grid-tmpl :fc="fc"></single-grid-tmpl>
</template>

<script>
export default {
  name: "ExceptionList",
  meta: {
    type: 'page',
    cn: '异常列表',
    buildIn: true
  },
  data() {
    return {
      fc: 'meta_exception',
    }
  }
}
</script>

<style scoped>

</style>
