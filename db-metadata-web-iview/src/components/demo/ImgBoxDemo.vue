<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="fileList" :meta="meta"></component>
        值为：
        <ul>
            <li v-for="(file, index) in fileList" :key="index">{{file}}</li>
        </ul>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "img-box-demo",
        data () {
            return {
                fileList: [],
                meta: {
                    component_name: 'ImgBox',
                    conf: {
                        "action": "/file/upload?objectCode=test_table&fieldCode=col_file",
                        "tip": "上传文件限制不超过2M",
                        "auto-upload": true,
                        "limit": 5,
                        "multiple": true,  // 暂时单选
                    }
                },
            }
        },
    }
</script>

<style scoped>

</style>
