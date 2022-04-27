import utils from '../../../utils'

export default function (meta) {
    const {columns = []} = meta
    columns.forEach(c => {
        c.label = utils.assertEmpty(c.label, c.name)

        this.$set(this.showColumns, c.name, {
            label: c.label,
            show: true
        })
    })
    return meta
}
