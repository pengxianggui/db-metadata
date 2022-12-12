<template>
  <div>
    <div class="operation-bar" v-if="innerShowBar">
      <span style="flex: 1"></span>
      <el-select v-model="mode" size="mini" v-if="innerMeta['show-mode-chose']" @change="handleChange('mode')">
        <el-option v-for="item in innerMeta.modes" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      &nbsp;
      <el-select v-model="theme" size="mini" v-if="innerMeta['show-theme-chose']" @change="handleChange('theme')">
        <el-option v-for="item in innerMeta.themes" :key="item" :label="item" :value="item"></el-option>
      </el-select>
    </div>
    <textarea ref="editor" type="textarea" :value="nativeValue"></textarea>
  </div>
</template>

<script>
import DefaultMeta from '../ui-conf'
import Meta from '../../mixins/meta'
import Val from '../../mixins/value'
import assembleMeta from './assembleMeta'
import CodeMirror from 'codemirror/lib/codemirror'
import "codemirror/lib/codemirror.css"
// 代码提示
import "codemirror/addon/hint/show-hint.css"
import "codemirror/addon/hint/show-hint.js"
import {assertEmpty} from "../../../utils/common";


export default {
  name: "CodeBox",
  label: "代码编辑框",
  mixins: [Meta(DefaultMeta, assembleMeta), Val()],
  props: {
    value: String,
    showBar: {
      type: Boolean,
      default: () => null
    }
  },
  data() {
    return {
      mode: null,
      theme: null,
      editor: null
    }
  },
  methods: {
    handleChange(key) {
      this.editor.setOption(key, this[key]);
    }
  },
  created() {
    let {conf: {mode, theme}} = this.innerMeta;
    this.mode = mode;
    this.theme = theme;
  },
  mounted() {
    let {innerMeta: {height, conf}} = this;
    this.editor = CodeMirror.fromTextArea(this.$refs.editor, conf);
    this.editor.on('change', (instance) => {
      this.nativeValue = instance.getValue();
    });
    this.editor.setSize('auto', height);
  },
  computed: {
    innerShowBar() {
      const {showBar, innerMeta: {'show-bar': showBarInMeta}} = this
      return assertEmpty(showBar, showBarInMeta)
    }
  }
}
</script>

<style scoped>
.operation-bar {
  padding: 2px;
  background-color: rgb(64, 158, 255);
  display: flex;
}
</style>
