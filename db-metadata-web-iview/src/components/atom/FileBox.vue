<template>
    <el-upload
            v-bind="meta.ui_config"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :on-exceed="handleExceed"
            :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip" v-text="meta.ui_config.tip"></div>
    </el-upload>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "file-box",
        data () {
            return {
                fileList: []
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
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
            },
            getDefaultConf () {
                return DEFAULT.FileBox
            },
            initConf () {
                // data effective check
                this.meta.ui_config = this.meta.ui_config || {}

                // merge options
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.meta.ui_config, defaultConf)
            }
        },
        created() {
            this.initConf()
        }
    }
</script>

<style scoped>

</style>
