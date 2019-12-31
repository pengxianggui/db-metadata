<template>
    <div class="el-card">
        <search-panel :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tableRefName" :meta="tlMeta" :filter-params="filterParams"></table-list>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {getSpMeta, getTlMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "SingleGridTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        props: {
            fc: String,
            oc: String
        },
        data() {
            const {featureCode: R_fc, objectCode: R_oc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);
            const objectCode = utils.assertUndefined(this.oc, R_oc);
            return {
                featureCode: featureCode,
                objectCode: objectCode,
                tlMeta: {},
                spMeta: {},
                filterParams: {}
            }
        },
        methods: {
            handleSearch(params) {
                const tableRefName = this.tableRefName;
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[tableRefName].getData();
                })
            },
            initMeta(objectCode) {
                this.getTlMeta(objectCode).then(resp => {
                    this.tlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getSpMeta(objectCode).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            const {featureCode, objectCode} = this;

            if (!utils.isEmpty(featureCode)) {
                this.loadFeature(featureCode).then(resp => {
                    const config = resp.data['singleGrid'];
                    this.initMeta(config.objectCode);
                })
            } else {
                this.initMeta(objectCode);
            }
        },
        computed: {
            tableRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>
