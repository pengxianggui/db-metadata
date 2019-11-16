<template>
    <div class="el-card">
        <el-form :model="confModel" label-width="80px" class="demo-form-inline" style="height: 100%">
            <el-row :gutter="12">
                <el-col :span="6">
                    <el-form-item label="组件">
                        <DropDownBox v-model="confModel.componentCode" :meta="componentMeta"
                                     @change="loadConf()"></DropDownBox>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item>
                        <el-button @click="deleteConf">删除配置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <template v-if="confModel.componentCode">
                <el-row>
                    <el-col>
                        <h2 align="center">{{confModel.componentCode}}</h2>
                        <el-form-item>
                            <JsonBox v-model="confModel.conf" :meta="confMeta" mode="form"></JsonBox>
                        </el-form-item>
                    </el-col>
                </el-row>
            </template>
            <template v-else>
                <div class="blank-tip">请先选择一个组件</div>
            </template>

            <el-row>
                <el-col>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
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
    import EleProps from '../element-props'

    export default {
        name: "MetaComponentGlobalConf",
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
            let confMeta = {
                name: "conf",
                label: "配置",
                conf: {
                    mode: 'code',
                }
            };
            this.$merge(componentMeta, DEFAULT.DropDownBox);
            this.$merge(confMeta, DEFAULT.JsonBox);
            return {
                componentMeta: componentMeta,
                confMeta: confMeta,
                confModel: {
                    componentCode: null,
                    conf: {}, // conf of component
                }
            }
        },
        methods: {
            loadConf: function () {
                const {componentCode} = this.confModel;
                const url = 'component/load';

                if (!componentCode) {
                    this.confModel['conf'] = {};
                    return;
                }

                this.$axios({
                    method: 'get',
                    url: url,
                    params: {
                        componentCode: componentCode
                    }
                }).then(resp => {
                    let data = resp.data;

                    for (let key in data) {
                        let confVal = data[key].replace(/\\/g, "");
                        let confValJson = JSON.parse(confVal);
                        confValJson['conf'] = confValJson['conf'] || {};
                        this.$merge(confValJson['conf'], EleProps(confValJson['component_name']));
                        this.confModel['conf'] = confValJson;
                        break;
                    }
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg||err);
                    this.$message.error(err.msg);
                })
            },
            deleteConf: function () {
                let url = this.$compile('/component/delete?componentCode={componentCode}', this.confModel);
                this.$axios.delete(url).then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onSubmit: function () {
                if (!this.confModel['componentCode']) {
                    this.$message.warning('必须选定一个组件');
                    return;
                }
                let componentCode = this.confModel['componentCode'];
                let conf = this.confModel['conf'];
                let params = {
                    "componentCode":  componentCode
                };

                params[componentCode] = conf;

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
                if (!this.confModel['componentCode']) {
                    this.$message.warning('必须选定一个组件');
                    return;
                }

                let key = this.confModel['componentCode'];
                let conf = this.confModel['conf'];
                let params = {};

                params[key] = conf;

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
            }
        },
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