<!--<template>-->
<!--  <div style="padding: 50px">-->
<!--  </div>-->
<!--</template>-->

<script>
import {strToFn} from "../../../package/utils/common";
import utils from "../../../package/utils";

export default {
  name: "WorkSpace",
  data() {
    return {
      value: '/file/preview?path=t_employee%5Cavatar%5CQQ%E5%9B%BE%E7%89%8720230629153023_20230703_11_16_10_257.jpg',
      fnStr: `
        function (h, value) {
          if (value && value.length > 0) {
              let url = this.isExternal(value) ? value : '/api' + value;
              return h('el-link', {
                  attrs: {
                      href: url
                  }
              }, '点击下载')
          }
          return h('span', value)
        }
      `
    }
  },
  render(h) {
    return this.fn.call(utils, h, this.value);
  },
  computed: {
    fn() {
      return strToFn(this.fnStr)
    }
  },
  methods: {}
}
</script>

<style scoped>
</style>
