<template>
  <vue-tinymce-text v-model="nativeValue"
                    :toolbar="toolbar" :menubar="menubar"
                    :width="width" :height="height" v-bind:config="conf"></vue-tinymce-text>
</template>

<script>
import VueTinymceText from 'vue-tinymce-text'
import Val from "../../mixins/value"
import Meta from '../../mixins/meta'
import defaultMeta from '../ui-conf'
import {utils} from "../../../index";

export default {
  name: "RichTextBox",
  label: "富文本框",
  mixins: [Meta(defaultMeta), Val()],
  components: {
    'vue-tinymce-text': VueTinymceText
  },
  props: {
    // id: {
    //   type: String,
    //   default: () => 'vue-tinymce-text' + (Math.random() * 10000)
    // },
    value: {
      type: String,
      default: () => ''
    },
    toolbar: {
      type: Array,
      required: false,
      default() {
        return []
      }
    },
    menubar: {
      type: String,
      default: 'file edit insert view format table'
    },
    height: {
      type: [Number, String],
      required: false,
      default: 360
    },
    width: {
      type: [Number, String],
      required: false,
      default: 'auto'
    }
  },
  computed: {
    baseURL() {
      const {defaults: {baseURL} = {}} = this.$axios
      return baseURL
    },
    conf() {
      const {innerMeta: {conf = {}}, baseURL} = this
      this.$reverseMerge(conf, {
        images_upload_url: utils.compile(baseURL + conf.images_upload_url, { // 兼容单独使用RichTextBox的情况，无objectCode和fieldCode，导致url未编译
          objectCode: null,
          fieldCode: null
        }),
        files_upload_url: utils.compile(baseURL + conf.files_upload_url, { // 兼容单独使用RichTextBox的情况，无objectCode和fieldCode，导致url未编译
          objectCode: null,
          fieldCode: null
        }),

      })
      return conf
    }
  },
  data() {
    return {}
  }
}
</script>

<style scoped>

</style>
