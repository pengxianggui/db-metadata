<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import {metaObjectCode} from "../../constant/variable";

    export default {
        name: "MetaConf",
        data() {
            return {
                oc: metaObjectCode.metaConfList
            }
        }
    }
</script>

<style scoped>

</style>