<!--
# meta
eg:
    {
        name: "formName",
        action: '/save', // form action (url)
        conf: {
            "label-width": '80px',
            size: 'medium', // medium|small|mini
            model: {
                username: '',
                // ...
            },
            rules: {
                username: [{required: true, message: '用户名必填', trigger: 'blur'}],
                // ...
            },
            // ...
        },
        columns: [{
            component_name: 'TextBox',
            name: 'username',
            label: '用户名',
            conf: {
                clearable: true,
                placeholder: "请输入姓名..",
                // ...
            }
        }],
        btns: {
            submit: {
                label: '提交',
                conf: {
                    // ... support conf of el-button
                }
            },
            cancel: {
                label: '取消',
                conf: {
                    // ... support conf of el-button
                }
            }
        }
    }
-->
<template>
    <div>
        <el-form :ref="innerMeta['name']" v-bind="innerMeta.conf" :model="model">
            <el-form-item :label="schemaMeta.label" :prop="schemaMeta.name">
                <drop-down-box :meta="schemaMeta" v-model="model[schemaMeta.name]"
                               @change="refreshTables()"></drop-down-box>
            </el-form-item>
            <el-form-item :label="tableMeta.label" :prop="tableMeta.name">
                <drop-down-box :meta="tableMeta" v-model="model[tableMeta.name]"
                               :options="tableOptions"></drop-down-box>
            </el-form-item>
            <el-form-item :label="objectMeta.label" :prop="objectMeta.name">
                <text-box :meta="objectMeta" v-model="model[objectMeta.name]"></text-box>
            </el-form-item>
            <el-form-item :label="codeMeta.label" :prop="codeMeta.name">
                <text-box :meta="codeMeta" v-model="model[codeMeta.name]"></text-box>
            </el-form-item>
            <el-form-item>
                <el-button :id="innerMeta.name + 'submit'" v-bind="innerMeta.btns.submit.conf" @click="onSubmit"
                           v-text="innerMeta.btns.submit.label"></el-button>
                <el-button :id="innerMeta.name + 'cancel'" v-bind="innerMeta.btns.cancel.conf" @click="onCancel"
                           v-text="innerMeta.btns.cancel.label"></el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "meta-import",
        data() {
            return {
                model: {},
                tableOptions: [],
                schemaOptions: []
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
                let _this = this;
                _this.innerMeta.columns.forEach(item => {
                    _this.$set(_this.model, item.name, item.value || null)
                })
            },
            getDefaultMeta() {
                return DEFAULT.FormTmpl
            },
            refreshTables() {
                let url = this.$complieString(this.model, this.tableMeta['data_url']);
                this.tableMeta['data_url'] = url;
            },
            doSubmit() {
                let _this = this;
                if (_this.$listeners.submit) {
                    _this.$emit('submit', _this.model)
                } else {
                    this.$axios.post(_this.innerMeta.action, _this.model).then(resp => {
                        _this.$message({type: 'success', message: resp.msg || '操作成功'})
                    }).catch(resp => {
                        _this.$message.error(resp.toString())
                    })
                }
            },
            onSubmit(event) {
                let _this = this;
                _this.$refs[_this.innerMeta['name']].validate((valid) => {
                    if (valid) {
                        _this.doSubmit(event) // submit
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
                return this.$merge(this.meta, DEFAULT.FormTmpl);
            },
            schemaMeta: function() {
                return this.meta.columns[0]
            },
            tableMeta: function() {
                return this.meta.columns[1]
            },
            objectMeta: function() {
                return this.meta.columns[2]
            },
            codeMeta: function() {
                return this.meta.columns[3]
            },
        }
    }
</script>

<style scoped>

</style>
