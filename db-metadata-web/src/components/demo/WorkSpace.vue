<template>
  <div>
    <auto-computed-one-key-config object-code="test1"></auto-computed-one-key-config>
  </div>
</template>

<script>

import AutoComputedOneKeyConfig from "../../../package/meta/meta-data-manager/ext/AutoComputedOneKeyConfig";

export default {
  name: "WorkSpace",
  components: {
    AutoComputedOneKeyConfig
  },
  data() {
    return {
      value: []
    }
  },
  computed: {},
  methods: {},
  created() {
  }
}
</script>

<style scoped>
</style>
