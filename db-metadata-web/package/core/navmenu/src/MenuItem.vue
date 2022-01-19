<template>
  <div v-if="!item.hidden &&  $hasRoles(needRoles)">

    <template v-if="hasOneShowingChild(item.children, item)
                        && (!onlyOneChild.children || onlyOneChild.children.length == 0 || onlyOneChild.noShowingChildren)">
      <!-- 只有一个需要展示的子节点，并且该子节点下再没有子节点了 -->
      <app-link :to="resolvePath(onlyOneChild.path)"
                :query="resolveParams(onlyOneChild['params'])">
        <pop-menu trigger="right-click" :disabled="!metaEditable">
          <template #label>
            <el-menu-item :index="resolvePath(onlyOneChild.path)"
                          :class="{'submenu-title-noDropdown':!isNest}">
              <svg-icon :value="onlyOneChild.icon" v-if="onlyOneChild.icon"></svg-icon>
              <span slot="title">{{ onlyOneChild.title }}</span>
            </el-menu-item>
          </template>
          <list>
            <list-item @click="editMenuMeta(onlyOneChild)">编辑元菜单</list-item>
          </list>
        </pop-menu>
      </app-link>
    </template>

    <!-- 有1个以上要展示的子节点 -->
    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template slot="title">
        <pop-menu trigger="right-click" :disabled="!metaEditable">
          <template #label>
            <div>
              <svg-icon :value="item.icon" v-if="item.icon"></svg-icon>
              <span slot="title">{{ item.title }}</span>
            </div>
          </template>
          <list>
            <list-item @click="editMenuMeta(item)">编辑元菜单</list-item>
          </list>
        </pop-menu>
      </template>
      <template v-for="(subMenu, index) in item.children">
        <menu-item :key="subMenu.path + index"
                   :is-nest="true"
                   :item="subMenu"
                   :base-path="resolvePath(subMenu.path)"></menu-item>
      </template>
    </el-submenu>
  </div>
</template>

<script>
import path from 'path'
import AppLink from './Link'
import utils from '@/../package/utils'
import {restUrl} from "../../../constant/url";
import {isEmpty} from "../../../utils/common";

export default {
  name: "MenuItem",
  components: {AppLink},
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      onlyOneChild: null
    }
  },
  methods: {
    editMenuMeta(item) {
      if (!this.$isRoot()) {
        this.$message.error("只有开发者账号可以执行此操作")
        return
      }
      const objectCode = "meta_menu" // 系统元对象编码-固定
      const {id, name} = item
      let url = this.$compile(restUrl.RECORD_TO_UPDATE, {objectCode: objectCode, primaryKv: id});
      this.$axios.get(url).then(({data}) => {
        this.$dialog(data, null, {
          title: `编辑菜单元数据:${name}`
        })
      });
    },
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter(item => !item.hasOwnProperty('hidden') || item.hidden === false);

      if (showingChildren.length === 1) {
        this.onlyOneChild = {...showingChildren[0]};
        return true
      }

      if (showingChildren.length === 0) {
        this.onlyOneChild = {...parent, path: '', noShowingChildren: true}
        return true
      }

      return false
    },
    resolvePath(routePath) {
      if (utils.isExternal(routePath)) {
        return routePath
      }
      if (utils.isExternal(this.basePath)) {
        return this.basePath
      }
      return path.resolve(this.basePath, routePath)
    },
    resolveParams(pathParams) {
      let params = {};
      if (pathParams && pathParams.indexOf('=') > -1) {
        let [key, value] = pathParams.split('=');
        params[key] = value;
      }
      return params;
    }
  },
  computed: {
    needRoles() {
      let {item: {meta}} = this
      meta = utils.strToObject(meta)
      if (!meta) {
        meta = {}
      }
      const {roles} = meta
      return roles
    },
    metaEditable() { // 是否可编辑菜单元数据
      const {item: {id}} = this
      return this.$isRoot() && !isEmpty(id)
    }
  },
  created() {
  }
}
</script>
