<template>
    <div class="el-card">
        <el-form :model="confModel" label-width="80px" class="demo-form-inline" style="height: 100%">
            <el-row :gutter="12">
                <el-col :span="6">
                    <el-form-item label="组件">
<!--                        <drop-down-box v-model="confModel.componentCode" :meta="componentMeta"-->
<!--                                     @change="loadConf"></drop-down-box>-->
                        <!-- pxg_todo 暂时硬编码, 等后端接口支持再修改 -->
                        <radio-box v-model="confModel.componentCode"
                                   :options="['FormTmpl', 'TableList', 'SearchPanel']"
                                   @change="loadConf"></radio-box>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="元对象">
                        <drop-down-box v-model="confModel.objectCode" :meta="objectMeta"
                                     @change="loadConf"></drop-down-box>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item>
                        <el-button @click="deleteConf">删除配置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                        <el-button type="primary" @click="preview">预览</el-button>
                        <el-button type="warning" @click="onUpdate">更新</el-button>
                        <el-button @click="onCancel">返回</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-tabs type="border-card">
                <!--                <el-tab-pane :label="slave.objectCode" v-for="slave in slaves" :key="slave.objectCode">-->
                <el-tab-pane label="高级配置">
                    <template v-if="confModel.componentCode && confModel.objectCode">
                        <el-row>
                            <el-col>
                                <h2 align="center">元对象:{{confModel.objectCode}} 模板: {{confModel.componentCode}}</h2>
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
                                <el-button type="primary" @click="onSubmit">提交</el-button>
                                <el-button type="primary" @click="preview">预览</el-button>
                                <el-button type="warning" @click="onUpdate">更新</el-button>
                                <el-button @click="onCancel">返回</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-tab-pane>
                <el-tab-pane v-if="confModel.componentCode=='FormTmpl'" label="表单设计">
                    <form-builder></form-builder>
                </el-tab-pane>
            </el-tabs>

        </el-form>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL} from '@/constant';
    import EleProps from '@/config/element-props'
    import FormBuilder from "@/components/meta/form-builder/FormBuilder";

    export default {
        name: "InstanceConf",
        components: {FormBuilder},
        data() {
            let componentMeta = {
                name: "component",
                label: "组件",
                data_url: URL.COMPONENT_CODE_LIST,
                group: false,
                conf: {
                    "filterable": true,
                }
            };
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

            this.$merge(objectMeta, DEFAULT.DropDownBox);
            this.$merge(componentMeta, DEFAULT.DropDownBox);
            this.$merge(confMeta, DEFAULT.JsonBox);

            return {
                objectMeta: objectMeta,
                componentMeta: componentMeta,
                confMeta: confMeta,
                confModel: {
                    componentCode: this.$route.query.componentCode,
                    objectCode: this.$route.query.objectCode,
                    conf: {}, // conf of metaObject
                    fConf: {} // conf of fields
                }
            }
        },
        methods: {
            loadConf: function () {
                const {componentCode, objectCode} = this.confModel;
                if (!componentCode || !objectCode) {

                    this.confModel['conf'] = {};
                    this.confModel['fConf'] = {};
                    return;
                }

                const url = this.$compile(URL.COMP_INSTANCE_CONF_LOAD, {
                    objectCode: objectCode,
                    componentCode: componentCode
                });

                this.$axios.get(url).then(resp => {
                    let data = resp.data;

                    for (let key in data) {
                        if (key === 'fields') {
                            let fConf = data[key];
                            for (let fConfKey in fConf) {
                                let fConfVal = fConf[fConfKey].replace(/\\/g, "");
                                let fConfValJson = JSON.parse(fConfVal);
                                fConfValJson['conf'] = fConfValJson['conf'] || {};
                                this.$merge(fConfValJson['conf'] || {}, EleProps(fConfValJson['component_name']));
                                this.$set(this.confModel['fConf'], fConfKey, fConfValJson);
                            }
                            continue;
                        }
                        let confVal = data[key].replace(/\\/g, "");
                        let confValJson = JSON.parse(confVal);
                        confValJson['conf'] = confValJson['conf'] || {};
                        this.$merge(confValJson['conf'], EleProps(confValJson['component_name']));
                        this.confModel['conf'] = confValJson;
                    }
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg || err);
                    this.$message.error(err.msg);
                })
            },
            deleteConf: function () {
                let url = this.$compile(URL.COMP_INSTANCE_CONF_DELETE, this.confModel);
                this.$axios.delete(url).then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onSubmit: function () {
                if (!this.confModel['componentCode'] || !this.confModel['objectCode']) {
                    this.$message.warning('必须选定组件和元对象');
                    return;
                }

                let componentCode = this.confModel['componentCode'];
                let objectCode = this.confModel['objectCode'];
                let params = {
                    componentCode: componentCode,
                    objectCode: objectCode
                };
                params[objectCode] = this.confModel['conf'];
                for (let fConfKey in this.confModel['fConf']) {
                    params[fConfKey] = this.confModel['fConf'][fConfKey];
                }

                this.$axios({
                    method: 'POST',
                    url: URL.COMP_CONF_ADD,
                    data: params
                }).then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onUpdate: function () {
                if (!this.confModel['componentCode'] || !this.confModel['objectCode']) {
                    this.$message.warning('必须选定组件和元对象');
                    return;
                }

                let componentCode = this.confModel['componentCode'];
                let objectCode = this.confModel['objectCode'];
                let params = {
                    componentCode: componentCode,
                    objectCode: objectCode
                };
                params[objectCode] = this.confModel['conf'];
                for (let fConfKey in this.confModel['fConf']) {
                    params[fConfKey] = this.confModel['fConf'][fConfKey];
                }

                this.$axios({
                    method: 'POST',
                    url: URL.COMP_CONF_UPDATE,
                    data: params
                }).then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onCancel: function () {
                this.$router.back();
            },
            preview: function () {
                let meta = this.confModel['conf'];
                meta.columns = [];
                for (let key in this.confModel['fConf']) {
                    let item = this.confModel['fConf'][key];
                    meta.columns.push(item);
                }

                this.$dialog(meta, null, {
                    title: '预览'
                })
            }
        },
        mounted() {
            if (!utils.isEmpty(this.componentCode) && !utils.isEmpty(this.objectCode)) {
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
