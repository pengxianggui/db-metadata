<template>
    <div class="el-card">
        <el-form ref="InstanceConf" :rules="rules" :model="confModel" label-width="80px" class="demo-form-inline"
                 style="height: 100%">
            <el-row :gutter="12">
                <el-col>
                    <el-form-item label="组件" prop="componentCode" class="inline">
                        <!-- pxg_todo 暂时硬编码, 等后端接口支持再修改 -->
                        <radio-box v-model="confModel.componentCode" :options="['FormView', 'TableList', 'SearchPanel']"
                                   :disabled="isEdit"></radio-box>
                    </el-form-item>
                    <el-form-item label="元对象" prop="objectCode" class="inline">
                        <drop-down-box v-model="confModel.objectCode" :meta="objectMeta"
                                       :disabled="isEdit"></drop-down-box>
                    </el-form-item>
                    <el-form-item label="实例编码" prop="instanceCode" class="inline">
                        <text-box v-model="confModel.instanceCode" :disabled="isEdit"></text-box>
                    </el-form-item>
                    <el-form-item label="实例描述">
                        <text-area-box v-model="confModel.instanceName"></text-area-box>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item>
                        <div style="display: flex">
                            <el-button type="primary" @click="preview">预览</el-button>
                            <el-button type="primary" @click="onSubmit">提交</el-button>
                            <el-button type="warning" @click="onUpdate">更新</el-button>
                            <el-button type="primary" @click="loadConf">自动计算</el-button>
                            <span style="flex: 1"></span>
                            <el-button @click="onCancel">返回</el-button>
                            <el-button @click="deleteConf" type="danger">删除配置</el-button>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-tabs type="border-card">
                <!--                <el-tab-pane :label="slave.objectCode" v-for="slave in slaves" :key="slave.objectCode">-->
                <el-tab-pane label="高级配置">
                    <template v-if="confModel.componentCode && confModel.objectCode">
                        <el-row>
                            <el-col>
                                <h2 align="center">元对象:{{confModel.objectCode}} 模板: {{confModel.componentCode}}<span
                                    v-if="isAutoComputed"
                                    style="color: red;font-size: 12px;margin-left: 10px">后台自动计算</span>
                                </h2>
                                <el-form-item>
                                    <json-box v-model="confModel.conf" :meta="confMeta" mode="form"></json-box>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </template>
                    <template v-else>
                        <div class="blank-tip">请先选择一个组件</div>
                    </template>
                    <el-row v-for="(key, index) in Object.keys(confModel.fConf).length" :key="key" v-if="index%2==0">
                        <el-col :span="12">
                            <el-form-item>
                                <span>{{index+1}}.{{Object.keys(confModel.fConf)[index]}}</span>
                                <json-box v-model="confModel.fConf[Object.keys(confModel.fConf)[index]]"
                                          :meta="confMeta"
                                          mode="form"></json-box>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12" v-if="(index+1)!==Object.keys(confModel.fConf).length">
                            <el-form-item>
                                <span>{{index+2}}.{{Object.keys(confModel.fConf)[index+1]}}</span>
                                <json-box v-model="confModel.fConf[Object.keys(confModel.fConf)[index+1]]"
                                          :meta="confMeta"
                                          mode="form"></json-box>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <el-form-item>
                                <el-button type="primary" @click="preview">预览</el-button>
                                <el-button type="primary" @click="onSubmit">提交</el-button>
                                <el-button type="warning" @click="onUpdate">更新</el-button>
                                <el-button @click="onCancel">返回</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-tab-pane>
                <el-tab-pane v-if="confModel.componentCode=='FormView'" label="表单设计">
                    <form-builder :oc="confModel.objectCode" @oc-change="handleOcChange"></form-builder>
                </el-tab-pane>
            </el-tabs>

        </el-form>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL} from '@/constant';
    import FormBuilder from "@/components/meta/form-builder/FormBuilder";
    import extractConfig from './extractConfig'

    let objectMeta = {
        name: "object",
        label: "元对象",
        data_url: URL.OBJECT_CODE_LIST,
        group: false,
        conf: {
            "filterable": true,
            "clearable": true
        },
        behavior: {
            format: function (data) {
                let arr = [];
                for (let i = 0; i < data.length; i++) {
                    arr.push({
                        key: data[i].code,
                        value: data[i].code
                    })
                }
                return arr;
            }
        }
    };
    let confMeta = {
        name: "conf",
        label: "配置",
        conf: {
            mode: 'code',
        }
    };

    export default {
        name: "InstanceConf",
        components: {FormBuilder},
        data() {
            const {instanceCode, componentCode, objectCode} = this.$route.query;

            this.$merge(objectMeta, DEFAULT.DropDownBox);
            this.$merge(confMeta, DEFAULT.JsonBox);
            const isEdit = !utils.isEmpty(instanceCode) || (!utils.isEmpty(componentCode) && !utils.isEmpty(objectCode));

            return {
                isEdit: isEdit,
                isAutoComputed: false,
                objectMeta: objectMeta,
                confMeta: confMeta,
                confModel: {
                    instanceCode: utils.assertUndefined(instanceCode),
                    instanceName: null,
                    componentCode: componentCode,
                    objectCode: objectCode,
                    conf: {}, // conf of metaObject
                    fConf: {} // conf of fieldsMap
                },
                rules: {
                    instanceCode: [{required: true, message: '请设置一个唯一配置编码', trigger: 'blur'}],
                    componentCode: [{required: true, message: '请选择组件', trigger: 'blur'}],
                    objectCode: [{required: true, message: '请选择元对象', trigger: 'blur'}]
                }
            }
        },
        methods: {
            initConf() {
                this.confModel['conf'] = {};
                this.confModel['fConf'] = {};
            },
            handleOcChange(objectCode) {
                this.confModel['objectCode'] = objectCode;
            },
            loadConf: function () {
                this.initConf();
                const {confModel: {instanceCode, componentCode, objectCode}, $axios} = this;
                this.$refs['InstanceConf'].validate((valid) => {
                    if (valid) {
                        const url = this.$compile(URL.COMP_INSTANCE_CONF_LOAD, {
                            instanceCode: instanceCode,
                            objectCode: objectCode,
                            componentCode: componentCode
                        });

                        $axios.safeGet(url).then(resp => {
                            let {data} = resp;
                            let {isAutoComputed = false, instanceCode, instanceName, fieldsMap} = data;
                            this.isAutoComputed = isAutoComputed;

                            this.confModel['instanceCode'] = instanceCode;
                            this.confModel['instanceName'] = instanceName;
                            // extract object config
                            this.confModel['conf'] = extractConfig.call(this, data, objectCode, true);

                            // extract field config
                            for (let fieldName in fieldsMap) {
                                this.$set(this.confModel['fConf'], fieldName, extractConfig.call(this, fieldsMap, fieldName));
                            }
                        }).catch(err => {
                            console.error('[ERROR] url: %s, msg: %s', url, err.msg || err);
                            this.$message.error(err.msg);
                        })
                    } else {
                        this.$message.warning('请填写必填项')
                    }
                });
            },
            deleteConf: function () {
                this.$confirm('确定删除当前配置? 此操作无法撤销!', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let url = this.$compile(URL.COMP_INSTANCE_CONF_DELETE, this.confModel);
                    this.$axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    })
                })
            },
            onSubmit: function () {
                this.$refs['InstanceConf'].validate((valid) => {
                    if (valid) {
                        const {instanceCode, instanceName, componentCode, objectCode, conf: objectConf, fConf: fieldsConf} = this.confModel;

                        if (utils.isEmpty(componentCode) || utils.isEmpty(objectCode)) {
                            this.$message.warning('必须选定组件和元对象');
                            return;
                        }

                        let params = {
                            instanceCode: instanceCode,
                            instanceName: instanceName,
                            componentCode: componentCode,
                            objectCode: objectCode,
                            fieldsMap: fieldsConf
                        };
                        params[objectCode] = objectConf;

                        this.$axios({
                            method: 'POST',
                            url: URL.COMP_CONF_ADD,
                            data: params
                        }).then(resp => {
                            this.$message.success(resp.msg);
                        }).catch(err => {
                            this.$message.error(err.msg);
                        })
                    } else {
                        this.$message.warning('请填写必填项');
                    }
                });
            },
            onUpdate: function () {
                this.$refs['InstanceConf'].validate((valid) => {
                    if (valid) {
                        const {instanceCode, instanceName, componentCode, objectCode, conf: objectConf, fConf: fieldsConf} = this.confModel;

                        if (!this.confModel['componentCode'] || !this.confModel['objectCode']) {
                            this.$message.warning('必须选定组件和元对象');
                            return;
                        }

                        let params = {
                            instanceCode: instanceCode,
                            instanceName: instanceName,
                            componentCode: componentCode,
                            objectCode: objectCode,
                            fieldsMap: fieldsConf
                        };
                        params[objectCode] = objectConf;

                        this.$axios({
                            method: 'POST',
                            url: URL.COMP_CONF_UPDATE,
                            data: params
                        }).then(resp => {
                            this.$message.success(resp.msg);
                        }).catch(err => {
                            this.$message.error(err.msg);
                        })
                    }
                });
            },
            onCancel: function () {
                this.$router.back();
            },
            preview: function () {
                const {confModel: {conf, fConf}} = this;
                let meta = conf;
                meta.columns = [];
                for (let key in fConf) {
                    let item = fConf[key];
                    meta.columns.push(item);
                }

                this.$dialog(meta, null, {
                    title: '预览'
                })
            }
        },
        mounted() {
            const {instanceCode} = this.confModel;

            this.initConf();
            if (!utils.isEmpty(instanceCode)) {
                this.loadConf();
            }
        }
    }
</script>

<style scoped>
    .blank-tip {
        height: 400px;
        line-height: 400px;
        text-align: center;
        border: 1px solid #eee;
        margin: 5px 0;
        color: #999999;
    }
</style>
