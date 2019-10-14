<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :meta="meta"></component>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "file-box-demo",
        data () {
            return {
                meta: {
                    component_name: 'FileBox',
                    ui_config: {
                        action: '/file/upload', // *
                        multiple: true,
                    }
                },
            }
        },
    }
</script>

<style scoped>

</style>
