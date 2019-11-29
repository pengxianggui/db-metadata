<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta"></table-list>
    </div>
</template>

<script>
    import {getTlMeta, getSpMeta} from "@/components/core/mixins/methods"

    export default {
        name: "SingleGridTmpl",
        mixins: [getTlMeta, getSpMeta],
        props: {
            R_oc: String
        },
        data() {
            return {
                objectCode: this.R_oc,
                tlMeta: {},
                spMeta: {}
            }
        },
        methods: {
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            }
        },
        created() {
            this.getTlMeta(this.objectCode).then(resp => {
                this.tlMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });

            this.getSpMeta(this.objectCode).then(resp => {
                this.spMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
        },
    }
</script>

<style scoped>

</style>
