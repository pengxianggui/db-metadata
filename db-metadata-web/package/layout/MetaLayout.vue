<template>
  <div class="layout">
    <meta-header>
      <meta-menu v-if="horizontal" v-bind="menuConf"></meta-menu>
      <slot></slot>
    </meta-header>
    <div class="body">
      <div class="menu" v-if="!horizontal">
        <meta-menu v-bind="menuConf"></meta-menu>
      </div>
      <div class="main">
        <meta-main></meta-main>
      </div>
    </div>
  </div>
</template>

<script>
import Theme from '../theme'
import MetaHeader from "./MetaHeader";
import MetaMenu from "./MetaMenu";
import MetaMain from "./MetaMain";

export default {
  name: "MetaLayout",
  components: {
    MetaHeader,
    MetaMenu,
    MetaMain
  },
  computed: {
    horizontal() {
      const {mode} = this.menuConf
      return mode == 'horizontal'
    },
    menuConf() {
      const {menu: menuConf} = Theme.getTheme()
      return menuConf
    }
  }
}
</script>

<style scoped lang="scss">

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
    }
  }
}
</style>
