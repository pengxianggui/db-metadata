<template>
    <el-form ref="metaImportForm" :model="formModel" :rules="rules">
        <el-form-item :label="dataSourceMeta.label" :prop="dataSourceMeta.en">
            <component :is="dataSourceMeta.component_name" v-model="formModel.dataSource" :meta="dataSourceMeta"
                       @focus="getSchema"></component>
        </el-form-item>
        <el-form-item :label="metaTypeMeta.label" :prop="metaTypeMeta.en">
            <component :is="metaTypeMeta.component_name" v-model="formModel.metaType" :meta="metaTypeMeta"></component>
        </el-form-item>
        <el-form-item :label="metaTableMeta.label" :prop="metaTableMeta.en">
            <component :is="metaTableMeta.component_name" v-model="formModel.metaType" :meta="metaTableMeta"></component>
        </el-form-item>
        <el-form-item :label="metaObjectMeta.label" :prop="metaObjectMeta.en">
            <component :is="metaObjectMeta.component_name" v-model="formModel.metaType" :meta="metaObjectMeta"></component>
        </el-form-item>
        <el-form-item :label="metaObjectCodeMeta.label" :prop="metaObjectCodeMeta.en">
            <component :is="metaObjectCodeMeta.component_name" v-model="formModel.metaType" :meta="metaObjectCodeMeta"></component>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button>取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: "MetaImport",
        data () {
            return {
                formModel: {
                    dataSource: null,
                    metaType: null,
                    metaTable: null,
                    metaObject: null,
                    metaObjectCode: null
                },
                dataSourceMeta: {
                    component_name: 'DropDownBox',
                    label: "数据源",
                    en: "dataSource",
                    ui_config: {
                        label: 'name',
                        value: 'value', // 可省略, value不指定时, 则选取的值即为整个item对象
                    },
                    options: null
                },
                metaTypeMeta: {
                    component_name: 'DropDownBox',
                    label: "元类型",
                    en: "metaType",
                    ui_config: {
                        label: "name",
                        value: "value",
                    },
                    options: null
                },
                metaTableMeta: {
                    component_name: 'DropDownBox',
                    label: "元数据表",
                    en: "metaTable",
                    ui_config: {
                        label: "name",
                        value: "value",
                        filterable: true
                    },
                    options: null
                },
                metaObjectMeta: {
                    component_name: 'TextBox',
                    label: "元数据对象",
                    en: "metaObject",
                    ui_config: {

                    }
                },
                metaObjectCodeMeta: {
                    component_name: 'TextBox',
                    label: "元数据编码",
                    en: "metaObjectCode",
                    ui_config: {}
                },
                rules: {
                    dataSource: {
                        required: true,
                        message: "必填字段"
                    },
                    metaType: {
                        required: true,
                        message: "必填字段"
                    },
                    metaTable: {
                        required: true,
                        message: "必填字段"
                    },
                    metaObject: {
                        required: true,
                        message: "必填字段"
                    },
                    metaObjectCode: {
                        required: true,
                        message: "必填字段"
                    },
                }
            }
        },
        methods: {
            getSchema: function () {
                let _this = this
                this.$axios({
                    methods: 'get',
                    url: '' // todo
                }).then(response => {
                    // todo
                })

                _this.dataSourceMeta.options = [{
                    'name': '航天恒嘉',
                    'value': '1'
                }]
            },
            onSubmit: function () {
                this.$refs['metaImportForm'].validate((valid) => {
                    if (valid) {
                        // todo 提交表单
                    } else {
                        return false;
                    }
                });
            }
        },
        created () {
        },
        components: {
        }
    }
</script>

<style scoped>

</style>
