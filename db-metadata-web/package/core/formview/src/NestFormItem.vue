<template>
  <div>
    <div v-for="(item, i) in columns" :key="i">
      <!-- 布局组件-->
      <template v-if="isLayoutComp(item)">
        <component :is="item.component_name" :meta="item" :show-line="false">
          <template v-for="(c, j) in item.conf.span" v-slot:[j]>
            <nest-form-item :columns="item.columns[j]" :model="model"></nest-form-item>
          </template>
        </component>
      </template>

      <!-- 表单组件-->
      <template v-else>
        <slot :name="'form-item-' + item.name" v-bind:column="item" v-bind:model="model">
          <el-form-item :label="item.label || item.name" :prop="item.name"
                        :rules="getItemRules(item)" v-if="!item.hasOwnProperty('showable') || item.showable">
            <component :is="item.component_name" :meta="item" v-model="model[item.name]"></component>
          </el-form-item>
        </slot>
      </template>

    </div>
  </div>
</template>

<script>
import {isLayoutComp} from "../../../meta/form-builder/relate/componentData";
import utils from "../../../utils";

export default {
  name: "NestFormItem",
  props: {
    columns: {
      type: Array,
      required: true
    },
    model: {
      type: Object,
      required: true
    }
  },
  methods: {
    isLayoutComp(column) {
      const {component_name: componentName} = column
      return isLayoutComp(componentName)
    },
    getItemRules(item) {
      const {conf: {rules} = {}} = {} = item
      return rules
    }
  }
}
</script>

<style scoped lang="scss">

//.form-item, .layout-item {
//  position: relative;
//  background: white;
//  //position: relative;
//  z-index: 1;
//  padding: 5px;
//  border: 1px dashed #DDDDDD;
//  box-sizing: border-box;
//
//  .handle {
//    display: none;
//  }
//
//  .delete-btn {
//    display: none;
//  }
//}
//
//$choseFormItemThemeColor: #409eff;
////$choseThemeColor: #F56C6C;
//.form-item.active {
//  border: 3px solid $choseFormItemThemeColor !important;
//
//  .handle {
//    display: block;
//    position: absolute;
//    top: 0;
//    left: 0;
//    font-size: 20px;
//    color: $choseFormItemThemeColor;
//    cursor: move;
//    z-index: 999;
//  }
//
//  .delete-btn {
//    background-color: #F56C6C;
//    border-color: #F56C6C;
//    color: white;
//    font-size: 15px;
//    display: block;
//    position: absolute;
//    bottom: 0;
//    right: 0;
//    margin-right: -1px;
//    margin-bottom: -1px;
//    border-radius: 0;
//    width: 35px;
//    height: 25px;
//    text-align: center;
//    padding: 0;
//  }
//}
//
//$choseLayoutItemThemeColor: #e6a23c;
//.layout-item.active {
//  border: 3px solid $choseLayoutItemThemeColor !important;
//
//  & > .handle {
//    display: block;
//    position: absolute;
//    top: 0;
//    left: 0;
//    font-size: 20px;
//    color: $choseFormItemThemeColor;
//    cursor: move;
//    z-index: 999;
//  }
//
//  & > .delete-btn {
//    background-color: #F56C6C;
//    border-color: #F56C6C;
//    color: white;
//    font-size: 15px;
//    display: block;
//    position: absolute;
//    bottom: 0;
//    right: 0;
//    margin-right: -1px;
//    margin-bottom: -1px;
//    border-radius: 0;
//    width: 35px;
//    height: 25px;
//    text-align: center;
//    padding: 0;
//  }
//}


</style>