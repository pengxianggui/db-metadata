<template>
    <div>
        <el-form ref="featureForm" :model="feature" label-width="120px">
            <el-form-item label="功能类别" prop="type" required>
                <radio-box :data-url="featureTypeUrl" v-model="feature.type"></radio-box>
            </el-form-item>
            <el-form-item label="功能名" class="inline" prop="name" required>
                <text-box v-model="feature.name"></text-box>
            </el-form-item>
            <el-form-item label="功能代码" class="inline" prop="code" required>
                <text-box v-model="feature.code"></text-box>
            </el-form-item>
            <el-form-item label="图标" class="inline" prop="icon">
                <icon-box v-model="feature.icon"></icon-box>
            </el-form-item>
            <el-form-item label="instanceCode" class="inline" prop="instanceCode" required>
                <drop-down-box v-model="feature.instanceCode" :data-url="instanceCodeUrl" filterable></drop-down-box>
            </el-form-item>

            <el-form-item label="独立路由" class="inline" prop="config.hasRouter" required>
                <el-tooltip effect="dark" placement="top">
                    <div slot="content">
                        独立路由是指,选择了指定功能后,需要在前端文件单独路由的选项
                    </div>
                    <i class="el-icon-question"></i>
                </el-tooltip>
                <el-radio v-model="feature.config.hasRouter" :label=false>否</el-radio>
                <el-radio v-model="feature.config.hasRouter" :label=true>是</el-radio>
            </el-form-item>

            <el-form-item v-if="feature.config.hasRouter" prop="config.componentName" required>
                <text-box v-model="feature.config.componentName"
                          placeholder="输入指定的Component名称与Router注册时相同"></text-box>
            </el-form-item>

            <div v-show="feature.type === FEATURE_TYPE.MasterSlaveGrid">
                <MasterSlaveGrid ref="masterSlaveGrid" :oc="objectCode" :pk="primaryKey"></MasterSlaveGrid>
                <!-- TODO 从表后期应当支持多个子表设置 -->
            </div>

            <div v-show="feature.type === FEATURE_TYPE.SingleGrid">
                <SingleGrid ref="singleGrid" :oc="objectCode"></SingleGrid>
            </div>

            <div v-show="feature.type === FEATURE_TYPE.TreeSingleGrid">
                <TreeSingleGrid ref="treeSingleGrid" :oc="objectCode"></TreeSingleGrid>
            </div>

            <div v-show="feature.type === FEATURE_TYPE.TreeAndSingleGrid">
                <TreeAndSingleGrid ref="treeAndSingleGrid" :oc="objectCode"></TreeAndSingleGrid>
            </div>

            <el-form-item>
                <el-button @click="onSubmit('featureForm')" type="primary">保存</el-button>
                <el-button @click="onCancel">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {restUrl} from "../../constant/url";
    import MasterSlaveGrid from './conf-mini/MasterSlaveGrid'
    import SingleGrid from './conf-mini/SingleGrid'
    import TreeSingleGrid from './conf-mini/TreeSingleGrid'
    import TreeAndSingleGrid from './conf-mini/TreeAndSingleGrid'

    const FEATURE_TYPE = {
        MasterSlaveGrid: 'MasterSlaveGrid',
        SingleGrid: 'SingleGrid',
        TreeSingleGrid: 'TreeInTable',
        TreeAndSingleGrid: 'TreeAndTable'
    };

    export default {
        name: "feature-add",
        components: {MasterSlaveGrid, SingleGrid, TreeSingleGrid, TreeAndSingleGrid},
        props: {
            params: {
                type: Object
            },
            meta: {
                type: Object,
                default: () => {
                }
            }
        },
        data() {
            return {
                FEATURE_TYPE: FEATURE_TYPE,
                objectCode: this.params['objectCode'],
                primaryKey: this.params['primaryKey'],
                featureTypeUrl: restUrl.LIST_FEATURE_TYPE,
                instanceCodeUrl: restUrl.INSTANCE_CODE_LIST,
                feature: {
                    type: FEATURE_TYPE['SingleGrid'],
                    name: null,
                    code: null,
                    config: {
                        //是否指定特殊的ComponentName
                        hasRouter: false,
                        componentName: '',
                        icon: ''// 功能图标,
                    },
                    instanceCode: null,

                },
                // listTableConf: {
                //     list: {
                //         objectCode: null,
                //         primaryKey: null
                //     },
                //     table: {
                //         objectCode: null,
                //         foreignFieldCode: null
                //     }
                // },
                // tableFormConf: {
                //     table: {
                //         objectCode: null,
                //         primaryKey: null
                //     },
                //     form: {
                //         objectCode: null,
                //         foreignFieldCode: null
                //     }
                // },
                // activeTab: 'first',
            }
        },
        methods: {
            assembleParams() {
                const {$refs, feature} = this;
                const {type: featureType} = feature;
                switch (featureType) {
                    case FEATURE_TYPE['MasterSlaveGrid']:
                        this.feature.config = $merge(this.feature.config, $refs['masterSlaveGrid'].config);
                        break;
                    case FEATURE_TYPE['SingleGrid']:
                        this.feature.config = $merge(this.feature.config, $refs['singleGrid'].config);
                        break;
                    case FEATURE_TYPE['TreeSingleGrid']:
                        this.feature.config = $merge(this.feature.config, $refs['treeSingleGrid'].config);
                        break;
                    case FEATURE_TYPE['TreeAndSingleGrid']:
                        this.feature.config = $merge(this.feature.config, $refs['treeAndSingleGrid'].config);
                        break;
                }
                return this.feature;
            },
            doSubmit() {
                const params = this.assembleParams();
                const {type: featureType} = params;
                let url = this.$compile(restUrl.FEATURE_ADD, {
                    featureType: featureType
                });
                this.$axios.post(url, params).then(resp => {
                    this.$message.success('操作成功');
                    this.$emit('ok', params);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.doSubmit() // submit
                    } else {
                        return false;
                    }
                });
            },
            onCancel: function (event) {
                this.$emit('cancel', event);
            }
        },
        created() {
        },
        mounted() {
        },
        computed: {}
    }
</script>

<style scoped>

</style>
