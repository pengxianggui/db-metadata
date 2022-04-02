<template>
  <div style="border: 2px solid #dddddd;">
    <template v-for="(v, k, index) in formCompLib">
      <div :key="index" v-if="!hiddenNonLayoutComponent || k === '布局组件'">
        <h5 v-text="k" style="margin: 5px"></h5>
        <draggable :clone="formItemCloneHandler"
                   :group="{ name: 'form', pull: 'clone', put: false }"
                   :list="v | extract"
                   @end="handleEnd"
                   @start="handleStart"
                   :move="handleMove"
                   :sort="false"
                   class="grid-box">
          <div v-for="c in v" class="grid-item">
            <!--            TODO icon美化-->
            <!--            <i class="el-icon-receiving"></i>-->
            <!--            <svg-icon :value="c.icon"></svg-icon>-->
            <span>{{ c.comp.label }}</span>
          </div>
        </draggable>
      </div>
    </template>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import {defaultMeta} from '../../core/index'
import compLib, {extract} from './relate/componentData'
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

      this.$merge(meta, defaultMeta[name]);
      return meta;
    },
    handleEnd() {
    },
    handleStart() {
    },
    handleMove(e) {
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
  }
}
</style>
