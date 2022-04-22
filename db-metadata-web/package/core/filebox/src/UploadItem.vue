<template>
    <div>
        <el-upload
                :limit="1"
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
        <span>{{seat}}</span>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>

        <!-- TODO 提供下载按钮 -->
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
        return (!utils.isObject(value) ? [] : [value]);
    }

    const reverse = function (value) {
        if (utils.isArray(value)) {
          return value.length > 0 ? value[0] : {}
        }
        if (utils.isObject(value)) {
          return value
        }
        return {}
    }

    export default {
        name: "UploadItem",
        mixins: [Meta(), Val(conver, reverse)],
        props: {
            value: {
                type: Object
            },
            seat: {
                type: String,
                default: () => ''
            }
        },
        data() {
          const {value} = this
          const hideUploadButton = !utils.isEmpty(value)
          const header = {}
          header[appConfig.tokenKey] = Token.get()
            return {
                hideUploadButton: hideUploadButton,
                fileList: [],
                dialogImageUrl: '',
                dialogVisible: false,
                headers: header
            };
        },
        methods: {
            handleChange(file, fileList) {
              this.hideUploadButton = fileList.length >= 1
            },
            handleBeforeUpload(file) {
                return file;
            },
            handleRemove(file, fileList) {
              this.handleChange(file, fileList)
              this.nativeValue = []
            },
            handlePreview(file) {
                window.open(file.url)
            },
            handleExceed(files, fileList) {
                this.$message.warning('文件数量超过设定值：' + files.length);
            },
            beforeRemove(file, fileList) {
                let self = this;
                return this.$confirm(`确定移除 ${file.name}？`).then(data => {
                    self.nativeValue = self.nativeValue.filter(i => i.uid !== file.uid);
                });
            },
            handleOnSuccess(response, file, fileList) {
                const {seat} = this
                if (response.state === 'ok') {
                    this.$message.success('文件上传成功!');
                    const {url, name, value} = response.data

                    this.fileList = fileList
                    this.nativeValue = [{url: url, name: name, value: value, uid: file.uid, seat: seat}]
                } else {
                    this.$message.error('文件上传失败');
                }
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.fileList = utils.deepClone(this.nativeValue);
                // 兼容处理 axios配置了baseURL编辑预览失败问题
                const {$axios: {defaults: {baseURL} = {}} = {}} = this
                this.fileList.filter(item => !utils.isEmpty(item)).map(item => {
                    this.$reverseMerge(item, {
                      url: resolve(baseURL, item.url)
                    })
                    return item
                })
            })
        },
        computed: {
            conf() {
                const {innerMeta: {conf = {}}, $attrs} = this
                const finalConf = this.$reverseMerge(conf, $attrs)

                const {defaults: {baseURL} = {}} = this.$axios
                const {action} = finalConf
                return this.$reverseMerge(finalConf, {
                    action: resolve(baseURL, action)
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
