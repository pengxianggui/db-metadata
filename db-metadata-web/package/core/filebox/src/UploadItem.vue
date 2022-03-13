<template>
    <div>
        <el-upload
                :limit="1"
                v-bind="conf"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="handleOnSuccess"
                :before-remove="beforeRemove"
                :on-exceed="handleExceed"
                :before-upload="handleBeforeUpload"
                :file-list="fileList">
            <i class="el-icon-plus"></i>
        </el-upload>
        <span>{{seat}}</span>
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
                default: () => ''
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
                return file;
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
        },
        computed: {
            conf() {
                const {meta: {conf = {}}, $attrs, $reverseMerge} = this
                return $reverseMerge(conf, $attrs)
            }
        }
    }
</script>

<style scoped>

</style>
