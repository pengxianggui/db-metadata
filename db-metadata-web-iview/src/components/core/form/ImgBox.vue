<template>
    <div>
        <el-upload
                v-bind="innerMeta.conf"
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
    import Meta from '../mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.ImgBox)],
        name: 'ImgBox',
        label: '图片上传框',
        data() {
            return {
                dialogImageUrl: '',
                dialogVisible: false
            };
        },
        methods: {
            handleRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            }
        },
    }
</script>
