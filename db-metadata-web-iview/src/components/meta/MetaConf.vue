<template>
    <el-form :model="confModel" label-width="80px" class="demo-form-inline" style="height: 100%">
        <el-row :gutter="12">
            <el-col :span="6">
                <el-form-item label="组件">
                    <drop-down-box v-model="confModel.componentCode" :meta="componentMeta"
                                   @change="loadConf"></drop-down-box>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item label="元对象">
                    <drop-down-box v-model="confModel.objectCode" :meta="objectMeta" @change="changeObject"></drop-down-box>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <el-form-item :label="confModel.objectCode ? confModel.objectCode : confModel.componentCode">
                    <json-box v-model="confModel.conf" :meta="confMeta"></json-box>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row v-for="(value, key, index) in confModel.fConf" :key="key">
            <el-col>
                <h3>{{index}}.{{key}}</h3>
                <el-form-item label="conf">
                    <json-box v-model="confModel.fConf[key]" :meta="confMeta"></json-box>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">提交</el-button>
                    <el-button @click="onCancel">取消</el-button>
                </el-form-item>
            </el-col>
        </el-row>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant';
    import DropDownBox from "../atom/DropDownBox";

    export default {
        name: "meta-conf",
        components: {DropDownBox},
        data() {
            const fieldUrl = "/table/list/meta_field?object_code={objectCode}&fs=field_code&s=100"; // Temporary solution: set a large pageSize value
            let componentMeta = {
                name: "component",
                data_url: "/component/list",
                group: false,
                conf: {
                    "filterable": true,
                }
            };
            let objectMeta = {
                name: "object",
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
                conf: {
                    mode: 'code',
                }
            };

            this.$merge(objectMeta, DEFAULT.DropDownBox);
            this.$merge(componentMeta, DEFAULT.DropDownBox);
            this.$merge(confMeta, DEFAULT.JsonBox);

            return {
                fieldUrl: fieldUrl,
                objectMeta: objectMeta,
                componentMeta: componentMeta,
                confMeta: confMeta,
                fields: [],
                activeFieldIndex: null,
                confModel: {
                    componentCode: null,
                    objectCode: null,
                    conf: {},
                    fConf: {}
                }
            }
        },
        methods: {
            loadConf: function () {
                let _this = this;
                if (!_this.confModel.componentCode) return;
                this.$axios({
                    method: 'get',
                    url: 'component/load',
                    params: {
                        objectCode: _this.confModel.objectCode,
                        componentCode: _this.confModel.componentCode
                    }
                }).then(resp => {
                    let data = resp.data;
                    // let componentConf = data[this.confModel.componentCode].replace(/\\/g, "");
                    let tableConf = data[this.confModel.objectCode].replace(/\\/g, "");
                    let fieldsConf = data['fields'];
                    // this.confModel.conf = JSON.parse(componentConf);
                    if (this.confModel.objectCode) {
                        this.confModel.conf = JSON.parse(tableConf);
                    }
                    for(let key in fieldsConf) {
                        let conf = fieldsConf[key].replace(/\\/g, "");
                        this.$set(this.confModel.fConf, key,  JSON.parse(conf));
                    }
                }).catch(err => {
                    console.log(err)
                })
            },
            changeObject: function () {
                this.loadConf();
            },
            onSubmit: function () {
                let _this = this;
                if (!_this.confModel.componentCode) {
                    _this.$message({type: 'error', message: '必须选定一个组件'});
                    return;
                }
                let params = {
                    componentCode: _this.confModel.componentCode,
                    objectCode: _this.confModel.objectCode,
                    conf: JSON.stringify(_this.confModel.conf)
                };

                let fieldCode = _this.confModel.fieldCode;
                if (fieldCode) {
                    let conf = {};
                    conf[fieldCode] = _this.confModel.conf;
                    params.conf = JSON.stringify(conf);
                }
                this.$axios({
                    method: 'POST',
                    url: 'component/doAdd',
                    data: params
                }).then(resp => {
                    this.$message({type: "success", message: "保存成功"});
                }).catch(err => {
                    this.$message({type: "error", message: "保存成功"});
                })
            },
            onCancel: function () {
                // todo
            }
        },
    }
</script>

<style scoped>
    .fields-ul {
        list-style: none;
    }
    .fields-ul li {
        min-height: 30px;
        line-height: 30px;
        padding: 3px 2px;
        border-bottom: 1px solid #e6e6e6;
        cursor: pointer;
    }
    .fields-ul li:hover {
        background-color: #cadfff;
    }
    .fields-ul li.active {
        background-color: aqua;
    }
</style>
