<template>
  <div class="page-container">
    <single-grid-tmpl :fc="fc">
      <template #inner-before-extend-btn="{scope}">
        <el-button size="mini" @click="toBindRole(scope)" class="el-icon-setting"></el-button>
        <el-button size="mini" v-bind="scope.conf" icon="el-icon-lock" @click="resetPass(scope)"></el-button>
      </template>
      <template #add-btn="{conf, add}">
        <el-button v-bind="conf.conf" @click="add" v-if="addable">新增</el-button>
        <span v-else></span>
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
import {restUrl} from "../../constant/url";

export default {
  name: "UserList",
  meta: {
    type: 'page',
    cn: '用户列表',
    buildIn: true // 内建：DbMeta提供
  },
  components: {
    RoleSet
  },
  data() {
    return {
      fc: 'meta_user',
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
    },
    resetPass({row}) {
      const {id: userId, username} = row
      this.$confirm(`确定为用户${username}重置密码?`, '提示', {
        type: 'warning'
      }).then(() => {
        this.$axios.safePost(restUrl.RESET_PASS, {userId: userId}).then(({message = '重置成功'}) => {
          this.$message.success(message)
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
