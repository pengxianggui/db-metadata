<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        值为: {{value}}
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "text-area-box-demo",
        data () {
            return {
                value: '',
                meta: {
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
