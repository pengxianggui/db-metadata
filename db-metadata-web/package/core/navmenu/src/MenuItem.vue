<template>
  <div v-if="!item.hidden &&  $hasRoles(needRoles)">
    <app-link v-if="noChild(item)" :to="item.path" :query="resolveParams(item['params'])">
      <pop-menu trigger="right-click" :disabled="!metaEditable">
        <template #label>
          <el-menu-item :index="item.path">
            <svg-icon :value="item.icon" v-if="item.icon"></svg-icon>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>
        <list>
          <list-item @click="editMenuMeta(item)">编辑元菜单</list-item>
        </list>
      </pop-menu>
    </app-link>

    <el-submenu v-else ref="subMenu" :index="item.path" popper-append-to-body>
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
      <template v-for="subMenu in item.children">
        <menu-item :key="subMenu.path"
                   :item="subMenu"></menu-item>
      </template>
    </el-submenu>
  </div>
</template>

<script>
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
    }
  },
  data() {
    return {}
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
    noChild(item) {
      return !item.hasOwnProperty('children') || item['children'].length === 0;
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
  }
}
</script>