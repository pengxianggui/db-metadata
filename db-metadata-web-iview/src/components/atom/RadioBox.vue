<template>
    <el-radio-group v-model="currValue">
        <el-radio v-for="item in options"
                  :key="item[conf.value]"
                  :label="item[conf.value]">
            {{item[conf.key]}}
        </el-radio>
    </el-radio-group>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "radio-box",
        data () {
            return {
                conf: {}
            }
        },
        props: {
            value: {
                type: [Object, String]
            },
            options: {
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
                return DEFAULT.RadioBox
            },
            initConf: function () {
                this.conf = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.conf, defaultConf)
            },
            getOptions: function () {
                this.$axios({
                    methods: 'GET',
                    url: '', // todo
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
            this.initConf()
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
