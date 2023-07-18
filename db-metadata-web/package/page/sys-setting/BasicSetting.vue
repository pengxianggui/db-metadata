<template>
  <div>
    <form-view ref="meta_app_config.formView" ic="meta_app_config.formView" form-type="add" :model="model"
               @submit="handleSubmit" @cancel="handleCancel" @ok="handleOk"
               v-if="model.id">
      <template #form-item-pass_encrypt_key="{column}">
        <el-form-item :name="column.name" :label="column.label">
          <pass-box v-model="model.pass_encrypt_key" :meta="column"></pass-box>
        </el-form-item>
      </template>
    </form-view>
  </div>
</template>

<script>
import {restUrl} from "@/../package/constant/url";
import {isIegalAesKey} from "../../utils/common";

export default {
  name: "BasicSetting",
  data() {
    return {
      model: {
        id: null,
        name: null,
        logo: null,
        registerable: false,
        default_pass: null,
        pass_encrypt_key: null,
        login_bg: null,
        reset_pass: null,
        show_greeting: true,
        show_theme_setting: false,
        allow_custom_theme: false,
        version: null
      }
    }
  },
  created() {
    this.initFormData()
  },
  methods: {
    initFormData() {
      this.$axios.get(restUrl.GET_APP_DYNAMIC_CONFIG).then(({data: config}) => {
        const {
          id,
          name,
          logo,
          registerable,
          defaultPass,
          passEncryptKey,
          loginBg,
          resetPass,
          showGreeting,
          showThemeSetting,
          allowCustomTheme,
          version
        } = config

        this.model.id = id
        this.model.name = name
        this.model.logo = [{url: logo}] // dbmeta内置的ImgBox接受此格式
        this.model.registerable = registerable
        this.model.default_pass = defaultPass
        this.model.pass_encrypt_key = passEncryptKey
        this.model.login_bg = [{url: loginBg}] // dbmeta内置的ImgBox接受此格式
        this.model.reset_pass = resetPass
        this.model.show_greeting = showGreeting
        this.model.show_theme_setting = showThemeSetting
        this.model.allow_custom_theme = allowCustomTheme
        this.model.version = version
      })
    },
    handleSubmit(model) {
      const {pass_encrypt_key} = model
      if (!isIegalAesKey(pass_encrypt_key)) {
        this.$alert('【加密密钥】无效, 长度必须是16、24或32', '表单校验不通过', {
          confirmButtonText: '返回修改',
          type: 'warning'
        })
        return;
      }
      this.$refs['meta_app_config.formView'].doSubmit()
    },
    handleCancel() {
      this.$confirm('修改将被丢弃', '请注意', {
        showCancelButton: true,
        type: 'warning'
      }).then(() => {
        this.initFormData()
      })
    },
    handleOk() {
      this.$confirm('是否刷新页面以显示新内容', '保存成功！', {
        showCancelButton: true
      }).then(() => {
        location.reload()
      }).catch(() => {
        this.initFormData()
      })
    },
  }
}
</script>

<style scoped>

</style>
