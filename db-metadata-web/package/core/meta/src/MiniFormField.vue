<template>
  <el-form ref="sub_form" :model="nativeValue" size="mini" class="form shadow-border"
           label-position="left" label-width="100px">
    <el-form-item label="默认值">
      <el-col :span="6">
        <!--  TODO: 应当根据元字段的数据类型决定使用什么控件设置默认值  -->
        <el-input v-model="nativeValue.defaultVal" size="mini"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item label="开关设置">
      <div class="el-checkbox-group">
        <el-checkbox v-model="nativeValue.isNullable" label="允许为空" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isUnique" label="是否唯一" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isSearch" label="允许搜索" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isListShow" border>
          <span>列表显示</span>&nbsp;
          <el-tooltip content="列表视图时,此项表示sql语句中此列是否被select,页面上的显隐可通过字段实例配置hidden控制">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </el-checkbox>
        <el-checkbox v-model="nativeValue.isMultiple" label="允许多值" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isFile" label="是否文件" border></el-checkbox>
        <el-tooltip content="上传文件/图片需要勾选" placement="right">
          <i class="el-icon-question"></i>
        </el-tooltip>
      </div>
    </el-form-item>

    <el-divider content-position="left">
      <span>表单配置</span>&nbsp;
      <el-tooltip placement="right">
        <div slot="content">
          显示: 正常状态;
          隐藏: 控件存在但不可见;
          只读: 控件被disable了;
          禁用: 该域不会存在于表单中。
          注意只读和禁用的区别。
        </div>
        <i class="el-icon-question"></i>
      </el-tooltip>
    </el-divider>
    <el-form-item label="新增状态">
      <el-radio-group v-model="nativeValue.addStatus">
        <el-radio-button label="100">显示</el-radio-button>
        <el-radio-button label="50">隐藏</el-radio-button>
        <el-radio-button label="30">只读</el-radio-button>
        <el-radio-button label="10">禁用</el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="更新状态">
      <el-radio-group v-model="nativeValue.updateStatus">
        <el-radio-button label="100">显示</el-radio-button>
        <el-radio-button label="50">隐藏</el-radio-button>
        <el-radio-button label="30">只读</el-radio-button>
        <el-radio-button label="10">禁用</el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="查看状态">
      <el-radio-group v-model="nativeValue.viewStatus">
        <el-radio-button label="100">显示</el-radio-button>
        <el-radio-button label="50">隐藏</el-radio-button>
        <el-radio-button label="30">只读</el-radio-button>
        <el-radio-button label="10">禁用</el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="数据源" class="form-item-options">
      <z-toggle-panel :default-open="hasTranslation" label-position="top-left">
        <template #label="{open}">
          <el-tooltip placement="right">
            <i class="el-icon-question"></i>
            <div slot="content">
              <span>优先级: 静态数组 > 字典名 > 接口 > 指定SQL</span>
              <doc-link path="/guide/further-use/metaFieldConfig.html#数据源">
                <span>更多详见:</span>
                <template #link="{open}"><el-link @click="open">文档</el-link></template>
              </doc-link>
            </div>
          </el-tooltip>
          &nbsp;
          <i :class="{'el-icon-caret-bottom': !open, 'el-icon-caret-top': open}"></i>
        </template>
        <el-tabs v-model="activeOption">
          <el-tab-pane label="静态数组" name="scopeOptions">
            <options-input v-model="nativeValue.scopeOptions"></options-input>
          </el-tab-pane>
          <el-tab-pane label="字典名" name="scopeDict">
            <drop-down-box data-url="/dict/names" v-model="nativeValue.scopeDict" :filterable="true"></drop-down-box>
          </el-tab-pane>
          <el-tab-pane label="接口" name="scopeUrl">
            <text-box v-model="nativeValue.scopeUrl" placeholder="请输入服务端可访问的接口地址">
              <template #append>
                <el-button @click="checkApi(nativeValue.scopeUrl)">接口校验</el-button>
              </template>
            </text-box>
            <doc-link path="/guide/further-use/metaFieldConfig.html#数据源-接口">
              <span>接口响应数据必须满足特定的条件, 详见:</span>
              <template #link="{open}">
                <el-link @click="open()">文档</el-link>
              </template>
            </doc-link>
          </el-tab-pane>
          <el-tab-pane label="sql" name="scopeSql">
            <code-box :check="true" v-model="nativeValue.scopeSql" :show-bar="false"></code-box>
            <div style="display: flex; align-items: center; justify-content: space-between; margin-top: 3px;">
              <el-tooltip content="SQL必须以id和cn作为返回的结果集" placement="right"><i class="el-icon-question"></i></el-tooltip>
              <el-button @click="checkSql(nativeValue.scopeSql)" type="primary">校验</el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </z-toggle-panel>
    </el-form-item>
    <el-form-item>
      <template #label>
        <span>是否转义</span>&nbsp;
        <el-tooltip placement="right">
          <div slot="content">勾选后, 列表页、详情页等显示处, 会自动将原始值转义为显示值(转义依据参考【数据源】配置)</div>
          <i class="el-icon-question"></i>
        </el-tooltip>
      </template>
      <bool-box v-model="nativeValue.escape"></bool-box>
    </el-form-item>

    <el-divider content-position="left">
      <doc-link path="/guide/further-use/metaFieldConfig.html">
        <span><i class="el-icon-question"></i>元字段配置, 详见:</span>
        <template #link="{open}">
          <el-link @click="open()">文档</el-link>
        </template>
      </doc-link>
    </el-divider>
  </el-form>
</template>

<script>
import utils from '../../../utils'
import OptionsInput from '../../../meta/form-builder/relate/OptionsInput'
import DocLink from "../../../doc/DocLink";
import {isEmpty, isString} from "../../../utils/common";
import {restUrl} from "../../../constant/url";

export default {
  name: "MiniFormField",
  label: "元字段Config迷你表单",
  description: "输入控件的一种,JsonBox的表单表现形式",
  components: {
    OptionsInput, DocLink
  },
  props: {
    value: {
      type: [Object, String],
      default: () => {
      }
    }
  },
  data() {
    return {
      activeOption: 'scopeOptions',

      config: {
        addStatus: 100,
        updateStatus: 100,
        viewStatus: 30,
        defaultVal: null,
        isNullable: true,
        isUnique: false,
        isSearch: false,
        isMultiple: false,
        isFile: false,
        scopeSql: null,
        scopeUrl: null,
        scopeOptions: [],
        scopeDict: null, // 字典名
        isListShow: true,
        escape: true
      }
    }
  },
  updated() {
    this.$emit("input", this.nativeValue);
  },
  mounted() {
    this.$nextTick(() => {
      const {scopeOptions, scopeDict, scopeUrl, scopeSql} = this.config
      if (!isEmpty(scopeOptions)) {
        this.activeOption = 'scopeOptions'
      } else if (!isEmpty(scopeDict)) {
        this.activeOption = 'scopeDict'
      } else if (!isEmpty(scopeUrl)) {
        this.activeOption = 'scopeUrl'
      } else if (!isEmpty(scopeSql)) {
        this.activeOption = 'scopeSql'
      }
    })
  },
  methods: {
    checkSql(sql) {
      this.$axios.safeGet(this.$compile(restUrl.CHECK_SQL, {sql: sql})).then(({msg = '校验通过'}) => {
        this.$message.success(msg)
      }).catch(({msg}) => {
        this.$message.error(msg)
      })
    },
    checkApi(url) {
      this.$axios.safeGet(this.$compile(restUrl.CHECK_API, {url: encodeURIComponent(url)})).then(({msg = '校验通过'}) => {
        this.$message.success(msg)
      }).catch(({msg}) => {
        this.$message.error(msg)
      })
    }
  },
  computed: {
    nativeValue() {
      let value = this.value
      if (isString(this.value)) {
        value = utils.parse(this.value);
      }

      this.$reverseMerge(this.config, value)

      this.$emit("input", this.config);  // immediate emit
      return this.config;
    },
    hasTranslation() {
      let {value = {}} = this
      if (isString(value)) {
        value = utils.convertToObject(value)
      }
      const {scopeSql = '', scopeOptions = [], scopeDict = null} = value
      return (utils.isString(scopeSql) && scopeSql.trim() !== '')
          || !isEmpty(scopeDict)
          || (utils.isArray(scopeOptions) && scopeOptions.length > 0)
    }
  }
}
</script>

<style scoped lang="scss">
.el-checkbox-group > * {
  margin: 0 10px 0 0 !important;
}

.shadow-border {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  padding: 15px;
}

.upload-seats {
  display: grid;
  grid-template-columns: 200px 200px 200px;
}

.form-item-options {
  ::v-deep .el-form-item__content {
    margin-left: 5px !important;
  }

  .sub-form-item-sql {
    ::v-deep .el-form-item__label {
      width: 100% !important;
    }
  }
}
</style>
