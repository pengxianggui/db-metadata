<template>
  <div class="img-box">
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
import conver from "./conver";
import reverse from "./reverse";
import DefaultMeta from '../ui-conf'
import UploadItem from "./UploadItem"
import Val from "../../mixins/value";

export default {
  mixins: [Meta(DefaultMeta), Val(conver, reverse)],
  components: {UploadItem},
  name: 'ImgBox',
  label: '图片上传框',
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
.img-box {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;

  .upload-item {
    flex: 1;
    display: inline-block;
  }
}
</style>
