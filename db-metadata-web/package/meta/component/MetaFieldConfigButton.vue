<template>
  <div>
    <slot v-bind:open="openLogicConf">
      <el-button @click="openLogicConf()">元字段逻辑配置</el-button>
    </slot>
    <dialog-box :visible.sync="logicConf.dialogShow" :title="'编辑元字段配置:' + fieldCode">
      <field-conf @callback="logicConf.dialogShow = false" :object-code="logicConf.objectCode"
                  :field-code="logicConf.fieldCode"></field-conf>
      <template #footer><span></span></template>  <!-- 表单自带button条 -->
    </dialog-box>
  </div>
</template>

<script>
import FieldConf from "../instance-component/ext/FieldConf";
import {isEmpty} from "../../utils/common";

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
      if (isEmpty(objectCode) || isEmpty(fieldCode)) {
        this.$message.error('必须指定元对象编码和元字段编码')
        return
      }
      this.logicConf.objectCode = objectCode;
      this.logicConf.fieldCode = fieldCode;
      this.logicConf.dialogShow = true;
    }
  }
}
</script>

<style scoped>

</style>
