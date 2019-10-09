<template>
    <div>
        <h2>搜索条(searchBar): </h2>
        <b>元数据:</b>
        {{searchMetadata}}
        <br>
        <b>效果:</b>
        <search-bar v-if="searchMetadata.length > 0" :search-model.sync="searchModel" :meta-data="searchMetadata" @search="search"></search-bar>
        <b>表单模型:</b> {{searchModel}}
    </div>
</template>

<script>
    import SearchBar from '@/components/base/SearchBar'

    export default {
        name: "search-bar-demo",
        data() {
            return {
                searchMetadata: [
                    {
                        "id": "1",
                        "object_code": "meta_object_code",
                        "en": "id",
                        "cn": "ID",
                        "order_num": 1,
                        "db_type": "varchar",
                        "db_type_length": "32",
                        "java_type": "String",
                        "component_type": "TextBox",
                        "config": {
                            "placeholder": "请输入查询关键词",
                            "clearable": false,
                            "show_label": true, // 显示内容为cn||en
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
                        "component_type": "TextBox",
                        "config": {
                            "placeholder": "请输入查询关键词",
                            "clearable": false,
                            "show_label": true, // 显示内容为cn||en
                            "size": "small" // mini, small, medium
                        }
                    }
                ],
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
