<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        值为：{{value}}
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
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
                meta: {
                    component_name: 'JsonBox',
                    ui_config: {
                        mode: 'code',
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
