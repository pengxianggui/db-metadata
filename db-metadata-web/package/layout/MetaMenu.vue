<template>
    <nav-menu :collapse.sync="collapse" :show-collapse-button="true" v-bind="$attrs">
      <!-- 非平台维护菜单 -->
      <template v-for="menu in menus">
        <menu-item :item="menu" :base-path="menu.path"
                   :key="menu.loopKey"
                   :class="{'horizontal': horizontal}"
                   :bg-color="bgColor"
                   v-menu-auth="menu"></menu-item>
      </template>
    </nav-menu>
</template>

<script>
import Menu from "../menu";
import {restUrl} from "../constant/url";
import Cache from "../constant/cacheKey";
import NavMenu from "./navmenu/src/NavMenu";
import MenuItem from "./navmenu/src/MenuItem";
import {isEmpty} from "../utils/common";

export default {
  name: "MetaMenu",
  components: {NavMenu, MenuItem},
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
      // 处理菜单: 生成随机key,剔出隐藏的菜单
      const dealMenus = function (menus) {
        let menuList = []
        menus.forEach((m) => {
          const {hidden = false} = m
          if (hidden === false) {
            // 非常重要! 由于menus中有编程菜单、动态菜单和平台维护的菜单, 动态菜单有id值，其他没有。且菜单是递归的，
            // 必须保证key的唯一，否则会出现某些菜单出不来的情况。
            m.loopKey = Math.floor(Math.random() * 10000000000000000)
            menuList.push(m)

            if (m.hasOwnProperty('children') && !isEmpty(m.children)) {
              m.children = dealMenus(m.children)
            }
          }
        })
        return menuList
      }

      let menus = [];
      const {dynamicMenus} = this
      menus.push(...dynamicMenus, ...Menu.programMenus, ...Menu.metaMenus)
      menus.sort((m1, m2) => m1.order - m2.order);
      let finalMenus = dealMenus(menus)
      return finalMenus
    },
    horizontal() {
      const {$attrs: {mode = 'vertical'}} = this
      return mode == 'horizontal'
    },
    bgColor() {
      const {$attrs: {backgroundColor}} = this
      return backgroundColor
    }
  }
}
</script>

<style scoped lang="scss">
  .horizontal {
    display: inline-block; // 一级菜单设为此display目的是，防止当为水平菜单时的被迫换行
  }
</style>
