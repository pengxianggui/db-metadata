<template>
    <el-upload
        v-bind="innerMeta.conf"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleOnSuccess"
        :before-remove="beforeRemove"
        :on-exceed="handleExceed"
        :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip" v-text="innerMeta.conf['tip']"></div>
    </el-upload>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import utils from '@/utils'

    export default {
        mixins: [Meta(DEFAULT.FileBox)],
        name: "FileBox",
        label: "文件上传框",
        props: {
            value: {
                type: [Object, Array],
                default: function () {
                    return []
                },
                validator: function (val) {
                    if (utils.isArr(val) && val.length > 0) {
                        return val.every(item => item.hasOwnProperty('name') && item.hasOwnProperty('url'))
                    }
                    if (utils.isObject(val) && Object.keys(val).length > 0) {
                        return val.hasOwnProperty('name') && val.hasOwnProperty('url');
                    }
                    return true;
                }
            }
        },
        data() {
            return {
                // fileList: this.value
            }
        },
        methods: {
            handleRemove(file, fileList) {
            },
            handlePreview(file) {
            },
            handleExceed(files, fileList) {
                this.$message.warning('文件数量超过设定值：' + files.length);
            },
            beforeRemove(file, fileList) {
                return this.$confirm(`确定移除 ${file.name}？`);
            },
            handleOnSuccess(response, file, fileList) {
                let emitFileList = [];
                if (response.state === 'ok') {
                    this.$message.success('文件上传成功!');
                    emitFileList.push(response.data);
                    this.fileList = emitFileList;
                } else {
                    this.$message.error('文件上传失败');
                }
            }
        },
        computed: {
            fileList: {
                get() {
                    let value = [];
                    let temp;
                    switch (utils.typeOf(this.value)) {
                        case "[object String]":
                            temp = JSON.parse(this.value);
                            utils.isObject(temp) ? value.push(temp) : value = temp;
                            break;
                        case "[object Object]":
                            value.push(this.value);
                            break;
                        case "[object Array]":
                            value = this.value;
                            break;
                    }
                    return value;
                },
                set(val) {
                    let newVal = val;
                    switch (utils.typeOf(this.value)) {
                        case "[object String]":
                            newVal = JSON.stringify(val);
                            break;
                    }
                    this.$emit('input', newVal);
                }
            }
        }
    }
</script>

<style scoped>

</style>
