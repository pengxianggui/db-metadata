<template>
  <div class="page-container" v-if="loaded">
    <!-- 表单实例UI编辑器 -->
    <form-builder :ic="ic" v-if="componentCode === 'FormView'"></form-builder>
    <!-- 通用的实例UI编辑器 -->
    <common-instance-editor :ic="ic" :ic-name="icName" :oc="objectCode" :fc="fc" v-else></common-instance-editor>
  </div>
</template>

<script>
import {restUrl} from "../../constant/url";
import FormBuilder from "../form-builder/FormBuilder";
import CommonInstanceEditor from "./CommonInstanceEditor";

export default {
  name: "InstanceConfEditor",
  components: {FormBuilder, CommonInstanceEditor},
  props: {
    ic: {
      type: String,
      required: true
    },
    fc: {
      type: String,
      required: false
    }
  },
  data() {
    return {
      componentCode: null,
      objectCode: null,
      icName: null,
      loaded: false
    }
  },
  created() {
    this.$axios.get(this.$compile(restUrl.COMPONENT_INSTANCE_INFO, {
      instanceCode: this.ic
    })).then((resp) => {
      const {comp_code: componentCode, object_code: objectCode, name: icName} = resp.data
      this.componentCode = componentCode
      this.objectCode = objectCode
      this.icName = icName
      this.loaded = true // 加载完毕
    })
  }
}
</script>

<style scoped>

</style>
