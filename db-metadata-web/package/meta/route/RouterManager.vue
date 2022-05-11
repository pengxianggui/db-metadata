<template>
  <div class="md_page-container">
    <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
  </div>
</template>

<script>

export default {
  name: "RouterManager",
  data() {
    return {
      fc: 'meta_router'
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
