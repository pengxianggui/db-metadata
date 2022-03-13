import utils from './index';
import DefaultDialogBoxMeta from '../core/dialogbox/ui-conf'

/**
 * Dynamic Content dialog box
 * @param meta 组件元对象, 弹出内容用什么组件渲染、数据是什么均有meta决定
 * @param data 弹出组件的业务数据
 * @param conf 弹框自身的配置
 * @returns {{}}
 */
export function createDialog (Vue) {
    return function dialog(meta, data, conf) {
        let promise = new Promise(function (resolve, reject) {

            let DialogTmpl = Vue.extend({
                template: `
                  <el-dialog :visible.sync="visible" v-bind="conf" center>
                      <component :ref="meta.name" :is="meta.component_name" :meta="meta" v-model="data"
                                 @ok="ok" @cancel="cancel" style="width: 100%"></component>
                  </el-dialog>
                `,
                data() {
                    return {
                        visible: true,
                        meta: meta,
                        data: data,
                        conf: utils.merge(conf, DefaultDialogBoxMeta['conf'])
                    }
                },
                methods: {
                    ok: function (params) {
                        this.visible = false;
                        resolve(params);
                    },
                    cancel: function (params) {
                        this.visible = false;
                        reject(params);
                    }
                },
                computed: {
                    ref: function () {
                        return this.$refs[this.meta.name];
                    }
                }
            });

            let dialog = new DialogTmpl().$mount();
            document.getElementById('app').appendChild(dialog.$el);

        });
        return promise;
    }
}
