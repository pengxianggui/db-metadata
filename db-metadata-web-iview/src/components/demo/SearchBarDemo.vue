<template>
    <div>
        <h2>{{metaData.component_name}}</h2>
        <br>
        <component :is="metaData.component_name"
                   :search-model.sync="searchModel" :meta-data="metaData" @search="search"></component>
        <b>值为:</b> {{searchModel}}
        <z-toggle-panel>
            <json-box v-model="metaData" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    import SearchBar from '@/components/feature/SearchBar'

    export default {
        name: "search-bar-demo",
        data() {
            return {
                metaData: {
                    'component_name': 'search-bar',
                    'ui_config': [
                        {
                            "id": "1",
                            "object_code": "meta_object_code",
                            "en": "id",
                            "cn": "ID",
                            "order_num": 1,
                            "db_type": "varchar",
                            "db_type_length": "32",
                            "java_type": "String",
                            "component_name": "TextBox",
                            "ui_config": {
                                "placeholder": "请输入查询关键词",
                                "clearable": true,
                                "show_label": false, // 显示内容为cn||en
                                "size": "small",
                            }
                        }, {
                            "id": "2",
                            "object_code": "meta_object_code",
                            "is_primary": "false",
                            "en": "object_code",
                            "cn": "对象编码",
                            "order_num": 2,
                            "db_type": "varchar",
                            "db_type_length": "64",
                            "java_type": "String",
                            "component_name": "TextBox",
                            "ui_config": {
                                "placeholder": "请输入查询关键词",
                                "clearable": false,
                                "show_label": true, // 显示内容为cn||en
                                "size": "small" // mini, small, medium
                            }
                        }
                    ]
                },
                searchModel: {}
            };
        },
        methods: {
            search () {
                // this.$refs.pageTable.refreshNew()
                // or show as a demo:
                alert("搜索数据模型： " + JSON.stringify(this.searchModel))
            }
        },
        components: {
            SearchBar,
        }
    }
</script>

<style scoped>

</style>
