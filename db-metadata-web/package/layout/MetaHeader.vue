<template>
  <div class="header" :style="headerStyle" v-bind="$attrs">
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
      <div v-if="greeting">欢迎您: {{user.username}}</div>

      <theme-set v-if="theme"></theme-set>

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
  props: {
    appName: String,
    appLogo: String,
    showGreeting: {
      type: Boolean,
      default: () => true
    },
    showThemeSetting: {
      type: Boolean,
      default: () => true
    }
  },
  components: {ThemeSet, UserProfile},
  data() {
    return {
      user: access.user
    }
  },

  computed: {
    logo() {
      return assertEmpty(this.appLogo, appConfig.logo)
    },
    name() {
      return assertEmpty(this.appName, appConfig.name)
    },
    greeting() {
      return assertEmpty(this.showGreeting, appConfig.showGreeting)
    },
    theme() {
      return assertEmpty(this.showGreeting, appConfig.showGreeting)
    },
    headerStyle() {
      const {header: {titleColor, backgroundColor} = {}} = Theme.getTheme()
      return {
        'color': titleColor,
        'background-color': backgroundColor
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

  .logo {
    font-size: 50px;
    margin-right: 10px;
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
    color: #777779;

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
