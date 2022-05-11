<template>
  <el-popover :trigger="nativeTrigger" :placement="placement" v-model="visible"
              popper-class="md_no-padding" v-bind="$attrs" @show="$emit('show')">
    <span slot="reference" @click.right="rightClickHander">
        <slot name="label">
            <i class="el-icon-caret-bottom" style="cursor: pointer"></i>
        </slot>
    </span>
    <slot>
      <list>
        <template #body>
          <slot name="body"></slot>
        </template>
      </list>
    </slot>
  </el-popover>
</template>

<script>
export default {
  name: "PopMenu",
  props: {
    trigger: {
      type: String,
      default: () => 'click',
      validator: value => ['click', 'focus', 'hover', 'right-click'].indexOf(value) > -1
    },
    placement: String,
    // visible: {
    //   type: Boolean,
    //   default: () => false
    // }
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    rightClickHander(ev) {
      let self = this;
      if (self.trigger === 'right-click') {
        ev.preventDefault();
        self.visible = true;
        // self.$emit('update:visible', true)
        let div = document.createElement('div');
        div.style = 'position: absolute; width: 100%; height: 100%; top: 0; left: 0; z-index: 1;';
        document.body.appendChild(div);
        div.addEventListener('click', function () {
          self.visible = false;
          // self.$emit('update:visible', false)
          document.body.removeChild(div);
        })
      }
    },
    close() {
      this.visible = false
    }
  },
  computed: {
    nativeTrigger() {
      return this.trigger !== 'right-click' ? this.trigger : 'manual';
    }
  }
}
</script>

<style scoped>
</style>
