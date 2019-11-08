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
    <el-form :ref="innerMeta['name']" v-bind="innerMeta.conf" :model="model">
        <el-form-item v-for="(item, index) in innerMeta.columns" :key="item.name + index"
                      :label="item.label" :prop="item.name" :class="{inline: item.inline}">
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
                model: {}
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
            doSubmit(ev) {
                const fn = 'submit';
                const params = this.model;
                const action = this.innerMeta['action'];

                if (this.$listeners.hasOwnProperty(fn)) {
                    this.$emit(fn, params);
                } else {
                    let url = this.$compile(action, {objectCode: this.innerMeta['objectCode']});
                    params['objectCode'] = this.innerMeta['objectCode'];
                    this.$axios.post(url, params).then(resp => {
                        this.$emit('ok', params); //  default callback
                        this.$message.success(resp.msg);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    })
                }
            },
            onSubmit(ev) {
                const refName = this.innerMeta['name'];
                this.$refs[refName].validate((valid) => {
                    if (valid) {
                        this.doSubmit(ev) // do submit
                    } else {
                        return false;
                    }
                });
            },
            onCancel: function (event) {
                if (this.$listeners.cancel) {
                    this.$emit('cancel', event);
                    return;
                }
            },
            assemblyModel(columns) {
                let keys = Object.keys(this.model);
                let names = [];
                columns.forEach(item => {
                    names.push(item['name']);
                    if (keys.indexOf(item['name']) === -1) {
                        this.$set(this.model, item['name'], item['value'] || null);
                    }
                });
                keys.forEach(key => {
                    if (names.indexOf(key) === -1) {
                        this.$delete(this.model, key);
                    }
                })
            }
        },
        computed: {
            innerMeta () {
                let meta = this.$merge(this.meta, DEFAULT.FormTmpl);
                this.assemblyModel(meta['columns']);
                return meta;
            }
        }
    }
</script>

<style scoped>

</style>
