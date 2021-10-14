<template>
  <div ref="formBuilder" id="form-builder">
    <div class="opr-box">
      <template v-if="!EDIT_MODE">
        <meta-object-selector size="mini" v-model="formMeta.objectCode" @change="handleChange"
                              @clear="handleChange"></meta-object-selector>
        <span v-if="isAutoComputed" style="color: red;font-size: 12px;margin-left: 10px">后台自动计算</span>
      </template>
      <template v-else>
        <span><span>实例编码:</span><el-tag size="mini" align="center">{{ instanceCode }}</el-tag></span>&nbsp;&nbsp;
        <span><span>元对象:</span><el-tag size="mini" align="center">{{ formMeta.objectCode }}</el-tag></span>&nbsp;&nbsp;
        <span><span>组件编码:</span><el-tag size="mini" align="center">FormView</el-tag></span>&nbsp;
      </template>

      <span style="flex: 1"></span>
      <el-button-group style="margin-right: 10px;">
        <el-tooltip content="视图预览" placement="top">
          <el-button @click="preview" icon="el-icon-view" size="mini" type="primary"></el-button>
        </el-tooltip>
        <el-tooltip content="JSON预览" placement="top">
          <el-button @click="jsonView" icon="el-icon-view" size="mini" type="warning"></el-button>
        </el-tooltip>
        <el-tooltip content="保存" placement="top">
          <el-button @click="submitForm" icon="el-icon-download" size="mini" type="success"></el-button>
        </el-tooltip>
        <el-tooltip content="重置" placement="top">
          <el-button @click="resetForm" icon="el-icon-refresh-left" size="mini" type="danger"
                     v-if="EDIT_MODE"></el-button>
        </el-tooltip>
      </el-button-group>
      <full-screen :target="$refs['formBuilder']" id="form-builder"></full-screen>
    </div>
    <div id="form-builder-main">
      <ComponentList :form-meta="formMeta" :edit-mode="EDIT_MODE" style="width: 200px; overflow: auto;"></ComponentList>
      <div style="flex: 5;margin:0 5px">
        <WorkArea v-model="formMeta" :active-item.sync="activeItem"></WorkArea>
      </div>
      <div style="width: 300px;">
        <ConfArea v-model="formMeta" :active-item="activeItem" :object-code="formMeta.objectCode"
                  :field-code="fieldCode"></ConfArea>
      </div>
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
import DefaultJsonBoxMeta from "../../core/jsonbox/ui-conf";
import {isEmpty} from "../../utils/common";
import extractConfig from "../instance-component/extractConfig";
import {gridInfoFattened, gridInfoStructured} from "./formViewMetaParser";

export default {
  name: "FormBuilder",
  components: {
    ComponentList,
    WorkArea,
    ConfArea,
    MetaObjectSelector
  },
  props: {
    ic: String,
    oc: String
  },
  data() {
    const {oc: objectCode, ic: instanceCode} = this
    return {
      metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
      formMeta: this.$merge({objectCode: objectCode}, DefaultFormViewMeta), // FormView渲染的元数据
      isAutoComputed: false,
      activeItem: {},
      instanceCode: instanceCode,
      instanceName: null
    }
  },
  methods: {
    handleChange() {
      const {formMeta: {objectCode}} = this;
      if (objectCode) {
        this.autoComputedLoad(objectCode);
      } else {
        this.setInitState(null)
        this.isAutoComputed = false
      }
      this.$emit('oc-change', objectCode);
    },
    setInitState(objectCode) {
      this.formMeta = this.$merge({objectCode: objectCode}, DefaultFormViewMeta);
      this.activeItem = {}
    },
    instanceConfLoad(instanceCode, objectCode) {
      let url = this.$compile(restUrl.COMP_INSTANCE_CONF_LOAD_EDIT, {
        instanceCode: instanceCode,
        objectCode: objectCode,
        componentCode: 'FormView'
      })
      this.loadConf(url)
    },
    autoComputedLoad(objectCode) {
      this.$confirm('系统将为您提供自动计算的配置', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = this.$compile(restUrl.COMP_INSTANCE_CONF_LOAD_NEW, {
          objectCode: objectCode,
          componentCode: 'FormView'
        });
        this.loadConf(url)
      }).catch(() => {
      })
    },
    loadConf(url) {
      const _this = this
      const {$axios, formMeta: {objectCode}} = _this;
      _this.setInitState(objectCode);

      return new Promise((resolve, reject) => {
        $axios.safeGet(url).then(resp => {
          let {data} = resp;

          let {isAutoComputed = false, instanceName, fieldsMap} = data;
          _this.isAutoComputed = isAutoComputed;
          _this.instanceName = instanceName;
          // extract object config
          _this.$reverseMerge(_this.formMeta, extractConfig.call(_this, data, objectCode));

          // extract field config
          Object.keys(fieldsMap).forEach(key => _this.formMeta.columns.push(extractConfig.call(_this, fieldsMap, key)));

          gridInfoStructured(_this.formMeta)
          resolve()
        }).catch(({msg = '配置加载成功'}) => {
          console.error('[ERROR] url: %s, msg: %s', url, msg);
          _this.setInitState(objectCode);
          reject()
        })
      })
    },
    jsonView() {
      this.$dialog(DefaultJsonBoxMeta, this.formMeta, {
        title: "Json预览"
      })
    },
    preview() {
      const {formMeta: {width}} = this
      this.$dialog(this.formMeta, null, {
        title: "视图预览",
        width: width
      })
    },
    resetForm() {
      this.$confirm('您当前的更改将丢失', '提示', {
        confirmButtonText: '继续重置',
        cancelButtonText: '取消'
      }).then(() => {
        const {instanceCode, objectCode} = this
        this.instanceConfLoad(instanceCode, objectCode)
      }).catch(() => {
      })
    },
    submitForm() {
      // TODO 由于目前UIConf的接口要求必须拍平字段的配置参数, 所以栅格设置数据先丢失，后面根据更新后的接口调整此处的逻辑
      const {formMeta: {objectCode: objectCode, columns}, ic: instanceCode = objectCode + '.FormView', EDIT_MODE} = this
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
          gridInfoFattened(objectConf)

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
        })
      })
    },
  },
  mounted() {
    const {instanceCode, formMeta: {objectCode}} = this
    if (instanceCode) {
      this.instanceConfLoad(instanceCode, objectCode)
    }
  },
  computed: {
    EDIT_MODE() { // 编辑模式: 实例配置编辑
      const {instanceCode} = this
      return !utils.isEmpty(instanceCode)
    },
    fieldCode() {
      const {activeItem: {name}} = this
      return name
    }
  }
}
</script>

<style scoped lang="scss">
  #form-builder {
    display: flex; flex-direction: column; padding: 5px;height: 100%;box-sizing: border-box; background-color: white;

    .opr-box {
      display: flex; margin: 2px; align-items: center;
    }

    #form-builder-main {
      display: flex; height: calc(100% - 32px); box-sizing: border-box;
    }
  }
</style>
