<template>
  <div class="ui-conf-tip">
    <h2>{{ componentName }}</h2>
    <vue-markdown :source="getSourceCode(componentName)"></vue-markdown>

    <br>
    <h2>Snippet For Render:</h2>
    <h3>表格图片渲染</h3>
    <code-box v-model="imgRender" :show-bar="false"></code-box>
    <h3>表格附件渲染</h3>
    <code-box v-model="fileRender" :show-bar="false"></code-box>
  </div>
</template>

<script>
import VueMarkdown from 'vue-markdown'
import {metaDesc} from "../../core";

export default {
  name: "UiConfTip",
  components: {
    'vue-markdown': VueMarkdown
  },
  props: {
    componentName: {
      type: String,
      required: true,
      default: () => ''
    }
  },
  data() {
    return {
      metaDesc: metaDesc,
      imgRender: " function(h, value) {\n" +
          "  const v = JSON.parse(value)\n" +
          "  if (v.length > 0) {\n" +
          "  const picUrl = v[0]['url']\n" +
          "  return h('el-image', {\n" +
          "    attrs: {\n" +
          "      src: picUrl,\n" +
          "      \"preview-src-list\":[picUrl]\n" +
          "    },\n" +
          "      style: {\n" +
          "      width: '30px',\n" +
          "      height: '30px'\n" +
          "      }\n" +
          "  }, null)\n" +
          "  }\n" +
          "  return h('span', null, null)\n" +
          "}",
      fileRender: "function(h, value) {\n" +
          "  const v = JSON.parse(value)\n" +
          "  if (v.length > 0) {\n" +
          "\tlet attachDOMS = []\n" +
          "\tv.forEach(f => {\n" +
          "\t\tattachDOMS.push(h('span', null, [\n" +
          "\t\t\th('svg-icon', {attrs: {value: 'attach'}}, null),\n" +
          "\t\t    h('a', {attrs: {href: f.url, target: '_blank'}}, f.name)\n" +
          "\t\t]))\n" +
          "\t})\n" +
          "\treturn h('div', null, attachDOMS)\n" +
          "  }\n" +
          "  return h('span', null, value)\n" +
          "}"
    }
  },
  methods: {
    getSourceCode(componentName) {
      const confDesc = this.metaDesc[componentName];
      return confDesc;
    }
  }

}
</script>

<style scoped lang="scss">
.ui-conf-tip {
  z-index: 9999999 !important;
  max-width: 800px;
  max-height: 600px;
  overflow: auto;
}
</style>
