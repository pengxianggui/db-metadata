<template>
    <div>
        <el-form :ref="innerMeta.name" v-bind="innerMeta.conf" :model="model">
            <el-form-item :label="schemaMeta.label" :prop="schemaMeta.name">
                <drop-down-box :ref="schemaMeta['name']" :meta="schemaMeta" v-model="model[schemaMeta.name]"
                               @change="refreshTables()" filterable></drop-down-box>
            </el-form-item>
            <el-form-item :label="tableMeta.label" :prop="tableMeta.name">
                <drop-down-box :ref="tableMeta['name']" :meta="tableMeta" v-model="model[tableMeta.name]"
                               @change="handleTableChange" filterable></drop-down-box>
            </el-form-item>
            <el-form-item :label="objectMeta.label" :prop="objectMeta.name">
                <text-box :ref="objectMeta['name']" :meta="objectMeta" v-model="model[objectMeta.name]"></text-box>
            </el-form-item>
            <el-form-item :label="codeMeta.label" :prop="codeMeta.name">
                <text-box :ref="codeMeta['name']" :meta="codeMeta" v-model="model[codeMeta.name]"></text-box>
            </el-form-item>
            <el-form-item>
                <el-button :id="innerMeta.name + 'submit'" v-bind="innerMeta.buttons['submit']['conf']"
                           @click="onSubmit"
                           v-text="innerMeta.buttons['submit']['label']"></el-button>
                <el-button :id="innerMeta.name + 'cancel'" v-bind="innerMeta.buttons['cancel']['conf']"
                           @click="onCancel"
                           v-text="innerMeta.buttons['cancel']['label']"></el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import DefaultFormViewMeta from '../core/form/ui-conf'

    export default {
        name: "meta-import",
        data() {
            return {
                model: {},
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        methods: {
            assemblyModel() {
                this.innerMeta['columns'].forEach(item => {
                    this.$set(this.model, item.name, item.value || null)
                })
            },
            refreshTables() {
                const tableKey = this.tableMeta['name'];
                const tableUrl = this.tableMeta['data_url'];

                this.model[tableKey] = null;
                let url = this.$compile(tableUrl, this.model);
                this.$refs[tableKey].getOptions(url);
            },
            handleTableChange(tableName) {
                this.model[this.objectMeta.name] = tableName;
                this.model[this.codeMeta.name] = tableName;
            },
            doSubmit() {
                const params = this.model;
                if (this.$listeners.submit) {
                    this.$emit('submit', params)
                } else {
                    const url = this.innerMeta.action;
                    this.$axios.post(url, params).then(resp => {
                        this.$message.success(resp.msg);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    })
                }
            },
            onSubmit(event) {
                const name = this.innerMeta['name'];
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.doSubmit(event) // submit
                    } else {
                        return false;
                    }
                });
            },
            onCancel: function (event) {
                if (this.$listeners.cancel) {
                    this.$emit('cancel', event)
                } else {
                    //default cancel behavior
                }
            }
        },
        created() {
            this.assemblyModel();
        },
        mounted() {
            // request business data
        },
        computed: {
            innerMeta() {
                return this.$merge(this.meta, DefaultFormViewMeta);
            },
            schemaMeta: function () {
                return this.meta['columns'][0]
            },
            tableMeta: function () {
                return this.meta['columns'][1]
            },
            objectMeta: function () {
                return this.meta['columns'][2]
            },
            codeMeta: function () {
                return this.meta['columns'][3]
            },
        }
    }
</script>

<style scoped>

</style>
