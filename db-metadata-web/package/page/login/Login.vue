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
import {restUrl, routeUrl} from "@/../package/constant/url";
import {appConfig} from "../../config";
import {isEmpty} from "../../utils/common";
import Token from "../../token";
import {utils} from "../../index";

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
      if (loginBg.startsWith('http') || loginBg.startsWith('.')) {
        style['background-image'] = `url(${loginBg})`
      } else if (loginBg.startsWith('/')) {
        const {defaults: {baseURL} = {}} = this.$axios
        style['background-image'] = `url(${baseURL}${loginBg})`
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
      let formData = {}
      formData[appConfig.loginKey] = model.username
      formData[appConfig.pwdKey] = model.password

      this.$axios.safePost(restUrl.LOGIN_URL, formData).then(({data}) => {
        Token.set(data.token)
        this.$router.push(this.redirectUrl).then(() => {
          location.reload()
        })
      })
    },
    register(model) {
      this.$message.warning("NOT FINISHED")
    }
  },
  computed: {
    redirectUrl() {
      const {query: {redirect_url}} = this.$route
      return utils.assertEmpty(redirect_url, routeUrl.R_INDEX)
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
    background-color: rgba(255,255,255, 0.8);

    .form {
      width: 100% !important;
    }
  }
}
</style>
