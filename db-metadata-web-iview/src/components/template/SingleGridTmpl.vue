<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta"></table-list>
    </div>
</template>

<script>
    import SearchPanel from "../core/SearchPanel";
    import {getTlMeta, getSpMeta} from "../core/mixins/methods"

    export default {
        name: "SingleGridTmpl",
        mixins: [getTlMeta, getSpMeta],
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
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            }
        },
        created() {
            this.getTlMeta(this.objectCode);
            this.getSpMeta(this.objectCode);
        },
    }
</script>

<style scoped>

</style>
