<template>
  <el-form :ref="formRefName" v-bind="formConf"
           :model="model" :rules="rules"
           class="form-view"
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
      <el-form-item class="form-item-button">
        <el-button :id="meta.name + 'submit'" v-bind="buttonsConf.submit.conf"
                   @click="onSubmit" v-if="buttonsConf.submit.show">{{buttonsConf.submit.label}}</el-button>
        <el-button :id="meta.name + 'cancel'" v-bind="buttonsConf.cancel.conf"
                   @click="onCancel" v-if="buttonsConf.cancel.show">{{buttonsConf.cancel.label}}</el-button>
      </el-form-item>
    </slot>
    <!-- render-less behavior slot -->
    <!--        <slot name="bhv-cancel" :on="on" :actions="actions">-->
    <!--            <cancel v-bind="{on, actions}"></cancel>-->
    <!--        </slot>-->
    <!--        <slot name="bhv-submit" :on="on" :actions="actions">-->
    <!--            <submit v-bind="{on, actions}"></submit>-->
    <!--        </slot>-->
  </el-form>
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
import {printErr} from "../../../utils/common";

export default {
  name: "FormView",
  components: {MetaEasyEdit, NestFormItem, ...DefaultBehaviors},
  mixins: [ViewMetaBuilder(DefaultMeta), ViewMixin],
  provide() {
    return {
      isView: this.isView,
      objectCode: this.objectCode
    }
  },
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
      let {meta, model: params, objectCode} = this;

      let url = this.$compile(meta.action, {objectCode: objectCode});
      params['objectCode'] = objectCode;

      utils.filterEmptyStrToNull(params)

      utils.joinArrInObj(params);
      this.$axios.post(url, params).then(({msg = '提交成功'}) => {
        this.$emit('ok', params); //  default callback
        this.$message.success(msg);
      }).catch(({msg, message = 'Error'}) => {
        console.error(msg || message)
      })
    },
    onSubmit(ev) {
      const {meta: {name: refName}, isView} = this
      if (isView) {
        printErr('查看模式下，禁止表单提交')
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
          columns.filter(item => item.component_name !== 'RowGrid').forEach(item => {
            this.$set(this.model, item.name, utils.assertUndefined(model[item.name], item.default_value));
          });
          break;
        case formTypes.update: // 更新表单、查看表单: 备选址取record值
        case formTypes.view:
          const {record = {}} = meta
          columns.filter(item => item.component_name !== 'RowGrid').forEach(item => {
            this.$set(this.model, item.name, utils.assertUndefined(model[item.name], record[item.name]));
          });
          break;
      }
    },
    init() {
      this.assemblyModel(this.meta);
      // 拍扁的布局结构化展开必须在model组装之后, 因为model组装是依据meta.columns来的，其内部并未对columns做递归兼容. TODO 2.3 model组装基于最终meta，gridInfoStructured扔到ViewMetaBuilder的第二个入参中
      gridInfoStructured(this.meta)
    }
  },
  computed: {
    isView() {
      const {meta: {form_type = formTypes.add}} = this
      return formTypes.view.toUpperCase() === form_type.toUpperCase()
    },
    objectCode() {
      const {meta: {objectCode}} = this
      return objectCode
    },
    rules() {
      const {meta: {conf: {rules} = {}} = {}} = this
      return rules;
    },
    formStyle() {
      const {meta: {style = {}}, $root: {$options: {name}}} = this
      const newStyle = {}
      this.$merge(newStyle, style)
      if (name === 'DialogTmpl') { // 若表单是由DialogTmpl打开的(见dialog.js)，则width属性由DialogTmpl集成, 当前FormView设定百分百
        newStyle.width = '100%'
      }
      return newStyle
    },
    formConf() {
      const {meta: {conf}, $attrs} = this
      const formConf = {}
      this.$reverseMerge(formConf, conf)
      this.$reverseMerge(formConf, $attrs)
      return formConf
    },
    buttonsConf() {
      const {buttons: buttonsConf = {}} = this.meta
      return buttonsConf
    },
    fieldSlots() {
      return this.$scopedSlots
    },
    formRefName() {
      const {meta: {name = 'FormView'}} = this
      return name
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

<style scoped lang="scss">
// 保持el-form-item下的表单控件撑开到100%, 这样可以对齐，提升表单显示效果
.form-view {
  & :not(.form-item-button) {
    /deep/ .el-form-item__content {
      & > *:not(.el-button) {
        width: 100%;
      }
    }
  }

  .form-item-button {
    /deep/ .el-form-item__content {
      margin-left: 0 !important;
      text-align: center;
    }
  }
}
</style>
