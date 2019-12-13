<template>
    <el-radio-group v-model="nativeValue">
        <el-radio v-for="item in innerOptions"
                  :key="item.key"
                  :label="item.value"
                  v-bind="$reverseMerge(innerMeta.conf, $attrs)">
            {{item.key}}
        </el-radio>
    </el-radio-group>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '@/components/core/mixins/meta'
    import {options} from "@/components/core/mixins/methods";
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.RadioBox), Val, options],
        name: "RadioBox",
        label: "单选框",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: [Object, String]
        },
    }
</script>

<style scoped>

</style>
