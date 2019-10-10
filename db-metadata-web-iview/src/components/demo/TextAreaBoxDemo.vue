<template>
    <div>
        <h2>{{metaData.component_name}}: </h2>
        <component :is="metaData.component_name" v-model="value" :meta-data="metaData"></component>
        值为: {{value}}
        <toggle-panel>
            <json-box v-model="metaData" mode="code"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "text-area-box-demo",
        data () {
            return {
                value: '',
                metaData: {
                    component_name: 'TextAreaBox',
                    ui_config: {
                        label: '内容',
                        placeholder: '请输入内容',
                        autocomplete: 'OFF'
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
