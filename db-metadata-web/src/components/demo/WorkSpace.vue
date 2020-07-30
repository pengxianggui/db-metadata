<template>
    <div style="padding: 20px;">
        <h1>组件开发调试页面</h1>

        <img-box v-model="value" :meta="meta"></img-box>
        <json-box v-model="value"></json-box>
    </div>
</template>

<script>
    export default {
        name: "WorkSpace",
        components: {},
        data() {
            return {
                meta: {
                    conf: {
                        action: '/meta/file/upload?objectCode=test_table&fieldCode=col_file',
                        limit: 100
                    }
                },
                value: [{url: 'http://demand.istaros.com/demand/images/p_default.jpg', name: '', seat: '营业执照'}]
            }
        },
        methods: {},
        mounted() {
        }
    }
</script>

<style scoped>

</style>
