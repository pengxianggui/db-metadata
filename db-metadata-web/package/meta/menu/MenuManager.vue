<template>
    <tree-single-grid-tmpl :oc="oc"></tree-single-grid-tmpl>
</template>

<script>
    import {innerObjectCode} from "../../constant/variable";
    import TreeSingleGridTmpl from "../../template/TreeSingleGridTmpl";

    export default {
        name: "MenuManager",
        components: {
            TreeSingleGridTmpl
        },
        data() {
            return {
                oc: innerObjectCode.MenuList
            }
        }
    }
</script>

<style scoped>

</style>