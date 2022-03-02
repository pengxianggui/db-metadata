<template>
  <el-checkbox-group v-model="nativeValue" size="mini">
    <el-checkbox-button v-for="key in fields" :label="key" :key="key"></el-checkbox-button>
  </el-checkbox-group>
</template>

<script>

import Val from "../../core/mixins/value";
export default {
  name: "FieldFilter",
  mixins: [Val()],
  props: {
    fields: {
      type: Array,
      required: true
    },
    value: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>

</style>
