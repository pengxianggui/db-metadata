<template>
  <div class="file-box">
    <template v-if="seats.length > 0">
      <!-- seat模式 -->
      <upload-item class="upload-item" v-for="(seat, index) in seats" :key="seat + index"
                   :seat="seat" :meta="innerMeta" v-model="nativeValue[index]" mode="seat"
                   @input="changeHandler" :show-file-list="true" v-bind="conf"></upload-item>
    </template>
    <template v-else>
      <!-- 普通模式 -->
      <upload-item :meta="innerMeta" v-model="nativeValue" mode="default"
                   :show-file-list="true" v-bind="conf"></upload-item>
    </template>
  </div>
</template>

<script>
import Meta from '../../mixins/meta'
import DefaultMeta from '../ui-conf'
import UploadItem from "../../filebox/src/UploadItem";
import conver from "./conver";
import reverse from "./reverse";
import Val from "../../mixins/value";

export default {
  mixins: [Meta(DefaultMeta), Val(conver, reverse)],
  components: {UploadItem},
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
  .upload-item {
    display: block;
  }
}
</style>
