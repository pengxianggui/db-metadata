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
                innerMeta: {},
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
            initMeta: function () {
                this.$merge(this.innerMeta, DEFAULT.DropDownBox);
                this.$merge(this.innerMeta, this.meta);
            },
            getOptions: function () {
                // http request options data by meta.data_url
                let _this = this;
                if (_this.innerMeta['data_url']) {
                    _this.$axios.get(_this.innerMeta['data_url']).then(resp => {
                        // if provide format callback fn, execute callback fn
                        if (_this.innerMeta.behavior && _this.innerMeta.behavior['format']) {
                            _this.innerOptions = _this.executeBehavior('format', resp.data);
                        } else { // default [{key: key1, value: value1}, ..]
                            _this.innerOptions = resp.data;
                        }
                    }).catch(resp => {
                        _this.$message({type: 'error', message: resp})
                    })
                }
            },
            initOptions: function () {
                // deep copy to innerOptions
                this.innerOptions = this.options;
                if (this.innerOptions) return;
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getOptions();
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
            executeBehavior (name, params) {
                if (this.innerMeta.behavior && this.innerMeta.behavior[name]) {
                    return this.innerMeta.behavior[name](params)
                } else {
                    console.error('${name} was not yet defined!')
                }
            },
        },
        created() {
            // init meta
            this.initMeta();
        },
        mounted() {
            // init option data
            this.initOptions()
        },
        watch: {
            'meta.data_url': {
                handler: function (n, o) {
                    if (n === o) return;
                    this.innerMeta['data_url'] = n;
                    this.getOptions();
                },
                deep: true
            }
        },
        computed: {
            currValue: {
                get: function () {
                    return this.value
                },
                set: function (newValue) {
                    return this.$emit("input", newValue); // 通过 input 事件更新 model
                }
            }
        }
    }
</script>

<style scoped>

</style>
