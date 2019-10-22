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

# custom event
## format
type: Function,
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
            <el-option v-for="item in options" :key="item.value" :label="item.key"
                       :value="item.value ? item.value : item">
                {{item.key}}
            </el-option>
        </template>
        <template v-else>
            <el-option-group
                    v-for="group in options"
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
    import utils from '@/utils'

    export default {
        name: "drop-down-box",
        data() {
            return {
                innerMeta: {}
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
            getDefaultMeta: function () {
                return DEFAULT.DropDownBox
            },
            initMeta: function () {
                let defaultMeta = this.getDefaultMeta();
                this.$merge(this.innerMeta, defaultMeta);
                this.$merge(this.innerMeta, this.meta);
            },
            getOptions: function () {
                // http request options data by meta.data_url
                let _this = this;
                _this.$axios.get(_this.innerMeta['data_url']).then(resp => {
                    // if provide format callback fn, execute callback fn
                    _this.options = utils.converKv1(resp.data)
                }).catch(resp => {
                    _this.$message.error(resp.toString())
                })
            },
            initOptions: function () {
                if (this.options) return
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getOptions()
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
            renderMethods: function () {
                // TODO
                // if (!this.innerMeta.methods || Object.keys(this.innerMeta.methods).length <= 0) return
                // for (let methodName in this.innerMeta.methods) {
                //     let fn = this.innerMeta.methods[methodName]
                    // this.$on(methodName, function (data) {
                    //     let options = []
                    //     for (let j = 0; j < data.length; j++) {
                    //         options.push({
                    //             key: data[j],
                    //             value: data[j]
                    //         })
                    //     }
                    //     return options
                    // })
                // }
            }
        },
        created() {
            // init meta
            this.initMeta()
            // render method
            this.renderMethods()
        },
        mounted() {
            // init option data
            this.initOptions()
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
