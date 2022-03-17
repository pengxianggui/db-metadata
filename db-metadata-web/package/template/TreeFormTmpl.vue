<template>
  <div class="page-container">
    <row-grid :span="[6, 18]">
      <template #0>
        <tree-view :ic="config.tree.instanceCodes.TreeView" :ref="config.tree.config.objectCode"
              @active-change="handleActiveChange" @chose-change="handleChoseChange"></tree-view>
      </template>
      <template #1>
        <form-view :meta-url="formMetaUrl" @ok="refreshTreeData" v-if="formShow">
          <template #action="scope">
            <slot name="action" v-bind="scope"></slot>
          </template>
        </form-view>
      </template>
    </row-grid>
  </div>
</template>

<script>
import utils from '../utils'
import {restUrl} from '../constant/url'
import {defaultPrimaryKey} from "../config";
import {getTreeMeta, loadFeature} from '../utils/rest'
import DefaultFormViewMeta from '@/../package/view/formview/ui-conf'
import {TableAndFormConfig} from "../meta/feature/ext/featureType";
import {isEmpty} from "../utils/common";
import {TreeAndFormConfig} from "../meta/feature/ext/featureType";

export default {
  name: "TreeFormTmpl",
  meta: {
    isTemplate: true,
    isPage: false,
    cn: '左树-右表单模板',
    icon: 'tree_form',
    buildIn: true // 内建：DbMeta提供
  },
  props: {
    fc: String,
    config: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    const {fc: R_fc} = this.$route.query;
    const featureCode = utils.assertUndefined(this.fc, R_fc);
    return {
      featureCode: featureCode,
      activeTreeData: {},
      formMetaUrl: null,
      formShow: true // 重新渲染form。当树点选的数据发生变化时，表单的meta可能不一样, 需要重新渲染。通过此值配置v-if实现
    }
  },
  methods: {
    reRenderForm() {
      this.formShow = false
      this.$nextTick(() => {
        this.formShow = true
      })
    },
    refreshTreeData() {
      const {config: {tree: {config: {objectCode: refName}}}} = this
      this.$nextTick(() => {
        this.$refs[refName].getData()
      })
    },
    handleChoseChange(rows) {
      const {tree: {config: {objectCode}}} = this
      this.$emit('tree-chose-change', {
        rows: rows,
        objectCode: objectCode,
        primaryKey: this.$refs[objectCode].primaryKey
      })
    },
    handleActiveChange(row) {
      const {config: {tree: {config: {objectCode}}}} = this
      this.activeTreeData = row
      this.$emit('tree-active-change', {
        row: row,
        objectCode: objectCode
      })
    },
    openFormView({url, params = {}}) {
      this.$merge(params, {
        instanceCode: this.config.form.instanceCodes.FormView
      })
      this.formMetaUrl = this.$compile(url, params)
      this.reRenderForm()
    }
  },
  created() {
    this.$merge(this.config, TreeAndFormConfig)

    if (!isEmpty(this.featureCode)) {
      loadFeature(this.$axios, this.featureCode).then(resp => {
        this.$reverseMerge(this.config, resp.data)
      })
    }
  }

}
</script>

<style scoped>

</style>
