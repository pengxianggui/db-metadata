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
                <code-box-demo></code-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <mini-form-box-demo></mini-form-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <form-view-demo></form-view-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <icon-box-demo></icon-box-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <data-list-table-tmpl-demo style="height: 700px;"></data-list-table-tmpl-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <tree-form-tmpl-demo style="height: 700px;"></tree-form-tmpl-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <tree-table-tmpl-demo style="height: 700px;"></tree-table-tmpl-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <table-form-tmpl-demo style="height: 700px;"></table-form-tmpl-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <data-list-demo></data-list-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <table-tree-view-demo></table-tree-view-demo>
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
                <tree-demo></tree-demo>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <dynamic-route-demo></dynamic-route-demo>
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
                <search-view-demo></search-view-demo>
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
    // 批量注册
    const requireComponent = require.context('./', false, /\w+Demo\.(vue|js)$/);
    let demos = {};

    requireComponent.keys().map(fileName => {
        let cmp = requireComponent(fileName).default;
        demos[cmp.name] = cmp;
    });

    export default {
        name: "demo",
        data() {
            return {
                icon: ''
            }
        },
        methods: {},
        components: demos
    }
</script>
