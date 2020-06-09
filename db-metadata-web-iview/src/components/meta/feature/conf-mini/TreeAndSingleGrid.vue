<template>
    <el-form>
        <el-divider content-position="left">Tree配置</el-divider>
        <el-form-item label="元对象编码" class="inline">
            <drop-down-box v-model="config.tree.objectCode" :data-url="metaObjectCodeUrl">
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="primaryKey" class="inline">
            <drop-down-box v-model="config.tree.primaryKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})">
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="idKey" class="inline">
            <drop-down-box v-model="config.tree.idKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="pidKey" class="inline">
            <drop-down-box v-model="config.tree.pidKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="rootIdentify" class="inline">
            <text-box v-model="config.tree.rootIdentify" required></text-box>
        </el-form-item>
        <el-form-item label="label" class="inline">
            <drop-down-box v-model="config.tree.label"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="isSync">
            <bool-box v-model="config.tree.isSync" required></bool-box>
        </el-form-item>
        <h3>Table配置</h3>
        <el-form-item label="objectCode" class="inline">
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
        <el-form-item label="foreignFieldCode" class="inline">
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
                    tree: {
                        objectCode: null,
                        idKey: null,
                        pidKey: null,
                        rootIdentify: null,
                        label: null,
                        isSync: false
                    },
                    table: {
                        objectCode: objectCode,
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
