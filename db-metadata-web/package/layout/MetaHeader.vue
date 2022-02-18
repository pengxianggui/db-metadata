<template>
  <div class="header">
    <span class="h2" style="font-family: unset">{{ appConfig.name }}</span>
  </div>
</template>

<script>
import {appConfig} from "../config";

export default {
  name: "MetaHeader",
  data() {
    return {
      appConfig: appConfig
    }
  }
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
