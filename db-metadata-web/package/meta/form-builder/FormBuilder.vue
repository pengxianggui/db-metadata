<template>
    <div style="display: flex; padding: 5px;">
        <ComponentList style="width: 200px"></ComponentList>
        <div style="flex: 5;margin-left: 5px">
            <WorkArea v-model="formMeta" :active-item.sync="activeItem">
                <template #object-code>
                    <drop-down-box size="mini" placeholder="选择元对象" v-model="objectCode" :data-url="metaObjectCodeUrl"
                                   @change="handleChange" @clear="handleClear" filterable>
                        <template #options="{options}">
                            <el-option v-for="item in options" :key="item.code" :label="item.code"
                                       :value="item.code">
                                {{item.code}}
                            </el-option>
                        </template>
                    </drop-down-box>
<!--                  TODO 下面组件找不到？！InstanceConfEdit正常 -->
<!--                  <meta-object-selector v-model="objectCode" @change="handleChange" @clear="handleClear"></meta-object-selector>-->
                </template>
            </WorkArea>
        </div>
        <div style="width: 300px;">
            <ConfArea v-model="formMeta" :active-item="activeItem" :object-code="objectCode"></ConfArea>
        </div>
    </div>
</template>

<script>
    import utils from '../../utils'
    import {restUrl} from "../../constant/url"
    import ComponentList from './ComponentList'
    import WorkArea from './WorkArea'
    import ConfArea from './ConfArea'
    import DefaultFormViewMeta from '../../core/formview/ui-conf'
    import MetaObjectSelector from "../component/MetaObjectSelector"

    export default {
        name: "FormBuilder",
        components: {MetaObjectSelector, ComponentList, WorkArea, ConfArea},
        props: {
            oc: String
        },
        data() {
            const objectCode = utils.assertUndefined(this.oc, this.$route.query.objectCode);
            return {
                formMeta: this.$merge({}, DefaultFormViewMeta),
                activeItem: {},
                objectCode: objectCode,
                metaObjectCodeUrl: restUrl.OBJECT_CODE_LIST,
            }
        },
        methods: {
            handleClear() {
                this.objectCode = null;
                this.handleChange();
                this.setInitState();
            },
            handleChange() {
                const {objectCode} = this;
                this.loadConf(objectCode);
                this.$emit('oc-change', objectCode);
            },
            setInitState() {
                this.formMeta = this.$merge({}, DefaultFormViewMeta);
            },
            loadConf(objectCode) {
                if (utils.isEmpty(objectCode)) return;
                const url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
                    componentCode: 'FormView',
                    objectCode: objectCode
                });
                this.$axios.safeGet(url).then(resp => {
                    let formMeta = resp.data;
                    this.$reverseMerge(this.formMeta, formMeta, true);
                }).catch(({msg = '加载失败'}) => {
                    this.setInitState();
                    this.$message.error(msg);
                })
            }
        },
        watch: {
            oc(newVal) {
                this.objectCode = newVal;
                this.loadConf(this.objectCode);
            }
        },
        mounted() {
            const objectCode = this.objectCode;
            // this.loadConf(objectCode); // 当该源对象无FormView配置时后端提示不友好直接报错， 这里先屏蔽
        },
    }
</script>

<style scoped>
</style>
