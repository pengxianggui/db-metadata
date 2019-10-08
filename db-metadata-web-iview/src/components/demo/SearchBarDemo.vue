<template>
    <div>
        <h2>搜索条(searchBar): </h2>
        <b>元数据:</b>
        <x-input :options="options" v-model="searchItemsStr" @change="searchItemsChange"></x-input>
        <b>效果:</b>
        <search-bar v-if="searchItems.length > 0" :form-items="searchItems" @search="search"></search-bar>
    </div>
</template>

<script>
    import SearchBar from '@/components/base/SearchBar'
    import XInput from '../atom/XInput'
    export default {
        name: "search-bar-demo",
        data() {
            return {
                options: {
                  type: 'textarea'
                },
                searchItems: [
                    {
                        name: 'enterpriseId',
                        label: '所属企业',
                        value: '',
                        formType: 'x-select',
                        rules: [
                            { required: true, type: 'number', message: '请选择所属单位', trigger: 'change' }
                        ],
                        options: {
                            selectItems: [],
                            selectLabelKey: 'enterpriseName',
                            selectValueKey: 'id',
                            width: 200
                        }
                    },
                    {
                        name: 'plateNo',
                        label: '车牌号',
                        value: '',
                        formType: 'x-input',
                        options: {}
                    }
                ],
                searchItemsStr: '',
                searchModel: {}
            };
        },
        methods: {
            searchItemsChange () {
                this.searchItems = JSON.parse(this.searchItemsStr)
            },
            search (model) {
                let params= {}
                let keys = Object.keys(model)
                keys.forEach(key => {
                    params[key] = model[key]
                })
                // this.$refs.pageTable.refreshNew()
                // or show as a demo:
                alert("搜索数据模型： " + JSON.stringify(params))
            }
        },
        mounted() {
            this.searchItemsStr = JSON.stringify(this.searchItems)
        },
        components: {
            SearchBar,
            XInput
        }
    }
</script>

<style scoped>

</style>
