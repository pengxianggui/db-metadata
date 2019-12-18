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
                <table-form-tmpl style="height: 700px;"></table-form-tmpl>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <table-tree-list-demo></table-tree-list-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <img-box-demo></img-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <file-box-demo></file-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <sql-box-demo></sql-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <tree-list-demo></tree-list-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <mini-form-config-demo></mini-form-config-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <dynamic-route-demo></dynamic-route-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <form-tmpl-demo></form-tmpl-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <DialogBoxDemo></DialogBoxDemo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <PopMenuDemo></PopMenuDemo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <search-panel-demo></search-panel-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <find-box-demo></find-box-demo>
            </el-col>
        </el-row>
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
                <text-area-box-demo></text-area-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <num-box-demo></num-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <radio-box-demo></radio-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <bool-box-demo></bool-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <check-box-demo></check-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <drop-down-box-demo></drop-down-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <time-box-demo></time-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <date-box-demo></date-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <date-time-box-demo></date-time-box-demo>
            </el-col>
        </el-row>
<!--        <el-row>-->
<!--            <el-col :span="24">-->
<!--                <json-box-demo></json-box-demo>-->
<!--            </el-col>-->
<!--        </el-row>-->
<!--        <el-row>-->
<!--            <el-col :span="24">-->
<!--                <img-box-demo></img-box-demo>-->
<!--            </el-col>-->
<!--        </el-row>-->
<!--        <el-row>-->
<!--            <el-col :span="24">-->
<!--                <search-bar-demo></search-bar-demo>-->
<!--            </el-col>-->
<!--        </el-row>-->
    </div>
</template>
<script>
    import TreeTableTmpl from '@/components/template/TableFormTmpl'
    // 批量注册
    const requireComponent = require.context('./', false, /\w+Demo\.(vue|js)$/);
    let demos = {};

    requireComponent.keys().map(fileName => {
        let cmp = requireComponent(fileName).default;
        demos[cmp.name] = cmp;
    });
    demos[TreeTableTmpl.name] = TreeTableTmpl;

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
