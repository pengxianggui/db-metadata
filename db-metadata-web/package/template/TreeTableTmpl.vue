<template>
  <div class="md_page-container">
    <el-row class="row">
      <el-col :span="6" class="col">
        <tree-view :ref="config.tree.config.objectCode"
                   :ic="config.tree.instanceCodes.TreeView"
                   @open-form-view="openTreeFormView"
                   @active-change="handleActiveChange"
                   @chose-change="handleChoseChange"
                   class="tree-view"></tree-view>
      </el-col>
      <el-col :span="18" class="col flex-container">
        <!-- SearchView的meta来源 -->
        <search-view :ic="config.table.instanceCodes.SearchView" @search="handleSearch"></search-view>
        <table-view :ref="config.table.config.objectCode"
                    :ic="config.table.instanceCodes.TableView"
                    @open-form-view="openTableFormView"
                    :filter-params="filterParams"
                    class="flex-container flex-item"
                    :height="tableHeight">

          <tempalte #operation-bar="scope">
            <slot name="operation-bar" v-bind="scope"></slot>
          </tempalte>
          <template #prefix-btn="scope">
            <slot name="prefix-btn" v-bind="scope"></slot>
          </template>
          <template #add-btn="scope">
            <slot name="add-btn" v-bind="scope">
              <el-button v-bind="scope.conf.conf" @click="handleAdd" v-authorize="scope.conf.authorize">新增</el-button>
            </slot>
          </template>
          <template #batch-delete-btn="scope">
            <slot name="batch-delete-btn" v-bind="scope"></slot>
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

          <template #pagination-extend="scope">
            <slot name="pagination-extend" v-bind="scope"></slot>
          </template>
        </table-view>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import utils from '../utils'
import {loadFeature} from "../utils/rest";
import {restUrl} from "../constant/url";
import {isEmpty} from "../utils/common";
import {getNameOfFormTypes} from "../view/formview/ui-conf";
import {FEATURE_TYPE} from "../meta/feature/ext/featureType";

export default {
  name: "TreeTableTmpl",
  meta: {
    type: 'template',
    cn: '树-表模板',
    icon: 'tree_table',
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
      filterParams: {},
      componentHeight: 480
    }
  },
  computed: {
    tableHeight() {
      // 动态计算表格高度，以便内部生成滚动条
      return this.componentHeight - 28 - 32;
    }
  },
  created() {
    this.$merge(this.config, FEATURE_TYPE.TreeTable.value)

    if (!isEmpty(this.featureCode)) {
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
  methods: {
    refresh() {
      this.refreshTreeData()
      this.refreshTableData()
    },
    refreshTreeData() {
      const {config: {tree: {config: {objectCode: refName}}}} = this
      this.$nextTick(() => {
        this.$refs[refName].getData()
      })
    },
    refreshTableData() {
      const {
        config: {
          tree: {config: {primaryKey}},
          table: {config: {objectCode: refName, foreignPrimaryKey}}
        }, activeTreeData
      } = this
      const foreignPrimaryValue = activeTreeData[primaryKey]

      if (!utils.isEmpty(foreignPrimaryValue)) { // 树有点选，做筛选
        this.filterParams[foreignPrimaryKey + "_eq"] = foreignPrimaryValue
      } else { // 树未点选，则移除树的关联筛选
        delete this.filterParams[foreignPrimaryKey + "_eq"]
      }

      this.$nextTick(() => {
        this.$refs[refName].getData()
      })
    },
    handleChoseChange(rows) {
      // pxg_todo 多选树节点时, table的默认行为(待定)
      const {config: {tree: {config: {objectCode}}}} = this
      this.$emit('tree-chose-change', {
        rows: rows,
        objectCode: objectCode,
        primaryKey: this.$refs[objectCode].primaryKey
      })
    },
    handleActiveChange(row) {
      const {config: {tree: {config: {objectCode, primaryKey}}}} = this
      this.activeTreeData = row
      this.refreshTableData() // TODO 2.2 点选树节点的子节点下的表数据，按理说也应当一并出来。也许这个可以让开发人员在功能上配置？
      this.$emit('tree-active-change', {
        row: row, // 树选中的单条记录
        treeObjectCode: objectCode, // 树的元对象编码
        tablePrimaryKey: primaryKey // 树的关联主键
      })
    },
    handleSearch(params) {
      this.filterParams = params;
      this.refreshTableData()
    },
    handleAdd() {
      if (utils.isEmpty(this.activeTreeData)) { // TODO 2.2 树+表 模板 并不一定非得选中节点才能新增。也许可以让开发人员在功能上配置？
        this.$message.warning('请先选择树节点', '提示');
        return;
      }

      const {
        featureCode,
        activeTreeData,
        config: {
          tree: {config: {primaryKey}},
          table: {config: {foreignPrimaryKey}}
        }
      } = this
      const foreignPrimaryValue = activeTreeData[primaryKey]
      const url = restUrl.TREE_TABLE_TO_ADD_S
      const params = {
        featureCode: featureCode,
        foreignKeyName: foreignPrimaryKey,
        foreignKeyValue: foreignPrimaryValue
      }
      this.openTableFormView({url: url, params: params})
    },
    openTableFormView({url, params = {}}) {
      const {config: {table: {instanceCodes}}} = this
      this.$merge(params, {
        instanceCode: instanceCodes.FormView
      })

      const finalUrl = this.$compile(url, params)
      this.$axios.get(finalUrl).then(resp => {
        const {data: meta = {}} = resp
        const {form_type} = meta
        this.$dialog(meta, null, {
          title: getNameOfFormTypes(form_type)
        }).then((/*value*/) => {
          this.refreshTableData()
        }).catch(() => {
          utils.printInfo('您取消了表单')
        })
      })
    },
    openTreeFormView({url, params = {}}) {
      const {config: {tree: {instanceCodes}}} = this
      this.$merge(params, {
        instanceCode: instanceCodes.FormView
      })

      const finalUrl = this.$compile(url, params)
      this.$axios.get(finalUrl).then(resp => {
        const {data: meta = {}} = resp
        const {form_type} = meta
        this.$dialog(meta, null, {
          title: getNameOfFormTypes(form_type)
        }).then((/*value*/) => {
          this.refreshTreeData()
        }).catch(() => {
          utils.printInfo('您取消了表单')
        })
      })
    }
  },
}
</script>

<style scoped lang="scss">
.row {
  height: 100%;
  .col {
    height: 100%;

    .tree-view {
      height: 100%;
    }

    .flex-item {
      flex: 1;
      overflow: hidden;
    }
  }
}
</style>
