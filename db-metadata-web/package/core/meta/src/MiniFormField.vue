<template>
  <el-form ref="sub_form" :model="nativeValue" size="mini" class="align-to-label shadow-border "
           label-position="left" label-width="80px">
    <el-form-item label="默认值">
      <el-col :span="6">
        <!--  TODO: 应当根据元字段的数据类型决定使用什么控件设置默认值  -->
        <el-input v-model="nativeValue.defaultVal" size="mini"></el-input>
      </el-col>
    </el-form-item>
    <el-form-item label="开关设置">
      <div class="el-checkbox-group">
        <el-checkbox v-model="nativeValue.isNullable" label="允许为空" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isSearch" label="允许搜索" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isListShow" label="列表显示" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isMultiple" label="允许多值" border></el-checkbox>
        <el-checkbox v-model="nativeValue.isFile" label="是否文件" border></el-checkbox>
        <el-tooltip content="上传文件/图片需要勾选" placement="right">
          <i class="el-icon-question"></i>
        </el-tooltip>
      </div>
    </el-form-item>
    <el-form-item v-if="nativeValue.isFile" label="上传插槽">
      <el-tooltip content="只有一个插槽时可不填,多个插槽时插槽名称指定后,生成多个上传组件后会标识每个上传组件传到哪一个插槽" placement="right">
        <i class="el-icon-question"></i>
      </el-tooltip>
      <div class="upload-seats">
        <template v-for="index in nativeValue.seats.length">
          <el-input v-model="nativeValue.seats[index-1]" :placeholder="'第'+index+'个位置插槽名称'" size="mini">
            <template slot="append">
              <el-button type="primary" icon="el-icon-plus" size="mini"
                         @click="nativeValue.seats.push('')"></el-button>
              <el-button type="primary" icon="el-icon-minus" size="mini"
                         @click="nativeValue.seats.splice(index - 1,1)" v-if="index !== 1"></el-button>
            </template>
          </el-input>
        </template>
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
          <el-tooltip content="优先级: 静态数组 > 字典名 > 指定SQL" placement="right">
            <i class="el-icon-question"></i>
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
  </el-form>
</template>

<script>
import utils from '../../../utils'
import OptionsInput from '../../../meta/form-builder/relate/OptionsInput'
import {isEmpty, isString} from "../../../utils/common";
import {restUrl} from "../../../constant/url";

export default {
  name: "MiniFormField",
  label: "元字段Config迷你表单",
  description: "输入控件的一种,JsonBox的表单表现形式",
  components: {
    OptionsInput
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
        seats: [""],
        addStatus: 100,
        updateStatus: 100,
        viewStatus: 30,
        defaultVal: null,
        isNullable: true,
        isSearch: false,
        isMultiple: false,
        isFile: false,
        scopeSql: null,
        scopeOptions: [],
        scopeDict: null, // 字典名
        scopeRange: [], // 不明
        isListShow: true,
      }
    }
  },
  updated() {
    this.$emit("input", this.nativeValue);
  },
  mounted() {
    this.$nextTick(() => {
      const {scopeOptions, scopeDict, scopeSql} = this.config
      if (!isEmpty(scopeOptions)) {
        this.activeOption = 'scopeOptions'
      } else if (!isEmpty(scopeDict)) {
        this.activeOption = 'scopeDict'
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

.align-to-label {
  margin-top: 5px;
}

.shadow-border {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  padding: 15px 0px 5px 5px;

}

.upload-seats {
  display: grid;
  grid-template-columns: 200px 200px 200px;
}

.form-item-options {
  /deep/ .el-form-item__content {
    margin-left: 5px !important;
  }

  .sub-form-item-sql {
    /deep/ .el-form-item__label {
      width: 100% !important;
    }
  }
}
</style>
