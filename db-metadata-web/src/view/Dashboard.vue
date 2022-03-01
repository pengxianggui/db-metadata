<template>
  <div>
    <el-card>
      欢迎您, {{user.username}}
    </el-card>

    <page-selector v-model="value"></page-selector>
  </div>
</template>

<script>
import {access} from "../../package/access";

export default {
  name: "Dashboard",
  data() {
    return {
      user: access.user,
      value: null
    }
  }
}
</script>

<style scoped>

</style>
