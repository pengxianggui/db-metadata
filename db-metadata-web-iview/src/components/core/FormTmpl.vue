<template>
    <el-form :ref="innerMeta['name']" v-bind="$reverseMerge(innerMeta.conf, $attrs)" :model="model">
        <slot name="form-item" v-bind:columns="innerMeta.columns">
            <template v-for="(item, index) in innerMeta.columns">
                <slot :name="'form-item-' + item.name" v-bind:columnMeta="item" v-bind:value="model[item.name]">
                    <el-form-item :key="item.name + index" v-if="!item.hasOwnProperty('showable') || item.showable"
                                  :label="item.label||item.name" :prop="item.name" :class="{inline: item.inline}"
                                  :rules="item.conf['rules']">
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
        </el-form-item>
    </el-form>
</template>

<script>
    import {DEFAULT} from '@/constant'
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
            doSubmit(ev) {
                const fn = 'submit';
                const action = this.innerMeta['action'];
                let params = this.model;

                if (this.$listeners.hasOwnProperty(fn)) {
                    this.$emit(fn, params);
                } else {
                    let url = this.$compile(action, {objectCode: this.innerMeta['objectCode']});
                    params['objectCode'] = this.innerMeta['objectCode'];
                    utils.joinArrInObj(params);
                    this.$axios.post(url, params).then(resp => {
                        this.$emit('ok', params); //  default callback
                        this.$message.success(resp.msg);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    })
                }
            },
            onSubmit(ev) {
                const refName = this.innerMeta['name'];
                this.$refs[refName].validate((valid) => {
                    if (valid) {
                        this.doSubmit(ev) // do submit
                    } else {
                        return false;
                    }
                });
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
                this.isEdit = meta.hasOwnProperty('record');

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
            }
        }
    }
</script>

<style scoped>

</style>
