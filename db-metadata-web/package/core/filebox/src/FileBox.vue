<template>
  <div class="file-box">
    <template v-if="seats.length > 0">
      <!-- seat模式 TODO 存在bug: 如果移除前面的图片，排后面的图片会被渲染到前面去。本质上是index没有跟具体的值强绑定 -->
      <upload-box class="upload-box" v-for="(seat, index) in seats" :key="seat + index"
                   :seat="seat" :meta="innerMeta" v-model="nativeValue[index]" mode="seat"
                   @input="changeHandler" :show-file-list="true" v-bind="conf">
        <template #default="{seat, isView}">
          <div class="upload-btn">
            <i class="seat-name">请上传{{ seat }}：</i>
            <i class="icon el-icon-plus" v-if="!isView"></i>
          </div>
        </template>
      </upload-box>
    </template>
    <template v-else>
      <!-- 普通模式 -->
      <upload-box :meta="innerMeta" v-model="nativeValue" mode="default"
                   :show-file-list="true" v-bind="conf">
        <template #default="{seat, isView}">
          <el-button icon="el-icon-upload" size="mini" class="upload-btn" v-if="!isView">点击上传</el-button>
        </template>
      </upload-box>
    </template>
  </div>
</template>

<script>
import Meta from '../../mixins/meta'
import DefaultMeta from '../ui-conf'
import conver from "./conver";
import reverse from "./reverse";
import Val from "../../mixins/value";

export default {
  mixins: [Meta(DefaultMeta), Val(conver, reverse)],
  name: "FileBox",
  label: "文件上传框",
  props: {
    value: {
      type: [Array, String],
      default: () => []
    },
  },
  methods: {
    changeHandler() {
      this.$emit('input', reverse(this.nativeValue))
    }
  },
  computed: {
    seats() {
      let {innerMeta: {seats = []}} = this
      if (!Array.isArray(seats)) {
        console.error('[MetaElement] seats参数必须为数组')
        seats = []
      }
      return seats
    },
    conf() {
      const {innerMeta: {conf = {}}, $attrs, $reverseMerge} = this
      return $reverseMerge(conf, $attrs)
    }
  }
}
</script>

<style lang="scss" scoped>
.file-box {
  .upload-box {
    display: block;
  }
}
</style>
