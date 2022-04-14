<template>
  <el-card class="container">

    <row-grid :span="[12, 12]">
      <template #0>
        <div class="avatar">
          <img :src="avatarSrc" class="avatar" v-if="user.avatar">
          <div class="avatar" v-else>
            <svg-icon :value="avatarSrc" class="svg-icon"></svg-icon>
          </div>
        </div>
      </template>
      <template #1>
        <p class="text-item">
          <label>用户名: </label>
          <span>{{ user.username }}</span>
        </p>
        <p class="text-item">
          <label>手机号: </label>
          <span>{{ user.phone | defaultStr('待完善') }}</span>
        </p>
        <p class="text-item">
          <label>角色: </label>
          <span><el-tag v-for="r in user.roles">{{ r }}</el-tag></span>
        </p>
        <p>
          <el-button type="primary" @click="visible = true" v-if="!$isRoot()">编辑个人信息</el-button>
          <el-button type="danger" @click="resetPass" v-if="!$isRoot()">密码重置</el-button>
        </p>
      </template>
    </row-grid>

    <el-dialog :visible.sync="visible" width="800px">
      <form-view ic="meta_user.formView" :primary-kv="user.id" form-type="update"
                 @submit="submit" @cancel="visible = false" style="width: 100%"></form-view>
    </el-dialog>
  </el-card>
</template>

<script>
import access from "../../access";
import {isEmpty, randomInt} from "../../utils/common";
import {resolve} from "../../utils/url";
import {restUrl} from "../../constant/url";

export default {
  name: "Profile",
  data() {
    return {
      user: access.getUser(),
      visible: false
    }
  },
  methods: {
    submit(data) {
      this.$axios.safePost(restUrl.USER_UPDATE, data).then(({message = '更新成功'}) => {
        this.$message.success(message)
        this.visible = false
      })
    },
    resetPass() {
      // TODO 2.2
    }
  },
  created() {
  },
  computed: {
    avatarSrc() {
      const {user: {age, sex, avatar}} = this
      if (!isEmpty(avatar)) {
        const {defaults: {baseURL} = {}} = this.$axios
        return resolve(baseURL, avatar)
      }

      if (isEmpty(sex) || isEmpty(age)) {
        return 'monster' + randomInt(1, 2)
      }
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
      color: #999898;
      font-size: 15px;
      margin-right: 20px;
    }
  }

  .avatar {
    width: 100px;
    height: 100px;
    font-size: 100px;
  }
}
</style>
