<template>
  <div class="page-container">
    <row-grid :span="[14, 10]">
      <template #0>
        <table-view :ref="config.table.config.objectCode"
                    :ic="config.table.instanceCodes.TableView"
                    @open-form-view="openFormView"
                    @active-change="handleActiveChange">
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
        </table-view>
      </template>
      <template #1>
        <div class="el-card" style="margin-left: 5px">
          <form-view :meta-url="formMetaUrl" @ok="refreshTableData" v-if="formShow">
            <template #action="scope">
              <slot name="action" v-bind="scope"></slot>
            </template>
          </form-view>
        </div>
      </template>
    </row-grid>
  </div>
</template>

<script>
import utils from '../utils'
import {FEATURE_TYPE} from "../meta/feature/ext/featureType";
import {loadFeature} from "../utils/rest";
import {isEmpty} from "../utils/common";

export default {
  name: "TableFormTmpl",
  meta: {
    type: 'template',
    cn: '左表-右表单模板',
    icon: 'table_form',
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
      activeTableData: {},
      filterParams: {},
      formMetaUrl: null,
      formShow: true // 重新渲染form。当表点选的数据发生变化时，表单的meta可能不一样, 需要重新渲染。通过此值配置v-if实现
    }
  },
  methods: {
    reRenderForm() {
      this.formShow = false
      this.$nextTick(() => {
        this.formShow = true
      })
    },
    refresh() {
      this.refreshTableData()
    },
    refreshTableData() {
      const {config: {table: {config: {objectCode: refName}}}} = this
      this.$nextTick(() => {
        this.$refs[refName].getData()
      })
    },
    handleChoseChange(rows) {
      const {table: {config: {objectCode}}} = this
      this.$emit('table-chose-change', {
        rows: rows,
        objectCode: objectCode,
        primaryKey: this.$refs[objectCode].primaryKey
      })
    },
    handleActiveChange(row) {
      const {config: {table: {config: {objectCode}}}} = this
      this.activeTableData = row
      this.$emit('table-active-change', {
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
    this.$merge(this.config, FEATURE_TYPE.TableForm.value)

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
