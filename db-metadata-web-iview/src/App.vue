<template>
    <div id="app">
        <router-view :key="new Date().getTime()"></router-view>
    </div>
</template>

<script>
    export default {
        name: 'app',
        data() {
            return {
            }
        }
    }
</script>

<style>
    html, body, #app {
        height: 100%;
        margin: 0;
        padding: 0;
    }
    .el-card {
        padding: 10px;
    }
    form .inline {
        display: inline-block;
    }
    .el-popover {
        min-width: 50px;
    }
</style>
