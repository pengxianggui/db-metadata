<!--
# meta
eg:
    {
        name: "formName",
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
    <el-form :ref="meta['name']" v-bind="meta.conf" :model="model">
        <el-form-item v-for="(item, index) in meta.columns" :key="item.name + index"
                      :label="item.label" :prop="item.name">
            <component :is="item.component_name" v-model="model[item.name]" :meta="item"></component>
        </el-form-item>
        <el-form-item>
            <el-button :id="meta.name + 'submit'" v-bind="meta.btns.submit.conf" @click="onSubmit"
                       v-text="meta.btns.submit.label"></el-button>
            <el-button :id="meta.name + 'cancel'" v-bind="meta.btns.cancel.conf" @click="onCancel"
                       v-text="meta.btns.cancel.label"></el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Vue from 'vue'

    export default {
        name: "form-tmpl",
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
            assemblyModel () {
                let _this = this;
                _this.meta.columns.forEach(item => {
                    Vue.set(_this.model, item.name, item.value || null) // 这种赋值方法, 双向绑定才生效
                })
            },
            getDefaultMeta() {
                return DEFAULT.FormTmpl
            },
            initMeta() {
                let defaultMeta = this.getDefaultMeta();
                this.$merge(this.meta, defaultMeta)
            },
            doSubmit() {
                let _this = this;
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
                            _this.$message({type: 'error', message: resp.msg})
                        }
                    })
                }
            },
            onSubmit(event) {
                let _this = this;
                _this.$refs[_this.meta['name']].validate((valid) => {
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
                }
            }
        },
        created() {
            this.initMeta();
            this.assemblyModel();
        }
    }
</script>

<style scoped>

</style>
