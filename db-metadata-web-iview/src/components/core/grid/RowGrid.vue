<template>
    <el-row style="height: 100%">
        <el-col :span="leftSpan" style="overflow-x:hidden; overflow-y: auto; height: 100%">
            <slot name="left"></slot>
        </el-col>
        <el-col :span="rightSpan" style="overflow-x:hidden; overflow-y: auto; height: 100%">
            <slot name="right"></slot>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "RowGrid",
        props: {
            span: Array
        },
        computed: {
            leftSpan() {
                return this.span[0];
            },
            rightSpan() {
                return this.span[1];
            }
        }
    }
</script>

<style scoped>

</style>