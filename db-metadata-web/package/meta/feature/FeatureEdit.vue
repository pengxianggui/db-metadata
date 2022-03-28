<template>
  <!-- 功能配置 -->
  <form-view ref="featureForm" ic="meta_feature.FormView" form-type="update" :primary-kv="primaryKv" style="width: 100%"
             @ok="finish" @cancel="cancel">

    <template #form-item-type="{column, model}">
      <el-form-item :label="column.label" prop="type">
        <drop-down-box v-model="model.type" :meta="column" @change="featureTypeChange(model.type)"></drop-down-box>
      </el-form-item>
    </template>

    <template #form-item-config="{column, model}">
      <el-form-item :label="column.label" prop="config">
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
      </el-form-item>
    </template>

  </form-view>
</template>

<script>
import {FEATURE_TYPE} from "./ext/featureType";
import AutoComputedButton from "./ext/AutoComputedButton";
import SingleGrid from "./ext/SingleGrid";
import TreeSingleGrid from "./ext/TreeSingleGrid";
import MasterSlaveGrid from "./ext/MasterSlaveGrid";
import TreeTable from "./ext/TreeTable";

export default {
  name: "FeatureEdit",
  components: {
    AutoComputedButton,
    SingleGrid,
    TreeSingleGrid,
    MasterSlaveGrid,
    TreeTable
  },
  props: {
    primaryKv: {
      type: String,
      required: true
    }
  },
  data() {
    return {
    }
  },
  methods: {
    featureTypeChange(featureType) {
      const {value} = FEATURE_TYPE[featureType]
      this.$refs['featureForm'].setItem("config", this.$merge({}, value))
    },

    finish() {
      this.$emit('ok');
    },

    cancel: function (event) {
      this.$emit('cancel', event);
    }
  }
}
</script>

<style scoped>

</style>
