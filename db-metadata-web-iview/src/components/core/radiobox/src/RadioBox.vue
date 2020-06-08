<template>
    <el-radio-group v-model="nativeValue">
        <el-radio v-for="item in innerOptions"
                  :key="item.key"
                  :label="item.value | stringify"
                  v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                  @change="$emit('change')">
            <slot name="label" v-bind:option="item">
                {{item.key}}
            </slot>
        </el-radio>
    </el-radio-group>
</template>

<script>
    import Meta from '../../mixins/meta'
    import {options} from "../../mixins/methods";
    import Val from '../../mixins/value'
    import conver from './conver'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val(conver), options],
        name: "RadioBox",
        label: "单选框",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: [String, Number, Boolean]
        }
    }
</script>

<style scoped>

</style>
