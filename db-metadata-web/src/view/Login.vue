<template>
  <div class="el-card container-view">
    <h2 style="text-align: center">登录</h2>
    <form-view :meta="meta" :model="model" @submit="login"></form-view>
  </div>
</template>

<script>
import {restUrl} from "@/../package/constant/url";

export default {
  name: "Login",
  data() {
    return {
      model: {
        username: null,
        password: null
      },
      meta: {
        label: '登录表单',
        conf: {
          rules: {
            "username": [{required: true, message: '账号必填', trigger: 'blur'}],
            "password": [{required: true, message: '密码必填', trigger: 'blur'}],
          }
        },
        columns: [
          {
            name: 'username',
            label: '账号',
            component_name: 'TextBox'
          }, {
            name: 'password',
            label: '密码',
            component_name: 'PassBox'
          }
        ],
        buttons: {
          submit: {
            label: '登录'
          },
          cancel: {
            label: '注册'
          }
        }
      }
    }
  },
  methods: {
    login(model) {
      this.$axios.safePost(restUrl.LOGIN_URL, model).then(({data}) => {
        console.log(data)
        sessionStorage.setItem("X-TOKEN", data.userId)
        this.$router.push('/')
      })
    }
  }
}
</script>

<style scoped>

</style>
