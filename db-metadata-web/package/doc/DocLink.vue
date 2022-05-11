<template>
  <div class="md_doc-link">
    <slot></slot>&nbsp;
    <slot name="link" v-bind:open="openHelpDoc">
      <el-button size="mini" icon="el-icon-question" circle @click="openHelpDoc"></el-button>
    </slot>
  </div>
</template>

<script>
import {appConfig} from "../config";
import utils from '../utils'

export default {
  name: "DocLink",
  props: {
    path: {
      type: String,
      default: () => ''
    }
  },
  methods: {
    openHelpDoc() {
      let url = utils.resolve(appConfig.docUrl, this.path)
      window.open(url, "帮助文档", "width=1100,height=700")
    }
  }
}
</script>
