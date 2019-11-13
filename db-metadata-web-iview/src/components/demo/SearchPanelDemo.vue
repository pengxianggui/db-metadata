<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :meta="meta" @search="handleSearch"></component>
        <br>
        参数为: {{params}}
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "SearchPanelDemo",
        data () {
            return {
                params: null,
                meta: {
                    "component_name": "SearchPanel",
                    "name": "SearchPanel",
                    "label": "搜索面板",
                    "conf": {
                    },
                    "columns": [
                        {
                            "component_name": "TextBox",
                            "name": "name",
                            "label": "姓名",
                            "index": 1,            // 排序号, 决定表格中列的先后顺序
                            "conf": {
                                // "inline": false
                            }
                        },
                        {
                            "component_name": "NumBox",
                            "name": "age",
                            "label": "年龄",
                            "index": 2,            // 排序号, 决定表格中列的先后顺序
                            "conf": {
                                // "inline": false
                            }
                        },
                        {
                            "component_name": "DropDownBox",
                            "name": "sex",
                            "label": "性别",
                            "data_url": "/dict/sex",
                            "index": 3,            // 排序号, 决定表格中列的先后顺序
                            "conf": {
                                // "inline": false
                            }
                        }
                    ],
                }
            }
        },
        methods: {
            handleSearch(params) {
                this.params = params;
                // do search
                console.log('do search job.., the params is %o', params);
            }
        }
    }
</script>

<style scoped>

</style>