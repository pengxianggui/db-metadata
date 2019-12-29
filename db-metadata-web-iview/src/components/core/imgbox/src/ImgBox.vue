<template>
    <div>
        <el-upload
            v-bind="$reverseMerge(innerMeta.conf, $attrs)"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :on-success="handleOnSuccess"
            :before-remove="beforeRemove"
            :on-exceed="handleExceed"
            :before-upload="handleBeforeUpload"
            accept="image/*"
            :file-list="fileList">
            <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </div>
</template>
<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import conver from './conver'

    export default {
        mixins: [Meta(DEFAULT.ImgBox), Val(conver)],
        name: 'ImgBox',
        label: '图片上传框',
        props: {
            value: {
                type: [Array, String],  // String could be convert to Array
                default: function () {
                    return []
                },
                validator: function (val) {
                    if (utils.isArray(val)) {
                        return val.every(item => item.hasOwnProperty('name') && item.hasOwnProperty('url')) // return true when val.length = 0
                    }
                    if (utils.isString(val)) {
                        let arrVal = utils.convertToArray(val);
                        return arrVal ? (arrVal.every(item => item.hasOwnProperty('name') && item.hasOwnProperty('url'))) : false;
                    }
                    return false;
                }
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
                    let delIndex = -1;
                    for (let i = 0; i < self.nativeValue.length; i++) {
                        if (self.nativeValue[i].url === file.url) {
                            delIndex = i;
                            break;
                        }
                    }
                    self.nativeValue.splice(delIndex, 1);
                });
            },
            handleOnSuccess(response, file, fileList) {
                if (response.state === 'ok') {
                    this.$message.success('文件上传成功!');
                    file.url = response.data.url; // 设置唯一标示
                    this.nativeValue.push(response.data);
                } else {
                    this.$message.error('文件上传失败');
                }
            }
        },
        mounted() {
            this.fileList = utils.deepClone(this.nativeValue); // TODO 其中的url键值应当为图片资源路径, 否则编辑时无法呈现并预览图片
        }
    }
</script>
