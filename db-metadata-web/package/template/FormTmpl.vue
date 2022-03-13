<template>
  <div class="page-container">
    <form-view :ic="config.instanceCodes.FormView" @ok="handleOk" @cancel="handleCancel"></form-view>
  </div>
</template>

<script>
import utils from '../utils'
import {loadFeature} from "../utils/rest";
import {FormConfig} from "../meta/feature/ext/featureType";

export default {
  name: "FormTmpl",
  meta: {
    isTemplate: true,
    isPage: false,
    cn: '表单模板',
    icon: 'form',
    buildIn: true // 内建：DbMeta提供
  },
  props: {
    fc: String,
    config: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  methods: {
    handleOk(params) {
      this.$goBack()
    },
    handleCancel(model) {
      this.$goBack()
    }
  },
  data() {
    const {fc: R_fc} = this.$route.query
    const featureCode = utils.assertUndefined(this.fc, R_fc);
    return {
      featureCode: featureCode
    }
  },
  created() {
    this.$merge(this.config, FormConfig)

    const {featureCode} = this
    if (!utils.isEmpty(featureCode)) {
      loadFeature(this.$axios, featureCode).then(resp => {
        this.$merge(this.config, resp.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
