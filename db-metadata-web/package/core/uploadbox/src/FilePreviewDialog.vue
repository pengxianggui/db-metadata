<template>
  <el-dialog :visible.sync="visibleDialog" :append-to-body="true">
    <img width="100%" :src="url" alt="">
  </el-dialog>
</template>

<script>
/**
 * 文件预览组件。
 */
export default {
  name: "FilePreviewDialog",
  props: {
    mime: {
      type: String,
      default: '*/*'
    },
    name: String,
    url: String,
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
    }
  },
  computed: {
    visibleDialog: {
      get() {
        // TODO 此处只针对image/*类型做弹窗预览，其他类型一律window.open处理，浏览器会下载
        //    针对更多MIME内容支持预览
        if (this.visible === true && !this.mime.startsWith('image')) {
          window.open(this.url)
          return false
        }
        return this.visible
      },
      set(val) {
        this.$emit('update:visible', val)
      }
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
