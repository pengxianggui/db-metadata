<template>
    <vue-json-editor v-model="currValue" :modes="meta.ui_config.modes" :mode="meta.ui_config.mode"></vue-json-editor>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import vueJsonEditor from 'vue-json-editor'
    export default {
        components: {
            vueJsonEditor
        },
        data () {
          return {
          }
        },
        props: {
            value: {
                type: Object
            },
            meta: {
                type: Object,
                default: function () {
                    return {
                    }
                }
            },
        },
        methods: {
            onError: function () {
            },
            getDefaultConf: function() {
                return DEFAULT.JsonBox
            },
            initConf: function () {
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.$merge(this.meta.ui_config, defaultConf)
            }
        },
        created() {
            this.initConf()
        },
        computed: {
            currValue: {
                get: function() {
                    return this.value;
                },
                set: function(newValue) {
                    this.$emit("input", newValue); // 通过 input 事件更新 model
                }
            }
        }
    };
</script>
