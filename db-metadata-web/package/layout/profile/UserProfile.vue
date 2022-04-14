<template>
  <div class="user-profile">
    <div class="user-div">
      <el-dropdown>
        <img :src="avatarSrc" class="avatar" v-if="user.avatar">
        <div class="avatar" v-else>
          <svg-icon :value="avatarSrc" class="svg-icon"></svg-icon>
        </div>

        <!--快捷菜单-->
        <el-dropdown-menu slot="dropdown">
          <slot></slot>
          <el-dropdown-item @click.native="init" v-any-auths="['api:app-init']">
            <svg-icon value="reset"></svg-icon>
            <span>系统重置</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout" v-if="enableCertification">
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
import {appConfig} from '../../config'
import {resolve} from "../../utils/url";

export default {
  name: "UserProfile",
  data() {
    return {
      user: access.user
    }
  },
  methods: {
    logout() {
      this.$confirm('确定退出登录?', '提示', {
        type: 'warning'
      }).then(() => {
        this.$axios.safePost(restUrl.LOGOUT_URL).then(() => {
          clearUser()
          this.$router.push(routeUrl.R_LOGIN)
        })
      }).catch(() => {})
    },
    init: function () {
      this.$prompt('此操作将重置内置的元数据: <br>元对象、元字段、组件默认配置、组件实例配置、功能 <br>以及内置的非元数据: <br>路由、菜单、权限、权限模块、字典、接口资源、角色' +
          '<br><b>注意:此操作只会重置内置数据，不会删除你的业务数据。不过还是建议你先执行数据库备份操作。</b>',
          '确定要重置?',
          {
            type: 'warning',
            inputType: 'password',
            inputPlaceholder: '请输入口令..',
            dangerouslyUseHTMLString: true,
            inputValidator: function (val) {
              return !isEmpty(val) ? true : '请输入口令(参见系统配置)';
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
      const {user: {age, sex, avatar}} = this
      if (!isEmpty(avatar)) {
        const {defaults: {baseURL} = {}} = this.$axios
        return resolve(baseURL, avatar)
      }

      if (isEmpty(sex) || isEmpty(age)) {
        return 'monster' + randomInt(1, 2)
      }
    },
    enableCertification() {
      return appConfig.enableCertification
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
      border-radius: $avatarSideLength / 2;
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
