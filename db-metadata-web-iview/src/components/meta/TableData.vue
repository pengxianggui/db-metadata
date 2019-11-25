<template>
    <div>
        <SearchPanel :meta="meta" @search="handleSearch"></SearchPanel>
        <table-list :ref="meta['name']" :meta="meta"></table-list>
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
                meta: {}
            }
        },
        methods: {
            getMeta() {
                let url = this.$compile("/meta/fields/{objectCode}", {
                    objectCode: this.objectCode
                });

                this.$axios.get(url).then(resp => {
                    this.meta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg);
                })
            },
            handleSearch(params) {
                this.$refs[this.meta['name']].getData(params);
            }
        },
        created() {
            this.getMeta();
        },
    }
</script>

<style scoped>

</style>
