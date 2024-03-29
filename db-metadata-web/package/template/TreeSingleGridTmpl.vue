<template>
  <div class="md_page-container">
    <search-view :ic="config.instanceCodes.SearchView" @search="handleSearch"></search-view>
    <table-tree-view :ref="tableRefName"
                     :ic="config.instanceCodes.TableTreeView"
                     :filter-params="filterParams"
                     @open-form-view="openFormView"
                     class="flex-container flex-item"
                     :height="tableHeight">
      <tempalte #operation-bar="scope">
        <slot name="operation-bar" v-bind="scope"></slot>
      </tempalte>

      <template #prefix-btn="scope">
        <slot name="prefix-btn" v-bind="scope"></slot>
      </template>
      <template #add-btn="scope">
        <slot name="add-btn" v-bind="scope"></slot>
      </template>
      <template #suffix-btn="scope">
        <slot name="suffix-btn" v-bind="scope"></slot>
      </template>
      <template #float-right-btn="scope">
        <slot name="float-right-btn" v-bind="scope"></slot>
      </template>

      <template #buttons="scope">
        <slot name="buttons" v-bind="scope"></slot>
      </template>

      <template #inner-before-extend-btn="scope">
        <slot name="inner-before-extend-btn" v-bind="scope"></slot>
      </template>
      <template #view-btn="scope">
        <slot name="view-btn" v-bind="scope"></slot>
      </template>
      <template #edit-btn="scope">
        <slot name="edit-btn" v-bind="scope"></slot>
      </template>
      <template #delete-btn="scope">
        <slot name="delete-btn" v-bind="scope"></slot>
      </template>
      <template #inner-after-extend-btn="scope">
        <slot name="inner-after-extend-btn" v-bind="scope"></slot>
      </template>
    </table-tree-view>
  </div>
</template>

<script>
import utils from '../utils'
import {loadFeature} from "../utils/rest";
import {getNameOfFormTypes} from "../view/formview/ui-conf";
import {FEATURE_TYPE} from "../meta/feature/ext/featureType";

export default {
  name: "TreeSingleGridTmpl",
  meta: {
    type: 'template',
    cn: '树表模板',
    icon: 'treetable',
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
      filterParams: {},
      componentHeight: 480 // default value
    }
  },
  methods: {
    refresh() {
      const {tableRefName} = this;
      this.$refs[tableRefName].getData();
    },
    handleSearch(params) {
      const {tableRefName} = this
      this.filterParams = params
      this.$nextTick(() => {
        this.$refs[tableRefName].getData();
      })
    },

    openFormView({url, params}) {
      // TODO 2.3 表单的打开方式可以配置到功能里: 1-弹窗(当前已实现) ;2-路由(系统预置FormTmpl模板以及内置的/form/:fc路由后，即可实现)
      this.$merge(params, {
        instanceCode: this.config.instanceCodes.FormView
      })
      const finalUrl = this.$compile(url, params)
      this.$axios.get(finalUrl).then(resp => {
        const {data: meta = {}} = resp
        const {form_type} = meta
        this.$dialog(meta, null, {
          title: getNameOfFormTypes(form_type)
        }).then((/*value*/) => {
          this.refresh()
        }).catch(() => {
          utils.printInfo('您取消了表单')
        })
      })
    }
  },
  created() {
    this.$merge(this.config, FEATURE_TYPE.TreeSingleGrid.value)

    if (!utils.isEmpty(this.featureCode)) {
      loadFeature(this.$axios, this.featureCode).then(resp => {
        this.$reverseMerge(this.config, resp.data)
      })
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.componentHeight = this.$el.offsetHeight
    })
  },
  computed: {
    tableRefName() {
      const {config: {config: {objectCode}}} = this
      return objectCode;
    },
    tableHeight() {
      // 动态计算表格高度，以便内部生成滚动条
      return this.componentHeight - 28 - 32;
    }
  }
}
</script>

<style scoped lang="scss">
</style>
