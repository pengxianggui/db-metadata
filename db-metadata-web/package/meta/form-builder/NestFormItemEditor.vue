<template>
  <draggable tag="div" :list="columns" :group="{name: 'form'}" :animation="200" handle=".handle"
             @add="handleAdd" :move="handleMove" @end="handleEnd">
    <slot v-if="columns.length === 0"></slot>

    <template v-else>
      <div v-for="(item, i) in columns" :key="i" class="cycle-item" @click="formItemClick(item, $event)">
        <!-- 布局组件-->
        <template v-if="isLayoutComp(item)">
          <div class="layout-item" :class="{'active': activeItem.name === item.name}" v-if="isLayoutCompShow(item)">
            <i class="el-icon-s-grid handle"></i>
            <component :is="item.component_name" :meta="item" :show-line="true"
                       style="min-height: 80px">
              <template v-for="(c, j) in item.conf.span" v-slot:[j]>
                <nest-form-item-editor :columns="fillColumns(item.columns, j)"
                                       :active-item="activeItem"
                                       @form-item-click="formItemClick"
                                       @add="handleAdd"
                                       @form-item-delete="deleteFormItem"
                                       @layout-item-delete="deleteLayoutItem"
                                       style="height: 100%"></nest-form-item-editor>
              </template>
            </component>
            <el-button class="delete-btn" size="mini" icon="el-icon-delete"
                       @click="deleteLayoutItem(columns, item, i, $event)"></el-button>
          </div>
        </template>

        <!-- 表单组件 TODO 防止编辑模式可以点击控件触发控件行为 -->
        <template v-else>
          <div :class="{'active': activeItem.name === item.name}" class="form-item" v-if="item.hidden !== true">
            <i class="el-icon-s-grid handle" @click="formItemClick(item, $event)"></i>
            <el-form-item :label="item.label || item.name" :prop="item.name"
                          v-if="!item.hasOwnProperty('showable') || item.showable">
              <component :is="item.component_name" :meta="item"></component>
            </el-form-item>
            <el-button class="delete-btn" size="mini" icon="el-icon-delete"
                       @click="deleteFormItem(columns, item, i, $event)" v-if="!objectCode"></el-button>
          </div>
        </template>

      </div>
    </template>
  </draggable>
</template>

<script>
import {isLayoutComp} from "./relate/componentData";
import {isLayoutCompShow} from "./formViewMetaParser";
import draggable from "vuedraggable";
import {isUndefined} from "../../utils/common";

export default {
  name: "NestFormItemEditor",
  inject: ['objectCode'],
  components: {
    draggable
  },
  props: {
    columns: { // 所有域配置数组
      type: Array,
      required: true
    },
    activeItem: Object // 选中激活的域配置
  },
  methods: {
    fillColumns(item, j) {
      if (isUndefined(item[j])) {
        this.$set(item, j, [])
      }
      return item[j]
    },
    isLayoutComp(item) {
      const {component_name: componentName} = item
      return isLayoutComp(componentName)
    },
    isLayoutCompShow(item) {
      return isLayoutCompShow(item)
    },
    formItemClick(item, ev) {
      if (ev) ev.stopPropagation()
      this.$emit('form-item-click', item)
    },
    deleteFormItem(columns, item, index, ev) {
      if (ev) ev.stopPropagation()
      this.$emit('form-item-delete', columns, item, index, ev)
    },
    deleteLayoutItem(columns, item, index, ev) {
      if (ev) ev.stopPropagation()
      this.$emit('layout-item-delete', columns, item, index, ev)
    },
    handleAdd(row) {
      this.$emit('add', row)
    },
    handleMove(e) {
      this.$emit('move', e)
    },
    handleEnd(e) {
      this.$emit('end', e)
    }
  }
}
</script>

<style scoped lang="scss">
.cycle-item {
  margin-bottom: 5px;
}

.form-item, .layout-item {
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
    //z-index: 999;
    z-index: 2;
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
    z-index: 1;
  }
}


</style>
