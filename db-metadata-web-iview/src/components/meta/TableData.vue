<template>
    <div>
        <SearchPanel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></SearchPanel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta"></table-list>
    </div>
</template>

<script>
    export default {
        name: "TableData",
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
                let url = this.$compile("/meta/fields/{objectCode}", {
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
                let url = this.$compile("/meta/fields/{objectCode}", {
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
