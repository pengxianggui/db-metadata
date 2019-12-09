<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :meta="meta"></component>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "file-box-demo",
        data () {
            return {
                meta: {
                    component_name: 'FileBox',
                    conf: {
                        "action": "/file/upload?objectCode=test_table&fieldCode=col_file",
                        "tip": "上传文件限制不超过2M",
                        "auto-upload": true,
                        "limit": 1,
                        "multiple": false,  // 暂时单选
                    }
                },
            }
        },
    }
</script>

<style scoped>

</style>
