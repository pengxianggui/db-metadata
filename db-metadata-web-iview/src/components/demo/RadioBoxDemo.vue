<template>
    <div>
        <h2>{{metaData.component_name}}: </h2>
        <component :is="metaData.component_name" v-model="value" :meta-data="metaData"></component>
        <br>
        值为: {{value}}
        <toggle-panel>
            <json-box v-model="metaData"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "radio-box-demo",
        data () {
            return {
                value: '1',
                metaData: {
                    component_name: 'RadioBox',
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
