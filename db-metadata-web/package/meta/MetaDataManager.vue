<template>
    <div>
        <div class="el-card">
            <search-view :meta="object.svMeta" @search="mHandleSearch"></search-view>
            <table-view :ref="object.objectCode" :meta="object.tvMeta" :filter-params="object.filterParams"
                        @active-change="handleMActiveChange"
                        @chose-change="handleMChoseChange" :page="{ size: 5 }">

                <template #prefix-btn="{conf}">
                    <el-button v-bind="conf" @click="featureAddVisible=true">创建功能</el-button>
                </template>
                <template #add-btn="{conf, add}">
                    <el-button v-bind="conf" @click="toAddMetaObject">创建元对象</el-button>
                </template>
                <template #batch-delete-btn="{conf, batchDelete}">
                    <el-button @click="handleDelete()" type="danger" icon="el-icon-delete-solid"
                               v-bind="conf">删除
                    </el-button>
                </template>

                <!-- 单条纪录操作扩展插槽 -->
                <template #delete-btn="{scope, conf}">
                    <el-button v-bind="conf" @click="handleDelete(scope.row)"></el-button>
                </template>
            </table-view>
        </div>

        <el-divider></el-divider>

        <div class="el-card">
            <search-view :meta="field.svMeta" @search="sHandleSearch"></search-view>
            <table-view :ref="field.objectCode" :meta="field.tvMeta" :filter-params="field.filterParams"
                        :page="{ size: 5 }">
                <template #add-btn="{conf}">
                    <el-button v-bind="conf" @click="handleAdd">新增</el-button>
                </template>
            </table-view>
        </div>

        <dialog-box :visible.sync="metaImportFormVisible" title="创建元对象">
            <meta-import :meta="metaImportFormMeta" @cancel="metaImportFormVisible=false"
                         @submit="handlerMetaImport"></meta-import>
            <template #footer><span></span></template>
        </dialog-box>
        <dialog-box :visible.sync="featureAddVisible" title="创建功能">
            <feature-add :params="featureParams" @ok="featureAddVisible=false"
                         @cancel="featureAddVisible=false"></feature-add>
            <template #footer><span></span></template>
        </dialog-box>
    </div>
</template>

<script>
    import utils from '../utils'
    import {restUrl, routeUrl} from '../constant/url';
    import {getSpMeta, getTlMeta} from "../core/mixins/methods"
    import MetaImport from './MetaImport'
    import FeatureAdd from './feature/FeatureAdd'

    export default {
        name: "MetaDataManager",
        mixins: [getTlMeta, getSpMeta],
        components: {
            MetaImport,
            FeatureAdd
        },
        data() {
            return {
                object: {
                    objectCode: 'meta_object',
                    svMeta: {},
                    tvMeta: {},
                    filterParams: {},
                    activeData: {},
                    choseData: []
                },
                field: {
                    objectCode: 'meta_field',
                    svMeta: {},
                    tvMeta: {},
                    filterParams: {},
                    activeData: {},
                    choseData: [],
                    urlTemplate: null,
                    pidKey: 'code'
                },
                featureParams: {},
                metaImportFormVisible: false,
                metaImportFormMeta: {},
                featureAddVisible: false
            }
        },
        methods: {
            handleMChoseChange({rows}) {
                this.choseMData = rows;
            },
            handleMActiveChange(row) {
                // 带入 feature add dialog
                const {field: {pidKey, urlTemplate}} = this
                this.featureParams = {
                    objectCode: row[pidKey]
                }

                this.object.activeData = row
                const objectCode = this.object.activeData[pidKey];
                let url = this.$compile(urlTemplate, {objectCode: objectCode});
                this.field.tvMeta['data_url'] = url
            },
            mHandleSearch(params) {
                const {object: {objectCode}} = this
                this.object.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[objectCode].getData();
                });
            },
            sHandleSearch(params) {
                const {field: {objectCode}} = this
                this.field.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[objectCode].getData();
                });
            },
            handleAdd() {
                const {object: {activeData, objectCode: objectCode}} = this
                const {field: {objectCode: fieldCode}} = this
                if (utils.isEmpty(activeData)) {
                    this.$message.warning('请先选择一条主表记录', '提示');
                    return;
                }

                const url = this.$compile(restUrl.RECORD_TO_ADD, {
                    objectCode: fieldCode,
                });
                this.$refs[fieldCode].dialog(utils.resolvePath(url,
                    {code: objectCode}
                ));
            },
            jumpToConf(objectCode) {
                let title = '创建成功，是否前往配置界面对' + objectCode + '进行UI配置?';
                //
                let url = this.$compile(routeUrl.baseURL + routeUrl.R_INSTANCE_CONF_NEW, {
                    componentCode: 'TableView',
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
            handlerMetaImport(formModel) {
                const {$refs, $axios, metaImportFormMeta, object: {objectCode}} = this;
                $axios.post(metaImportFormMeta.action, formModel).then(resp => {
                    this.$message.success(resp.msg);
                    this.metaImportFormVisible = false;
                    // refresh master
                    $refs[objectCode].refreshMasterData();
                    this.jumpToConf(objectCode);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            handleDelete(row) {
                const {object: {objectCode}, field: {objectCode: fieldCode}} = this
                let title, objectCodes, url;
                if (utils.isUndefined(row)) {   // 批量删除
                    const objectCodeArr = this.choseMData.map(row => row.code);
                    if (utils.isEmpty(objectCodeArr)) {
                        this.$message.warning('请至少选择一项!');
                        return;
                    }
                    objectCodes = objectCodeArr.join(',');
                } else {    // 单行删除
                    objectCodes = row.code;
                }
                title = '<div style="overflow: auto;">确定删除如下元对象? ' + objectCodes + '</div>';
                url = this.$compile(restUrl.META_OBJECT_DELETE, {objectCode: objectCodes});

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    dangerouslyUseHTMLString: true
                }).then(() => {
                    const {$axios, $refs} = this;
                    $axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                        // refresh master,slave data
                        $refs[objectCode].getData();
                        $refs[fieldCode].getData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            },
            toAddMetaObject() {
                this.$axios.get(restUrl.META_OBJECT_TO_ADD).then(resp => {
                    this.metaImportFormMeta = resp.data
                    this.metaImportFormVisible = true
                }).catch(err => {
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            // 获取meta数据
            const {objectCode: metaObjectCode} = this.object;
            const {objectCode: metaFieldCode} = this.field;
            this.getSpMeta(metaObjectCode).then(resp => {
                this.object.svMeta = resp.data;
            })
            this.getTlMeta(metaObjectCode).then(resp => {
                this.object.tvMeta = resp.data;
            })
            this.getSpMeta(metaFieldCode).then(resp => {
                this.field.svMeta = resp.data
            })
            this.getTlMeta(metaFieldCode).then(resp => {
                const meta = resp.data;
                this.field.urlTemplate = meta['data_url'] + '?object_code={objectCode}';
                meta.data_url = this.field.urlTemplate
                this.field.tvMeta = meta
            })
        }
    }
</script>

<style scoped>
</style>
