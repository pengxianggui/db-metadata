<template>
  <div class="upload-item">
    <el-upload
        v-bind="conf"
        :headers="headers"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleOnSuccess"
        :before-remove="beforeRemove"
        :on-exceed="handleExceed"
        :before-upload="handleBeforeUpload"
        :file-list="fileList" :class="{__hide: hideUploadButton}">
      <i class="el-icon-plus"></i>
    </el-upload>
    <span>{{ seat }}</span>
  </div>
</template>

<script>
import utils from "../../../utils";
import Meta from "../../mixins/meta";
import Val from "../../mixins/value";
import {appConfig} from "../../../config";
import Token from "../../../token";
import {resolve} from "../../../utils/url";

const conver = function (value) {
  if (utils.isArray(value)) {
    return value
  }
  return [value];
}

const reverse = function (value) {
  if (this.mode === 'default') { // 输出数组
    return value
  } else {
    if (utils.isArray(value)) {
      return value.length > 0 ? value[0] : {}
    }
    if (utils.isObject(value)) {
      return value
    }
    return {}
  }
}

export default {
  name: "UploadItem",
  mixins: [Meta(), Val(conver, reverse)],
  inject: {
    isView: {default: false}
  },
  props: {
    value: {
      type: [Object, Array]
    },
    seat: {
      type: String,
      default: () => ''
    },
    mode: {
      type: String,
      default: () => 'default'
    }
  },
  data() {
    const {value} = this
    const hideUploadButton = !utils.isEmpty(value)
    const header = {}
    header[appConfig.tokenKey] = Token.get()
    return {
      hideUploadButton: hideUploadButton && this.mode !== 'default',
      fileList: [],
      headers: header
    };
  },
  methods: {
    handleChange(file, fileList) {
    },
    handleBeforeUpload(file) {
      return file;
    },
    handleRemove(file, fileList) {
      this.handleChange(file, fileList)
      this.fileList = fileList
    },
    handlePreview(file) {
      window.open(file.url)
    },
    handleExceed(files, fileList) {
      const {conf: {limit}} = this
      this.$message.warning('文件数量超过设定值：' + limit);
    },
    beforeRemove(file, fileList) {
      let fileName = utils.isEmpty(file.name) ? file.url : file.name
      return this.$confirm(`确定移除 ${fileName}？`).then(data => {});
    },
    handleOnSuccess(response, file, fileList) {
      const {seat} = this
      if (response.state === 'ok') {
        this.$message.success('文件上传成功!');
        const {url, name, value} = response.data

        // 将后端url等值设置到file
        file.url = resolve(this.baseURL, url);
        file.name = name;
        file.value = value;
        file.seat = seat;

        this.imageUrl = file.url;
        this.fileList = fileList
      } else {
        this.$message.error('文件上传失败');
      }
    }
  },
  watch: {
    fileList: function (newV) { // 依据fileList 获取 nativeValue
      this.$nextTick(() => {
        if (utils.isEmpty(newV)) {
          this.nativeValue = []
        } else {
          this.nativeValue = newV.filter(f => !utils.isEmpty(f)).map(f => {
            let url = f.url.substring(this.baseURL.length)
            return {name: f.name, value: f.value, url: url}
          })
        }
      })

      if (this.mode === 'default') {
        const {conf: {limit}} = this
        this.hideUploadButton = (newV.length >= limit)
      } else {
        this.hideUploadButton = (newV.length >= 1) // seat模式下只能传1个，因此在已经有文件时需要隐藏上传按钮
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      // 兼容处理 axios配置了baseURL编辑预览失败问题

      this.fileList = utils.assertEmpty(this.nativeValue, [])
          .filter(item => !utils.isEmpty(item)).map(item => {
            this.$reverseMerge(item, {
              url: resolve(this.baseURL, item.url)
            })
            return item
          })
    })
  },
  computed: {
    baseURL() {
      const {defaults: {baseURL} = {}} = this.$axios
      return baseURL
    },
    conf() {
      const {innerMeta: {conf = {}}, $attrs, mode, baseURL} = this
      const finalConf = this.$reverseMerge(conf, $attrs)

      const {action} = finalConf
      return this.$reverseMerge(finalConf, {
        action: resolve(baseURL, action),
        limit: mode === 'default' ? finalConf.limit : 1 // default模式依据配置决定limit，非default模式(即seat模式)则限制只能传1个文件
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.upload-item {
  ::v-deep {
    .__hide .el-upload--text {
      display: none;
    }
  }
}
</style>
<style scoped>

</style>
