<template>
    <row-grid :span="[14, 10]">
        <template #left>
            <table-list :ref="tlRefName" :meta="tlMeta" @active-change="handleActiveChange"></table-list>
        </template>
        <template #right>
            <form-tmpl :meta="fmMeta" @ok="formSubmit"></form-tmpl>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL} from '@/constant'
    import {getSpMeta, getTlMeta, loadFeature} from "@/components/core/mixins/methods"
    export default {
        name: "TableFormTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        data() {
            return {
                featureCode: null,
                objectCode: 'test_table',
                fmMeta: {},
                tlMeta: {}
            }
        },
        methods: {
            formSubmit() {
                const tlRefName = this.tlRefName;
                this.$refs[tlRefName].getData();
            },
            handleActiveChange(row) {
                if (utils.isEmpty(row)) {
                    this.fmMeta = this.$merge({}, DEFAULT.FormTmpl);
                    return;
                }
                const primaryKey = this.tlMeta['objectPrimaryKey'];
                const primaryValue = utils.extractValue(row, primaryKey.split(',')).join('_');
                const objectCode = this.tlMeta['objectCode'];

                let url = this.$compile(URL.RECORD_TO_UPDATE, {
                    objectCode: objectCode,
                    primaryKey: primaryValue
                });
                this.$axios.get(url).then(resp => {
                    this.fmMeta = resp.data;
                });
            },
            initMeta(objectCode) {
                this.getTlMeta(objectCode).then(resp => {
                    this.tlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            const featureCode = this.featureCode;
            let objectCode;

            if (!utils.isEmpty(featureCode)) {
                this.loadFeature(featureCode).then(resp => {
                    objectCode = resp.data['objectCode'];
                    this.initMeta(objectCode);
                })
            } else {
                objectCode = this.objectCode;
                this.initMeta(objectCode);
            }
        },
        computed: {
            tlRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>