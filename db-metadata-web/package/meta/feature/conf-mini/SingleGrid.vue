<template>
    <el-form ref="singleGridForm" :model="config">
        <el-divider content-position="left">单表配置</el-divider>
        <el-form-item label="元对象编码" prop="singleGrid.objectCode" required>
            <drop-down-box v-model="config.singleGrid.objectCode" :data-url="metaObjectCodeUrl" filterable>
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
    </el-form>
</template>

<script>
    import {restUrl} from "../../../constant/url";

    export default {
        name: "SingleGrid",
        props: {
            config: {
              type: Object,
              required: true
            }
        },
        data() {
            return {
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST
            }
        },
        created() {
          this.$merge(this.config, {
            singleGrid: {
              objectCode: null
            }
          })
        },
        methods: {
            validate(callback) {
              return this.$refs['singleGridForm'].validate(valid => callback(valid))
            }
        }
    }
</script>

<style scoped>

</style>
