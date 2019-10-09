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
                <text-box-demo></text-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <pass-box-demo></pass-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <bool-box-demo></bool-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <text-area-box-demo></text-area-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <drop-down-box-demo></drop-down-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <json-box-demo></json-box-demo>
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

    // 批量注册
    const requireComponent = require.context('./demo', false, /\w+\.(vue|js)$/);
    let demos = {};

    requireComponent.keys().map(fileName => {
        let cmp = requireComponent(fileName).default;
        demos[cmp.name] = cmp;
    });
    console.log(demos)

    export default {
        name: "demo",
        data() {
            return {
            }
        },
        methods: {
        },
        components: demos
    }
</script>
