<template>
    <el-card>
        <h1>tip for {{componentName}}:</h1>
    </el-card>
</template>

<script>
    export default {
        name: "UiConfTip",
        props: {
            componentName: {
                type: String,
                required: true
            }
        },

    }
</script>

<style scoped>

</style>