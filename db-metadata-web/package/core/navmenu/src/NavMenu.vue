<template>
    <div>
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu :default-active="activeMenu"
                     :collapse="collapse"
                     v-bind="$reverseMerge(innerMeta.conf, $attrs)">
                <template v-for="menu in data">
                    <menu-item v-if="!menu.hidden" :item="menu" :base-path="menu.index"></menu-item>
                </template>
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
    import Meta from "../../mixins/meta";
    import DefaultMeta from "../../navmenu/ui-conf";
    import {restUrl} from "../../../constant/url";
    import MenuItem from './MenuItem'

    export default {
        name: "NavMenu",
        mixins: [Meta(DefaultMeta)],
        components: {
            MenuItem
        },
        props: {
            dataUrl: {
                type: String,
                default: () => restUrl.MENU_DATA
            },
            collapse: {
                type: Boolean,
                default: () => false
            }
        },
        data() {
            return {
                data: []
            }
        },
        watch: {
            dataUrl: function (newV, oldV) {
                this.getData(newV)
            }
        },
        methods: {
            getData(url) {
                this.$axios.get(url).then(resp => this.data = resp.data)
            }
        },
        created() {
            this.getData(this.dataUrl)
        },
        computed: {
            activeMenu() {
                const route = this.$route;
                const {path} = route;
                console.log(route)
                return path
            }
        }
    }
</script>

<style scoped>

</style>