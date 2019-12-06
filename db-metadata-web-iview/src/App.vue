<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
    export default {
        name: 'app',
        data() {
            return {}
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

    .quote {
        margin: 10px;
        padding: 10px;
        border-left: 5px solid #409EFF;
        color: #666666;
        background-color: #eeeeee;
    }

    .h2 {
        font-size: 1.5em;
        margin-block-start: 0.83em;
        margin-block-end: 0.83em;
        margin-inline-start: 0px;
        margin-inline-end: 0px;
        font-weight: bold;
    }
</style>
