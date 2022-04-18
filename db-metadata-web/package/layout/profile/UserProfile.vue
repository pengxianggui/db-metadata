<template>
  <div class="user-profile">
    <div class="user-div">
      <el-dropdown v-if="user.id" class="">
        <div>
          <svg-icon :value="avatarSrc" class="avatar"></svg-icon>
        </div>

        <!--快捷菜单-->
        <!-- FIXME 很奇怪, 异步更新的profileMenus并不会触发dom更新。借助v-if实现也是无奈之举 -->
        <el-dropdown-menu slot="dropdown" v-if="profileMenusLoaded">
          <slot></slot>
          <el-dropdown-item v-for="m in profileMenus" @click.native="toPath(m.path)" :key="m.id">
            <svg-icon :value="m.icon"></svg-icon>
            <span>{{m.title}}</span>
          </el-dropdown-item>
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
      <div v-else>
        <el-link type="primary" @click="toLogin">登录</el-link>
      </div>
    </div>
  </div>
</template>

<script>
import {access, clearUser, hasAuth, hasRole} from "../../access";
import {isEmpty, randomInt, strSplitToArray} from "../../utils/common";
import {restUrl, routeUrl} from '../../constant/url'
import {appConfig} from '../../config'
import {resolve} from "../../utils/url";

// 判断是否有指定菜单的权限
const hasMenuAuth = function (menu) {
  const {
    need_permit = true,
    permit_by = 'auth',
    auths = [],
    roles = [],
    auth_match_mode = 'any',
    role_match_mode = 'any'
  } = menu
  if (!need_permit) {
    return true
  }

  let has;
  if (permit_by == 'role') {
    // 数据库中roles可能是以英文逗号分隔的字符串。而编程菜单采用的是数组，因此兼容处理下
    has = hasRole(strSplitToArray(roles, ','), role_match_mode)
  } else {
    // 数据库中auths可能是以英文逗号分隔的字符串。而编程菜单采用的是数组，因此兼容处理下
    has = hasAuth(strSplitToArray(auths, ','), auth_match_mode)
  }

  return has
}

// 处理菜单: 剔除隐藏的菜单和无权限的菜单
const dealMenus = function (menus) {
  let menuList = []

  menus.forEach((m) => {
    const {hidden = false} = m
    if (hidden === false && hasMenuAuth(m)) {
      menuList.push(m)
    }
  })

  menuList.sort((m1, m2) => m1.order - m2.order);
  return menuList
}

export default {
  name: "UserProfile",
  data() {
    return {
      user: access.user,
      profileMenus: [],
      profileMenusLoaded: false
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
      }).catch(() => {
      })
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
    },
    toLogin() {
      this.$router.push(routeUrl.R_LOGIN)
    },
    toPath(path) {
      this.$router.push(path)
    }
  },
  created() {
    this.$axios.safeGet(restUrl.PROFILE_MENU_DATA).then(resp => {
      const {data: profileMenus} = resp
      const menus = dealMenus(profileMenus)
      this.profileMenus.push(...menus)
      this.profileMenusLoaded = true
    })
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
      //   return 'avatar' + randomInt(1, 2)
      // }
      return this.user.avatar
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
  }
}
</style>
