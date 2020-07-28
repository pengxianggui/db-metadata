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
                fieldConf: {},
                id: null
            }
        },
        methods: {
            submit() {
                const {objectCode, fieldCode, fieldConf, id} = this;
                const url = this.$compile(restUrl.RECORD_DO_UPDATE, {objectCode: "meta_field"})
                this.$axios.post(url, {
                    id: id,
                    object_code: objectCode,
                    field_code: fieldCode,
                    config: fieldConf
                }).then(resp => {
                    this.$emit('callback')
                })
            }
        },
        created() {
            const {objectCode, fieldCode} = this;
            const url = this.$compile(restUrl.FIELD_CODE_CONF, {objectCode: objectCode, fieldCode: fieldCode});
            this.$axios.safeGet(url).then(resp => {
                let {id, config} = Array.isArray(resp.data) ? resp.data[0] : resp.data;
                this.id = id;
                this.fieldConf = config;
            })
        }
    }
</script>

<style scoped>

</style>
