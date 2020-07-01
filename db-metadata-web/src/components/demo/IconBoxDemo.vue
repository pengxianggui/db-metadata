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
        name: "icon-box-demo",
        data () {
            return {
                value: '',
                meta: {
                    component_name: 'IconBox'
                }
            }
        }
    }
</script>

<style scoped>

</style>
