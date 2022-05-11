import utils from '../../../utils'

export default function (meta) {
    const {columns = []} = meta

    let i = columns.length
    while (i-- > 0) {
        const {hidden = false, name, label} = columns[i]

        // 处理数据
        let finalLabel = utils.assertEmpty(label, name)
        columns[i].label = finalLabel

        // 初始化showColumns
        if (hidden !== true) {
            this.$set(this.showColumns, name, {
                label: finalLabel,
                show: true
            })
        }
    }
    return meta
}
