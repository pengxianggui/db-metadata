<template>
  <!-- 背景色设置主要是防止层级展开后，div没有背景色，而里面的li有背景色，但div由于宽度大于下一层的li，导致背景色有明显的断层 -->
  <div>
    <template v-if="isLeaf">
      <!-- 只有一个需要展示的子节点，并且该子节点下再没有子节点了 -->
      <app-link :to="resolvePath(item.path)" :disabled="item.disable"
                :query="resolveParams(item['params'])">
        <pop-menu trigger="right-click" :disabled="!metaEditable">
          <template #label>
            <el-menu-item :index="resolvePath(item.path)" :disabled="item.disable"
                          :class="{'submenu-title-noDropdown':!isNest}">
              <svg-icon :value="item.icon" v-if="item.icon"></svg-icon>
              <span slot="title">{{ item.title }}</span>
            </el-menu-item>
          </template>
          <list>
            <list-item @click="editMenuMeta(item)">编辑元菜单</list-item>
          </list>
        </pop-menu>
      </app-link>
    </template>

    <!-- 有1个以上要展示的子节点 -->
    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body :disabled="item.disable">
      <template slot="title">
        <div>
          <svg-icon :value="item.icon" v-if="item.icon"></svg-icon>

          <pop-menu trigger="right-click" :disabled="!metaEditable">
            <template #label>
              <span slot="title">{{ item.title }}</span>
            </template>
            <list>
              <list-item @click="editMenuMeta(item)">编辑元菜单</list-item>
            </list>
          </pop-menu>
        </div>
      </template>
      <template v-for="(subMenu) in childrenMenus">
        <menu-item :key="subMenu.loopKey"
                   :is-nest="true"
                   :item="subMenu"
                   :base-path="resolvePath(subMenu.path)">
        </menu-item>
      </template>
    </el-submenu>
  </div>
</template>

<script>
import path from 'path'
import AppLink from './Link'
import utils from '@/../package/utils'
import {restUrl} from "../../../constant/url";
import {isEmpty, isArray} from "../../../utils/common";

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
    }
  },
  created() {
    if (this.item.title == '可编程菜单1') {
      debugger
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
    resolvePath(routePath = '') {
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
    isLeaf() { // 是否叶子节点
      const {item} = this
      // 菜单项没有children属性, 或 children数据非法(非数组), 或children中所有子菜单项都是隐藏的(hidden)，则视为叶子节点了
      return !item.hasOwnProperty('children')
          || !isArray(item.children)
          || item.children.every(subM => subM.hasOwnProperty('hidden') && subM.hidden === true)
    },
    metaEditable() { // 是否可编辑菜单元数据
      const {item: {id}} = this
      return this.$isRoot() && !isEmpty(id) // 有id值表示是数据库数据
    },
    childrenMenus() {
      const {children} = this.item
      return children.sort((m1, m2) => m1.order - m2.order)
    }
  }
}
</script>
