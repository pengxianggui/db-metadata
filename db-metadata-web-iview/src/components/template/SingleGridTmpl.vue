<template>
    <div>
        <slot name="search-panel">
            <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        </slot>
        <slot name="table-list">
            <table-list :ref="tlMeta['name']" :meta="tlMeta"></table-list>
        </slot>
    </div>
</template>

<script>
    import SearchPanel from "../core/SearchPanel";

    export default {
        name: "SingleGridTmpl",
        components: {SearchPanel},
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
            getTlMeta() {
                let url = this.$compile("/table/meta/{objectCode}", {
                    objectCode: this.objectCode
                });
                this.$axios.get(url).then(resp => {
                    this.tlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg);
                });
            },
            getSpMeta() {
                let url = this.$compile("/component/meta?componentCode=SearchPanel&objectCode={objectCode}", {
                    objectCode: this.objectCode
                });
                this.$axios.get(url).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg);
                });
            },
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            }
        },
        created() {
            this.getTlMeta();
            this.getSpMeta();
        },
    }
</script>

<style scoped>

</style>
