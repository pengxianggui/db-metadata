<template>
  <div class="err-page-container">
    <div>
      <el-button icon="el-icon-arrow-left" class="pan-back-btn" @click="back">返回</el-button>
      <div v-if="msg">
        <p class="text-jumbo">{{ msg }}</p>
        <p v-if="request_uri">资源地址: {{ request_uri }}</p>
      </div>
      <h1 class="text-jumbo" v-else>无权限</h1>
      <p>或者你可以去:</p>
      <ul class="list-unstyled">
        <li class="link-type">
          <router-link to="/">回首页</router-link>
        </li>
        <li><a href="#" @click="logout">换个账号登录</a></li>
      </ul>
    </div>
    <div>
      <svg-icon value="401" style="font-size: 450px;"></svg-icon>
    </div>
  </div>
</template>

<script>
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
      request_uri: request_uri
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
.err-page-container {
  width: 1000px;
  max-width: 100%;
  margin: auto;
  top: 100px;
  position: relative;
  display: flex;
  justify-content: center;

  .pan-back-btn {
    background: #008489;
    color: #fff;
    border: none !important;
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
