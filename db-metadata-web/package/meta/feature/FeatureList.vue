<template>
    <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
    import SingleGridTmpl from "../../template/SingleGridTmpl";
    import {innerObjectCode} from "../../constant/variable";

    export default {
        name: "FeatureList",
        components: {SingleGridTmpl},
        data() {
            return {
                oc: innerObjectCode.featureList
            }
        }
    }
</script>

<style scoped>

</style>