<template>
    <div>
        <template v-if="$isRoot() && objectCode">
            <template v-if="all">
                <pop-menu trigger="right-click">
                    <template #label>{{label}}</template>
                    <list>
                        <list-item @click="editMetaObject">编辑元对象({{objectCode}})</list-item>
                        <list-item @click="editMetaField">编辑元字段({{fieldCode}})</list-item>
                        <list-item @hover="getInstanceCode">
                            <pop-menu trigger="hover" placement="right">
                                <template #label>编辑实例UI配置({{objectCode}})</template>
                                <template #default>
                                    <list>
                                        <list-item v-for="ic in instanceCodes" :key="ic"
                                                   @click="editInstanceConf(ic)">
                                            {{ic}}
                                        </list-item>
                                    </list>
                                </template>
                            </pop-menu>
                        </list-item>
                        <list-item @hover="getInstanceCode">
                            <pop-menu trigger="hover" placement="right">
                                <template #label>编辑实例字段UI配置({{fieldCode}})</template>
                                <template #default>
                                    <list>
                                        <list-item v-for="ic in instanceCodes" :key="ic"
                                                   @click="editInstanceConf(ic, fieldCode)">
                                            {{ic}}
                                        </list-item>
                                    </list>
                                </template>
                            </pop-menu>
                        </list-item>
                    </list>
                </pop-menu>
                <dialog-box :visible.sync="dialogVisible" :meta="dialogMeta" :component-meta="dialogComponentMea"
                            @ok="dialogVisible=false" @cancel="dialogVisible=false">
                    <template #default>
                        <slot name="dialog-body" v-bind:meta="dialogComponentMea"></slot>
                    </template>
                    <template #footer><span></span></template>  <!-- 表单自带button条 -->
                </dialog-box>
            </template>
            <template v-else>
                <pop-menu trigger="click" placement="right" @show="getInstanceCode">
                    <template #label><i class="el-icon-setting"></i></template>
                    <template #default>
                        <list>
                            <list-item v-for="ic in instanceCodes" :key="ic"
                                       @click="editInstanceConf(ic)">{{ic}}
                            </list-item>
                        </list>
                    </template>
                </pop-menu>
            </template>
        </template>
        <template v-else>{{label}}</template>
    </div>
</template>

<script>
    import utils from '../../../utils'
    import {restUrl, routeUrl} from "../../../constant/url";

    export default {
        name: "MetaEasyEdit",
        props: {
            all: {
                type: Boolean
            },
            objectCode: {
                type: String,
                // required: true
            },
            componentCode: {
                type: String
            },
            fieldCode: {
                type: String
            },
            label: {
                type: String
            }
        },
        data() {
            return {
                dialogComponentMea: {}, // 弹窗内包含的组件元对象
                dialogMeta: {}, // 弹窗组件元对象
                dialogVisible: false,   // 弹窗显隐
                componentCodes: [], // 当前objectCode 配置过的所有componentCode(容器层, 及元对象层)
                instanceCodes: []   // 当前objectCode + componentCode组合的所有 instanceCodes
            }
        },
        methods: {
            checkObjectCode() {
                const {objectCode} = this;
                if (utils.isEmpty(objectCode)) {
                    this.$message.warning('元对象无效: ' + objectCode);
                    return false;
                }
                return true;
            },
            getComponentCode() {
                const {objectCode, componentCodes} = this;
                if (!utils.isEmpty(componentCodes)) return;
                if (!this.checkObjectCode()) return;

                this.$axios.get(this.$compile(restUrl.LOAD_COMP_BY_OBJECT, {objectCode: objectCode, kv: false}))
                    .then(resp => {
                        this.componentCodes = resp.data;
                    })
            },
            getInstanceCode() {
                const {objectCode, instanceCodes, componentCode} = this;
                if (!utils.isEmpty(instanceCodes)) return; // 避免重复请求
                if (!this.checkObjectCode()) return;

                this.$axios.get(this.$compile(restUrl.LOAD_INSTANCE_CODE_BY_OBJECT_COMP, {
                    objectCode: objectCode,
                    componentCode: componentCode,
                    kv: false
                })).then(resp => {
                    this.instanceCodes = resp.data;
                });
            },
            editMetaObject() {
                let {objectCode} = this;
                if (!this.checkObjectCode()) return;

                let url = this.$compile(restUrl.META_OBJECT_TO_EDIT, {objectCode: objectCode});
                this.$axios.get(url).then(resp => {
                    this.dialogComponentMea = resp.data;
                    this.dialogMeta = {
                        component_name: "DialogBox",
                        conf: {
                            title: "编辑元对象:" + objectCode
                        }
                    };
                    this.dialogVisible = true
                });
            },
            editMetaField() {
                let {objectCode, fieldCode} = this;
                if (!this.checkObjectCode()) return;

                let url = this.$compile(restUrl.META_FIELD_TO_EDIT, {
                    objectCode: objectCode,
                    fieldCode: fieldCode
                });
                this.$axios.get(url).then(resp => {
                    this.dialogComponentMea = resp.data;
                    this.dialogMeta = {
                        component_name: "DialogBox",
                        conf: {
                            title: "编辑元字段:" + fieldCode
                        }
                    };
                    this.dialogVisible = true
                });
            },
            editInstanceConf(ic, fieldCode) {
                const {objectCode, componentCode: compCode} = this;
                if (!this.checkObjectCode()) return;

                const url = this.$compile(routeUrl.R_INSTANCE_CONF_EDIT, {
                    componentCode: compCode,
                    objectCode: objectCode,
                    instanceCode: ic,
                    fieldCode: fieldCode
                })
                const finalRouteUrl = this.$router.resolve({
                    path: url
                });
                window.open(finalRouteUrl.href, '_blank');
            },
            editInstanceFieldConf(componentCode) {
                this.editInstanceConf(componentCode); // just edit the ui conf of field named fieldCode. anchor point ———— filtered
            }
        }
    }
</script>

<style scoped>

</style>
