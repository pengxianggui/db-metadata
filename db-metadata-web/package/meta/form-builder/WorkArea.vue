<template>
  <div class="container">
    <div class="opr-box">
      <el-button-group>
        <el-button @click="preview" icon="el-icon-view" size="mini" type="primary">视图预览</el-button>
        <el-button @click="jsonView" icon="el-icon-view" size="mini" type="primary">json预览</el-button>
        <el-button @click="submitForm" icon="el-icon-download" size="mini" type="success">保存</el-button>
        <el-button @click="resetForm" icon="el-icon-delete" size="mini" type="danger">重置</el-button>
      </el-button-group>
      <slot name="object-code"></slot>
    </div>
    <div class="work-area">
      <form-view :ref="formMeta.name" :meta="formMeta" style="height: 100%;">
        <template #form-item>
            <nest-form-item :columns="formMeta.columns" :active.sync="selectItemName"
                            @formItemClick="handleFormItemClick"
                            @formItemDelete="handleFormItemDelete"
                            @layoutItemDelete="handleLayoutItemDelete"
                            @add="handleAdd"
                            style="height: 100%">
              <div class="blank-tip">从左侧拖拽来添加表单项</div>
            </nest-form-item>
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
import {restUrl} from "../../constant/url";
import DropDownBox from "../../core/dropdownbox/src/DropDownBox";
import DefaultJsonBoxMeta from "../../core/jsonbox/ui-conf";
import {isLayoutComp} from './relate/componentData'
import NestFormItem from "./NestFormItem";

export default {
  name: "WorkArea",
  components: {
    DropDownBox,
    FormView,
    draggable,
    NestFormItem
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
    handleLayoutItemDelete(columns, item, index, ev){
      const {formMeta: {objectCode}} = this
      if (objectCode) {
        this.$message.warning('编辑元对象时不允许移除控件')
      }
      columns.splice(index, 1)
    },
    handleFormItemDelete(columns, item, index, ev){
      const {formMeta: {objectCode}} = this
      if (objectCode) {
        this.$message.warning('编辑元对象时不允许移除控件')
      }
      columns.splice(index, 1)
    },
    // 新增
    handleAdd({to, from, item, clone, oldIndex, newIndex}) {
      const {formMeta: {objectCode}} = this
      if (objectCode) {
        this.$message.warning('编辑元对象时不允许新增控件')
        return false;
      }
      return true;
    },
    // 点击选中
    handleFormItemClick(column) {
      const {name} = column
      this.selectItemName = name

      this.$emit('update:activeItem', column)
    },
    jsonView() {
      this.$dialog(DefaultJsonBoxMeta, this.formMeta, {
        title: "Json预览"
      })
    },
    preview() {
      this.$dialog(this.formMeta, null, {
        title: "视图预览"
      })
    },
    submitForm() {
      const componentCode = 'FormView';
      const objectCode = this.formMeta.objectCode;
      let params = {
        componentCode: componentCode,
        objectCode: objectCode
      };
      let objectMeta = utils.deepClone(this.formMeta);
      let columnsMeta;
      if (objectMeta.hasOwnProperty('columns')) {
        columnsMeta = utils.deepClone(objectMeta.columns);
        delete objectMeta['columns'];
      }

      params[objectCode] = objectMeta;
      columnsMeta.forEach(fieldMeta => params[fieldMeta.name] = fieldMeta);

      this.$axios({
        method: 'POST',
        url: restUrl.COMP_CONF_UPDATE,
        data: params
      }).then(({msg = '提交成功'}) => {
        this.$message.success(msg);
      }).catch(({msg = '提交失败'}) => {
        this.$message.error(msg);
      })
    },
    resetForm() {
      this.$message.error("resetForm action not finished!");
    },
  },
  // watch: {
  //   selectItemName(newVal) {
  //     this.$emit('select', newVal)
  //   }
  // },
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
  border: 10px solid #dddddd;
  padding: 10px;
}

.opr-box {
  display: flex;
  margin-bottom: 5px;
  align-items: center;
  justify-content: space-between;
}

.work-area {
  flex: 1;
  //overflow: auto;
  position: relative;

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
