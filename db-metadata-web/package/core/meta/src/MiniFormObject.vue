<template>
  <el-form ref="sub_form" :model="nativeValue" size="mini" class="align-to-label shadow-border" label-position="right"
           label-width="130px">

    <el-form-item label="元对象编码">
      <el-col :span="6">
        <el-input v-model="nativeValue.objectCode" size="mini" readonly></el-input>
      </el-col>
    </el-form-item>
    <el-form-item label="主键生成策略">
      <div class="el-checkbox-group">
        <el-checkbox v-model="nativeValue.isUUIDPrimary" label="UUID主键" border
                     @change="primaryStrategyChange('isNumberSequence', 'isAutoIncrement')"></el-checkbox>
        <el-checkbox v-model="nativeValue.isNumberSequence" label="数字序列" border
                     @change="primaryStrategyChange('isUUIDPrimary', 'isAutoIncrement')"></el-checkbox>
        <el-checkbox v-model="nativeValue.isAutoIncrement" label="自动生成" border
                     @change="primaryStrategyChange('isUUIDPrimary', 'isNumberSequence')"></el-checkbox>
      </div>
    </el-form-item>
    <el-form-item label="数据结构">
      <el-radio-group v-model="nativeValue.structure">
        <el-radio-button label="list">列表</el-radio-button>
        <el-radio-button label="tree">树结构</el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="树形结构配置" v-if="nativeValue.structure=='tree'">
      <row-grid :span="[6, 6, 6, 6]">
        <template #0>
          <drop-down-box v-model="nativeValue.structureConfig.idKey" :data-url="getFieldCodeUrl(nativeValue.objectCode)"
                         placeholder="选择id"></drop-down-box>
        </template>
        <template #1>
          <drop-down-box v-model="nativeValue.structureConfig.pidKey" :data-url="getFieldCodeUrl(nativeValue.objectCode)"
                         placeholder="选择pid"></drop-down-box>
        </template>
        <template #2>
          <drop-down-box v-model="nativeValue.structureConfig.label" :data-url="getFieldCodeUrl(nativeValue.objectCode)"
                         placeholder="选择展示字段"></drop-down-box>
        </template>
        <template #3>
          <text-box v-model="nativeValue.structureConfig.rootIdentify" placeholder="根节点标识"></text-box>
        </template>
      </row-grid>
      <!--      <el-col :span="4">-->
      <!--        <el-input v-model="nativeValue.structureConfig.idKey" placeholder="id 列名"></el-input>-->
      <!--      </el-col>-->
      <!--      <el-col :span="4">-->
      <!--        <el-input v-model="nativeValue.structureConfig.pidKey" placeholder="pid 列名"></el-input>-->
      <!--      </el-col>-->
      <!--      <el-col :span="4">-->
      <!--        <el-input v-model="nativeValue.structureConfig.label" placeholder="label"></el-input>-->
      <!--      </el-col>-->
    </el-form-item>

    <el-form-item label="排序规则(SQL)">
      <el-input placeholder="默认排序规则: columnA desc,columnB asc" v-model="nativeValue.orderBy"></el-input>
    </el-form-item>
    <el-form-item label="过滤规则(SQL)">
      <el-input placeholder="默认过滤条件: a=1 and b=2" v-model="nativeValue.where"></el-input>
    </el-form-item>
    <el-form-item label="业务拦截器">
      <el-input placeholder="配置业务拦截器 完整的包名,多个拦截器使用逗号分割 例如: com.github.md.web.controller.itp.MetaFieldEditPointCut"
                v-model="nativeValue.bizInterceptor"></el-input>
    </el-form-item>

    <el-divider content-position="left">
      <doc-link path="/guide/further-use/metaObjectConfig.html">
        <span><i class="el-icon-question"></i>元对象配置, 详见:</span>
        <template #link="{open}">
          <el-link @click="open()">文档</el-link>
        </template>
      </doc-link>
    </el-divider>
  </el-form>
</template>

<script>
import utils from '../../../utils'
import {isEmpty} from "../../../utils/common";
import {restUrl} from "../../../constant/url";
import DocLink from "../../../doc/DocLink";

export default {
  name: "MiniFormObject",
  label: "元对象Config迷你表单",
  description: "输入控件的一种,JsonBox的表单表现形式",
  components: {DocLink},
  props: {
    value: {
      type: [Object, String],
      default: () => {
      }
    }
  },
  data() {
    return {
      config: {
        objectCode: null,
        structure: "list",
        structureConfig: {
          idKey: null,
          pidKey: null,
          label: null,
          rootIdentify: null
        },
        isUUIDPrimary: false,
        isNumberSequence: false,
        isAutoIncrement: false,
        bizInterceptor: '',
        orderBy: null,
        where: null,
      }
    }
  },
  updated() {
    this.$emit("input", this.nativeValue);
  },
  mounted() {
  },
  methods: {
    primaryStrategyChange() {
      for (let i = 0; i < arguments.length; i++) {
        let key = arguments[i]
        this.config[key] = false
      }
    },
    getFieldCodeUrl(objectCode) {
      if (isEmpty(objectCode)) {
        return restUrl.FIELD_CODE_LIST_BY_OBJECT
      }

      return this.$compile(restUrl.FIELD_CODE_LIST_BY_OBJECT, {
        objectCode: objectCode
      })
    }
  },
  computed: {
    nativeValue() {
      let value = utils.convertToObject(this.value)

      Object.keys(this.config).forEach(key => {
        if (value && value.hasOwnProperty(key)) {
          this.config[key] = value[key];
        }
      });
      // this.$emit("input", this.config);  // immediate emit
      return this.config;
    }
  }
}
</script>

<style scoped>
.align-to-label {
  margin-top: 5px;
}

.shadow-border {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  padding: 15px 5px 5px 5px;

}
</style>
