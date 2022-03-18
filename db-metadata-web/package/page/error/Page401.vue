<template>
  <div class="errPage-container">
    <el-button icon="el-icon-arrow-left" class="pan-back-btn" @click="back">
      返回
    </el-button>
    <el-row>
      <el-col :span="12">
        <div v-if="msg">
          <p class="text-jumbo">{{ msg }}</p>
          <p v-if="request_uri">资源地址: {{ request_uri }}</p>
        </div>
        <h class="text-jumbo" v-else>无权限</h>
        <p>或者你可以去:</p>
        <ul class="list-unstyled">
          <li class="link-type">
            <router-link to="/">
              回首页
            </router-link>
          </li>
          <li><a href="#" @click="logout">换个账号登录</a></li>
        </ul>
      </el-col>
      <el-col :span="12">
        <img :src="errGif" width="313" height="428" alt="Girl has dropped her ice cream.">
      </el-col>
    </el-row>
  </div>
</template>

<script>
import errGif from '../../asserts/401.gif'
import {restUrl, routeUrl} from "../../constant/url";
import {clearUser} from "../../access";

export default {
  name: 'Page401',
  meta: {
    type: 'page',
    cn: '401页面',
    buildIn: true // 内建：DbMeta提供
  },
  data() {
    const {query: {msg = '', request_uri}} = this.$route
    return {
      msg: msg,
      request_uri: request_uri,
      errGif: errGif + '?' + +new Date()
    }
  },
  methods: {
    logout() {
      this.$axios.safePost(restUrl.LOGOUT_URL).then(() => {
        clearUser()
        this.$router.push(routeUrl.R_LOGIN)
      })
    },
    back() {
      if (this.$route.query.noGoBack) {
        this.$router.push({path: '/dashboard'})
      } else {
        this.$router.go(-1)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.errPage-container {
  width: 800px;
  max-width: 100%;
  margin: 100px auto;

  .pan-back-btn {
    background: #008489;
    color: #fff;
    border: none !important;
  }

  .pan-gif {
    margin: 0 auto;
    display: block;
  }

  .pan-img {
    display: block;
    margin: 0 auto;
    width: 100%;
  }

  .text-jumbo {
    font-size: 60px;
    font-weight: 700;
    color: #484848;
  }

  .list-unstyled {
    font-size: 14px;

    li {
      padding-bottom: 5px;
    }

    a {
      color: #008489;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
