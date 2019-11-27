<template>
    <el-form ref="sub_form" :model="config" label-width="80px" size="mini">
        <el-form-item label="默认值">
            <el-col :span="6">
                <el-input v-model="config.defaultVal" size="mini"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="开关">
            <el-checkbox v-model="config.isNullable" label="允许为空" border></el-checkbox>
            <el-checkbox v-model="config.isSearch" label="允许搜索" border></el-checkbox>
            <el-checkbox v-model="config.isListShow" label="列表显示" border></el-checkbox>
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
                    <el-input v-model="config.scopeSql" size="mini"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="静态数组">
                <el-select
                        v-model="config.scopeOptions"
                        multiple
                        filterable
                        allow-create
                        default-first-option
                        placeholder="动态添加选项">
                </el-select>
            </el-form-item>
        </template>
    </el-form>
</template>

<script>
    export default {
        name: "MiniFormConfigDemo",
        props: {
            model: {
                type: [Object, String],
                default: function () {
                    return {}
                }
            }
        },
        data() {
            return {
                hasTranslation: false,
                config: {
                    addStatus: 100,
                    updateStatus: 100,
                    viewStatus: 30,
                    defaultVal: '',
                    isNullable: true,
                    isSearch: false,
                    scopeSql: '',
                    scopeOptions: [],
                    isListShow: true,
                    scopeRange: [],
                }
            }
        },
        mounted() {
            let self = this;
            let model = self.model;

            if (typeof model === 'string') {
                model = JSON.parse(model);
            }

            Object.keys(self.config).forEach(key => {
                if (model.hasOwnProperty(key)) {
                    self.config[key] = model[key];
                }
            });
        },
        updated() {
            this.$emit("submit", this.config);  // immediate emit
        }
    }
</script>

<style scoped>

</style>