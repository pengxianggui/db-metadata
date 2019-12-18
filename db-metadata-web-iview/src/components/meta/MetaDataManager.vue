<template>
    <div>
        <div class="el-card">
            <search-panel :meta="mSpMeta" @search="mHandleSearch"></search-panel>
            <table-list :ref="mTlMeta['name']" :meta="mTlMeta" :active-data.sync="activeMData" :page="{ size: 5 }">
                <template #prefix-btn="{conf}">
                    <el-button v-bind="conf" @click="featureAddVisible=true">创建功能</el-button>
                </template>
                <template #add-btn="{conf}">
                    <el-button v-bind="conf" @click="visible=true">创建元对象</el-button>
                </template>
            </table-list>

            <search-panel :meta="sSpMeta" @search="sHandleSearch"></search-panel>
            <table-list :ref="sTlMeta['name']" :meta="sTlMeta" :page="{ size: 5 }">
                <template #add-btn="{conf}">
                    <el-button v-bind="conf" @click="handleAdd">新增</el-button>
                </template>
            </table-list>
        </div>

        <el-dialog title="创建元对象" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="visible = false" @submit="formSubmit"></meta-import>
        </el-dialog>
        <dialog-box :visible.sync="featureAddVisible" title="创建功能">
            <feature-add></feature-add>
            <template #footer><span></span></template>
        </dialog-box>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {URL} from '@/constant'
    import {loadFeature, getTlMeta, getSpMeta} from "../core/mixins/methods"
    import MetaImport from './MetaImport'
    import FeatureAdd from './feature/FeatureAdd'

    export default {
        name: "MetaDataManager",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        components: {
            MetaImport,
            FeatureAdd
        },
        data() {
            return {
                featureCode: "tcode_test",

                master: {},
                slave: {},  // 元对象/元字段 主子表一对一

                mSpMeta: {},
                mTlMeta: {},
                activeMData: {},
                sSpMeta: {},
                sTlMeta: {},
                sTableUrl: null, // 初始sTlMeta['data_url']的暂存变量
                visible: false,
                formMeta: {},
                featureAddVisible: false
            }
        },
        methods: {
            mHandleSearch(params) {
                this.mTlRef.getData(params);
            },
            sHandleSearch(params) {
                this.sTlRef.getData(params);
            },
            getFormMeta() {
                this.$axios.get(URL.META_OBJECT_TO_ADD).then(resp => {
                    this.formMeta = resp.data
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            jumpToConf(objectCode) {
                let title = '创建成功，是否前往配置界面对' + objectCode + '进行UI配置?';
                let url = this.$compile(URL.R_INSTANCE_CONF_EDIT, {
                    componentCode: 'TableList',
                    objectCode: objectCode
                });
                this.$confirm(title, '提示', {
                    confirmButtonText: '去配置',
                    cancelButtonText: '下次再说',
                    type: 'success',
                }).then(() => {
                    this.$router.push(url);
                }).catch(() => {
                    const h = this.$createElement;

                    this.$notify.info({
                        title: '消息',
                        message: h('span', {}, [
                            h('span', {}, '如不进行UI配置, 此元对象相关视图可能出现异常.'),
                            h('a', {
                                attrs: {
                                    href: '#' + url
                                }
                            }, '去配置')
                        ])
                    });
                });
            },
            formSubmit(formModel) {
                this.$axios.post(this.formMeta.action, formModel).then(resp => {
                    this.$message.success(resp.msg);
                    let objectCode = formModel['objectCode'];
                    this.visible = false;
                    this.refreshMasterTableData();
                    this.jumpToConf(objectCode);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            refreshMasterTableData() {
                this.mTlRef.getData();
            },
            refreshSlaveTableData() {
                this.sTlRef.getData();
            },
            handleAdd() {
                const sObjectCode = this.slave['objectCode'];
                const featureCode = this.featureCode;
                const foreignKeyName = this.slave['foreignFieldCode'];
                const foreignKeyValue = this.activeMData[this.master['primaryKey']];

                if (utils.isEmpty(this.activeMData)) {
                    this.$message.warning('请先选择一条主表记录', '提示');
                    return;
                }
                const url = this.$compile(URL.MASTER_SLAVE_TO_ADD_S, {
                    objectCode: sObjectCode,
                    featureCode: featureCode,
                    foreignKeyName: foreignKeyName,
                    foreignKeyValue: foreignKeyValue
                });
                this.$refs[this.sTlMeta['name']].dialog(url);
            },
            doDelete(ids, ev, row, index) {
                let objectCodes;
                if (Array.isArray(ids)) {
                    const objectCodeArr = this.mTlRef.innerChoseData.map(row => row.code);
                    objectCodes = objectCodeArr.join(',');
                } else {
                    objectCodes = row.code;
                }
                let title = '<div style="overflow: auto;">确定删除如下元对象? ' + objectCodes + '</div>';
                let url = this.$compile(URL.META_OBJECT_DELETE, {
                    objectCode: objectCodes
                });

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    dangerouslyUseHTMLString: true
                }).then(() => {
                    this.$axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                        this.refreshMasterTableData();
                        this.refreshSlaveTableData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            }
        },
        watch: {
            activeMData: {
                handler: function (newVal, oldVal) {
                    let primaryKey = this.master['primaryKey'];
                    this.sTlMeta['data_url'] = this.$compile(this.sTableUrl, {
                        objectCode: newVal[primaryKey]
                    });
                },
                deep: true
            }
        },
        created() {
            this.getFormMeta();

            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                this.master = feature['master'];
                this.slave = feature['slaves'][0]; // 元对象/元字段 主子表一对一

                const mObjectCode = this.master['objectCode'];
                const sObjectCode = this.slave['objectCode'];

                // 获取主表TableList 组件meta
                this.getTlMeta(mObjectCode).then(resp => {
                    this.mTlMeta = resp.data;
                    this.$nextTick(() => {
                        this.mTlRef.doDelete = this.doDelete; // override doDelete
                    });
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
                // 获取主表SearchPanel 组件meta
                this.getSpMeta(mObjectCode).then(resp => {
                    this.mSpMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
                // 获取从表TableList 组件meta
                this.getTlMeta(sObjectCode).then(resp => {
                    let foreignFieldKey = this.slave['foreignFieldCode'];
                    this.sTlMeta = resp.data;
                    this.sTableUrl = this.sTlMeta['data_url'] + '?' + foreignFieldKey + '={objectCode}';
                    this.sTlMeta['data_url'] = this.sTableUrl;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
                // 获取从表SearchPanel 组件meta
                this.getSpMeta(sObjectCode).then(resp => {
                    this.sSpMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            });
        },
        computed: {
            mTlRef() {
                return this.$refs[this.mTlMeta['name']];
            },
            sTlRef() {
                return this.$refs[this.sTlMeta['name']];
            }
        }
    }
</script>

<style scoped>
</style>
