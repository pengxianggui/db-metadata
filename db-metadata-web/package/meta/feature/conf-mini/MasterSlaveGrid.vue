<template>
    <el-form ref="masterSlaveForm" :model="config">
        <el-divider content-position="left">主表配置</el-divider>
        <el-form-item label="元对象编码" class="inline" prop="master.objectCode" required>
            <drop-down-box v-model="config.master.objectCode"
                           :data-url="metaObjectCodeUrl" @change="config.master.primaryKey = null"
                           filterable>
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="主键" class="inline" prop="master.primaryKey" required>
            <el-tooltip effect="dark" placement="top">
              <div slot="content">从表关联的主键</div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            <drop-down-box v-model="config.master.primaryKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.master.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-tabs v-model="activeTab" @tab-click="handleClick" @edit="handleTabEdit" editable>
            <el-tab-pane v-for="(slave, index) in config.slaves" :label="'从表' + index" :name="index + ''" :key="slave.objectCode">
              <el-form-item label="元对象编码" :prop="'slaves.' + index + '.objectCode'" class="inline" required>
                <drop-down-box v-model="slave.objectCode"
                               @change="slave.foreignFieldCode = null"
                               :data-url="metaObjectCodeUrl" filterable>
                  <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                      {{item.code}}
                    </el-option>
                  </template>
                </drop-down-box>
              </el-form-item>
              <el-form-item label="外键" :prop="'slaves.' + index + '.foreignFieldCode'" class="inline" required>
                <el-tooltip effect="dark" placement="top">
                  <div slot="content">关联主表中主键的字段</div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
                <drop-down-box v-model="slave.foreignFieldCode"
                               :data-url="$compile(metaFieldCodeUrl, {objectCode: slave.objectCode})"
                               filterable>
                  <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                  </template>
                </drop-down-box>
              </el-form-item>
            </el-tab-pane>
        </el-tabs>
    </el-form>
</template>

<script>
    import {restUrl} from "../../../constant/url";

    export default {
        name: "MasterSlaveGrid",
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
                activeTab: '0',
            }
        },
        created() {
            this.$merge(this.config, {
              master: {
                objectCode: null,
                primaryKey: null
              },
              slaves: [{
                objectCode: null,
                foreignFieldCode: null,
                order: 0
              }]
            })
        },
        methods: {
            handleClick(tab, event) {
                // todo
            },
            validate(callback) {
                this.$refs['masterSlaveForm'].validate((valid) => callback(valid))
            },
            handleTabEdit(targetName, action) {
                if (action === 'add') {
                    const length = this.config.slaves.length
                    this.config.slaves.push({
                        objectCode: null,
                        foreignFieldCode: null,
                        order: length
                    })
                }
                if (action === 'remove') {
                    let index = targetName - 0
                    if (this.config.slaves.length > 1) {
                        this.config.slaves.splice(index, 1)
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
