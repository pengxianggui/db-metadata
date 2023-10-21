<template>
  <div class="md_page-container">
    <master-slave-table-tmpl :fc="fc" :ref="fc">
      <template #add-btn="{conf}">
        <el-button v-bind="conf.conf" type="primary" @click="toAddMetaObject" icon="el-icon-plus">创建元对象</el-button>
      </template>

      <template #meta_field_add-btn="{conf, activeMData}">
        <el-button v-bind="conf.conf" @click="syncIncrementHandler(activeMData)" icon="el-icon-refresh">同步增量字段</el-button>
      </template>
    </master-slave-table-tmpl>

    <dialog-box :visible.sync="metaImportFormVisible" title="创建元对象">
      <meta-import :meta="metaImportFormMeta" @cancel="metaImportFormVisible=false"
                   @submit="handlerMetaImport"></meta-import>
      <template #footer><span></span></template>
    </dialog-box>
  </div>
</template>

<script>
import utils from '@/../package/utils'
import {restUrl} from '@/../package/constant/url';
import MetaImport from './ext/MetaImport'

export default {
  name: "MetaDataManager",
  components: {
    MetaImport
  },
  data() {
    return {
      fc: 'meta_data',

      metaImportFormVisible: false,
      metaImportFormMeta: {}
    }
  },
  methods: {
    syncIncrementHandler(activeMData) {
      if (utils.isEmpty(activeMData)) {
        this.$message.warning('请先选择一条主表记录');
        return;
      }
      const {code} = activeMData
      this.$confirm('只会同步新增的表字段', `确定同步元对象：${code}?`, {
        confirmButtonText: '是的',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.get(this.$compile(restUrl.META_FIELD_SYNC, {objectCode: code}))
            .then(({msg = '同步成功'}) => {
              this.$message.success(msg)
              this.$refs[this.fc].refresh()
            }).catch(({msg = '同步发生错误'}) => {
          console.error(msg)
        })
      })
    },
    handlerMetaImport(formModel) {
      const {metaImportFormMeta, fc} = this;
      this.$axios.post(metaImportFormMeta.action, formModel).then(({msg = '元对象导入成功'}) => {
        this.$message.success(msg);
        this.metaImportFormVisible = false;
        // refresh master
        this.$refs[fc].refresh()
      }).catch(({msg = '元对象导入失败'}) => {
        console.error(msg)
      })
    },
    toAddMetaObject() {
      this.$axios.get(restUrl.META_OBJECT_TO_ADD).then(resp => {
        this.metaImportFormMeta = resp.data
        this.metaImportFormVisible = true
      }).catch(({msg = '发生错误'}) => {
        console.error(msg)
      });
    }
  }
}
</script>

<style scoped>
</style>
