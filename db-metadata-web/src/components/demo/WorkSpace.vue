<template>
  <div style="padding: 20px; overflow: auto; height: 100%;">
    <h1>组件开发调试页面</h1>
    <ui-conf-editor v-model="mergedValue"></ui-conf-editor>
  </div>
</template>

<script>
import {defaultMeta} from "@/../package/core";
import UiConfEditor from "../../../package/meta/component/UiConfEditor";
export default {
  name: "WorkSpace",
  components: {UiConfEditor},
  data() {
    return {
      value: {
        component_name: 'TableView',
        name: 'test',
        label: 'test'
      }
    }
  },
  computed: {
    mergedValue() {
      return this.$merge(this.value, defaultMeta[this.value.component_name])
    }
  },
  methods: {},
  created() {
  }
}
</script>

<style scoped>
</style>
