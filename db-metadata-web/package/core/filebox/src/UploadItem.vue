<template>
  <div class="upload-item">
    <el-upload
        v-bind="conf"
        :headers="headers"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleOnSuccess"
        :on-error="handleOnError"
        :before-remove="beforeRemove"
        :on-exceed="handleExceed"
        :before-upload="handleBeforeUpload"
        :file-list="nativeValue" :class="{__hide: hideUploadButton}">

      <div slot="default" class="upload-btn">
        <i class="seat-name" v-if="seat">请上传{{ seat }}：</i>
        <i class="icon el-icon-plus" v-if="!isView"></i>
      </div>
    </el-upload>
  </div>
</template>

<script>
import utils from "../../../utils";
import Meta from "../../mixins/meta";
import Val from "../../mixins/value";
import {appConfig} from "../../../config";
import Token from "../../../token";

/**
 * 入参如果是相对路径，则拼接前缀baseURL，以便代理命中
 * @param value 对象数组或对象
 * @returns {*[]}
 */
const conver = function (value) {
  let arr;
  if (utils.isArray(value)) {
    arr = value
  } else {
    arr = utils.isObject(value) ? [value] :[]
  }
  utils.assert(arr.every(f => utils.isObject(f) && !utils.isEmpty(f.url)), "数据错误, 格式必须满足[{url:''}] 或 {url:''}")

  return arr.map(f => {
    if (!utils.isExternal(f.url)) {
      f.url = utils.resolve(this.baseURL, f.url, true)
    }
    return f
  })
}

/**
 * 出参如果是相对路径，则移除前缀baseURL, 防止前缀被作为路径的一部分保存。
 * @param value 对象数组或对象
 * @returns {*|{}|{}}
 */
const reverse = function (value) {
  let arr;
  if (utils.isArray(value)) {
    arr = value
  } else {
    arr = utils.isObject(value) ? [value] :[]
  }
  utils.assert(arr.every(f => utils.isObject(f) && !utils.isEmpty(f.url)), "数据错误, 格式必须满足[{url:''}] 或 {url:''}")

  if (this.mode === 'default') { // 输出数组
    return arr
  } else { // seat模式，输出对象
    return value.length > 0 ? value[0] :[]
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
    mode: { // 模式: default/seat, default模式value入参为对象数组; seat模式value入参为对象
      type: String,
      default: () => 'default'
    }
  },
  data() {
    const header = {}
    header[appConfig.tokenKey] = Token.get()
    return {
      headers: header
    };
  },
  methods: {
    handleBeforeUpload(file) {
      return file;
    },
    handleRemove(file, fileList) {
      this.nativeValue = fileList
    },
    handlePreview(file) {
      window.open(file.url)
    },
    handleExceed(/*files, fileList*/) {
      const {conf: {limit}} = this
      this.$message.warning('文件数量超过设定值：' + limit);
    },
    beforeRemove(file/*, fileList*/) {
      let fileName = utils.isEmpty(file.name) ? file.url : file.name
      return this.$confirm(`确定移除 ${fileName}？`).then((/*data*/) => {
      });
    },
    handleOnError(err/*, file, fileList*/) {
      const {message = '上传失败'} = err
      this.$message.warning(message)
    },
    handleOnSuccess(response, file, fileList) {
      const {seat} = this
      const {state, code, message} = response
      if (state === 'ok' || code === 0) {
        this.$message.success('文件上传成功!');
        const {url, name, value} = response.data

        // 将后端url等值设置到file
        file.url = url
        file.name = name;
        file.value = value;
        file.seat = seat;

        this.nativeValue = fileList
      } else {
        this.$message.error('文件上传失败:' + message);
      }
    },
  },
  mounted() {
  },
  computed: {
    hideUploadButton() {
      return !utils.isEmpty(this.value) && this.mode !== 'default'
    },
    baseURL() {
      const {defaults: {baseURL} = {}} = this.$axios
      return baseURL
    },
    conf() {
      const {innerMeta: {conf = {}}, $attrs, mode, baseURL} = this
      const finalConf = this.$reverseMerge(conf, $attrs)

      const {action} = finalConf
      return this.$reverseMerge(finalConf, {
        action: utils.resolve(baseURL, action),
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
<style scoped lang="scss">
.upload-item {
  .upload-btn {
    height: 100%;
    line-height: 20px;
    padding: 10px;
    box-sizing: border-box;

    & > .seat-name {
      overflow: hidden;
    }
  }
}
</style>
