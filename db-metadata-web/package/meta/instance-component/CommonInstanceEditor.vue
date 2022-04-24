<template>
  <div id="instance-conf-container" ref="instance-conf-container" class="page-container">
    <el-form id="form-box" size="mini" ref="InstanceConf" :rules="rules" :model="confModel" label-width="80px">
      <div id="opr-box">
        <el-button size="mini" type="primary" plain @click="$goBack()">
          <i class="el-icon-back"></i><span>返回</span>
        </el-button>
        &nbsp;
        <span><span>实例编码:</span><el-tag size="mini" align="center">{{ confModel.instanceCode }}</el-tag></span>&nbsp;
        <span><span>元对象:</span><el-tag size="mini" align="center">{{ confModel.objectCode }}</el-tag></span>&nbsp;
        <span><span>模板:</span><el-tag size="mini" align="center">{{ confModel.componentCode }}</el-tag></span>&nbsp;
        <div style="display: flex; align-items: center;">
          <span style="white-space: nowrap">实例名:</span>
          <text-box v-model="confModel.instanceName" size="mini"></text-box>
        </div>

        <span style="flex: 1"></span>
        <el-button-group>
          <full-screen :target="$refs['instance-conf-container']" id="instance-conf-container"></full-screen>
          <el-button size="mini" icon="el-icon-view" type="primary" @click="preview"></el-button>
          <el-button size="mini" icon="el-icon-view" type="warning" @click="jsonView"></el-button>
          <!-- TODO 判断是否有未提交内容, 进行保存提示 -->
          <el-badge :is-dot="false">
            <el-dropdown size="mini" type="success" split-button @click="submit" trigger="click">
              <i class="el-icon-upload"></i>
              <span>保存</span>
              <el-dropdown-menu slot="dropdown" trigger="click">
                <el-dropdown-item size="mini" @click.native="rollback" class="hover-warning-plain">
                  <i class="el-icon-refresh"></i>
                  <span>配置回滚</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-badge>
        </el-button-group>

      </div>
      <el-tabs id="tab-box-instance-conf-edit" type="border-card" v-model="elTabValue"
               :class="{'show-form-builder': elTabValue === '2'}">
        <el-tab-pane label="容器配置" name="0">
          <ui-conf-editor v-model="confModel.conf" :object-code="confModel.objectCode"
                          :view-component-code="cc"></ui-conf-editor>
        </el-tab-pane>
        <el-tab-pane label="域配置" name="1">
          <div id="conf-panel">
            <div id="conf-content">
              <field-filter v-model="filterFields" :fields="Object.keys(confModel.fConf)"></field-filter>
              <div class="field-conf-parent">
                <div class="field-conf" v-for="(key, index) in filteredFields" :key="key">
                  <el-form-item label-width="0">
                    <h1 :name="key">{{ index + 1 }}.{{ key }}</h1>
                    <el-card shadow>
                      <ui-conf-editor v-model="confModel.fConf[key]"
                                      :object-code="confModel.objectCode" :field-code="key"
                                      :view-component-code="cc"></ui-conf-editor>
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
import extractConfig from './extractConfig'
import buildMeta from "../buildMeta";
import FieldFilter from "../component/FieldFilter";
import MetaObjectSelector from "../component/MetaObjectSelector";
import ComponentSelector from "../component/ComponentSelector";
import MetaFieldConfigButton from "../component/MetaFieldConfigButton";
import UiConfEditor from "../component/UiConfEditor";
import {isEmpty} from "../../utils/common";
import DefaultJsonBoxMeta from "../../core/jsonbox/ui-conf";

export default {
  name: "CommonInstanceEditor",
  components: {
    FieldFilter,
    MetaObjectSelector,
    ComponentSelector,
    MetaFieldConfigButton,
    UiConfEditor
  },
  props: {
    ic: String, // instanceCode
    oc: String, // objectCode
    fc: String, // fieldCode
    cc: String, // componentCode
  },
  data() {
    const {ic: instanceCode} = this;

    return {
      elTabValue: '0',
      filterFields: [],
      objConfMeta: {}, // 构建元对象配置迷你表单的元数据
      fieldsConfMeta: {}, // 构建元字段配置迷你表单的元数据

      confModel: {
        instanceCode: instanceCode,
        instanceName: null,
        componentCode: null,
        objectCode: null,
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
    this.loadConf(this.ic)

    if (!isEmpty(this.fc)) {
      this.filterFields.push(this.fc)
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

    loadConf(instanceCode) {
      this.resetConf();

      let url = this.$compile(restUrl.COMP_INSTANCE_CONF_LOAD_EDIT, {
        instanceCode: instanceCode
      });

      this.$axios.safeGet(url).then(resp => {
        let {data} = resp;
        let {instanceName, objectCode, componentCode, fieldsMap} = data;
        this.confModel.instanceName = instanceName;
        this.confModel.objectCode = objectCode;
        this.confModel.componentCode = componentCode;

        // extract object config
        this.confModel.conf = extractConfig.call(this, data, objectCode);

        // extract field config
        Object.keys(fieldsMap).forEach(key =>
            this.$set(this.confModel.fConf, key, extractConfig.call(this, fieldsMap, key)));

        // build object conf meta
        this.buildObjectConfMeta(this.confModel.conf);

        // build fields conf meta
        Object.keys(this.confModel.fConf).forEach(key => {
          this.buildFieldConfMeta(this.confModel.fConf[key], key);
        });
      }).catch(({msg}) => {
        console.error('[ERROR] url: %s, msg: %s', url, msg);
      })
    },

    submit() {
      this.$confirm('确认提交?').then(() => {
        this.$refs['InstanceConf'].validate((valid) => {
          if (valid) {
            const {instanceCode, instanceName, objectCode, conf: objectConf, fConf: fieldsConf} = this.confModel

            let params = {
              instanceCode: instanceCode,
              instanceName: instanceName,
              fieldsMap: fieldsConf
            };
            params[objectCode] = objectConf;

            this.$axios({
              method: 'POST',
              url: restUrl.COMP_CONF_UPDATE,
              data: params
            }).then(({msg = '配置保存成功'}) => {
              this.$message.success(msg);
            }).catch(({msg = '配置保存失败'}) => {
              console.error(msg)
            })
          }
        });
      })
    },

    jsonView() {
      const {confModel: {conf, fConf}} = this;
      let meta = utils.deepClone(conf);
      meta.columns = [];
      for (let key in fConf) {
        let item = fConf[key];
        meta.columns.push(item);
      }

      this.$dialog(DefaultJsonBoxMeta, meta, {
        title: "Json预览"
      })
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
      this.objConfMeta = buildMeta(value, null, this.cc);
    },
    buildFieldConfMeta(value, key) {
      this.fieldsConfMeta[key] = buildMeta(value, key, this.cc);
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
.page-container {
  background-color: #f7f7f7;

  #form-box {
    position: relative;
    height: 100%;

    #opr-box {
      display: flex;
      margin-bottom: 5px;
      align-items: center;
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
}
</style>
