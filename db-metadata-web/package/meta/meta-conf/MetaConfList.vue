<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import {innerObjectCode} from "../../constant/variable";

    export default {
        name: "MetaConf",
        data() {
            return {
                oc: innerObjectCode.metaConfList
            }
        }
    }
</script>

<style scoped>

</style>