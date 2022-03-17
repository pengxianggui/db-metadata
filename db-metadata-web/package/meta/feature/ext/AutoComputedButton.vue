<template>
  <span>
    <slot v-bind:click="toAuto">
      <el-link type="primary" @click="toAuto">一键配置</el-link>
    </slot>
    <dialog-box :visible.sync="visible" title="一键生成UI配置">
      <auto-computed-one-key-config :oc="oc" @ok="ok" @cancel="cancel"></auto-computed-one-key-config>
      <template #footer><span></span></template>
    </dialog-box>
  </span>
</template>

<script>
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
      this.visible = true
    },
    ok() {
      this.visible = false
      this.$emit('ok')
    },
    cancel() {
      this.visible = false
      this.$emit('cancel')
    }
  }
}
</script>

<style scoped>

</style>
