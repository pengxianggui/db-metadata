<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        <br>
        值为: {{value}}
        <toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "bool-box-demo",
        data () {
            return {
                value: true,
                meta: {
                    component_name: 'BoolBox',
                    ui_config: {
                        label: '包邮'
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
