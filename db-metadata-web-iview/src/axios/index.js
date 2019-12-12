import axios from './default'

// 扩展cancel ----------------------------------------------------------------------------------------------------------
Promise.cancel = function (msg) {
    console.warn(msg);
    return new Promise(resolve => {
    }, reject => {
    })
};
Promise.prototype.cancel = function (onCancel) {
    onCancel();
    return this;
};
// ---------------------------------------------------------------------------------------------------------------------

// 安全请求, 如果url中含有未编译的占位变量, 则取消请求

axios.safeGet = function (url, config) {
    if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
        return Promise.cancel('url: ' + url + ' 未编译 ...');
    }
    return axios.get(url, config);
};

axios.safePost = function (url, data, config) {
    if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
        return Promise.cancel('url: ' + url + ' 未编译 ...');
    }
    return axios.post(url, data, config);
};

export default axios
