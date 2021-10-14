<template>
  <div class="container el-card">
    <el-form :model="confModel" label-width="80px" class="demo-form-inline" size="mini" style="height: 100%">
      <div class="opr-box">
        <div>
          <el-button size="mini" type="primary" plain @click="$goBack()">
            <i class="el-icon-back"></i><span>返回</span>
          </el-button>
          <el-form-item label="组件" class="inline">
            <component-selector v-model="confModel.componentCode" @change="loadConf"></component-selector>
          </el-form-item>
        </div>
        <el-form-item>
          <el-button-group>
            <el-button type="primary" @click="preview">
              <i class="el-icon-view"></i>
              <span>预览</span>
            </el-button>
            <el-button type="success" @click="submit">
              <i class="el-icon-upload"></i>
              <span>保存</span>
            </el-button>
          </el-button-group>
        </el-form-item>
      </div>
      <template v-if="confModel.componentCode">
        <el-row class="conf-box">
          <el-col>
            <h2 align="center">{{ confModel.componentCode }}</h2>
            <el-form-item>
              <mini-form-box v-model="confModel.conf" class="shadow"
                             :meta="confMeta" :show-change-type="true"
                             @json-change="() => buildObjectConfMeta(confModel.conf)">
                <template #button-expand="{value}">
                  <el-popover placement="right" trigger="click"
                              popper-class="ui-conf-tip-popper">
                    <ui-conf-tip
                        :component-name="confModel.conf['component_name']"></ui-conf-tip>
                    <el-button slot="reference" size="mini" icon="el-icon-question"
                               circle></el-button>
                  </el-popover>
                </template>
              </mini-form-box>
            </el-form-item>
          </el-col>
        </el-row>
      </template>
      <template v-else>
        <div class="blank-tip">请先选择一个组件</div>
      </template>
    </el-form>
  </div>
</template>

<script>
import utils from '../../utils'
import {restUrl} from "../../constant/url";
import EleProps from '../../constant/element-props'
import DefaultJsonBoxMeta from '../../core/jsonbox/ui-conf'
import {TagViewUtil} from "../../index";
import buildMeta from "../buildMeta";
import UiConfTip from "../component/UiConfTip";
import ComponentSelector from "../component/ComponentSelector";

export default {
  name: "GlobalConf",
  components: {UiConfTip, ComponentSelector},
  data() {
    let confMeta = {
      name: "conf",
      label: "配置",
      conf: {
        mode: 'code',
      }
    };
    this.$merge(confMeta, DefaultJsonBoxMeta);
    return {
      confMeta: confMeta,
      confModel: {
        componentCode: this.$route.query.componentCode,
        conf: {}, // conf of component
      }
    }
  },
  methods: {
    buildObjectConfMeta(value) {
      this.confMeta = buildMeta(value);
    },
    loadConf: function () {
      const {componentCode} = this.confModel;
      if (!componentCode) {
        this.confModel['conf'] = {};
        return;
      }

      const url = this.$compile(restUrl.COMP_GOBAL_CONF_LOAD, {
        componentCode: componentCode
      });

      this.$axios.get(url).then(resp => {
        let data = resp.data;

        for (let key in data) {
          let confVal = data[key].replace(/\\/g, "");
          let confValJson = JSON.parse(confVal);
          confValJson['conf'] = confValJson['conf'] || {};
          this.$merge(confValJson['conf'], EleProps(confValJson['component_name']));
          this.confModel['conf'] = confValJson;
          break;
        }
      }).catch(({msg = '配置加载失败'}) => {
        console.error('[ERROR] url: %s, msg: %s', url, msg);
      })
    },
    submit: function () {
      const {confModel: {componentCode, conf} = {}} = this
      if (!componentCode) {
        this.$message.warning('必须选定一个组件');
        return;
      }

      let params = {
        componentCode: componentCode
      }
      params[componentCode] = conf;

      this.$confirm('确认提交?').then(() => {
        this.$axios({
          method: 'POST',
          url: restUrl.COMP_CONF_UPDATE,
          data: params
        }).then(({msg = '配置更新成功'}) => {
          this.$message.success(msg);
          this.$goBack()
        })
      })
    },
    preview: function () {
      let data = this.confModel['conf'].hasOwnProperty('default_value') ? this.confModel['conf']['default_value'] : null;
      this.$dialog(this.confModel['conf'], data, {
        title: '预览'
      })
    }
  },
  mounted() {
    const {componentCode} = this.confModel;
    if (!utils.isEmpty(componentCode)) {
      this.loadConf();
    }
  }
}
</script>

<style scoped lang="scss">
.container {
  $oprBarHeight: 50px;

  .opr-box {
    height: $oprBarHeight;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    font-size: 13px;
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
</style>
