<template>
  <ul class="container">
    <draggable v-model="value">
      <li v-for="(item, index) in value" :key="item.id">
        <div :class="{'red-border': item.error}" style="white-space: nowrap">
          <el-input v-model="item.key">
            <template #prepend><label>key</label></template>
          </el-input>
          <el-input v-model="item.value">
            <template #prepend><label>value</label></template>
          </el-input>
          <el-button circle icon="el-icon-minus" @click="minus(item)"></el-button>
        </div>
      </li>
    </draggable>
    <li style="text-align: left;">
      <el-button circle icon="el-icon-plus" @click="plus"></el-button>
      <span v-if="errorTip">
                <span class="tip">&nbsp;规则: key值不能重复, 且key, value均不能为空.</span>
                <el-tooltip content="不符合规则的红色项将不被保存" placement="right">
                    <i class="el-icon-question"></i>
                </el-tooltip>
            </span>
    </li>
  </ul>
</template>

<script>
import utils from '../../../utils'
import draggable from 'vuedraggable'

export default {
  name: "OptionsInput",
  components: {
    draggable
  },
  props: {
    value: Array
  },
  methods: {
    plus() {
      this.value.push({
        id: Math.floor(Math.random() * 10000),
        key: null,
        value: null
      });
    },
    minus(item) {
      if (this.value.length === 0) return;
      let index = this.value.map(item => item.key).indexOf(item.key);
      this.value.splice(index, 1);
    }
  },
  computed: {
    errorTip() {
      let result
      utils.markNotRepeatEle(this.value, 'key', function (ele) {
      }, function (ele) {
        result = true
      });
      return result
    }
  }
}
</script>

<style scoped>
ul.container {
  list-style: none;
  margin: 0px;
}

ul.container li {
  margin: 3px 0;
}

ul.container .el-input {
  width: 200px;
}

.red-border {
  display: inline-block;
  border: 1px dotted #f56c6c;
  border-radius: 20px;
  padding: 2px;
}

.tip {
  font-size: 12px;
  color: #f56c6c;
}
</style>
