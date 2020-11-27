<template>
  <div>
    <slot v-bind:open="openLogicConf">
      <el-button @click="openLogicConf()">元字段逻辑配置</el-button>
    </slot>
    <dialog-box :visible.sync="logicConf.dialogShow" :title="'编辑逻辑配置:' + fieldCode">
      <field-conf @callback="logicConf.dialogShow = false" :object-code="logicConf.objectCode"
                  :field-code="logicConf.fieldCode"></field-conf>
      <template #footer><span></span></template>  <!-- 表单自带button条 -->
    </dialog-box>
  </div>
</template>

<script>
import FieldConf from "../instance-component/ext/FieldConf";

export default {
  name: "MetaFieldConfigButton",
  components: {FieldConf},
  props: {
    objectCode: String,
    fieldCode: String
  },
  data() {
    return {
      logicConf: {
        objectCode: null,
        fieldCode: null,
        dialogShow: false,
      }
    }
  },
  methods: {
    openLogicConf() {
      const {objectCode, fieldCode} = this
      this.logicConf.objectCode = objectCode;
      this.logicConf.fieldCode = fieldCode;
      this.logicConf.dialogShow = true;
    }
  }
}
</script>

<style scoped>

</style>