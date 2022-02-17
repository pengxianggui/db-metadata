<template>
  <div class="header">
    <span class="h2" style="font-family: unset">元数据管理系统</span>
    <span style="font-family: cursive;">—— Data Drive Everything</span>
  </div>
</template>

<script>
export default {
  name: "MetaHeader"
}
</script>
<style scoped lang="scss">
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
</style>
