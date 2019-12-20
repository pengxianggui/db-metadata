<template>
    <div>
        <el-form ref="featureForm" :model="feature" label-width="100px">
            <el-form-item label="功能类别">
                <radio-box :data-url="featureTypeUrl" v-model="feature.type" required></radio-box>
            </el-form-item>
            <el-form-item label="功能名" class="inline">
                <text-box v-model="feature.name" required></text-box>
            </el-form-item>
            <el-form-item label="功能代码" class="inline">
                <text-box v-model="feature.code" required></text-box>
            </el-form-item>
            <template v-if="feature.type === 'MasterSlaveGrid'">
                <h3>主表</h3>
                <el-form-item label="元对象编码" class="inline">
                    <drop-down-box v-model="masterSlaveConfig.master.objectCode"
                                   :data-url="metaObjectCodeUrl" @change="masterSlaveConfig.master.primaryKey = null"
                                   filterable required>
                        <template #options="{options}">
                            <el-option v-for="item in options" :key="item.code" :label="item.code"
                                       :value="item.code">
                                {{item.code}}
                            </el-option>
                        </template>
                    </drop-down-box>
                </el-form-item>
                <el-form-item label="主键" class="inline">
                    <drop-down-box v-model="masterSlaveConfig.master.primaryKey"
                                   :data-url="$compile(metaFieldCodeUrl, {objectCode: masterSlaveConfig.master.objectCode})"
                                   filterable required>
                        <template #label="{option}">
                            <span>{{option.value}}({{option.label}})</span>
                        </template>
                    </drop-down-box>
                </el-form-item>

                <el-tabs v-model="activeTab" @tab-click="handleClick">
                    <el-tab-pane label="从表1" name="first">
                        <el-form-item label="元对象编码" class="inline">
                            <drop-down-box v-model="masterSlaveConfig.slaves[0].objectCode"
                                           @change="masterSlaveConfig.slaves[0].foreignFieldCode = null"
                                           :data-url="metaObjectCodeUrl" filterable required>
                                <template #options="{options}">
                                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                                               :value="item.code">
                                        {{item.code}}
                                    </el-option>
                                </template>
                            </drop-down-box>
                        </el-form-item>
                        <el-form-item label="外键" class="inline">
                            <drop-down-box v-model="masterSlaveConfig.slaves[0].foreignFieldCode"
                                           :data-url="$compile(metaFieldCodeUrl, {objectCode: masterSlaveConfig.slaves[0].objectCode})"
                                           filterable>
                                <template #label="{option}">
                                    <span>{{option.value}}({{option.label}})</span>
                                </template>
                            </drop-down-box>
                        </el-form-item>
                        <el-form-item label="排序" class="inline">
                            <num-box v-model="masterSlaveConfig.slaves[0].order" controls required></num-box>
                        </el-form-item>
                    </el-tab-pane>
                </el-tabs>

                <!-- TODO 从表后期应当支持多个子表设置 -->
            </template>
            <template v-if="feature.type === 'SingleGrid'">
                <el-form-item label="元对象编码">
                    <drop-down-box v-model="singleGridConfig.singleGrid.objectCode" :data-url="metaObjectCodeUrl">
                        <template #options="{options}">
                            <el-option v-for="item in options" :key="item.code" :label="item.code"
                                       :value="item.code">
                                {{item.code}}
                            </el-option>
                        </template>
                    </drop-down-box>
                </el-form-item>
            </template>

            <el-button @click="onSubmit" type="primary">保存</el-button>
            <el-button @click="onCancel">取消</el-button>
        </el-form>
    </div>
</template>

<script>
    import {CONSTANT, URL} from '@/constant'

    export default {
        name: "feature-add",
        props: {
            params: {
                type: Object
            },
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        data() {
            return {
                metaObjectCodeUrl: URL.OBJECT_CODE_LIST,
                metaFieldCodeUrl: URL.FIELD_CODE_LIST_BY_OBJECT,
                featureTypeUrl: URL.LIST_FEATURE_TYPE,
                feature: {
                    type: 'MasterSlaveGrid',
                    name: null,
                    code: null,
                    config: null
                },
                masterSlaveConfig: {
                    master: {
                        objectCode: this.params['objectCode'],
                        primaryKey: this.params['primaryKey']
                    },
                    slaves: [{
                        objectCode: null,
                        foreignFieldCode: null,
                        order: 0
                    }],
                },
                singleGridConfig: {
                    singleGrid: {
                        objectCode: this.params['objectCode']
                    }
                },
                activeTab: 'first'
            }
        },
        methods: {
            handleClick(tab, event) {
                // todo
            },
            assemblyParams() {
                let type = this.feature.type;
                switch (type) {
                    case CONSTANT.FEATURE_TYPE.MasterSlaveGrid:
                        this.feature.config = this.masterSlaveConfig;
                        break;
                    case CONSTANT.FEATURE_TYPE.SingleGrid:
                        this.feature.config = this.singleGridConfig;
                        break;
                }
                return this.feature;
            },
            doSubmit() {
                let params = this.assemblyParams();
                const featureType = this.feature.type;
                let url = this.$compile(URL.FEATURE_ADD, {
                    featureType: featureType
                });
                this.$axios.post(url, params).then(resp => {
                    this.$message.success('操作成功');
                    this.$emit('ok', params);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onSubmit(event) {
                const name = 'featureForm';
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.doSubmit(event) // submit
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