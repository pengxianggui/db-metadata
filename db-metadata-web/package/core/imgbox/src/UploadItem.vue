<template>
  <div class="upload-item">
    <el-upload
        v-bind="conf"
        :headers="headers"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove"
        :on-error="handleOnError"
        :on-success="handleOnSuccess"
        :before-remove="beforeRemove"
        :on-exceed="handleExceed"
        :before-upload="handleBeforeUpload"
        accept="image/*"
        :file-list="nativeValue" :class="{__hide: hideUploadButton}">
      <div slot="default" class="upload-btn">
        <i class="icon el-icon-plus"></i>
        <el-tooltip :content="'请上传' + seat" :disabled="seat.length <= 6" v-if="seat">
          <i class="seat-name">请上传{{ seat | subStr(6) }}</i>
        </el-tooltip>
      </div>
      <div slot="file" slot-scope="{file}" v-if="isView">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
        <span class="el-upload-list__item-actions">
              <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                <i class="el-icon-zoom-in"></i>
              </span>
              <span class="el-upload-list__item-delete" @click="handleDownload(file)">
                <i class="el-icon-download"></i>
              </span>
            </span>
      </div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" :append-to-body="true">
      <img width="100%" :src="imageUrl" alt="">
    </el-dialog>
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
  return (utils.isArray(value) ? value : [value])
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
      let isImage = utils.isImageFile(file);
      if (!isImage) {
        this.$message.warning('只支持图片文件！');
      }
      return isImage;
    },
    handleRemove(file, fileList) {
      this.nativeValue = fileList
    },
    handlePictureCardPreview(file) {
      const url = utils.isExternal(file.url) ? file.url : file.url.substring(this.baseURL.length)
      this.imageUrl = url;
      this.dialogVisible = true;
    },
    handleExceed(/*files, fileList*/) {
      const {conf: {limit}} = this
      this.$message.warning('文件数量超过设定值：' + limit);
    },
    beforeRemove(file/*, fileList*/) {
      let fileName = utils.isEmpty(file.name) ? file.url : file.name
      return this.$confirm(`确定移除 ${fileName}？`).then(data => {
      });
    },
    handleDownload(file) {
      const url = utils.isExternal(file.url) ? file.url : file.url.substring(this.baseURL.length)
      window.open(url)
    },
    handleOnError(err/*, file, fileList*/) {
      const {message = '上传失败'} = err
      this.$message.warning(message)
    },
    handleOnSuccess(response, file, fileList) {
      const {seat} = this
      const {state, code, message} = response
      if (state === 'ok' || code == 0) {
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
        action: resolve(baseURL, action),
        limit: mode === 'default' ? finalConf.limit : 1 // default模式依据配置决定limit，非default模式(即seat模式)则限制只能传1个文件
      })
    }
  }
}
</script>

<style lang="scss">
.upload-item > .__hide .el-upload--picture-card {
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
