<template>
    <el-form ref="sub_form" :model="nativeValue" size="mini" class="align-to-label">
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
        <el-form-item label="排序规则(SQL)">
            <el-col :span="6">
                <el-input placeholder="默认排序规则: columnA desc,columnB asc" v-model="nativeValue.orderBy"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="过滤规则(SQL)">
            <el-col :span="6">
                <el-input placeholder="默认过滤条件: a=1 and b=2" v-model="nativeValue.where"></el-input>
            </el-col>
        </el-form-item>
    </el-form>
</template>

<script>
    import utils from '@/utils'

    export default {
        name: "MiniFormObject",
        label: "元对象Config迷你表单",
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
                config: {
                    objectCode: null,
                    isUUIDPrimary: false,
                    isNumberSequence: false,
                    isAutoIncrement: false,
                    orderBy: null,
                    where: null
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
    .align-to-label {
        margin-top: 5px;
    }
</style>