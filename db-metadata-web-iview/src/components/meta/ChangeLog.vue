<template>
    <table-list :ref="meta['name']" :meta="meta"></table-list>
</template>
<script>

export default {
    name: "ChangeLog",
    data() {
        return {
            meta: {}
        }
    },
    methods: {
        getMeta() {
            const url = '/meta/fields/change_log';
            this.$axios.get(url).then(resp => {
                this.meta = resp.data;
            }).catch(err => {
                console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                this.$message.error(err.msg)
            });
        },
    },
    created() {
        this.getMeta()
    },
}
</script>