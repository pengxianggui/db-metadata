<template>
  <div class="img-box">
    <template v-if="seats.length > 0">
      <!-- seat模式 TODO 存在bug: 如果移除前面的图片，排后面的图片会被渲染到前面去。本质上是index没有跟具体的值强绑定 -->
      <upload-box class="upload-box" v-for="(seat, index) in seats" :key="seat + index"
                   :seat="seat" :meta="innerMeta" v-model="nativeValue[index]" mode="seat"
                   @input="changeHandler"
                   :before-upload="handleBeforeUpload"
                   :show-file-list="true"
                   v-bind="conf">
        <template #default="{seat, isView}">
          <div class="upload-btn">
            <i class="icon el-icon-plus"></i>
            <el-tooltip :content="'请上传' + seat" :disabled="seat.length <= 6">
              <i class="seat-name">请上传{{ seat | subStr(6) }}</i>
            </el-tooltip>
          </div>
        </template>
      </upload-box>
    </template>
    <template v-else>
      <!-- 普通模式 -->
      <upload-box :meta="innerMeta" v-model="nativeValue" mode="default"
                    :show-file-list="true" v-bind="conf">
          <div slot="default" class="upload-btn">
            <i class="icon el-icon-plus"></i>
          </div>
      </upload-box>
    </template>
  </div>
</template>
<script>
import Meta from '../../mixins/meta'
import conver from "./conver";
import reverse from "./reverse";
import DefaultMeta from '../ui-conf'
import Val from "../../mixins/value";
import utils from "../../../utils";

export default {
  mixins: [Meta(DefaultMeta), Val(conver, reverse)],
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
    },
    handleBeforeUpload(file) {
      const fn = utils.assertEmpty(this.$attrs['before-upload'], (f) => {
        let isImage = utils.isImageFile(f);
        if (!isImage) {
          this.$message.warning('只支持图片文件！');
        }
        return isImage;
      })
      fn.call(this, file)
    },
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
      const config = $reverseMerge(conf, $attrs)
      config.accept = 'image/*' // 固定上传时接受的类型为图片
      return config
    }
  }
}
</script>
<style lang="scss" scoped>
.img-box {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  font-size: 14px;

  .upload-box {
    flex: 1;
    display: inline-block;

    .upload-btn {
      height: 100%;
      line-height: 20px;
      padding: 10px;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      justify-content: space-evenly;

      & > .seat-name {
        overflow: hidden;
        font-size: 12px;
      }
    }
  }
}
</style>
