<template>
  <div class="body">
    <div class="menu">
      <nav-menu :collapse.sync="collapse" :show-collapse-button="true">
        <!-- 非平台维护菜单 -->
        <template v-for="menu in menus">
          <menu-item :item="menu" :base-path="menu.path" :key="menu.title + menu.path" v-if="!menu.hidden"></menu-item>
        </template>

        <!-- 平台维护菜单 -->
        <template v-for="menu in metaMenus" v-if="$isRoot()">
          <menu-item :item="menu" :base-path="menu.path" :key="menu.title + menu.path" v-if="!menu.hidden"></menu-item>
        </template>
      </nav-menu>
    </div>

    <div class="main">
      <div class="fixed-header" v-if="showTagView">
        <tag-view @cacheViewChange="(value) => cachedViews = value"></tag-view>
      </div>
      <div class="stage">
        <!--  keep-alive必须为router-view的直接父级, 否则keep-alive机制不生效 -->
        <transition name="fade-transform" mode="out-in">
          <keep-alive :include="cachedViews">
            <router-view :key="$route.fullPath"></router-view>
          </keep-alive>
        </transition>
      </div>
    </div>

  </div>
</template>

<script>
import NavMenu from "../../core/navmenu";
import TagView from "../../core/tagview/src/TagView";
import Menu from "../../menu";
import Conf from "../../core/tagview/conf";
import {restUrl} from "../../constant/url";

export default {
  name: "MetaMain",
  components: {
    NavMenu,
    TagView
  },
  data() {
    return {
      collapse: false,
      metaMenus: Menu.metaMenus,
      cachedViews: [],
      showTagView: Conf.show,
      dynamicMenus: []
    }
  },
  methods: {
    getData(url) {
      this.$axios.get(url).then(resp => {
        const {data: dynamicMenu} = resp
        this.dynamicMenus.push(...dynamicMenu)
      })
    },
  },
  created() {
    this.getData(restUrl.MENU_DATA)
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

.body {
  margin: 0;
  padding: 0;
  flex: 1;
  display: flex;
  flex-direction: row;
  overflow: hidden auto;

  .menu {
    height: 100%;
    overflow: auto;
    position: relative;
  }

  .main {
    flex: 1;
    overflow: auto;
    height: 100%;
    position: relative;
    display: flex;
    flex-direction: column;

    .fixed-header {
      right: 0;
      z-index: 9;
      width: 100%;
    }

    .stage {
      flex: 1;
      overflow: hidden auto;
      position: relative;
    }
  }
}
</style>
