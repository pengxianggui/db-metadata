<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        <br>
        值为: {{value}}
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "check-box-demo",
        data () {
            return {
                value: [],
                meta: {
                    component_name: 'CheckBox',
                    label: 'name',
                    value: 'value', // 可省略, value不指定时, 则选取的值即为整个item对象
                    ui_config: {
                    },
                    options: [{
                        'name': '航天恒嘉',
                        'value': '1'
                    },{
                        'name': '中国石化',
                        'value': '2'
                    },{
                        'name': '阿里巴巴',
                        'value': '3'
                    },{
                        'name': '小米科技',
                        'value': '4'
                    }]
                }
            }
        }
    }
</script>

<style scoped>

</style>
