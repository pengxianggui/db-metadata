<template>
  <div class="md_page-container" v-if="loaded">
    <!-- 表单实例UI编辑器 -->
    <form-builder :ic="ic" v-if="componentCode === 'FormView'" :oc="objectCode"></form-builder>
    <!-- 通用的实例UI编辑器 -->
    <common-instance-editor :ic="ic" :oc="objectCode" :fc="fc" :cc="componentCode" v-else></common-instance-editor>
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
    fc: { // fieldCode
      type: String,
      required: false
    }
  },
  data() {
    return {
      componentCode: null,
      objectCode: null,
      loaded: false
    }
  },
  created() {
    this.$axios.get(this.$compile(restUrl.COMPONENT_INSTANCE_INFO, {
      instanceCode: this.ic
    })).then((resp) => {
      const {comp_code: componentCode, object_code: objectCode} = resp.data
      this.componentCode = componentCode
      this.objectCode = objectCode
      this.loaded = true // 加载完毕
    })
  }
}
</script>

<style scoped>

</style>
