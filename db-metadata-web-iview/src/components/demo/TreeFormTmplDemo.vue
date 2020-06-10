<template>
    <div>
        <h3>TreeFormTmpl</h3>
        <tree-form-tmpl :fc="featureCode"></tree-form-tmpl>
    </div>
</template>

<script>
    import TreeFormTmpl from '@/../package/template/TreeFormTmpl'
    export default {
        name: "TreeFormTmplDemo",
        components: {TreeFormTmpl},
        data() {
            return {
                featureCode: "tree-form-tmpl-demo"
            }
        }
    }
</script>

<style scoped>

</style>
