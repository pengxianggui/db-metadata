import utils from './index'

export function fileExtractUrl(objVal, name) {
    if (!utils.isObject(objVal) || !utils.isString(name)) return objVal;

    let data = utils.deepCopy(objVal);

    if (data.hasOwnProperty(name)) {
        try {
            data[name] = objVal[name][0]['url'];
        } catch (e) {
            console.error(e);
        }
    }

    return data;
}