<template>
    <table-list :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    export default {
        name: "ChangeLog",
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
                });
            },
        },
        created() {
            this.getMeta();
        },
    }
</script>