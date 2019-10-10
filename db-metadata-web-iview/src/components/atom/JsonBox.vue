<template>
    <vue-json-editor v-model="currValue" :modes="metaData.ui_config.modes||modes" :mode="metaData.ui_config.mode||mode"></vue-json-editor>
</template>

<script>
    import vueJsonEditor from 'vue-json-editor'
    export default {
        components: {
            vueJsonEditor
        },
        data () {
          return {
              modes: ["tree", "code", 'text'] // all : ["tree", "code", "form", "text", "view"];
          }
        },
        props: {
            value: {
                type: Object
            },
            metaData: {
                type: Object,
                default: function () {
                    return {
                        ui_config: {}
                    }
                }
            },
            mode: {
                type: String,
                default: function () {
                    return 'code'
                }
            }
        },
        methods: {
            onError: function () {

            }
        },
        computed: {
            currValue: {
                get: function() {
                    return this.value;
                },
                set: function(newValue) {
                    console.log('s')
                    this.$emit("input", newValue); // 通过 input 事件更新 model
                }
            }
        }
    };
</script>
