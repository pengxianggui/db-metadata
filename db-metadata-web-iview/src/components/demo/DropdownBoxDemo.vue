<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
<!--        <el-button @click="refreshOptions">刷新options</el-button>-->
        <component :is="model.meta.component_name" v-model="model.value" :ref="model.meta.name"
                   :meta="model.meta"></component>
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
                    value: ['1', '2'],
                    meta: {
                        name: "DropDownBoxDemo",
                        component_name: 'DropDownBox',
                        group: true,
                        conf: {
                            multiple: true
                        },
                        options: [{
                            'key': '航天恒嘉',
                            'value': '1',
                            'group': "世界五百强"
                        }, {
                            'key': '中国移动',
                            'value': '2',
                            'group': "SB公司"
                        }, {
                            'key': '阿里巴巴',
                            'value': '3',
                            'group': "世界五百强"
                        }, {
                            'key': '小米科技',
                            'value': '4',
                            'group': "手机厂商"
                        }, {
                            'key': '华为公司',
                            'value': '5',
                            'group': "手机厂商"
                        }]
                    }
                }
            }
        },
        methods: {
            refreshOptions() {
                let son = this.$refs[this.model.meta.name];
                son.getOptions();
            }
        }
    }
</script>

<style scoped>

</style>
