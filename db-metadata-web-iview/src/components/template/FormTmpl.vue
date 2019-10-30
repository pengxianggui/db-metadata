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
    <el-form :ref="innerMeta['name']" v-bind="innerMeta.conf" :model="model">
        <el-form-item v-for="(item, index) in innerMeta.columns" :key="item.name + index"
                      :label="item.label" :prop="item.name">
            <component :is="item.component_name" v-model="model[item.name]" :meta="item"></component>
        </el-form-item>
        <el-form-item>
            <el-button :id="innerMeta.name + 'submit'" v-bind="innerMeta.btns.submit.conf" @click="onSubmit"
                       v-text="innerMeta.btns.submit.label"></el-button>
            <el-button :id="innerMeta.name + 'cancel'" v-bind="innerMeta.btns.cancel.conf" @click="onCancel"
                       v-text="innerMeta.btns.cancel.label"></el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "FormTmpl",
        data() {
            return {
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            },
            value: {
                type: Object,
                default: function () {
                    return {}
                }
            },
        },
        methods: {
            assemblyModel () {
                let _this = this;
                _this.innerMeta.columns.forEach(item => {
                    _this.$set(_this.model, item.name, item.value || null);
                })
            },
            doSubmit() {
                let _this = this;
                if (_this.$listeners.submit) {
                    _this.$emit('submit', _this.model)
                } else {
                    this.$axios.post(_this.innerMeta.action, _this.model).then(resp => {
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
                }
            }
        },
        created() {
            this.assemblyModel();
        },
        computed: {
            innerMeta: {
                get: function () {
                    return this.$merge(this.meta, DEFAULT.FormTmpl);
                },
                set: function (n) {
                    return this.$emit("update:meta", n)
                }
            },
            model: {
                get: function () {
                    return this.value;
                },
                set: function (n) {
                    return this.$emit('input', n)
                }
            }
        }
    }
</script>

<style scoped>

</style>
