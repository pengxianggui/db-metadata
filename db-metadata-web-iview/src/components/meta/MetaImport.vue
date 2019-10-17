<!--
# meta
eg:
    {
        form_name: "formName",
        action: '/save', // form action (url)
        methods: 'POST',
        component_name: 'FormTmpl',
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
        btn: {
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
    <el-form :ref="meta['form_name']" v-bind="meta.conf" :model="model">
        <el-form-item :label="meta.columns[0].label" :prop="meta.columns[0].name">
            {{schemaOptions}}
<!--            <component :is="item.component_name" v-model="model[item.name]" :meta="item"></component>-->
            <drop-down-box :meta="meta.columns[0]" v-model="model[meta.columns[0].name]" @change="loadTables()" :options.sync="schemaOptions"></drop-down-box>
        </el-form-item>
        <el-form-item :label="meta.columns[1].label" :prop="meta.columns[1].name">
            <drop-down-box :meta="meta.columns[1]" v-model="model[meta.columns[1].name]" :options.sync="tableOptions"></drop-down-box>
        </el-form-item>
        <el-form-item :label="meta.columns[2].label" :prop="meta.columns[2].name">
            <text-box :meta="meta.columns[2]" v-model="model[meta.columns[2].name]"></text-box>
        </el-form-item>
        <el-form-item :label="meta.columns[3].label" :prop="meta.columns[3].name">
            <text-box :meta="meta.columns[3]" v-model="model[meta.columns[3].name]"></text-box>
        </el-form-item>
        <el-form-item>
            <el-button :id="meta.form_name + 'submit'" v-bind="meta.btn.submit.conf" @click="onSubmit"
                       v-text="meta.btn.submit.label"></el-button>
            <el-button :id="meta.form_name + 'cancel'"  v-bind="meta.btn.cancel.conf" @click="onCancel"
                       v-text="meta.btn.cancel.label"></el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Vue from 'vue'

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
            assemblyModel () {
                // this.model = this.meta.conf.model
                let _this = this
                _this.meta.columns.forEach(item => {
                    Vue.set(_this.model, item.name, item.value || null)
                })
            },
            assemblyMethods () {
                // todo
            },
            getDefaultMeta() {
                return DEFAULT.FormTmpl
            },
            initMeta() {
                this.meta.conf = this.meta.conf || {}
                this.meta.columns = this.meta.columns || []
                this.meta.btn = this.meta.btn || {}
                let defaultMeta = this.getDefaultMeta() || {}
                this.$merge(this.meta, defaultMeta)
            },
            loadSchema() {
                let _this = this
                _this.$axios({
                    methods: 'GET',
                    url: _this.meta.columns[0]['data_url']
                }).then(resp => {
                    if (resp.state === 'ok')
                        _this.schemaOptions = resp.data
                    else
                        _this.$message.error(resp.msg)
                })
            },
            loadTables() {
                let _this = this
                let value = _this.model[this.meta.columns[0].name]
                // TODO 联动获取表名数据
                _this.$axios({
                    methods: 'GET',
                    url: _this.meta.columns[0]['data_url'] + "?schema=" + value
                }).then(resp => {
                    if (resp.state === 'ok')
                        _this.tableOptions = resp.data
                    else
                        _this.$message.error(resp.msg)
                })
            },
            doSubmit() {
                let _this = this
                if (_this.$listeners.submit) {
                    _this.$emit('submit', _this.model)
                } else {
                    this.$axios({
                        methods: _this.meta.methods,
                        url: _this.meta.action,
                        data: _this.model
                    }).then(resp => {
                        if (resp['state'] === 'ok') {
                            _this.options = resp.data
                        } else {
                            _this.$message.error(resp['msg'])
                        }
                    })
                }
            },
            onSubmit(event) {
                let _this = this
                _this.$refs[_this.meta['form_name']].validate((valid) => {
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
                    // todo
                }
            }
        },
        created() {
            this.initMeta()
            this.assemblyModel()
            this.assemblyMethods()
        },
        mounted() {
            // request business data
            this.loadSchema()
        }
    }
</script>

<style scoped>

</style>
