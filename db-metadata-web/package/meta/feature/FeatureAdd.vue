<template>
  <div class="container">
    <el-steps :active="step" finish-status="success" class="step">
      <el-step title="功能配置"></el-step>
      <el-step title="创建路由"></el-step>
      <el-step title="关联菜单"></el-step>
    </el-steps>

    <!-- 功能配置 -->
    <form-view ref="featureForm" ic="meta_feature.FormView" type="add" :model="feature"
               @submit="submitFeatureForm('featureForm')" @cancel="cancel" v-show="step == 0">
      <template #form-item-type="{column, model}">
        <el-form-item :label="column.label">
          <drop-down-box v-model="model.type" :meta="column" @change="featureTypeChange(model.type)"></drop-down-box>
        </el-form-item>
      </template>

      <template #form-item-config="{column, model}">
        <el-form-item :label="column.label">
          <div>
            <el-card v-if="model.type">
              <component ref="extendConf" :is="model.type" v-model="model.config"></component>
            </el-card>
            <span v-else style="color: #E6A23C">请先选择功能类型!</span>
          </div>
        </el-form-item>
      </template>

      <template #action="{submit, cancel}">
        <el-form-item>
          <el-button @click="submit" type="primary">提交</el-button>
          <el-button @click="cancel">取消</el-button>
          &nbsp;
          <el-checkbox v-model="keepGo">保存后继续下一步</el-checkbox>
        </el-form-item>
      </template>
    </form-view>

    <!-- 路由配置 -->
    <form-view ic="meta_router.FormView" type="add" @ok="next" @cancel="cancel" v-show="step == 1">
      <template #action="{submit, cancel}">
        <el-form-item>
          <el-button type="primary" @click="submit">提交</el-button>
          <el-button @click="finish">关闭</el-button>
          &nbsp;
          <el-checkbox v-model="keepGo">保存后继续下一步</el-checkbox>
        </el-form-item>
      </template>
    </form-view>

    <!-- 菜单配置 -->
    <form-view ic="meta_menu.FormView" type="add" @ok="finish" @cancel="cancel" v-show="step == 2">
      <template #action="{submit, cancel}">
        <el-form-item>
          <el-button @click="submit">提交</el-button>
          <el-button @click="finish">关闭</el-button>
          &nbsp;
          <el-checkbox v-model="keepGo">保存后继续下一步</el-checkbox>
        </el-form-item>
      </template>
    </form-view>
  </div>
</template>

<script>
import {restUrl} from "../../constant/url";
import utils from '../../utils'
import {FEATURE_TYPE} from "./ext/featureType";
import {isEmpty} from "../../utils/common";
import AutoComputedButton from "./ext/AutoComputedButton";

import SingleGrid from './ext/SingleGrid'
import TreeSingleGrid from "./ext/TreeSingleGrid";
import MasterSlaveGrid from "./ext/MasterSlaveGrid";
import TreeTable from "./ext/TreeTable";

export default {
  name: "FeatureAdd",
  components: {
    AutoComputedButton,
    SingleGrid,
    TreeSingleGrid,
    MasterSlaveGrid,
    TreeTable
  },
  data() {
    return {
      feature: {
        type: null,
        name: null,
        code: null,
        bizInterceptor: '',
        config: {},
      },
      keepGo: true,

      baseRoutePath: '',
      step: 0,

      featureTypeUrl: restUrl.LIST_FEATURE_TYPE,

      instanceCodeUrl: utils.resolvePath(restUrl.INSTANCE_CODE_LIST, {
        type: 'META_OBJECT'
      }),

    }
  },
  methods: {
    featureTypeChange(featureType) {
      const {value} = FEATURE_TYPE[featureType]
      this.$refs['featureForm'].setItem("config", this.$merge({}, value))
    },
    pathChange(model) {
      const {menu: {objectCode}, feature: {code: fc}, baseRoutePath} = this
      let {path: value} = model
      if (!isEmpty(baseRoutePath) && !value.startsWith("/")) {
        value = (baseRoutePath.endsWith("/") ? baseRoutePath : baseRoutePath + '/') + value
      }
      this.$refs[objectCode].setItem('path', value + '?fc=' + fc)
    },
    submitFeatureForm(formName) {
      const {feature: {type: featureType}, feature} = this
      const _this = this
      if (!_this.$refs.hasOwnProperty('extendConf')) {
        _this.$message.error('请选择功能类型并配置')
        return
      }
      _this.$refs['extendConf'].validate(valid => {
        if (valid) {
          let url = _this.$compile(restUrl.FEATURE_ADD, {
            featureType: featureType
          });
          _this.$axios.post(url, feature).then(({msg = '操作成功'}) => {
            _this.$message.success(msg);
            _this.next()
          }).catch(({msg = '操作失败'}) => {
            console.error(msg)
          })
        } else {
          return false
        }
      })
    },
    next() {
      if(!this.keepGo) {
        this.finish()
        return;
      }
      if (this.step++ > 2) this.step = 0;
    },
    finish() {
      this.$emit('ok', this.params);
    },
    cancel: function (event) {
      this.$emit('cancel', event);
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;

  .step {
    margin: 20px;
  }
}
</style>
