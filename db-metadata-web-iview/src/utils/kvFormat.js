// 预制的K:V格式器

/**
 * data like : ['Main', 'Slave', ...]
 * @param data
 */
const converKv1 = function (data) {
    let arr = [];
    for (let i = 0; i < data.length; i++) {
        let item = {
            key: data[i],
            value: data[i]
        };
        arr.push(item)
    }
    return arr;
}

const converKv2 = function (data) {
    // ...
}

export default {
    converKv1,
    converKv2
}
