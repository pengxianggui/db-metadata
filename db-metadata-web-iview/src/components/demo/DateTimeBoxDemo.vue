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
        name: "date-time-box-demo",
        data () {
            return {
                value: null,
                metaData: {
                    component_name: 'DateTimeBox',
                    ui_config: {
                        "clearable": true,
                        // "value-format": 'Date',
                        // set more
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
