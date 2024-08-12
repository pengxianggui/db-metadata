<template>
  <el-card class="container">
    <row-grid :span="[12, 12]">
      <template #0>
        <div class="avatar-box">
          <svg-icon :value="avatarSrc" class="avatar"></svg-icon>
        </div>
      </template>
      <template #1>
        <p class="text-item">
          <label>用户名: </label>
          <span>{{ user.username }}</span>
        </p>
        <p class="text-item">
          <label>昵称: </label>
          <span>{{ user.nickname | defaultStr('待完善') }}</span>
        </p>
        <p class="text-item">
          <label>真实姓名: </label>
          <span>{{ user.realname | defaultStr('待完善') }}</span>
        </p>
        <p class="text-item">
          <label>身份证号: </label>
          <span>{{ user.id_card | defaultStr('待完善') }}</span>
        </p>
        <p class="text-item">
          <label>手机号: </label>
          <span>{{ user.phone | defaultStr('待完善') }}</span>
        </p>
        <p class="text-item">
          <label>邮箱: </label>
          <span>{{ user.email | defaultStr('待完善') }}</span>
        </p>
        <p class="text-item">
          <label>角色: </label>
          <span>
            <el-tag v-for="r in user.roles" style="margin: 0 5px;">{{ r }}</el-tag>
          </span>
        </p>
        <p>
          <el-button type="primary" @click="editDialogVisible = true">编辑个人信息</el-button>
          <el-button type="danger" @click="passResetDialogVisible = true">密码重置</el-button>
        </p>
      </template>
    </row-grid>

    <el-dialog :visible.sync="editDialogVisible" width="800px">
      <form-view ic="meta_user.formView" :primary-kv="user.id" form-type="update"
                 @submit="submit" @cancel="editDialogVisible = false" style="width: 100%"></form-view>
    </el-dialog>
    <el-dialog :visible.sync="passResetDialogVisible" width="500px">
      <form-view :meta="resetPassFormMeta" @submit="setPass" style="width: 100%"
                 @cancel="passResetDialogVisible = false"></form-view>
    </el-dialog>
  </el-card>
</template>

<script>
import access from "../../access";
import {restUrl} from "../../constant/url";

export default {
  name: "Profile",
  data() {
    return {
      user: access.getUser(),
      editDialogVisible: false,
      passResetDialogVisible: false,
      // TODO 2.2 组件实例配置加载应当支持这样:
      //  1.加载指定容器组件的实例配置;  入参: ic, type=META_OBJECT
      //  2.加载指定容器组件的默认配置;  入参: componentCode
      //  3.加载指定域组件的实例配置;    入参: ic, type=META_FIELD
      //  4.加载指定域组件的默认配置;   入参: componentCode
      resetPassFormMeta: {
        conf: {
          'label-width': '130px'
        },
        columns: [{
          name: 'password',
          label: '请输入密码',
          component_name: 'PassBox',
          conf: {
            rules: [{required: true, message: '请输入密码', trigger: 'blur'}]
          }
        }, {
          name: 'password_confirm',
          label: '请再次输入密码',
          component_name: 'PassBox',
          conf: {
            rules: [{required: true, message: '请输入密码', trigger: 'blur'}]
          }
        }]
      }
    }
  },
  methods: {
    submit(data) {
      this.$axios.safePost(restUrl.USER_UPDATE, data).then(({message = '更新成功'}) => {
        this.$message.success(message)
        this.editDialogVisible = false
      })
    },
    setPass({password, password_confirm}) {
      if (password !== password_confirm) {
        this.$message.warning('两次密码输入不一致')
        return
      }
      this.$axios.safePost(restUrl.SET_PASS, {password: password}).then(({message = '设置成功'}) => {
        this.$message.success(message)
        this.passResetDialogVisible = false
      })
    }
  },
  created() {
  },
  computed: {
    avatarSrc() {
      // const {user: {age, sex, avatar}} = this
      // if (!isEmpty(avatar)) {
      //   const {defaults: {baseURL} = {}} = this.$axios
      //   return resolve(baseURL, avatar)
      // }
      //
      // if (isEmpty(sex) || isEmpty(age)) {
      //   return 'monster' + randomInt(1, 2)
      // }
      return this.user.avatar
    },
  }
}
</script>

<style scoped lang="scss">
.container {
  overflow: auto;
  margin: 30px 80px !important;

  .text-item {
    color: #3e3c3e;

    label {
      display: inline-block;
      width: 100px;
      color: #999898;
      font-size: 15px;
      margin-right: 20px;
    }

    span {
      font-family: cursive
    }
  }

  .avatar-box {
    text-align: center;

    .avatar {
      $avatarBorderLength: 200px;
      width: $avatarBorderLength;
      height: $avatarBorderLength;
      font-size: $avatarBorderLength;
      border-radius: calc($avatarBorderLength / 2);
    }
  }
}
</style>
