export default function (value) {
    if (Array.isArray(value)) {
        return value.map(i => {
            return {
                url: i.url,
                name: i.name,
                value: i.value,
                seat: i.seat
            }
        })
    }

    console.error(`[MetaElement] ImgBox输出格式有误， 必须是数组`)
    return []
}
