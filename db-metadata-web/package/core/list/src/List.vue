<template>
  <ul id="menu" v-bind="$attrs" :style="style">
    <slot></slot>
  </ul>
</template>

<script>
export default {
  name: "List",
  props: {
    direction: {
      type: String,
      default: () => 'row' // row / column
    }
  },
  computed: {
    style() {
      const {direction} = this
      if (direction == 'row') {
        return {
          'display': 'flex',
          'flex-direction': 'row'
        }
      } else {
        return {
          'display': 'flex',
          'flex-direction': 'column'
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
ul#menu {
  list-style: none;
  margin: 0;
  padding: 5px;
  justify-content: space-evenly;

  li {
    flex: 1;
    text-align: center;
  }
}
</style>
