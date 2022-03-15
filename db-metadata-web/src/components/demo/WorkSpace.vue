<template>
  <div style="padding: 50px">

    <el-button @click="openDialogJs">编程式弹窗(传meta)</el-button>
    <el-button @click="openDialogJs1">编程式弹窗(传ic)</el-button>
    <el-button @click="openDialogHtml">标签式弹窗</el-button>

    <el-dialog :visible.sync="visible">
      <form-view ic="meta_router.FormView" type="add"></form-view>
    </el-dialog>

    <!--    <single-grid-tmpl :config="singleGridConfig"></single-grid-tmpl>-->
    <!--    <master-slave-table-tmpl :config="masterSlaveGridConfig"></master-slave-table-tmpl>-->
    <!--    <tree-single-grid-tmpl :config="treeSingleGridConfig"></tree-single-grid-tmpl>-->

    <!--        <single-grid-tmpl fc="meta_auth"></single-grid-tmpl>-->
    <!--    <master-slave-table-tmpl fc="meta_data"></master-slave-table-tmpl>-->
    <!--        <tree-single-grid-tmpl fc="meta_router"></tree-single-grid-tmpl>-->
  </div>
</template>

<script>
import {getAddFormMeta} from "../../../package/utils/rest";

export default {
  name: "WorkSpace",
  data() {
    return {
      visible: false,
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
      }
    }
  },
  computed: {},
  methods: {
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
    }
  },
  created() {
  }
}
</script>

<style scoped>
</style>
