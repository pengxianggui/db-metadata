<template>
    <div style="height: 100%;">
        <!--        <el-scrollbar wrap-class="scrollbar-wrapper" style="height: 100%">-->
        <el-menu :default-active="activeMenu"
                 :collapse="collapse"
                 v-bind="conf" class="menu-class">
            <template v-for="menu in metaMenus" v-if="$isRoot()">
                <menu-item v-if="!menu.hidden" :item="menu" :base-path="menu.path"></menu-item>
            </template>

            <template v-for="menu in menus">
                <menu-item v-if="!menu.hidden" :item="menu" :base-path="menu.path"></menu-item>
            </template>
            <slot></slot>
        </el-menu>
        <!--        </el-scrollbar>-->
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
                metaMenus: MetaMenuData,
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

<style lang="scss" scoped>
    .menu-class {
        height: 100%;
    }
</style>