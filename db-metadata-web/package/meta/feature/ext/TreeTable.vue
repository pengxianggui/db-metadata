<template>
  <el-form ref="featureConfigForm" :model="value" inline>
    <el-divider content-position="left">树</el-divider>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="tree.config.objectCode" required error="必填">
      <drop-down-box v-model="value.tree.config.objectCode"
                     :data-url="getObjectCodeUrl(value.tree)" @change="resetTreeRelate(value.tree)"></drop-down-box>
    </el-form-item>
    <el-form-item prop="tree.config.idKey" required error="必填">
      <template #label>
        <span>主键标识</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">Tree自身关联的主键</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.tree.config.idKey" :data-url="getFieldCodeUrl(value.tree)"></drop-down-box>
    </el-form-item>
    <el-form-item prop="tree.config.pidKey" required error="必填">
      <template #label>
        <span>父节点标识</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">Tree自身关联的外键</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.tree.config.pidKey" :data-url="getFieldCodeUrl(value.tree)"></drop-down-box>
    </el-form-item>
    <el-form-item prop="tree.config.rootIdentify">
      <template #label>
        <span>根节点标识</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">根节点标识, 为何值时表示根节点</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <text-box v-model="value.tree.config.rootIdentify"></text-box>
    </el-form-item>
    <el-form-item prop="tree.config.label" required error="必填">
      <template #label>
        <span>显示字段</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">显示为树节点名的字段(当为树形态而非树表时, 这是有用的)</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.tree.config.label" :data-url="getFieldCodeUrl(value.tree)"></drop-down-box>
    </el-form-item>
    <el-form-item prop="tree.config.isSync">
      <template #label>
        <span>是否异步加载树</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">树节点展开是否异步加载</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <bool-box v-model="value.tree.config.isSync"></bool-box>
    </el-form-item>
    <el-form-item prop="tree.config.primaryKey" required error="必填">
      <template #label>
        <span>关联外键</span>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">关联表的外键</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.tree.config.primaryKey"
                     :data-url="getFieldCodeUrl(value.tree)"></drop-down-box>
    </el-form-item>

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击
        <auto-computed-button :oc="value.tree.config.objectCode"></auto-computed-button>)
      </span>
    </el-divider>
    <el-form-item label="树" prop="tree.instanceCodes.TreeView" required error="必填">
      <drop-down-box v-model="value.tree.instanceCodes.TreeView"
                     :data-url="getInstanceCodeOptionsUrl(value.tree, 'TreeView')"></drop-down-box>
    </el-form-item>

    <el-divider content-position="left">表格</el-divider>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="table.config.objectCode" required error="必填">
      <drop-down-box v-model="value.table.config.objectCode" :data-url="getObjectCodeUrl(value.table)"
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
      <drop-down-box v-model="value.table.config.foreignPrimaryKey"
                     :data-url="getFieldCodeUrl(value.table)"></drop-down-box>
    </el-form-item>

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击<auto-computed-button :oc="value.table.config.objectCode"></auto-computed-button>)</span>
    </el-divider>

    <el-form-item label="搜索面板" prop="table.instanceCodes.SearchView" required error="必填">
      <drop-down-box v-model="value.table.instanceCodes.SearchView" :data-url="getInstanceCodeOptionsUrl(value.table, 'SearchView')"></drop-down-box>
    </el-form-item>
    <el-form-item label="表格" prop="table.instanceCodes.TableView" required error="必填">
      <drop-down-box v-model="value.table.instanceCodes.TableView" :data-url="getInstanceCodeOptionsUrl(value.table, 'TableView')"></drop-down-box>
    </el-form-item>
    <el-form-item label="表单" prop="table.instanceCodes.FormView" required error="必填">
      <drop-down-box v-model="value.table.instanceCodes.FormView" :data-url="getInstanceCodeOptionsUrl(value.table, 'FormView')"></drop-down-box>
    </el-form-item>
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
  methods: {
    resetTreeRelate(item) {
      this.$reverseMerge(item, {
        "config": {
          "idKey": null,
          "pidKey": null,
          "rootIdentify": null,
          "label": null,
          "isSync": false,
          "primaryKey": null
        },
        "instanceCodes": {
          "TreeView": null
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
    }
  },
  beforeDestroy() {
    this.$refs['featureConfigForm'].clearValidate()
    this.$refs['featureConfigForm'].resetFields()
  }
}
</script>

<style scoped>

</style>
