<template>
    <el-container direction="vertical">
        <el-row class="el-card">
            <el-col :span="24">
                搜索表单模型： {{searchModel}}
                <search-bar :meta-data="searchMeta"
                            :search-model.sync="searchModel" @search="masterSearch"></search-bar>
            </el-col>
            <el-col :span="24">
<!--                勾选数据：{{choseMasterData}}-->
                <br>
<!--                当前激活行：{{activeMasterData}}-->
                <table-list :meta-data="masterMeta" :field-meta-data="masterFieldMeta" :data="masterData"
                    :chose-data.sync="choseMasterData" :active-data.sync="activeMasterData"
                    :sort-model.sync="sortModel" :pagination-model.sync="paginationModel"
                    :operation-data="operationData">
                </table-list>
                排序数据模型: {{sortModel}}
                <br>
                分页数据模型: {{paginationModel}}
                <br>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
<!--                根据{{activeMasterData}}获取子表数据-->
                <table-list :meta-data="slaveMeta" :field-meta-data="slaveFieldMeta" :data="slaveData"
                    :chose-data.sync="choseSlaveData" :active-data.sync="activeSlaveData"
                    :sort-model.sync="sortModel"></table-list>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    import mockData from '@/mockdata.js'
    import SearchBar from '@/components/feature/SearchBar'
    import TableList from '@/components/feature/TableList'
    export default {
        name: "master-slave-table-tmpl",
        data() {
            return {
                searchMeta: {}, // 搜索条的元数据
                masterMeta: {}, // 主表元对象
                slaveMeta: {}, // 子表元对象
                masterFieldMeta: [], // 主表元字段
                slaveFieldMeta: [], // 子表元字段
                masterData: [], // 主表业务数据
                slaveData: [], // 子表业务数据
                choseMasterData: [], // TableList中选中的数据
                choseSlaveData: [], // TableList中选中的数据
                activeMasterData: {}, // 主表中当前激活的行
                activeSlaveData: {}, // 从表中当前激活的行

                /* 搜索、排序、分页数据, 作为参数获取真实数据 */
                searchModel: {}, // 搜索表单模型
                sortModel: {
                    prop: null,
                    order: null // ascending, descending
                }, // 排序数据模型
                paginationModel: { // 分页数据模型
                    pageSize: null, // 每页数量
                    currentPage: null, // 默认显示第几页
                    total: null // 总数, 被实际页数覆盖
                },
                operationData: {} // 用于构成操作面板，绑定函数 todo 操作条的动态实现还需要进一步思考
            }
        },
        methods: {
            getSearchMeta () {
                return {
                    component_name: 'search-bar',
                    ui_config: mockData.masterSearchBarMetadata
                }
            },
            getMasterMeta () {
                return mockData.masterMetadata
            },
            getMasterFieldMeta () {
                return mockData.masterFieldMetadata
            },
            getSlaveMeta () {
                return mockData.slaveMetadata
            },
            getSlaveFieldMeta() {
                return mockData.slaveFieldMetadata
            },
            getMasterData() { // ajax http请求的实际执行处
                let data = mockData.masterData
                this.paginationModel.total = mockData.masterData.length
                // todo request {table: masterMeta.table_name, schema: masterMeta.schema_name, condition: parse from params}
                console.log('searchModel: ' + JSON.stringify(this.searchModel))
                console.log('sortModel: ' + JSON.stringify(this.sortModel))
                console.log('paginationModel: ' + JSON.stringify(this.paginationModel))
                return data
            },
            getSlaveData() {
                // todo 根据masterActiveData 获取slaveData
            },
            masterSearch () {
                this.masterData = this.getMasterData()
            },
            slaveSearch () {
                this.slaveData = this.getSlaveData()
            }
        },
        created () {
            // 获取元数据
            this.searchMeta = this.getSearchMeta()
            this.masterMeta = this.getMasterMeta()
            this.masterFieldMeta = this.getMasterFieldMeta()
            this.slaveMeta = this.getSlaveMeta()
            this.slaveFieldMeta = this.getSlaveFieldMeta()
        },
        mounted () {
            // 获取业务数据
            this.masterSearch({})
        },
        watch: {
            paginationModel: {
                handler: function () {
                    this.masterSearch()
                },
                deep: true
            },
            sortModel: {
                handler: function () {
                    this.masterSearch()
                },
                deep: true
            },
            activeMasterData: {
                handler: function () {
                    this.slaveSearch()
                },
                deep: true
            }
        },
        components: {
            SearchBar,
            TableList
        }
    }
</script>

<style scoped>
</style>
