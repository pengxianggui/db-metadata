<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :meta="meta"></component>
        <toggle-panel>
            <json-box v-model="meta"></json-box>
        </toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "img-box-demo",
        data () {
            return {
                meta: {
                    component_name: 'ImgBox',
                    ui_config: {
                        action: '/img/upload', // *
                        multiple: true,
                        "auto-upload": true,
                    }
                },
            }
        },
    }
</script>

<style scoped>

</style>
