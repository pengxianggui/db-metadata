<template>
    <row-grid :span="[14, 10]">
        <template #left>
            <table-list :ref="tlRefName" :meta="tlMeta" @active-change="handleActiveChange"></table-list>
        </template>
        <template #right>
            <div class="el-card" style="margin-left: 5px">
                <form-tmpl :meta="fmMeta" @ok="formSubmit"></form-tmpl>
            </div>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {CONSTANT, DEFAULT, URL} from '@/constant'
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
            handleActiveChange(activeData) {
                if (utils.isEmpty(activeData)) {
                    this.fmMeta = this.$merge({}, DEFAULT.FormTmpl);
                    return;
                }
                const primaryKey = this.primaryKey;
                const primaryValue = utils.extractValue(activeData, primaryKey);
                const primaryKv = utils.spliceKvs(primaryKey, primaryValue);

                const objectCode = this.tlMeta['objectCode'];

                let url = this.$compile(URL.RECORD_TO_UPDATE, {
                    objectCode: objectCode,
                    primaryKv: primaryKv
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
            },
            primaryKey() {
                let primaryKey = this.tlMeta.hasOwnProperty('objectPrimaryKey') ? this.tlMeta['objectPrimaryKey'] : CONSTANT.DEFAULT_PRIMARY_KEY;
                return primaryKey.split(',');
            }
        }
    }
</script>

<style scoped>

</style>