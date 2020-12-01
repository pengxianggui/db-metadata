<template>
  <div>
    <div v-for="(v, k, index) in formCompLib" :key="index">
      <h5 v-text="k" style="margin: 5px"></h5>
      <draggable :clone="formItemCloneHandler"
                 :group="{ name: 'form', pull: 'clone', put: false }"
                 :list="v | extract"
                 @end="handleMoveEnd"
                 @start="handleMoveStart"
                 :sort="false"
                 class="grid-box">
        <div v-for="c in v" class="grid-item">
          <!--            <i class="el-icon-receiving"></i>-->
          <!--            <svg-icon :value="c.icon"></svg-icon>-->
          <span>{{ c.comp.label }}</span>
        </div>
      </draggable>
    </div>
<!--    <div class="layout-comp">-->
<!--      <h5>布局组件</h5>-->
<!--      <draggable :clone="formItemCloneHandler" :group="{name:'form', pull: 'clone', put: false}" :list="layoutComps"-->
<!--                 :sort="false" class="grid-box">-->
<!--        <div v-for="c in layoutComps" class="grid-item">-->
<!--          <span>{{ c.label }}</span>-->
<!--        </div>-->
<!--      </draggable>-->
<!--    </div>-->
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import {defaultMeta} from '../../core/index'
import compLib, {extract, isLayoutComp} from './relate/componentData'
import RowGrid from "../../core/rowgrid/src/RowGrid";

export default {
  name: "ComponentList",
  components: {
    draggable
  },
  filters: {
    extract(value) {
      return extract(value)
    }
  },
  data() {
    return {
      globalId: 0,
      formCompLib: compLib,
      layoutComps: [RowGrid]
    }
  },
  methods: {
    layoutCloneHandler(data) {
      // 容器组件
    },
    formItemCloneHandler(data) {
      let {name, label} = data;
      let meta = {
        name: name + this.globalId++,
        label: label
      };

      this.$merge(meta, defaultMeta[name]);

      if (isLayoutComp(name)) { // 包装布局组件配置
        const {conf: {span}} = meta
        for (let i = 0; i < span.length; i++) {
          meta['columns'][i] = []
        }
      }
      return meta;
    },
    handleMoveEnd() {
    },
    handleMoveStart() {
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
