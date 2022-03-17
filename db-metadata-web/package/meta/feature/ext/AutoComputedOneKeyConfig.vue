<template>
  <div class="container">
    <meta-object-selector v-model="objectCode" @change="calInstanceCodes" placeholder="选择元对象" style="margin-bottom: 5px;"></meta-object-selector>
    <el-alert :closable="false">
      <i class="el-icon-warning"></i>为元对象: {{ objectCode }} 选择容器组件，并一键生成UI配置:
    </el-alert>
    <h3>选择容器</h3>
    <component-selector v-model="viewComponents" scope="view" multiple placeholder="请先选择要生成实例配置的容器组件"
                        @change="calInstanceCodes" style="display: block"></component-selector>
    <h3>
      <span>指定实例编码</span>
      <el-tooltip placement="right" content="英文逗号分隔, 与选择的容器对应"><i class="el-icon-question"></i></el-tooltip>
    </h3>
    <text-box v-model="instanceCodeStr"></text-box>
    <div class="flex-container button-bar">
      <span class="flex"></span>
      <el-button type="primary" size="small" @click="autoComputed">一键生成</el-button>
    </div>
  </div>
</template>

<script>
import ComponentSelector from "../../component/ComponentSelector";
import MetaObjectSelector from "../../component/MetaObjectSelector";
import {isEmpty} from "../../../utils/common";
import {restUrl} from "../../../constant/url";

export default {
  name: "AutoComputedOneKeyConfig",
  components: {
    ComponentSelector,
    MetaObjectSelector
  },
  props: {
    oc: String
  },
  data() {
    return {
      viewComponents: [],
      instanceCodeStr: '',
      objectCode: this.oc
    }
  },
  methods: {
    calInstanceCodes() {
      const {objectCode, viewComponents = []} = this
      this.instanceCodeStr = viewComponents.map(c => objectCode + '.' + c).join(",")
    },
    autoComputed() {
      const {objectCode, viewComponents, instanceCodeStr} = this
      const instanceCodes = instanceCodeStr.split(',').filter(ic => !isEmpty(ic))
      if (instanceCodes.length !== viewComponents.length) {
        this.$message.error('实例编码与容器不匹配')
        return
      }
      if ((new Set(instanceCodes)).size !== instanceCodes.length) {
        this.$message.error('实例编码重复!')
        return;
      }

      this.$axios.safePost(restUrl.COMPONENT_INSTANCE_AUTO_CAL, {
        objectCode: objectCode,
        componentCodes: viewComponents.join(","),
        instanceCodes: instanceCodes.join(",")
      }).then(({msg = '操作成功'}) => {
        this.$message.success(msg)
        this.$emit('ok')
      }).catch(({msg = '发生错误'}) => {
        console.error(msg)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.button-bar {
  margin-top: 10px;
  margin-bottom: -40px;
}

.flex-container {
  display: flex;

  & .flex {
    flex: 1;
  }
}
</style>
