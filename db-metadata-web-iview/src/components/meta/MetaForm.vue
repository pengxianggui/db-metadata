<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
        <component :is="model.meta.component_name" :meta="model.meta"></component>
        <z-toggle-panel>
            <json-box v-model="model"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    import FormTmpl from '@/components/template/FormTmpl'

    export default {
        name: "MetaForm",
        data() {
            return {
                model: {
                    meta: {
                        name: "formName",
                        action: '/save', // form action (url)
                        methods: 'POST',
                        component_name: 'form-tmpl',
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
                                disabled: true,
                                clearable: true,
                                placeholder: "请输入姓名..",
                                // ...
                            }
                        }, {
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
                }
            }
        },
        components: {
            "form-tmpl": FormTmpl
        }
    }
</script>

<style scoped>

</style>
