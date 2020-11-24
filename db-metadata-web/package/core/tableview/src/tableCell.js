import utils from '../../../utils'

export default {
    name: 'TableCell',
    props: {
        data: {
            type: Object,
            required: true
        },
        meta: {
            type: Object,
            required: true
        },
        edit: {
            type: Boolean,
            default: () => false
        }
    },
    data() {
        return {}
    },
    render(h) {
        const {edit, data: {row}, meta = {}} = this;
        let value = row[meta.name];
        if (!edit) {
            /*
                meta.render 为函数声明. 例如:
                {
                    render: function(h, value) {
                        return h("span", {
                            attrs: {
                                style: "color: red",
                            }
                        }, value);
                    }
                }
                因此, render函数中必须调用h创建节点, 并返回. 参数：
                1. h: 即$createElement函数
                2. value: 即单元格原本需要展示的值
             */

            if (!utils.isEmpty(meta.render)) {
                const {data: {row}} = this
                let render

                try {
                    render = utils.strToFn(meta.render);
                } catch (e) {
                    console.error('[Meta-Element] the render(in field meta) is not function, meta: %o, value: %s, will show original value', meta, value);
                    return h('span', null, value);
                }

                try {
                    return render(h, value, row);
                } catch (e) {
                    console.error("[Meta-Element] s% 的render函数运行发生错误, 请检查!", meta.name)
                    console.error(e)
                }
            }
            return h('span', null, value);
        } else {
            // todo 编辑模式, 即 多行编辑, 根据meta和value, 渲染表单控件

        }
    },
    create() {

    }

}
