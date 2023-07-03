<template>
  <div class="file-box">
    <template v-if="isSeat">
      <upload-box class="upload-box" v-for="(seat, index) in seats" :key="seat + index"
                   :seat="seat" :meta="innerMeta" v-model="nativeValue[getIndexOfNativeValueBySeat(seat, index)]"
                   mode="seat"
                   @input="changeHandler"
                   :before-upload="handleBeforeUpload"
                   :show-file-list="true"
                   v-bind="conf">
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
import utils from "../../../utils";
import upload from '../../mixins/upload'

export default {
  mixins: [Meta(DefaultMeta), upload],
  name: "FileBox",
  label: "文件上传框",
  methods: {
    handleBeforeUpload(file) {
      const fn = utils.assertEmpty(this.$attrs['before-upload'], (/*f*/) => {
        return true
      })
      fn.call(this, file)
    },
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
