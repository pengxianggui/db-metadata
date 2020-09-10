<template>
    <el-form ref="treeSingleGrid" :model="config">
        <el-divider content-position="left">配置</el-divider>
        <el-form-item label="元对象编码" class="inline" prop="table.objectCode" required>
            <drop-down-box v-model="config.table.objectCode" :data-url="metaObjectCodeUrl" filterable>
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="idKey" class="inline" prop="table.idKey" required>
            <drop-down-box v-model="config.table.idKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="pidKey" class="inline" prop="table.pidKey" required>
            <drop-down-box v-model="config.table.pidKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="rootIdentify" class="inline" prop="table.rootIdentify">
            <text-box v-model="config.table.rootIdentify" required></text-box>
        </el-form-item>
        <el-form-item label="label" class="inline" prop="table.label" required>
            <drop-down-box v-model="config.table.label"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="isSync" prop="table.isSync">
            <bool-box v-model="config.table.isSync" required></bool-box>
        </el-form-item>
    </el-form>
</template>

<script>
    import {restUrl} from "../../../constant/url";

    export default {
        name: "TreeSingleGrid",
        props: {
          config: {
            type: Object,
            required: true
          }
        },
        created() {
          this.$merge(this.config, {
            table: {
              objectCode: null,
              idKey: null,
              pidKey: null,
              rootIdentify: null,
              label: null,
              isSync: false
            }
          })
        },
        data() {
            return {
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
                metaFieldCodeUrl: restUrl.FIELD_CODE_LIST_BY_OBJECT
            }
        },
        methods: {
          validate(callback) {
            return this.$refs['treeSingleGrid'].validate(valid => callback(valid))
          }
        }
    }
</script>

<style scoped>

</style>
