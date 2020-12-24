<template>
  <div class="container" :class="{'min-width': !nativeCollapse}" style="height: 100%">
    <slot name="toggle-button">
      <div class="toggle-button" @click="toggleMenu" v-if="showCollapseButton">
        <i :class="nativeCollapse ? 'el-icon-caret-right' : 'el-icon-caret-left'"></i>
      </div>
    </slot>
    <el-menu :default-active="activeMenu"
             :collapse="nativeCollapse"
             v-bind="conf" id="__DEFAULT_MENU">
      <template v-for="menu in metaMenus" v-if="$isRoot()">
        <menu-item v-if="!menu.hidden" :item="menu" :base-path="menu.path"></menu-item>
      </template>

      <template v-for="menu in menus">
        <menu-item :item="menu" :base-path="menu.path"></menu-item>
      </template>
      <slot></slot>
    </el-menu>
  </div>
</template>

<script>
import Meta from "../../mixins/meta";
import DefaultMeta from "../../navmenu/ui-conf";
import {restUrl} from "../../../constant/url";
import MenuItem from './MenuItem'
import MetaMenuData from '../../../menu'
import {resolvePath} from '@/../package/utils/url'
import utils from '@/../package/utils'

export default {
  name: "NavMenu",
  mixins: [Meta(DefaultMeta)],
  components: {
    MenuItem
  },
  props: {
    dataUrl: {
      type: String,
      default: () => restUrl.MENU_DATA
    },
    collapse: {
      type: Boolean,
      default: () => false
    }
  },
  data() {
    return {
      metaMenus: MetaMenuData,
      menus: []
    }
  },
  watch: {
    dataUrl: function (newV, oldV) {
      this.getData(newV)
    }
  },
  methods: {
    getData(url) {
      this.$axios.get(url).then(resp => {
        const {data: dynamicMenu} = resp
        this.menus.push(...dynamicMenu)
      })
    },
    toggleMenu() {
      this.nativeCollapse = !this.nativeCollapse
    }
  },
  created() {
    this.getData(this.dataUrl)
  },
  computed: {
    activeMenu() {
      const route = this.$route;
      const {path, query} = route;
      return resolvePath(path, query)
    },
    showCollapseButton() {
      const {$attrs: {'show-collapse-button': showCollapseButton}, innerMeta: {show_collapse_button}} = this
      return utils.assertUndefined(showCollapseButton, show_collapse_button)
    },
    nativeCollapse: {
      get: function () {
        const {collapse: attrCollapse, innerMeta: {conf: {collapse: metaCollapse}}} = this
        return utils.assertUndefined(attrCollapse, metaCollapse);
      },
      set: function (val) {
        return this.$emit("update:collapse", val);
      }
    },
    conf() {
      const {$reverseMerge, innerMeta: {conf}, $attrs} = this
      return $reverseMerge(conf, $attrs)
    }
  }
}
</script>

<style lang="scss" scoped>
div.container {
  $toggleButtonHeight: 100px;
  $toggleButtonWidth: 10px;

  .toggle-button {
    position: absolute;
    z-index: 99;
    top: 50%;
    margin-top: -$toggleButtonHeight / 2;
    right: 0;
    width: $toggleButtonWidth;
    height: $toggleButtonHeight;
    line-height: $toggleButtonHeight;
    border: 1px solid #dddddd;
    cursor: pointer;
    text-align: center;
    overflow: hidden;
    border-top-left-radius: 10px;
    border-bottom-left-radius: 10px;

    i {
      width: $toggleButtonWidth;
      text-align: center;
      color: #999999;

      &::before {
        width: $toggleButtonWidth;
        margin-left: -3px
      }
    }
  }

  .toggle-button:hover {
    background-color: #DDDDDD;
  }

  #__DEFAULT_MENU {
    height: 100%;
    position: relative;
  }
}

div.container.min-width {
  $menuWidth: 200px;
  min-width: $menuWidth;
}
</style>

<style lang="scss">
// ElementUI el-menu组件的一个坑:
// 1. https://blog.csdn.net/qq_34452824/article/details/106546478
// 2. https://blog.csdn.net/weixin_43080554/article/details/106081419
#__DEFAULT_MENU.el-menu--collapse {
  div > .el-submenu > .el-submenu__title .el-submenu__icon-arrow {
    display: none;
  }

  div > .el-submenu > .el-submenu__title span {
    height: 0;
    width: 0;
    overflow: hidden;
    visibility: hidden;
    display: inline-block;
  }
}
</style>