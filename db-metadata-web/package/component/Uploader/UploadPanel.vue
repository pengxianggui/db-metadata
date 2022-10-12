<template>
  <div>
    <div class="el-upload__tip">
      <slot name="alert" class="el-upload__tip"></slot>
    </div>
    <br>
    <el-upload
        ref="upload"
        :accept="accept"
        :headers="headers"
        :limit="limit"
        class="upload-panel"
        :action="action"
        :auto-upload="false"
        :on-exceed="exceedHandle"
        :on-progress="progressHandle"
        :on-success="successHandle"
        :on-error="errorHandle"
        multiple>
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submit">上传到服务器</el-button>
    </el-upload>
  </div>
</template>

<script>
import Token from '../../token'
import {appConfig} from "../../config";
import utils from '../../utils'

export default {
  name: "UploadPanel",
  props: {
    url: {
      type: String,
      default: () => "/file/upload"
    },
    accept: {
      type: String,
      default: () => "*/*"
    },
    limit: {
      type: Number,
      default: () => 1
    }
  },
  computed: {
    action() {
      const {url} = this
      if (utils.isExternal(url)) {
        return url
      }
      return this.$axios.defaults.baseURL + url
    },
    headers() {
      const headers = {}
      const tokenKey = appConfig.tokenKey
      const tokenValue = Token.get()
      headers[tokenKey] = tokenValue
      return headers
    }
  },
  data() {
    return {
      loadingInstance: null
    }
  },
  methods: {
    submit() {
      this.$refs['upload'].submit()
    },
    successHandle(res) {
      const {code, state, message} = res
      if (code == 0 || state == 'ok') {
        this.$notify({
          title: '操作成功',
          message: message,
          dangerouslyUseHTMLString: true,
          type: 'success',
          duration: 5000,
          position: 'top-right'
        })
        this.$emit('success')
      } else {
        this.$message.error(message)
        this.$emit('fail')
      }
      this.loadingInstance.close() // 关闭加载遮罩
    },
    errorHandle() {
      this.loadingInstance.close() // 关闭加载遮罩
    },
    exceedHandle() {
      this.$message.warning(`文件个数限制为${this.limit}`)
    },
    progressHandle() {
      this.loadingInstance = this.$loading({
        lock: true,
        text: '导入中, 请勿刷新页面, 并耐心等待..',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    }
  }
}
</script>

<style scoped lang="scss">
.upload-panel {
  width: 100%;
}
</style>
