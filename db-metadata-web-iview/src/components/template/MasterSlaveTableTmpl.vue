<template>
    <div>
        <div class="el-card">
            <search-panel :meta="master.spMeta" @search="mHandleSearch"></search-panel>
            <table-list :ref="master['objectCode']" :meta="master.tlMeta" :filter-params="filterParams"
                        @active-change="handleActiveChange"
                        @chose-change="handleChoseChange" :page="{ size: 5 }">

                <!-- 主表操作栏扩展插槽 -->
                <template #prefix-btn="{conf}">
                    <slot name="prefix-btn" v-bind:conf="conf"></slot>
                </template>
                <template #add-btn="{conf, add}">
                    <slot name="add-btn" v-bind:conf="conf" v-bind:add="add"></slot>
                </template>
                <template #batch-delete-btn="{conf, batchDelete}">
                    <slot name="batch-delete-btn" v-bind:conf="conf"
                          v-bind:batchDelete="batchDelete">
                    </slot>
                </template>
                <template #suffix-btn="{conf}">
                    <slot name="suffix-btn" v-bind:conf="conf"></slot>
                </template>

                <!-- 主表单条纪录操作扩展插槽 -->
                <template #inner-before-extend-btn="{scope, conf}">
                    <slot name="inner-before-extend-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
                <template #edit-btn="{scope, conf, edit}">
                    <slot name="edit-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
                <template #delete-btn="{scope, conf}">
                    <slot name="delete-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
                <template #inner-after-extend-btn="{scope, conf}">
                    <slot name="inner-after-extend-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
            </table-list>
        </div>
        <el-divider></el-divider>
        <!-- multi slave -->
        <div class="el-card" v-if="slaves.length > 1">
            <el-tabs type="border-card">
                <el-tab-pane :label="slave.objectCode" v-for="slave in slaves" :key="slave.objectCode">
                    <search-panel :meta="slave.spMeta" @search="sHandleSearch(slave, arguments)"></search-panel>
                    <table-list :ref="slave['objectCode']" :meta="slave.tlMeta" :filter-params="slave.filterParams"
                                :page="{ size: 5 }">
                        <template #add-btn="{conf}">
                            <el-button v-bind="conf" @click="handleAdd(slave)">新增</el-button>
                        </template>
                    </table-list>
                </el-tab-pane>
            </el-tabs>
        </div>
        <!-- single slave -->
        <div class="el-card" v-if="slaves.length === 1">
            <search-panel :meta="slaves[0].spMeta" @search="sHandleSearch(slaves[0], arguments)"></search-panel>
            <table-list :ref="slaves[0]['objectCode']" :meta="slaves[0].tlMeta" :filter-params="slaves[0].filterParams"
                        :page="{ size: 5 }">

                <!-- 子表操作栏扩展插槽 -->
                <template #prefix-btn="{conf}">
                    <slot name="s-prefix-btn" v-bind:conf="conf"></slot>
                </template>
                <template #add-btn="{conf}">
                    <slot name="s-add-btn" v-bind:conf="conf">
                        <el-button v-bind="conf" @click="handleAdd(slaves[0])">新增</el-button>
                    </slot>
                </template>
                <template #batch-delete-btn="{conf, batchDelete}">
                    <slot name="s-batch-delete-btn" v-bind:conf="conf"
                          v-bind:batchDelete="batchDelete">
                    </slot>
                </template>
                <template #suffix-btn="{conf}">
                    <slot name="s-suffix-btn" v-bind:conf="conf"></slot>
                </template>

                <!-- 子表单条纪录操作扩展插槽 -->
                <template #inner-before-extend-btn="{scope, conf}">
                    <slot name="s-inner-before-extend-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
                <template #edit-btn="{scope, conf, edit}">
                    <slot name="s-edit-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
                <template #delete-btn="{scope, conf}">
                    <slot name="s-delete-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
                <template #inner-after-extend-btn="{scope, conf}">
                    <slot name="s-inner-after-extend-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
            </table-list>
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
        props: {
            fc: String,
        },
        data() {
            const {featureCode: R_fc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);
            return {
                filterParams: {},
                featureCode: featureCode,
                master: {},
                slaves: [],
                activeMData: {},
            }
        },
        methods: {
            refresh() {
                this.refreshMasterData();
                this.refreshSlavesData();
            },
            handleChoseChange(rows) {
                // pxg_todo 多选主表(master)时, 子表(slaves)的默认行为(待定)
                const {master, $refs} = this;
                this.$emit('m-chose-change', {
                    rows: rows,
                    primaryKey: $refs[master['objectCode']].primaryKey,
                    objectCode: master['objectCode']
                });
            },
            handleActiveChange(row) {
                const {master} = this;
                this.activeMData = row;
                this.refreshSlavesData();
                this.$emit('m-active-change', {
                    row: row,   // 主表选中的单条记录
                    masterObjectCode: master['objectCode'],   // 主表的元对象编码
                    masterPrimaryKey: master['primaryKey']    // 主表的关联主键
                });
            },
            mHandleSearch(params) {
                const refName = this.master['objectCode'];
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[refName].getData();
                });
            },
            sHandleSearch(slave, params) {
                const {$refs} = this;
                const refName = slave['objectCode'];
                let ref = utils.isArray($refs[refName]) ? $refs[refName][0] : $refs[refName];
                this.$set(slave, 'filterParams', params[0]);
                this.$nextTick(() => {
                    ref.getData();
                });
            },
            handleAdd(slave) {
                if (utils.isEmpty(this.activeMData)) {
                    this.$message.warning('请先选择一条主表记录', '提示');
                    return;
                }

                const {$refs, featureCode, activeMData, master} = this;
                const sObjectCode = slave.objectCode;
                const refName = slave.objectCode;
                const foreignKeyName = slave['foreignFieldCode'];
                const foreignKeyValue = activeMData[master['primaryKey']];

                // 主子一对一时, this.$refs[refName]为对象, 主子一对多时, 该结果为数组?
                let ref = utils.isArray($refs[refName]) ? $refs[refName][0] : $refs[refName];

                const url = this.$compile(URL.MASTER_SLAVE_TO_ADD_S, {
                    objectCode: sObjectCode,
                    featureCode: featureCode,
                    foreignKeyName: foreignKeyName,
                    foreignKeyValue: foreignKeyValue
                });
                ref.dialog(url);
            },
            refreshMasterData() {   // 刷新master的业务数据
                const {master, $refs} = this;
                const refName = master['objectCode'];
                $refs[refName].getData();
            },
            refreshSlavesData() {   // 刷新所有slave的业务数据
                const {master, activeMData} = this;
                const primaryKey = master['primaryKey'];
                const objectCode = activeMData[primaryKey];
                this.slaves.forEach(slave => {
                    let url = this.$compile(slave['tableUrl'], {objectCode: objectCode});
                    slave.tlMeta['data_url'] = url;
                });
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
