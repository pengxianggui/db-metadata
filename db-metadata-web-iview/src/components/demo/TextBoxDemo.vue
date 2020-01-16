<template>
    <div>
        <h2>{{model.meta.component_name}}: </h2>
        <component :is="model.meta.component_name" v-model="model.value" :meta="model.meta" @blur="blurHandler"></component>
        <label>值为：</label><code>{{model.value}}</code>
        <z-toggle-panel>
            <json-box v-model="model" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "text-box-demo",
        data() {
            return {
                model: {
                    value: null,
                    meta: {
                        "component_name": "TextBox",
                        "conf": {}
                    }
                }
            }
        },
        methods: {
            blurHandler(ev) {
                ev.target.style.color = 'red';
            }
        }
    }
</script>

<style scoped>

</style>
