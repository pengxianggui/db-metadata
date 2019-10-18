<template>
    <el-form :model="confModel">
        <el-form-item label="元对象">
            <drop-down-box v-model="confModel.metaObject" :meta="objectMeta"></drop-down-box>
        </el-form-item>
        <el-form-item label="组件">
            <drop-down-box v-model="confModel.componentName" :meta="componentMeta"></drop-down-box>
        </el-form-item>
        <el-form-item label="Meta-Conf">
            <json-box v-model="confModel.metaConf" :meta="confMeta"></json-box>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="onCancel">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "meta-conf",
        data() {
            let objectMeta = {
                "name": "meta",
                "data_url": "", // todo reset
                "group": false,
                "conf": {
                    clearable: true
                }
            }
            let componentMeta = {
                "name": "component",
                "data_url": "", // todo reset
                "group": false,
                "conf": {
                    clearable: true
                }
            }
            let confMeta = {
                "name": "conf",
                "conf": {
                    mode: 'code',
                }
            }

            this.$merge(objectMeta, DEFAULT.DropDownBox)
            this.$merge(componentMeta, DEFAULT.DropDownBox)
            this.$merge(confMeta, DEFAULT.JsonBox)

            return {
                objectMeta: objectMeta,
                componentMeta: componentMeta,
                confMeta: confMeta,
                confModel: {
                    metaObject: null,
                    componentName: null,
                    metaConf: null
                }
            }
        },
        methods: {
            onSubmit: function () {
                let _this = this
                this.$axios({
                    method: 'POST',
                    url: '', // todo
                    data: _this.confModel
                })
            },
            onCancel: function () {
                // todo
            }
        },
    }
</script>

<style scoped>

</style>
