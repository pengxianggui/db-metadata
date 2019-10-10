<template>
    <el-container direction="vertical">
        <el-row class="el-card">
            <el-col :span="24">
                搜索表单模型： {{searchModel}}
                <search-bar :meta-data="masterSearchMetadata"
                            :search-model.sync="searchModel" @search="masterSearch"></search-bar>
            </el-col>
            <el-col :span="24">
                勾选数据：{{choseMasterData}}
                <br>
                当前激活行：{{activeMasterData}}
                <table-list :meta-data="masterMetadata" :field-meta-data="masterFieldMetadata" :data="masterData"
                    :chose-data.sync="choseMasterData" :active-data.sync="activeMasterData"
                    :sort-model.sync="sortModel" :pagination-model.sync="paginationModel"
                    :operation-data="operationData">
                </table-list>
<!--                {{paginationModel}}-->
            </el-col>
        </el-row>
        <el-row>
<!--            <el-col :span="24">-->
<!--                &lt;!&ndash;                {{searchModel}}&ndash;&gt;-->
<!--                <search-bar v-if="searchItems.length > 0" :form-items="searchItems"-->
<!--                            :search-model.sync="searchModel" @search="search" :options="masterMetadata.config"></search-bar>-->
<!--            </el-col>-->
            <el-col :span="24">
                根据{{activeMasterData}}获取子表数据
                <table-list :meta-data="slaveMetadata" :field-meta-data="slaveFieldMetadata" :data="slaveData"
                    :chose-data.sync="choseSlaveData" :active-data.sync="activeSlaveData"></table-list>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    import mockData from '@/mockdata.js'
    import SearchBar from '@/components/base/SearchBar'
    import TableList from '@/components/base/TableList'
    import {DEFAULT} from '@/constant'
    export default {
        name: "MasterSlaveComponent",
        data() {
            return {
                masterSearchMetadata: {}, // 搜索条的元数据
                masterMetadata: {}, // 主表元对象
                slaveMetadata: {}, // 子表元对象
                masterFieldMetadata: [], // 主表元字段
                slaveFieldMetadata: [], // 子表元字段
                searchItems: [], // 搜索字段元数据
                masterData: [], // 主表实际数据
                slaveData: [], // 子表实际数据
                choseMasterData: [], // TableList中选中的数据
                choseSlaveData: [], // TableList中选中的数据
                activeMasterData: {}, // 主表中当前激活的行
                activeSlaveData: {}, // 从表中当前激活的行

                /* 搜索、排序、分页数据, 作为参数获取真实数据 */
                searchModel: {}, // 搜索表单模型
                sortModel: [DEFAULT.sort], // 排序数据模型
                paginationModel: { // 分页数据模型
                    pageNum: DEFAULT.pagination.pageNum, // 每页数量
                    totalNum: 0, // 总数
                    currentPage: 1 // 当前页码
                },
                operationData: {} // 用于构成操作面板，绑定函数 todo 操作条的动态实现还需要进一步思考
            }
        },
        methods: {
            getMasterSearchMetadata () {
                return {
                    component_name: 'search-bar',
                    ui_config: mockData.masterSearchBarMetadata
                }
            },
            getMasterMetadata () {
                return mockData.masterMetadata
            },
            getMasterFieldMetadata () {
                return mockData.masterFieldMetadata
            },
            getSlaveMetadata () {
                return mockData.slaveMetadata
            },
            getSlaveFieldMetadata() {
                return mockData.slaveFieldMetadata
            },
            getMasterData() { // ajax http请求的实际执行处
                // todo request {table: masterMetadata.table_name, schema: masterMetadata.schema_name, condition: parse from params}
                console.log('searchModel: ' + JSON.stringify(this.searchModel))
                console.log('sortModel: ' + JSON.stringify(this.sortModel))
                console.log('paginationModel: ' + JSON.stringify(this.paginationModel))
                let data = mockData.masterData
                this.paginationModel.totalNum = mockData.masterData.length
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
            this.masterSearchMetadata = this.getMasterSearchMetadata()
            this.masterMetadata = this.getMasterMetadata()
            this.masterFieldMetadata = this.getMasterFieldMetadata()
            this.slaveMetadata = this.getSlaveMetadata()
            this.slaveFieldMetadata = this.getSlaveFieldMetadata()
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
