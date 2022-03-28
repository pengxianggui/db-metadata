import cacheKey from "../constant/cacheKey";
import {isEmpty} from "../utils/common";
import {reverseMerge} from "../utils/merge";

// 默认主题数据
const defaultThemeData = {
    layout: 'row', // row/column
    freeMode: false, // 自由定义模式
    themeColor: 'light',
    header: {
        textColor: '#409EFF',
        backgroundColor: '#ffffff',
    },
    menu: {
        textColor: '#303133',
        activeTextColor: '#409EFF',
        backgroundColor: '#ffffff',
        uniqueOpened: false
    },
    tag: {
        show: true,
        textColor: '#818080',
        backgroundColor: '#f8f8f8',
        activeTextColor: '#ffffff',
        activeBackgroundColor: '#409EFF'
    }
}

// 缓存中的主题数据：为了实现实时预览
const themeData = {}

export const buildInThemesOptions = [
    {key: '默认', value: 'light', color: '#ffffff'},
    {key: '深色', value: 'dark', color: '#1f1f1f'},
    {key: '紫色', value: 'purple', color: '#605ca8'},
    {key: '蓝色', value: 'blue', color: '#008ec0'},
    {key: '红色', value: 'red', color: '#dd4b39'},
    {key: '绿色', value: 'green', color: '#4dca8f'},
]
// 内建主题色
const buildInThemes = {
    light: {
        header: {
            textColor: '#409EFF',
            backgroundColor: '#ffffff'
        },
        menu: {
            textColor: '#303133',
            activeTextColor: '#409EFF',
            backgroundColor: '#ffffff'
        },
        tag: {
            textColor: '#818080',
            backgroundColor: '#f8f8f8',
            activeTextColor: '#ffffff',
            activeBackgroundColor: '#409EFF'
        }
    },
    dark: {
        header: {
            textColor: '#409EFF',
            backgroundColor: '#1f1f1f'
        },
        menu: {
            textColor: '#ceced0',
            activeTextColor: '#409EFF',
            backgroundColor: '#1f1f1f'
        },
        tag: {
            textColor: '#818080',
            backgroundColor: '#dddddd',
            activeTextColor: '#ffffff',
            activeBackgroundColor: '#828689'
        }
    },
    purple: {
        header: {
            textColor: '#ffffff',
            backgroundColor: '#605ca8'
        },
        menu: {
            textColor: '#ceced0',
            activeTextColor: '#605ca8',
            backgroundColor: '#2a4051'
        },
        tag: {
            textColor: '#818080',
            backgroundColor: '#dddddd',
            activeTextColor: '#ffffff',
            activeBackgroundColor: '#afaed0'
        }
    },
    blue: {
        header: {
            textColor: '#ffffff',
            backgroundColor: '#008ec0'
        },
        menu: {
            textColor: '#4b4d4e',
            activeTextColor: '#008ec0',
            backgroundColor: '#f9fafc'
        },
        tag: {
            textColor: '#818080',
            backgroundColor: '#f8f8f8',
            activeTextColor: '#ffffff',
            activeBackgroundColor: '#8fcfe5'
        }
    },
    red: {
        header: {
            textColor: '#ffffff',
            backgroundColor: '#dd4b39'
        },
        menu: {
            textColor: '#c1c1c1',
            activeTextColor: '#dd4b39',
            backgroundColor: '#2a4051'
        },
        tag: {
            textColor: '#969696',
            backgroundColor: '#f8f8f8',
            activeTextColor: '#5a5a5a',
            activeBackgroundColor: '#ffd9d0'
        }
    },
    green: {
        header: {
            textColor: '#ffffff',
            backgroundColor: '#4dca8f'
        },
        menu: {
            textColor: '#4b4d4e',
            activeTextColor: '#4dca8f',
            backgroundColor: '#f9fafc'
        },
        tag: {
            textColor: '#a7a7a7',
            backgroundColor: '#f8f8f8',
            activeTextColor: '#6e6e6e',
            activeBackgroundColor: '#c8ffe2'
        }
    }
}

export const layoutOptions = [
    {
        key: '垂直',
        value: 'column',
        icon: {
            'light': 'light-column-layout',
            'dark': 'dark-column-layout'
        }
    },
    {
        key: '水平',
        value: 'row',
        icon: {
            'light': 'light-row-layout',
            'dark': 'dark-row-layout'
        }
    }
]

/**
 * 保存主题配置数据
 * @param data
 */
const setTheme = function (data) {
    const {freeMode} = data
    if (!freeMode) { // 简单模式: 则由themeColor推导的具体配置覆盖
        reverseMerge(data, buildInThemes[data.themeColor])
    }
    localStorage.setItem(cacheKey.keyInLocal.THEME_DATA_KEY.value, JSON.stringify(data))
}

/**
 * 重置主题: 清空用户主题缓存
 */
const resetTheme = function () {
    localStorage.removeItem(cacheKey.keyInLocal.THEME_DATA_KEY.value)
    localStorage.removeItem(cacheKey.keyInLocal.THEME_OVERVIEW.value)
}

/**
 * 获取主题配置数据
 */
const getTheme = function () {
    if (!isEmpty(themeData)) {
        return themeData
    }

    const dataStr = localStorage.getItem(cacheKey.keyInLocal.THEME_DATA_KEY.value)
    reverseMerge(themeData, isEmpty(dataStr) ? defaultThemeData : JSON.parse(dataStr))
    return themeData
}

const configDefaultTheme = function (opts) {
    const {theme = {}} = opts
    reverseMerge(defaultThemeData, theme)
}

export default {
    getTheme,
    setTheme,
    resetTheme,
    configDefaultTheme
}
