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
        data () {
            return {
                value: '',
                meta: {
                    component_name: 'TextBox',
                    ui_config: {
                        label: '姓名',
                        autocomplete: 'OFF'
                    }
                },
            }
        }
    }
</script>

<style scoped>

</style>
