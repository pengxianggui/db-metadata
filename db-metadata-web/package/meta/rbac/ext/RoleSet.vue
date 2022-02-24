<template>
  <div>
    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll"
                 @change="handleCheckAllChange">全选
    </el-checkbox>
    <div style="margin: 15px 0;"></div>

    <el-checkbox-group v-model="value" @change="handleCheckedCitiesChange">
      <div v-for="(v, k) in groups">
        <h4 class="group-title">【{{ k }}】</h4>
        <div class="group-options">
          <el-checkbox class="role-item" v-for="r in v" :label="r.id" :key="r.id">{{ r.name }}</el-checkbox>
        </div>
      </div>
    </el-checkbox-group>
  </div>
</template>

<script>
import {restUrl} from "../../../constant/url";
import {utils} from "../../../index";

export default {
  name: "RoleSet",
  props: {
    userId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      value: [],
      options: [],

      checkAll: false,
      isIndeterminate: true
    }
  },
  methods: {
    handleCheckAllChange(val) {
      this.value = val ? this.options.map(r => r.id) : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.options.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.options.length;
    },
    doBind() {
      return this.$axios.safePost(utils.compile(restUrl.ROLE_SET_FOR_USER, {userId: this.userId}), {
        roleId: this.value.join(',')
      })
    }
  },
  mounted() {
    this.$axios.safeGet(restUrl.ROLE_LIST).then(({data: roles}) => {
      this.options = roles

      let url = utils.compile(restUrl.ROLE_LIST_FOR_USER, {userId: this.userId});
      this.$axios.safeGet(url).then(({data: roles}) => {
        this.value = roles.map(r => r.id)
        this.handleCheckedCitiesChange(this.value)
      })
    });
  },
  computed: {
    groups() {
      const {options} = this
      const groups = {}
      options.forEach(o => {
        let group = (utils.isEmpty(o.group) ? '默认' : o.group)
        if (groups.hasOwnProperty(group)) {
          groups[group].push(o)
        } else {
          groups[group] = [o]
        }
      })
      return groups
    }
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
