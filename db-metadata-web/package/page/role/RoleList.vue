<template>
  <div class="md_page-container">
    <single-grid-tmpl :fc="fc">
      <template #inner-before-extend-btn="{scope}">
        <el-button size="mini" icon="el-icon-setting" @click="toBindRole(scope)"></el-button>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="visible" width="1000px"
               :title="'为角色【' + activeRow.name + '】配置权限:'">
      <auth-set ref="AuthSet" :role-id="activeRow.id" v-if="visible"></auth-set>
      <template #footer>
        <el-button size="mini" @click="visible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="doBind">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import AuthSet from "./ext/AuthSet";

export default {
  name: "RoleList",
  meta: {
    type: 'page',
    cn: '角色列表',
    buildIn: true // 内建：DbMeta提供
  },
  components: {
    AuthSet
  },
  data() {
    return {
      fc: 'meta_role',
      visible: false,
      activeRow: {}
    }
  },
  methods: {
    toBindRole({row}) {
      this.activeRow = row
      this.visible = true
    },
    doBind() {
      this.$refs['AuthSet'].doBind().then(() => {
        this.visible = false;
      })
    }
  }
}
</script>

<style scoped lang="scss">
.footer {
  display: flex;
  flex-direction: row;
}
</style>
