<template>
  <div style="padding: 20px; overflow: auto; height: 100%;">
    <h1>组件开发调试页面</h1>

    <el-form ref="form" :model="model" :rules="rules">
      <el-form-item prop="image" label="图片">
        <img-box v-model="model.image" action="/file/upload?objectCode=ctrl_demand&fieldCode=pic"
                 list-type="picture-card"></img-box>
      </el-form-item>
      <el-form-item>
        <el-button @click="submit('form')">提交</el-button>
      </el-form-item>
    </el-form>

    <form-view :meta="formMeta"></form-view>
  </div>
</template>

<script>
import {Rest} from '@/../package/index'
export default {
  name: "WorkSpace",
  data() {
    return {
      model: {
        image: []
      },
      rules: {
        image: [{required: true, message: '图片必填', trigger: 'blur'}]
      },

      formMeta: {}
    }
  },
  methods: {
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('success')
        } else {
          console.log('error')
        }

      })
    }
  },
  created() {
    Rest.getAddFormMeta('ctrl_demand').then(resp => {
      this.formMeta = resp.data
    })
  }
}
</script>

<style scoped>
.ddd {
  font-size: 40px;
}
</style>
