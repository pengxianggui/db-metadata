<template>
    <div>
        <h2>{{metaData.component_name}}: </h2>
        <component :is="metaData.component_name" v-model="value" :meta-data="metaData"></component>
        值为：{{value}}
    </div>
</template>

<script>
    // 引入vue-json-editor模块
    import vueJsonEditor from 'vue-json-editor'
    export default {
        name: 'json-box-demo',
        data () {
            return {
                value: {
                    'name': '张三'
                },
                metaData: {
                    component_name: 'JsonBox',
                    ui_config: {
                    }
                },
            }
        },
        // 注入vueJsonEditor组件
        components: {
            vueJsonEditor
        }
    }
</script>
