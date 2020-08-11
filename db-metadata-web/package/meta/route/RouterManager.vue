<template>
    <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
</template>

<script>
    import JsonBox from "../../core/jsonbox/src/JsonBox";
    import {restUrl} from "../../constant/url";

    export default {
        name: "RouterManager",
        components: {
            JsonBox
        },
        data() {
            return {
                activeIndex: 0,
                routes: [],
                fc: 'meta_router'
            }
        },
        methods: {
            getRoutes() {
                this.$axios.get(restUrl.ROUTE_DATA).then(resp => {
                    this.routes = resp.data
                })
            }
        },
        created() {
            this.getRoutes()
        }
    }
</script>

<style lang="scss" scoped>
</style>