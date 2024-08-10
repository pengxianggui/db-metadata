<template>
  <div style="padding: 50px">
    <form-view ic="stock_log.FormView"></form-view>
  </div>
</template>

<script>
import utils from "../../../package/utils";

export default {
  name: "WorkSpace",
  data() {
    return {
    }
  },
  render(h) {
    return this.fn.call(utils, h, this.value);
  },
  computed: {
  },
  methods: {}
}
</script>

<style scoped>
</style>
