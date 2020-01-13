import utils from '@/utils'

export default {
    bind: function (el, binding, vnode) {
        const {scope, item: column} = binding.value;
        const value = scope.row[column.name];
        if (column.render) {
            let first = column.render.indexOf('{');
            let last = column.render.lastIndexOf('}');
            let renderBody = column.render.substring(first + 1, last);
            let renderFn = new Function('el', 'value', renderBody);
            renderFn(el, value);
        } else {
            el.innerHTML = '<span>' + utils.assertEmpty(value, '') + '</span>'
        }
    }
}