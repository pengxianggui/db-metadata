<template>
  <div class="menu" :style='{"background-color": bgColor}'>
    <nav-menu :collapse.sync="collapse"
              :show-collapse-button="true"
              v-bind="menuConf">
      <template v-for="menu in menus">
        <menu-item :item="menu" :base-path="menu.path"
                   :key="menu.loopKey"></menu-item>
      </template>
    </nav-menu>
  </div>
</template>

<script>
import Menu from "../menu";
import {restUrl} from "../constant/url";
import Cache from "../constant/cacheKey";
import NavMenu from "./navmenu/src/NavMenu";
import MenuItem from "./navmenu/src/MenuItem";
import {isEmpty, strSplitToArray} from "../utils/common";
import {hasAuth, hasRole} from "../access";

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
// 处理菜单: 生成随机key,剔出隐藏的菜单和无权限的菜单
const dealMenus = function (menus) {
  let menuList = []

  menus.forEach((m) => {
    const {hidden = false} = m
    if (hidden === false && hasMenuAuth(m)) {
      // 非常重要! 由于menus中有编程菜单、动态菜单和平台维护的菜单, 动态菜单有id值，其他没有。且菜单是递归的，
      // 必须保证key的唯一，否则会出现某些菜单出不来的情况。
      m.loopKey = Math.floor(Math.random() * 10000000000000000)
      menuList.push(m)

      if (m.hasOwnProperty('children') && !isEmpty(m.children)) {
        m.children = dealMenus(m.children)
      }
    }
  })

  menuList.sort((m1, m2) => m1.order - m2.order);
  return menuList
}

export default {
  name: "MetaMenu",
  components: {NavMenu, MenuItem},
  props: {
    menuConf: Object
  },
  data() {
    let collapse = localStorage.getItem(Cache.keyInLocal.MENU_COLLAPSE_KEY.value);
    return {
      collapse: collapse === 'true',
      dynamicMenus: []
    }
  },
  methods: {
    getData(url) {
      this.$axios.get(url).then(resp => {
        const {data: dynamicMenu} = resp
        this.dynamicMenus.push(...dynamicMenu)
      })
    }
  },
  created() {
    this.getData(restUrl.MENU_DATA)
  },
  watch: {
    collapse(newV) {
      localStorage.setItem(Cache.keyInLocal.MENU_COLLAPSE_KEY.value, newV)
    }
  },
  computed: {
    menus() {
      let menus = []
      const {dynamicMenus} = this
      menus.push(...dynamicMenus, ...Menu.programMenus, ...Menu.metaMenus)
      return dealMenus(menus)
    },
    bgColor() {
      return this.menuConf.backgroundColor
    }
  }
}
</script>

<style scoped lang="scss">
.menu {
  height: 100%;
  overflow: hidden auto;
  position: relative;
}
</style>
