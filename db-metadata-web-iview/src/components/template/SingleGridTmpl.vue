<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta"></table-list>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {loadFeature, getTlMeta, getSpMeta} from "@/components/core/mixins/methods"

    export default {
        name: "SingleGridTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        props: {
            R_fc: String,
            R_oc: String
        },
        data() {
            return {
                featureCode: this.R_fc,
                objectCode: this.R_oc,
                tlMeta: {},
                spMeta: {}
            }
        },
        methods: {
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
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
        }
    }
</script>

<style scoped>

</style>
