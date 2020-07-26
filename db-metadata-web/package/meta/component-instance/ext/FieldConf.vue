<template>
    <div>
        <mini-form-field v-model="fieldConf"></mini-form-field>
        <div style="margin-top: 10px; text-align: right">
            <el-button @click="submit" type="primary">保存</el-button>
        </div>
    </div>
</template>

<script>
    import {restUrl} from "../../../constant/url";

    export default {
        name: "FieldConf",
        props: {
            objectCode: {
                type: String,
                required: true
            },
            fieldCode: {
                type: String,
                required: true
            }
        },
        data() {
            return {
                fieldConf: {}
            }
        },
        methods: {
            submit() {
                // TODO 只更新某个元字段配置, 暂无法实现——没有只更新某个字段的接口, 若拉出整个meta_field编辑表单, 需要
                // 此条记录的ID, 而光靠objectCode和fieldCode无法实现
                this.$message.warning("暂未实现: 无法只更新某个字段, 若拉出整个meta_field编辑表单, 需要此记录的ID, 只有objectCode和fieldCode 无法实现")
            }
        },
        created() {
            const {objectCode, fieldCode} = this;
            const url = this.$compile(restUrl.FIELD_CODE_CONF, {objectCode: objectCode, fieldCode: fieldCode});
            this.$axios.safeGet(url).then(resp => {
                let {config} = Array.isArray(resp.data) ? resp.data[0] : resp.data;
                this.fieldConf = config;
            })
        }
    }
</script>

<style scoped>

</style>
