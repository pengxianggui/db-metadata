<template>
  <div ref="formBuilder" id="form-builder">
    <div class="opr-box">
      <el-button size="mini" type="primary" plain @click="$goBack()">
        <i class="el-icon-back"></i><span>返回</span>
      </el-button> &nbsp;
      <span><span>实例编码:</span><el-tag size="mini" align="center">{{ instanceCode }}</el-tag></span>&nbsp;&nbsp;
      <span><span>元对象:</span><el-tag size="mini" align="center">{{ objectCode }}</el-tag></span>&nbsp;&nbsp;
      <span><span>组件编码:</span><el-tag size="mini" align="center">{{ componentCode }}</el-tag></span>&nbsp;

      <div style="display: flex; align-items: center;">
        <span style="white-space: nowrap">实例名:</span>
        <text-box v-model="instanceName" size="mini"></text-box>
      </div>

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

<!--        <el-dropdown>-->
<!--          <el-button @click="preview" icon="el-icon-view" size="mini" type="primary">页面</el-button>-->
<!--          <el-dropdown-menu>-->
<!--            <el-dropdown-item @click.native="preview('ADD')">新增模式</el-dropdown-item>-->
<!--            <el-dropdown-item @click.native="preview('UPDATE')">更新模式</el-dropdown-item>-->
<!--            <el-dropdown-item @click.native="preview('VIEW')">查看模式</el-dropdown-item>-->
<!--          </el-dropdown-menu>-->
<!--        </el-dropdown>-->
<!--        <el-button @click="jsonView" icon="el-icon-view" size="mini" type="warning">JSON</el-button>-->

      </el-button-group>
      <full-screen :target="$refs['formBuilder']" id="form-builder"></full-screen>
    </div>
    <div id="form-builder-main">
      <ComponentList :form-meta="meta" :edit-mode="true" style="width: 200px; overflow: auto;"></ComponentList>
      <div style="flex: 5;margin:0 5px">
        <WorkArea v-model="meta" :active-item.sync="activeItem"></WorkArea>
      </div>
      <div style="width: 300px;">
        <ConfArea v-model="meta" :active-item="activeItem" :object-code="objectCode" :field-code="activeItem.name"></ConfArea>
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
import DefaultFormViewMeta from '@/../package/view/formview/ui-conf'
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
    ic: {
      type: String,
      required: true
    }
  },
  data() {
    const {ic: instanceCode} = this
    return {
      metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
      meta: this.$merge({}, DefaultFormViewMeta), // FormView渲染的元数据
      activeItem: {},
      instanceCode: instanceCode,
      instanceName: null,
      objectCode: null,
      componentCode: null,
      formType: null, // 表单模式(ADD/UPDATE/VIEW)
    }
  },
  methods: {
    setInitState() {
      this.meta = this.$merge({}, DefaultFormViewMeta);
      this.activeItem = {}
    },
    instanceConfLoad(instanceCode) {
      this.setInitState();
      let url = this.$compile(restUrl.COMP_INSTANCE_CONF_LOAD_EDIT, {instanceCode: instanceCode})

      this.$axios.safeGet(url).then(resp => {
        let {data} = resp;
        let {instanceName, fieldsMap, objectCode, componentCode} = data;
        this.instanceName = instanceName
        this.objectCode = objectCode
        this.componentCode = componentCode

        // 提取元对象在FormView下的UI配置
        this.$reverseMerge(this.meta, extractConfig.call(this, data, objectCode));

        // 提取元字段在FormView下的UI配置
        Object.keys(fieldsMap).forEach(key => this.meta.columns.push(extractConfig.call(this, fieldsMap, key)));
        gridInfoStructured(this.meta) // 结构化meta(建立递归的布局支持)

        // TODO 加载不同表单模式下的配置，然后依据前者将 默认的缺省配置meta中的 禁用和隐藏的字段移除掉。
        // let promise
        // switch (this.formType) {
        //   case 'ADD':
        //     promise = this.$axios.safeGet(this.$compile(restUrl.RECORD_TO_ADD, {instanceCode: instanceCode}))
        //     break;
        //   case 'UPDATE':
        //     promise = this.$axios.safeGet(this.$compile(restUrl.RECORD_TO_UPDATE, {instanceCode: instanceCode, primaryKv: null}))
        //     break;
        //   case 'VIEW':
        //     promise = this.$axios.safeGet(this.$compile(restUrl.RECORD_TO_VIEW, {instanceCode: instanceCode, primaryKv: null}))
        //     break;
        //   default:
        //     promise = new Promise((resolve, reject) => resolve({}))
        // }


      }).catch(({msg}) => {
        console.error('[ERROR] url: %s, msg: %s', url, msg);
        this.setInitState();
      })
    },

    jsonView() {
      const jsonBoxMeta = this.$reverseMerge(DefaultJsonBoxMeta, {
        conf: {mode: 'form'}
      })

      this.$dialog(jsonBoxMeta, this.meta, {
        title: "Json预览"
      })
    },
    preview(formType) {
      const {meta: {width}} = this
      this.$dialog(this.meta, null, {
        title: "视图预览",
        width: width
      })

      // const {meta: {width}, instanceCode} = this
      // let promise
      // switch (formType) {
      //   case 'ADD':
      //     promise = this.$axios.safeGet(this.$compile(restUrl.RECORD_TO_ADD, {instanceCode: instanceCode}))
      //     break;
      //   case 'UPDATE':
      //     promise = this.$axios.safeGet(this.$compile(restUrl.RECORD_TO_UPDATE, {instanceCode: instanceCode, primaryKv: null}))
      //     break;
      //   case 'VIEW':
      //     promise = this.$axios.safeGet(this.$compile(restUrl.RECORD_TO_VIEW, {instanceCode: instanceCode, primaryKv: null}))
      //     break;
      //   default:
      //     promise = new Promise((resolve, reject) => resolve({}))
      // }
      //
      // const copyMeta = utils.deepClone(this.meta)
      // const recursiveRemove = function (meta, keepColumnCodes) {
      //   const {columns = []} = meta
      //   if (utils.isArray(columns) && columns.length > 0) {
      //     let i = columns.length
      //     while (i--) {
      //       const {component_name, name} = columns[i]
      //       if (component_name != 'RowGrid' && keepColumnCodes.indexOf(name) == -1) {
      //         columns.splice(i, 1)
      //       }
      //     }
      //   }
      // }
      //
      // promise.then(({data = {}}) => {
      //   const {columns} = data
      //   if (!utils.isEmpty(columns)) {
      //     const keepColumnCodes = columns.map(c => c.name) // 移除隐藏、禁用的
      //     recursiveRemove(copyMeta, keepColumnCodes)
      //     debugger
      //   }
      //
      //   this.$dialog(copyMeta, null, {
      //     title: "视图预览",
      //     width: width
      //   })
      // })
    },
    submitForm() {
      const {meta: {columns}, instanceCode, instanceName, objectCode} = this
      if (isEmpty(objectCode)) {
        this.$message.warning('元对象编码不能为空!')
        return
      }
      if (isEmpty(columns)) {
        this.$message.warning('无字段配置内容!')
        return
      }

      this.$confirm('确认提交?').then(() => {
        let objectConf = utils.deepClone(this.meta);
        let fieldsMap = {};

        if (objectConf.hasOwnProperty('columns')) {
          gridInfoFattened(objectConf) // TODO 2.4 由于目前UIConf的接口要求必须拍平字段的配置参数, 所以栅格设置数据先丢失，后面根据更新后的接口调整此处的逻辑

          objectConf.columns.forEach(c => {
            fieldsMap[c.name] = c
          })
          delete objectConf['columns']; // 平铺, 接口不接受层级结构
        }

        let params = {
          instanceCode: instanceCode,
          instanceName: instanceName,
          fieldsMap: fieldsMap
        };

        params[objectCode] = objectConf;

        this.$axios.post(restUrl.COMP_CONF_UPDATE, params).then(({msg = '配置保存成功'}) => {
          this.$message.success(msg);
          this.$goBack()
        })
      })
    }
  },
  mounted() {
    this.instanceConfLoad(this.instanceCode) // 加载实例配置
  }
}
</script>

<style scoped lang="scss">
#form-builder {
  display: flex;
  flex-direction: column;
  padding: 5px;
  height: 100%;
  box-sizing: border-box;
  background-color: white;

  .opr-box {
    display: flex;
    margin: 2px;
    align-items: center;
  }

  #form-builder-main {
    display: flex;
    height: calc(100% - 32px);
    box-sizing: border-box;
  }
}
</style>
