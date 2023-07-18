<template>
  <div class="header" :style="headerStyle">
    <slot name="logo">
      <svg-icon :value="logo" class="logo" v-if="logo"></svg-icon>
    </slot>

    <slot name="name">
      <h2 class="name">{{ name }}</h2>
    </slot>

    <div class="nav">
      <slot></slot>
    </div>

    <div class="dock">
      <div v-if="greeting && user.username">欢迎您: {{ user.username }}</div>

      <theme-set v-if="themeSetting"></theme-set>

      <slot name="side"></slot>

      <slot name="profile">
        <user-profile>
          <template>
            <slot name="profile-menu"></slot>
          </template>
        </user-profile>
      </slot>

    </div>
  </div>
</template>

<script>
import {access} from "../access";
import {appConfig} from "../config";
import Theme from '../theme'
import UserProfile from "./profile/UserProfile";
import ThemeSet from "../theme/ThemeSet";
import {assertEmpty} from '../utils/common'

export default {
  name: "MetaHeader",
  mixins: [],
  props: {
    appName: String,
    appLogo: String,
    showGreeting: {
      type: Boolean,
      default: () => null
    },
    showThemeSetting: {
      type: Boolean,
      default: () => null
    },
    themeConf: Object
  },
  components: {ThemeSet, UserProfile},
  data() {
    return {
      user: access.user
    }
  },
  computed: {
    logo() {
      return assertEmpty(this.appLogo, assertEmpty(appConfig.logo, 'meta'))
    },
    name() {
      return assertEmpty(this.appName, appConfig.name)
    },
    greeting() {
      return assertEmpty(this.showGreeting, appConfig.showGreeting)
    },
    themeSetting() {
      return assertEmpty(this.showThemeSetting, appConfig.showThemeSetting)
    },
    headerConf() {
      const {header: headerConf} = Theme.getTheme()
      return headerConf
    },
    headerStyle() { // FIXME: 随着主题设置, headerConf实时响应了, 但是headerStyle却并未跟着变动。有意思的是 TagView中是一样的却可以实现实时预览
      const {themeConf: {textColor, backgroundColor} = {}} = this
      const {headerConf: {textColorDef, backgroundColorDef}} = this
      return {
        'color': assertEmpty(textColor, textColorDef),
        'background-color': assertEmpty(backgroundColor, backgroundColorDef)
      }
    }
  }
}
</script>
<style scoped lang="scss">
$headerHeight: 60px;
.header {
  height: $headerHeight;
  line-height: $headerHeight;
  padding: 0 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  color: #409EFF;
  background-color: #001529;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .1);
  z-index: 1;

  .logo {
    font-size: 50px;
    margin-right: 10px;
    width: 0.8em;
    height: 0.8em;
  }

  .name {
    margin: 0;
  }

  div.nav {
    flex: 1;
    padding: 0 10px;
    display: flex;
    align-items: center;
    height: 100%;
  }

  div.dock {
    display: flex;
    justify-content: flex-end;
    align-items: center;

    & > * {
      display: inline-block;
      margin: 0 10px;
    }

    span.empty {
      flex: 1
    }
  }
}
</style>
