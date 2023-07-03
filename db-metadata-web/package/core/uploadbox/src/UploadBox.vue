<template>
  <div class="upload-item">
    <el-upload
        :action="conf.action"
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
      <slot v-bind:seat="seat" v-bind:isView="isView">
      </slot>
    </el-upload>
    <file-preview-dialog :mime="conf.accept"
                         :visible.sync="dialogVisible"
                         :url="imageUrl"></file-preview-dialog>
  </div>
</template>

<script>
import FilePreviewDialog from "./FilePreviewDialog.vue";
import utils from "../../../utils";
import Meta from "../../mixins/meta";
import Val from "../../mixins/value";
import {appConfig} from "../../../config";
import Token from "../../../token";
import conver from "./conver";
import reverse from "./reverse";

export default {
  name: "UploadBox",
  mixins: [Meta(), Val(conver, reverse)],
  inject: {
    isView: {default: false}
  },
  components: { FilePreviewDialog },
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
      imageUrl: '',
      dialogVisible: false,
      headers: header
    };
  },
  methods: {
    handleBeforeUpload(file) {
      this.$emit('before-upload', file)
    },
    handleRemove(file, fileList) {
      this.nativeValue = fileList
    },
    handlePreview(file) {
      this.imageUrl = file.url;
      this.dialogVisible = true
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
    handleDownload(file) {
      window.open(file.url)
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

        this.imageUrl = file.url;
        this.nativeValue = fileList
      } else {
        this.$message.error('文件上传失败: ' + message);
      }
    }
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
        action: utils.resolve(baseURL, action),
        limit: mode === 'default' ? finalConf.limit : 1 // default模式依据配置决定limit，非default模式(即seat模式)则限制只能传1个文件
      })
    },
    // 是否隐藏上传按钮：1. 表单查看时(isView); 2. seat模式下此seat有值时; 3. 当前文件数量>=limit限制时
    // 满足上述任一条件时返回true
    hideUploadButton() {
      const isView = this.isView // 表单查看时(isView);

      const notEmptyWhenSeat = (!utils.isEmpty(this.value) && this.mode !== 'default'); // seat模式下此seat有值时

      const { conf: { limit }, nativeValue } = this
      const isLeLimit = nativeValue.length >= limit // 当前文件数量>=limit限制时

      return notEmptyWhenSeat || isView || isLeLimit
    },
  }
}
</script>

<style lang="scss">
.upload-item > .__hide .el-upload {
  display: none;
}
</style>
<style lang="scss" scoped>
.upload-item {
  .upload-btn {
    height: 100%;
    line-height: 20px;
    padding: 10px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;

    & > .seat-name {
      overflow: hidden;
      font-size: 12px;
    }
  }
}
</style>
