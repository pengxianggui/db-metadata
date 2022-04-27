<template>
  <div class="layout" :style="layoutStyle">

    <svg-icon value="dev_mode" class="dev-mode" v-if="devMode"></svg-icon>

    <template v-if="isRow">
      <meta-menu :theme-conf="menuConf"></meta-menu>
      <div class="body" :style="bodyStyle">
        <slot name="header">
          <meta-header :theme-conf="headerConf">
            <template #logo>
              <slot name="logo"></slot>
            </template>

            <template #name>
              <slot name="name"></slot>
            </template>

            <template #default>
              <slot></slot>
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
        </slot>

        <meta-main></meta-main>
      </div>
    </template>

    <template v-else>
      <slot name="header">
        <meta-header :theme-conf="headerConf">
          <template #logo>
            <slot name="logo"></slot>
          </template>

          <template #name>
            <slot name="name"></slot>
          </template>

          <template #default>
            <slot></slot>
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
      </slot>

      <div class="body" :style="bodyStyle">
        <meta-menu :theme-conf="this.theme.menu"></meta-menu>
        <meta-main></meta-main>
      </div>
    </template>

  </div>
</template>

<script>
import Theme from '../theme'
import MetaHeader from "./MetaHeader";
import MetaMenu from "./MetaMenu";
import MetaMain from "./MetaMain";
import {appConfig} from "../config";

export default {
  name: "MetaLayout",
  meta: {
    type: 'layout',
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
    theme() {
      return Theme.getTheme()
    },
    isRow() {
      return this.theme.layout === 'row'
    },
    headerConf() {
      return this.theme.header
    },
    menuConf() {
      return this.theme.menu
    },
    layoutStyle() {
      return this.isRow ? {
        "flex-direction": 'row',
      } : {
        "flex-direction": 'column',
      }
    },
    bodyStyle() {
      return this.isRow ? {
        "flex-direction": "column",
      } : {
        "flex-direction": "row",
      }
    },
    devMode() {
      return appConfig.devMode
    }
  }
}
</script>

<style scoped lang="scss">

.layout {

  .dev-mode {
    width: 120px;
    height: 120px;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 9;
    pointer-events: none;
  }

  width: 100%;
  height: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
  display: flex;

  $headerHeight: 60px;

  .body {
    margin: 0;
    padding: 0;
    flex: 1;
    display: flex;
    overflow: hidden auto;
  }
}
</style>
