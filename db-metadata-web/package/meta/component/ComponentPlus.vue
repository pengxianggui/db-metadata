<template>
  <component v-model="nativeValue" :is="componentName" :meta="meta">
    <slot></slot>
  </component>
</template>

<script>
import Val from "../../core/mixins/value";

export default {
  name: "ComponentPlus",
  mixins: [Val()],
  props: {
    value: {
      type: [
        String,
        Number,
        Boolean,
        Array,
        Object,
        Date,
        Function,
        Symbol
      ],
      required: false
    },
    meta: {
      type: Object,
      required: true
    }
  },
  computed: {
    componentName() {
      const {meta: {component_name}} = this
      return component_name
    }
  }
}
</script>

<style scoped>

</style>