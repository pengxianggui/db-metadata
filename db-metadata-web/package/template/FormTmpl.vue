<template>
  <div class="page-container">
    <form-view :ic="config.instanceCodes.FormView" @ok="handleOk" @cancel="handleCancel">
      <template #action="scope">
        <slot name="action" v-bind="scope"></slot>
      </template>
    </form-view>
  </div>
</template>

<script>
import utils from '../utils'
import {loadFeature} from "../utils/rest";
import {FormConfig} from "../meta/feature/ext/featureType";

export default {
  name: "FormTmpl",
  meta: {
    type: 'template',
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
  data() {
    const {fc: R_fc} = this.$route.query
    const featureCode = utils.assertUndefined(this.fc, R_fc);
    return {
      featureCode: featureCode
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
  created() {
    this.$merge(this.config, FormConfig)

    if (!utils.isEmpty(this.featureCode)) {
      loadFeature(this.$axios, this.featureCode).then(resp => {
        this.$reverseMerge(this.config, resp.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
