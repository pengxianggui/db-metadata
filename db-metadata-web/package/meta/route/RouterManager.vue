<template>
    <div class="route-manager-box">
        <!--        <div class="preview">-->
        <!--            <div class="item" v-for="(r, index) in routes" :key="r.path" @click.native="activeIndex = index"></div>-->
        <!--        </div>-->
        <!--        <div class="content">-->
        <!--            <json-box v-model="routes[activeIndex]"></json-box>-->
        <!--        </div>-->
        <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>
    </div>
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
    .route-manager-box {
        height: 100%;
        display: flex;

        .preview {
            width: 300px;
        }

        .content {
            flex: 1;
        }
    }
</style>