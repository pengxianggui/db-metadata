<template>
    <el-form :ref="innerMeta['name']" v-bind="innerMeta.conf" :model="model">
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
        <el-form-item>
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
                const params = this.model;
                const action = this.innerMeta['action'];

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
                let columns = meta.columns;
                let record = meta.hasOwnProperty('record') && meta.record ? meta.record : {};

                this.isEdit = record.hasOwnProperty('id') && (record.id != null);

                if (Array.isArray(columns)) {
                    columns.forEach(item => {
                        this.$set(this.model, item.name, record[item.name]);
                    });
                }
            }
        },
        computed: {
            innerMeta() {
                let meta = this.$merge(this.meta, DEFAULT.FormTmpl);
                this.assemblyModel(meta);
                return meta;
            },
            // innerMeta() {
            //     let meta = this.$merge(this.meta, DEFAULT.FormTmpl);
            //     let columns = meta.columns;
            //     for (let i = 0; i < columns.length; i++) {
            //         let item = columns[i];
            //         this.$merge(item, DEFAULT[item.component_name]); // merge column
            //     }
            //     return meta;
            // },
            // model() { // rely on innerMeta
            //     let model = {};
            //     let record = this.innerMeta.hasOwnProperty('record') ? this.innerMeta.record : {};
            //     let columns = this.innerMeta.columns;
            //     for (let i = 0; i < columns.length; i++) {
            //         let item = columns[i];
            //         this.$set(model, item.name, record[item.name] || item.default_value);
            //     }
            //     return model;
            // }
        }
    }
</script>

<style scoped>

</style>
