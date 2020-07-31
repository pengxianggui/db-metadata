<template>
    <el-form ref="sub_form" :model="nativeValue" size="mini" class="align-to-label shadow-border "
             label-position="right" label-width="80px">
        <el-form-item label="默认值">
            <el-col :span="6">
                <el-input v-model="nativeValue.defaultVal" size="mini"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="开关设置">
            <div class="el-checkbox-group">
                <el-checkbox v-model="nativeValue.isNullable" label="允许为空" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isSearch" label="允许搜索" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isListShow" label="列表显示" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isMultiple" label="允许多值" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isFile" label="是否文件" border></el-checkbox>
                <el-tooltip content="上传文件/图片需要勾选" placement="right">
                    <i class="el-icon-question"></i>
                </el-tooltip>
            </div>
        </el-form-item>
        <el-form-item v-if="nativeValue.isFile" label="上传插槽">
            <el-tooltip content="只有一个插槽时可不填,多个插槽时插槽名称指定后,生成多个上传组件后会标识每个上传组件传到哪一个插槽" placement="right">
                <i class="el-icon-question"></i>
            </el-tooltip>
            <template v-for="index in nativeValue.seats.length">
                <el-col :span="6">
                    <el-input v-model="nativeValue.seats[index-1]" :placeholder="'第'+index+'个位置插槽名称'" size="mini">
                        <template slot="append">
                            <el-button type="primary" icon="el-icon-plus" size="mini"
                                       @click="nativeValue.seats.push('')"></el-button>
                            <el-button type="primary" icon="el-icon-minus" size="mini"
                                       @click="nativeValue.seats.splice(index-1,1)"></el-button>
                        </template>
                    </el-input>
                </el-col>
            </template>
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
    import utils from '../../../utils'
    import OptionsInput from '../../../meta/form-builder/relate/OptionsInput'

    export default {
        name: "MiniFormField",
        label: "元字段Config迷你表单",
        description: "输入控件的一种,JsonBox的表单表现形式",
        components: {
            OptionsInput
        },
        props: {
            value: {
                type: [Object, String],
                default: () => {
                }
            }
        },
        data() {
            return {
                config: {
                    seats: [""],
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
                    value = JSON.parse(value);
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
                let value = utils.isString(this.value) ? JSON.parse(this.value) : this.value;
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

    .align-to-label {
        margin-top: 5px;
    }

    .shadow-border {
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        padding: 15px 0px 5px 5px;

    }
</style>
