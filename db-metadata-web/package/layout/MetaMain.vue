<template>
  <div id="md_main">
    <div id="fixed-header">
      <tag-view></tag-view>
    </div>
    <div id="stage">
      <!--  keep-alive必须为router-view的直接父级, 否则keep-alive机制不生效 -->
      <keep-alive v-if="cacheCurrentRoute">
        <router-view :key="$route.fullPath"></router-view>
      </keep-alive>
      <router-view :key="$route.fullPath" v-else></router-view>
    </div>
  </div>
</template>

<script>
import TagView from "./tagview/src/TagView";

export default {
  name: "MetaMain",
  components: {
    TagView
  },
  data() {
    return {
    }
  },
  computed: {
    cacheCurrentRoute() {
      return this.$route.meta.noCache === true ? false : true;
    }
  }
}
</script>

<style scoped lang="scss">

#md_main {
  flex: 1;
  overflow: auto;
  height: 100%;
  position: relative;
  display: flex;
  flex-direction: column;

  #fixed-header {
    right: 0;
    width: 100%;
  }

  #stage {
    flex: 1;
    overflow: auto;
    position: relative;
    background-color: #f7f7f7;
  }
}
</style>
