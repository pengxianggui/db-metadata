const path = require('path')
function resolve(dir) {
    return path.join(__dirname, dir)
}

const name = "db-metadata-web-iview"

module.exports = {
    publicPath: '/',
    outputDir: 'dist',
    assetsDir: 'static',
    indexPath: 'index.html',
    configureWebpack: {
        // provide the app's title in webpack's name field, so that
        // it can be accessed in index.html to inject the correct title.
        name: name,
        resolve: {
            alias: {
                '@': resolve('src')
            }
        }
    },
    devServer: {
        host: '0.0.0.0',
        port: 8080,
        https: false,
        sockHost: 'localhost',
        // proxy: 'http://192.168.110.67:8888'
        // proxy: {
            // '/': {
            //     target: 'http://192.168.110.67:8888/',
            //     changeOrigin: true
            // }
        // }
    }
}
