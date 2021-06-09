<template>
  <div class="container">
    <el-alert :closable="false">
      <i class="el-icon-warning"></i>为元对象: {{ objectCode }} 选择容器组件，并一键生成UI配置:
    </el-alert>
    <br>
    <component-selector v-model="viewComponents" scope="view" multiple placeholder="请选择容器组件"
                        style="display: block"></component-selector>
    <div class="flex-container button-bar">
      <span class="flex"></span>
      <el-button type="primary" size="small" @click="autoComputed">一键生成</el-button>
    </div>
  </div>
</template>

<script>
import ComponentSelector from "../../component/ComponentSelector";

export default {
  name: "AutoComputedOneKeyConfig",
  components: {
    ComponentSelector
  },
  props: {
    objectCode: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      viewComponents: []
    }
  },
  methods: {
    autoComputed() {
      const {objectCode, viewComponents} = this
      this.$axios.safePost("/component/import-auto-computed", {
        objectCode: objectCode,
        componentCodes: viewComponents.join(",")
      }).then(({msg = '操作成功'}) => {
        this.$message.success(msg)
        this.$emit('ok')
      }).catch(({msg = '发生错误'}) => {
        this.$message.error(msg)
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