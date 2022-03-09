<template>
  <div class="layout" :style="layoutStyle">

    <template v-if="isRow">
      <meta-menu :theme-conf="menuConf"></meta-menu>
      <div class="body" :style="bodyStyle">
        <slot name="header">
          <meta-header>
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
        <div class="main">
          <meta-main></meta-main>
        </div>
      </div>
    </template>

    <template v-else>
      <slot name="header">
        <meta-header>
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
        <div class="main">
          <meta-main></meta-main>
        </div>
      </div>
    </template>

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
    theme() {
      return Theme.getTheme()
    },
    isRow() {
      return this.theme.layout === 'row'
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

  $headerHeight: 60px;

  .body {
    margin: 0;
    padding: 0;
    flex: 1;
    display: flex;
    overflow: hidden auto;

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
