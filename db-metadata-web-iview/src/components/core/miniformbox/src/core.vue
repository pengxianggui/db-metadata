<template>
    <el-form v-bind="$reverseMerge(innerMeta.conf, $attrs)" :model="value" size="mini">
        <template v-for="(item, index) in innerMeta.columns">
            <el-form-item :key="item.name + index" :label="item.label||item.name" :prop="item.name"
                          :class="{'inline': item.inline, 'width-align': item.inline}">
                <component :is="item.component_name" v-model="value[item.name]" :meta="item"></component>
            </el-form-item>
        </template>
    </el-form>
</template>

<script>
    import {DEFAULT} from "@/constant";
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    export default {
        name: "MiniForm",
        label: "迷你表单",
        description: "输入控件的一种,JsonBox的表单表现形式",
        mixins: [Meta(DEFAULT.FormView), Val()],
        props: {
            value: {
                type: [Object, String],
                default: function () {
                    return {}
                }
            }
        }
    }
</script>

<style scoped>

</style>