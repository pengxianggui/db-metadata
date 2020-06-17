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
                // TODO 只更新某个元字段配置
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
