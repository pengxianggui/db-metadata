<template>
    <div style="padding: 20px; overflow: auto; height: 100%;">
        <h1>组件开发调试页面</h1>
        <!--        <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>-->
        <text-area-box v-model="content"></text-area-box>
        <rich-text-box v-model="content">

        </rich-text-box>
    </div>
</template>

<script>

    export default {
        name: "WorkSpace",
        // mixins: [getTableTlMeta],
        data() {
            return {
                // meta: {}
                fc: 'meta_dict_tree',
                content: ''
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
