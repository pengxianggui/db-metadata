<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
        <component :is="model.meta.component_name" :meta="model.meta"></component>
        <z-toggle-panel>
            <json-box v-model="model"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    import FormTmpl from '@/components/template/FormTmpl'
    export default {
        name: "form-tmpl-demo",
        data () {
            return {
                model: {
                    meta: {
                        component_name: 'form-tmpl',
                        form_name: 'demoForm'
                    }
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
