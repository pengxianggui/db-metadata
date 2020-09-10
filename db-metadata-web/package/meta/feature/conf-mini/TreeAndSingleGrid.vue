<template>
    <el-form ref="treeAndSingleGrid" :model="config">
        <el-divider content-position="left">Tree配置</el-divider>
        <el-form-item label="元对象编码" class="inline" prop="tree.objectCode" required>
            <drop-down-box name="objectCode" v-model="config.tree.objectCode" :data-url="metaObjectCodeUrl" filterable>
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="idKey" class="inline" prop="tree.idKey" required>
            <drop-down-box v-model="config.tree.idKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="pidKey" class="inline" prop="tree.pidKey" required>
            <drop-down-box v-model="config.tree.pidKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="rootIdentify" class="inline" prop="tree.rootIdentity">
            <text-box name="rootIdentity" v-model="config.tree.rootIdentify"></text-box>
        </el-form-item>
        <el-form-item label="label" class="inline" prop="tree.label" required>
            <drop-down-box v-model="config.tree.label"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="isSync" prop="tree.isSync">
            <bool-box v-model="config.tree.isSync" required></bool-box>
        </el-form-item>

        <el-divider content-position="left">Table配置</el-divider>
        <el-form-item label="objectCode" class="inline" prop="table.objectCode" required>
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
        <el-form-item label="primaryKey" class="inline" prop="table.primaryKey" required>
          <drop-down-box v-model="config.table.primaryKey"
                         :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                         filterable required>
            <template #label="{option}">
              <span>{{option.value}}({{option.label}})</span>
            </template>
          </drop-down-box>
        </el-form-item>
        <el-form-item label="foreignFieldCode" class="inline" prop="table.foreignFieldCode">
          <drop-down-box v-model="config.table.foreignFieldCode"
                         :data-url="$compile(metaFieldCodeUrl, {objectCode: config.table.objectCode})"
                         filterable>
            <template #label="{option}">
              <span>{{option.value}}({{option.label}})</span>
            </template>
          </drop-down-box>
        </el-form-item>
    </el-form>
</template>

<script>
    import {restUrl} from "../../../constant/url";

    export default {
        name: "TreeAndSingleGrid",
        props: {
          config: {
            type: Object,
            required: true
          }
        },
        data() {
            return {
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
                metaFieldCodeUrl: restUrl.FIELD_CODE_LIST_BY_OBJECT,
                // rules: {
                //   tree: {
                //     objectCode: [{required: true, message: '必填项', trigger: 'blur'}]
                //   }
                // }
            }
        },
        created() {
          this.$merge(this.config, {
            tree: {
              objectCode: null,
              idKey: null,
              pidKey: null,
              rootIdentify: null,
              label: null,
              isSync: false
            },
            table: {
              objectCode: null,
              primaryKey: null,
              foreignFieldCode: null
            }
          })
        },
        methods: {
          validate(callback) {
            return this.$refs['treeAndSingleGrid'].validate(valid => callback(valid))
          }
        }
    }
</script>

<style scoped>

</style>
