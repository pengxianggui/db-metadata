<template>
    <div>
        <el-input v-model="nativeValue"
                  v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                  :name="innerMeta.name"
                  @blur="$emit('blur', $event)"
                  @focus="$emit('focus', $event)"
                  @change="$emit('change', $event)"
                  @clear="handlerClear($event)"
                  v-on:click.native="handlerClick($event)"
                  suffix-icon="el-icon-search"
        ></el-input>
        <dialog-box :visible.sync="dialogVisible" :meta="dialogMeta">
            <find-panel :meta="findPanelMeta" @ok="handlerOk" @cancel="handlerCancel"></find-panel>
            <template #footer><span></span></template>
        </dialog-box>
    </div>
</template>

<script>
    // TODO 需要支持 1. 多选！ 2. 控件上需要能够显示key, 值为value

    import utils from '../../../utils'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import FindPanel from './FindPanel'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "FindBox",
        label: "查找框",
        components: {FindPanel},
        inject: ['formData'],
        props: {
            value: [String, Number, Array]
        },
        data() {
            return {
                findPanelMeta: {},
                dialogMeta: {
                  conf: {
                    width: '70%'
                  }
                },
                dialogVisible: false
            }
        },
        methods: {
            handlerClick(ev) {
                if (ev) ev.stopPropagation();
                const {innerMeta} = this;
                const {data_url: url} = innerMeta;

                if (!url) {
                    console.error('data_url is required property, and not nullable. meta: %o', innerMeta);
                    return;
                }
                this.$axios.get(url).then(resp => {
                    this.findPanelMeta = resp.data;
                    this.findPanelMeta.component_name = 'FindPanel';
                    this.dialogVisible = true;
                }).catch(({msg ='Error'}) => {
                    console.error(msg)
                })
            },
            handlerOk(row) {
                // const {table: tableMeta} = this.findPanelMeta;
                // const {objectPrimaryKey} = tableMeta;

                this.callbackOk(row);
                // const feedBackValue = utils.extractValue(row, objectPrimaryKey);
                // if (this.$listeners.hasOwnProperty('ok')) {
                //     this.$emit('ok', {row, meta: tableMeta});
                //     this.dialogVisible = false;
                //     return;
                // }
                // this.nativeValue = feedBackValue[0];
                this.dialogVisible = false;
            },
            handlerCancel() {
                this.nativeValue = null;
                this.dialogVisible = false;
                this.callbackCancel();
            },
            handlerClear(ev) {
                if (ev) ev.stopPropagation();
                this.$emit('clear', ev);
                this.callbackCancel();
            },
            callbackOk(row) {
              const {name, okFn} = this.innerMeta;
              let okFunction
              try {
                okFunction = utils.strToFn(okFn);
              } catch (e) {
                utils.printErr('%s 的callback.ok配置不是合法的函数, 请检查! row:%s', name, JSON.stringify(row))
                return;
              }

              try {
                return okFunction.call(utils, row, this.formData);
              } catch (e) {
                utils.printErr("%s 的callback.ok函数运行发生错误, 请检查! 错误信息: %s", name, e.message)
              }
            },
            callbackCancel() {
              const {name, cancelFn} = this.innerMeta;
              let cancelFunction
              try {
                cancelFunction = utils.strToFn(cancelFn);
              } catch (e) {
                utils.printErr('%s 的callback.cancel配置不是合法的函数, 请检查! row:%s', name, JSON.stringify(row))
                return;
              }

              try {
                return cancelFunction.call(utils, this.formData);
              } catch (e) {
                utils.printErr("%s 的callback.cancel函数运行发生错误, 请检查! 错误信息: %s", name, e.message)
              }
            }
        }
    }
</script>

<style scoped>

</style>
