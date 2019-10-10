<template>
    <div>
        <h2>{{metaData.component_name}}: </h2>
        <component :is="metaData.component_name" v-model="value" :meta-data="metaData"></component>
        <label>值为：</label><code>{{value}}</code>
        <toggle-panel>
            <json-box v-model="metaData" mode="code"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "text-box-demo",
        data () {
            return {
                value: '',
                metaData: {
                    component_name: 'TextBox',
                    ui_config: {
                        label: '姓名',
                        placeholder: '请输入姓名',
                        autocomplete: 'OFF'
                    }
                },
            }
        }
    }
</script>

<style scoped>

</style>
