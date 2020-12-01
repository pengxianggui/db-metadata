<template>
  <el-row class="row" style="width: 100%;">
    <el-col class="col" v-for="(c, index) in colSpan" :span="c" :class="{'dotted-line': line}">
      <slot :name="index"></slot>
    </el-col>
  </el-row>
</template>

<script>
import utils from '../../../utils'
import Meta from "../../mixins/meta";
import DefaultMeta from "../ui-conf";

export default {
  name: "RowGrid",
  label: "栅格布局",
  mixins: [Meta(DefaultMeta)],
  props: {
    span: {
      type: Array,
      validator: (value) => value.every(ele => utils.isNumber(ele))
    },
    showLine: {
      type: Boolean,
      default: () => false
    }
  },
  computed: {
    colSpan() {
      const {span: attrSpan, innerMeta: {conf: {span: metaSpan}}} = this
      return utils.assertEmpty(attrSpan, metaSpan)
    },
    line() {
      const {showLine: attrLine, innerMeta: {conf : { 'show-line': metaLine}}} = this
      return utils.assertEmpty(attrLine, metaLine)
    }
  }
}
</script>

<style scoped lang="scss">
  .dotted-line {
    border: 1px dotted #dddddd;
  }

  .row.dotted-line {
    padding: 6px;
  }

  .row {
    display: flex;

    .col {
      padding: 5px;
    }
  }
</style>
