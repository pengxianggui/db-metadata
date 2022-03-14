<template>
  <span>
    <slot v-bind:click="toAuto">
      <el-link type="primary" @click="toAuto">一键配置</el-link>
    </slot>
    <dialog-box :visible.sync="visible" title="一键生成UI配置">
      <auto-computed-one-key-config :object-code="oc" @ok="visible=false" @cancel="visible=false"></auto-computed-one-key-config>
      <template #footer><span></span></template>
    </dialog-box>
  </span>
</template>

<script>
import {isEmpty} from "../../../utils/common";
import AutoComputedOneKeyConfig from "./AutoComputedOneKeyConfig";

export default {
  name: "AutoComputedButton",
  components: {AutoComputedOneKeyConfig},
  props: {
    oc: String
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    toAuto() {
      if (isEmpty(this.oc)) {
        this.$message.warning('请先选择元对象!')
        return
      }
      this.visible = true
    }
  }
}
</script>

<style scoped>

</style>
