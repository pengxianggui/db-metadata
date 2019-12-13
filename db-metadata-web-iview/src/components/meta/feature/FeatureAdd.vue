<template>
    <div>
        <el-form ref="featureForm" :model="feature" label-width="100px">
            <el-form-item label="功能类别">
                <radio-box :options="featureTypes" v-model="feature.type" required></radio-box>
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
                                   :options="objectCodes"
                                   :filterable="true" :required="true"></drop-down-box>
                </el-form-item>
                <el-form-item label="主键" class="inline">
                    <text-box v-model="masterSlaveConfig.master.primaryKey" required></text-box>
                </el-form-item>

                <el-tabs v-model="activeTab" @tab-click="handleClick">
                    <el-tab-pane label="从表1" name="first">
                        <el-form-item label="元对象编码" class="inline">
                            <drop-down-box v-model="masterSlaveConfig.slaves[0].objectCode"
                                           :options="objectCodes" :filterable="true" :required="true"></drop-down-box>
                        </el-form-item>
                        <el-form-item label="外键" class="inline">
                            <text-box v-model="masterSlaveConfig.slaves[0].foreignFieldCode" required></text-box>
                        </el-form-item>
                        <el-form-item label="排序" class="inline">
                            <text-box v-model="masterSlaveConfig.slaves[0].order" required></text-box>
                        </el-form-item>
                    </el-tab-pane>
                </el-tabs>

                <!-- TODO 从表后期应当支持多个子表设置 -->
            </template>
            <template v-if="feature.type === 'SingleGrid'">
                <el-form-item label="元对象编码">
                    <text-box v-model="singleGridConfig.objectCode" required></text-box>
                </el-form-item>
            </template>

            <el-button @click="onSubmit" type="primary">保存</el-button>
            <el-button @click="onCancel">取消</el-button>
        </el-form>
    </div>
</template>

<script>
    import {DEFAULT, URL} from '@/constant'
    import DropDownBox from "@/components/core/form/DropDownBox";

    export default {
        name: "feature-add",
        components: {DropDownBox},
        data() {
            return {
                featureTypes: [],
                objectCodes: [],
                feature: {
                    type: 'MasterSlaveGrid',
                    name: null,
                    code: null,
                    config: null
                },
                masterSlaveConfig: {
                    master: {
                        objectCode: null,
                        primaryKey: null
                    },
                    slaves: [{
                        objectCode: null,
                        foreignFieldCode: null,
                        order: 0
                    }],
                },
                singleGridConfig: {
                    objectCode: null
                },
                activeTab: 'first'
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        methods: {
            handleClick(tab, event) {
                  // todo
            },
            loadObjectCode() {
                this.$axios.get(URL.OBJECT_CODE_LIST).then(resp => {
                    this.objectCodes = resp.data.map(ele => {
                        return {
                            key: ele.code,
                            value: ele.code
                        }
                    })
                })
            },
            assemblyParams () {
                let type = this.feature.type;
                switch (type) {
                    case "MasterSlaveGrid":
                        this.feature.config = this.masterSlaveConfig;
                        break;
                    case "SingleGrid":
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
            this.$axios.get(URL.LIST_FEATURE_TYPE).then(resp => {
                this.featureTypes = resp.data;
            });
            this.loadObjectCode();
        },
        mounted() {
            // request business data
        },
        computed: {}
    }
</script>

<style scoped>

</style>