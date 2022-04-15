<template>
  <el-form ref="featureConfigForm" :model="value" inline>
    <el-divider content-position="left">树</el-divider>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="tree.config.objectCode" required error="必填">
      <drop-down-box v-model="value.tree.config.objectCode" filterable
                     :data-url="getObjectCodeUrl(value.tree)" @change="resetTreeRelate(value.tree)"></drop-down-box>
    </el-form-item>
    <el-form-item prop="tree.config.primaryKey" required error="必填">
      <template #label>
        <span>关联外键</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">关联表的外键</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.tree.config.primaryKey" filterable
                     :data-url="getFieldCodeUrl(value.tree)"></drop-down-box>
    </el-form-item>

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击 <auto-computed-button :oc="value.tree.config.objectCode" @ok="autoComputedOk"></auto-computed-button>)
      </span>
    </el-divider>
    <div v-if="refresh">
      <el-form-item label="树" prop="tree.instanceCodes.TreeView" required error="必填">
        <drop-down-box v-model="value.tree.instanceCodes.TreeView" filterable
                       :data-url="getInstanceCodeOptionsUrl(value.tree, 'TreeView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="表单" prop="tree.instanceCodes.FormView" required error="必填">
        <drop-down-box v-model="value.tree.instanceCodes.FormView" filterable
                       :data-url="getInstanceCodeOptionsUrl(value.tree, 'FormView')"></drop-down-box>
      </el-form-item>
    </div>

    <el-divider content-position="left">表格</el-divider>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="table.config.objectCode" required error="必填">
      <drop-down-box v-model="value.table.config.objectCode" filterable :data-url="getObjectCodeUrl(value.table)"
                     @change="resetTableRelate(value.table)"></drop-down-box>
    </el-form-item>
    <el-form-item prop="table.config.foreignPrimaryKey" required error="必填">
      <template #label>
        <span>关联主键</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">关联树的外键</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.table.config.foreignPrimaryKey" filterable
                     :data-url="getFieldCodeUrl(value.table)"></drop-down-box>
    </el-form-item>

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击<auto-computed-button :oc="value.table.config.objectCode" @ok="autoComputedOk"></auto-computed-button>)</span>
    </el-divider>

    <div v-if="refresh">
      <el-form-item label="搜索面板" prop="table.instanceCodes.SearchView" required error="必填">
        <drop-down-box v-model="value.table.instanceCodes.SearchView" filterable :data-url="getInstanceCodeOptionsUrl(value.table, 'SearchView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="表格" prop="table.instanceCodes.TableView" required error="必填">
        <drop-down-box v-model="value.table.instanceCodes.TableView" filterable :data-url="getInstanceCodeOptionsUrl(value.table, 'TableView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="表单" prop="table.instanceCodes.FormView" required error="必填">
        <drop-down-box v-model="value.table.instanceCodes.FormView" filterable :data-url="getInstanceCodeOptionsUrl(value.table, 'FormView')"></drop-down-box>
      </el-form-item>
    </div>
  </el-form>
</template>

<script>
import AutoComputedButton from "./AutoComputedButton";
import {restUrl} from "../../../constant/url";
import {isEmpty} from "../../../utils/common";
export default {
  name: "TreeTable",
  components: {AutoComputedButton},
  props: {
    value: Object
  },
  data() {
    return {
      refresh: true
    }
  },
  methods: {
    resetTreeRelate(item) {
      this.$reverseMerge(item, {
        "config": {
          "objectCode": null,
          "primaryKey": null
        },
        "instanceCodes": {
          "TreeView": null,
          "FormView": null,
          "SearchView": null
        }
      })
    },
    getObjectCodeUrl(item) {
      return restUrl.OBJECT_CODE_LIST_AS_KV
    },
    getFieldCodeUrl(item) {
      const {config: {objectCode}} = item
      if (isEmpty(objectCode)) {
        return restUrl.FIELD_CODE_LIST_BY_OBJECT
      }
      return this.$compile(restUrl.FIELD_CODE_LIST_BY_OBJECT, {
        objectCode: objectCode
      })
    },
    resetTableRelate(item) {
      this.$reverseMerge(item, {
        "config": {
          "foreignPrimaryKey": null
        },
        "instanceCodes": {
          "SearchView": null,
          "TableView": null,
          "FormView": null
        }
      })
    },
    getInstanceCodeOptionsUrl(item, componentCode) {
      const {config: {objectCode}} = item
      if (isEmpty(objectCode)) {
        return restUrl.LOAD_INSTANCE_CODE_BY_OBJECT_COMP
      }
      return this.$compile(restUrl.LOAD_INSTANCE_CODE_BY_OBJECT_COMP, {
        componentCode: componentCode,
        objectCode: objectCode,
        kv: true
      })
    },
    validate(callback) {
      return this.$refs['featureConfigForm'].validate(valid => callback(valid))
    },
    autoComputedOk() {
      this.refresh = false
      this.$nextTick(() => {
        this.refresh = true
      })
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.$refs['featureConfigForm'].clearValidate()
    })
  },
  beforeDestroy() {
    this.$refs['featureConfigForm'].clearValidate()
    this.$refs['featureConfigForm'].resetFields()
  }
}
</script>

<style scoped>

</style>
