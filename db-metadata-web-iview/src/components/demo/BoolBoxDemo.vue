<template>
    <div>
        <h2>{{metaData.component_name}}: </h2>
        <component :is="metaData.component_name" v-model="value" :meta-data="metaData"></component>
        <br>
        值为: {{value}}
        <toggle-panel>
            <json-box v-model="metaData" mode="code"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "bool-box-demo",
        data () {
            return {
                value: true,
                metaData: {
                    component_name: 'BoolBox',
                    ui_config: {
                        label: '打开'
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
