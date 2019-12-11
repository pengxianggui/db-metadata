<template>
    <div>
        <div class="el-card">
            <search-panel :meta="mSpMeta" @search="mHandleSearch"></search-panel>
            <table-list :ref="mTlMeta['name']" :meta="mTlMeta" :active-data.sync="activeMData"></table-list>

            <search-panel :meta="sSpMeta" @search="sHandleSearch"></search-panel>
            <table-list :ref="sTlMeta['name']" :meta="sTlMeta">
                <template #add-btn="{conf}">
                    <el-button v-bind="conf" @click="handleAdd">新增</el-button>
                </template>
            </table-list>
        </div>
    </div>
</template>

<script>
    import {loadFeature, getTlMeta, getSpMeta} from "@/components/core/mixins/methods"
    import {URL} from '@/constant'

    export default {
        name: "MasterSlaveTableTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        props: {
            R_fc: String,    // fc: 功能code(feature_code)
        },
        data() {
            return {
                featureCode: this.R_fc,
                linkage: {
                    m: null,
                    s: null
                },
                mObjectCode: null,
                sObjectCode: null,
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
            },
            handleAdd() {
                const sObjectCode = this.sObjectCode;
                const featureCode = this.featureCode;
                const url = this.$compile(URL.MASTER_SLAVE_TO_ADD_S, {
                    objectCode: sObjectCode,
                    featureCode: featureCode
                });
                this.$refs[this.sTlMeta['name']].dialog(url);
            }
        },
        watch: {
            activeMData: {
                handler: function (newVal, oldVal) {
                    this.sTlMeta['data_url'] = this.$compile(this.sTableUrl, {
                        objectCode: newVal[this.linkage['m']]
                    });
                },
                deep: true
            }
        },
        created() {
            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                const master = feature['master'];
                const slave = feature['slaves'][0]; // TODO 暂时只处理第一个子元对象

                this.mObjectCode = master['objectCode'];
                this.sObjectCode = slave['objectCode'];
                this.linkage = {m: master['primaryKey'], s: slave['foreignFieldCode']};

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
                    this.sTableUrl = this.sTlMeta['data_url'] + '?' + this.linkage['s'] + '={objectCode}';
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

            });


        },
    }
</script>

<style scoped>
</style>
