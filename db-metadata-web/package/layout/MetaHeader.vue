<template>
  <div class="header">
    <span class="h2" style="font-family: unset">{{ appConfig.name }}</span>

    <div class="dock">
      <user-profile></user-profile>
    </div>
  </div>
</template>

<script>
import {appConfig} from "../config";
import UserProfile from "./profile/UserProfile";

export default {
  name: "MetaHeader",
  components: {UserProfile},
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

  div.dock {
    flex: 1;
    display: flex;
    justify-content: flex-end;

    & > * {
      margin: 0 10px;
    }
    span.empty {
      flex: 1
    }
  }
}
</style>
