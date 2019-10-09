<template>
    <div>
        <h2>{{componentName}}: </h2>
        <component :is="componentName" v-model="name" :options="options"></component>
        值为: {{name}}
    </div>
</template>

<script>
    export default {
        name: "text-area-box-demo",
        data () {
            return {
                componentName: 'TextAreaBox',
                name: '',
                options: {
                    label: '内容',
                    placeholder: '请输入内容',
                    autocomplete: 'OFF'
                }
            }
        }
    }
</script>

<style scoped>

</style>
