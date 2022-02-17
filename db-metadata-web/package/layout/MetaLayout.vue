<template>
  <div class="layout">
    <slot name="header">
      <meta-header></meta-header>
    </slot>
    <div class="body">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import MetaHeader from "./MetaHeader";
export default {
  name: "MetaLayout",
  components: {
    MetaHeader
  }
}
</script>

<style lang="scss" scoped>
.layout {
  width: 100%;
  height: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;

  $headerHeight: 60px;

  .header {
    height: $headerHeight;
    line-height: $headerHeight;
    padding: 0 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
    color: #409EFF;
  }

  .body {
    margin: 0;
    padding: 0;
    flex: 1;
    display: flex;
    flex-direction: row;
    overflow: hidden auto;
  }
}
</style>
