<template>
  <div class="md_page-container">
    <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
  </div>
</template>

<script>
export default {
  name: "MenuManager",
  data() {
    return {
      fc: 'meta_menu'
    }
  }
}
</script>

<style scoped>

</style>
