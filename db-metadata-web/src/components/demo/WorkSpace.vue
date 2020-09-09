<template>
  <div style="padding: 20px; overflow: auto; height: 100%;">
    <h1>组件开发调试页面</h1>
    <region-box type="cascader" v-model="value" :level="1"></region-box>
  </div>
</template>

<script>
export default {
  name: "WorkSpace",
  data() {
    return {
      value: ""
    }
  },
  methods: {},
  created() {
  }
}
</script>

<style scoped>
.ddd {
  font-size: 40px;
}
</style>
