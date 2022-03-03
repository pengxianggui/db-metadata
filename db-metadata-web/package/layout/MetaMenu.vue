<template>
    <nav-menu :collapse.sync="collapse" :show-collapse-button="true" v-bind="$attrs">
      <!-- 非平台维护菜单 -->
      <template v-for="menu in menus">
        <menu-item :item="menu" :base-path="menu.path"
                   :key="menu.title + menu.path"
                   v-if="!menu.hidden" :class="{'horizontal': horizontal}"
                   :bg-color="bgColor"></menu-item>
      </template>

      <!-- 平台维护菜单 -->
      <template v-for="menu in metaMenus">
        <menu-item :item="menu" :base-path="menu.path"
                   :key="menu.title + menu.path"
                   v-if="!menu.hidden" :class="{'horizontal': horizontal}"
                   :bg-color="bgColor"></menu-item>
      </template>
    </nav-menu>
</template>

<script>
import Menu from "../menu";
import {restUrl} from "../constant/url";
import Cache from "../constant/cacheKey";
import NavMenu from "./navmenu/src/NavMenu";
import MenuItem from "./navmenu/src/MenuItem";

export default {
  name: "MetaMenu",
  components: {NavMenu, MenuItem},
  data() {
    let collapse = localStorage.getItem(Cache.keyInLocal.MENU_COLLAPSE_KEY);
    return {
      collapse: collapse === 'true',
      metaMenus: Menu.metaMenus,
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
      localStorage.setItem(Cache.keyInLocal.MENU_COLLAPSE_KEY, newV)
    }
  },
  computed: {
    menus() {
      let menus = [];
      const {dynamicMenus} = this
      menus.push(...dynamicMenus, ...Menu.programMenus)
      return menus.sort((m1, m2) => m1.order - m2.order);
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
