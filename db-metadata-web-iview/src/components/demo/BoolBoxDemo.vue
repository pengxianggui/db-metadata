<template>
    <div>
        <h2>{{componentName}}: </h2>
        <component :is="componentName" v-model="boolVal" :options="options"></component>
        <br>
        值为: {{boolVal}}
    </div>
</template>

<script>
    export default {
        name: "bool-box-demo",
        data () {
            return {
                componentName: 'BoolBox',
                boolVal: true,
                options: {
                    label: '确定？'
                }
            }
        }
    }
</script>

<style scoped>

</style>
