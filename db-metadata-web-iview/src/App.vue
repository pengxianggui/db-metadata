<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
    export default {
        name: 'app'
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
