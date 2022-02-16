<template>
  <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
import {metaObjectCode} from "../../constant/variable";
export default {
  name: "UserList",
  data() {
    return {
      oc: metaObjectCode.UserList
    }
  }
}
</script>

<style scoped>

</style>
