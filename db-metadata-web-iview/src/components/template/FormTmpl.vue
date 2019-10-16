<template>
    <el-form :ref="meta['form_name']" v-bind="meta.conf">
        <el-form-item v-for="(item, index) in meta.columns" :key="item.name + index"
                      :label="item.label" :prop="item.name">
            {{model}}
            <component :is="item.component_name" v-model="model[item.name]" :meta="item"></component>
        </el-form-item>
        <el-form-item>
            <el-button :id="meta.form_name + 'submit'" v-bind="meta.btn.conf" @click="onSubmit"
                       v-text="meta.btn.submit.label"></el-button>
            <el-button :id="meta.form_name + 'cancel'"  v-bind="meta.btn.conf" @click="onCancel"
                       v-text="meta.btn.cancel.label"></el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "form-tmpl",
        data() {
            return {
                model: {}
            }
        },
        props: {
            /*
             * meta component.
             * eg:
             * {
             *  form_name: "formName",
             *  action: '', // form action (url)
             *  methods: 'POST',
             *  conf: {
             *      "label-width": '80px',
             *      size: 'medium', // medium|small|mini
             *      model: {
             *          username: '',
             *          // ...
             *      },
             *      rules: {
             *          username: [{required: true, message: '用户名必填', trigger: 'blur'}, ...],
             *          // ...
             *      },
             *      // ...
             *  },
             *  columns: [{
             *      component_name: 'TextBox',
             *      name: 'username',
             *      label: '用户名',
             *      conf: {
             *          clearable: "true",
             *          placeholder: "请输入姓名..",
             *          // ...
             *      }
             *  }, ...],
             *  btn: {
             *      'submit': {
             *          label: '提交',
             *          conf: {
             *              // ... support conf of el-button
             *          }
             *      },
             *      'cancel': {
             *          label: '取消',
             *          conf: {
             *              // ... support conf of el-button
             *          }
             *      }
             *  }
             * }
             */
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        methods: {
            assemblyModel () {
                this.model = this.meta.conf.model
            },
            getDefaultMeta() {
                return DEFAULT.FormTmpl
            },
            initMeta() {
                this.meta.conf = this.meta.conf || {}
                this.meta.columns = this.meta.columns || []
                this.meta.btn = this.meta.btn || {}
                let defaultMeta = this.getDefaultMeta() || {}
                this.merge(this.meta, defaultMeta)
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
                this.$refs[this.meta['form_name']].validate((valid) => {
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
                    // TODO
                }
            }
        },
        created() {
            this.initMeta()
            this.assemblyModel()
        },
        mounted() {
            // request business data
        }
    }
</script>

<style scoped>

</style>
