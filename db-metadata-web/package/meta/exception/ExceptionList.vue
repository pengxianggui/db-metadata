<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import {metaObjectCode} from "../../constant/variable";

    export default {
        name: "ExceptionList",
        data() {
            return {
                oc: metaObjectCode.exceptionList
            }
        }
    }
</script>

<style scoped>

</style>