<template>
  <div class="container" :style="style">
    <h2 class="title">{{ name }}</h2>
    <el-card>
      <h2 style="text-align: center">登录</h2>
      <form-view :meta="meta" :model="model" @submit="login" class="form" @cancel="register"></form-view>
      <!-- TODO 对appConfig.registerable的兑现 -->
    </el-card>
  </div>
</template>

<script>
import {restUrl} from "@/../package/constant/url";
import {appConfig} from "../../config";
import {isEmpty} from "../../utils/common";
import Token from "../../token";

export default {
  name: "Login",
  meta: {
    cn: '登录页面',
    buildIn: true // 内建：DbMeta提供
  },
  data() {
    const {loginBg} = appConfig
    let style = {}
    if (!isEmpty(loginBg)) {
      if (loginBg.startsWith('http')) {
        style['background-image'] = `url(${loginBg})`
      } else {
        style['background'] = `${loginBg}`
      }
    }
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
          },
          "label-width": '60px'
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
      },
      name: appConfig.name,
      style: style
    }
  },
  methods: {
    login(model) {
      this.$axios.safePost(restUrl.LOGIN_URL, model).then(({data}) => {
        Token.set(data.token)
        this.$router.push('/')
        location.reload()
      })
    },
    register(model) {
      this.$message.warning("NOT FINISHED")
    }
  }
}
</script>

<style scoped lang="scss">
.container {
  width: 100%;
  height: 100%;
  background-color: #2f4a75;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-repeat: no-repeat;
  background-size: cover;

  .title {
    color: white;
  }

  .el-card {
    width: 500px;
    margin: 0 auto;

    .form {
      width: 100% !important;
    }
  }
}
</style>
