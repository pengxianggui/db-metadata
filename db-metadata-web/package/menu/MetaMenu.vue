<template>
    <div v-if="$isRoot()">
        <template v-for="m in menus">
            <menu-item v-if="!m.hidden" :item="m" :base-path="basePath" :key="m.path">
            </menu-item>
        </template>
    </div>
</template>

<script>
    import MenuItem from '../core/navmenu/src/MenuItem'
    import MetaMenu from './index'

    export default {
        name: "MetaMenu",
        components: {
            MenuItem
        },
        props: {
            basePath: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                menus: MetaMenu
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>