<template>
  <div class="view-container">
    <list style="width: 100%">
      <list-item v-for="(item, index) in innerData" :key="index"
                 :class="{'active': index === activeIndex}"
                 @click="handleClick(item, index, $event)">
        <slot v-bind:item="item">
          <span>{{ item[innerLabelProps.label] }}</span>
        </slot>
      </list-item>
    </list>
    <slot name="pagination" v-bind:pageModel="pageModel">
      <el-pagination background
                     :page-size.sync="pageModel.size"
                     :current-page.sync="pageModel.index"
                     :total="pageModel.total"
                     v-bind="meta.pagination"
                     @size-change="sizeChange"
                     @current-change="getData(pageModel)"
      ></el-pagination>
    </slot>
  </div>
</template>

<script>
import utils from '../../../utils'
import {defaultPrimaryKey} from "../../../config";
import DefaultMeta from '../ui-conf'

export default {
  name: "ListView",
  props: {
    labelProps: Object,
    meta: {
      type: Object,
      default: () => {
      }
    },
    data: Array,
    dataFunction: Function,
    page: {
      type: Object,
      default: () => {
        return {
          size: 10,
          index: 1,
          total: 0
        }
      },
      validator: (value) => value.hasOwnProperty('size')
          && value.hasOwnProperty('index')
          && value.hasOwnProperty('total')
    }
  },
  data() {
    const {size, index, total} = this.page
    return {
      innerData: [],
      activeIndex: null,
      activeData: {},
      pageModel: {
        size: size,
        index: index,
        total: total
      },
    }
  },
  methods: {
    setPage(index) {
      this.pageModel['index'] = index;
    },
    sizeChange() {
      this.setPage(1); // jump to page one
      this.getData(this.pageModel);
    },
    handleClick(row, index, event) {
      this.activeData = row;
      this.activeIndex = index;
      this.$emit('active-change', row);
    },
    setPageModel(page) {
      const {total, index, size} = page;
      if (!utils.isEmpty(total)) this.pageModel['total'] = parseInt(total);
      if (!utils.isEmpty(index)) this.pageModel['index'] = parseInt(index);
      if (!utils.isEmpty(size)) this.pageModel['size'] = parseInt(size);
    },
    getData(pageModel) {
      let {meta, dataFunction} = this;

      if (dataFunction && utils.isFunction(dataFunction)) {
        dataFunction.call(this, pageModel)
        return
      }

      if (!utils.hasProp(meta, 'data_url')) {
        console.error('lack data_url attribute');
        return;
      }

      const {data_url: url} = meta;
      const {index, size} = pageModel;

      Object.assign(params, {
        'p': index,
        's': size
      });

      this.$axios.safeGet(url, {
        params: params
      }).then(resp => {
        this.innerData = resp.data;
        this.$emit("data-change", resp.data);
        if (utils.hasProp(resp, 'page')) {
          this.setPageModel(resp['page']);
        }
      }).catch(({msg = 'Error'}) => {
        console.error(msg)
      });
    },
    initData() { // init business data
      let {pageModel, data, meta} = this;

      if (!utils.isUndefined(data)) {
        this.innerData = data;
        return;
      }

      if (utils.hasProp(meta, 'data_url')) {
        this.getData(pageModel);
        return;
      }
      console.error("data or data_url in meta provide one at least!")
    },
  },
  mounted() {
    this.initData();
  },
  watch: {
    'data': function (newVal, oldVal) {
      this.initData();    // 为避免data数据过大, 不进行深度监听
    },
    'meta.data_url': {
      handler: function () {
        this.initData();
      },
      immediate: false
    }
  },
  computed: {
    meta() {
      return this.$merge(this.meta, DefaultMeta);
    },
    innerLabelProps() {
      return utils.assertUndefined(this.labelProps, this.meta['conf']['label-props'])
    },
    primaryKey() {
      const {objectPrimaryKey} = this.meta;
      const defaultPrimaryKey = defaultPrimaryKey;

      if (utils.isEmpty(objectPrimaryKey)) {
        console.error('Missing primary key info! will use default primaryKey:%s', defaultPrimaryKey);
        return defaultPrimaryKey;
      } else {
        return objectPrimaryKey;
      }
    },
  }
}
</script>

<style scoped>
.active {
  background-color: #dddddd;
  color: #409EFF;
}
</style>
