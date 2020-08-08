<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import SingleGridTmpl from "../../template/SingleGridTmpl";
    import {metaObjectCode} from "../../constant/variable";

    export default {
        name: "FeatureList",
        components: {SingleGridTmpl},
        data() {
            return {
                oc: metaObjectCode.featureList
            }
        }
    }
</script>

<style scoped>

</style>