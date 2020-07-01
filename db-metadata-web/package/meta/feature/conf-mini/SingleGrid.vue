<template>
    <el-form>
        <el-divider content-position="left">单表配置</el-divider>
        <el-form-item label="元对象编码" prop="objectCode" required>
            <drop-down-box v-model="config.singleGrid.objectCode" :data-url="metaObjectCodeUrl">
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
            oc: String
        },
        data() {
            const {oc: objectCode} = this;
            return {
                config: {
                    singleGrid: {
                        objectCode: objectCode
                    }
                },
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST
            }
        },
        updated() {
            this.$emit('change', this.config);
        }
    }
</script>

<style scoped>

</style>
