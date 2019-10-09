<template>
    <div>
        <h2>{{componentName}}: </h2>
        <component :is="componentName" v-model="password" :options="options"></component>
        值为: {{password}}
    </div>
</template>

<script>
    export default {
        name: "pass-box-demo",
        data () {
            return {
                componentName: 'PassBox',
                password: '',
                options: {
                    label: '密码',
                    placeholder: '请输入密码',
                    autocomplete: 'NO'
                }
            }
        }
    }
</script>

<style scoped>

</style>
