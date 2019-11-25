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
                            "conf": {
                            }
                        },
                        {
                            "component_name": "NumBox",
                            "name": "age",
                            "label": "年龄",
                            "conf": {
                            }
                        },
                        {
                            "component_name": "DropDownBox",
                            "name": "sex",
                            "label": "性别",
                            "data_url": "/dict/sex",
                            "conf": {
                            }
                        },
                        {
                            "component_name": "DateBox",
                            "name": "birthday",
                            "label": "出生日期",
                            "conf": {
                            }
                        },
                        {
                            "component_name": "TimeBox",
                            "name": "birthtime",
                            "label": "出生时间",
                            "conf": {
                            }
                        },
                        {
                            "component_name": "DateTimeBox",
                            "name": "birthdaytime",
                            "label": "日期时间",
                            "conf": {
                            }
                        },
                        {
                            "component_name": "BoolBox",
                            "name": "buff",
                            "label": "Buff加成",
                            "conf": {
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