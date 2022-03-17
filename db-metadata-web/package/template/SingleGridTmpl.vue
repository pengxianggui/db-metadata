<template>
  <div class="page-container">
    <search-view :ic="config.instanceCodes.SearchView"
                 @search="handleSearch"></search-view>

    <table-view :ref="tableRefName"
                :ic="config.instanceCodes.TableView"
                :filter-params="filterParams"
                @open-form-view="openFormView">

      <tempalte #operation-bar="scope">
        <slot name="operation-bar" v-bind="scope"></slot>
      </tempalte>

      <template #prefix-btn="scope">
        <slot name="prefix-btn" v-bind="scope"></slot>
      </template>
      <template #add-btn="scope">
        <slot name="add-btn" v-bind="scope"></slot>
      </template>
      <template #batch-delete-btn="scope">
        <slot name="batch-delete-btn" v-bind="scope"></slot>
      </template>
      <template #suffix-btn="scope">
        <slot name="suffix-btn" v-bind="scope"></slot>
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

    </table-view>
  </div>
</template>

<script>
import utils from '../utils'
import {loadFeature} from "../utils/rest";
import {getNameOfFormTypes} from "../view/formview/ui-conf";
import {SingleGridConfig} from "../meta/feature/ext/featureType";

export default {
  name: "SingleGridTmpl",
  meta: {
    isTemplate: true,
    isPage: false,
    cn: '单表模板',
    icon: 'table',
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
      filterParams: {}
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
        }).then(value => {
          this.refresh()
        }).catch(() => {
          utils.printInfo('您取消了表单')
        })
      })
    }
  },
  created() {
    this.$merge(this.config, SingleGridConfig)

    if (!utils.isEmpty(this.featureCode)) {
      loadFeature(this.$axios, this.featureCode).then(resp => {
        this.$reverseMerge(this.config, resp.data)
      })
    }
  },
  computed: {
    tableRefName() {
      const {config: {config: {objectCode}}} = this
      return objectCode;
    }
  }
}
</script>

<style scoped>

</style>
