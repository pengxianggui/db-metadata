<template>
  <div class="page-container">
    <search-view :ic="config.master.instanceCodes.SearchView" @search="mHandleSearch"></search-view>
    <table-view :ref="config.master.config.objectCode"
                :ic="config.master.instanceCodes.TableView"
                :filter-params="config.master.filterParams"
                @open-form-view="openMasterFormView"
                @active-change="handleActiveChange"
                @chose-change="handleChoseChange" :page="{ size: 5 }">
      <tempalte #operation-bar="{conf, choseData}">
        <slot name="operation-bar" v-bind:conf="conf" v-bind:choseData="choseData"></slot>
      </tempalte>

      <!-- 主表操作栏扩展插槽 -->
      <template #prefix-btn="scope">
        <slot name="prefix-btn" v-bind="scope"></slot>
      </template>
      <template #add-btn="scope">
        <slot name="add-btn" v-bind="scope"></slot>
      </template>
      <template #batch-delete-btn="scope">
        <slot name="batch-delete-btn" v-bind="scope"></slot>
      </template>
      <template #suffix-btn="scope">
        <slot name="suffix-btn" v-bind="scope"></slot>
      </template>

      <template #buttons="scope">
        <slot name="buttons" v-bind="scope"></slot>
      </template>

      <template #inner-before-extend-btn="scope">
        <slot name="inner-before-extend-btn" v-bind="scope"></slot>
      </template>
      <template #view-btn="scope">
        <slot name="view-btn" v-bind="scope"></slot>
      </template>
      <template #edit-btn="scope">
        <slot name="edit-btn" v-bind="scope"></slot>
      </template>
      <template #delete-btn="scope">
        <slot name="delete-btn" v-bind="scope">
          <!-- TODO 2.3 主子表的删除级联, 还有批量删除 -->
        </slot>
      </template>
      <template #inner-after-extend-btn="scope">
        <slot name="inner-after-extend-btn" v-bind="scope"></slot>
      </template>
    </table-view>

    <el-divider></el-divider>

    <!-- multi slave -->
    <div class="el-card" v-if="config.slaves.length > 1">
      <el-tabs type="border-card">
        <el-tab-pane v-for="slave in config.slaves" :key="slave.config.objectCode" :label="slave.config.objectCode">
          <search-view :ic="slave.instanceCodes.SearchView" @search="sHandleSearch(slave, arguments)"></search-view>
          <table-view :ref="slave.config.objectCode" :filter-params="slave.filterParams" :page="{ size: 5 }">
            <tempalte #operation-bar="scope">
              <slot :name="slave.config.objectCode + '_operation-bar'" v-bind="scope"></slot>
            </tempalte>
            <!-- 子表操作栏扩展插槽 -->
            <template #prefix-btn="scope">
              <slot :name="slave.config.objectCode + '_prefix-btn'" v-bind="scope"></slot>
            </template>
            <template #add-btn="{conf}">
              <slot :name="slave.config.objectCode + '_add-btn'"
                    v-bind:conf="conf" v-bind:add="handleAdd"
                    v-bind:params="config.slaves[0]"
                    v-bind:activeMData="activeMData">
                <el-button v-bind="conf.conf" @click="handleAdd(config.slaves[0])">新增</el-button>
              </slot>
            </template>
            <template #batch-delete-btn="scope">
              <slot :name="slave.config.objectCode + '_batch-delete-btn'" v-bind="scope"></slot>
            </template>
            <template #suffix-btn="scope">
              <slot :name="slave.config.objectCode + '_suffix-btn'" v-bind="scope"></slot>
            </template>

            <template #buttons="scope">
              <slot :name="slave.config.objectCode + '_buttons'" v-bind="scope"></slot>
            </template>

            <!-- 子表单条纪录操作扩展插槽 -->
            <template #inner-before-extend-btn="scope">
              <slot :name="slave.config.objectCode + '_inner-before-extend-btn'" v-bind="scope"></slot>
            </template>
            <template #view-btn="scope">
              <slot :name="slave.config.objectCode + '_view-btn'" v-bind="scope"></slot>
            </template>
            <template #edit-btn="scope">
              <slot :name="slave.config.objectCode + '_edit-btn'" v-bind="scope"></slot>
            </template>
            <template #delete-btn="scope">
              <slot :name="slave.config.objectCode + '_delete-btn'" v-bind="scope"></slot>
            </template>
            <template #inner-after-extend-btn="scope">
              <slot :name="slave.config.objectCode + '_inner-after-extend-btn'" v-bind="scope"></slot>
            </template>



          </table-view>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- single slave -->
    <div class="el-card" v-if="config.slaves.length === 1">
      <search-view :ic="config.slaves[0].instanceCodes.SearchView"
                   @search="sHandleSearch(config.slaves[0], arguments)"></search-view>
      <table-view :ref="config.slaves[0].config.objectCode"
                  :ic="config.slaves[0].instanceCodes.TableView"
                  :filter-params="config.slaves[0].filterParams"
                  @open-form-view="openSlaveFormView($event, config.slaves[0])"
                  :page="{ size: 5 }">

        <tempalte #operation-bar="scope">
          <slot :name="config.slaves[0].config.objectCode + '_operation-bar'" v-bind="scope"></slot>
        </tempalte>
        <!-- 子表操作栏扩展插槽 -->
        <template #prefix-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_prefix-btn'" v-bind="scope"></slot>
        </template>
        <template #add-btn="{conf}">
          <slot :name="config.slaves[0].config.objectCode + '_add-btn'"
                v-bind:conf="conf" v-bind:add="handleAdd"
                v-bind:params="config.slaves[0]"
                v-bind:activeMData="activeMData">
            <el-button v-bind="conf.conf" @click="handleAdd(config.slaves[0])">新增</el-button>
          </slot>
        </template>
        <template #batch-delete-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_batch-delete-btn'" v-bind="scope"></slot>
        </template>
        <template #suffix-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_suffix-btn'" v-bind="scope"></slot>
        </template>

        <template #buttons="scope">
          <slot :name="config.slaves[0].config.objectCode + '_buttons'" v-bind="scope"></slot>
        </template>

        <!-- 子表单条纪录操作扩展插槽 -->
        <template #inner-before-extend-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_inner-before-extend-btn'" v-bind="scope"></slot>
        </template>
        <template #view-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_view-btn'" v-bind="scope"></slot>
        </template>
        <template #edit-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_edit-btn'" v-bind="scope"></slot>
        </template>
        <template #delete-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_delete-btn'" v-bind="scope"></slot>
        </template>
        <template #inner-after-extend-btn="scope">
          <slot :name="config.slaves[0].config.objectCode + '_inner-after-extend-btn'" v-bind="scope"></slot>
        </template>
      </table-view>
    </div>
  </div>
</template>

<script>
import utils from '../utils'
import {loadFeature} from "../utils/rest";
import {restUrl} from "../constant/url";
import {MasterSlaveGridConfig} from "../meta/feature/ext/featureType";
import {getNameOfFormTypes} from "../view/formview/ui-conf";
import {isEmpty} from "../utils/common";

export default {
  name: "MasterSlaveTableTmpl",
  meta: {
    isTemplate: true,
    isPage: false,
    cn: '主子表模板',
    icon: 'master_slaves',
    buildIn: true // 内建：DbMeta提供
  },
  props: {
    fc: String,
    config: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    const {fc: R_fc} = this.$route.query;
    const featureCode = utils.assertUndefined(this.fc, R_fc);
    return {
      featureCode: featureCode,
      activeMData: {}, // 主表选中的行
      filterParams: {},
    }
  },
  methods: {
    refresh() {
      this.refreshMasterData();
      this.refreshSlavesData();
    },
    handleChoseChange(rows) {
      // pxg_todo 多选主表(master)时, 子表(slaves)的默认行为(待定)
      const {master: {config: {objectCode}}} = this;
      this.$emit('m-chose-change', {
        rows: rows,
        objectCode: objectCode,
        primaryKey: this.$refs[objectCode].primaryKey
      });
    },
    handleActiveChange(row) {
      const {config: {master}} = this;
      this.activeMData = row;
      this.refreshSlavesData();
      this.$emit('m-active-change', {
        row: row, // 主表选中的单条记录
        masterObjectCode: master.config.objectCode,   // 主表的元对象编码
        masterPrimaryKey: master.config.primaryKey    // 主表的关联主键
      });
    },
    mHandleSearch(params) {
      const refName = this.config.master.config.objectCode;
      this.config.master.filterParams = params
      this.$nextTick(() => {
        this.$refs[refName].getData();
      });
    },
    sHandleSearch(slave, params) {
      slave.filterParams = params[0]
      this.refreshSlaveData(slave)
    },
    refreshMasterData() {   // 刷新master的业务数据
      const {master: {config: {objectCode: refName}}} = this.config;
      this.$nextTick(() => {
        this.$refs[refName].getData();
      })
    },
    refreshSlavesData() {   // 刷新所有slave的业务数据
      const {config: {slaves}} = this;
      slaves.forEach(slave => {
        this.refreshSlaveData(slave)
      });
    },
    refreshSlaveData(slave) {
      const {config: {master: {config: {primaryKey}}}, activeMData} = this;
      const foreignPrimaryValue = activeMData[primaryKey];
      const {config: {objectCode: refName, foreignPrimaryKey}} = slave

      if (utils.isEmpty(foreignPrimaryValue)) {
        this.$nextTick(() => {
          this.$refs[refName].emptyData(); // 主表未选择，子表置空
        })
      } else {
        slave.filterParams[foreignPrimaryKey + "_eq"] = foreignPrimaryValue
        this.$nextTick(() => {
          this.$refs[refName].getData();
        })
      }
    },
    handleAdd(slave) {
      if (utils.isEmpty(this.activeMData)) {
        this.$message.warning('请先选择一条主表记录', '提示');
        return;
      }

      const {featureCode, activeMData, config: {master: {config: {primaryKey}}}} = this;
      const {config: {foreignPrimaryKey: foreignKeyName}} = slave
      const sObjectCode = slave.config.objectCode;
      const foreignKeyValue = activeMData[primaryKey];
      const url = restUrl.MASTER_SLAVE_TO_ADD_S
      const params = {
        objectCode: sObjectCode,
        featureCode: featureCode,
        foreignKeyName: foreignKeyName,
        foreignKeyValue: foreignKeyValue
      }
      this.openSlaveFormView({url: url, params: params}, slave);
    },
    openMasterFormView({url, params = {}}) {
      // TODO 2.3 表单的打开方式可以配置到功能里: 1-弹窗(当前已实现) ;2-路由(系统预置FormTmpl模板以及内置的/form/:fc路由后，即可实现)
      this.$merge(params, {
        instanceCode: this.config.master.instanceCodes.FormView
      })
      const finalUrl = this.$compile(url, params)
      this.$axios.get(finalUrl).then(resp => {
        const {data: meta = {}} = resp
        const {form_type} = meta
        this.$dialog(meta, null, {
          title: getNameOfFormTypes(form_type)
        }).then(value => {
          this.refreshMasterData()
        }).catch(() => {
          utils.printInfo('您取消了表单')
        })
      })
    },
    openSlaveFormView({url, params = {}}, slave) {
      this.$merge(params, {
        instanceCode: slave.instanceCodes.FormView
      })

      const finalUrl = this.$compile(url, params)
      this.$axios.get(finalUrl).then(resp => {
        const {data: meta = {}} = resp
        const {form_type} = meta
        this.$dialog(meta, null, {
          title: getNameOfFormTypes(form_type)
        }).then(value => {
          this.refreshSlaveData(slave)
        }).catch(() => {
          utils.printInfo('您取消了表单')
        })
      })
    }
  },
  watch: {
    'fc': {
      handler: function (newV) {
        if (!isEmpty(newV)) {
          loadFeature(this.$axios, newV).then(resp => {
            this.$reverseMerge(this.config, resp.data)
          }).catch(() => {
          }).finally(() => {
            const {master, slaves = []} = this.config

            // 初始化主表外部过滤参数
            this.$merge(master, {
              filterParams: {}
            })

            // 初始化子表外部过滤参数
            slaves.forEach(slave => {
              const {config: {foreignPrimaryKey}} = slave
              const filterParams = {}
              // 通过向子表的数据请求参数中植入未编译的参数项，达到——主表未选择时，子表不出数据的目的
              filterParams[foreignPrimaryKey + "_eq"] = `{${foreignPrimaryKey}}`
              this.$merge(slave, {
                filterParams: filterParams
              })
            })
          })
        }
      },
      immediate: true
    }
  },
  created() {
    this.$merge(this.config, MasterSlaveGridConfig)
  },
}
</script>

<style scoped>
</style>
