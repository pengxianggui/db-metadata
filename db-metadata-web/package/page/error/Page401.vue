<template>
  <div>
    <h1>401: {{ msg }}: {{ url }}</h1>
    <router-link to="/">首页</router-link>
  </div>
</template>

<script>
export default {
  name: "Page401",
  data() {
    return {
      msg: '',
      url: ''
    }
  },
  created() {
    const {query: {msg, request_uri: url}} = this.$route
    this.msg = msg
    this.url = url
  }
}
</script>

<style scoped>

</style>
