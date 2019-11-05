import Vue from 'vue'


/**
 * pxg_todo 传入 component、 meta 和 调用组件对象本身(调用者this), 将渲染的视图组件 添加到dialog中
 * @param component
 * @param meta
 * @param self
 * @returns {{}}
 */
export default function (meta, self) {

    let DialogTmpl = Vue.extend({
        template: `
            <el-dialog :visible.sync="visible">
                <component :is="innerMeta.component_name" :meta="innerMeta"></component>
            </el-dialog>
        `,
        data() {
            return {
                visible: true,
                innerMeta: meta
            }
        },
    });

    let dialog = new DialogTmpl().$mount();
    document.getElementById('app').appendChild(dialog.$el);
}