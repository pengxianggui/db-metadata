<template>
    <div>
        <div class="el-card">
            <search-panel :meta="master.spMeta" @search="mHandleSearch"></search-panel>
            <table-list :ref="master['name']" :meta="master.tlMeta" :active-data.sync="activeMData"></table-list>
        </div>
        <el-divider></el-divider>
        <div class="el-card">
            <el-tabs type="border-card">
                <el-tab-pane :label="slave.objectCode" v-for="slave in slaves" :key="slave.objectCode">
                    <search-panel :meta="slave.spMeta" @search="sHandleSearch(slave, arguments)"></search-panel>
                    <table-list :ref="slave['name']" :meta="slave.tlMeta">
                        <template #add-btn="{conf}">
                            <el-button v-bind="conf" @click="handleAdd(slave)">新增</el-button>
                        </template>
                    </table-list>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {getSpMeta, getTlMeta, loadFeature} from "@/components/core/mixins/methods"
    import {URL} from '@/constant'

    export default {
        name: "MasterSlaveTableTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        data() {
            return {
                featureCode: this.$route.query.featureCode,
                master: {},
                slaves: [],
                activeMData: {},
            }
        },
        methods: {
            mHandleSearch(params) {
                const refName = this.master['name'];
                this.$refs[refName].getData(params);
            },
            sHandleSearch(slave, params) {
                let param = params[0];
                const refName = slave['name'];
                let ref = this.$refs[refName][0];
                ref.getData(param);
            },
            handleAdd(slave) {
                if (utils.isEmpty(this.activeMData)) {
                    this.$message.warning('请先选择一条主表记录', '提示');
                    return;
                }

                const featureCode = this.featureCode;
                const sObjectCode = slave.objectCode;
                const foreignKeyName = slave['foreignFieldCode'];
                const foreignKeyValue = this.activeMData[this.master['primaryKey']];
                const refName = slave['name'];
                let ref = this.$refs[refName][0];

                const url = this.$compile(URL.MASTER_SLAVE_TO_ADD_S, {
                    objectCode: sObjectCode,
                    featureCode: featureCode,
                    foreignKeyName: foreignKeyName,
                    foreignKeyValue: foreignKeyValue
                });
                ref.dialog(url);
            },
            refreshSlaves(objectCode) {
                this.slaves.forEach(slave => {
                    let url = this.$compile(slave['tableUrl'], {objectCode: objectCode});
                    slave.tlMeta['data_url'] = url;
                });
            }
        },
        watch: {
            activeMData: {
                handler: function (newVal, oldVal) {
                    let primaryKey = this.master['primaryKey'];
                    this.refreshSlaves(newVal[primaryKey]);
                },
                deep: true
            }
        },
        created() {
            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                this.master = feature['master'];
                this.slaves = feature['slaves'];

                const mObjectCode = this.master['objectCode'];

                // 获取主表TableList 组件meta
                this.getTlMeta(mObjectCode).then(resp => {
                    let tlMeta = resp.data;
                    this.$set(this.master, 'tlMeta', tlMeta);
                    this.$set(this.master, 'name', tlMeta['name']);
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
                // 获取主表SearchPanel 组件meta
                this.getSpMeta(mObjectCode).then(resp => {
                    let spMeta = resp.data;
                    this.$set(this.master, 'spMeta', spMeta);
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                // 子表数据装载
                for (let i = 0; i < this.slaves.length; i++) {
                    let slave = this.slaves[i];
                    const sObjectCode = slave['objectCode'];

                    // 获取从表TableList 组件meta
                    this.getTlMeta(sObjectCode).then(resp => {
                        let foreignFieldKey = slave['foreignFieldCode'];
                        let tlMeta = resp.data;
                        const data_url = tlMeta['data_url'] + '?' + foreignFieldKey + '={objectCode}';
                        tlMeta['data_url'] = data_url;

                        this.$set(slave, 'tlMeta', tlMeta);
                        this.$set(slave, 'name', tlMeta['name'] + '_' + i);
                        this.$set(slave, 'tableUrl', data_url); // 暂存
                    }).catch(err => {
                        console.error('[ERROR] msg: %s', err.msg);
                        this.$message.error(err.msg);
                    });

                    // 获取从表SearchPanel 组件meta
                    this.getSpMeta(sObjectCode).then(resp => {
                        let spMeta = resp.data;
                        this.$set(slave, 'spMeta', spMeta);
                    }).catch(err => {
                        console.error('[ERROR] msg: %s', err.msg);
                        this.$message.error(err.msg);
                    });
                }
            });
        },
    }
</script>

<style scoped>
</style>
