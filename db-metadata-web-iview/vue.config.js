let alias = require('./alias.config');

const name = "db-metadata-web";
const serverProxy = {
    target: 'http://localhost:8888',
    // target: 'http://192.168.110.67:8888',
    pathRewrite: {'^/': ''},
    changeOrigin: true
};
module.exports = {
    publicPath: '/',
    outputDir: 'dist',
    assetsDir: 'static',
    indexPath: 'index.html',
    runtimeCompiler: true, // 运行时编译, 支持运行时动态template
    configureWebpack: {
        // provide the app's title in webpack's name field, so that
        // it can be accessed in index.html to inject the correct title.
        name: name,
        resolve: alias.resolve
    },
    devServer: {
        host: '0.0.0.0',
        port: 8080,
        https: false,
        sockHost: 'localhost',
        // proxy: 'http://192.168.110.67:8888'
        proxy: {
            '^\/db\/': serverProxy,
            '^\/meta\/': serverProxy,
            '^\/component\/': serverProxy,
            '^\/table\/': serverProxy,
            '^\/form\/': serverProxy,
            '^\/dict\/': serverProxy,
            '^\/file\/': serverProxy,
            '^\/check\/': serverProxy,
            '^\/find\/': serverProxy,
            '^\/m\/': serverProxy,
            '^\/route\/': serverProxy,
        }
    }
};
