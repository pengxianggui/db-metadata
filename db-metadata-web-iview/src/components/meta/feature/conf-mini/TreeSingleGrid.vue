<template>
    <el-form>
        <el-divider content-position="left">Table配置</el-divider>
        <el-form-item label="objectCode" class="inline" prop="objectCode" required>
            <drop-down-box v-model="config.table.objectCode"
                           :data-url="metaObjectCodeUrl" @change="config.table.foreignFieldCode = null"
                           filterable required>
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="primaryKey" class="inline" prop="primaryKey" required>
            <drop-down-box v-model="config.table.primaryKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="foreignFieldCode" class="inline" prop="foreignFieldCode" required>
            <drop-down-box v-model="config.table.foreignFieldCode"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
    </el-form>
</template>

<script>
    import {restUrl} from "@/constant/url";

    export default {
        name: "TreeSingleGrid",
        props: {
            oc: String
        },
        data() {
            const {oc: objectCode} = this;
            return {
                config: {
                    table: {
                        objectCode: objectCode,
                        primaryKey: null,
                        foreignFieldCode: null
                    }
                },
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
                metaFieldCodeUrl: restUrl.FIELD_CODE_LIST_BY_OBJECT
            }
        },
        updated() {
            this.$emit('change', this.config);
        }
    }
</script>

<style scoped>

</style>
