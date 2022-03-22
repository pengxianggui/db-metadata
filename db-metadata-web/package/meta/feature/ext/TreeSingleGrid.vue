<template>
  <el-form ref="featureConfigForm" :model="value" inline>
    <el-divider content-position="right">配置</el-divider>
    <el-form-item label="元对象" prop="config.objectCode" required error="必填">
      <drop-down-box v-model="value.config.objectCode" :data-url="getObjectCodeUrl(value)"
                     @change="resetRelate(value)"></drop-down-box>
    </el-form-item>
<!--    <el-form-item prop="config.idKey" required error="必填">-->
<!--      <template #label>-->
<!--        <span>主键标识</span>-->
<!--        <el-tooltip effect="dark" placement="top">-->
<!--          <div slot="content">Tree自身关联的主键</div>-->
<!--          <i class="el-icon-question"></i>-->
<!--        </el-tooltip>-->
<!--      </template>-->
<!--      <drop-down-box v-model="value.config.idKey" :data-url="getFieldCodeUrl(value)"></drop-down-box>-->
<!--    </el-form-item>-->
<!--    <el-form-item prop="config.pidKey" required error="必填">-->
<!--      <template #label>-->
<!--        <span>父节点标识</span>-->
<!--        <el-tooltip effect="dark" placement="top">-->
<!--          <div slot="content">Tree自身关联的外键</div>-->
<!--          <i class="el-icon-question"></i>-->
<!--        </el-tooltip>-->
<!--      </template>-->
<!--      <drop-down-box v-model="value.config.pidKey" :data-url="getFieldCodeUrl(value)"></drop-down-box>-->
<!--    </el-form-item>-->
<!--    <el-form-item prop="config.rootIdentify">-->
<!--      <template #label>-->
<!--        <span>根节点标识</span>-->
<!--        <el-tooltip effect="dark" placement="top">-->
<!--          <div slot="content">根节点标识, 为何值时表示根节点</div>-->
<!--          <i class="el-icon-question"></i>-->
<!--        </el-tooltip>-->
<!--      </template>-->
<!--      <text-box v-model="value.config.rootIdentify"></text-box>-->
<!--    </el-form-item>-->
<!--    <el-form-item prop="config.label" required error="必填">-->
<!--      <template #label>-->
<!--        <span>显示字段</span>-->
<!--        <el-tooltip effect="dark" placement="top">-->
<!--          <div slot="content">显示为树节点名的字段(当为树形态而非树表时, 这是有用的)</div>-->
<!--          <i class="el-icon-question"></i>-->
<!--        </el-tooltip>-->
<!--      </template>-->
<!--      <drop-down-box v-model="value.config.label" :data-url="getFieldCodeUrl(value)"></drop-down-box>-->
<!--    </el-form-item>-->
<!--    <el-form-item prop="config.isSync">-->
<!--      <template #label>-->
<!--        <span>是否异步加载树</span>-->
<!--        <el-tooltip effect="dark" placement="top">-->
<!--          <div slot="content">树节点展开是否异步加载</div>-->
<!--          <i class="el-icon-question"></i>-->
<!--        </el-tooltip>-->
<!--      </template>-->
<!--      <bool-box v-model="value.config.isSync"></bool-box>-->
<!--    </el-form-item>-->

    <el-divider content-position="right">
      <span>选择实例编码</span>
      <span style="color: #acacac">&nbsp;(还没有实例配置? 点击<auto-computed-button :oc="value.config.objectCode" @ok="autoComputedOk"></auto-computed-button>)</span>
    </el-divider>
    <div v-if="refresh">
      <el-form-item label="搜索面板" prop="instanceCodes.SearchView" required error="必填">
        <drop-down-box v-model="value.instanceCodes.SearchView" :data-url="getInstanceCodeOptionsUrl(value, 'SearchView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="树形表格" prop="instanceCodes.TableTreeView" required error="必填">
        <drop-down-box v-model="value.instanceCodes.TableTreeView"
                       :data-url="getInstanceCodeOptionsUrl(value, 'TableTreeView')"></drop-down-box>
      </el-form-item>
      <el-form-item label="表单" prop="instanceCodes.FormView" required error="必填">
        <drop-down-box v-model="value.instanceCodes.FormView" :data-url="getInstanceCodeOptionsUrl(value, 'FormView')"></drop-down-box>
      </el-form-item>
    </div>
  </el-form>
</template>

<script>
import AutoComputedButton from "./AutoComputedButton";
import {isEmpty} from "../../../utils/common";
import {restUrl} from "../../../constant/url";

export default {
  name: "TreeSingleGrid",
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
    resetRelate(item) {
      this.$reverseMerge(item, {
        config: {
          idKey: null,
          pidKey: null,
          rootIdentify: null,
          label: null,
          isSync: false
        },
        instanceCodes: {
          SearchView: null,
          TableTreeView: null,
          FormView: null
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
  }
}
</script>

<style scoped>

</style>
