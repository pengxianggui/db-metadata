<template>
    <div style="padding: 20px; overflow: auto; height: 100%;">
        <h1>组件开发调试页面</h1>
        <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
    </div>
</template>

<script>

    export default {
        name: "WorkSpace",
        // mixins: [getTableTlMeta],
        data() {
            return {
                // meta: {}
                fc: 'meta_dict_tree'
            }
        },
        methods: {},
        created() {
            // this.getTableTlMeta("dict_test").then(resp => {
            //     this.meta = resp.data
            // })
        }
    }
</script>

<style scoped>

</style>
