<template>
    <el-container direction="vertical" class="el-card" style="height: 100%;">
        <el-form ref="InstanceConf" :rules="rules" :model="confModel" label-width="80px"
                 style="height: 100%; display: flex; flex-direction: column;">
            <div class="top-fields">
                <el-form-item label="实例编码" prop="instanceCode" class="inline">
                    <text-box v-model="confModel.instanceCode" disabled></text-box>
                </el-form-item>
                <el-form-item label="元对象" prop="objectCode" class="inline">
                    <drop-down-box v-model="confModel.objectCode" :meta="objectMeta"
                                   disabled></drop-down-box>
                </el-form-item>
                <el-form-item label="组件" prop="componentCode" class="inline">
                    <!-- pxg_todo 暂时硬编码, 等后端接口支持再修改 -->
                    <radio-box v-model="confModel.componentCode" disabled
                               :options="['FormView', 'TableList', 'SearchPanel']"></radio-box>
                </el-form-item>
                <el-form-item label="实例描述">
                    <text-area-box v-model="confModel.instanceName"></text-area-box>
                </el-form-item>
                <el-form-item>
                    <div style="display: flex">
                        <el-button type="primary" @click="preview">预览</el-button>
                        <el-button type="warning" @click="onUpdate">更新</el-button>
                        <span style="flex: 1"></span>
                        <el-button @click="$router.go(-1)">返回</el-button>
                        <el-button @click="deleteConf" type="danger">删除配置</el-button>
                    </div>
                </el-form-item>
            </div>

            <div style="flex: 1; overflow: auto">
                <el-tabs type="border-card">
                    <el-tab-pane label="高级配置">
                        <div id="conf-panel">
                            <!--                            hash 模式下无法使用锚点-->
                            <!--                                                        <div id="conf-menu">-->
                            <!--                                                            <ul>-->
                            <!--                                                                <li v-for="(key, index) in Object.keys(confModel.fConf)" :key="key">-->
                            <!--                                                                    <a :href="'#' + key">{{index+1}}.{{key}}</a>-->
                            <!--                                                                </li>-->
                            <!--                                                            </ul>-->
                            <!--                                                        </div>-->
                            <div id="conf-content">
                                <el-row v-if="confModel.componentCode && confModel.objectCode">
                                    <el-col>
                                        <h2 align="center">元对象:{{confModel.objectCode}} 模板:
                                            {{confModel.componentCode}}<span
                                                v-if="isAutoComputed"
                                                style="color: red;font-size: 12px;margin-left: 10px">后台自动计算</span>
                                        </h2>
                                        <el-form-item>
                                            <mini-form-box v-model="confModel.conf" class="shadow"
                                                           :meta="objConfMeta" :show-change-type="true"
                                                           @json-change="() => buildObjectConfMeta(confModel.conf)">
                                                <template #button-expand="{value}">
                                                    <el-popover placement="right" trigger="click" popper-class="ui-conf-tip-popper">
                                                        <ui-conf-tip
                                                            :component-name="confModel.conf['component_name']"></ui-conf-tip>
                                                        <el-button slot="reference" size="mini" icon="el-icon-question"
                                                                   circle></el-button>
                                                    </el-popover>
                                                </template>
                                            </mini-form-box>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row v-else>
                                    <div class="blank-tip">请先选择一个组件</div>
                                </el-row>

                                <div class="field-conf-parent">
                                    <div class="field-conf" v-for="(key, index) in Object.keys(confModel.fConf)"
                                         :key="key">
                                        <el-form-item>
                                            <h1 :name="key">{{index+1}}.{{key}}</h1>
                                            <mini-form-box v-model="confModel.fConf[key]" class="shadow"
                                                           :meta="fieldsConfMeta[key]" :show-change-type="true"
                                                           @json-change="() => buildFieldConfMeta(confModel.fConf[key], key)">
                                                <template #button-expand="{value}">
                                                    <el-popover placement="right" trigger="click" popper-class="ui-conf-tip-popper">
                                                        <ui-conf-tip
                                                            :component-name="confModel.fConf[key]['component_name']"></ui-conf-tip>
                                                        <el-button slot="reference" size="mini" icon="el-icon-question"
                                                                   circle></el-button>
                                                    </el-popover>
                                                </template>
                                            </mini-form-box>
                                        </el-form-item>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane v-if="confModel.componentCode=='FormView'" label="表单设计">
                        <form-builder :oc="confModel.objectCode" @oc-change="handleOcChange"></form-builder>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </el-form>
    </el-container>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL} from '@/constant';
    import FormBuilder from "@/components/meta/form-builder/FormBuilder";
    import UiConfTip from '@/components/meta/component-instance/ext/UiConfTip'
    import extractConfig from './extractConfig'
    import buildMeta from '../buildMeta'

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

    export default {
        name: "InstanceConf",
        components: {FormBuilder, UiConfTip},
        data() {
            const {instanceCode, componentCode, objectCode} = this.$route.query;

            this.$merge(objectMeta, DEFAULT.DropDownBox);

            return {
                isAutoComputed: false,
                objectMeta: objectMeta,
                objConfMeta: {}, // 构建元对象配置迷你表单的元数据
                fieldsConfMeta: {}, // 构建元字段配置迷你表单的元数据
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
                        const url = this.$compile(URL.COMP_INSTANCE_CONF_LOAD_EDIT, {
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
                            Object.keys(fieldsMap).forEach(key =>
                                this.$set(this.confModel['fConf'], key, extractConfig.call(this, fieldsMap, key)));
                            // build object conf meta
                            this.buildObjectConfMeta(this.confModel['conf']);
                            // build fields conf meta
                            Object.keys(this.confModel['fConf']).forEach(key => this.buildFieldConfMeta(this.confModel['fConf'][key], key));
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
            onSubmit: function () { // 新增
                this.$refs['InstanceConf'].validate((valid) => {
                    if (valid) {
                        this.$prompt('请为这套配置设定一个唯一编码', {}).then(data => {
                            const instanceCode = data.value;
                            const {instanceName, componentCode, objectCode, conf: objectConf, fConf: fieldsConf} = this.confModel;

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
                        }).catch(() => {
                        });
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
            preview: function () {
                const {confModel: {conf, fConf}} = this;
                let meta = utils.deepClone(conf);
                meta.columns = [];
                for (let key in fConf) {
                    let item = fConf[key];
                    meta.columns.push(item);
                }

                this.$dialog(meta, null, {
                    title: '预览'
                })
            },
            buildObjectConfMeta(value) {
                this.objConfMeta = buildMeta(value);
            },
            buildFieldConfMeta(value, key) {
                this.fieldsConfMeta[key] = buildMeta(value, key);
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

<style lang="scss" scoped>
    .blank-tip {
        height: 400px;
        line-height: 400px;
        text-align: center;
        border: 1px solid #eee;
        margin: 5px 0;
        color: #999999;
    }

    .shadow {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
    }

    .top-fields {
        height: 200px;

        .inline {
            display: inline-block;
        }
    }

    .field-conf-parent {
        display: grid;
        grid-template-columns: 50% 50%;
        grid-template-rows: auto;
    }

    #conf-panel {
        display: flex;

        #conf-menu {
            width: 150px;

            & ul {
                padding: 0;
                list-style: none;
            }
        }

        #conf-content {
            flex: 1;
            overflow: auto;
        }
    }
</style>
<style>
    .ui-conf-tip-popper {
        box-shadow: 4px 12px 26px 0 rgba(0,0,0,0.2)
    }
</style>