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
            <slot name="action" v-bind:model="model" v-bind:conf="buttonsConf">
                <el-button :id="innerMeta.name + 'submit'" v-bind="buttonsConf['submit']['conf']"
                           @click="$emit('submit')"
                           v-text="buttonsConf['submit']['label']"></el-button>
                <el-button :id="innerMeta.name + 'cancel'" v-bind="buttonsConf['cancel']['conf']"
                           @click="$emit('cancel')"
                           v-text="buttonsConf['cancel']['label']"></el-button>
            </slot>
            <div style="float: right">
                <meta-easy-edit :object-code="innerMeta.objectCode" component-code="FormView">
                    <template #label><i class="el-icon-setting"></i></template>
                </meta-easy-edit>
            </div>
        </el-form-item>

        <!-- render-less behavior slot -->
        <slot name="bhv-cancel" :on="on" :actions="actions">
            <cancel v-bind="{on, actions}"></cancel>
        </slot>
        <slot name="bhv-submit" :on="on" :actions="actions">
            <submit v-bind="{on, actions}"></submit>
        </slot>
    </el-form>
</template>

<script>
    import MetaEasyEdit from '@/components/meta/relate/MetaEasyEdit'
    import utils from '@/utils'
    import DefaultBehaviors from './defaultBehaviors'
    import DefaultMeta from '../ui-conf'

    export default {
        name: "FormView",
        components: {MetaEasyEdit, ...DefaultBehaviors},
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
            onCancel: function (ev) {
                console.log('FormView default onCancel behavior.');
            },
            assemblyModel(meta) {
                this.model = {};
                let columns = utils.isArray(meta.columns) ? meta.columns : [];

                // 编辑/新增 模式根据是否含有record字段 && record非空
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
                this.$merge(newMeta, DefaultMeta);
                this.assemblyModel(newMeta);
                return newMeta;
            },
            rules() {
                let rules = this.innerMeta.hasOwnProperty('conf') ? this.innerMeta['conf']['rules'] : {};
                return utils.isEmpty(rules) ? {} : rules;
            },
            buttonsConf() {
                return this.innerMeta['buttons'];
            },
            // 支持无渲染的行为插槽
            actions() {
                const {onSubmit, doSubmit, onCancel} = this;
                return {onSubmit, doSubmit, onCancel};
            },
            on() {
                return this.$on.bind(this);
            }
        }
    }
</script>

<style scoped>
    .width-align {
        width: 400px; /* pxg_todo 临时解决控件对齐问题 */
    }
</style>
