<template>
    <vue-json-editor v-model="currValue"></vue-json-editor>
</template>

<script>
    import vueJsonEditor from 'vue-json-editor'
    export default {
        components: {
            vueJsonEditor
        },
        props: {
            value: {
                type: Object
            },
            metaData: {
                type: Object
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
