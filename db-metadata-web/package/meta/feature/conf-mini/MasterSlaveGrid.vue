<template>
    <el-form>
        <el-divider content-position="left">主表配置</el-divider>
        <el-form-item label="元对象编码" class="inline" prop="objectCode" required>
            <drop-down-box v-model="config.master.objectCode"
                           :data-url="metaObjectCodeUrl" @change="config.master.primaryKey = null"
                           filterable required>
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
        <el-form-item label="主键" class="inline" prop="primaryKey" required>
            <drop-down-box v-model="config.master.primaryKey"
                           :data-url="$compile(metaFieldCodeUrl, {objectCode: config.master.objectCode})"
                           filterable required>
                <template #label="{option}">
                    <span>{{option.value}}({{option.label}})</span>
                </template>
            </drop-down-box>
        </el-form-item>

        <el-tabs v-model="activeTab" @tab-click="handleClick">
            <el-tab-pane label="从表1" name="first">
                <el-form-item label="元对象编码" class="inline">
                    <drop-down-box v-model="config.slaves[0].objectCode"
                                   @change="config.slaves[0].foreignFieldCode = null"
                                   :data-url="metaObjectCodeUrl" filterable required>
                        <template #options="{options}">
                            <el-option v-for="item in options" :key="item.code" :label="item.code"
                                       :value="item.code">
                                {{item.code}}
                            </el-option>
                        </template>
                    </drop-down-box>
                </el-form-item>
                <el-form-item label="外键" class="inline">
                    <drop-down-box v-model="config.slaves[0].foreignFieldCode"
                                   :data-url="$compile(metaFieldCodeUrl, {objectCode: config.slaves[0].objectCode})"
                                   filterable>
                        <template #label="{option}">
                            <span>{{option.value}}({{option.label}})</span>
                        </template>
                    </drop-down-box>
                </el-form-item>
                <el-form-item label="排序" class="inline">
                    <num-box v-model="config.slaves[0].order" controls required></num-box>
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
            oc: String,
            pk: String
        },
        data() {
            const {oc: objectCode, pk: primaryKey} = this;
            return {
                config: {
                    master: {
                        objectCode: objectCode,
                        primaryKey: primaryKey
                    },
                    slaves: [{
                        objectCode: null,
                        foreignFieldCode: null,
                        order: 0
                    }]
                },
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
                metaFieldCodeUrl: restUrl.FIELD_CODE_LIST_BY_OBJECT,
                activeTab: 'first',
            }
        },
        updated() {
            this.$emit('change', this.config);
        },
        methods: {
            handleClick(tab, event) {
                // todo
            }
        }
    }
</script>

<style scoped>

</style>
