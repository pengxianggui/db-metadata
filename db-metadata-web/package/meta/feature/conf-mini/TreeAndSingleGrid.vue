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
            <el-tooltip effect="dark" placement="top">
              <div slot="content">Tree自身关联的主键以及Table关联的主键, 目前这两者要求为同一个字段</div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            <drop-down-box v-model="config.tree.idKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="pidKey" class="inline" prop="tree.pidKey" required>
            <el-tooltip effect="dark" placement="top">
              <div slot="content">Tree自身关联的外键</div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            <drop-down-box v-model="config.tree.pidKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="rootIdentify" class="inline" prop="tree.rootIdentity">
            <el-tooltip effect="dark" placement="top">
              <div slot="content">根节点标识, pidKey为何值时表示根节点</div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            <text-box name="rootIdentity" v-model="config.tree.rootIdentify"></text-box>
        </el-form-item>
        <el-form-item label="label" class="inline" prop="tree.label" required>
            <el-tooltip effect="dark" placement="top">
              <div slot="content">显示为树节点名的字段</div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            <drop-down-box v-model="config.tree.label"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="isSync" prop="tree.isSync">
            <el-tooltip effect="dark" placement="top">
              <div slot="content">树节点展开是否异步加载</div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            <bool-box v-model="config.tree.isSync" required></bool-box>
        </el-form-item>
<!--        <el-form-item label="主键" class="inline" prop="tree.primaryKey" required>-->
<!--          <el-tooltip effect="dark" placement="top">-->
<!--            <div slot="content">Table关联的主键</div>-->
<!--            <i class="el-icon-question"></i>-->
<!--          </el-tooltip>-->
<!--          <drop-down-box v-model="config.tree.primaryKey"-->
<!--                         :data-url="$compile(metaFieldCodeUrl, {objectCode: config.tree.objectCode})"-->
<!--                         filterable required>-->
<!--            <template #label="{option}">-->
<!--              <span>{{option.value}}({{option.label}})</span>-->
<!--            </template>-->
<!--          </drop-down-box>-->
<!--        </el-form-item>-->

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
        <el-form-item label="外键" class="inline" prop="table.foreignFieldCode">
          <el-tooltip effect="dark" placement="top">
            <div slot="content">关联Tree中主键的字段</div>
            <i class="el-icon-question"></i>
          </el-tooltip>
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
