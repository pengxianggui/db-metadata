<template>
  <el-button @click="click" icon="el-icon-full-screen" size="mini"></el-button>
</template>

<script>
import screenfull from 'screenfull'
import {isEmpty} from "../../../utils/common";

export default {
  name: "FullScreen",
  props: {
    // element 可直接传ref, 优先级高于id, 两者(element和id)只有一个生效
    element: {
      type: Element
    },
    id: {
      type: String
    }
  },
  data() {
    return {
      isFullScreen: false
    }
  },
  methods: {
    click() {
      if (!screenfull.isEnabled) {
        this.$message.warning("浏览器未支持, 请检查是否启用")
        return false
      } else {
        const {element, id} = this
        let target = element

        if (isEmpty(target)) {
          target = document.getElementById(id)
        }
        screenfull.toggle(target)
      }
    }
  }
}
</script>

<style scoped>
</style>
