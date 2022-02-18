<template>
  <div>
    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll"
                 @change="handleCheckAllChange">全选
    </el-checkbox>
    <div style="margin: 15px 0;"></div>

    <el-checkbox-group v-model="value" @change="handleCheckedCitiesChange">
      <el-checkbox class="role-item" v-for="r in options" :label="r.id" :key="r.id">{{ r.name }}</el-checkbox>
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
      this.checkAll = checkedCount === this.value.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.value.length;
    },
    doBindRole() {
      return this.$axios.safePost(utils.compile(restUrl.ROLE_SET_FOR_USER, {userId: this.userId}), {
        roleId: this.value.join(',')
      })
    }
  },
  beforeCreate() {
    console.log('beforeCreate')
    this.value = []
  },
  beforeDestroy() {
    console.log('beforeDestroy')
  },
  mounted() {
    this.$axios.safeGet(restUrl.ROLE_LIST).then(({data: roles}) => {
      this.options = roles
    });
    this.$axios.safeGet(utils.compile(restUrl.ROLE_LIST_FOR_USER, {userId: this.userId}))
        .then(({data: roles}) => {
          this.value = roles.map(r => r.id)
        })
  }
}
</script>

<style scoped lang="scss">
.role-item {
  width: 150px;
  height: 40px;
}
</style>
