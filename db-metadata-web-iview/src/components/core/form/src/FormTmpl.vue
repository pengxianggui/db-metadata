<template>
    <el-form :ref="innerMeta['name']" v-bind="$reverseMerge(innerMeta.conf, $attrs)" :model="model" :rules="rules">
        <slot name="form-item" v-bind:columns="innerMeta.columns">
            <template v-for="(item, index) in innerMeta.columns">
                <slot :name="'form-item-' + item.name" v-bind:columnMeta="item" v-bind:value="model[item.name]">
                    <el-form-item :key="item.name + index" v-if="!item.hasOwnProperty('showable') || item.showable"
                                  :label="item.label||item.name" :prop="item.name"
                                  :class="{'inline': item.inline, 'width-align': item.inline}"
                                  :rules="getItemRules(item)">
                        <component :is="item.component_name" v-model="model[item.name]" :meta="item"></component>
                    </el-form-item>
                </slot>
            </template>
        </slot>
        <el-form-item v-if="innerMeta.columns.length > 0">
            <slot name="action" v-bind:model="model">
                <el-button :id="innerMeta.name + 'submit'" v-bind="innerMeta.btns['submit']['conf']"
                           @click="onSubmit"
                           v-text="innerMeta.btns['submit']['label']"></el-button>
                <el-button :id="innerMeta.name + 'cancel'" v-bind="innerMeta.btns['cancel']['conf']"
                           @click="onCancel"
                           v-text="innerMeta.btns['cancel']['label']"></el-button>
            </slot>
            <div style="float: right">
                <pop-menu trigger="click" placement="right" v-if="$hasAuth('ADMIN')">
                    <template #label><i class="el-icon-setting"></i></template>
                    <template #default>
                        <list>
                            <list-item @click="editUIConf">编辑UI</list-item>
                        </list>
                    </template>
                </pop-menu>
            </div>
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT, URL} from '@/constant'
    import utils from '@/utils'

    export default {
        name: "FormTmpl",
        data() {
            return {
                model: {},
                isEdit: false
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        methods: {
            getItemRules(item) {
                let rules = item.hasOwnProperty('conf') ? item.conf['rules'] : [];
                return utils.isEmpty(rules) ? [] : rules;
            },
            editUIConf() {
                const url = URL.RR_INSTANCE_CONF_ADD;
                const {objectCode} = this.innerMeta;
                let routeUrl = this.$router.resolve({
                    path: url,
                    query: {
                        componentCode: 'FormTmpl',
                        objectCode: objectCode
                    }
                });
                window.open(routeUrl.href, '_blank');
            },
            doSubmit(ev) {
                let {innerMeta, model: params} = this;
                const {action, objectCode} = innerMeta;

                let url = this.$compile(action, {objectCode: objectCode});
                params['objectCode'] = objectCode;
                
                utils.joinArrInObj(params);
                this.$axios.post(url, params).then(resp => {
                    this.$emit('ok', params); //  default callback
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            onSubmit(ev) {
                const {name: refName} = this.innerMeta;
                const fn = 'submit';
                if (this.$listeners.hasOwnProperty(fn)) {
                    this.$emit(fn, this.model);
                } else {
                    this.$refs[refName].validate((valid) => {
                        if (valid) {
                            this.doSubmit(ev) // do submit
                        } else {
                            return false;
                        }
                    });
                }
            },
            onCancel: function (event) {
                if (this.$listeners.cancel) {
                    this.$emit('cancel', event);

                }
            },
            assemblyModel(meta) {
                this.model = {};
                let columns = utils.isArray(meta.columns) ? meta.columns : [];

                // pxg_todo 编辑/新增 模式根据是否含有record字段 && record非空
                this.isEdit = utils.hasProp(meta, 'record');

                if (this.isEdit) {
                    let record = utils.isObject(meta['record']) ? meta['record'] : {};
                    columns.forEach(item => {
                        this.$set(this.model, item.name, record[item.name]);
                    });
                } else {
                    columns.forEach(item => {
                        this.$set(this.model, item.name, item.default_value);
                    });
                }
            }
        },
        computed: {
            innerMeta() {
                let newMeta = utils.deepClone(this.meta);
                this.$merge(newMeta, DEFAULT.FormTmpl);
                this.assemblyModel(newMeta);
                return newMeta;
            },
            rules() {
                let rules = this.innerMeta.hasOwnProperty('conf') ? this.innerMeta['conf']['rules'] : {};
                return utils.isEmpty(rules) ? {} : rules;
            }
        }
    }
</script>

<style scoped>
    .width-align {
        width: 400px; /* pxg_todo 临时解决控件对齐问题 */
    }
</style>
