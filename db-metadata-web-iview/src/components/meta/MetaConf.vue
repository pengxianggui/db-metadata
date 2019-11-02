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
                    <drop-down-box v-model="confModel.objectCode" :meta="objectMeta" @change="loadConf"></drop-down-box>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <h2 align="center">{{objectOrCompCode}}</h2>
                <el-form-item>
                    <json-box v-model="confModel.conf" :meta="confMeta"></json-box>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row v-for="(value, key, index) in confModel.fConf" :key="key">
            <el-col>
                <h4>{{index}}.{{key}}</h4>
                <el-form-item>
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
            const fieldUrl = "/table/list/meta_field?object_code={objectCode}&fs=field_code&s=100"; // Temporary solution: set a large pageSize value. you know, for load all data
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
                fieldUrl: fieldUrl,
                objectMeta: objectMeta,
                componentMeta: componentMeta,
                confMeta: confMeta,
                confModel: {
                    componentCode: null,
                    objectCode: null,
                    conf: {}, // conf of component or metaObject
                    fConf: {} // conf of fields
                }
            }
        },
        methods: {
            loadConf: function () {
                const {componentCode, objectCode} = this.confModel;

                if (!componentCode) return;
                this.$axios({
                    method: 'get',
                    url: 'component/load',
                    params: {
                        objectCode: objectCode,
                        componentCode: componentCode
                    }
                }).then(resp => {
                    let data = resp.data;
                    let confKey = this.objectOrCompCode;
                    let confVal = data[confKey].replace(/\\/g, "");
                    this.confModel.conf = JSON.parse(confVal);

                    let fConf = data['fields'];
                    for (let fConfKey in fConf) {
                        let fConfVal = fConf[fConfKey].replace(/\\/g, "");
                        this.$set(this.confModel['fConf'], fConfKey, JSON.parse(fConfVal));
                    }
                    // if (fConf)
                }).catch(err => {
                    console.log(err)
                })
            },
            onSubmit: function () {
                if (!this.confModel['componentCode']) {
                    this.$message({type: 'error', message: '必须选定一个组件'});
                    return;
                }
                let params = {
                    componentCode: this.confModel['componentCode'],
                    objectCode: this.confModel['objectCode']
                };

                let confKey = this.objectOrCompCode;
                params[confKey] = this.confModel['conf'];

                for (let fConfKey in this.confModel['fConf']) {
                    params[fConfKey] = this.confModel['fConf'][fConfKey];
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
                // pxg_todo
            }
        },
        computed: {
            objectOrCompCode() {
                return this.confModel['objectCode'] ? this.confModel['objectCode'] : this.confModel['componentCode']
            }
        }
    }
</script>

<style scoped>
</style>
