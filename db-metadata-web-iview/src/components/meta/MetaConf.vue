<template>
    <el-form :model="confModel" label-width="80px" class="demo-form-inline" style="height: 100%">
        <el-container style="display: flex; height: 100%">
            <el-header>
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
            </el-header>
            <el-container style="flex: 1;">
                <el-aside v-if="fields.length > 0" width="200px" style="max-height: 600px; overflow: auto;">
                    <ul class="fields-ul">
                        <li v-for="(item, index) in fields" :key="index" v-bind:class="{'active':activeFieldIndex === index }" @click="choseField(item, index)">
                            <span v-text="index"></span>.
                            <span v-text="item['field_code']"></span>
                        </li>
                    </ul>
                </el-aside>
                <el-container>
                    <el-main>
                        <el-form-item label="Meta-Conf">
                            <json-box v-model="confModel.conf" :meta="confMeta" style="height: 600px;"></json-box>
                        </el-form-item>
                    </el-main>
                    <el-footer>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">提交</el-button>
                            <el-button @click="onCancel">取消</el-button>
                        </el-form-item>
                    </el-footer>
                </el-container>
            </el-container>
        </el-container>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant';
    import utils from '@/utils';
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
                    fieldCode: null,
                    conf: null
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
                        componentCode: _this.confModel.componentCode,
                        fieldCode: _this.confModel.fieldCode
                    }
                }).then(resp => {
                    if (resp.data) {
                        let conf = resp.data.replace(/\\/g, "");
                        _this.confModel.conf = JSON.parse(conf);
                    } else {
                        _this.confModel.conf = {}
                    }
                }).catch(err => {
                    console.log(err)
                })
            },
            loadFields: function () {
                if (!this.confModel.objectCode) {
                    this.fields = [];
                    this.activeFieldIndex = null;
                    this.confModel.objectCode = null;
                    this.confModel.fieldCode = null;
                } else {
                    this.$axios.get(utils.compile(this.fieldUrl, this.confModel)).then(resp => {
                        this.fields = resp.data;
                    }).catch(err => {
                        console.log(err)
                    })
                }
            },
            choseField: function(item, index) {
                if (this.activeFieldIndex === index) {
                    this.activeFieldIndex = null;
                    this.confModel.fieldCode = null;
                } else {
                    this.activeFieldIndex = index;
                    this.confModel.fieldCode = item['field_code'];
                }
                this.loadConf();
            },
            changeObject: function () {
                this.loadFields();
                this.loadConf();
            },
            onSubmit: function () {
                let _this = this;
                if (!_this.confModel.componentCode) {
                    _this.$message({type: 'error', message: '必须选定一个组件'});
                    return;
                }
                this.$axios({
                    method: 'POST',
                    url: 'component/doAdd',
                    data: {
                        componentCode: _this.confModel.componentCode,
                        objectCode: _this.confModel.objectCode,
                        fieldCode: _this.confModel.fieldCode,
                        conf: JSON.stringify(_this.confModel.conf)
                    }
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
