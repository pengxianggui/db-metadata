<template>
  <div style="padding: 50px">

<!--    <tree-view ref="tree" :meta="treeMeta" @node-click="handleNodeClick"></tree-view>-->

<!--    <el-button @click="openDialogJs">编程式弹窗(传meta)</el-button>-->
<!--    <el-button @click="openDialogJs1">编程式弹窗(传ic)</el-button>-->
<!--    <el-button @click="openDialogHtml">标签式弹窗</el-button>-->

<!--    <el-dialog :visible.sync="visible">-->
<!--      <form-view ic="meta_router.FormView" type="add"></form-view>-->
<!--    </el-dialog>-->

    <!--    <single-grid-tmpl :config="singleGridConfig"></single-grid-tmpl>-->
    <!--    <master-slave-table-tmpl :config="masterSlaveGridConfig"></master-slave-table-tmpl>-->
    <!--    <tree-single-grid-tmpl :config="treeSingleGridConfig"></tree-single-grid-tmpl>-->

    <!--        <single-grid-tmpl fc="meta_auth"></single-grid-tmpl>-->
    <!--    <master-slave-table-tmpl fc="meta_data"></master-slave-table-tmpl>-->
    <!--        <tree-single-grid-tmpl fc="meta_router"></tree-single-grid-tmpl>-->

<!--    <form-view ic="meta_user.FormView" form-type="add"></form-view>-->
<!--    <form-view ic="t_employee.FormView" form-type="update" primary-kv="1" style="width: 100%"></form-view>-->

    <!--    <el-form ref="form" :model="model">-->
    <!--      <el-form-item prop="config" label="配置" required>-->
    <!--        <json-box v-model="model.config"></json-box>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item>-->
    <!--        <el-button @click="valid">验证</el-button>-->
    <!--      </el-form-item>-->
    <!--    </el-form>-->

<!--    <form-view :meta="formMeta" :model="formModel" @submit="submit"></form-view>-->
  </div>
</template>

<script>
import {getAddFormMeta} from "../../../package/utils/rest";
import AuthSet from "../../../package/page/role/ext/AuthSet";

export default {
  name: "WorkSpace",
  components: {AuthSet},
  data() {
    return {
      visible: false,
      treeMeta: {
        "objectPrimaryKey": "id",
        "objectCode": "meta_auth_module",
        "label": "权限模块",
        "data_url": "/table/tree?objectCode=meta_auth_module",
        "operation-bar": {
          "show": false
        },
        "editable": false,
        "name": "meta_auth_moduleTreeView",
        "conf": {
          "check-strictly": true,
          "accordion": false,
          "indent": 16,
          "show-checkbox": true,
          "default-checked-keys": [],
          "default-expand-all": false,
          "default-expanded-keys": [],
          "node-key": "id",
          "props": {"children": "children", "label": "name"},
          "expand-on-click-node": false,
          "icon-class": "el-icon-caret-right",
          "draggable": false,
          "highlight-current": true,
          "check-on-click-node": false
        }
      },
      singleGridConfig: {
        config: {
          objectCode: 'meta_auth'
        },
        instanceCodes: {
          SearchView: 'meta_auth.SearchView',
          TableView: 'meta_auth.TableView',
          FormView: 'meta_auth.FormView'
        }
      },
      masterSlaveGridConfig: {
        "master": {
          "config": {
            "objectCode": 'meta_object',
            "primaryKey": 'code',
          },
          "instanceCodes": {
            "SearchView": 'meta_object.SearchView',
            "TableView": 'meta_object.TableView',
            "FormView": 'meta_object.FormView'
          }
        },
        "slaves": [
          {
            "config": {
              "objectCode": 'meta_field',
              "foreignPrimaryKey": 'object_code',
            },
            "instanceCodes": {
              "SearchView": 'meta_field.SearchView',
              "TableView": 'meta_field.TableView',
              "FormView": 'meta_field.FormView'
            }
          }
        ]
      },
      treeSingleGridConfig: {
        "config": {
          "objectCode": "meta_menu",
          "idKey": "id",
          "pidKey": "pid",
          "rootIdentify": null,
          "label": "name",
          "isSync": false,
        },
        "instanceCodes": {
          "SearchView": "meta_menu.SearchView",
          "TableTreeView": "meta_menu.TableTreeView",
          "FormView": "meta_menu.FormView"
        }
      },

      formModel: {
        config: {}
      },
      formMeta: {}
    }
  },
  computed: {},
  methods: {
    handleNodeClick(row, node, event) {
    },
    openDialogJs() {
      getAddFormMeta(this.$axios, 'meta_router.FormView').then(resp => {
        this.$dialog(resp.data, {}, {
          title: '新增'
        })
      })
    },
    openDialogJs1() {
      this.$dialog({ic: 'meta_router.FormView', formType: 'view', primaryKv: '698273579418128384'}, {}, {
        title: '查看'
      })
      // this.$dialog({ic: 'meta_router.FormView', formType: 'update', primaryKv: '698273579418128384'}, {}, {
      //   title: '更新'
      // })
    },
    openDialogHtml() {
      this.visible = true
    },
    submit(model) {
      this.$message.info('验证通过了' + JSON.stringify(model))
    }
  },
  created() {
    // setTimeout(() => {
      this.formMeta = {
        component_name: 'FormView',
        columns: [
          {
            component_name: 'JsonBox',
            name: 'config',
            label: '配置',
            conf: {
              "rules": [{required: true, message: "必填字段", trigger: "blur"}]
            }
          }
        ]
      }
    // }, 0)
  }
}
</script>

<style scoped>
</style>
