<template>
    <div>
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu :default-active="activeMenu"
                     :collapse="collapse"
                     v-bind="conf">
                <template v-for="menu in menus">
                    <menu-item v-if="!menu.hidden" :item="menu" :base-path="menu.path"></menu-item>
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
    import MetaMenuData from '../../../menu'
    import {resolvePath} from '@/../package/utils/url'

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
                menus: []
            }
        },
        watch: {
            dataUrl: function (newV, oldV) {
                this.getData(newV)
            }
        },
        methods: {
            getData(url) {
                this.menus.push(...MetaMenuData)
                this.$axios.get(url).then(resp => {
                    const {data: dynamicMenu} = resp
                    this.menus.push(...dynamicMenu)
                })
            }
        },
        created() {
            this.getData(this.dataUrl)
        },
        computed: {
            activeMenu() {
                const route = this.$route;
                const {path, query} = route;
                return resolvePath(path, query)
            },
            conf() {
                const {$reverseMerge, innerMeta: {conf}, $attrs} = this
                return $reverseMerge(conf, $attrs)
            }
        }
    }
</script>

<style scoped>

</style>