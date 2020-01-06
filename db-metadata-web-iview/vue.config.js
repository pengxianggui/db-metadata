let config = require('./config');
let alias = require('./alias.config');

const name = "db-metadata-web";
const serverProxy = {
    target: 'http://localhost:8888',
    // target: 'http://192.168.110.67:8888',
    pathRewrite: {'^/meta': ''},
    changeOrigin: true
};

module.exports = {
    publicPath: config.publicPath,
    outputDir: 'dist',
    assetsDir: 'static',    // 静态资源存放目录,相对于outputDir
    indexPath: 'index.html', // 生成的index.html的输出路径(相对于outputDir), 也可以是绝对路径
    runtimeCompiler: true, // 运行时编译, 支持运行时动态template, dialog.js等部分内容需要此项目支持
    // crossorigin: "",  // 设置生成的html中 link和script标签的 crossorigin属性, 仅影响由html-webpack-plugin在构建时注入的标签,直接写在模板中的标签不受影响
    // configureWebpack: config => {
    // },
    configureWebpack: {
        // provide the app's title in webpack's name field, so that
        // it can be accessed in index.html to inject the correct title.
        name: name,
        resolve: alias.resolve  // 提取成独立的alias.config, 配置idea识别`@`根目录
    },
    devServer: {
        host: '0.0.0.0',
        port: 8080,
        https: false,
        sockHost: 'localhost',
        // proxy: 'http://192.168.110.67:8888'
        proxy: {
            '^\/meta\/': serverProxy,   // 超过11个node会提示MaxListenersExceededWarning
            // '^\/db\/': serverProxy,
            // '^\/meta\/': serverProxy,
            // '^\/component\/': serverProxy,
            // '^\/table\/': serverProxy,
            // '^\/form\/': serverProxy,
            // '^\/dict\/': serverProxy,
            // '^\/file\/': serverProxy,
            // '^\/check\/': serverProxy,
            // '^\/find\/': serverProxy,
            // '^\/feature\/': serverProxy,
            // '^\/route\/': serverProxy,
        }
    }
};
