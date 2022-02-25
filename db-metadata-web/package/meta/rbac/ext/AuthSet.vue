<template>
  <div>
    <el-tabs v-model="activeTab">
      <el-tab-pane v-for="(groupMap, type, index) in authMap" :label="type" :name="'' + index">

        <el-checkbox :indeterminate="model[type].isIndeterminate" v-model="model[type].checkAll"
                     @change="handleCheckAllChange($event, type)">全选
        </el-checkbox>
        <div style="margin: 15px 0;"></div>

        <el-checkbox-group v-model="model[type].value" @change="handleCheckedCitiesChange($event, type)">
          <div v-for="(v, k) in groupMap">
            <h4 class="group-title">【{{ k }}】</h4>
            <div class="group-options">
              <el-checkbox class="role-item" v-for="r in v" :label="r.id" :key="r.id">
                <span>{{ r.name }}</span>&nbsp;
                <el-tooltip :content="r.remark" placement="right" v-if="r.remark">
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </el-checkbox>
            </div>
          </div>
        </el-checkbox-group>
      </el-tab-pane>
    </el-tabs>

  </div>
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
      activeTab: '0',
      options: [],
      model: {},
      authMap: {}
    }
  },
  methods: {
    handleCheckAllChange(val, type) {
      let typeModel = this.authMap[type];
      if (val) {
        Object.keys(typeModel).forEach(group => {
          let authIds = typeModel[group].map(a => a.id)
          this.model[type].value.push(...authIds)
        })
      } else {
        this.model[type].value = []
      }
      this.model[type].isIndeterminate = false
    },
    handleCheckedCitiesChange(value, type) {
      let checkedCount = value.length;
      let sumLength = Object.keys(this.authMap[type]).map(k => this.authMap[type][k].length).reduce((length1, length2) => length1 + length2)

      this.model[type].checkAll = checkedCount === sumLength
      this.model[type].isIndeterminate = checkedCount > 0 && checkedCount < sumLength;
    },
    doBind() {
      const {model} = this
      const value = []
      Object.keys(model).forEach(type => {
        value.push(...model[type].value)
      })
      return this.$axios.safePost(utils.compile(restUrl.AUTH_SET_FOR_ROLE, {roleId: this.roleId}), {
        authId: value.join(',')
      })
    },
    allAuth() {
      return new Promise((resolve, reject) => {
        this.$axios.safeGet(restUrl.AUTH_LIST).then(({data: auths}) => {
          let map = utils.group(auths, 'type', '其它');
          for (let key in map) {
            map[key] = utils.group(map[key], 'group', '默认')
          }

          // init model
          Object.keys(map).forEach(type => {
            this.$set(this.model, type, {
              checkAll: false,
              isIndeterminate: true,
              value: []
            })
          })

          resolve({map, auths})
        }).catch(err => {
          reject(err)
        })
      });
    },
    getAuthOfRole(roleId, list = []) {
      this.$axios.safeGet(utils.compile(restUrl.AUTH_LIST_FOR_ROLE, {roleId: roleId}))
          .then(({data: auths = []}) => {
            let hadAuthCodes = auths.map(a => a.id)
            list.forEach(a => {
              if (hadAuthCodes.indexOf(a.id) > -1) {
                this.model[a.type].value.push(a.id)
              }
            })

            Object.keys(this.model).forEach(type => {
              this.handleCheckedCitiesChange(this.model[type].value, type)
            })
          })
    }
  },
  mounted() {
    this.allAuth().then(({map, auths: list}) => {
      this.authMap = map
      this.getAuthOfRole(this.roleId, list)
    });
  },
  computed: {
    // authMap() {
    //   let map = utils.group(this.options, 'type', '其它');
    //   for (let key in map) {
    //     map[key] = utils.group(map[key], 'group', '默认')
    //   }
    //
    //   Object.keys(map).forEach(type => {
    //     this.$set(this.model, type, {
    //       checkAll: false,
    //       isIndeterminate: true,
    //       value: []
    //     })
    //   })
    //
    //   return map
    // },
    // groups() {
    //   return utils.group(this.options, 'group')
    // }
  }
}
</script>

<style scoped lang="scss">
.role-item {
  width: 150px;
  height: 40px;
}

.group-title {
  font-size: 15px;
}

.group-options {
  padding-left: 20px;
  border-left: 3px solid #3f9eff;
}
</style>
