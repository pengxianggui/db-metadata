<template>
    <div>
        <el-form-item label="元对象编码">
            <drop-down-box v-model="config.singleGrid.objectCode" :data-url="metaObjectCodeUrl">
                <template #options="{options}">
                    <el-option v-for="item in options" :key="item.code" :label="item.code"
                               :value="item.code">
                        {{item.code}}
                    </el-option>
                </template>
            </drop-down-box>
        </el-form-item>
    </div>
</template>

<script>
    import {URL} from '@/constant'
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
                metaObjectCodeUrl: URL.OBJECT_CODE_LIST
            }
        },
        updated() {
            this.$emit('change', this.config);
        }
    }
</script>

<style scoped>

</style>