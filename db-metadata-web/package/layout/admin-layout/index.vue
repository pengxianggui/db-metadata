<template>
    <div class="layout">
        <div class="header">
            <slot name="header">
                <div>
                    <span class="h2" style="font-family: unset">元数据管理系统</span>
                    <span style="font-family: cursive;">—— Data Fuck Everything</span>
                </div>
            </slot>
        </div>
        <div class="body">
            <div class="menu">
                <slot name="menu">
                    <nav-menu></nav-menu>
                </slot>
            </div>
            <div class="main">
                <router-view :key="$route.path"></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    import NavMenu from "../../core/navmenu";

    export default {
        name: "AdminLayout",
        components: {
            NavMenu
        }
    }
</script>

<style lang="scss" scoped>
    .layout {
        width: 100%;
        height: 100%;
        overflow: hidden;
        margin: 0;
        padding: 0;
        display: flex;
        flex-direction: column;

        .header {
            height: 60px;
            line-height: 60px;
            padding: 0 20px;
            display: flex;
            flex-direction: row;
            color: #409EFF;
        }

        .body {
            width: 100%;
            margin: 0;
            padding: 0;
            flex: 1;
            display: flex;
            flex-direction: row;
            overflow: auto;

            .menu {
                min-width: 200px;
                max-width: 300px;
                overflow-y: hidden;
                height: 100%;
                overflow: auto;
            }

            .main {
                flex: 1;
                overflow: auto;
                padding: 20px;
                overflow: auto;
            }
        }
    }
</style>