<template>
  <div class="md_page-container">
    <div>
      <svg-icon value="404" style="font-size: 450px"></svg-icon>
    </div>
    <div class="bullshit">
      <div class="bullshit__oops">OOPS!</div>
      <div class="bullshit__info">页面找不到了</div>
      <div class="bullshit__info">请检查您的url地址，或者:</div>
      <div class="buttons">
        <a @click="back" style="background-color:#4aa75d;">返回</a>
        <a href="/">去主页</a>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Page404',
  meta: {
    type: 'page',
    cn: '404页面',
    buildIn: true // 内建：DbMeta提供
  },
  methods: {
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
.md_page-container {
  width: 1000px;
  max-width: 100%;
  margin: auto;
  top: 100px;
  position: relative;
  display: flex;
  align-items: center;

  .bullshit {
    position: relative;
    float: left;
    width: 300px;
    padding: 30px 0;
    overflow: hidden;

    &__oops {
      font-size: 32px;
      font-weight: bold;
      line-height: 40px;
      color: #1482f0;
      opacity: 0;
      margin-bottom: 20px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-fill-mode: forwards;
    }

    &__headline {
      font-size: 20px;
      line-height: 24px;
      color: #222;
      font-weight: bold;
      opacity: 0;
      margin-bottom: 10px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.1s;
      animation-fill-mode: forwards;
    }

    &__info {
      font-size: 13px;
      line-height: 21px;
      color: grey;
      opacity: 0;
      margin-bottom: 30px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.2s;
      animation-fill-mode: forwards;
    }

    .buttons {
      display: flex;

      a {
        display: block;
        //float: left;
        width: 110px;
        height: 36px;
        background: #1482f0;
        border-radius: 100px;
        text-align: center;
        color: #ffffff;
        opacity: 0;
        font-size: 14px;
        line-height: 36px;
        cursor: pointer;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.3s;
        animation-fill-mode: forwards;
      }

      @keyframes slideUp {
        0% {
          transform: translateY(60px);
          opacity: 0;
        }
        100% {
          transform: translateY(0);
          opacity: 1;
        }
      }
    }
  }
}
</style>
