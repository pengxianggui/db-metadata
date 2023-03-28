<template>
  <div style="padding: 50px">
    <drop-down-box v-model="value" multiple :options="options"></drop-down-box>
  </div>
</template>

<script>
export default {
  name: "WorkSpace",
  data() {
    return {
      value: null,
      options: [
        {value: 123424, key: '标签1'},
        {value: 223424, key: '标签2'},
      ]
    }
  },
  computed: {},
  methods: {
  }
}
</script>

<style scoped>
</style>
