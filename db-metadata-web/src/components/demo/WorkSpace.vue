<template>
    <div style="padding: 20px;">
        <h1>组件开发调试页面</h1>

        <text-area-box v-model="value"></text-area-box>
        <rich-text-box v-model="value"></rich-text-box>
    </div>
</template>

<script>

    export default {
        name: "WorkSpace",
        data() {
            return {
                value: "test value"
            }
        },
        methods: {}
    }
</script>

<style scoped>

</style>
