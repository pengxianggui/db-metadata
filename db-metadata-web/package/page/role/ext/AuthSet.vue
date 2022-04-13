<template>
  <row-grid :span="[8, 16]" class="auth-set">
    <template #0>
      <tree-view ref="tree" :meta="customMeta" @active-change="handleTreeClick" @check="handleCheck"></tree-view>
    </template>
    <template #1>
      <div class="right">
        <!-- 利用moduleId对所有权限进行过滤 -->
        <div class="opr">
          <el-input v-model="searchWord" placeholder="输入过滤" clearable></el-input>&nbsp;
          <el-button type="primary" @click="checkAll(filteredAuths)">全选</el-button>
          <el-button type="danger" @click="unCheckAll(filteredAuths)">取消全选</el-button>
        </div>
        <el-checkbox-group v-model="checkedAuthIds" class="options">
          <el-checkbox v-for="a in filteredAuths" :label="a.id" :key="a.id">
            <span>{{ a.name }}</span>&nbsp;
            <el-tooltip :content="a.remark" placement="right" v-if="a.remark">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </el-checkbox>
        </el-checkbox-group>
      </div>
    </template>
  </row-grid>
</template>

<script>
import {restUrl} from "../../../constant/url";
import {utils} from "../../../index";

export default {
  name: "AuthSet",
  props: {
    roleId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      customMeta: {
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
      moduleId: null,
      searchWord: null,
      allAuths: [],
      checkedAuthIds: []
    }
  },
  methods: {
    handleCheck(row, {checkedKeys}) {
      const {id: moduleId} = row
      const {allAuths} = this
      const checked = checkedKeys.indexOf(moduleId) > -1
      allAuths.filter(a => a.module_id === moduleId).forEach(a => {
        const index = this.checkedAuthIds.indexOf(a.id)
        if (checked && index === -1) { // 树节点被选中，则该模块下没勾选的都勾上
          this.checkedAuthIds.push(a.id)
        } else if (!checked && index > -1) { // 树节点取消选中，则该模块下勾选了的，都取消勾选
          this.checkedAuthIds.splice(index, 1)
        }
      })
    },
    handleTreeClick(row) {
      this.moduleId = row.id
    },
    doBind() {
      const {checkedAuthIds} = this
      return this.$axios.safePost(utils.compile(restUrl.AUTH_SET_FOR_ROLE, {roleId: this.roleId}), {
        authId: checkedAuthIds.join(',')
      })
    },
    checkAll(filteredAuths) {
      filteredAuths.forEach(a => {
        if (this.checkedAuthIds.indexOf(a.id) === -1) {
          this.checkedAuthIds.push(a.id)
        }
      })
    },
    unCheckAll(filteredAuths) {
      filteredAuths.forEach(a => {
        const index = this.checkedAuthIds.indexOf(a.id);
        if (index > -1) {
          this.checkedAuthIds.splice(index, 1)
        }
      })
    }
  },
  watch: {
    checkedModuleIds: function (newV, oldV = []) {
      if (newV.sort().toString() !== oldV.sort().toString()) {
        this.$refs['tree'].handleCleanChose()
        this.$nextTick(() => {
          this.$refs['tree'].setCheckedKeys(Array.from(new Set(newV)))
        })
      }
    }
  },
  created() {
    // 1. 获取所有权限
    this.$axios.safeGet(restUrl.AUTH_LIST).then(({data: auths}) => {
      this.allAuths = auths

      this.$axios.safeGet(utils.compile(restUrl.AUTH_LIST_FOR_ROLE, {roleId: this.roleId}))
          .then(({data: auths = []}) => {
            this.checkedAuthIds = auths.map(a => a.id)
          })
    })
  },
  computed: {
    filteredAuths() {
      const {allAuths, moduleId, searchWord} = this
      return allAuths.filter(a => {
        return (utils.isEmpty(moduleId) || a.module_id === moduleId)
            && (utils.isEmpty(searchWord) || a.name.indexOf(searchWord) > -1)
      }).sort((a1, a2) => {
        return utils.assertEmpty(a1.module_id, '').localeCompare(utils.assertEmpty(a2.module_id))
      });
    },
    checkedModuleIds() {
      const {checkedAuthIds, allAuths} = this
      const checkedModuleIds = allAuths.filter(a => checkedAuthIds.indexOf(a.id) > -1).map(a => a.module_id)
      return Array.from(new Set(checkedModuleIds));
    }
  }
}
</script>

<style scoped lang="scss">
.auth-set {
  .right {
    .opr {
      display: flex;
      flex-direction: row;
    }

    .options {
      overflow: auto;
      max-height: 400px;
      min-height: 200px;
    }

    .el-checkbox {
      display: block;
      margin: 15px;
    }
  }
}
</style>
