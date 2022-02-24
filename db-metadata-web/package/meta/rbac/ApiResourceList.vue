<template>
  <single-grid-tmpl :oc="oc"></single-grid-tmpl>
</template>

<script>
import {metaObjectCode} from "../../constant/variable";

export default {
  name: "ApiResourceList",
  data() {
    return {
      oc: metaObjectCode.ApiResourceList
    }
  }
}
</script>

<style scoped>

</style>
