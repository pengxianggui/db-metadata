<template>
  <div class="page-container">
    <el-form id="form-box" size="mini" ref="InstanceConf" :rules="rules" :model="confModel" label-width="80px">
      <div id="opr-box">
        <div style="font-size: 13px;">
          <el-button size="mini" type="primary" plain @click="$goBack()">
            <i class="el-icon-back"></i><span>返回</span>
          </el-button>
          &nbsp;
          <template v-if="EDIT_MODE">
            <span><span>实例编码:</span><el-tag size="mini" align="center">{{ confModel.instanceCode }}</el-tag></span>&nbsp;
            <span><span>元对象:</span><el-tag size="mini" align="center">{{ confModel.objectCode }}</el-tag></span>&nbsp;
            <span><span>模板:</span><el-tag size="mini" align="center">{{ confModel.componentCode }}</el-tag></span>&nbsp;

            <el-badge value="hot" type="danger" v-if="confModel.componentCode === 'FormView'">
              <el-tag type="danger" size="mini" style="cursor: pointer"
                      @click="toFormBuilder(confModel.instanceCode, confModel.objectCode, confModel.componentCode)">
                表单设计!
              </el-tag>
            </el-badge>
          </template>
          <template v-else>
            <el-form-item label="元对象" prop="objectCode" class="inline" style="margin: 0">
              <meta-object-selector v-model="confModel.objectCode" :disabled="EDIT_MODE"></meta-object-selector>
            </el-form-item>
            <el-form-item label="组件" prop="componentCode" class="inline" style="margin: 0">
              <component-selector v-model="confModel.componentCode" scope="view"
                                  :disabled="EDIT_MODE"></component-selector>
            </el-form-item>
            <span v-if="isAutoComputed" style="color: red;font-size: 12px;margin-left: 10px">后台自动计算</span>
            <el-form-item class="inline" style="margin: 0">
              <el-button type="primary" @click="autoComputedLoad" icon="el-icon-download">导入自动计算</el-button>
            </el-form-item>
          </template>
        </div>
        <el-button-group>
          <ui-config-help></ui-config-help>
          <el-button size="mini" type="primary" @click="preview">
            <i class="el-icon-view"></i>
            <span>预览</span>
          </el-button>
          <!-- TODO 判断是否有未提交内容, 进行保存提示 -->
          <el-badge :is-dot="false">
            <el-dropdown size="mini" type="success" split-button @click="submit" trigger="click">
              <i class="el-icon-upload"></i>
              <span>保存</span>
              <el-dropdown-menu slot="dropdown" trigger="click">
                <el-dropdown-item size="mini" @click.native="rollback" class="hover-warning-plain" v-if="EDIT_MODE">
                  <i class="el-icon-refresh"></i>
                  <span>配置回滚</span>
                </el-dropdown-item>
                <el-dropdown-item size="mini" @click.native="deleteConf" class="hover-danger-plain" v-if="EDIT_MODE">
                  <i class="el-icon-delete-solid"></i>
                  <span>删除配置</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-badge>
        </el-button-group>

      </div>
      <el-tabs id="tab-box-instance-conf-edit" type="border-card" v-model="elTabValue"
               :class="{'show-form-builder': elTabValue === '2'}">
        <el-tab-pane label="元对象配置" name="0">
          <el-form-item label="实例描述">
            <text-area-box v-model="confModel.instanceName"></text-area-box>
          </el-form-item>
          <el-form-item label="元对象配置">
            <mini-form-box v-model="confModel.conf" class="shadow" :meta="objConfMeta" :show-change-type="true"
                           @json-change="() => buildObjectConfMeta(confModel.conf)">
              <template #button-expand="{value}">
                <el-popover placement="right" trigger="click" popper-class="ui-conf-tip-popper">
                  <ui-conf-tip :component-name="confModel.conf['component_name']"></ui-conf-tip>
                  <el-button slot="reference" size="mini" icon="el-icon-question" circle></el-button>
                </el-popover>
              </template>
            </mini-form-box>
          </el-form-item>
        </el-tab-pane>
        <el-tab-pane label="元字段配置" name="1">
          <div id="conf-panel">
            <div id="conf-content">
              <field-filter v-model="filterFields" :fields="Object.keys(confModel.fConf)"></field-filter>
              <div class="field-conf-parent">
                <div class="field-conf" v-for="(key, index) in filteredFields" :key="key">
                  <el-form-item label-width="0">
                    <h1 :name="key">{{ index + 1 }}.{{ key }}</h1>
                    <el-card shadow>
                      <ui-conf-editor v-model="confModel.fConf[key]"
                                      :object-code="objectCode" :field-code="key"></ui-conf-editor>
                    </el-card>
                  </el-form-item>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-form>
  </div>
</template>

<script>
import utils from '../../utils'
import {restUrl} from "../../constant/url";
import UiConfTip from '../component/UiConfTip'
import extractConfig from './extractConfig'
import buildMeta from '../buildMeta'
import FieldFilter from "../component/FieldFilter";
import MetaObjectSelector from "../component/MetaObjectSelector";
import ComponentSelector from "../component/ComponentSelector";
import MetaFieldConfigButton from "../component/MetaFieldConfigButton";
import UiConfEditor from "../component/UiConfEditor";

export default {
  name: "InstanceConfEdit",
  components: {
    UiConfTip,
    FieldFilter,
    MetaObjectSelector,
    ComponentSelector,
    MetaFieldConfigButton,
    UiConfEditor
  },
  props: {
    instanceCode: String,
    objectCode: String,
    componentCode: String,
    fieldCode: String
  },
  data() {
    const {instanceCode, componentCode, objectCode} = this;

    return {
      elTabValue: '0',
      EDIT_MODE: true,
      filterFields: [],
      isAutoComputed: false,
      objConfMeta: {}, // 构建元对象配置迷你表单的元数据
      fieldsConfMeta: {}, // 构建元字段配置迷你表单的元数据
      confModel: {
        instanceCode: instanceCode,
        instanceName: null,
        componentCode: componentCode,
        objectCode: objectCode,
        conf: {}, // 元对象的配置数据
        fConf: {} // 元字段的配置数据
      },
      rules: {
        instanceCode: [{required: true, message: ' ', trigger: 'blur'}],
        componentCode: [{required: true, message: ' ', trigger: 'blur'}],
        objectCode: [{required: true, message: ' ', trigger: 'blur'}]
      }
    }
  },
  created() {
    const {instanceCode, objectCode, componentCode, fieldCode} = this;
    if (utils.isEmpty(instanceCode)) {
      this.EDIT_MODE = false
    } else {
      this.EDIT_MODE = true
      let url = this.$compile(restUrl.COMP_INSTANCE_CONF_LOAD_EDIT, {
        instanceCode: instanceCode,
        objectCode: objectCode,
        componentCode: componentCode
      });
      this.loadConf(url)
    }

    if (fieldCode) {
      this.filterFields.push(fieldCode)
      this.elTabValue = '1'
    }
  },
  methods: {
    resetConf() {
      this.confModel['conf'] = {};
      this.confModel['fConf'] = {};
    },
    rollback() {
      // TODO 配置回滚
      this.$message.warning("TODO 待完成")
    },
    handleOcChange(objectCode) {
      this.confModel['objectCode'] = objectCode;
    },
    autoComputedLoad() {
      this.$refs['InstanceConf'].validate((valid) => {
        if (valid) {
          const {confModel: {objectCode, componentCode}} = this
          const url = this.$compile(restUrl.COMP_INSTANCE_CONF_LOAD_NEW, {
            objectCode: objectCode,
            componentCode: componentCode
          });
          this.loadConf(url)
        } else {
          this.$message.warning('请填写必填项')
        }
      })
    },
    loadConf(url) {
      this.resetConf();
      const {$axios, confModel: {objectCode}} = this;

      $axios.safeGet(url).then(resp => {
        let {data} = resp;
        let {isAutoComputed = false, instanceCode, instanceName, fieldsMap} = data;
        this.isAutoComputed = isAutoComputed;
        this.confModel['instanceCode'] = instanceCode;
        this.confModel['instanceName'] = instanceName;
        // extract object config
        this.confModel['conf'] = extractConfig.call(this, data, objectCode);

        // extract field config
        Object.keys(fieldsMap).forEach(key =>
            this.$set(this.confModel['fConf'], key, extractConfig.call(this, fieldsMap, key)));
        // build object conf meta
        this.buildObjectConfMeta(this.confModel['conf']);
        // build fields conf meta
        Object.keys(this.confModel['fConf']).forEach(key => {
          this.buildFieldConfMeta(this.confModel['fConf'][key], key);
        });
      }).catch(({msg = '配置加载成功'}) => {
        console.error('[ERROR] url: %s, msg: %s', url, msg);
      })
    },
    deleteConf: function () {
      this.$confirm('确定删除当前配置? 此操作无法撤销!', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = this.$compile(restUrl.COMP_INSTANCE_CONF_DELETE, this.confModel);
        this.$axios.delete(url).then(({msg = '配置删除成功'}) => {
          this.$message.success(msg);
        }).catch(({msg = '配置删除失败'}) => {
          console.error(msg)
        })
      })
    },
    submit() {
      this.$refs['InstanceConf'].validate((valid) => {
        if (valid) {
          const {
            confModel: {
              instanceCode,
              instanceName,
              componentCode,
              objectCode,
              conf: objectConf,
              fConf: fieldsConf
            }, EDIT_MODE
          } = this
          let $confirm = EDIT_MODE ?
              this.$confirm('确认提交?')
              : this.$prompt(`请为这套配置设定一个唯一编码(instanceCode), 如不输入, 则默认为:${instanceCode}`, {})

          $confirm.then(({value}) => {
            let params = {
              instanceCode: utils.assertEmpty(value, instanceCode),
              instanceName: instanceName,
              componentCode: componentCode,
              objectCode: objectCode,
              fieldsMap: fieldsConf
            };
            params[objectCode] = objectConf;

            this.$axios({
              method: 'POST',
              url: EDIT_MODE ? restUrl.COMP_CONF_UPDATE : restUrl.COMP_CONF_ADD,
              data: params
            }).then(({msg = '配置保存成功'}) => {
              this.$message.success(msg);
            }).catch(({msg = '配置保存失败'}) => {
              console.error(msg)
            })
          })
        }
      });
    },
    preview: function () {
      const {confModel: {conf, fConf}} = this;
      let meta = utils.deepClone(conf);
      meta.columns = [];
      for (let key in fConf) {
        let item = fConf[key];
        meta.columns.push(item);
      }
      this.$dialog(meta, null, {title: '预览', width: meta.width})
    },
    buildObjectConfMeta(value) {
      this.objConfMeta = buildMeta(value);
    },
    buildFieldConfMeta(value, key) {
      this.fieldsConfMeta[key] = buildMeta(value, key);
    },
    toFormBuilder(instanceCode, objectCode, componentCode) {
      this.$router.push({path: '/meta/form-builder', query: {ic: instanceCode, oc: objectCode, cc: componentCode}})
    }
  },
  computed: {
    filteredFields: function () {
      const {confModel: {fConf}, filterFields} = this;
      if (fConf && filterFields.length > 0) {
        return (Object.keys(fConf)).filter(f => filterFields.indexOf(f) > -1)
      }
      return Object.keys(fConf)
    }
  }
}
</script>
<style lang="scss">
// 实现el-tab内部滚动条
#form-box {
  #tab-box-instance-conf-edit .el-tabs__content {
    overflow: auto;
    height: calc(100% - 70px);
    box-sizing: border-box;
  }

  //#tab-box-instance-conf-edit.show-form-builder .el-tabs__content {
  //  padding: 5px;
  //}

  //#tab-box-instance-conf-edit.show-form-builder #pane-2 {
  //  height: 100%;
  //}
}
</style>
<style lang="scss" scoped>
#form-box {
  position: relative;
  height: 100%;

  #opr-box {
    display: flex;
    margin-bottom: 5px;
    align-items: center;
    justify-content: space-between;
  }

  #tab-box-instance-conf-edit {
    height: 100%;
  }
}

.blank-tip {
  height: 400px;
  line-height: 400px;
  text-align: center;
  border: 1px solid #eee;
  margin: 5px 0;
  color: #999999;
}

.shadow {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
}

.field-conf-parent {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 20px;
}

#conf-panel {
  display: flex;

  & ul {
    padding: 0;
    list-style: none;
  }

  #conf-content {
    flex: 1;
    overflow: auto;
  }
}
</style>
<style>
.ui-conf-tip-popper {
  box-shadow: 4px 12px 26px 0 rgba(0, 0, 0, 0.2)
}
</style>
