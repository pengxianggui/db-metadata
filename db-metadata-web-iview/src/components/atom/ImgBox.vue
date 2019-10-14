<template>
    <div>
        <el-upload
                v-bind="meta.ui_config"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                accept="image">
            <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </div>
</template>
<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: 'img-box',
        data() {
            return {
                dialogImageUrl: '',
                dialogVisible: false
            };
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {
                        ui_config: DEFAULT.upload
                    }
                }
            }
        },
        methods: {
            handleRemove(file, fileList) {
                // console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            getDefaultConf() {
                return DEFAULT.ImgBox
            },
            initConf() {
                // data effective check
                this.meta.ui_config = this.meta.ui_config || {}

                // merge options
                let defaultConf = this.getDefaultConf()
                this.merge(this.meta.ui_config, defaultConf)
            }
        },
        created() {
            this.initConf()
        }
    }
</script>
