<template>
  <div>
    <el-card>
      欢迎您, {{user.username}}
    </el-card>
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
