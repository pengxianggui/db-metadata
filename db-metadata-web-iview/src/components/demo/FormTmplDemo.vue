<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :object-code="meta.object_code"></component>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    import FormTmpl from '@/components/template/FormTmpl'
    export default {
        name: "form-tmpl-demo",
        data () {
            return {
                meta: {
                    component_name: 'form-tmpl',
                    object_code: "meta_object_code",
                }
            }
        },
        components: {
            "form-tmpl": FormTmpl
        }
    }
</script>

<style scoped>

</style>
