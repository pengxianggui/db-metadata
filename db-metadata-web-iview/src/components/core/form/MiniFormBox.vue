<template>
    <el-form ref="sub_form" :model="nativeValue" v-bind="innerMeta['conf']">
        <el-form-item label="默认值">
            <el-col :span="6">
                <el-input v-model="nativeValue.defaultVal" size="mini"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="开关">
            <el-checkbox v-model="nativeValue.isNullable" label="允许为空" border></el-checkbox>
            <el-checkbox v-model="nativeValue.isSearch" label="允许搜索" border></el-checkbox>
            <el-checkbox v-model="nativeValue.isListShow" label="列表显示" border></el-checkbox>
        </el-form-item>
        <el-form-item label="新增状态">
            <el-radio-group v-model="nativeValue.addStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="更新状态">
            <el-radio-group v-model="nativeValue.updateStatus">
                <el-radio-button label="100">显示</el-radio-button>
                <el-radio-button label="50">隐藏</el-radio-button>
                <el-radio-button label="30">只读</el-radio-button>
                <el-radio-button label="10">禁用</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="查看状态">
            <el-radio-group v-model="nativeValue.viewStatus">
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
                    <el-input v-model="nativeValue.scopeSql" size="mini"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="静态数组">
                <el-select
                    v-model="nativeValue.scopeOptions"
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
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.JsonBox)],
        name: "MiniFormBox",
        label: "迷你表单",
        description: "输入控件的一种,JsonBox的表单表现形式",
        components: {},
        props: {
            value: {
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
        updated() {
            this.$emit("input", this.nativeValue);
        },
        computed: {
            nativeValue() {
                let self = this;
                let value = self.value;

                if (typeof value === 'string') {
                    value = JSON.parse(value);
                }

                Object.keys(self.config).forEach(key => {
                    if (value.hasOwnProperty(key)) {
                        self.config[key] = value[key];
                    }
                });
                this.$emit("input", self.config);  // immediate emit
                return self.config;
            }
        }
    }
</script>

<style scoped>

</style>