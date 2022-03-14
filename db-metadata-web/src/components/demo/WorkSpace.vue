<template>
  <div style="padding: 50px">
<!--    <single-grid-tmpl :config="singleGridConfig"></single-grid-tmpl>-->
<!--    <master-slave-table-tmpl :config="masterSlaveGridConfig"></master-slave-table-tmpl>-->
    <!--    <tree-single-grid-tmpl :config="treeSingleGridConfig"></tree-single-grid-tmpl>-->

<!--        <single-grid-tmpl fc="meta_auth"></single-grid-tmpl>-->
    <master-slave-table-tmpl fc="meta_data"></master-slave-table-tmpl>
<!--        <tree-single-grid-tmpl fc="meta_router"></tree-single-grid-tmpl>-->
  </div>
</template>

<script>

export default {
  name: "WorkSpace",
  data() {
    return {
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
  },
  created() {
  }
}
</script>

<style scoped>
</style>
