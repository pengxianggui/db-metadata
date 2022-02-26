<template>
  <div>
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
</template>

<script>
import Conf from "../core/tagview/conf";

export default {
  name: "MetaMain",
  data() {
    return {
      cachedViews: [],
      showTagView: Conf.show,
    }
  }
}
</script>

<style scoped lang="scss">
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
</style>
