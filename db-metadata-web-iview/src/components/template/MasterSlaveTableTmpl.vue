<template>
    <div>
        <div class="el-card">
            <search-panel :meta="mSpMeta" @search="mHandleSearch"></search-panel>
            <table-list :ref="mTlMeta['name']" :meta="mTlMeta" :active-data.sync="activeMData"></table-list>

            <search-panel :meta="sSpMeta" @search="sHandleSearch"></search-panel>
            <table-list :ref="sTlMeta['name']" :meta="sTlMeta"></table-list>
        </div>
    </div>
</template>

<script>
    import {getTlMeta, getSpMeta} from "../core/mixins/methods"

    export default {
        name: "MasterSlaveTableTmpl",
        mixins: [getTlMeta, getSpMeta],
        props: {
            R_moc: String,
            R_soc: String,
            R_linkage: {
                type: Object,
                validator: (value) => { return value.hasOwnProperty('m') && value.hasOwnProperty('s') }
            },
        },
        data() {
            return {
                mObjectCode: this.R_moc,
                sObjectCode: this.R_soc,
                mSpMeta: {},
                mTlMeta: {},
                activeMData: {},
                sSpMeta: {},
                sTlMeta: {},
                sTableUrl: null, // 初始sTlMeta['data_url']的暂存变量
            }
        },
        methods: {
            mHandleSearch(params) {
                this.$refs[this.mTlMeta['name']].getData(params);
            },
            sHandleSearch(params) {
                this.$refs[this.sTlMeta['name']].getData(params);
            }
        },
        watch: {
            activeMData: {
                handler: function (newVal, oldVal) {
                    this.sTlMeta['data_url'] = this.$compile(this.sTableUrl, {
                        objectCode: newVal[this.R_linkage['m']]
                    });
                },
                deep: true
            }
        },
        created() {
            // 获取主表TableList 组件meta
            this.getTlMeta(this.mObjectCode).then(resp => {
                this.mTlMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
            // 获取主表SearchPanel 组件meta
            this.getSpMeta(this.mObjectCode).then(resp => {
                this.mSpMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
            // 获取从表TableList 组件meta
            this.getTlMeta(this.sObjectCode).then(resp => {
                this.sTlMeta = resp.data;
                this.sTableUrl = this.sTlMeta['data_url'] + '?' + this.R_linkage['s'] + '={objectCode}';
                this.sTlMeta['data_url'] = this.sTableUrl;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
            // 获取从表SearchPanel 组件meta
            this.getSpMeta(this.sObjectCode).then(resp => {
                this.sSpMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
        },
    }
</script>

<style scoped>
</style>
