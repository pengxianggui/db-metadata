<template>
    <el-checkbox-group v-model="nativeValue" @change="handleChange">
        <el-checkbox v-for="item in innerOptions"
                     :key="item.key"
                     :label="item.value | stringify"
                     v-bind="$reverseMerge(innerMeta.conf, $attrs)">
            <slot name="label" v-bind:option="item">
                {{item.key}}
            </slot>
        </el-checkbox>
    </el-checkbox-group>
</template>

<script>
    import Meta from '../../mixins/meta'
    import options from "../../mixins/options"
    import Val from '../../mixins/value'
    import conver from './conver'
    import reverse from './reverse'
    import DefaultMeta from '../ui-conf'
    import {isArray} from "../../../utils/common";

    export default {
        mixins: [Meta(DefaultMeta), Val(conver, reverse), options],
        name: "CheckBox",
        label: "复选框",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: {
                type: [Array, String]
            }
        },
        methods: {
          handleChange(value) {
            if (!isArray(value)) {
              value = [value]
            }
            const {innerOptions} = this
            const activeOption = innerOptions.filter(o => value.indexOf(o.value) > -1)
            this.$emit('change', {value, activeOption})
          }
        }
    }
</script>

<style scoped>

</style>
