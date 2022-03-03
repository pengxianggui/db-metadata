import cacheKey from "../constant/cacheKey";
import {isEmpty} from "../utils/common";

// 默认主题数据
const defaultThemeData = {
    header: {
        titleColor: '#409EFF',
        backgroundColor: '#ffffff'
    },
    menu: {
        textColor: '#303133',
        activeTextColor: '#409EFF',
        backgroundColor: '#ffffff',
        uniqueOpened: false,
        mode: 'vertical' // horizontal/vertical
    },
    tag: {
        show: true,
        textColor: '#ffffff',
        backgroundColor: '#409EFF',
        outPath: '/'
    }
}

// 内建布局
export const buildInLayouts = {
    vertical: {
        menu: {
            mode: 'vertical' // horizontal/vertical
        }
    },
    horizontal: {
        menu: {
            mode: 'horizontal' // horizontal/vertical
        }
    }
}

// 内建色彩
export const buildColors = {
    light: {
        header: {
            titleColor: '#409EFF',
            backgroundColor: '#ffffff'
        },
        menu: {
            textColor: '#303133',
            activeTextColor: '#409EFF',
            backgroundColor: '#ffffff'
        }
    },
    dark: {
        header: {
            titleColor: '#409EFF',
            backgroundColor: '#001528'
        },
        menu: {
            textColor: '#ceced0',
            activeTextColor: '#409EFF',
            backgroundColor: '#001528'
        }
    }
}

/**
 * 保存主题配置数据
 * @param data
 */
const setTheme = function (data) {
    localStorage.setItem(cacheKey.keyInLocal.THEME_DATA_KEY, JSON.stringify(data))
}

/**
 * 获取主题配置数据
 */
const getTheme = function () {
    const themeData = localStorage.getItem(cacheKey.keyInLocal.THEME_DATA_KEY)
    if (isEmpty(themeData)) {
        return defaultThemeData
    }
    return JSON.parse(themeData)
}

export default {
    getTheme,
    setTheme
}
