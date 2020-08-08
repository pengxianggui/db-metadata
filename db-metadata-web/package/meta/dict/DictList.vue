<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import {metaObjectCode} from "../../constant/variable";

    export default {
        name: "DictList",
        data() {
            return {
                oc: metaObjectCode.dictList
            }
        }
    }
</script>

<style scoped>

</style>