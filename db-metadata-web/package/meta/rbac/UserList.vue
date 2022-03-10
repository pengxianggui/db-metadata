<template>
  <div class="page-container">
    <single-grid-tmpl :oc="oc" :table-meta="tableMeta">
      <template #inner-before-extend-btn="{scope}">
        <el-button size="mini" @click="toBindRole(scope)" class="el-icon-setting"></el-button>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="visible" width="800px"
               :title="'为用户' + activeRow.username + '设置角色:'">
      <role-set ref="RoleSet" :user-id="activeRow.id" v-if="visible"></role-set>
      <template #footer>
        <el-button size="mini" @click="visible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="doBind">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import RoleSet from "./ext/RoleSet";
import {appConfig} from "../../config";
import {metaObjectCode} from "../../constant/variable";

export default {
  name: "UserList",
  meta: {
    isTemplate: false,
    isPage: true,
    cn: '用户列表',
    buildIn: true // 内建：DbMeta提供
  },
  components: {
    RoleSet
  },
  data() {
    return {
      oc: metaObjectCode.UserList,
      addable: appConfig.addable,
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
      this.$refs['RoleSet'].doBind().then(() => {
        this.visible = false;
      })
    }
  },
  computed: {
    tableMeta() {
      const {addable} = appConfig
      return {
        "operation-bar": {
          "show": addable
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
