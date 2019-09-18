<style scoped>
    .el-row {
        margin-top: 20px;
        border: 1px solid #ddd;
        padding: 10px;
        -webkit-box-shadow: 2px 2px 1px #eeeeee;
        box-shadow: 5px 5px 10px 6px #ddd;
    }
</style>
<template>
    <div>
        <el-row>
            <el-col :span="24">
                <x-input-demo></x-input-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <x-select-demo></x-select-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <search-bar-demo></search-bar-demo>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    import XInputDemo from './demo/XInputDemo'
    import XSelectDemo from './demo/XSelectDemo'
    import SearchBarDemo from './demo/SearchBarDemo'
    export default {
        name: "demo",
        data() {
            return {
            }
        },
        methods: {
        },
        components: {
            SearchBarDemo,
            XInputDemo,
            XSelectDemo
        }
    }
</script>
