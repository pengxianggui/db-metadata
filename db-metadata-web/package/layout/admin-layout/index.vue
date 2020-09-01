<template>
  <div class="layout">
    <slot name="header">
      <div class="header">
        <div>
          <span class="h2" style="font-family: unset">元数据管理系统</span>
          <span style="font-family: cursive;">—— Data Fuck Everything</span>
        </div>
      </div>
    </slot>
    <div class="body">
      <slot name="menu">
        <div class="menu">
          <nav-menu></nav-menu>
        </div>
      </slot>
      <div class="main">
        <div class="fixed-header">
          <tag-view @cacheViewChange="(value) => cachedViews = value" v-if="showTagView"></tag-view>
        </div>
        <keep-alive :include="cachedViews">
          <router-view :key="$route.path" style="margin-top: 40px;"></router-view>
        </keep-alive>
      </div>
    </div>
  </div>
</template>

<script>
import NavMenu from "../../core/navmenu";
import TagView from "../../core/tagview/src/TagView";
import Conf from '../../core/tagview/conf'

export default {
  name: "AdminLayout",
  components: {
    NavMenu,
    TagView
  },
  data() {
    return {
      cachedViews: [],
      showTagView: Conf.show
    }
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

  .header {
    height: 60px;
    line-height: 60px;
    padding: 0 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
    color: #409EFF;
  }

  .body {
    width: 100%;
    margin: 0;
    padding: 0;
    flex: 1;
    display: flex;
    flex-direction: row;
    overflow: auto;

    $menuWidth: 220px;
    .menu {
      width: $menuWidth;
      height: 100%;
      overflow: auto;
    }

    .main {
      flex: 1;
      overflow: hidden auto;

      .fixed-header {
        position: absolute;
        right: 0;
        z-index: 9;
        width: calc(100% - #{$menuWidth});
        transition: width 0.28s;
      }
    }
  }
}
</style>