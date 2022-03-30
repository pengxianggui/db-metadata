let alias = require('./alias.config');
let path = require('path')

const name = "db-metadata-web";
module.exports = {
    publicPath: "/",
    outputDir: 'dist',
    lintOnSave: false,
    assetsDir: 'static',    // 静态资源存放目录,相对于outputDir
    indexPath: 'index.html', // 生成的index.html的输出路径(相对于outputDir), 也可以是绝对路径

    runtimeCompiler: true, // 运行时编译, 支持运行时动态template, dialog.js等部分内容需要此项目支持
    // crossorigin: "",  // 设置生成的html中 link和script标签的 crossorigin属性, 仅影响由html-webpack-plugin在构建时注入的标签,直接写在模板中的标签不受影响
    configureWebpack: {
        // provide the app's title in webpack's name field, so that
        // it can be accessed in index.html to inject the correct title.
        name: name,
        resolve: alias.resolve,  // 提取成独立的alias.config, 配置idea识别`@`根目录
    },
    devServer: {
        host: '0.0.0.0',
        port: 8080,
        https: false,
        sockHost: 'localhost',
        proxy: {
            [process.env.VUE_APP_BASE_API]: {
                target: process.env.VUE_APP_PROXY_URL,
                pathRewrite: {['^' + process.env.VUE_APP_BASE_API]: ''},
                changeOrigin: true
            }
        }
    },
    // 扩展 webpack 配置，使 packages 加入编译
    chainWebpack: config => {
        config.module.rule('js')
            .include.add('/package').end()
            .use('babel').loader('babel-loader').tap(options => options)

        // set svg-sprite-loader
        config.module
            .rule('svg')
            .exclude.add(path.join(__dirname, 'package/asserts/svg/src'))
            .end()
        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(path.join(__dirname, 'package/asserts/svg/src'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'me-icon-[name]'
            })
            .end()
    }
};
