<template>
  <meta-layout>
    <template #header>
      <div class="header">
        <div>
          <span class="h2" style="font-family: unset">元数据管理系统</span>
          <span style="font-family: cursive;">—— Data Drive Everything</span>
        </div>

        <!--        <list class="nav">-->
        <!--          <list-item>-->
        <!--            <router-link to="/index">首页</router-link>-->
        <!--          </list-item>-->
        <!--          <list-item>-->
        <!--            <router-link to="/about">关于我们</router-link>-->
        <!--          </list-item>-->
        <!--          <list-item>-->
        <!--            <router-link to="/dashboard">后台</router-link>-->
        <!--          </list-item>-->
        <!--          <list-item>-->
        <!--            <router-link to="/workspace">workspace</router-link>-->
        <!--          </list-item>-->
        <!--        </list>-->

        <span style="flex: 1"></span>


        &nbsp;&nbsp
        <el-tooltip class="item" effect="dark" content="初始化系统" placement="bottom">
          <el-button @click="init" type="danger" icon="el-icon-magic-stick" size="mini"></el-button>
        </el-tooltip>
      </div>
    </template>
  </meta-layout>
</template>

<script>
import {routes} from "@/router";

export default {
  name: "MyLayout",
  data() {
    return {
      routes: routes
    }
  },
  methods: {
    init: function () {
      this.$prompt('初始化不会删除你业务表中的数据, 但是你创建的元对象、元字段, 及其实例配置将被清空, 且无法找回！ 若继续，请输入口令:',
          '确定要初始化?',
          {}).then(data => {
        this.$axios.get('/db/init?token=' + data.value).then(({msg = '初始化成功'}) => {
          this.$message.success(msg);
          this.$router.go(0);
        }).catch(({msg = '发生错误'}) => {
          console.error(msg)
        })
      }).catch(() => {

      })
    }
  }
}
</script>

<style lang="scss" scoped>
.nav {
  width: 500px;

  li {
    border: none;

    a {
      color: #3f9eff;
      text-decoration: none;
    }
  }
}
</style>
