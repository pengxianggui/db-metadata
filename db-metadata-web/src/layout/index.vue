<template>
  <admin-layout>
    <template #header>
      <div class="header">
        <div>
          <span class="h2" style="font-family: unset">元数据管理系统</span>
          <span style="font-family: cursive;">—— Data Drive Everything</span>
        </div>
        <span style="flex: 1"></span>
        <router-link to="/workspace" style="cursor: pointer;">
          workspace
        </router-link>
        &nbsp;&nbsp;
        <el-tooltip class="item" effect="dark" content="初始化数据库" placement="bottom">
          <el-button @click="initDb" type="primary" icon="el-icon-magic-stick" size="mini"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="清空数据库" placement="bottom">
          <el-button @click="cleanDb" type="danger" icon="el-icon-delete-solid" size="mini"></el-button>
        </el-tooltip>
      </div>
    </template>
    <template #menu>
      <div class="menu">
        <nav-menu :collapse.sync="collapse" :show-collapse-button="true" :unique-opened="true"
                  style="height: 100%"></nav-menu>
      </div>
    </template>
  </admin-layout>
</template>

<script>
export default {
  name: "Layout",
  data() {
    return {
      collapse: false
    }
  },
  methods: {
    initDb: function () {
      this.$prompt('提示:请输入口令', '确定要初始化系统', {}).then(data => {
        this.$axios.get('/db/init/' + data.value).then(({msg = '初始化成功'}) => {
          this.$message.success(msg);
          this.$router.go(0);
        }).catch(({msg = '发生错误'}) => {
          this.$message.error(msg);
        })
      })
    },
    cleanDb: function () {
      this.$prompt('提示:请输入口令', '确定要清空数据库', {}).then(data => {
        this.$axios.get('/db/truncate/' + data.value).then(({msg = '操作成功'}) => {
          this.$message.success(msg);
        }).catch(({msg = '操作失败'}) => {
          this.$message.error(msg);
        })
      }).catch(() => {
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  /*background-color: #3b3e3f;*/
}

.menu {
  height: 100%;
}
</style>