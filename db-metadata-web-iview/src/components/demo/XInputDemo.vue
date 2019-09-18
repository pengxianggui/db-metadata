<template>
    <div>
        <h2>xInput: </h2>
        <x-input v-model="name" :options="options"></x-input>
        值为: {{name}}
    </div>
</template>

<script>
    import XInput from '../atom/XInput'
    export default {
        name: "x-input-demo",
        data () {
            return {
                name: '',
                options: {
                    label: '姓名',
                    placeholder: '请输入姓名'
                }
            }
        },
        components: {
            XInput
        }
    }
</script>

<style scoped>

</style>