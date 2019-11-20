<template>
    <table-list :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    export default {
        name: "InstanceConfList",
        data() {
            return {
                meta: {}
            }
        },
        methods: {
            getMeta() {
                this.$axios.get("/meta/fields/meta_component_instance")
                    .then(resp => {
                        this.meta = resp.data;
                    }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
        },
        created() {
            this.getMeta();
        },
    }
</script>

<style scoped>

</style>
