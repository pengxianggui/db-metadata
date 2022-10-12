<template>
  <div class="md_page-container">
    <el-collapse v-model="active">
      <el-collapse-item title="元数据备份/还原" name="1">
        <el-button type="primary" icon="el-icon-download" @click="exportBackup">导出元数据备份</el-button>
        <uploader-button url="/meta/restore" panel-title="备份还原，请上传导出的ZIP文件">
          <template #default="{click}">
            <el-button type="danger" plain icon="el-icon-upload2" @click="click">导入元数据还原</el-button>
          </template>
          <template #alert>
            <el-alert type="warning" show-icon :closable="false" :center="false">
              <div>注意! 导入的元数据将执行全量覆盖, 且数据无法找回。以下数据可能被覆盖:</div>
              <ul>
                <li>元对象和元字段</li>
                <li>功能配置</li>
                <li>组件全局配置</li>
                <li>组件实例配置</li>
                <li>动态菜单</li>
                <li>动态Profile菜单</li>
                <li>动态路由</li>
                <li>字典数据</li>
                <li>接口资源</li>
                <li>权限分组和权限数据</li>
              </ul>
              <div>具体覆盖范围，取决于你导入的ZIP文件中包含的范围。默认上述范围。</div>
              <div><b style="color: indianred">导入前, 建议先在当前应用中执行【导出元数据备份】</b></div>
            </el-alert>
          </template>
        </uploader-button>
      </el-collapse-item>
      <el-collapse-item title="元数据重置" name="2" v-any-auths="['api:app-init']">
        <el-button type="danger" @click="init">
          <svg-icon value="reset"></svg-icon>
          <span>系统重置</span>
        </el-button>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import UploaderButton from "../../component/Uploader/UploaderButton";
import {isEmpty} from "../../utils/common";
import {restUrl} from "../../constant/url";

export default {
  name: "SysSetting",
  meta: {
    type: 'page',
    cn: '系统设置',
    buildIn: true // 内建：DbMeta提供
  },
  components: {UploaderButton},
  data() {
    return {
      active: ['1', '2']
    }
  },
  methods: {
    exportBackup() {
      this.$confirm(`
          <p>此操作将默认导出以下数据: </p>
          <ul>
            <li>元对象和元字段</li>
            <li>功能配置</li>
            <li>组件全局配置</li>
            <li>组件实例配置</li>
            <li>动态菜单</li>
            <li>动态Profile菜单</li>
            <li>动态路由</li>
            <li>字典数据</li>
            <li>接口资源</li>
            <li>权限分组和权限数据</li>
          </ul>
          <p>此功能一般有两个用法:</p>
          <ul>
            <li>将导出的zip包作为备份，后面需要还原时执行导入。</li>
            <li>导出的zip文件作为跨环境交付物。例如: 在开发环境利用dbmeta特性在线开发，然后导出全量数据，测试部署或线上部署时，再于测试或线上环境执行导入还原。</li>
          </ul>`,
          '注意', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            dangerouslyUseHTMLString: true
          }).then(() => {
        this.$axios.post(restUrl.META_DATA_BACKUP, {}, {responseType: 'blob'}).then(res => {
          const {data, headers} = res
          const fileName = headers['content-disposition'].replace(/\w+;filename="(.*)"/, '$1')
          // 此处当返回json文件时需要先对data进行JSON.stringify处理，其他类型文件不用做处理
          //const blob = new Blob([JSON.stringify(data)], ...)
          const blob = new Blob([data], {type: headers['content-type']})
          let dom = document.createElement('a')
          let url = window.URL.createObjectURL(blob)
          dom.href = url
          dom.download = decodeURI(fileName)
          dom.style.display = 'none'
          document.body.appendChild(dom)
          dom.click()
          dom.parentNode.removeChild(dom)
          window.URL.revokeObjectURL(url)
        }).catch((err) => {
        })
      })
    },
    init: function () {
      this.$prompt(`
            <p>此操作将重置那些【系统内置】的数据，以下数据将被重置: </p>
            <ul>
                <li>系统内置的元对象和元字段</li>
                <li>系统内置的功能</li>
                <li>系统内置的组件全局配置</li>
                <li>系统内置的组件实例配置</li>
                <li>系统内置的动态菜单和动态Profile菜单</li>
                <li>系统内置的动态路由</li>
                <li>系统内置的接口资源</li>
                <li>系统内置的权限和角色</li>
                <li>系统内置的字典</li>
            </ul>
            <p>注意: </p>
            <ol>
                <li>此功能应用场景是当你误操作把内置数据更新坏了，而你又忘了怎么更新回来。或数据库里误删了这些内置数据</li>
                <li>系统内置是dbmeta首次启动时自动初始化的数据，而启动后，人为页面操作生成的这些数据不是系统内置的，因此这些数据不受影响。<b>但仍然建议你执行备份操作！</b></li>
            </ol>
          `,
          '确定要重置?',
          {
            type: 'warning',
            inputType: 'password',
            inputPlaceholder: '请输入口令..',
            dangerouslyUseHTMLString: true,
            inputValidator: function (val) {
              return !isEmpty(val) ? true : '请输入口令(参见系统配置)';
            }
          }).then(data => {

        const loadingInstance = this.$loading({
          lock: true,
          text: '元数据重置中, 请勿刷新页面, 并耐心等待..',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })

        this.$axios.get('/db/init?token=' + data.value).then(({msg = '初始化成功'}) => {
          this.$message.success(msg);
          this.$router.go(0);
        }).catch(({msg = '发生错误'}) => {
          console.error(msg)
        }).finally(() => {
          loadingInstance.close()
        })
      }).catch(() => {
      })
    },
  }
}
</script>

<style scoped lang="scss">
.md_page-container {
  padding: 5px 50px !important;
  background-color: #fff;
}
</style>
