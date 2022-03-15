<template>
  <div class="view-container">
    <el-form :ref="meta['name']" v-bind="$reverseMerge(meta.conf, $attrs)"
             :model="model" :rules="rules"
             :style="formStyle"
             :disabled="isView">
      <slot name="form-item" v-bind:columns="meta.columns">

        <nest-form-item :columns="meta.columns" :model="model">
          <template v-for="(v, k) in fieldSlots" v-slot:[k]="props">
            <slot :name="k" v-bind:model="props.model" v-bind:column="props.column"></slot>
          </template>
        </nest-form-item>

      </slot>
      <slot name="action" v-bind:model="model" v-bind:conf="buttonsConf"
            v-bind:submit="onSubmit" v-bind:cancel="onCancel" v-if="!isView && buttonsConf.show">
        <el-form-item>
          <el-button :id="meta.name + 'submit'" v-bind="buttonsConf.submit.conf"
                     @click="onSubmit" v-text="buttonsConf.submit.label"
                     v-if="buttonsConf.submit.show"></el-button>
          <el-button :id="meta.name + 'cancel'" v-bind="buttonsConf.cancel.conf"
                     @click="onCancel" v-text="buttonsConf.cancel.label"
                     v-if="buttonsConf.cancel.show"></el-button>
        </el-form-item>
      </slot>
      <div style="float: right">
        <meta-easy-edit :object-code="meta.objectCode" component-code="FormView">
          <template #label><i class="el-icon-setting"></i></template>
        </meta-easy-edit>
      </div>

      <!-- render-less behavior slot -->
      <!--        <slot name="bhv-cancel" :on="on" :actions="actions">-->
      <!--            <cancel v-bind="{on, actions}"></cancel>-->
      <!--        </slot>-->
      <!--        <slot name="bhv-submit" :on="on" :actions="actions">-->
      <!--            <submit v-bind="{on, actions}"></submit>-->
      <!--        </slot>-->
    </el-form>
  </div>
</template>

<script>
import MetaEasyEdit from '@/../package/core/meta/src/MetaEasyEdit'
import utils from '../../../utils'
import DefaultBehaviors from './defaultBehaviors'
import DefaultMeta from '../ui-conf'
import NestFormItem from "./NestFormItem";
import {formTypes} from "../ui-conf";
import {gridInfoStructured} from "../../../meta/form-builder/formViewMetaParser";
import {ViewMixin, ViewMetaBuilder} from "../../ext/mixins";
import {getFormInstanceUrl} from "../index";

export default {
  name: "FormView",
  components: {MetaEasyEdit, NestFormItem, ...DefaultBehaviors},
  mixins: [ViewMetaBuilder(DefaultMeta, gridInfoStructured), ViewMixin],
  props: {
    model: { // 外部model的值拥有最高优先级, 但是key却是依据meta来确定
      type: Object,
      default: () => {
        return {}
      }
    },
    formType: String,
    primaryKv: String // 主键和值的表达式
  },
  watch: {
    'model': {
      handler: function (newV) {
        this.init()
      }
    }
  },
  methods: {
    getMetaUrl() {
      return getFormInstanceUrl(this.formType, this.primaryKv)
    },
    getItemRules(item) {
      let rules = item.hasOwnProperty('conf') ? item.conf['rules'] : [];
      return utils.isEmpty(rules) ? [] : rules;
    },
    setItem(name, value) {
      this.$set(this.model, name, value)
    },
    doSubmit(ev) {
      let {meta, model: params} = this;
      const {action, objectCode} = meta;

      let url = this.$compile(action, {objectCode: objectCode});
      params['objectCode'] = objectCode;

      utils.filterEmptyStrToNull(params)

      utils.joinArrInObj(params);
      this.$axios.post(url, params).then(({msg = '提交成功'}) => {
        this.$emit('ok', params); //  default callback
        this.$message.success(msg);
      }).catch(({msg = 'Error'}) => {
        console.error(msg)
      })
    },
    onSubmit(ev) {
      const {meta: {name: refName}, isView} = this
      if (isView) {
        return
      }

      const fn = 'submit';
      this.$refs[refName].validate((valid) => {
        if (valid) {
          if (this.$listeners.hasOwnProperty(fn)) {
            this.$emit(fn, this.model)
            return;
          }
          this.doSubmit(ev) // do submit
        } else {
          return false;
        }
      });
    },
    onCancel: function (ev) {
      const fn = 'cancel';
      if (this.$listeners.hasOwnProperty(fn)) {
        this.$emit(fn, this.model);
      } else {
        console.log('FormView default onCancel behavior.');
      }
    },
    assemblyModel(meta) {
      const {model} = this
      const {columns = [], form_type} = meta

      switch (form_type.toUpperCase()) {
        case formTypes.add: // 新增表单：备选值取默认值
          columns.forEach(item => {
            this.$set(this.model, item.name, utils.assertUndefined(model[item.name], item.default_value));
          });
          break;
        case formTypes.update: // 更新表单、查看表单: 备选址取record值
        case formTypes.view:
          const {record = {}} = meta
          columns.forEach(item => {
            this.$set(this.model, item.name, utils.assertUndefined(model[item.name], record[item.name]));
          });
          break;
      }
    },
    init() {
      this.assemblyModel(this.meta);
    }
  },
  computed: {
    isView() {
      const {meta: {form_type = formTypes.add}} = this
      return formTypes.view.toUpperCase() === form_type.toUpperCase()
    },
    rules() {
      const {meta: {conf: {rules} = {}} = {}} = this
      return rules;
    },
    formStyle() {
      const {$attrs: {width: attrWidth}, meta: {width: metaWidth}} = this
      return {
        margin: 'auto',
        width: utils.assertUndefined(attrWidth, metaWidth)
      }
    },
    buttonsConf() {
      const {buttons: buttonsConf = {}} = this.meta
      return buttonsConf
    },
    fieldSlots() {
      return this.$scopedSlots
    }
    // 支持无渲染的行为插槽
    // actions() {
    //     const {onSubmit, doSubmit, onCancel} = this;
    //     return {onSubmit, doSubmit, onCancel};
    // },
    // on() {
    //     return this.$on.bind(this);
    // }
  }
}
</script>

<style scoped>
</style>
