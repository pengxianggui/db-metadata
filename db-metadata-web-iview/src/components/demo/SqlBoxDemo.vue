<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        <br>
        值为: {{value}}
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "sql-box-demo",
        data () {
            return {
                value: 'select * ',
                meta: {
                    component_name: 'SqlBox'
                }
            }
        }
    }
</script>

<style scoped>

</style>
