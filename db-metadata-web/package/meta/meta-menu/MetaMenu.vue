<template>
    <div>
        <template v-for="m in menus">
            <menu-item v-if="!m.hidden" :item="m" :base-path="basePath" :key="m.path">
            </menu-item>
        </template>
    </div>
</template>

<script>
    import MenuItem from './MenuItem'
    import MetaRoute from '../../route'

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
                menus: MetaRoute
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>