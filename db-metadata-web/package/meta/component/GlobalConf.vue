<template>
    <div class="el-card">
        <el-form :model="confModel" label-width="80px" class="demo-form-inline" style="height: 100%">
            <el-row :gutter="12">
                <el-col :span="6">
                    <el-form-item label="组件">
                        <drop-down-box v-model="confModel.componentCode" :meta="componentMeta"
                                       @change="loadConf"></drop-down-box>
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
                            <json-box v-model="confModel.conf" :meta="confMeta" mode="form"></json-box>
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
                        <el-button type="primary" @click="preview">预览</el-button>
                        <el-button type="warning" @click="onUpdate">更新</el-button>
                        <el-button @click="onCancel">返回</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
    import utils from '../../utils'
    import {restUrl} from "../../constant/url";
    import EleProps from '../../constant/element-props'
    import DefaultDropDownBoxMeta from '../../core/dropdownbox/ui-conf'
    import DefaultJsonBoxMeta from '../../core/jsonbox/ui-conf'
    import {TagViewUtil} from "../../index";

    export default {
        name: "GlobalConf",
        data() {
            let componentMeta = {
                name: "component",
                label: "组件",
                data_url: restUrl.COMPONENT_CODE_LIST,
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
            this.$merge(componentMeta, DefaultDropDownBoxMeta);
            this.$merge(confMeta, DefaultJsonBoxMeta);
            return {
                componentMeta: componentMeta,
                confMeta: confMeta,
                confModel: {
                    componentCode: this.$route.query.componentCode,
                    conf: {}, // conf of component
                }
            }
        },
        methods: {
            loadConf: function () {
                const {componentCode} = this.confModel;
                if (!componentCode) {

                    this.confModel['conf'] = {};
                    return;
                }

                const url = this.$compile(restUrl.COMP_GOBAL_CONF_LOAD, {
                    componentCode: componentCode
                });

                this.$axios.get(url).then(resp => {
                    let data = resp.data;

                    for (let key in data) {
                        let confVal = data[key].replace(/\\/g, "");
                        let confValJson = JSON.parse(confVal);
                        confValJson['conf'] = confValJson['conf'] || {};
                        this.$merge(confValJson['conf'], EleProps(confValJson['component_name']));
                        this.confModel['conf'] = confValJson;
                        break;
                    }
                }).catch(({msg = '配置加载失败'}) => {
                    console.error('[ERROR] url: %s, msg: %s', url, msg);
                    this.$message.error(msg);
                })
            },
            deleteConf: function () {
                let url = this.$compile(restUrl.COMP_GOBAL_CONF_DELETE, this.confModel);
                this.$axios.delete(url).then(({msg = '配置删除成功'}) => {
                    this.$message.success(msg);
                }).catch(({msg = '配置删除失败'}) => {
                    this.$message.error(msg);
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
                    "componentCode": componentCode
                };

                params[componentCode] = conf;

                this.$axios({
                    method: 'POST',
                    url: restUrl.COMP_CONF_ADD,
                    data: params
                }).then((msg = '配置提交成功') => {
                    this.$message.success(msg);
                }).catch(({msg = '配置提交失败'}) => {
                    this.$message.error(msg);
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
                    url: restUrl.COMP_CONF_UPDATE,
                    data: params
                }).then(({msg = '配置更新成功'}) => {
                    this.$message.success(msg);
                }).catch(({msg = '配置更新失败'}) => {
                    this.$message.error(msg);
                })
            },
            onCancel: function () {
              TagViewUtil.deleteView(this.$route)
              this.$router.push(TagViewUtil.pop())
            },
            preview: function () {
                let data = this.confModel['conf'].hasOwnProperty('default_value') ? this.confModel['conf']['default_value'] : null;
                this.$dialog(this.confModel['conf'], data, {
                    title: '预览'
                })
            }
        },
        mounted() {
            const {componentCode} = this.confModel;
            if (!utils.isEmpty(componentCode)) {
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
