<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        <label>值为：</label><code>{{value}}</code>
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "text-box-demo",
        data() {
            return {
                value: '',
                meta: {
                    "id": "1",
                    "object_code": "meta_object_code",
                    "is_primary": "true",
                    "en": "id",
                    "cn": "ID",
                    "order_num": 1,
                    "component_name": "TextBox",
                    "ui_config": {
                        "show_label": true,
                        // label: '姓名',
                        // autocomplete: 'OFF'
                        // ...
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
