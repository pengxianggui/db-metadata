<template>
    <div>
        <h3>TreeTableTmplDemo</h3>
        <tree-table-tmpl :fc="featureCode"></tree-table-tmpl>
    </div>
</template>

<script>
    import TreeTableTmpl from '@/../package/template/TreeTableTmpl'
    export default {
        name: "TreeTableTmplDemo",
        components: {TreeTableTmpl},
        data() {
            return {
                featureCode: "tree-table-tmpl"
            }
        }
    }
</script>

<style scoped>

</style>
