<template>
  <div class="layout">
    <slot name="header">
      <div class="header">
        <span class="h2" style="font-family: unset">元数据管理系统</span>
        <span style="font-family: cursive;">—— Data Drive Everything</span>
      </div>
    </slot>
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
  </div>
</template>

<script>
import NavMenu from "../../core/navmenu";
import TagView from "../../core/tagview/src/TagView";
import Conf from '../../core/tagview/conf'

import MetaMenuData from '../../menu'
import {restUrl} from "../../constant/url";

export default {
  name: "AdminLayout",
  props: {
    // 动态菜单接口url
    dataUrl: {
      type: String,
      default: () => restUrl.MENU_DATA
    },
    collapse: {
      type: Boolean,
      default: () => false
    },
    // 编程路由
    routes: {
      type: Array,
      default: () => []
    }
  },
  components: {
    NavMenu,
    TagView
  },
  data() {
    return {
      metaMenus: MetaMenuData,
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
    this.getData(this.dataUrl)
  },
  computed: {
    menus() {
      let menus = [];
      const {dynamicMenus, routes} = this

      // 递归转换
      const routeToMenu = function (routes, menus) {
        routes.forEach(r => {
          const {hidden} = r
          if (hidden !== true) {
            let childrenMenus = []
            let {meta = {}, path, children: childrenRoutes = []} = r

            menus.push({
              ...meta,
              path,
              children: childrenMenus
            })
            routeToMenu(childrenRoutes, childrenMenus)
          }
        })
      }

      const programMenus = []
      routeToMenu(routes, programMenus)

      return menus.concat(dynamicMenus, programMenus).sort((m1, m2) => m1.order - m2.order)
    },
    // nativeCollapse: {
    //   get: function () {
    //     const {collapse: attrCollapse} = this
    //     return attrCollapse;
    //   },
    //   set: function (val) {
    //     return this.$emit("update:collapse", val);
    //   }
    // },
  }
}
</script>

<style lang="scss" scoped>
.layout {
  width: 100%;
  height: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;

  $headerHeight: 60px;

  .header {
    height: $headerHeight;
    line-height: $headerHeight;
    padding: 0 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
    color: #409EFF;
  }

  .body {
    margin: 0;
    padding: 0;
    flex: 1;
    display: flex;
    flex-direction: row;
    overflow: hidden;

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
        overflow: auto;
        position: relative;
      }
    }
  }
}
</style>
