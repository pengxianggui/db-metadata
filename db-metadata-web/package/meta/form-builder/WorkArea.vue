<template>
  <div class="work-area">
    <form-view :ref="value.name" :meta="value" style="height: 100%; width:100%">
      <template #form-item>
        <nest-form-item-editor :columns="value.columns"
                               :active-item="activeItem"
                               @form-item-click="handleFormItemClick"
                               @form-item-delete="handleFormItemDelete"
                               @layout-item-delete="handleLayoutItemDelete"
                               @add="handleAdd"
                               @move="handleMove"
                               @end="handleEnd"
                               style="height: 100%">
          <div class="blank-tip">从左侧拖拽来添加表单项</div>
        </nest-form-item-editor>
      </template>
      <template #action><span></span></template>
    </form-view>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import FormView from "@/../package/view/formview/src/FormView";
import DropDownBox from "../../core/dropdownbox/src/DropDownBox";
import {isLayoutComp} from './relate/componentData'
import NestFormItemEditor from "./NestFormItemEditor"
import {refreshColumnsSort, isEmptyGridRow} from './formViewMetaParser'
import {utils} from "../../index";

export default {
  name: "WorkArea",
  inject: ['objectCode'],
  components: {
    DropDownBox,
    FormView,
    draggable,
    NestFormItemEditor
  },
  props: {
    value: Object,
    fieldCode: String,
    activeItem: Object
  },
  data() {
    return {}
  },
  methods: {
    isLayoutComps({component_name: componentName}) { // 判断是否容器组件
      return isLayoutComp(componentName)
    },
    handleLayoutItemDelete(columns, item, index, ev) {
      const {objectCode} = this
      if (objectCode && !isEmptyGridRow(item)) {
        this.$message.warning('当前处于编辑模式, 只允许删除空的栅格容器。当前栅格容器也许在其他表单状态下有域组件')
        return false;
      }
      columns.splice(index, 1)
    },
    handleFormItemDelete(columns, item, index, ev) {
      const {objectCode} = this
      const {component_name} = item

      if (!utils.isEmpty(objectCode) && !utils.isEmpty(component_name)) { // component_name为空说明是无效控件
        this.$message.warning('编辑实例配置时不允许移除控件')
        return false
      }
      columns.splice(index, 1)
    },
    // 新增
    handleAdd({to, from, item, clone, oldIndex, newIndex}) {
      // TODO 新增脱离了元对象的前提， 暂不支持
      // const {formMeta: {objectCode}} = this
      // if (objectCode) {
      //   this.$message.warning('编辑元对象时不允许新增控件')
      //   return false
      // }
      // return true
    },
    handleEnd(e) {
      // 重新计算排序值
      refreshColumnsSort(this.value.columns)
    },
    handleMove(e) {
      return true;
    },
    // 点击选中
    handleFormItemClick(column) {
      this.$emit('update:active-item', column)
    }
  }
}
</script>

<style scoped lang="scss">
.work-area {
  .form-item {
    background: white;
    position: relative;
    padding: 0;
    border: 1px dashed #DDDDDD;
    box-sizing: border-box;
    margin-bottom: 2px;
  }

  .form-item-active {
    border: 3px solid #409eff !important;

    .handle {
      position: absolute;
      font-size: 20px;
      color: #409EFF;
      cursor: move;
      z-index: 1;
    }

    .form-item-delete-btn {
      position: absolute;
      right: 0;
      bottom: 0;
      //z-index: 3;
    }
  }

  .blank-tip {
    height: 100%;
    line-height: 400px;
    text-align: center;
    box-sizing: border-box;
    color: #999999;
  }
}

</style>
