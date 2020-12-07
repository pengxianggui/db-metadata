<template>
  <div style="padding: 20px; overflow: auto; height: 100%;">
    <h1>组件开发调试页面</h1>
    <form-view :meta="meta">
      <template #form-item-name="{item, model}">
        <el-form-item label="姓名1" prop="name">
          <text-box v-model="model.name"></text-box>
        </el-form-item>
      </template>

      <template #form-item-height="{item, model}">
        <el-form-item label="身高1" prop="height">
          <text-box v-model="model.height"></text-box>
        </el-form-item>
      </template>
      <template #form-item-weight="{item, model}">
        <el-form-item label="体重1" prop="weight">
          <text-box v-model="model.weight"></text-box>
        </el-form-item>
      </template>
    </form-view>

  </div>
</template>

<script>

export default {
  name: "WorkSpace",
  data() {
    return {
      meta: {
        columns: [
          {
            name: 'name',
            label: '姓名',
            component_name: 'TextBox'
          },
          {
            name: 'age',
            label: '年龄',
            component_name: 'NumBox'
          },
          {
            name: 'sex',
            label: '性别',
            component_name: 'DropDownBox'
          }, {
            name: 'RowGird',
            label: '布局',
            component_name: 'RowGrid',
            "conf": {
              "span": [12, 12],
              "show-line": false, // 显示虚线边框
            },
            columns: {
              "0": [{
                name: 'height',
                label: '身高',
                component_name: 'NumBox'
              }],
              "1": [{
                name: 'weight',
                label: '体重',
                component_name: 'NumBox'
              }]
            }
          }
        ]
      }
    }
  },
  methods: {},
  created() {
  }
}
</script>

<style scoped>
</style>
