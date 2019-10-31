<!--
# meta
description: meta data
eg:
    {
        "name": "",
        "data_url": "", // for options data
        "group": false, // 是否选项组模式
        "conf": {   // ui conf
            "clearable": true,
            "placeholder": "请下拉选择",
            "..."
        }
    }

# options
description: 选项数据(业务数据), 支持直接传入, 或者通过传入组件元数据, 在其中配置data_url参数, 从远端获取选项数据.
eg:
    [{
        key: 'xxx',
        value: 'yyy',
        disabled: false
    },
    ...],
若为选项组模式,
eg:
    [{
     label: "group1",
     options: [{
         key: "xxx",
         value: "yyy"
     }]
    }, {...}]

# custom behavior
## format: Function,
description: format option data, and return formatted data, like: [{key: "xxx", value: "yyy"}, ...]

 -->
<template>
    <el-select v-model="currValue"
               v-bind="innerMeta.conf"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <template v-if="!innerMeta.group">
            <el-option v-for="item in innerOptions" :key="item.value" :label="item.key"
                       :value="item.value ? item.value : item">
                {{item.key}}
            </el-option>
        </template>
        <template v-else>
            <el-option-group
                v-for="group in innerOptions"
                :key="group.label"
                :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.key"
                           :value="item.value ? item.value : item">
                    {{item.key}}
                </el-option>
            </el-option-group>
        </template>
    </el-select>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "drop-down-box",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: {
                type: [Object, String]
            },
            options: Array,
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            },
        },
        methods: {
            getOptions: function () {
                // http request options data by meta.data_url
                let url = this.innerMeta['data_url'];
                if (url) {
                    this.$axios.get(url).then(resp => {
                        // if provide format callback fn, execute callback fn
                        let options;

                        if (this.innerMeta.hasOwnProperty('behavior')
                            && this.innerMeta['behavior'].hasOwnProperty('format')) {
                            options = this.executeBehavior('format', resp.data);
                        } else { // default [{key: key1, value: value1}, ..]
                            options = resp.data;
                        }

                        this.innerOptions = options;
                        this.$emit('update:options', options);
                    }).catch(resp => {
                        this.$message({type: 'error', message: resp})
                    })
                }
            },
            initOptions: function () {
                if (this.options !== undefined) { // 父组件定义了options
                    this.innerOptions = this.options;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getOptions();
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
            executeBehavior(name, params) {
                if (this.innerMeta.behavior && this.innerMeta.behavior[name]) {
                    return this.innerMeta.behavior[name](params)
                } else {
                    console.error('${name} was not yet defined!')
                }
            },
        },
        created() {
        },
        mounted() {
            // init option data
            this.initOptions()
        },
        watch: {
            'innerMeta.data_url': {
                handler: function (n, o) {
                    if (n === o) return;
                    this.innerMeta['data_url'] = n;
                    this.getOptions();
                }
            }
        },
        computed: {
            currValue: {
                get: function () {
                    return this.value;
                },
                set: function (n) {
                    return this.$emit("input", n); // 通过 input 事件更新 model
                }
            },
            innerMeta() {
                return this.$merge(this.meta, DEFAULT.DropDownBox);
            }
        }
    }
</script>

<style scoped>

</style>
