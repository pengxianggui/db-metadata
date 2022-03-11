import {assert, isArray, isObject, typeOf} from "../../../utils/common";

export default function (columns) {
    assert(isArray(columns, '[MetaElement] columns必须是数组'))

    columns.forEach(c => {
        const {name, conf} = c
        assert(isObject(conf), `[MetaElement] ${name}列的conf配置不是对象类型, 其类型为 ${typeOf(conf)}`)
    })
}