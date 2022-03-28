<template>
  <div>
    <el-card>
      欢迎您, {{user.username}}
    </el-card>
  </div>
</template>

<script>
import {access} from "../../access";

export default {
  name: "Dashboard",
  meta: {
    type: 'page',
    cn: '首页',
    icon: 'dashboard',
    buildIn: true // 内建：DbMeta提供
  },
  data() {
    return {
      user: access.user
    }
  }
}
</script>

<style scoped>

</style>
