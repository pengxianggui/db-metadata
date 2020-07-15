<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import {innerObjectCode} from "../../constant/variable";

    export default {
        name: "ExceptionList",
        data() {
            return {
                oc: innerObjectCode.exceptionList
            }
        }
    }
</script>

<style scoped>

</style>