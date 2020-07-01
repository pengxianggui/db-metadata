<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta" :options="options"></component>
        <br>
        值为: {{value}}
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "radio-box-demo",
        data () {
            return {
                value: null,
                meta: {
                    component_name: 'RadioBox',
                    conf: {
                    },
                },
                options: [{
                    'key': '航天恒嘉',
                    'value': '1'
                },{
                    'key': '中国石化',
                    'value': '2'
                },{
                    'key': '阿里巴巴',
                    'value': '3'
                },{
                    'key': '小米科技',
                    'value': '4'
                }]
            }
        }
    }
</script>

<style scoped>

</style>
