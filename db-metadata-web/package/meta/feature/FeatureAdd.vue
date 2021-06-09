<template>
  <div class="container">
    <el-steps :active="step" finish-status="success" class="step">
      <el-step title="功能配置"></el-step>
      <el-step title="创建路由"></el-step>
      <el-step title="关联菜单"></el-step>
    </el-steps>
    <el-form ref="featureForm" :model="feature" label-width="120px" v-show="step == 0">
      <el-form-item label="功能类别" prop="type" required>
        <radio-box :data-url="featureTypeUrl" v-model="feature.type" @active-option="initFeatureConfMeta"
                   @change="initFeatureConfMeta"></radio-box>
      </el-form-item>
      <el-form-item label="功能名" class="inline" prop="name" required>
        <text-box v-model="feature.name"></text-box>
      </el-form-item>
      <el-form-item label="功能代码" class="inline" prop="code" required>
        <text-box v-model="feature.code"></text-box>
      </el-form-item>
      <!--        TODO 配置的instanceCode暂未应用，先屏蔽-->
      <!--        <el-form-item label="instanceCode" class="inline" prop="instanceCode" required>-->
      <!--          <drop-down-box v-model="feature.instanceCode" :data-url="instanceCodeUrl" filterable></drop-down-box>-->
      <!--        </el-form-item>-->
      <el-form-item label="业务拦截器">
        <el-input placeholder="配置业务拦截器 完整的包名,多个拦截器使用逗号分割 例如: com.hthjsj.web.controller.itp.MetaFieldEditPointCut"
                  v-model="feature.config.bizInterceptor"></el-input>
      </el-form-item>

      <!--扩展配置-->
      <component ref="extendConf" :is="feature.type" :config.sync="feature.config"></component>

      <el-form-item>
        <el-button size="small" type="success" @click="submitFeatureForm('featureForm')">提交</el-button>
        <!--        <el-button size="small" type="primary" @click="next">下一步</el-button>-->
        <el-button size="small" type="danger" @click="close">关闭</el-button>
      </el-form-item>
    </el-form>

    <form-view :ref="route.objectCode" :meta="route.meta" @ok="next" v-show="step == 1">
      <template #form-item-pid="{column, model}">
        <el-form-item :name="column.name" :label="column.label">
          <drop-down-box :data-url="column.data_url" v-model="model[column.name]"
                         @change="baseRoutePathChange"></drop-down-box>
        </el-form-item>
      </template>
      <template #form-item-cn="{column, model}">
        <el-form-item :name="column.name" :label="column.label">
          <text-box v-model="model[column.name]"
                    @change="$refs[route.objectCode].setItem('meta', {'title': model[column.name]});
                      $refs[menu.objectCode].setItem('title', model[column.name]);"></text-box>
        </el-form-item>
      </template>
      <template #form-item-component="{column, model}">
        <el-form-item :name="column.name" :label="column.label">
          <drop-down-box v-if="!customComponent" :options="tmplOptions" v-model="model[column.name]"></drop-down-box>
          <text-box v-else v-model="model[column.name]"></text-box>&nbsp;
          <bool-box v-model="customComponent">是否自定义组件</bool-box>
        </el-form-item>
      </template>
      <template #form-item-path="{column, model}">
        <el-form-item :name="column.name" :label="column.label">
          <text-box v-model="model[column.name]" @change="pathChange(model)"></text-box>
        </el-form-item>
      </template>
      <template #action="{submit}">
        <el-button size="small" type="success" @click="submit">提交</el-button>
        <!--        <el-button size="small" type="primary" @click="next">下一步</el-button>-->
        <el-button size="small" type="danger" @click="close">关闭</el-button>
      </template>
    </form-view>

    <form-view :ref="menu.objectCode" :meta="menu.meta" @ok="close" v-show="step == 2">
      <template #action="{submit}">
        <el-button size="small" type="success" @click="submit">提交</el-button>
        <el-button size="small" type="danger" @click="close">关闭</el-button>
      </template>
    </form-view>
  </div>
</template>

<script>
import {restUrl} from "../../constant/url";
import MasterSlaveGrid from './conf-mini/MasterSlaveGrid'
import SingleGrid from './conf-mini/SingleGrid'
import TreeSingleGrid from './conf-mini/TreeSingleGrid'
import TreeAndSingleGrid from './conf-mini/TreeAndSingleGrid'
import utils from '../../utils'
import {getAddFormMeta} from '../../utils/rest'
import {isEmpty} from "../../utils/common";
import {FEATURE_TYPE_MAPPING} from './ext/featureType'

export default {
  name: "FeatureAdd",
  components: {
    'MasterSlaveGrid': MasterSlaveGrid,
    'SingleGrid': SingleGrid,
    'TreeInTable': TreeSingleGrid,
    'TreeAndTable': TreeAndSingleGrid
  },
  props: {
    params: {
      type: Object
    },
    meta: {
      type: Object,
      default: () => {
      }
    }
  },
  data() {
    const {params: {objectCode = null, primaryKey = null} = {}} = this
    return {
      baseRoutePath: '',
      step: 0,
      objectCode: objectCode,
      primaryKey: primaryKey,
      featureTypeUrl: restUrl.LIST_FEATURE_TYPE,
      instanceCodeUrl: utils.resolvePath(restUrl.INSTANCE_CODE_LIST, {
        type: 'META_OBJECT'
      }),
      feature: {
        type: 'SingleGrid',
        name: objectCode,
        code: objectCode,
        instanceCode: null,
        bizInterceptor: '',
        config: {},
      },
      route: {
        objectCode: 'meta_router',
        meta: {}
      },
      menu: {
        objectCode: 'meta_menu',
        meta: {}
      },
      customComponent: false,
      tmplOptions: [ // TODO 后端的功能编码应当与模板名保持一致，此处先静态配置options
        {key: '主子表', value: 'MasterSlaveTableTmpl'},
        {key: '单表', value: 'SingleGridTmpl'},
        {key: '树形表', value: 'TreeSingleGridTmpl'},
        {key: '树和表', value: 'TreeTableTmpl'},
      ]
    }
  },
  methods: {
    initFeatureConfMeta() {
      const {feature: {type: featureType}, objectCode, primaryKey} = this
      this.feature.config = utils.deepClone(FEATURE_TYPE_MAPPING[featureType].getInitConf(objectCode, primaryKey))
    },
    baseRoutePathChange({activeOption}) {
      const {key} = activeOption
      this.baseRoutePath = key
    },
    pathChange(model) {
      const {menu: {objectCode}, feature: {code: fc}, baseRoutePath} = this
      let {path: value} = model
      if (!isEmpty(baseRoutePath) && !value.startsWith("/")) {
        value = (baseRoutePath.endsWith("/") ? baseRoutePath : baseRoutePath + '/') + value
      }
      this.$refs[objectCode].setItem('path', value + '?fc=' + fc)
    },
    initAssociateMeta() {
      const {route: {objectCode: routeObjectCode}, menu: {objectCode: menuObjectCode}} = this
      getAddFormMeta(routeObjectCode).then(resp => {
        const {data: meta} = resp
        this.route.meta = meta
      })
      getAddFormMeta(menuObjectCode).then(resp => {
        const {data: meta} = resp
        this.menu.meta = meta
      })
    },
    close() {
      this.$emit('ok', this.params);
    },
    submitFeatureForm(formName) {
      const {feature: {type: featureType}, feature} = this
      const _this = this
      _this.$refs[formName].validate((valid) => {
        _this.$refs['extendConf'].validate(subValid => {
          if (valid && subValid) {
            let url = _this.$compile(restUrl.FEATURE_ADD, {
              featureType: featureType
            });
            _this.$axios.post(url, feature).then(({msg = '操作成功'}) => {
              _this.$message.success(msg);
              _this.next()
            }).catch(({msg = '操作失败'}) => {
              _this.$message.error(msg);
            })
          } else {
            return false
          }
        })
      });
    },
    next() {
      if (this.step++ > 2) this.step = 0;
    },
    onCancel: function (event) {
      this.$emit('cancel', event);
    }
  },
  created() {
    this.initAssociateMeta()
    this.initFeatureConfMeta()
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
