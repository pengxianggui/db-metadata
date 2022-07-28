<template>
  <div style="border: 2px solid #dddddd;">
    <template v-for="(v, k, index) in formCompLib">
      <div :key="index" v-if="!hiddenNonLayoutComponent || k === '布局组件'">
        <h5 v-text="k" style="margin: 5px"></h5>
        <draggable :clone="formItemCloneHandler"
                   :group="{ name: 'form', pull: 'clone', put: false }"
                   :list="v | extract"
                   @start="handleStart"
                   @end="handleEnd"
                   :move="handleMove"
                   :sort="false"
                   class="grid-box">
          <div v-for="c in v" class="grid-item">
            <svg-icon :value="c.icon" class="icon"></svg-icon>
            <span>{{ c.comp.label }}</span>
          </div>
        </draggable>
      </div>
    </template>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import {buildDefaultMeta} from '../../core/index'
import compLib, {extract, isLayoutComp} from './relate/componentData'
import {randomNum, isEmpty} from '../../utils/common'

export default {
  name: "ComponentList",
  components: {
    draggable
  },
  props: {
    editMode: Boolean,
    formMeta: Object
  },
  filters: {
    extract(value) {
      return extract(value)
    }
  },
  data() {
    return {
      formCompLib: compLib
    }
  },
  methods: {
    layoutCloneHandler(data) {
      // 容器组件
    },
    formItemCloneHandler(data) {
      let {name, label} = data;
      let meta = {
        name: name + randomNum(8),
        label: label
      };

      this.$merge(meta, buildDefaultMeta(name));
      return meta;
    },
    handleEnd(event) {
    },
    handleStart(event) {
    },
    handleMove(event) {
      const {draggedContext: {element}} = event
      const allowDrag = isLayoutComp(element.name)
      if (!allowDrag) {
        this.$message.closeAll(); // 防止下面这句提示疯狂炸屏
        this.$message.warning('目前编辑模式下, 只允许添加布局组件')
      }
      return allowDrag
    }
  },
  computed: {
    hiddenNonLayoutComponent() {
      const {editMode, formMeta: {objectCode}} = this
      return editMode || !isEmpty(objectCode)
    }
  }
}
</script>

<style scoped lang="scss">
.grid-box {
  display: grid;
  grid-template-columns: 1fr 1fr;
  border-top: 1px solid #EEEEEE;
  border-left: 1px solid #EEEEEE;

  $gridItemHeight: 50px;

  .grid-item {
    border: 1px solid #EEEEEE;
    margin-left: -1px;
    margin-top: -1px;
    height: $gridItemHeight;
    text-align: center;
    font-size: 12px;
    cursor: move;
    background-color: white;

    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    .icon {
      font-size: 20px;
    }
  }
}
</style>
