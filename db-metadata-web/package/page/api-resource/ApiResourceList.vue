<template>
  <single-grid-tmpl :fc="fc"></single-grid-tmpl>
</template>

<script>

export default {
  name: "ApiResourceList",
  meta: {
    type: 'page',
    cn: '接口资源列表',
    buildIn: true
  },
  data() {
    return {
      fc: 'meta_api_resource'
    }
  }
}
</script>

<style scoped>

</style>
