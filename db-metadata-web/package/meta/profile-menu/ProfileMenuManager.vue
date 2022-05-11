<template>
  <div class="md_page-container">
    <single-grid-tmpl :fc="fc"></single-grid-tmpl>
  </div>
</template>

<script>
export default {
  name: "ProfileMenuManager",
  data() {
    return {
      fc: 'meta_profile_menu'
    }
  }
}
</script>

<style scoped>

</style>
