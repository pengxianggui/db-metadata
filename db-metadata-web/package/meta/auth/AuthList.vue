<template>
  <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
import {metaObjectCode} from "../../constant/variable";
export default {
  name: "AuthList",
  data() {
    return {
      oc: metaObjectCode.AuthList
    }
  }
}
</script>

<style scoped>

</style>