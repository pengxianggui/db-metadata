<template>
    <div>
        <h3>DataListTableTmpl</h3>
        <data-list-table-tmpl :fc="featureCode"></data-list-table-tmpl>
    </div>
</template>

<script>
    import DataListTableTmpl from '@/components/template/DataListTableTmpl'

    export default {
        name: "DataListTableTmplDemo",
        components: {DataListTableTmpl},
        data() {
            return {
                featureCode: "list-table-tmpl"
            }
        }
    }
</script>

<style scoped>

</style>