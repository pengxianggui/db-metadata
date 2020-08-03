import utils from '@/../package/utils'
import Mock from 'mockjs'
import component from './modules/component'
import feature from './modules/feature'
import menu from './modules/menu'
import route from './modules/route'

const mocks = [
    ...component,
    ...feature,
    ...menu,
    ...route
];

export function mockXHR() {
    function XHR2ExpressReqWrap(respond) {
        return function (options) {
            let result = null;
            if (respond instanceof Function) {
                const {body, type, url} = options;
                // https://expressjs.com/en/4x/api.html#req
                result = respond({
                    method: type,
                    body: JSON.parse(body),
                    query: utils.param2Obj(url)
                })
            } else {
                result = respond
            }
            return Mock.mock(result)
        }
    }

    for (const i of mocks) {
        if (i.disable) continue; // 方便mock/real切换

        let resp = XHR2ExpressReqWrap(i.response);
        Mock.mock(new RegExp(i.url), i.type || 'get', resp)
    }
}
