import Vue from 'vue'

/**
 * Dynamic Content dialog box
 * @param meta 组件元对象, 弹出内容用什么组件渲染、数据是什么均有meta决定
 * @returns {{}}
 */
export default function (meta) {
    let promise = new Promise(function (resolve, reject) {

        let DialogTmpl = Vue.extend({
            template: `
            <el-dialog :visible.sync="visible">
                <component :ref="innerMeta.name" :is="innerMeta.component_name" :meta="innerMeta" @ok="ok" @cancel="cancel"></component>
            </el-dialog>
        `,
            data() {
                return {
                    visible: true,
                    innerMeta: meta
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
            }
        });

        let dialog = new DialogTmpl().$mount();
        document.getElementById('app').appendChild(dialog.$el);

    });
    return promise;
}