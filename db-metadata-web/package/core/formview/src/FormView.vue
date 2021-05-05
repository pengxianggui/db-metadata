<template>
  <el-form :ref="innerMeta['name']" v-bind="$reverseMerge(innerMeta.conf, $attrs)" :model="innerModel" :rules="rules"
           :style="formStyle">
    <slot name="form-item" v-bind:columns="innerMeta.columns">

      <nest-form-item :columns="innerMeta.columns" :model="innerModel">
        <template v-for="(v, k) in fieldSlots" v-slot:[k]="props">
          <slot :name="k" v-bind:model="props.model" v-bind:column="props.column"></slot>
        </template>
      </nest-form-item>

    </slot>
    <slot name="action" v-bind:model="innerModel" v-bind:conf="buttonsConf"
          v-bind:submit="onSubmit" v-bind:cancel="onCancel" v-if="!isView && buttonsConf.show">
      <el-form-item>
        <el-button :id="innerMeta.name + 'submit'" v-bind="buttonsConf['submit']['conf']"
                   @click="onSubmit"
                   v-text="buttonsConf['submit']['label']"></el-button>
        <el-button :id="innerMeta.name + 'cancel'" v-bind="buttonsConf['cancel']['conf']"
                   @click="onCancel"
                   v-text="buttonsConf['cancel']['label']"></el-button>
      </el-form-item>
    </slot>
    <div style="float: right">
      <meta-easy-edit :object-code="innerMeta.objectCode" component-code="FormView">
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
</template>

<script>
import MetaEasyEdit from '../../meta/src/MetaEasyEdit'
import utils from '../../../utils'
import DefaultBehaviors from './defaultBehaviors'
import DefaultMeta from '../ui-conf'
import NestFormItem from "./NestFormItem";
import {formTypes} from "../ui-conf";
import {gridInfoStructured} from "../../../meta/form-builder/formViewMetaParser";

export default {
  name: "FormView",
  components: {MetaEasyEdit, NestFormItem, ...DefaultBehaviors},
  provide() {
    return {
      isView: this.isView // 注入子field, 以便实现FormView的view形态
    }
  },
  data() {
    return {
      innerModel: {},
      isEdit: false,
      formTypes: formTypes,
    }
  },
  props: {
    meta: {
      type: Object,
      default: () => {
        return {}
      }
    },
    model: { // 外部model的值拥有最高优先级, 但是key却是依据meta来确定
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  methods: {
    getItemRules(item) {
      let rules = item.hasOwnProperty('conf') ? item.conf['rules'] : [];
      return utils.isEmpty(rules) ? [] : rules;
    },
    setItem(name, value) {
      this.$set(this.innerModel, name, value)
    },
    doSubmit(ev) {
      let {innerMeta, innerModel: params} = this;
      const {action, objectCode} = innerMeta;

      let url = this.$compile(action, {objectCode: objectCode});
      params['objectCode'] = objectCode;

      utils.joinArrInObj(params);
      this.$axios.post(url, params).then(({msg = '提交成功'}) => {
        this.$emit('ok', params); //  default callback
        this.$message.success(msg);
      }).catch(({msg = 'Error'}) => {
        this.$message.error(msg);
      })
    },
    onSubmit(ev) {
      const {innerMeta: {name: refName}, isView} = this
      if (isView) {
        return
      }

      const fn = 'submit';
      this.$refs[refName].validate((valid) => {
        if (valid) {
          if (this.$listeners.hasOwnProperty(fn)) {
            this.$emit(fn, this.innerModel)
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
        this.$emit(fn, this.innerModel);
      } else {
        console.log('FormView default onCancel behavior.');
      }
    },
    assemblyModel(meta) {
      const {model: model} = this
      this.innerModel = {};
      let columns = utils.isArray(meta.columns) ? meta.columns : [];

      // 编辑/新增 模式根据是否含有record字段 && record非空
      this.isEdit = utils.hasProp(meta, 'record');

      if (this.isEdit) {
        let record = utils.isObject(meta['record']) ? meta['record'] : {};
        columns.forEach(item => {
          this.$set(this.innerModel, item.name, utils.assertUndefined(model[item.name], record[item.name]));
        });
      } else {
        columns.forEach(item => {
          this.$set(this.innerModel, item.name, utils.assertUndefined(model[item.name], item.default_value));
        });
      }
    }
  },
  computed: {
    isView() {
      const {innerMeta: {form_type: metaFormType}, $attrs: {formType: attrFormType}} = this
      const formType = utils.assertUndefined(attrFormType, metaFormType)
      return formTypes.view.toUpperCase() === formType.toUpperCase()
    },
    innerMeta() {
      let newMeta = utils.deepClone(this.meta);
      this.$merge(newMeta, DefaultMeta);
      this.assemblyModel(newMeta);
      return gridInfoStructured(newMeta);
    },
    rules() {
      const {innerMeta: {conf: {rules} = {}} = {}} = this
      return rules;
    },
    formStyle() {
      const {$attrs: {width: attrWidth}, innerMeta: {width: metaWidth}} = this
      return {
        margin: 'auto',
        width: utils.assertUndefined(attrWidth, metaWidth)
      }
    },
    buttonsConf() {
      return this.innerMeta['buttons'];
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
