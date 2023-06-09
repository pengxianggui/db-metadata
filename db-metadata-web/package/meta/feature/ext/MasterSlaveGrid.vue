<template>
  <el-form ref="featureConfigForm" :model="value" inline>
    <el-divider content-position="left">主表</el-divider>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="master.config.objectCode" required error="必填">
      <drop-down-box v-model="value.master.config.objectCode" :filterable="true"
                     :data-url="getObjectCodeUrl(value.master)" @change="resetMasterRelate(value.master)"></drop-down-box>
    </el-form-item>
    <el-form-item label="关联主键" prop="master.config.primaryKey" required error="必填">
      <template #label>
        <span>关联主键</span>
        <el-tooltip content="此字段必须和从表的【关联主键】对应上，即从表的该值为主表此值的外键字段。">
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.master.config.primaryKey" :filterable="true"
                     :data-url="getFieldCodeUrl(value.master)"></drop-down-box>
    </el-form-item>
    <el-form-item label="显示字段" prop="master.config.labelKey" required error="必填">
      <template #label>
        <span>显示字段</span>
        <el-tooltip content="当展开子表时，需要使用主表哪个字段的数据作为子表顶部的展示。它应该是一个易读的值。">
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <drop-down-box v-model="value.master.config.labelKey" :filterable="true"
                     :data-url="getFieldCodeUrl(value.master)"></drop-down-box>
    </el-form-item>

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击
        <auto-computed-button :oc="value.master.config.objectCode" @ok="autoComputedOk"></auto-computed-button>)
      </span>
    </el-divider>
    <div v-if="refresh">
      <el-form-item label="搜索面板" prop="master.instanceCodes.SearchView" required error="必填">
        <drop-down-box v-model="value.master.instanceCodes.SearchView" :filterable="true"
                       :data-url="getInstanceCodeOptionsUrl(value.master, 'SearchView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="表格" prop="master.instanceCodes.TableView" required error="必填">
        <drop-down-box v-model="value.master.instanceCodes.TableView" :filterable="true"
                       :data-url="getInstanceCodeOptionsUrl(value.master, 'TableView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="表单" prop="master.instanceCodes.FormView" required error="必填">
        <drop-down-box v-model="value.master.instanceCodes.FormView" :filterable="true"
                       :data-url="getInstanceCodeOptionsUrl(value.master, 'FormView')"></drop-down-box>
      </el-form-item>
    </div>

    <el-divider content-position="left">子表</el-divider>
    <el-tabs v-model="activeTab" @tab-click="handleClick" @edit="handleTabEdit" editable>
      <el-tab-pane v-for="(slave, index) in value.slaves" :label="'从表' + index" :name="index + ''" :key="slave.config.objectCode">
        <el-divider content-position="right">配置</el-divider>
        <el-form-item label="元对象" :prop="'slaves.' + index + '.config.objectCode'" required error="必填">
          <drop-down-box v-model="slave.config.objectCode" :data-url="getObjectCodeUrl(slave)" :filterable="true"
                         @change="resetSlaveRelate(slave)"></drop-down-box>
        </el-form-item>
        <el-form-item label="关联主键" :prop="'slaves.' + index + '.config.foreignPrimaryKey'" required error="必填">
          <template #label>
            <span>关联主键</span>
            <el-tooltip content="此字段必须和主表的【关联主键】对应上，即此值为主表此值的外键字段。">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </template>
          <drop-down-box v-model="slave.config.foreignPrimaryKey" :filterable="true"
                         :data-url="getFieldCodeUrl(slave)"></drop-down-box>
        </el-form-item>
        <el-form-item label="显示值" prop="slave.config.labelKey">
          <template #label>
            <span>显示值</span>
            <el-tooltip content="当存在多个子表时，会以tab形态以便切换展示。你会希望配置一个易读的值作为标签上显示的内容，如果不配置会使用元对象编码。">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </template>
          <el-input v-model="slave.config.labelKey"></el-input>
        </el-form-item>

        <el-divider content-position="right">
          <span>选择实例编码</span>
          <span style="color: #acacac">&nbsp;(还没有实例配置? 点击<auto-computed-button :oc="slave.config.objectCode" @ok="autoComputedOk"></auto-computed-button>)</span>
        </el-divider>
        <div v-if="refresh">
          <el-form-item label="搜索面板" :prop="'slaves.' + index + '.instanceCodes.SearchView'" required error="必填">
            <drop-down-box v-model="slave.instanceCodes.SearchView" :data-url="getInstanceCodeOptionsUrl(slave, 'SearchView')" :filterable="true"></drop-down-box>
          </el-form-item>
          <el-form-item label="表格" :prop="'slaves.' + index + '.instanceCodes.TableView'" required error="必填">
            <drop-down-box v-model="slave.instanceCodes.TableView" :data-url="getInstanceCodeOptionsUrl(slave, 'TableView')" :filterable="true"></drop-down-box>
          </el-form-item>
          <el-form-item label="表单" :prop="'slaves.' + index + '.instanceCodes.FormView'" required error="必填">
            <drop-down-box v-model="slave.instanceCodes.FormView" :data-url="getInstanceCodeOptionsUrl(slave, 'FormView')" :filterable="true"></drop-down-box>
          </el-form-item>
        </div>
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
      refresh: true
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
    autoComputedOk() {
      this.refresh = false
      this.$nextTick(() => {
        this.refresh = true
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

    handleClick(tab, event) {
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
  mounted() {
    this.$nextTick(() => {
      this.$refs['featureConfigForm'].clearValidate()
    })
  },
  beforeDestroy() {
    this.$refs['featureConfigForm'].clearValidate()
  }
}
</script>

<style scoped>

</style>
