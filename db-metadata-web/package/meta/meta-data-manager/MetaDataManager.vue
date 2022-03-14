<template>
  <div class="page-container">
    <master-slave-table-tmpl :fc="fc">
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
import {restUrl, routeUrl} from '@/../package/constant/url';
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
            }).catch(({msg = '同步发生错误'}) => {
          console.error(msg)
        })
      })
    },
    jumpToConf(objectCode) {
      // TODO 2.2 改为去创建功能

      let title = '创建成功，是否前往配置界面对' + objectCode + '进行UI配置?';

      let url = this.$compile(routeUrl.R_INSTANCE_CONF_EDIT, {
        componentCode: 'TableView',
        objectCode: objectCode
      });
      this.$confirm(title, '提示', {
        confirmButtonText: '去配置',
        cancelButtonText: '下次再说',
        type: 'success',
      }).then(() => {
        this.$router.push(url);
      }).catch(() => {
        const h = this.$createElement;

        this.$notify.info({
          title: '消息',
          message: h('span', {}, [
            h('span', {}, '如不进行UI配置, 此元对象相关视图可能出现异常.'),
            h('a', {
              attrs: {
                href: '#' + url
              }
            }, '去配置')
          ])
        });
      });
    },
    handlerMetaImport(formModel) {
      const {$refs, $axios, metaImportFormMeta, object: {objectCode}} = this;
      $axios.post(metaImportFormMeta.action, formModel).then(({msg = '元对象导入成功'}) => {
        this.$message.success(msg);
        this.metaImportFormVisible = false;
        // refresh master
        $refs[objectCode].getData();
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
