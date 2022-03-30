<template>
    <div class="upload-item">
        <el-upload
                :limit="1"
                v-bind="conf"
                :headers="headers"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="handleOnSuccess"
                :before-remove="beforeRemove"
                :on-exceed="handleExceed"
                :before-upload="handleBeforeUpload"
                accept="image/*"
                :on-change="handleChange"
                :file-list="fileList" :class="{__hide: hideUploadButton}">
          <i class="el-icon-plus"></i>
        </el-upload>
        <p class="name">{{seat}}</p>
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
    import {getToken} from "../../../access";
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
            header[appConfig.tokenKey] = getToken()

            return {
                hideUploadButton: hideUploadButton,
                fileList: [],
                imageUrl: '',
                dialogVisible: false,
                headers: header
            };
        },
        methods: {
            handleChange(file, fileList) {
              this.hideUploadButton = fileList.length >= 1
            },
            handleBeforeUpload(file) {
                let isImage = utils.isImageFile(file);
                if (!isImage) {
                    this.$message.warning('只支持图片文件！');
                }
                return isImage;
            },
            handleRemove(file, fileList) {
              this.handleChange(file, fileList)
              this.nativeValue = []
            },
            handlePictureCardPreview(file) {
                this.imageUrl = file.url;
                this.dialogVisible = true;
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

                    this.imageUrl = file.url;
                    this.fileList = fileList
                    this.nativeValue = [{url: url, name: name, value: value, uid: file.uid, seat: seat}]
                } else {
                    this.$message.error('文件上传失败');
                }
            }
        },
        mounted() {
            this.$nextTick(() => {
                // 兼容处理 axios配置了baseURL编辑预览失败问题
                this.fileList = utils.deepClone(this.nativeValue);
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

<style lang="scss">
  .upload-item > .__hide .el-upload--picture-card{
    display: none;
  }
</style>
<style lang="scss" scoped>
    .upload-item {
        p.name {
          margin: 0;
          text-align: left;
          color: #666666;
        }
    }
</style>
