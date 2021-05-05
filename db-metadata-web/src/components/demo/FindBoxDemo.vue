<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta" style="width: 300px;"></component>
        <label>值为：</label><code>{{value}}</code>
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "FindBoxDemo",
        data() {
            return {
                value: null,
                meta: {
                    "component_name": "FindBox",
                    "name": "FindBox",
                    "label": "查找框",
                    "data_url": "/find/meta/test_table?f=col_findbox", // 这个data_url响应的是FindPanel的meta, 也可是TableView的meta
                    "conf": {
                        "clearable": true,
                        "placeholder": "戳我展开搜索面板..",
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
