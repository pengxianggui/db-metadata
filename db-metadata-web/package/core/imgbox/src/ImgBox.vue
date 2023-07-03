<template>
  <div class="img-box">
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
import DefaultMeta from '../ui-conf'
import utils from "../../../utils";
import upload from '../../mixins/upload'

export default {
  mixins: [Meta(DefaultMeta), upload],
  name: 'ImgBox',
  label: '图片上传框',
  methods: {
    handleBeforeUpload(file) {
      const fn = utils.assertEmpty(this.$attrs['before-upload'], (f) => {
        let isImage = utils.isImageFile(f);
        if (!isImage) {
          this.$message.warning('只支持图片文件！');
        }
        return isImage;
      })
      fn.call(this, file)
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
