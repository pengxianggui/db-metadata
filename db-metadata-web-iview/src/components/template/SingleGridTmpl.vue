<template>
    <div class="el-card">
        <search-panel :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tableRefName" :meta="tlMeta"></table-list>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {getSpMeta, getTlMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "SingleGridTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        data() {
            return {
                featureCode: this.$route.query.featureCode,
                objectCode: this.$route.query.objectCode,
                tlMeta: {},
                spMeta: {}
            }
        },
        methods: {
            handleSearch(params) {
                const tableRefName = this.tableRefName;
                this.$refs[tableRefName].getData(params);
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
            tableRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>
