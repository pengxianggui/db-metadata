<template>
    <el-select v-model="nativeValue"
               v-bind="$reverseMerge(innerMeta.conf, $attrs)"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <template v-if="!innerMeta.group">
            <slot name="options" v-bind:options="innerOptions">
                <el-option v-for="item in innerOptions" :key="item.value" :label="item.key||item.value"
                           :value="item.value | stringify">
                    <slot name="label" v-bind:option="item">
                        {{item.key||item.value}}
                    </slot>
                </el-option>
            </slot>
        </template>
        <template v-else>
            <el-option-group
                    v-for="group in groupify(innerOptions)"
                    :key="group.label"
                    :label="group.label">
                <slot name="options" v-bind:options="innerOptions">
                    <el-option v-for="item in group.options" :key="item.value" :label="item.key||item.value"
                               :value="(item.value ? item.value : item) | stringify">
                        <slot name="label" v-bind:options="item">
                            {{item.key||item.value}}
                        </slot>
                    </el-option>
                </slot>
            </el-option-group>
        </template>
    </el-select>
</template>

<script>
    import utils from '@/utils'
    import Meta from '../../mixins/meta'
    import {options} from "../../mixins/methods";
    import Val from '../../mixins/value'
    import conver from './conver'
    import reverse from './reverse'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val(conver, reverse), options],
        name: "DropDownBox",
        label: "下拉框",
        inheritAttrs: true,
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: {
                type: [String, Number, Boolean, Array]  // DropDownBox可能是单选, 也可能是多选
            }
        },
        methods: {
            groupify(options) {
                let groupOptions = [];
                let groups = {};
                options.forEach(ele => {
                    let groupName = utils.hasProp(ele, 'group') ? ele['group'] : '其它';

                    let hasKey = utils.hasProp(groups, groupName);
                    if (hasKey) {
                        groups[groupName].push(ele);
                    } else {
                        groups[groupName] = [ele];
                    }
                });

                for (let key in groups) {
                    groupOptions.push({
                        "label": key,
                        "options": groups[key]
                    });
                }

                return groupOptions;
            }
        },
        computed: {
            multiple() {    // is multiple value
                let {innerMeta} = this;
                return (utils.hasProp(innerMeta, 'conf') && innerMeta['conf']['multiple'] === true);
            }
        }
    }
</script>

<style scoped>

</style>
