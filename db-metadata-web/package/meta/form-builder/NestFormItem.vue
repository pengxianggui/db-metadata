<template>
  <draggable tag="div" :list="columns" :group="{name: 'form'}" :animation="200" handle=".handle" @add="handleAdd">
    <slot v-if="columns.length === 0"></slot>

    <template v-else>
      <div v-for="(item, i) in columns" :key="i" class="cycle-item" @click="cycleItemClick(item, $event)">
        <!-- 布局组件-->
        <template v-if="isLayoutComp(item)">
          <div class="layout-item" :class="{'active': active === item.name}">
            <i class="el-icon-s-grid handle"></i>
            <component :is="item.component_name" :meta="item" :show-line="true"
                       style="min-height: 80px">
              <template v-for="(c, j) in item.conf.span" v-slot:[j]>
                <nest-form-item :columns="item.columns[j]" :active.sync="active"
                                @formItemClick="cycleItemClick"
                                @add="handleAdd"
                                style="height: 100%"></nest-form-item>
              </template>
            </component>
            <el-button class="delete-btn" size="mini" icon="el-icon-delete" @click="deleteLayoutItem(columns, item, i, $event)"></el-button>
          </div>
        </template>

        <!-- 表单组件-->
        <template v-else>
          <div :class="{'active': active === item.name}" class="form-item">
            <i class="el-icon-s-grid handle" @click="$emit('update:active', item.name)"></i>
            <el-form-item :label="item.label || item.name" :prop="item.name">
              <component :is="item.component_name" :meta="item"></component>
            </el-form-item>
            <el-button class="delete-btn" size="mini" icon="el-icon-delete" @click="deleteFormItem(columns, item, i, $event)"></el-button>
          </div>
        </template>

      </div>
    </template>
  </draggable>
</template>

<script>
import {isLayoutComp} from "./relate/componentData";
import draggable from "vuedraggable";

export default {
  name: "NestFormItem",
  components: {
    draggable
  },
  props: {
    columns: {
      type: Array,
      required: true
    },
    active: {
      type: String
    }
  },
  methods: {
    isLayoutComp(column) {
      const {component_name: componentName} = column
      return isLayoutComp(componentName)
    },
    cycleItemClick(item, ev) {
      if(ev) ev.stopPropagation()
      this.$emit('formItemClick', item)
    },
    deleteFormItem(columns, item, index, ev) {
      if (ev) ev.stopPropagation()
      this.$emit('formItemDelete', columns, item, index, ev)
    },
    deleteLayoutItem(columns, item, index, ev) {
      if (ev) ev.stopPropagation()
      this.$emit('layoutItemDelete', columns, item, index, ev)
    },
    handleAdd(row) {
      this.$emit('add', row)
    }
  }
}
</script>

<style scoped lang="scss">
.cycle-item {
  margin-bottom: 5px;
}
.form-item,.layout-item {
  position: relative;
  background: white;
  //position: relative;
  z-index: 1;
  padding: 5px;
  border: 1px dashed #DDDDDD;
  box-sizing: border-box;

  .handle {
    display: none;
  }

  .delete-btn {
    display: none;
  }
}

$choseFormItemThemeColor: #409eff;
//$choseThemeColor: #F56C6C;
.form-item.active {
  border: 3px solid $choseFormItemThemeColor !important;

  .handle {
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    font-size: 20px;
    color: $choseFormItemThemeColor;
    cursor: move;
    z-index: 999;
  }

  .delete-btn {
    background-color: #F56C6C;
    border-color: #F56C6C;
    color: white;
    font-size: 15px;
    display: block;
    position: absolute;
    bottom: 0;
    right: 0;
    margin-right: -1px;
    margin-bottom: -1px;
    border-radius: 0;
    width: 35px;
    height: 25px;
    text-align: center;
    padding: 0;
  }
}

$choseLayoutItemThemeColor: #e6a23c;
.layout-item.active {
  border: 3px solid $choseLayoutItemThemeColor !important;

  & > .handle {
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    font-size: 20px;
    color: $choseFormItemThemeColor;
    cursor: move;
    z-index: 999;
  }

  & > .delete-btn {
    background-color: #F56C6C;
    border-color: #F56C6C;
    color: white;
    font-size: 15px;
    display: block;
    position: absolute;
    bottom: 0;
    right: 0;
    margin-right: -1px;
    margin-bottom: -1px;
    border-radius: 0;
    width: 35px;
    height: 25px;
    text-align: center;
    padding: 0;
  }
}


</style>