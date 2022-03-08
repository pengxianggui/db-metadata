<template>
  <meta-layout>
    <template>
      <span class="menu">编程菜单1</span>
      <span class="menu">编程菜单2</span>
      <span class="menu">编程菜单3</span>
    </template>
    <template #side>
      <el-button type="primary" size="mini">扩展按钮</el-button>
    </template>
    <template #profile-menu>
      <el-dropdown-item>扩展</el-dropdown-item>
    </template>
  </meta-layout>
</template>

<script>
export default {
  name: "Layout",
  meta: {
    isLayout: true,
    cn: '我的布局'
  }
}
</script>

<style scoped lang="scss">
  .menu {
    width: 100px;
    text-align: center;
    cursor: pointer;
    color: #b5b3b3;
    &:hover {
      background-color: rgba(255, 255, 255, 0.2);
      color: #1d8ce0;
    }
  }
</style>
