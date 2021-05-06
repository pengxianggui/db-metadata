<template>
  <div class="container">
    <div class="opr-box">
      <slot name="opr-box"></slot>
    </div>
    <div class="work-area">
      <form-view :ref="formMeta.name" :meta="formMeta" style="height: 100%; width:100%">
        <template #form-item>
          <nest-form-item-editor :columns="formMeta.columns" :active.sync="selectItemName"
                                 @formItemClick="handleFormItemClick"
                                 @formItemDelete="handleFormItemDelete"
                                 @layoutItemDelete="handleLayoutItemDelete"
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
  </div>
</template>

<script>
import utils from '../../utils'
import draggable from 'vuedraggable'
import FormView from "../../core/formview/src/FormView";
import DropDownBox from "../../core/dropdownbox/src/DropDownBox";
import {isLayoutComp} from './relate/componentData'
import NestFormItemEditor from "./NestFormItemEditor"
import {refreshColumnsSort} from './formViewMetaParser'

export default {
  name: "WorkArea",
  components: {
    DropDownBox,
    FormView,
    draggable,
    NestFormItemEditor
  },
  props: {
    value: Object,
    activeItem: Object
  },
  data() {
    return {
      selectItemName: null
    }
  },
  methods: {
    isLayoutComps({component_name: componentName}) { // 判断是否容器组件
      return isLayoutComp(componentName)
    },
    handleLayoutItemDelete(columns, item, index, ev) {
      const {formMeta: {objectCode}} = this
      if (objectCode) {
        this.$message.warning('编辑元对象时不允许移除控件')
        return false;
      }
      columns.splice(index, 1)
    },
    handleFormItemDelete(columns, item, index, ev) {
      const {formMeta: {objectCode}} = this
      if (objectCode) {
        this.$message.warning('编辑元对象时不允许移除控件')
        return false
      }
      columns.splice(index, 1)
    },
    // 新增
    handleAdd({to, from, item, clone, oldIndex, newIndex}) {
      // const {formMeta: {objectCode}} = this
      // if (objectCode) {
      //   this.$message.warning('编辑元对象时不允许新增控件')
      //   return false
      // }
      // return true
    },
    handleEnd(e) {
      // 重新计算排序值
      refreshColumnsSort(this.formMeta.columns)
    },
    handleMove(e) {
    },
    // 点击选中
    handleFormItemClick(column) {
      const {name} = column
      this.selectItemName = name

      this.$emit('update:activeItem', column)
    }
  },
  computed: {
    formMeta() {
      let formMeta = this.value;
      if (!utils.isArray(formMeta.columns)) {
        this.$set(formMeta, 'columns', []);
      }
      return formMeta;
    },
  }
}
</script>

<style scoped lang="scss">
.container {
  height: 100%;
  display: flex;
  flex-direction: column;
  //border: 10px solid #dddddd;
  //padding: 10px;
}

.opr-box {
  display: flex;
  margin-bottom: 5px;
  align-items: center;
  justify-content: space-between;
}

.work-area {
  flex: 1;
  overflow: auto;
  position: relative;
  border: 5px solid #dddddd;
  padding: 5px;

  .form-item {
    background: white;
    position: relative;
    z-index: 1;
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
      z-index: 999;
    }

    .form-item-delete-btn {
      position: absolute;
      right: 0;
      bottom: 0;
      z-index: 3;
    }
  }
}

.blank-tip {
  height: 100%;
  line-height: 400px;
  text-align: center;
  box-sizing: border-box;
  color: #999999;
}


/*!* 遮挡区(遮挡住) *!*/
//.form-item::after {
//  content: " ";
//  display: block;
//  left: 0;
//  top: 0;
//  right: 0;
//  bottom: 0;
//  position: absolute;
//  z-index: 2;
//}

</style>
