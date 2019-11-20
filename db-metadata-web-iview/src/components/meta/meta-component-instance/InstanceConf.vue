<template>
    <div class="el-card">
        <el-form :model="confModel" label-width="80px" class="demo-form-inline" style="height: 100%">
            <el-row :gutter="12">
                <el-col :span="6">
                    <el-form-item label="组件">
                        <DropDownBox v-model="confModel.componentCode" :meta="componentMeta"></DropDownBox>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="元对象">
                        <DropDownBox v-model="confModel.objectCode" :meta="objectMeta"
                                     @change="loadConf()"></DropDownBox>
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
                        <el-button @click="onCancel">取消</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <template v-if="confModel.componentCode && confModel.objectCode">
                <el-row>
                    <el-col>
                        <h2 align="center">{{confModel.objectCode}}</h2>
                        <el-form-item>
                            <JsonBox v-model="confModel.conf" :meta="confMeta" mode="form"></JsonBox>
                        </el-form-item>
                    </el-col>
                </el-row>
            </template>
            <template v-else>
                <div class="blank-tip">请先选择一个组件</div>
            </template>
            <el-row v-for="(value, key, index) in confModel.fConf" :key="key">
                <el-col>
                    <h4>{{index}}.{{key}}</h4>
                    <el-form-item>
                        <JsonBox v-model="confModel.fConf[key]" :meta="confMeta" mode="form"></JsonBox>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                        <el-button type="primary" @click="preview">预览</el-button>
                        <el-button type="warning" @click="onUpdate">更新</el-button>
                        <el-button @click="onCancel">取消</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant';
    import EleProps from '../../element-props'

    export default {
        name: "InstanceConf",
        props: {
            R_cc: String,
            R_oc: String
        },
        data() {
            let componentMeta = {
                name: "component",
                label: "组件",
                data_url: "/component/list",
                group: false,
                conf: {
                    "filterable": true,
                }
            };
            let objectMeta = {
                name: "object",
                label: "元对象",
                data_url: "/table/list?objectCode=meta_object&fs=code",
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
                    componentCode: this.R_cc,
                    objectCode: this.R_oc,
                    conf: {}, // conf of metaObject
                    fConf: {} // conf of fields
                }
            }
        },
        methods: {
            loadConf: function () {
                const {componentCode, objectCode} = this.confModel;
                const url = 'component/load';

                if (!componentCode || !objectCode) {
                    this.confModel['conf'] = {};
                    this.confModel['fConf'] = {};
                    return;
                }

                this.$axios({
                    method: 'get',
                    url: url,
                    params: {
                        objectCode: objectCode,
                        componentCode: componentCode
                    }
                }).then(resp => {
                    let data = resp.data;

                    for (let key in data) {
                        if (key === 'fields') {
                            let fConf = data[key];
                            for (let fConfKey in fConf) {
                                let fConfVal = fConf[fConfKey].replace(/\\/g, "");
                                let fConfValJson = JSON.parse(fConfVal);
                                fConfValJson['conf'] = fConfValJson['conf'] || {};
                                this.$merge(fConfValJson['conf']||{}, EleProps(fConfValJson['component_name']));
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
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg||err);
                    this.$message.error(err.msg);
                })
            },
            deleteConf: function () {
                let url = this.$compile('/component/delete/{objectCode}?componentCode={componentCode}', this.confModel);
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
                    url: 'component/doAdd',
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
                    url: 'component/doUpdate',
                    data: params
                }).then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onCancel: function () {
                // pxg_todo
            },
            preview: function () {
                let meta = this.confModel['conf'];
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
            if (this.R_cc !== undefined && this.R_oc !== undefined) {
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
