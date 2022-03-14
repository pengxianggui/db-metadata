<template>
  <el-form ref="featureConfigForm" :model="value" inline>
    <el-divider content-position="left">主表</el-divider>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="master.config.objectCode" required error="必填">
      <drop-down-box v-model="value.master.config.objectCode"
                     :data-url="getObjectCodeUrl(value.master)" @change="resetMasterRelate(value.master)"></drop-down-box>
    </el-form-item>
    <el-form-item label="关联主键" prop="master.config.primaryKey" required error="必填">
      <drop-down-box v-model="value.master.config.primaryKey"
                     :data-url="getFieldCodeUrl(value.master)"></drop-down-box>
    </el-form-item>

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击
        <auto-computed-button :oc="value.master.config.objectCode"></auto-computed-button>)
      </span>
    </el-divider>

    <el-form-item label="搜索面板" prop="master.instanceCodes.SearchView" required error="必填">
      <drop-down-box v-model="value.master.instanceCodes.SearchView"
                     :data-url="getInstanceCodeOptionsUrl(value.master, 'SearchView')"></drop-down-box>
    </el-form-item>
    <el-form-item label="表格" prop="master.instanceCodes.TableView" required error="必填">
      <drop-down-box v-model="value.master.instanceCodes.TableView"
                     :data-url="getInstanceCodeOptionsUrl(value.master, 'TableView')"></drop-down-box>
    </el-form-item>
    <el-form-item label="表单" prop="master.instanceCodes.FormView" required error="必填">
      <drop-down-box v-model="value.master.instanceCodes.FormView"
                     :data-url="getInstanceCodeOptionsUrl(value.master, 'FormView')"></drop-down-box>
    </el-form-item>

    <el-divider content-position="left">子表</el-divider>

    <el-tabs v-model="activeTab" @tab-click="handleClick" @edit="handleTabEdit" editable>
      <el-tab-pane v-for="(slave, index) in value.slaves" :label="'从表' + index" :name="index + ''" :key="slave.config.objectCode">
        <el-divider content-position="right">配置</el-divider>
        <el-form-item label="元对象" :prop="'slaves.' + index + '.config.objectCode'" required error="必填">
          <drop-down-box v-model="slave.config.objectCode" :data-url="getObjectCodeUrl(slave)"
                         @change="resetSlaveRelate(slave)"></drop-down-box>
        </el-form-item>
        <el-form-item label="关联主键" :prop="'slaves.' + index + '.config.foreignPrimaryKey'" required error="必填">
          <drop-down-box v-model="slave.config.foreignPrimaryKey"
                         :data-url="getFieldCodeUrl(slave)"></drop-down-box>
        </el-form-item>

        <el-divider content-position="right">
          <span>选择实例编码</span>
          <span style="color: #acacac">&nbsp;(还没有实例配置? 点击<auto-computed-button :oc="slave.config.objectCode"></auto-computed-button>)</span>
        </el-divider>

        <el-form-item label="搜索面板" :prop="'slaves.' + index + '.instanceCodes.SearchView'" required error="必填">
          <drop-down-box v-model="slave.instanceCodes.SearchView" :data-url="getInstanceCodeOptionsUrl(slave, 'SearchView')"></drop-down-box>
        </el-form-item>
        <el-form-item label="表格" :prop="'slaves.' + index + '.instanceCodes.TableView'" required error="必填">
          <drop-down-box v-model="slave.instanceCodes.TableView" :data-url="getInstanceCodeOptionsUrl(slave, 'TableView')"></drop-down-box>
        </el-form-item>
        <el-form-item label="表单" :prop="'slaves.' + index + '.instanceCodes.FormView'" required error="必填">
          <drop-down-box v-model="slave.instanceCodes.FormView" :data-url="getInstanceCodeOptionsUrl(slave, 'FormView')"></drop-down-box>
        </el-form-item>
      </el-tab-pane>
    </el-tabs>
  </el-form>
</template>

<script>
import AutoComputedButton from "./AutoComputedButton";
import {isEmpty} from "../../../utils/common";
import {restUrl} from "../../../constant/url";
import {FEATURE_TYPE} from "./featureType";

export default {
  name: "MasterSlaveGrid",
  components: {AutoComputedButton},
  props: {
    value: Object
  },
  data() {
    return {
      activeTab: '0',
    }
  },
  methods: {
    resetMasterRelate(item) {
      this.$reverseMerge(item, {
        "config": {
          "primaryKey": null,
        },
        "instanceCodes": {
          "SearchView": null,
          "TableView": null,
          "FormView": null
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
    resetSlaveRelate(item) {
      this.$reverseMerge(item, {
        "config": {
          "foreignPrimaryKey": null,
          "order": 0
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

    handleClick(tab, event) {
      // todo
    },
    handleTabEdit(targetName, action) {
      const {slaves} = FEATURE_TYPE.MasterSlaveGrid.value
      if (action === 'add') {
        const length = this.value.slaves.length
        const newSlave = {}
        this.$reverseMerge(newSlave, slaves[0])
        this.$reverseMerge(newSlave, {
          config: {order: length}
        })
        this.value.slaves.push(newSlave)
      }
      if (action === 'remove') {
        let index = targetName - 0
        if (this.value.slaves.length > 1) {
          this.value.slaves.splice(index, 1)
        }
      }
    },
    validate(callback) {
      return this.$refs['featureConfigForm'].validate(valid => callback(valid))
    }
  },
  beforeDestroy() {
    this.$refs['featureConfigForm'].clearValidate()
  }
}
</script>

<style scoped>

</style>
