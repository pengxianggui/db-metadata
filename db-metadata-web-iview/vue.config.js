const path = require('path')
function resolve(dir) {
    return path.join(__dirname, dir)
}

const name = "db-metadata-web-iview"

module.exports = {
    publicPath: '/',
    outputDir: 'dist',
    assetsDir: 'static',
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
}
