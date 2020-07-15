<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import {innerObjectCode} from "../../constant/variable";

    export default {
        name: "DictList",
        data() {
            return {
                oc: innerObjectCode.dictList
            }
        }
    }
</script>

<style scoped>

</style>