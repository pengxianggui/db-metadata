<template>
    <table-list v-if="meta" :ref="meta['name']" :meta="meta"></table-list>
</template>
<script>

export default {
    name: "ChangeLog",
    data() {
        return {
            meta: null
        }
    },
    methods: {
        getMeta() {
            const url = '/meta/fields/change_log';
            this.$axios.get(url).then(resp => {
                this.meta = resp.data;
                this.$refs[this.meta['name']].getData();
            }).catch(err => {
                console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                this.$message.error(err.msg)
            });
        },
    },
    mounted() {
        this.getMeta()
    },
}
</script>