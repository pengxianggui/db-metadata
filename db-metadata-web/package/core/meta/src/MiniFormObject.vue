<template>
    <el-form ref="sub_form" :model="nativeValue" size="mini" class="align-to-label shadow-border" label-position="right"
             label-width="130px">
        <el-form-item label="元对象编码">
            <el-col :span="6">
                <el-input v-model="nativeValue.objectCode" size="mini" readonly></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="主键生成策略">
            <div class="el-checkbox-group">
                <el-checkbox v-model="nativeValue.isUUIDPrimary" label="UUID主键" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isNumberSequence" label="数字序列" border></el-checkbox>
                <el-checkbox v-model="nativeValue.isAutoIncrement" label="自动生成" border></el-checkbox>
            </div>
        </el-form-item>
        <el-form-item label="数据结构">
            <el-radio-group v-model="nativeValue.structure">
                <el-radio-button label="list">列表</el-radio-button>
                <el-radio-button label="tree">树型表</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="树型表配置" v-if="nativeValue.structure=='tree'">
            <el-col :span="4">
                <el-input v-model="nativeValue.structureConfig.idKey" placeholder="id 列名"></el-input>
            </el-col>
            <el-col :span="4">
                <el-input v-model="nativeValue.structureConfig.pidKey" placeholder="pid 列名"></el-input>
            </el-col>
            <el-col :span="4">
                <el-input v-model="nativeValue.structureConfig.label" placeholder="label"></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="排序规则(SQL)">
            <el-input placeholder="默认排序规则: columnA desc,columnB asc" v-model="nativeValue.orderBy"></el-input>
        </el-form-item>
        <el-form-item label="过滤规则(SQL)">
            <el-input placeholder="默认过滤条件: a=1 and b=2" v-model="nativeValue.where"></el-input>
        </el-form-item>
        <el-form-item label="业务拦截器">
            <el-input placeholder="配置业务拦截器 完整的包名,多个拦截器使用逗号分割 例如: com.github.md.web.controller.itp.MetaFieldEditPointCut"
                      v-model="nativeValue.bizInterceptor"></el-input>
        </el-form-item>
    </el-form>
</template>

<script>
    import utils from '../../../utils'

    export default {
        name: "MiniFormObject",
        label: "元对象Config迷你表单",
        description: "输入控件的一种,JsonBox的表单表现形式",
        components: {},
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
                    objectCode: null,
                    structure: "list",
                    structureConfig: {},
                    isUUIDPrimary: false,
                    isNumberSequence: false,
                    isAutoIncrement: false,
                    bizInterceptor: '',
                    orderBy: null,
                    where: null,
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
                    value = utils.strToObject(value)
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
    .align-to-label {
        margin-top: 5px;
    }

    .shadow-border {
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        padding: 15px 5px 5px 5px;

    }
</style>
