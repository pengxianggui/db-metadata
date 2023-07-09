<template>
  <form-view ic="meta_app_config.formView" form-type="add" :model="model"></form-view>
</template>

<script>
export default {
  name: "BasicSetting",
  data() {
    return {
      model: {
        id: null,
        name: null,
        logo: null,
        registerable: false,
        defaultPass: null,
        passEncryptKey: null,
        loginBg: null,
        resetPass: null,
        showGreeting: true,
        showThemeSetting: false,
        allowCustomTheme: false,
        version: null
      }
    }
  }
}
</script>

<style scoped>

</style>
