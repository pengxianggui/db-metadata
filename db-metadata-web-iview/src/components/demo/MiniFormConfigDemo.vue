<template>
    <el-form ref="sub_form" :model="form" label-width="80px" size="mini">
        <el-form-item label="默认值">
            <el-col :span="6">
                <el-input v-model="form.defaultVal" size="mini"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="开关">
            <el-checkbox v-model="form.isNullable" label="允许为空" border></el-checkbox>
            <el-checkbox v-model="form.isQuery" label="允许搜索" border></el-checkbox>
        </el-form-item>
        <el-form-item label="新增状态">
            <el-radio-group v-model="form.addStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="更新状态">
            <el-radio-group v-model="form.updateStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="查看状态">
            <el-radio-group v-model="form.viewStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="数据源">
            <el-checkbox v-model="hasTranslation" border></el-checkbox>
        </el-form-item>
        <template v-if="hasTranslation">
            <el-form-item label="指定SQL">
                <el-col :span="6">
                    <el-input v-model="form.defaultVal" size="mini"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="静态数组">
                <el-checkbox-group v-model="checkList">
                    <el-checkbox label="复选框 A"></el-checkbox>
                    <el-checkbox label="复选框 B"></el-checkbox>
                    <el-checkbox label="复选框 C"></el-checkbox>
                    <el-checkbox label="禁用" disabled></el-checkbox>
                    <el-checkbox label="选中且禁用" disabled></el-checkbox>
                </el-checkbox-group>
            </el-form-item>
        </template>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">确定</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: "MiniFormConfigDemo",
        data() {
            return {
                hasTranslation: false,
                form: {
                    addStatus: 100,
                    updateStatus: 100,
                    viewStatus: 100,
                    defaultVal: '',
                    isNullable: '',
                    isQuery: false,
                    scopeSql: '',
                    scopeOptions: [],
                    scopeRange: [],
                }
            }
        },
        methods: {
            onSubmit() {
                // this.$refs.sub_form.
                console.log('submit!');
            }
        }
    }
</script>

<style scoped>

</style>