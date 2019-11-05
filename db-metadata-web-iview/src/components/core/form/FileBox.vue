<template>
    <el-upload
            v-bind="innerMeta.conf"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :on-exceed="handleExceed"
            :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip" v-text="innerMeta.conf['tip']"></div>
    </el-upload>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.FileBox)],
        name: "FileBox",
        data () {
            return {
                fileList: []
            }
        },
        methods: {
            handleRemove (file, fileList) {
            },
            handlePreview (file) {
            },
            handleExceed (files, fileList) {
            },
            beforeRemove (file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            }
        },
    }
</script>

<style scoped>

</style>
