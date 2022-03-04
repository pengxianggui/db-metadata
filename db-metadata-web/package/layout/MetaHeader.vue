<template>
  <div class="header" :style="headerStyle">
    <svg-icon :value="logo" class="logo" v-if="logo"></svg-icon>
    <h2 class="name">{{ appName }}</h2>
    <slot></slot>
    <div class="dock">
      <div>欢迎您: {{user.username}}</div>
      <theme-set></theme-set>
      <div></div>
      <user-profile></user-profile>
    </div>
  </div>
</template>

<script>
import {access} from "../access";
import {appConfig} from "../config";
import Theme from '../theme'
import UserProfile from "./profile/UserProfile";
import ThemeSet from "../theme/ThemeSet";

export default {
  name: "MetaHeader",
  components: {ThemeSet, UserProfile},
  data() {
    return {
      user: access.user
    }
  },

  computed: {
    logo() {
      const {logo} = appConfig
      return logo
    },
    appName() {
      const {name: appName} = appConfig
      return appName
    },
    showGreeting() {
      const {showGreeting = true} = appConfig
      return showGreeting
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

  div.dock {
    flex: 1;
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
