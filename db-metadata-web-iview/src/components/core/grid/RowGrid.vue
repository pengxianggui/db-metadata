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
    import utils from '@/utils'

    export default {
        name: "RowGrid",
        props: {
            span: {
                type: Array,
                validator: (value) => value.length === 2 && value.every(ele => utils.isNumber(ele))
            }
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