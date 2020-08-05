<template>
    <div class="layout">
        <div class="menu">
            <router-view name="menu">
                MENU
            </router-view>
        </div>
        <div class="body">
            <div class="header">
                <router-view name="header"></router-view>
            </div>
            <div class="main">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Management"
    }
</script>

<style lang="scss" scoped>
    .layout {
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        display: flex;
        flex-direction: row;

        .menu {
            height: 100%;
            overflow-y: hidden;
            min-width: 200px;
            max-width: 300px;
        }

        .body {
            height: 100%;
            margin: 0;
            padding: 0;
            flex: 1;
            display: flex;
            flex-direction: column;
            overflow: auto;

            .header {
                overflow: hidden;
                min-height: 60px;
            }

            .main {
                flex: 1;
                overflow: auto;
            }
        }
    }
</style>