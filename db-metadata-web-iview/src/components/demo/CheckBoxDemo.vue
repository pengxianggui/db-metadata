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
                    name: 'test', // 可省略, value不指定时, 则选取的值即为整个item对象
                    label: '测试',
                    conf: {
                        // other options
                    }
                },
                // meta: {
                //     "component_name": "CheckBox",
                //     "conf": {},
                //     "data_url": "/dict/yn",
                //     "inline": true,
                //     "label": "系统模块",
                //     "name": "is_sys"
                // },
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
