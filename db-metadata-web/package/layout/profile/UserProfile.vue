<template>
  <div class="user-profile">
    <div class="user-div">
      <el-dropdown>
        <img :src="user.avatar" class="avatar" v-if="false">
        <div class="avatar" v-else>
          <svg-icon :value="avatarSrc" class="svg-icon"></svg-icon>
        </div>
        <!--快捷菜单-->
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="init" v-any-auths="['api:app-init']">
            <svg-icon value="reset"></svg-icon>
            <span>系统重置</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout">
            <svg-icon value="logout"></svg-icon>
            <span>登出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {access, clearUser} from "../../access";
import {isEmpty, randomInt} from "../../utils/common";
import {restUrl, routeUrl} from '../../constant/url'

export default {
  name: "UserProfile",
  data() {
    return {
      user: access.user
    }
  },
  methods: {
    logout() {
      this.$axios.safePost(restUrl.LOGOUT_URL).then(() => {
        clearUser()
        this.$router.push(routeUrl.R_LOGIN)
      })
    },
    init: function () {
      this.$prompt('此操作将重新生成内置的元数据。注意:此操作不会删除你业务表中的数据，但是你创建的元对象、元字段, 及其实例配置将被清空, 且无法找回！ 若继续，请输入口令:',
          '确定要重置?',
          {
            type: 'warning',
            center: true,
            inputType: 'password',
            inputValidator: function (val) {
              return !isEmpty(val) ? true : '请输入口令(存在于系统配置中)';
            }
          }).then(data => {
        this.$axios.get('/db/init?token=' + data.value).then(({msg = '初始化成功'}) => {
          this.$message.success(msg);
          this.$router.go(0);
        }).catch(({msg = '发生错误'}) => {
          console.error(msg)
        })
      }).catch(() => {
      })
    }
  },
  computed: {
    avatarSrc() {
      const {user: {age, sex}} = this
      if (isEmpty(sex) || isEmpty(age)) {
        return 'monster' + randomInt(1, 2)
      }
    }
  }
}
</script>

<style scoped lang="scss">
$width: 60px;
$headerHeight: 60px;
.user-profile {
  $avatarSideLength: 40px;
  line-height: $headerHeight;

  .user-div {
    width: $width;
    display: flex;
    justify-content: space-around;

    .avatar {
      width: $avatarSideLength;
      height: $avatarSideLength;
      vertical-align: middle;
      cursor: pointer;
    }

    div.avatar {
      display: inline-block;
      font-size: 25px;
      border: 1px solid #cbcbd7;
      border-radius: $avatarSideLength / 2;
      line-height: $avatarSideLength;
      padding: 5px;
      box-sizing: border-box;

      .svg-icon {
        height: 100%;
        width: 100%;
      }
    }
  }
}
</style>
