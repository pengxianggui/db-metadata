<template>
  <div>
    <single-grid-tmpl :oc="oc">
      <template #inner-before-extend-btn="{scope}">
        <el-button size="mini" icon="el-icon-s-custom" @click="toBindRole(scope)"></el-button>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="visible" width="800px"
               :title="'为角色' + activeRow.name + '配置权限:'">
      <auth-set ref="AuthSet" :role-id="activeRow.id" v-if="visible"></auth-set>
      <template #footer>
        <el-button size="mini" @click="visible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="doBindRole">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {metaObjectCode} from "../../constant/variable";
import AuthSet from "./ext/AuthSet";
export default {
  name: "RoleList",
  components: {
    AuthSet
  },
  data() {
    return {
      oc: metaObjectCode.RoleList,
      visible: false,
      activeRow: {}
    }
  },
  methods: {
    toBindRole({row}) {
      this.activeRow = row
      this.visible = true
    },
    doBindRole() {
      this.$refs['AuthSet'].doBindRole().then(() => {
        this.visible = false;
      })
    }
  }
}
</script>

<style scoped>

</style>
