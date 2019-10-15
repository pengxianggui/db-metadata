<template>
    <el-select v-model="currValue"
               v-bind="conf"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <el-option v-for="item in options" :key="item[conf.value]" :label="item[conf.key]"
            :value="item[conf.value] ? item[conf.value] : item">
            {{item[conf.value]}}-{{item[conf.key]}}
        </el-option>
    </el-select>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "drop-down-box",
        data () {
            return {
                conf: {}
            }
        },
        props: {
            value: {
                type: [Object, String]
            },
            options: { // 选项数据(业务数据), 优先取传入值, 默认取服务端数据
                required: false,
                type: Array,
                default: function () {
                    return []
                }
            },
            meta: {
                type: Object,
                default: function () {
                    return {
                        ui_config: {}
                    }
                }
            }
        },
        methods: {
            getDefaultConf: function() {
                return DEFAULT.DropDownBox
            },
            initConf: function () {
                this.conf = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.conf, defaultConf)
            },
            getOptions: function () {
                // todo http request options data by meta.object_code、 meta.en
                let _this = this
                this.$axios({
                    methods: 'GET',
                    url: _this.conf['data-url'],
                    data: {
                    }
                }).then(resp => {
                    if (resp.state === 'ok') {
                        // 成功
                    } else {
                        // 失败
                    }
                })
            },
            initOptions: function () {
                if (this.options.length === 0) this.getOptions()
            }
        },
        created() {
            // init data
            this.initConf()
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
