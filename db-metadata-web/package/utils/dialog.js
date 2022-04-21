import utils from './index';
import DefaultDialogBoxMeta from '../core/dialogbox/ui-conf'
import {compile, validUrlCompiled} from "./url";
import {restUrl} from "../constant/url";
import {isEmpty, printErr} from "./common";
import {getFormInstanceUrl} from "../view/formview";

/**
 * Dynamic Content dialog box
 * @param meta 组件元对象, 弹出内容用什么组件渲染、数据是什么均有meta决定
 * @param data 弹出组件的业务数据
 * @param conf 弹框自身的配置
 * @returns {{}}
 */
export function createDialog(Vue) {

    return function dialog(metaOrProps, data, conf) {
        const parentComponent = this

        let dialogPromise = new Promise(function (resolve, reject) {
            const {ic} = metaOrProps

            let metaPromise
            if (isEmpty(ic)) { // ic为空, 则将metaOrProp视为meta
                metaPromise = new Promise(((resolve1, reject1) => {
                    resolve1({data: metaOrProps}) // 包装到data里是为了下面解构时和实际http响应结构一致
                }))
            } else { // ic不为空, 表明弹窗的组件所需的元数据根据实例编码获取
                let metaUrl = restUrl.VIEW_INSTANCE_CONF // 获取meta的url

                const {formType} = metaOrProps
                if (!isEmpty(formType)) { // 表示是表单, 表单的metaUrl并不是通用的restUrl.VIEW_INSTANCE_CONF, 因为其渲染依据于其formType
                    const {primaryKv} = metaOrProps // 当表单为view或update时, 需要传递primaryKv
                    metaUrl = getFormInstanceUrl(formType, primaryKv)
                }

                const params = {instanceCode: ic}
                const compiledMetaUrl = compile(metaUrl, params)
                if (!validUrlCompiled(compiledMetaUrl)) {
                    printErr('url未编译, 确认参数传递能匹配。url: %s, params: %o', metaUrl, params)
                    reject()
                } else {
                    metaPromise = Vue.prototype.$axios.safeGet(compiledMetaUrl)
                }
            }

            metaPromise.then(({data: meta}) => {
                let DialogTmpl = Vue.extend(
                    // 'DialogTmpl',
                    {
                    template: `
                      <el-dialog :visible.sync="visible" v-bind="conf" center  :width="width">
                      <component :ref="meta.name" :is="meta.component_name" :meta="meta" v-model="data" v-bind="props"
                                 @ok="ok" @cancel="cancel"></component>
                      </el-dialog>
                    `,
                    name: "DialogTmpl",
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
                        ref() {
                            return this.$refs[this.meta.name];
                        },
                        props() {
                            return this.meta.conf
                        },
                        width() {
                            const {meta: {style: {width: widthInMeta = '50%'} = {}} = {}, conf: {width: widthInAttr}} = this
                            return utils.assertEmpty(widthInMeta, widthInAttr)
                        }
                    }
                });

                let dialog = new DialogTmpl().$mount();

                // doubt 貌似两种方式都不能保证vue devTool检测到弹窗内的组件
                document.getElementById('app').appendChild(dialog.$el);
                // parentComponent.$el.appendChild(dialog.$el);
            })
        });
        return dialogPromise;
    }
}


