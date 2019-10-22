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
            <el-form-item :label="innerMeta.columns[0].label" :prop="innerMeta.columns[0].name">
                <drop-down-box :meta="innerMeta.columns[0]" v-model="model[innerMeta.columns[0].name]" @change="loadTables()"
                               :options.sync="schemaOptions"></drop-down-box>
            </el-form-item>
            <el-form-item :label="innerMeta.columns[1].label" :prop="innerMeta.columns[1].name">
                <drop-down-box :meta="innerMeta.columns[1]" v-model="model[innerMeta.columns[1].name]"
                               :options.sync="tableOptions"></drop-down-box>
            </el-form-item>
            <el-form-item :label="innerMeta.columns[2].label" :prop="innerMeta.columns[2].name">
                <text-box :meta="innerMeta.columns[2]" v-model="model[innerMeta.columns[2].name]"></text-box>
            </el-form-item>
            <el-form-item :label="innerMeta.columns[3].label" :prop="innerMeta.columns[3].name">
                <text-box :meta="innerMeta.columns[3]" v-model="model[innerMeta.columns[3].name]"></text-box>
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
    import Vue from 'vue'

    export default {
        name: "meta-import",
        data() {
            return {
                innerMeta: {},
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
                    Vue.set(_this.model, item.name, item.value || null)
                })
            },
            assemblyMethods() {
                // TODO
            },
            getDefaultMeta() {
                return DEFAULT.FormTmpl
            },
            initMeta() {
                let defaultMeta = this.getDefaultMeta();
                this.$merge(this.innerMeta, defaultMeta);
                this.$merge(this.innerMeta, this.meta);
            },
            loadSchema() {
                let _this = this;
                let schemaItem = _this.innerMeta.columns[0];
                if (!schemaItem.hasOwnProperty('data_url') || !schemaItem['data_url'])
                    return;
                _this.$axios.get(schemaItem['data_url']).then(resp => {
                    for (let i = 0; i < resp.data.length; i++) {
                        // schema data like : ['Main', 'Slave', ...]
                        let option = {
                            key: resp.data[i],
                            value: resp.data[i]
                        };
                        _this.schemaOptions.push(option)
                    }
                }).catch(resp => {
                    _this.$message({message: resp, type: 'error'})
                })
            },
            loadTables() {
                let _this = this;
                let url = _this.$complieString(_this['model'], _this.innerMeta.columns[1]['data_url']);
                // TODO 联动获取表名数据
                _this.$axios.get(url).then(resp => {
                    for (let i = 0; i < resp.data.length; i++) {
                        let option = {
                            key: resp.data[i],
                            value: resp.data[i]
                        };
                        _this.tableOptions.push(option)
                    }
                }).catch(resp => {
                    _this.$message.error(resp.msg)
                })
            },
            doSubmit() {
                let _this = this;
                if (_this.$listeners.submit) {
                    _this.$emit('submit', _this.model)
                } else {
                    this.$axios.post(_this.innerMeta.action, _this.model).then(resp => {
                        _this.options = resp.data
                    }).catch(resp => {
                        _this.$message.error(resp.msg)
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
                    // TODO default cancel behavior
                }
            }
        },
        created() {
            this.initMeta();
            this.assemblyModel();
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
