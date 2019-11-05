<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
        <component :is="model.meta.component_name" :meta="model.meta"></component>
        <z-toggle-panel>
            <json-box v-model="model.meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "form-tmpl-demo",
        data () {
            return {
                model: {
                    meta: {
                        component_name: 'form-tmpl',
                        name: 'demoForm',
                        label: '测试表单',
                        action: '/demo/save',
                        conf: {},
                        columns: [
                            {
                                name: 'username',
                                label: '姓名',
                                component_name: 'TextBox',
                                conf: {}
                            }
                        ]
                    }
                }
            }
        },
        methods: {
            getFormMeta () {
                this.$axios.get("/form/toAdd/test_table").then(resp => {
                    this.model.meta = resp.data;
                }).catch(err => {
                })
            }
        },
        created() {
            this.getFormMeta();
        },
    }
</script>

<style scoped>

</style>
