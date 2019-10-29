<template>
    <el-container direction="vertical">
        <el-row class="el-card">
            <el-col :span="24">
                <search-bar :meta="searchMeta"
                            :search-model.sync="searchModel" @search="masterSearch"></search-bar>
            </el-col>
            <el-col :span="24">
                <table-list :meta="masterMeta" :data="masterData" :chose-data.sync="choseMasterData"
                            :active-data.sync="activeMasterData">
                </table-list>
            </el-col>
        </el-row>
        <el-row class="el-card" style="margin-top: 10px">
            <el-col :span="24">
                <table-list :meta="slaveMeta" :data="slaveData" :chose-data.sync="choseSlaveData"
                            :active-data.sync="activeSlaveData"></table-list>
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
                masterData: [], // 主表业务数据
                slaveData: [], // 子表业务数据
                choseMasterData: [], // TableList中选中的数据
                choseSlaveData: [], // TableList中选中的数据
                activeMasterData: {}, // 主表中当前激活的行
                activeSlaveData: {}, // 从表中当前激活的行

                /* 搜索、排序、分页数据, 作为参数获取真实数据 */
                searchModel: {}, // 搜索表单模型
                // operationData: {} // 用于构成操作面板，绑定函数 todo 操作条的动态实现还需要进一步思考
            }
        },
        methods: {
            getSearchMeta () {
                return {
                    component_name: 'search-bar',
                    conf: mockData.masterSearchBarMetadata
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
                let data = mockData.masterData;
                // todo request {table: masterMeta.table_name, schema: masterMeta.schema_name, condition: parse from params}
                console.log('searchModel: ' + JSON.stringify(this.searchModel));
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
        watch: {
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
        },
        created () {
            // 获取元数据
            this.searchMeta = this.getSearchMeta();
            this.masterMeta = this.getMasterMeta();
            // this.masterFieldMeta = this.getMasterFieldMeta()
            this.slaveMeta = this.getSlaveMeta()
            // this.slaveFieldMeta = this.getSlaveFieldMeta()
        },
        mounted () {
            // 获取业务数据
            this.masterSearch({})
        },
    }
</script>

<style scoped>
</style>
