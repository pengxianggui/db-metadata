<template>
    <el-container>
        <list style="width: 100%">
            <list-item v-for="(item, index) in data" :key="index">
                <span>{{item.label}}</span>
            </list-item>
        </list>
    </el-container>
</template>

<script>
    import utils from '@/utils'

    export default {
        name: "DataList",
        props: {
            data: Array,
            labelProps: {
                type: Object,
                default: () => {
                    return {label: 'label'}
                }
            }
        },
        data() {
            return {}
        }
    }
</script>

<style scoped>

</style>