<template>
  <div style="display: flex; padding: 5px;height: 100%;box-sizing: border-box;">
    <ComponentList :edit-mode="EDIT_MODE" style="width: 200px; overflow: auto;"></ComponentList>
    <div style="flex: 5;margin:0 5px">
      <WorkArea v-model="formMeta" :active-item.sync="activeItem">
        <template #opr-box>
          <el-button-group>
            <el-button @click="preview" icon="el-icon-view" size="mini" type="primary">视图预览</el-button>
            <el-button @click="jsonView" icon="el-icon-view" size="mini" type="primary">json预览</el-button>
            <el-button @click="submitForm" icon="el-icon-download" size="mini" type="success">保存</el-button>
            <el-button @click="resetForm" icon="el-icon-delete" size="mini" type="danger">重置</el-button>
          </el-button-group>
          <drop-down-box size="mini" placeholder="选择元对象" v-model="objectCode" :data-url="metaObjectCodeUrl"
                         @change="handleChange" @clear="handleClear" filterable>
            <template #options="{options}">
              <el-option v-for="item in options" :key="item.code" :label="item.code"
                         :value="item.code">
                {{ item.code }}
              </el-option>
            </template>
          </drop-down-box>
          <!--                  TODO 下面组件找不到？！InstanceConfEdit正常 -->
          <!--                  <meta-object-selector v-model="objectCode" @change="handleChange" @clear="handleClear"></meta-object-selector>-->
        </template>
      </WorkArea>
    </div>
    <div style="width: 300px;">
      <ConfArea v-model="formMeta" :active-item="activeItem" :object-code="objectCode"
                :field-code="fieldCode"></ConfArea>
    </div>
  </div>
</template>

<script>
import utils from '../../utils'
import {restUrl} from "../../constant/url"
import ComponentList from './ComponentList'
import WorkArea from './WorkArea'
import ConfArea from './ConfArea'
import DefaultFormViewMeta from '../../core/formview/ui-conf'
import MetaObjectSelector from "../component/MetaObjectSelector"
import {defaultMeta} from "../../core";
import DefaultJsonBoxMeta from "../../core/jsonbox/ui-conf";
import {isEmpty} from "../../utils/common";
import {TagViewUtil} from "../../index";

export default {
  name: "FormBuilder",
  components: {MetaObjectSelector, ComponentList, WorkArea, ConfArea},
  props: {
    ic: String,
    oc: String,
    cc: String
  },
  data() {
    const {oc: objectCode} = this
    return {
      formMeta: this.$merge({objectCode: objectCode}, DefaultFormViewMeta),
      activeItem: {},
      objectCode: objectCode,
      metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
    }
  },
  methods: {
    handleClear() {
      this.objectCode = null;
      this.handleChange();
      this.setInitState();
    },
    handleChange() {
      const {objectCode} = this;
      this.loadConf(objectCode);
      this.$emit('oc-change', objectCode);
    },
    setInitState() {
      this.formMeta = this.$merge({}, DefaultFormViewMeta);
    },
    loadConf(objectCode) {
      if (utils.isEmpty(objectCode)) return;
      const url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
        componentCode: 'FormView',
        objectCode: objectCode
      });
      this.$axios.safeGet(url).then(resp => {
        let formMeta = resp.data;
        this.$reverseMerge(this.formMeta, formMeta, true);
      }).catch(({msg = '加载失败'}) => {
        this.setInitState();
        this.$message.error(msg);
      })
    },
    jsonView() {
      this.$dialog(DefaultJsonBoxMeta, this.formMeta, {
        title: "Json预览"
      })
    },
    preview() {
      this.$dialog(this.formMeta, null, {
        title: "视图预览"
      })
    },
    submitForm() {
      const {formMeta: {objectCode: objectCode, columns}, ic: instanceCode, EDIT_MODE} = this
      if (isEmpty(objectCode)) {
        this.$message.warning('元对象编码不能为空!')
        return
      }
      if (isEmpty(columns)) {
        this.$message.warning('无字段配置内容!')
        return
      }

      let $confirm = EDIT_MODE ?
          this.$confirm('确认提交?')
          : this.$prompt(`请为这套配置设定一个唯一编码(instanceCode), 如不输入, 则默认为:${instanceCode}`, {})


      $confirm.then(({value}) => {
        let instanceName = '' // TODO 将此值合并到上面的prompt一并收集
        const componentCode = 'FormView';
        let objectConf = utils.deepClone(this.formMeta);
        let fieldsMap = {};

        if (objectConf.hasOwnProperty('columns')) {
          objectConf.columns.forEach(c => {
            fieldsMap[c.name] = c
          })
          delete objectConf['columns']; // 平铺, 接口不接受层级结构
        }
        let params = {
          instanceCode: utils.assertEmpty(value, instanceCode),
          instanceName: instanceName,
          componentCode: componentCode,
          objectCode: objectCode,
          fieldsMap: fieldsMap
        };
        params[objectCode] = objectConf;

        this.$axios({
          method: 'POST',
          url: EDIT_MODE ? restUrl.COMP_CONF_UPDATE : restUrl.COMP_CONF_ADD,
          data: params
        }).then(({msg = '配置保存成功'}) => {
          this.$message.success(msg);
          this.$goBack()
        }).catch(({msg = '配置保存失败'}) => {
          this.$message.error(msg);
        })
      })


    },
    resetForm() {
      this.$message.error("resetForm action not finished!");
    },
  },
  watch: {
    objectCode(newVal) {
      this.objectCode = newVal;
      this.loadConf(this.objectCode);
    }
  },
  mounted() {
    const objectCode = this.objectCode;
    this.loadConf(objectCode);
  },
  computed: {
    EDIT_MODE() { // 实例配置编辑模式
      const {oc: objectCode} = this
      return !utils.isEmpty(objectCode)
    },
    fieldCode() {
      const {activeItem: {name}} = this
      return name
    }
  }
}
</script>

<style scoped>
</style>
