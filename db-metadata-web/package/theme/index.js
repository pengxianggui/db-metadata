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

// 内建主题色
const buildColors = {
    light: {
        header: {
            titleColor: '#409EFF',
            backgroundColor: '#ffffff'
        },
        menu: {
            textColor: '#303133',
            activeTextColor: '#409EFF',
            backgroundColor: '#ffffff'
        },
        tag: {
            show: true,
            textColor: '#818080',
            backgroundColor: '#f8f8f8',
            activeTextColor: '#ffffff',
            activeBackgroundColor: '#409EFF'
        }
    },
    dark: {
        header: {
            titleColor: '#409EFF',
            backgroundColor: '#2d3032'
        },
        menu: {
            textColor: '#ceced0',
            activeTextColor: '#409EFF',
            backgroundColor: '#2d3032'
        },
        tag: {
            show: true,
            textColor: '#818080',
            backgroundColor: '#f8f8f8',
            activeTextColor: '#ffffff',
            activeBackgroundColor: '#409EFF'
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
        reverseMerge(data, buildColors[data.themeColor])
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
