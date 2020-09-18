<template>
  <div class="container">
    <el-card class="feature-form">
      <el-form ref="featureForm" :model="feature" label-width="120px">
        <el-form-item label="功能类别" prop="type" required>
          <radio-box :data-url="featureTypeUrl" v-model="feature.type" @change="initFeatureConfMeta"></radio-box>
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
          <el-button @click="onSubmit('featureForm')" type="primary">保存</el-button>
          <el-button @click="onCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="associate-box">
      <h3>关联路由</h3>
      <form-view :ref="route.objectCode" :meta="route.meta" @ok="route.saved = true"
                 :disabled="route.saved">
        <template #form-item-pid="{column, model}">
          <el-form-item :name="column.name" :label="column.label">
            <drop-down-box :data-url="column.data_url" v-model="model[column.name]"></drop-down-box>
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
            <text-box v-model="model[column.name]"
                      @change="$refs[menu.objectCode].setItem('path', model[column.name] + '?fc=' + feature.code)"></text-box>
          </el-form-item>
        </template>
      </form-view>
      <h3>关联菜单</h3>
      <form-view :ref="menu.objectCode" :meta="menu.meta" @ok="menu.saved = true"
                 :disabled="menu.saved"></form-view>
    </el-card>
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

// 后端功能类别代码 和 前端功能类别代码的映射： TODO 统一
const FEATURE_TYPE = {
  MasterSlaveGrid: 'MasterSlaveGrid',
  SingleGrid: 'SingleGrid',
  TreeSingleGrid: 'TreeInTable',
  TreeAndSingleGrid: 'TreeAndTable'
};

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
      FEATURE_TYPE: FEATURE_TYPE,
      objectCode: objectCode,
      primaryKey: primaryKey,
      featureTypeUrl: restUrl.LIST_FEATURE_TYPE,
      instanceCodeUrl: utils.resolvePath(restUrl.INSTANCE_CODE_LIST, {
        type: 'META_OBJECT'
      }),
      feature: {
        type: FEATURE_TYPE['SingleGrid'],
        name: objectCode,
        code: objectCode,
        instanceCode: null,
        bizInterceptor: '',
        config: {},
      },
      route: {
        objectCode: 'meta_router',
        meta: {},
        saved: false,
      },
      menu: {
        objectCode: 'meta_menu',
        meta: {},
        saved: false
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
      switch (featureType) {
        case FEATURE_TYPE['MasterSlaveGrid']:
          this.feature.config = {
            master: {
              objectCode: objectCode,
              primaryKey: primaryKey
            },
            slaves: [{
              objectCode: null,
              foreignFieldCode: null,
              order: 0
            }]
          }

          break;
        case FEATURE_TYPE['SingleGrid']:
          this.feature.config = {
            singleGrid: {
              objectCode: objectCode
            }
          }

          break;
        case FEATURE_TYPE['TreeSingleGrid']:
          this.feature.config = {
            table: {
              objectCode: objectCode,
              idKey: null,
              pidKey: null,
              rootIdentify: null,
              label: null,
              isSync: false
            }
          }

          break;
        case FEATURE_TYPE['TreeAndSingleGrid']:
          this.feature.config = {
            tree: {
              objectCode: null,
              idKey: primaryKey,
              pidKey: null,
              rootIdentify: null,
              label: null,
              isSync: false,
              // primaryKey: primaryKey
            },
            table: {
              objectCode: objectCode,
              primaryKey: null,
              foreignFieldCode: null
            }
          }
          break;
      }
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
    doSubmit() {
      const {feature: {type: featureType}, feature} = this

      let url = this.$compile(restUrl.FEATURE_ADD, {
        featureType: featureType
      });
      this.$axios.post(url, feature).then(({msg = '操作成功'}) => {
        this.$message.success(msg);
        this.$emit('ok', this.params);
      }).catch(({msg = '操作失败'}) => {
        this.$message.error(msg);
      })
    },

    onSubmit(formName) {
      const {route: {saved: routeSaved}, menu: {saved: menuSaved}} = this
      this.$refs[formName].validate((valid) => {
        this.$refs['extendConf'].validate(subValid => {
          if (valid && subValid) {
            if (!(routeSaved && menuSaved)) {
              let msg = ''
              msg += (!routeSaved ? '路由表单没有保存,' : '')
              msg += (!menuSaved ? '菜单表单没有保存,' : '')
              this.$confirm(`${msg}建议你填写并手动保存下, 我可不会自动帮你保存, 因为我不知道你要不要保存.`, '提示', {
                type: 'warning',
                showCancelButton: true,
                cancelButtonText: '不了, 就保存功能吧',
                showConfirmButton: true,
                confirmButtonText: '采纳你的建议'
              }).then(() => {
              }).catch(() => {
                this.doSubmit()
              })
            } else {
              this.doSubmit() // submit
            }
          } else {
            return false
          }
        })
      });
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
  //height: 600px;

  .feature-form {
    flex: 1;
    overflow: auto;
  }

  .associate-box {
    flex: 1;
    overflow: auto;
  }
}
</style>
