<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="buildMeta(value)"></component>
        <br>
        值为: {{value}}
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    import buildMeta from '@/../package/meta/buildMeta'
    export default {
        name: "mini-form-box-demo",
        data() {
            return {
                value: {
                    "inline": false,
                    "component_name": "TextBox",
                    "name": "id",
                    "conf": {
                        "maxlength": 32,
                        "rules": [
                            {
                                "trigger": [
                                    "blur"
                                ],
                                "type": "string",
                                "message": "[主键]是必须填写的",
                                "required": true
                            }
                        ]
                    },
                    "label": "主键"
                },
                meta: {
                    component_name: 'MiniFormBox',
                    name: 'MiniFormBox',
                    label: '迷你表单',
                    // other options
                    conf: {
                        // other options
                    }
                }
            }
        },
        methods: {
            buildMeta(value) {
                return buildMeta('MiniFormBox', value);
            }
        }
    }
</script>

<style scoped>

</style>
