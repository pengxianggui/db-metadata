<template>
    <div class="upload-item">
        <el-upload
                :limit="1"
                v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="handleOnSuccess"
                :before-remove="beforeRemove"
                :on-exceed="handleExceed"
                :before-upload="handleBeforeUpload"
                accept="image/*"
                :file-list="fileList">
            <!--            <img v-if="value && value.hasOwnProperty('url')" :src="value.url" class="avatar">-->
            <!--            <i v-else class="el-icon-plus"></i>-->
            <i class="el-icon-plus"></i>
        </el-upload>
        <p class="name">{{seat}}</p>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </div>
</template>

<script>
    import utils from "../../../utils";
    import Meta from "../../mixins/meta";
    import Val from "../../mixins/value";

    export default {
        name: "UploadItem",
        mixins: [Meta(), Val(function (value) {
            return (!utils.isObject(value) ? [] : [value]);
        }, function (value) {
            if (utils.isArray(value)) {
                return value.length > 0 ? value[0] : {}
            }
            if (utils.isObject(value)) {
                return value
            }
            return {}
        })],
        props: {
            value: {
                type: Object
            },
            seat: {
                type: String,
                default: () => 'DEFAULT'
            }
        },
        data() {
            return {
                fileList: [],
                dialogImageUrl: '',
                dialogVisible: false
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
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
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
            })
        }
    }
</script>

<style lang="scss" scoped>
    .avatar {
        width: 100%;
        height: 100%;
    }

    .upload-item {
        p.name {
            text-align: center;
        }
    }
</style>