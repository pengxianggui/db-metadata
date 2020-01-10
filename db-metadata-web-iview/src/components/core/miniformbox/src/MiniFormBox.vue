<!--
    {
        component_name: 'MiniFormBox',
        name: 'MiniFormBox',
        label: '迷你表单框',
        conf: {},
        columns: [

        ]
    }
-->

<template>
    <el-form ref="sub_form" :model="nativeValue" size="mini">
        <el-form-item label="默认值">
            <el-col :span="6">
                <el-input v-model="nativeValue.defaultVal" size="mini"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="开关">
            <div class="el-checkbox-group">
                <el-checkbox v-model="nativeValue.isNullable" label="允许为空" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isSearch" label="允许搜索" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isListShow" label="列表显示" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isMultiple" label="允许多值" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isFile" label="是否文件" border></el-checkbox>
            </div>
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
            <z-toggle-panel :default-open="hasTranslation" label-position="top-left">
                <template #label="{open}">
                    <el-tooltip content="优先级: 静态数组 > 指定SQL" placement="right">
                        <i class="el-icon-question"></i>
                    </el-tooltip>
                    &nbsp;
                    <i :class="{'el-icon-caret-bottom': !open, 'el-icon-caret-top': open}"></i>
                </template>
                <div style="padding: 20px; border-left: 3px solid #409EFF">
                    <el-form-item label="指定SQL">
                        <el-col :span="24">
                            <sql-box v-model="nativeValue.scopeSql"></sql-box>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="静态数组">
                        <br>
                        <options-input v-model="nativeValue.scopeOptions"></options-input>
                    </el-form-item>
                </div>
            </z-toggle-panel>
        </el-form-item>
    </el-form>
</template>

<script>
    import utils from '@/utils'
    import OptionsInput from '@/components/meta/form-builder/relate/OptionsInput'

    // PXG_TODO 改为通用型迷你表单控件
    export default {
        name: "MiniFormBox",
        label: "迷你表单",
        description: "输入控件的一种,JsonBox的表单表现形式",
        components: {
            OptionsInput
        },
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
                config: {
                    addStatus: 100,
                    updateStatus: 100,
                    viewStatus: 30,
                    defaultVal: '',
                    isNullable: true,
                    isSearch: false,
                    isMultiple: false,
                    isFile: false,
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
        mounted() {
        },
        computed: {
            nativeValue() {
                let self = this;
                let value = self.value;

                if (typeof value === 'string') {
                    try {
                        value = JSON.parse(value);
                    } catch (e) {
                        value = {}
                    }
                }

                Object.keys(self.config).forEach(key => {
                    if (value.hasOwnProperty(key)) {
                        self.config[key] = value[key];
                    }
                });
                this.$emit("input", self.config);  // immediate emit
                return self.config;
            },
            hasTranslation() {
                let {value} = this;
                if (utils.isString(value)) {
                    value = utils.convertToObject(value);
                }
                return (utils.isString(value.scopeSql) && value.scopeSql.trim() !== '')
                    || (utils.isArray(value.scopeOptions) && value.scopeOptions.length > 0)
            }
        }
    }
</script>

<style scoped>
    .el-checkbox-group > * {
        margin: 0 10px 0 0 !important;
    }
</style>