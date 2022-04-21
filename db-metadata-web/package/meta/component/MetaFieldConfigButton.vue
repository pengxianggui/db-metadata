<template>
  <div>
    <slot v-bind:open="openLogicConf">
      <el-button @click="openLogicConf()">元字段逻辑配置</el-button>
    </slot>

    <dialog-box :visible.sync="dialogVisible" :meta="dialogMeta" :component-meta="dialogComponentMea" width="70%"
                @ok="dialogVisible=false" @cancel="dialogVisible=false">
      <template #default>
        <slot name="dialog-body" v-bind:meta="dialogComponentMea"></slot>
      </template>
      <template #footer><span></span></template>  <!-- 表单自带button条 -->
    </dialog-box>
  </div>
</template>

<script>
import FieldConf from "../instance-component/ext/FieldConf";
import {isEmpty} from "../../utils/common";
import {restUrl} from "../../constant/url";

export default {
  name: "MetaFieldConfigButton",
  components: {FieldConf},
  props: {
    objectCode: String,
    fieldCode: String
  },
  data() {
    return {
      dialogComponentMea: {}, // 弹窗内包含的组件元对象
      dialogMeta: {}, // 弹窗组件元对象
      dialogVisible: false   // 弹窗显隐
    }
  },
  methods: {
    openLogicConf() {
      const {objectCode, fieldCode} = this
      if (isEmpty(objectCode) || isEmpty(fieldCode)) {
        this.$message.error('必须指定元对象编码和元字段编码')
        return
      }

      let url = this.$compile(restUrl.META_FIELD_TO_EDIT, {
        objectCode: objectCode,
        fieldCode: fieldCode
      });
      this.$axios.get(url).then(resp => {
        this.dialogComponentMea = resp.data;
        this.dialogMeta = {
          component_name: "DialogBox",
          conf: {
            title: "编辑元字段:" + fieldCode
          }
        };
        this.dialogVisible = true
      });
    }
  }
}
</script>

<style scoped>

</style>
