<template>
    <nav-menu :collapse.sync="collapse" :show-collapse-button="true">
      <!-- 非平台维护菜单 -->
      <template v-for="menu in menus">
        <menu-item :item="menu" :base-path="menu.path"
                   :key="menu.title + menu.path"
                   v-if="!menu.hidden"></menu-item>
      </template>

      <!-- 平台维护菜单 -->
      <template v-for="menu in metaMenus">
        <menu-item :item="menu" :base-path="menu.path"
                   :key="menu.title + menu.path"
                   v-if="!menu.hidden"></menu-item>
      </template>
    </nav-menu>
</template>

<script>
import Menu from "../menu";
import {restUrl} from "../constant/url";
import Cache from "../constant/cacheKey";

export default {
  name: "MetaMenu",
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
    }
  }
}
</script>

<style scoped lang="scss">
</style>
