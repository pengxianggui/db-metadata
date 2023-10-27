<template>
  <span>
    <template v-if="enable">
      <template>
        <pop-menu trigger="right-click">
          <template #label>
            <slot>{{ label }}</slot>
          </template>
          <list direction="column">
            <list-item @click="toEditMetaObject">编辑元对象({{ objectCode }})</list-item>
            <list-item @click="toEditMetaField">编辑元字段({{ fieldCode }})</list-item>
            <list-item @hover="getInstanceCode">
              <pop-menu class="pop-menu" trigger="hover" placement="right" :append-to-body="false">
                <template #label>
                  <div>编辑({{ objectCode }})的UI实例配置}</div>
                </template>
                <template #default>
                  <list direction="column">
                    <list-item v-for="ic in instanceCodes" :key="ic"
                               @click="toEditInstanceConf(ic)"
                               class="list-item"
                    >{{ic}}</list-item>
                  </list>
                </template>
              </pop-menu>
            </list-item>
            <list-item @hover="getInstanceCode">
              <pop-menu class="pop-menu" trigger="hover" placement="right" :append-to-body="false">
                <template #label>编辑实例字段UI配置({{ fieldCode }})</template>
                <template #default>
                  <list direction="column">
                    <list-item v-for="ic in instanceCodes" :key="ic"
                               @click="toEditInstanceConf(ic, fieldCode)"
                               class="list-item"
                    >{{ic}}</list-item>
                  </list>
                </template>
              </pop-menu>
            </list-item>
          </list>
        </pop-menu>
        <dialog-box :visible.sync="dialogVisible" :meta="dialogMeta" :component-meta="dialogComponentMea" width="70%"
                    @ok="dialogVisible=false" @cancel="dialogVisible=false">
          <template #default>
            <slot name="dialog-body" v-bind:meta="dialogComponentMea"></slot>
          </template>
          <template #footer><span></span></template>  <!-- 表单自带button条 -->
        </dialog-box>
      </template>
    </template>
    <template v-else>
      <slot>{{ label }}</slot>
    </template>
  </span>
</template>

<script>
import utils from '../../../utils'
import {restUrl, routeUrl} from "../../../constant/url";
import {isEmpty} from "../../../utils/common";

export default {
  name: "MetaEasyEdit",
  props: {
    objectCode: String,
    fieldCode: String,
    label: String
  },
  data() {
    return {
      dialogComponentMea: {}, // 弹窗内包含的组件元对象
      dialogMeta: {}, // 弹窗组件元对象
      dialogVisible: false,   // 弹窗显隐
      instanceCodes: []   // 当前objectCode的所有容器实例配置编码
    }
  },
  methods: {
    getInstanceCode() {
      const {objectCode, instanceCodes} = this;
      if (!utils.isEmpty(instanceCodes)) return; // 避免重复请求

      this.$axios.get(this.$compile(restUrl.LOAD_INSTANCE_CODE_BY_OBJECT, {
        objectCode: objectCode,
        kv: false // 不以kv对返回
      })).then(resp => {
        this.instanceCodes = resp.data;
      });
    },
    toEditMetaObject() {
      let {objectCode} = this;
      if (isEmpty(objectCode)) return;

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
    toEditMetaField() {
      let {objectCode, fieldCode} = this;
      if (isEmpty(objectCode)) return;

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
    toEditInstanceConf(ic, fieldCode) {
      const url = this.$compile(routeUrl.R_INSTANCE_CONF_EDIT, {
        instanceCode: ic,
        fieldCode: fieldCode
      })
      this.$router.push({path: url})
    }
  },
  computed: {
    enable() {
      return this.$isRoot() && !isEmpty(this.objectCode)
    }
  }
}
</script>

<style scoped lang="scss">
.pop-menu {
  &::v-deep {
    .el-popover {
      margin-left: 0;
    }
  }

  .list-item {
    white-space: nowrap;
    padding: 5px;
  }
}
</style>
