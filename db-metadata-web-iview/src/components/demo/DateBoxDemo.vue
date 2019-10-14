<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        <br>
        <label>值为：</label><code>{{value}}</code>
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "date-box-demo",
        data () {
            return {
                value: null,
                meta: {
                    component_name: 'DateBox',
                    ui_config: {
                        "clearable": true,
                        'placeholder': '输入日期..',
                        // "value-format": 'Date'
                        // set more
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
