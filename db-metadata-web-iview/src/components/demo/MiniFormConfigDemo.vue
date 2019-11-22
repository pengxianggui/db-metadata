<template>
    <el-form ref="sub_form" :model="config" label-width="80px" size="mini">
        <el-form-item label="默认值">
            <el-col :span="6">
                <el-input v-model="config.defaultVal" size="mini"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="开关">
            <el-checkbox v-model="config.isNullable" label="允许为空" border></el-checkbox>
            <el-checkbox v-model="config.isQuery" label="允许搜索" border></el-checkbox>
        </el-form-item>
        <el-form-item label="新增状态">
            <el-radio-group v-model="config.addStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="更新状态">
            <el-radio-group v-model="config.updateStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="查看状态">
            <el-radio-group v-model="config.viewStatus">
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
                    <el-input v-model="config.defaultVal" size="mini"></el-input>
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
        props: {
            value: [Object, String]
        },
        data() {
            return {
                hasTranslation: false
            }
        },
        methods: {
            onSubmit() {
                // this.$refs.sub_config.
                console.log('submit!');
                this.$emit("input", this.config);
            },
            assemblyModel(value) {

            }
        },
        computed: {
            config: {
                get: function () {
                    let self = this;
                    let config = {
                        addStatus: 100,
                        updateStatus: 100,
                        viewStatus: 100,
                        defaultVal: '',
                        isNullable: '',
                        isQuery: false,
                        scopeSql: '',
                        scopeOptions: [],
                        scopeRange: [],
                    };
                    // this.assemblyModel(this.value);

                    Object.keys(config).forEach(key => {
                        let val = JSON.parse(self.value).hasOwnProperty(key) ? JSON.parse(self.value)[key] : null;
                        self.$set(config, key, val)
                    });
                    return config;
                },
                set: function (n) {
                    if (n === '') n = null;
                    return this.$emit("input", n); // 通过 input 事件更新 model
                }
            }
        }
    }
</script>

<style scoped>

</style>