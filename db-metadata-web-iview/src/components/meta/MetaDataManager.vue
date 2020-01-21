<template>
    <div>
        <master-slave-table-tmpl :ref="featureCode" :fc="featureCode"
                                 @m-active-change="handleMActiveChange"
                                 @m-chose-change="handleMChoseChange">
            <template #prefix-btn="{conf}">
                <el-button v-bind="conf" @click="featureAddVisible=true">创建功能</el-button>
            </template>
            <template #add-btn="{conf}">
                <el-button v-bind="conf" @click="visible=true">创建元对象</el-button>
            </template>

            <template #batch-delete-btn="{conf}">
                <el-button @click="handleDelete()" type="danger" icon="el-icon-delete-solid"
                           v-bind="conf">删除
                </el-button>
            </template>

            <template #delete-btn="{scope, conf}">
                <el-button v-bind="conf" @click="handleDelete(scope.row)"></el-button>
            </template>
        </master-slave-table-tmpl>

        <dialog-box :visible.sync="visible" title="创建元对象">
            <meta-import :meta="formMeta" @cancel="visible=false" @submit="formSubmit"></meta-import>
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
                featureCode: "meta_manager",
                featureParams: {},

                choseMData: [],
                visible: false,
                formMeta: {},
                featureAddVisible: false
            }
        },
        methods: {
            handleMChoseChange({rows}) {
                this.choseMData = rows;
            },
            handleMActiveChange({row, masterPrimaryKey}) {
                this.featureParams = {
                    objectCode: row[masterPrimaryKey]
                }
            },
            jumpToConf(objectCode) {
                let title = '创建成功，是否前往配置界面对' + objectCode + '进行UI配置?';
                //
                let url = this.$compile(URL.R_INSTANCE_CONF_NEW, {
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
                const {$refs, $axios, featureCode, formMeta} = this;
                $axios.post(formMeta.action, formModel).then(resp => {
                    this.$message.success(resp.msg);
                    let objectCode = formModel['objectCode'];
                    this.visible = false;
                    // refresh master
                    $refs[featureCode].refreshMasterData();
                    this.jumpToConf(objectCode);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            handleDelete(row) {
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
                url = this.$compile(URL.META_OBJECT_DELETE, {objectCode: objectCodes});

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    dangerouslyUseHTMLString: true
                }).then(() => {
                    const {$axios, $refs, featureCode} = this;
                    $axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                        // refresh master,slave data
                        $refs[featureCode].refreshMasterData();
                        $refs[featureCode].refreshSlavesData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            }
        },
        created() {
            this.$axios.get(URL.META_OBJECT_TO_ADD).then(resp => {
                this.formMeta = resp.data
            }).catch(err => {
                this.$message.error(err.msg);
            })
        }
    }
</script>

<style scoped>
</style>
