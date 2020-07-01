<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
        <component :is="model.meta.component_name" :meta="model.meta">
            <!--            <template #bhv-cancel="{on, actions}">-->
            <!--                <cancel-on-click v-bind="{on, actions}"></cancel-on-click>-->
            <!--            </template>-->
            <!--            <template #bhv-submit="{on, actions}">-->
            <!--                <submit-on-click v-bind="{on, actions}"></submit-on-click>-->
            <!--            </template>-->
        </component>
        <z-toggle-panel>
            <json-box v-model="model.meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "form-view-demo",
        // components: {
        //     'cancel-on-click': {
        //         props: ['on', 'actions'],
        //         render: () => null,
        //         created() {
        //             this.on('cancel', (ev) => {
        //                 alert('无渲染组件插槽, 替换内部onCancel逻辑!');
        //             });
        //         }
        //     },
        //     'submit-on-click': {
        //         props: ['on', 'actions'],
        //         render: () => null,
        //         created() {
        //             this.on('submit', (ev) => {
        //                 alert('无渲染组件插槽, 替换内部的onSubmit逻辑');
        //             })
        //         }
        //     }
        // },
        data() {
            return {
                model: {
                    meta: {
                        component_name: 'form-view',
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
            getFormMeta() {
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
