<template>
    <div>
        <h2>{{metaData.component_name}}: </h2>
        <component :is="metaData.component_name" v-model="value" :meta-data="metaData"></component>
        <br>
        <label>值为：</label><code>{{value}}</code>
        <toggle-panel>
            <json-box v-model="metaData" mode="code"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "date-box-demo",
        data () {
            return {
                value: null,
                metaData: {
                    component_name: 'DateBox',
                    ui_config: {
                        // "value-format": 'Date'
                        "clearable": true,
                        'placeholder': '输入日期..',
                        // set more
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
