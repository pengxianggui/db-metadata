<template>
    <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
</template>

<script>
    import TreeSingleGridTmpl from "../../template/TreeSingleGridTmpl";

    export default {
        name: "MenuManager",
        components: {
            TreeSingleGridTmpl
        },
        data() {
            return {
                fc: 'meta_menu'
            }
        }
    }
</script>

<style scoped>

</style>