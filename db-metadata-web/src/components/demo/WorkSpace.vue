<template>
    <div style="padding: 20px;">
        <h1>组件开发调试页面</h1>
        <nav-menu :meta="menuMeta"></nav-menu>
    </div>
</template>

<script>
    export default {
        name: "WorkSpace",
        components: {},
        data() {
            return {
                menuMeta: {
                    component_name: 'NavMenu',
                    conf: {
                        "unique-opened": true
                    }
                }
            }
        },
        methods: {},
        mounted() {
        }
    }
</script>

<style scoped>

</style>
