/**
 * 各个功能模板的初始config配置
 * @type {{MasterSlaveGrid: {getInitConf: (function(*=, *=): {slaves: [{objectCode: null, foreignFieldCode: null, order: number}], master: {objectCode: *, primaryKey: *}})}, TreeAndTable: {getInitConf: (function(*=, *=): {tree: {pidKey: null, idKey: *, objectCode: null, label: null, rootIdentify: null, isSync: boolean}, table: {objectCode: *, foreignFieldCode: null, primaryKey: null}})}, TreeInTable: {getInitConf: (function(*=): {table: {pidKey: null, idKey: null, objectCode: *, label: null, rootIdentify: null, isSync: boolean}})}, SingleGrid: {getInitConf: (function(*=): {singleGrid: {objectCode: *}})}}}
 */
export const FEATURE_TYPE_MAPPING = {
    'MasterSlaveGrid': {
        getInitConf: function (objectCode, primaryKey) {
            return {
                master: {
                    objectCode: objectCode,
                    primaryKey: primaryKey
                },
                slaves: [{
                    objectCode: null,
                    foreignFieldCode: null,
                    order: 0
                }]
            }
        }
    },
    'SingleGrid': {
        getInitConf: function (objectCode) {
            return {
                singleGrid: {
                    objectCode: objectCode
                }
            }
        }
    },
    'TreeInTable': {
        getInitConf: function (objectCode) {
            return {
                table: {
                    objectCode: objectCode,
                    idKey: null,
                    pidKey: null,
                    rootIdentify: null,
                    label: null,
                    isSync: false
                }
            }
        }
    },
    'TreeAndTable': {
        getInitConf: function (objectCode, primaryKey) {
            return {
                tree: {
                    objectCode: null,
                    idKey: primaryKey,
                    pidKey: null,
                    rootIdentify: null,
                    label: null,
                    isSync: false,
                    // primaryKey: primaryKey
                },
                table: {
                    objectCode: objectCode,
                    primaryKey: null,
                    foreignFieldCode: null
                }
            }
        }
    }
};

