<template>
  <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
import {metaObjectCode} from "../../constant/variable";
export default {
  name: "RoleList",
  data() {
    return {
      oc: metaObjectCode.RoleList
    }
  }
}
</script>

<style scoped>

</style>
