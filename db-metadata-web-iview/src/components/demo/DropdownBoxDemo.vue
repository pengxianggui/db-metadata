<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
        <component :is="model.meta.component_name" v-model="model.value" :meta="model.meta" :options="model.options"></component>
        值为: {{model.value}}
        <z-toggle-panel>
            <json-box v-model="model"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "drop-down-box-demo",
        data() {
            return {
                model: {
                    value: '1',
                    meta: {
                        component_name: 'DropDownBox',
                        group: true,
                        conf: {}
                    },
                    options: [{
                        label: "五百强公司",
                        options: [{
                            'key': '航天恒嘉',
                            'value': '1'
                        }, {
                            'key': '中国石化',
                            'value': '2'
                        }, {
                            'key': '阿里巴巴',
                            'value': '3'
                        }, {
                            'key': '小米科技',
                            'value': '4'
                        }]
                    }],
                }
            }
        },
    }
</script>

<style scoped>

</style>
