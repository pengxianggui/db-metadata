<template>
  <div class="layout">
    <meta-header>
      <template #logo>
        <slot name="logo"></slot>
      </template>
      <template #name>
        <slot name="name">
          <!-- 水平则隐藏系统名 -->
          <span v-if="isHorizontal"></span>
        </slot>
      </template>

      <template #default>
        <slot></slot>
        <meta-menu v-if="isHorizontal" :menu-conf="menuConf"></meta-menu>
      </template>

      <template #side>
        <slot name="side"></slot>
      </template>

      <template #profile>
        <slot name="profile"></slot>
      </template>

      <template #profile-menu>
        <slot name="profile-menu"></slot>
      </template>
    </meta-header>

    <div class="body">
      <div class="menu" v-if="!isHorizontal">
        <meta-menu :menu-conf="menuConf"></meta-menu>
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
  meta: {
    isLayout: true,
    cn: 'Admin布局',
    icon: 'admin-layout',
    buildIn: true // 内建：DbMeta提供
  },
  components: {
    MetaHeader,
    MetaMenu,
    MetaMain
  },
  computed: {
    menuConf() {
      const {menu: menuConf} = Theme.getTheme()
      return menuConf
    },
    isHorizontal() {
      const {mode} = this.menuConf
      return mode == 'horizontal'
    },
    headerMenuSlotUsed() {
      return this.$slots['default']
          && Array.isArray(this.$slots['default'])
          && this.$slots['default'].length > 0
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
