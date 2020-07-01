<template>
    <el-upload
            v-bind="$reverseMerge(innerMeta.conf, $attrs)"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleOnSuccess"
            :before-remove="beforeRemove"
            :on-exceed="handleExceed"
            :file-list="fileList">
        <el-button size="mini" icon="el-icon-upload" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip" v-text="innerMeta.conf['tip']"></div>
    </el-upload>
</template>

<script>
    import utils from '../../../utils'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import conver from './conver'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val(conver)],
        name: "FileBox",
        label: "文件上传框",
        props: {
            value: {
                type: [Array, String],  // String could be convert to Array
                default: () => [],
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
                fileList: []
            }
        },
        methods: {
            handleRemove(file, fileList) {
            },
            handlePreview(file) { // download
                window.open(file['download_url'], '_blank');
            },
            handleExceed(files, fileList) {
                this.$message.warning('文件数量超过设定值：' + files.length);
            },
            beforeRemove(file, fileList) {
                let self = this;
                return this.$confirm(`确定移除 ${file.name}？`).then(data => {
                    let delIndex = -1;
                    for (let i = 0; i < self.nativeValue.length; i++) {
                        if (self.nativeValue[i].url === file.url) { // 根据唯一标示进行移除
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
                    this.$emit('input', this.nativeValue);
                } else {
                    this.$message.error('文件上传失败');
                }
            }
        },
        mounted() {
            this.fileList = utils.deepClone(this.nativeValue);
        }
    }
</script>

<style scoped>

</style>
