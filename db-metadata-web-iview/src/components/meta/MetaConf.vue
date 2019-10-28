<template>
    <el-form :model="confModel" label-width="80px">
        <el-form-item label="组件">
            <drop-down-box v-model="confModel.componentCode" :meta="componentMeta" @change="loadConf"></drop-down-box>
        </el-form-item>
        <el-form-item label="元对象">
            <drop-down-box v-model="confModel.objectCode" :meta="objectMeta" @change="loadConf"></drop-down-box>
        </el-form-item>
        <el-form-item label="Meta-Conf">
            <json-box v-model="confModel.conf" :meta="confMeta"></json-box>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="onCancel">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "meta-conf",
        data() {
            let objectMeta = {
                "name": "meta",
                "data_url": "/table/list?objectCode=meta_object&fs=code",
                "group": false,
                "conf": {
                    clearable: true
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
            let componentMeta = {
                "name": "component",
                "data_url": "/component/list",
                "group": false,
                "conf": {
                    clearable: true
                }
            };
            let confMeta = {
                "name": "conf",
                "conf": {
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
                    objectCode: null,
                    componentCode: null,
                    conf: null
                }
            }
        },
        methods: {
            loadConf: function() {
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
            onSubmit: function () {
                let _this = this;
                this.$axios({
                    method: 'POST',
                    url: 'component/doAdd',
                    data: {
                        componentCode: _this.confModel.componentCode,
                        objectCode: _this.confModel.objectCode,
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

</style>
