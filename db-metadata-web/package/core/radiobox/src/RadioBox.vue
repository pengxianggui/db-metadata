<template>
    <el-radio-group v-model="nativeValue">
        <el-radio v-for="item in innerOptions"
                  :key="item.key"
                  :label="item.value | stringify"
                  v-bind="$reverseMerge(meta.conf, $attrs)"
                  @change="handleChange">
            <slot :name="item.value | stringify" v-bind:option="item">
                {{item.key}}
            </slot>
        </el-radio>
    </el-radio-group>
</template>

<script>
    import Meta from '../../mixins/meta'
    import options from "../../mixins/options"
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
        },
        methods: {
            handleChange(value) {
              const {innerOptions} = this
              const activeOption = innerOptions.find(o => o.value === value)
              this.$emit('change', {value, activeOption})
            }
        }
    }
</script>

<style scoped>

</style>
