<template>
  <div class="uploader">
    <slot v-bind:click="openPanel">
      <el-button size="mini" type="success" plain icon="el-icon-upload" @click="openPanel">上传</el-button>
    </slot>
    <el-dialog :visible.sync="visible" :title="panelTitle" width="500px">
      <slot name="message"></slot>
      <upload-panel :url="url" :accept="accept" :limit="limit"
                    @success="visible = false" @fail="$emit('fail')">
        <template #alert><slot name="alert"></slot></template>
      </upload-panel>

      <template #footer>
        <div class="template-download-tip">
          <div class="footer-start">
            <slot name="footer-start"></slot>
          </div>
          <div class="footer-end">
            <slot name="footer-end"></slot>
          </div>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import UploadPanel from "./UploadPanel";

export default {
  name: "UploaderButton",
  components: {UploadPanel},
  props: {
    url: {
      type: String,
      default: () => "/file/upload"
    },
    accept: {
      type: String,
      default: () => "*/*"
    },
    limit: {
      type: Number,
      default: () => 1
    },
    panelTitle: {
      type: String,
      default: () => '请上传文件'
    }
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    openPanel() {
      this.visible = true
    }
  }
}
</script>

<style scoped lang="scss">
.uploader {
  display: inline-block;
  margin: 0 10px;

  .template-download-tip {
    font-size: 13px;
    font-family: Microsoft YaHei !important;
    height: 20px;
    line-height: 20px;
    display: flex;
    justify-content: space-between;
  }

  &::v-deep{
    .el-dialog__body {
      padding: 10px 20px;
    }
  }
}
</style>
