<template>
    <data-list-table-tmpl :fc="featureCode"></data-list-table-tmpl>
</template>

<script>
    import DataListTableTmpl from '@/components/template/DataListTableTmpl'

    export default {
        name: "DataListTableTmplDemo",
        components: {DataListTableTmpl},
        data() {
            return {
                featureCode: "data-list-table"
            }
        }
    }
</script>

<style scoped>

</style>