<template>
  <div class="layout">
    <slot name="header">
      <div class="header">
        <div>
          <span class="h2" style="font-family: unset">元数据管理系统</span>
          <span style="font-family: cursive;">—— Data Drive Everything</span>
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

    $menuWidth: 220px;

    .menu {
      width: $menuWidth;
      min-width: $menuWidth;
      height: 100%;
      overflow: auto;
    }

    .main {
      flex: 1;
      overflow: auto;
      height: 100%;
      $tagViewHeight: 40px;
      position: relative;

      .fixed-header {
        position: absolute;
        right: 0;
        z-index: 9;
        width: 100%;
      }

      .stage {
        height: calc(100% - #{$tagViewHeight});
        margin-top: $tagViewHeight;
        overflow: auto;
        position: relative;
      }
    }
  }
}
</style>