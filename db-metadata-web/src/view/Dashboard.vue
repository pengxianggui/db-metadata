<template>
  <div>
    <h1>首页</h1>
    {{user}}
  </div>
</template>

<script>
import {access} from "../../package/access";

export default {
  name: "Dashboard",
  data() {
    return {
      user: access.user
    }
  }
}
</script>

<style scoped>

</style>
